package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;

public class ServerIO extends NanoHTTPD
{
	

	public ServerIO(int port) 
	{
		super(port);
	}
	
	
	@Override
	public Response serve(IHTTPSession session)
	{
		Response r = new Response("DB Proc");
		
		// We will only be recieving POSTs
		if(session.getMethod().equals(Method.POST))
		{
			System.out.println("Recieved POST request");
			try 
			{
				session.parseBody(new HashMap<String, String>());
				
				// parse the request
				Map<String, String> params = session.getParms();
			} 
			catch (IOException e) { e.printStackTrace(); } 
			catch (ResponseException e) { e.printStackTrace(); } 

		} 
		else // A bad request was made
		{
			System.err.println("HTTP Request recieved was not POST!");
			
		}
		return r;
	}
	
	
	
}
