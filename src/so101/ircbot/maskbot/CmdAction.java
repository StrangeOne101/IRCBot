package so101.ircbot.maskbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CmdAction 
{
	public static boolean runCommand(String command, ChannelSender sender)
	{
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", command);
	    builder.redirectErrorStream(true);
	    Process p;
	    try 
		{
			p = builder.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    String line;
		    while (true) 
		    {
		    	try 
		        {
		    		line = r.readLine();
					if (line == null) 
					{ 
						break; 
					}
				    sender.sendToChannel(line);
				} 
		        catch (IOException e) 
		        {
		        	sender.sendToChannel("ERROR: An error occured while reading cmd output");
		        	return false;
				}
		    }
		    return true;
		} 
		catch (IOException e1) 
		{
			sender.sendToChannel("ERROR: An error occured building command");
			return false;
		}     
	}
}
