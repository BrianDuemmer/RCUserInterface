package db;

/**
 * Stores key and datatype of an sqlite coulumn
 * @author Duemmer
 *
 */
public class DBCol 
{
	public String key = "";
	public DBType type;
	public boolean notNull = false;
	public boolean unique = false;
	public String defVal = "";
	public boolean autoInc = false;
	
	public DBCol(String key, DBType type, boolean notNull, boolean unique, String defVal, boolean autoInc) 
	{
		this.key = key;
		this.type = type;
		this.notNull = notNull;
		this.unique = unique;
		this.defVal = defVal;
		this.autoInc = autoInc;
	}
	
	
	
	public DBCol(String key, DBType type) 
	{
		this.key = key;
		this.type = type;
	}
	
	
	/**
	 * Formats the column into a string for a CREATE TABLE statement
	 */
	public String fmtColCreate()
	{
		String ret = "";
		ret += key; // key
		ret += " " +type.getValue(); // type 
		ret += notNull  ?  " NOT NULL" : "";
		ret += unique  ?  " UNIQUE" : "";
		ret += autoInc  ?  " AUTO INCREMENT" : "";
		
		
		return ret;
	}
	
	
	@Override
	public String toString() { return fmtColCreate(); }
	
}
