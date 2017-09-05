package db;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import org.farng.mp3.MP3File;

import application.Dystrack;

public class PLGenDB 
{
	static String root;
	
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
	
	
	public static void process(String root, boolean overwrite) 
	{
		root = new File(PLGenDB.class.getProtectionDomain().getCodeSource().getLocation().getPath()).getParent();
		reassignSyso();
		initDB();
		
		
		File[] ostDirs = new File(root).listFiles(File::isDirectory);
		for(File ost : ostDirs)
		{
			File[] songs = ost.listFiles(PLGenDB::isLegalExtension);
			for(File f : songs) 
				procSong(ost, f);
		}
	}

	
	


	private static void procSong(File ost, File f) {
		try 
		{
			// Duration info is universal between formats
			AudioFileFormat aff = AudioSystem.getAudioFileFormat(f);
			int dur = (int) (aff.getFrameLength() / aff.getFormat().getFrameRate());
			
			String songName;
			String ostName;
			
			// Only mp3 files have inbuilt name / ost info, so only use internal info for mp3, filesystem info otherwise
			if(f.getPath().substring(f.getPath().lastIndexOf('.')).equalsIgnoreCase(".mp3"))
			{
				MP3File mp3 = new MP3File(f);
				songName = clean(mp3.getID3v2Tag().getSongTitle());
				ostName = clean(mp3.getID3v2Tag().getAlbumTitle());
			} else
			{
				songName = f.getName().substring(0, f.getName().lastIndexOf("."));
				ostName = f.getParentFile().getName();
			}
			
			// relative path of song on disk from the program root directory
			String songID = ost.getName() +"\\"+ f.getName();
			
			// insert into database
			RCTables.playlistTable.verifyExists(Dystrack.db.getDb());
			PreparedStatement ps = Dystrack.db.getDb().prepareStatement("INSERT INTO playlist VALUES(?, ?, ?, ?, ?, ?)");
			ps.setString(1, songName);
			ps.setString(2, ostName);
			ps.setInt(3, dur);
			ps.setDouble(4, 0);
			ps.setInt(5, 0);
			ps.setString(6, songID);
			
			Dystrack.db.execRaw(ps);
			System.out.println("Sucessfully added song " +f.getAbsolutePath());
		} 
		catch (Exception e) { System.out.println("Error reading MP3File " +f.getAbsolutePath()); e.printStackTrace(); }
	}
	
	
	
	
	
	
	public static void initDB()
	{
			RCTables.playlistTable.verifyExists(Dystrack.db.getDb());
			System.out.println("Successfully created database");
	}
	
	
	
	
	public static void reassignSyso()
	{
		try {
			PrintStream ps = new PrintStream(new BufferedOutputStream(new FileOutputStream(root +"/PLGenDB.log", false)), true);
			System.setOut(ps);
			System.setErr(ps);
		} catch (Exception e) { e.printStackTrace(); }
	}
	
	
	
	
	public static String clean(String in)
	{
		in = in.replace("<", "");
		in = in.replace(">", "");
		in = in.replace(":", "");
		in = in.replace("\"", "");
		in = in.replace("/", "");
		in = in.replace("\\", "");
		in = in.replace("|", "");
		in = in.replace("?", "");
		in = in.replace("*", "");
		in = in.replace(",", "");
		in = in.replace("[", "");
		in = in.replace("]", "");
		in = in.replace("{", "");
		in = in.replace("}", "");
		in = in.replace("ÿþ", ""); // this funky thing is there... sometimes. IDK why, but it screws with the path
		in = in.replace(Character.toString('\0'), ""); // Same with NULs. Sometimes there, sometimes not...


		in.trim();

		return in;
	}
	
	
	
	
	/**
	 * checks if the extension on the song file is legal
	 */
	private static boolean isLegalExtension(File song)
	{
		String ext = song.getName().substring(song.getName().lastIndexOf(".")+1);
		
		for(String foo: legalExtensions)
		{
			if(foo.equalsIgnoreCase(ext))
				return true;
		}
		
		return false;
	}

}









