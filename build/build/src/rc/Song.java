package rc;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.Dystrack;
import db.RCTables;

public class Song 
{
	private String songName = "";
	private String ostName = "";
	private String franchiseName = "";
	private String songID = "";
	private double length = -1;

	public Song(String songName, String ostName) 
	{
		this.songName = songName;
		this.ostName = ostName;

		this.songID = getSongID();
		this.length = 0;
	}



	/**
	 * Constructs a Song based on it's Song ID, by checking it against
	 * the playlist table. If no matches are found (case insensitive), 
	 * all of the attributes are set to empty string and an error is printed
	 */
	public Song(String songID)
	{
		this.songID = songID;

		String sql = "SELECT song_name, ost_name, song_length FROM " +RCTables.playlistTable.getName()+ " WHERE song_id=? COLLATE NOCASE;";
		ResultSet rs;

		try {
			RCTables.playlistTable.verifyExists(Dystrack.db.getDb());

			PreparedStatement ps = Dystrack.db.getDb().prepareStatement(sql);
			ps.setString(1, songID);
			rs = Dystrack.db.execRaw(ps);

			if(rs.next()) // only proceed if there is a record
			{
				this.songName = rs.getString("song_name");
				this.ostName = rs.getString("ost_name");
				this.length = rs.getDouble("song_length");
			} else { System.err.println("Failed to find songID " +songID+ " in playlist table. Try running a playlist regen!"); }

			rs.close();
		} catch (SQLException e) 
		{
			System.err.println("Failed to construct song from song_id");
			e.printStackTrace();
		}
	}



	public Song(String songName, String ostName, double length, String franchiseName) 
	{
		this.songName = songName;
		this.ostName = ostName;
		this.length = length;
		this.franchiseName = franchiseName;

		this.songID = getSongID();
	}



	public String getSongName() {
		return songName;
	}

	public void setSongName(String songName) {
		this.songName = songName;
	}

	public String getOstName() {
		return ostName;
	}

	public void setOstName(String ostName) {
		this.ostName = ostName;
	}

	public String getFranchiseName() {
		return franchiseName;
	}

	public void setFranchiseName(String franchiseName) {
		this.franchiseName = franchiseName;
	}




	/**
	 * Fetches the length, either one that's stored, or if one isn't stored in here, fetch it from the database
	 */
	public double getLength() 
	{
		if(songID.isEmpty())
			getSongID();

		if(this.length == 0)
		{
			// Extract the songID from the playlist table
			RCTables.playlistTable.verifyExists(Dystrack.db.getDb());
			String sql = "SELECT song_length FROM " +RCTables.playlistTable.getName()+ " WHERE song_id = ?  COLLATE NOCASE;";
			ResultSet rs;

			try 
			{
				PreparedStatement ps = Dystrack.db.getDb().prepareStatement(sql);
				ps.setString(1, songID);

				rs = Dystrack.db.execRaw(ps);

				if(rs.next())
				{
					this.length = rs.getDouble(1);
				} 
				else { System.err.println("Could not find length given songID " +songID); }
				
				rs.close();
			} catch (SQLException e) 
			{
				System.err.println("Could not find length given songID " +songID);
				e.printStackTrace();
			}
		}

		return length;
	}
	
	
	

	public void setLength(double length) {
		this.length = length;
	}



	/**
	 * Fetches the songID, either one that's stored, or if one isn't stored in here, 
	 */
	public String getSongID() 
	{
		if(songID.isEmpty())
		{
			// Extract the songID from the playlist table
			RCTables.playlistTable.verifyExists(Dystrack.db.getDb());
			String sql = "SELECT song_id FROM " +RCTables.playlistTable.getName()+ " WHERE song_name = ? COLLATE NOCASE AND ost_name = ? COLLATE NOCASE;";
			ResultSet rs;

			try 
			{
				PreparedStatement ps = Dystrack.db.getDb().prepareStatement(sql);
				ps.setString(1, songName);
				ps.setString(2, ostName);

				rs = Dystrack.db.execRaw(ps);

				if(rs.next())
				{
					this.songID = rs.getString("song_id");
				} 
				else { System.err.println("Could not find Song_id given name " +songName+ " and OST " +ostName); }
				
				rs.close();
			} catch (SQLException e) 
			{
				System.err.println("Could not find Song_id given name " +songName+ " and OST " +ostName);
				e.printStackTrace();
			}
		}

		return songID;
	}
	
	
	
	
	/**
	 * Does a simple random selection from the playlist to get a 
	 * random song
	 */
	public static Song getUniformRandom()
	{
		RCTables.playlistTable.verifyExists(Dystrack.db.getDb());
		String sql = "SELECT song_id FROM " +RCTables.playlistTable.getName()+ " ORDER BY RANDOM() LIMIT 1";
		Song s = null;
		
		try {
			ResultSet rs = Dystrack.db.execRaw(sql);
			rs.next();
			s= new Song(rs.getString(1));
			
			rs.close();
		} catch (SQLException e) {
			System.err.println("RNGsus failed to take the wheel!");
			e.printStackTrace();
		}
		
		return s;
	}



	/**
	 * @param songID the songID to set
	 */
	public void setSongID(String songID) {
		this.songID = songID;
	}

}
