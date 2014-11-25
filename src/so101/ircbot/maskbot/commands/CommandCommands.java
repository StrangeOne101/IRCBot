package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.CommandRegistry;
import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class CommandCommands implements IBotCommand 
{

	@Override
	public String getCommandName() 
	{
		return "command";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length > 0)
		{
			if (args[0].equals("chat"))
			{
				if (args.length > 1)
				{
					if (args[1].equals("add"))
					{
						if (args.length > 5)
						{
							String s = Utils.formatArrayToString(args);
							s = s.replaceFirst(args[0] + " ", "");
							s = s.replaceFirst(args[1] + " ", "");
							if (s.contains("-r") || s.contains("-o"))
							{
								CommandRegistry.registerChatCommand(s);
								sender.sendToChannel(sender.senderName + ": Added.");
							}
							else
							{
								sender.sendToChannel(sender.senderName + ": Command must have reply argument.");
							}
						}
						else
						{
							if (args.length < 3)
							{
								sender.sendToChannel(sender.senderName + ": To add chat command, use \"" + IRCBot.getNick() + " commands chat add <command>\"");
								sender.sendToChannel("Command arguments are -e (Equals), -s (StartsWith), -n (EndsWith) and -c (Contains). Use -r to specify reply. Must be present or the chat command won't work.");
							}
							else
							{
								sender.sendToChannel(sender.senderName + ": Looks like you don't have enough arguments. Please try again.");
							}
						}
					}
					else if (args[1].equals("list"))
					{
						int page = 0;
						if (args.length > 2)
						{
							if (Utils.isInteger(args[2]))
							{
								if (Integer.parseInt(args[2]) < 0)
								{
									sender.sendToChannel(sender.senderName + ": Page number has to be positive.");
									return true;
								}
								else if (Integer.parseInt(args[2]) > CommandRegistry.chatCommands.size() / 5)
								{
									sender.sendToChannel(sender.senderName + ": No page found.");
									return true;
								}
								else
								{
									page = Integer.parseInt(args[2]);
								}
							}
							else
							{
								sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands chat list [page]\"");
								return true;
							}
						}
						
						for (int i = page; i < CommandRegistry.chatCommands.size() && i < page + 5; i++)
						{
							sender.sendToChannel("Command #" + i + ": " + CommandRegistry.chatCommands.get(i));
						}
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands chat <add/remove/edit/list>\"");
					}
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands chat <add/remove/edit/list>\"");
				}
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands <chat/basic>\". More will be implemented later.");
			}
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"commands"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 1;
	}

}
