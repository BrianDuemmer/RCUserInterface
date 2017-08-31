package rc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import application.Main;
import db.RCTables;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

/**
 * Represents a single song in the forward queue
 * @author Duemmer
 *
 */
public class QueueEntry
{
	// User metrics
	private Viewer vw;
	private long time; // if a history entry, this is the time it was played. If it's a queue entry, it's the time it was requested
	private Rating rating;
	private Song song;
	private int priority;

	private boolean pending = false; //indicates whether this request has played yet



	public QueueEntry(Viewer vw, long time, Rating rating, Song song) 
	{
		this.vw = vw;
		this.time  = time;
		this.rating  = rating;
		this.song = song;
	}



	/**
	 * Reads the entire active queue from the database
	 */
	public static ObservableList<QueueEntry> getForwardQueue()
	{
		List<QueueEntry> entries = new ArrayList<QueueEntry>();

		try 
		{
			// just dump the whole table
			ResultSet rs = Main.db.execRaw("SELECT * FROM " +RCTables.forwardQueueTable.getName()+ ";");

			while(rs.next()) // read each table entry seperately and dump it into the QueueEntry
			{
				String username = rs.getString("username");
				String user_id = rs.getString("user_id");
				String song_name = rs.getString("song_name");
				String ost_name = rs.getString("ost_name");
				String franchise_name = rs.getString("franchise_name");
				long time_requested = rs.getLong("time_requested");
				int length = rs.getInt("length");
				int rating_num = rs.getInt("rating_num");
				double rating_pct = rs.getDouble("rating_pct");

				// format into QE
				QueueEntry e = new QueueEntry(
						new Viewer(user_id, username), 
						time_requested, 
						new Rating(rating_num, rating_pct), 
						new Song(song_name, ost_name, length, franchise_name)
						);

				entries.add(e);
			}
		} catch (Exception e) 
		{
			System.err.println("Error encountered trying to read queue from database!");
			e.printStackTrace();
		}

		return FXCollections.observableArrayList(entries);
	}



	/**
	 * Writes this queueEntry to the forward queue in the database
	 */
	public void writeToDB()
	{
		Task<Void> tsk= new Task<Void>() {

			@Override
			protected Void call() throws Exception 
			{
				// setup insert / field names
				String sql = "INSERT INTO " +RCTables.forwardQueueTable.getName()+ " ("
						+ "username, "
						+ "user_id, "
						+ "song_name, "
						+ "ost_name, "
						+ "franchise_name, "
						+ "rating_pct, "
						+ "rating_num, "
						+ "time_requested, "
						+ "length, "
						+ "song_id) ";
				
				// Add values clause, build prepared statement
				sql += "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				
				try 
				{
					PreparedStatement ps = Main.db.getDb().prepareStatement(sql);
					
					// Add each value
					ps.setString(1, vw.getUsername());
					ps.setString(2, vw.getUserID());
					ps.setString(3, song.getSongName());
					ps.setString(4, song.getOstName());
					ps.setString(5, song.getFranchiseName());
					ps.setDouble(6, rating.getPct());
					ps.setInt(7, rating.getNum());
					ps.setLong(8, time);
					ps.setInt(9, song.getLength());
					ps.setString(10, song.getSongID());
					
					
					Main.db.execRaw(ps); // write the statement down
					System.out.println("Wrote song \"" +song.getOstName()+ " - " +song.getSongName()+ "\" to the queue");
				} catch (SQLException e) 
				{
					System.err.println("Exception encountered trying to write QueueEntry to database");
					e.printStackTrace();
				}
				
				return null;
			}};
			
			Thread t = new Thread(tsk);
			t.setDaemon(true);
			t.setName("writeQueueEntryToDB");
			t.start();
	}





	public Viewer getVw() {
		return vw;
	}



	public void setVw(Viewer vw) {
		this.vw = vw;
	}



	public long getTime() {
		return time;
	}



	public void setTime(long time) {
		this.time = time;
	}



	public Rating getRating() {
		return rating;
	}



	public void setRating(Rating rating) {
		this.rating = rating;
	}



	public Song getSong() {
		return song;
	}



	public void setSong(Song song) {
		this.song = song;
	}



	public boolean isPending() {
		return pending;
	}



	public void setPending(boolean pending) {
		this.pending = pending;
	}



	public Object getDate() 
	{
		return null;
	}



	public int getPriority() {
		return priority;
	}



	public void setPriority(int priority) {
		this.priority = priority;
	}

}
