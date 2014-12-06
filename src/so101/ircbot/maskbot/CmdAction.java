package so101.ircbot.maskbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CmdAction 
{
	public List<CommandThread> actions = new ArrayList<CommandThread>();
	
	public boolean runCommand(String command, ChannelSender sender)
	{
		new CommandThread(command, sender);
		return true;
	}
	
	public class CommandThread
	{
		public String command;
		public ChannelSender sender;
		public int id;
		public Thread thread;
		
		public CommandThread(final String command, final ChannelSender sender)
		{
			this.command = command;
			this.sender = sender;
			
			this.thread = new Thread(new Runnable() {

				@Override
				public void run() 
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
					        	return;
							}
					    }
					    return;
					} 
					catch (IOException e1) 
					{
						sender.sendToChannel("ERROR: An error occured building command");
						return;
					}     
				}				
			});
			thread.start();
			actions.add(this);
		}	
	}
}
