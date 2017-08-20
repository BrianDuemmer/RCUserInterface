package util;

import java.io.OutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Extends the @link PrintStream class by having it format a date and time before each message
 * @author Duemmer
 *
 */
public class SmartPrintStream extends PrintStream 
{
	public SmartPrintStream(OutputStream out) 
	{ 
		super(out); 
		
	}

	@Override
	public void print(String s)
	{
		SimpleDateFormat sd = new SimpleDateFormat("MM/dd/YY - hh:mm:ss a");
		String head = "[" +sd.format(new Date())+"]	";
		super.print(head + s);
	}
}