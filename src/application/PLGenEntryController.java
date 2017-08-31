/**
 * Sample Skeleton for 'plGenEntry.fxml' Controller Class
 */

package application;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import db.RCTables;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import util.SongUtils;

public class PLGenEntryController 
{
	// if a potential song file does not have these extensions, discount it automatically
	static final String[] legalExtensions =
		{
				"mp3",
				"flac",
				"wma",
				"ogg",
				"wav",
				"m4a"
		};


	// will cause the scanAndUpdate method to terminate if true
	private boolean stop = false;

	// Specifies whether we shut down gracefully or not
	private boolean graceful = false;






	@FXML // ResourceBundle that was given to the FXMLLoader
	private ResourceBundle resources;

	@FXML // URL location of the FXML file that was given to the FXMLLoader
	private URL location;

	@FXML // fx:id="songLabel"
	private Label songLabel; // Value injected by FXMLLoader

	@FXML // fx:id="overallProgressBar"
	private ProgressBar overallProgressBar; // Value injected by FXMLLoader

	@FXML // fx:id="mainBtn"
	private Button mainBtn; // Value injected by FXMLLoader

	@FXML // fx:id="ostProgressBar"
	private ProgressBar ostProgressBar; // Value injected by FXMLLoader

	@FXML // fx:id="ostLabel"
	private Label ostLabel; // Value injected by FXMLLoader

	@FXML
	void onButtonHit(ActionEvent event) 
	{
		if(!graceful) // User forcefully stopped the application, so throw up an alert
		{
			Alert a = new Alert(AlertType.CONFIRMATION, "", ButtonType.YES, ButtonType.NO);
			a.setHeaderText("Playlist Generator Cancel");
			a.setContentText("Really abort playlist generation? This will result in an incomplete playlist.");
			a.showAndWait();

			if(a.getResult().equals(ButtonType.YES))
			{
				System.out.println("Aborted Playlist Generation");
				stop = true;

				Stage stage = (Stage) mainBtn.getScene().getWindow(); // Only close if they hit yes...
				stage.close();
			}
		} else {

			// ...Or we finished gracefully and they acknowledged it
			Stage stage = (Stage) mainBtn.getScene().getWindow();
			stage.close();
		}
	}

	@FXML // This method is called by the FXMLLoader when initialization is complete
	void initialize() 
	{
		assert songLabel != null : "fx:id=\"songLabel\" was not injected: check your FXML file 'plGenEntry.fxml'.";
		assert overallProgressBar != null : "fx:id=\"overallProgressBar\" was not injected: check your FXML file 'plGenEntry.fxml'.";
		assert mainBtn != null : "fx:id=\"cancelBtn\" was not injected: check your FXML file 'plGenEntry.fxml'.";
		assert ostProgressBar != null : "fx:id=\"ostProgressBar\" was not injected: check your FXML file 'plGenEntry.fxml'.";
		assert ostLabel != null : "fx:id=\"ostLabel\" was not injected: check your FXML file 'plGenEntry.fxml'.";



		// business logic
		scanAndUpdate("/C:/users/duemmer/music/mh");
	}



	public void scanAndUpdate(String root)
	{
		Task<Void> tsk = new Task<Void>() 
		{
			// Used for calculating runtime metrics
			long tStart = System.currentTimeMillis();
			int numSongs = 0;
			
			// mark the song / ost the system is currently processing
			int currOst;
			int currSong;

			@Override
			protected Void call() throws Exception 
			{
				RCTables.playlistTable.verifyExists(Main.db.getDb());

				File[] ostArr = new File(root).listFiles(File::isDirectory);
				int ostCt = ostArr.length;

				for(currOst=0; currOst<ostCt; currOst++)
				{
					if(stop) { break; } // break if necessary

					File ost = ostArr[currOst];

					//update Overall Progress bar(percent done w/ OSTs) and OST label (in form OST: $ost_dir_name)
					Platform.runLater(() -> {
						overallProgressBar.setProgress( ((double)currOst+1) / ((double)ostCt) ); 
						ostLabel.setText("OST: " +ost.getName());
//						ostLabel.setText("OST: AM2R"); // just for the pictures I send dys <3
					});


					File[] songArr = ostArr[currOst].listFiles(SongUtils::isLegalExtension); // We only want to mess with song files
					int songCt = songArr.length;
					
					numSongs+= songCt; // add these songs to the running total
					
					for(currSong=0; currSong<songCt; currSong++)
					{
						if(stop) { break; } // break if necessary

						File song = songArr[currSong];

						//update OST Progress bar(percent done w/ Songs) and Song label (in form Song: $song_file_name)
						Platform.runLater(() -> {
							ostProgressBar.setProgress( ((double)currSong+1) / ((double)songCt) ); 
							songLabel.setText("Song: " +song.getName());
//							songLabel.setText("Song: Hydro_Station.mp3"); // just for the pictures I send dys <3
						});
						procSong(song, ost); // do all the brute work on this current song
					}
				}

				graceful = !stop; //if stop hasn't been set, then it finished on its own
				if(graceful)
				{
					Platform.runLater(() -> {
						ostLabel.setText("OST: Done");
						songLabel.setText("Song: Done");
						mainBtn.setText("OK");
					});
				}
				
				// Calculate / print final metrics
				double numSecs = ((double)(System.currentTimeMillis() -  tStart)) / 1000.0;
				double songsPerSec = numSongs / numSecs;
				
				System.out.println(String.format("Playlist generator processed %d songs in %.3f seconds at an average rate of %.4f songs per second", numSongs, numSecs, songsPerSec));

				return null;
			}
		};


		Thread t = new Thread(tsk);
		t.setDaemon(true);
		t.setName("PlaylistGen");
		t.start();
	}





	private static void procSong(File song, File ost)
	{
		// All the important pieces of info about the song
		String songName = "";
		String ostName = "";
		String songID = "";
		int dur = -1;


		try {
			// Calculate duration, which is independent of audio file format
			AudioFileFormat aff = AudioSystem.getAudioFileFormat(song);
			dur = (int) (aff.getFrameLength() / aff.getFormat().getFrameRate());

			// we can only get song name/ost from tag data in MP3s, so only try to get that info if it's an MP3
			if(SongUtils.getExt(song).equalsIgnoreCase("mp3"))
			{
				MP3File mp3 = new MP3File(song);
				songName = SongUtils.clean(mp3.getID3v2Tag().getSongTitle());
				ostName = SongUtils.clean(mp3.getID3v2Tag().getAlbumTitle());
			} 

			// Just use file / directory names for song / ost names if they are empty (not an MP3 or missing tag data)
			if(songName.isEmpty())
				songName = song.getName().substring(0, song.getName().lastIndexOf('.')); // remove extension

			if(ostName.isEmpty())
				ostName = ost.getName();

			songID = ost.getName() +"/"+ song.getName(); // SongID is just the path of the song relative to the playlist root directory

			// Format the statement to enter these into the database
			// We will either insert new records OR if we find a matching
			// song_id, we will update the song_name, song_ost, and song_length records only
			String sql = "INSERT OR REPLACE INTO " +RCTables.playlistTable.getName()+ " (song_name, ost_name, song_length, rating_pct, rating_num, song_id) VALUES ("
					+ "?, "
					+ "?, "
					+ "?, "
					+ "COALESCE((SELECT rating_pct FROM " +RCTables.playlistTable.getName()+ " WHERE song_id=?), 0.0), "
					+ "COALESCE((SELECT rating_num FROM " +RCTables.playlistTable.getName()+ " WHERE song_id=?), 0), "
					+ "? );";

			PreparedStatement ps = Main.db.getDb().prepareStatement(sql);

			ps.setString(1, songName);
			ps.setString(2, ostName);
			ps.setInt(3, dur);
			ps.setString(4, songID); // 4/5 are for the select statements to copy old rating data
			ps.setString(5, songID);
			ps.setString(6, songID); // 6 is the regular song_id insert

			Main.db.execRaw(ps);
		} 

		// Print a little bt about what happened then print the stack trace to stderr
		catch (UnsupportedAudioFileException e) { System.err.println("Bad Audio file format for song " +song.getName());  e.printStackTrace(); } 
		catch (IOException e) { System.out.println("Error reading song file " +song.getName());   e.printStackTrace(); } 
		catch (TagException e) { e.printStackTrace(); } 
		catch (SQLException e) { System.err.println("Error writing to database for song " +song.getName());   e.printStackTrace(); }
	}



}

















