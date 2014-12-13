package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.PermissionsManager;

public class CommandDebug implements IBotCommand
{

	@Override
	public String getCommandName() 
	{
		return "debug";
	}	

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length > 0)
		{
			if (args[0].equals("-o"))
			{
				if (args.length > 1)
				{
					if (args[1].toLowerCase().equals("crash"))
					{
						if (!PermissionsManager.getUserHasPermission(sender, 4))
						{
							return true;
						}
						sender.sendToChannel(sender.senderName + ": Attempting forced crash with arrayoutofbounds exception");
						Integer.parseInt(args[999]);
						
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Invalid optional arguments");
					}
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": Invalid optional arguments");
				}
			}
			else if (args[0].toLowerCase().equals("true") || args[0].toLowerCase().equals("1"))
			{
				IRCBot.getInstance().debugMode = true;
				sender.sendToChannel(sender.senderName + ": TRUE");
			}
			else if (args[0].toLowerCase().equals("false") || args[0].toLowerCase().equals("0"))
			{
				IRCBot.getInstance().debugMode = false;
				sender.sendToChannel(sender.senderName + ": FALSE");
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Usage is \"" + IRCBot.getNick() + " debug [true/false]\"");
			}
		}
		else
		{
			sender.sendToChannel(sender.senderName + ": Debug mode is at " + String.valueOf(IRCBot.getInstance().debugMode).toUpperCase());
		}
		return true;
	}

	@Override
	public String[] getAliasis() {
		// TODO Auto-generated method stub
		return null;
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
