package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.ThreadedProcess;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandQuit implements IBotCommand
{

	@Override
	public String getCommandName() 
	{
		return "quit";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		String commandargs = " " + Utils.formatArrayToString(args);
		
		boolean silent = commandargs.contains(" -s");
		String message = "";
		if (commandargs.contains(" -m "))
		{
			if (IRCBot.getInstance().debugMode)
			{
				sender.sendToChannel("true");
				sender.sendToChannel("\"" + commandargs + "\"");
			}
			int place = -1;
			for (int i = 0; i < args.length; i++)
			{
				if (args[i].equals("-m") && place == -1)
				{
					place = i;
				}
				
				if (place != -1 && i > place)
				{
					message = message + args[i].replaceAll("%s", sender.senderName).replaceAll("%c", sender.channelName) + " ";
				}
			}
		}
		else if (commandargs.contains(" -n"))
		{
			final String s = IRCBot.getNick();
			sender.sendToServer("NICK " + s + "|Offline");
			ThreadedProcess p = new ThreadedProcess() 
			{
				@Override
				public void run() 
				{
					try 
					{
						Thread.sleep(1000L);
					} 
					catch (InterruptedException e) 
					{
					}
					IRCBot.getGlobalVars().remove("NICK");	
					IRCBot.getGlobalVars().put("NICK", s);
				}
			};
			p.start();
		}
		if (!silent && message.equals(""))
		{
			sender.sendToChannel("Laters!");
		}
		else if (!message.equals(""))
		{
			sender.sendToChannel(message);
		}
		
		try 
		{
			if (commandargs.contains(" -n"))
			{
				Thread.sleep(1500L);
			}
		} 
		catch (InterruptedException e) 
		{
		}
		IRCBot.getInstance().closeConnections();
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"exit"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 3;
	}

}
