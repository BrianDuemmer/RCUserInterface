package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
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
					ret += columnValue + " " + md.getColumnName(i) + "\n";
				}
				ret += "\n";
			}
		} 

		catch (SQLException e)  { e.printStackTrace(); }
		return ret;
	}
}









