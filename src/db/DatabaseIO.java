package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteErrorCode;

import application.Main;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.ReqMode;

/**
 * This class performs all of the database IO. Any SQL to execute is 
 * sent here, and results returned
 * @author Duemmer
 *
 */
public class DatabaseIO 
{

	// maximum number of retries when encountering sqlite_busy
	private int busyRetriesMax = 3;






	private Connection db; // reference to the database we are connected to
	private String url; // address of the database
	private boolean connected = false; // specifies if we currently have a valid connection to the database

	/**
	 * @param dbPath the path on the filesystem of the database file
	 */
	public DatabaseIO(String dbPath)
	{
		this.url = "jdbc:sqlite:" +dbPath;
	}




	/**
	 * Checks to make sure we are validly connected to the 
	 * database. If it isn't open already, this will do it
	 */
	public void verifyConnected() throws SQLException
	{
		if(db == null || !db.isValid(3)) // If we are null or the connection is invalid, reestablish the connection
		{
			if(db != null)
				db.close();

			System.out.println("Connecting to database...");
			db = DriverManager.getConnection(url);
			connected = db.isValid(3);

			if(connected)
				System.out.println("Connection to database: \"" +url+ "\" has been established");
			else
			{
				System.err.println("Failed to establish connection to database \"" +url+ "\"");
				throw new SQLException("Connection invalid");
			}
		}
	}





	/**
	 * Executes sql on the database. This will automatically verify that the connection to the
	 * database is valid. Will also commit the results.
	 * @param sql the sql statement to execute
	 * @return the @link ResultSet of the query, or null if the reference to the database is invalid
	 */
	public synchronized ResultSet execRaw(String sql) throws SQLException
	{
		ResultSet res = null;
		Statement st;
		boolean isBusy = false;
		int busyRetries = 0;

		// Attempt to connect. Print an error and return an empty set on failure
		try { verifyConnected(); } 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return res;
		}

		// Prepare and execute the query, and continue and retry if necessary
		do {
			try  {
				st = db.createStatement();
				if (st.execute(sql)) // execute the query, and if it gave us a result set, grab it
					res = st.getResultSet();

				break; // If we get this far the statement executed correctly, so we can just break out of the loop
			} catch (SQLException e) 
			{
				isBusy = e.getErrorCode() == SQLiteErrorCode.SQLITE_BUSY.code;
				if(isBusy && busyRetries <= busyRetriesMax) // If it's busy, and hasn't retried too much, make a special note, wait a little bit for everything to clear, and try again
				{
					busyRetries++;
					System.err.println("WARNING: read query \"" +sql+ "\" encountered SQLITE_BUSY. Retryig in 250ms...");
					try { Thread.sleep(250); } catch (InterruptedException e1) { e1.printStackTrace(); }
				} else if (isBusy && busyRetries > busyRetriesMax) // we retried too many times. This is likely an indicator of a larger issue if this runs...
				{
					System.err.println("FATAL: read query \"" +sql+ "\" encountered SQLITE_BUSY too many times. Cancelling...");
					throw e; //propagate up the error in the event it gives a little more info
				}

				else // something other than a busy happened - let the caller handle it
					throw e;
			}
		} while(isBusy);
		return res;
	}






	/**
	 * Executes the preparedStatement on the database. This will automatically verify that the connection to the
	 * database is valid. Will also commit the results.<br><br>
	 * 
	 * <B> NOTE: </B> These methods are thread safe for writes. Regular statements ARE NOT!
	 * 
	 * @param sql the preparedStatement to execute
	 * @return the @link ResultSet of the query, or null if the reference to the database is invalid
	 */
	public synchronized ResultSet execRaw(PreparedStatement ps) throws SQLException
	{
		ResultSet res = null;
		boolean isBusy = false;
		int busyRetries = 0;

		// Attempt to connect. Print an error and return an empty set on failure
		try { verifyConnected(); } 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return res;
		}

		// Prepare and execute the query, and continue and retry if necessary
		do {
			try  {
				if (ps.execute()) // execute the query, and if it gave us a result set, grab it
					res = ps.getResultSet();

				break; // If we get this far the statement executed correctly, so we can just break out of the loop
			} catch (SQLException e) 
			{
				isBusy = e.getErrorCode() == SQLiteErrorCode.SQLITE_BUSY.code;
				if(isBusy && busyRetries <= busyRetriesMax) // If it's busy, and hasn't retried too much, make a special note, wait a little bit for everything to clear, and try again
				{
					busyRetries++;
					System.err.println("WARNING: PreparedStatement encountered SQLITE_BUSY. Retryig in 250ms...");
					try { Thread.sleep(250); } catch (InterruptedException e1) { e1.printStackTrace(); }
				} else if (isBusy && busyRetries > busyRetriesMax) // we retried too many times. This is likely an indicator of a larger issue if this runs...
				{
					System.err.println("FATAL: PreparedStatement encountered SQLITE_BUSY too many times. Cancelling...");
					throw e; //propagate up the error in the event it gives a little more info
				}

				else // something other than a busy happened - let the caller handle it
					throw e;
			}
		} while(isBusy);
		return res;
	}








	/**
	 * Converts a @link ResultSet to a string
	 */
	public String resultsToString(ResultSet rs)
	{
		String ret = "";
		try 
		{
			ResultSetMetaData md = rs.getMetaData();
			int colNum = md.getColumnCount();
			while(rs.next())
			{
				for (int i = 1; i <= colNum; i++) {
					if (i > 1) 
						ret += ", ";
					String columnValue = rs.getString(i);
					ret += md.getColumnName(i) + " "+ columnValue + "\n";
				}
				ret += "\n";
			}
		} 

		catch (SQLException e)  { e.printStackTrace(); }
		return ret;
	}




	/**
	 * Reads a Request controller parameter from the database
	 * @param key
	 * @return the value of the key (cast to an appropriate type) or null
	 * if the key wasn't found
	 */
	private String readParameter(String key)
	{		
		String val = ""; // Will return empty string on failure

		// Use a prepared Statement for added security
		String sql = "SELECT value2 FROM " +RCTables.paramTable.getName()+ " WHERE setting = ?";
		ResultSet rs;
		try
		{
			// Be sure we are connected and that the table exists before proceeding
			verifyConnected();
			RCTables.paramTable.verifyExists(db);

			PreparedStatement ps = db.prepareStatement(sql);
			ps.setString(1, key);
			rs = execRaw(ps);

			if(rs == null) // gets returned if the statement was an update, not a read
				System.err.println("Null ResultSet returned by readParameter() on param " +key+ ". This is likely due to a faulty query!");

			if(!rs.next()) // if empty, print a warning and return empty string
			{
				System.err.println("WARNING: Could not find parameter key " +key+ ". Creating key with empty value...");
				setParameter(key, "");
			} 
			// If this is hit then there is at least 1 result
			else { val = rs.getString(1); }

		} catch (SQLException e) 
		{ 
			System.err.println("ERROR attempting to read parameter key " +key);
			e.printStackTrace(); 
		}

		return val;
	}



	/**
	 * Reads a boolean parameter from the database
	 * @param key the name of the parameter
	 * @return the value saved in the database
	 */
	public boolean readBoolParam(String key) { return new Boolean(readParameter(key)); }




	/**
	 * Reads a real (double) parameter from the database
	 * @param key the name of the parameter
	 * @return the value saved in the database, or 0 on an error
	 */
	public double readRealParam(String key) 
	{ 
		try { return new Double(readParameter(key)); }
		catch(Exception e) { System.err.println("Failed to parse Real for key \"" +key+ "\""); e.printStackTrace();}
		return 0;
	}





	/**
	 * Reads a Integer parameter from the database
	 * @param key the name of the parameter
	 * @return the value saved in the database, or 0 on an error
	 */
	public int readIntegerParam(String key) 
	{ 
		try { return new Integer(readParameter(key)); }
		catch(Exception e) { System.err.println("Failed to parse Integer for key \"" +key+ "\""); e.printStackTrace();}
		return 0;
	}





	/**
	 * Reads a String parameter from the database
	 * @param key the name of the parameter
	 * @return the value saved in the database, or 0 on an error
	 */
	public String readStringParam(String key) { return readParameter(key);}


	/** Writes a real parameter to the database 
	 * @throws SQLException */
	public boolean setParameter(String key, double val) throws SQLException { return setParameter(key, String.valueOf(val)); }

	/** Writes a boolean parameter to the database 
	 * @throws SQLException */
	public boolean setParameter(String key, boolean val) throws SQLException { return setParameter(key, String.valueOf(val)); }

	/** Writes an integer parameter to the database 
	 * @throws SQLException */
	public boolean setParameter(String key, int val) throws SQLException { return setParameter(key, String.valueOf(val)); }


	/**
	 * Writes the specified key value pair to the database
	 * @return true if the parameter was created, false otherwise
	 * @throws SQLException 
	 */
	public boolean setParameter(String key, String value) throws SQLException
	{
		String sql1 = "DELETE FROM " +RCTables.paramTable.getName()+ " WHERE setting=?";
		String sql2 = "INSERT OR REPLACE INTO " +RCTables.paramTable.getName()+ " VALUES( ?, ?, ?)";

		try
		{
			//verifyTableExists(paramTable, paramTableCols);
			verifyConnected();

			PreparedStatement ps1 = db.prepareStatement(sql1);
			ps1.setString(1, key);
			execRaw(ps1);
			
			PreparedStatement ps2 = db.prepareStatement(sql2);
			ps2.setString(1, key);
			ps2.setLong(2, 0);
			ps2.setString(3, value);
			execRaw(ps2);

			// if we reached this point, then the parameter was created and the values should be in the table
			return true;
		} 	
		catch (SQLException e) // if this exception is thrown, that means the key already exists, so update instead
		{ 
			// if there's a timeout exception, let it propagate further to prevent locking up the database routines
			if(e.getErrorCode() == SQLiteErrorCode.SQLITE_BUSY.code)
				throw e;
			
			else
			{
				System.err.println("Exception encountered in SetParameter()!");
				e.printStackTrace();
			}
		} 

		// If it didn't hit the above return statement, then the key wasn't newly created
		return false;
	}
	
	
	
	
	
	/**
	 * Writes the ReqMode to the database. This will run in a background thread.
	 */
	public void writeRequestModeToDB(ReqMode mode) {
		Thread t = new Thread(() -> {
			try { Main.db.setParameter("requestMode", mode.toString()); } 
			catch (Exception e) 
			{
				Platform.runLater(() -> { new Alert(AlertType.ERROR, "Failed to write parameter \"requestMode\" to database!").show(); });
				e.printStackTrace();
			}
		});
		t.setDaemon(true);
		t.setName("writeRequestMode");
		t.start();
	}
	
	




	public Connection getDb() {
		return db;
	}





}









