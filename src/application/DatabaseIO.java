package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

/**
 * This class performs all of the database IO. Any SQL to execute is 
 * sent here, and results returned
 * @author Duemmer
 *
 */
public class DatabaseIO 
{
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
	 * database
	 * @throws @likk SQLException if it is unable to connect
	 */
	public void verifyConnected() throws SQLException
	{
		if(db == null || !db.isValid(3)) // If we are null or the connection is invalid, reestablish the connection
		{
			if(db != null)
				db.close();

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
	public ResultSet execRawQuery(String sql)
	{
		ResultSet res = null;

		// Attempt to connect. Print an error and return an empty set on failure
		try { verifyConnected(); } 
		catch (SQLException e) 
		{
			e.printStackTrace();
			return res;
		}

		// Prepare and execute the query
		try 
		{
			Statement st = db.createStatement();
			res = st.executeQuery(sql);
		} catch (Exception e) 
		{
			System.err.println("Error occured attempting to execute query \"" +sql+ "\" on the database!");
			e.printStackTrace();
		}

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
		String sql = "SELECT val FROM request_controller_params WHERE name = ?";
		ResultSet rs;
		try
		{
			// Be sure we are connected before proceeding
			verifyConnected();
			
			PreparedStatement ps = db.prepareStatement(sql);
			ps.setString(1, key);
			rs = ps.executeQuery();
			
			if(!rs.next()) // if empty, print a warning and return empty string
			{
				System.err.println("WARNING: Could not find parameter key " +key+ ". Creating key with empty value...");
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


	/** Writes a real parameter to the database */
	public boolean setParameter(String key, double val) { return setParameter(key, String.valueOf(val)); }
	
	/** Writes a boolean parameter to the database */
	public boolean setParameter(String key, boolean val) { return setParameter(key, String.valueOf(val)); }
	
	/** Writes an integer parameter to the database */
	public boolean setParameter(String key, int val) { return setParameter(key, String.valueOf(val)); }


	/**
	 * Writes the specified key value pair to the database
	 * @return true if the parameter was created, false otherwise
	 */
	public boolean setParameter(String key, String value)
	{
		String sql = "INSERT INTO request_controller_params VALUES( ?, ?)";

		try
		{
			PreparedStatement ps = db.prepareStatement(sql);
			ps.setString(1, key);
			ps.setString(2, value);
			ps.execute();

			// if we reached this point, then the parameter was created and the values should be in the table
			return true;
		} 	
		catch (SQLException e) // if this exception is thrown, that means the key already exists, so update instead
		{ 
			try 
			{
				String updateSQL = "UPDATE request_controller_params SET val = ? WHERE name = ?";
				PreparedStatement updatePS = db.prepareStatement(updateSQL);
				updatePS.setString(1, value);
				updatePS.setString(2, key);
				updatePS.execute();
			} catch( SQLException j)
			{
				System.err.println("Error occured attempting to update key " +key);
				j.printStackTrace();
			}
		} 
		
		// If it didn't hit the above return statement, then the key wasn't newly created
		return false;
	}





}









