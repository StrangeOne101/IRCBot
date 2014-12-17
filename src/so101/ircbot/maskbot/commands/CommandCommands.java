package so101.ircbot.maskbot.commands;

import java.util.ArrayList;
import java.util.List;

import so101.ircbot.maskbot.ChatColors;
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
			if (args[0].toLowerCase().equals("add"))
			{
				if (args.length > 4)
				{
					String s = Utils.formatArrayToString(args);
					s = s.replaceFirst(args[0] + " ", "");
					
					if (this.canRegisterCommand(s, sender))
					{
						CommandRegistry.registerChatCommand(s);
						sender.sendToChannel(sender.senderName + ": Command added with ID " + (CommandRegistry.chatCommands.size() - 1));
					}
				}
				else
				{
					if (args.length < 2)
					{
						sender.sendToChannel(sender.senderName + ": To add chat command, use \"" + IRCBot.getNick() + " commands add <command>\". For in depth command help, use \"" + IRCBot.getNick() + " commands help [arguments/options/delay/color]\"");
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Looks like you don't have enough arguments. Please try again. Be sure to include at least one detecting argument (-c, -s, -n or -e) and a reply argument (-r)");
					}
				}
			}
			else if (args[0].toLowerCase().equals("list"))
			{
				int page = 0;
				if (args.length > 1)
				{
					if (Utils.isInteger(args[1]))
					{
						if (Integer.parseInt(args[1]) < 0)
						{
							sender.sendToChannel(sender.senderName + ": Page number has to be positive.");
							return true;
						}
						else if (Integer.parseInt(args[1]) + 1 > Math.ceil(((double)CommandRegistry.chatCommands.size() / 5)))
						{
							sender.sendToChannel(sender.senderName + ": No page found.");
							return true;
						}
						else
						{
							page = Integer.parseInt(args[1]);
						}
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands list [page]\"");
						return true;
					}
				}
						
				for (int i = page * 5; i < CommandRegistry.chatCommands.size() && i < page * 5 + 5; i++)
				{
					sender.sendToChannel("Command #" + i + ": " + CommandRegistry.chatCommands.get(i));
				}
			}
			else if (args[0].toLowerCase().equals("edit"))
			{
				if (args.length > 1)
				{
					if (Utils.isInteger(args[1]))
					{
						if (Integer.parseInt(args[1]) < 0)
						{
							sender.sendToChannel(sender.senderName + ": Command ID has to be positive.");
							return true;
						}
						else if (Integer.parseInt(args[1]) >= CommandRegistry.chatCommands.size())
						{
							sender.sendToChannel(sender.senderName + ": No command found with ID " + args[1]);
							return true;
						}
						else
						{
							if (args.length < 5)
							{
								sender.sendToChannel(sender.senderName + ": Looks like you don't have enough arguments. Please try again. Be sure to include at least one detecting argument (-c, -s, -n or -e) and a reply argument (-r)");
								return true;
							}
							String s = Utils.formatArrayToString(args);
							s = s.replaceFirst(args[0] + " ", "");
							s = s.replaceFirst(args[1] + " ", "");
							//s = s.replaceFirst(args[2] + " ", "");
							if (this.canRegisterCommand(s, sender))
							{
								List<String> l = new ArrayList<String>();
										
								for (int i = 0; i < CommandRegistry.chatCommands.size(); i++)
								{
									if (i != Integer.parseInt(args[1]))
									{
										l.add(CommandRegistry.chatCommands.get(i));
									}
									else
									{
										l.add(s);
									}
								}
								//CommandRegistry.chatCommands.remove(Integer.parseInt(args[2]));
								CommandRegistry.chatCommands = l;
								sender.sendToChannel(sender.senderName + ": Command successfully edited for command of ID " + args[1]);
							}									
							return true;
						}
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands edit <id> <new command>\"");
						return true;
					}
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands edit <id> <new command>\"");
				}
			}
			else if (args[0].toLowerCase().equals("remove"))
			{
				if (args.length > 1)
				{
					if (Utils.isInteger(args[1]))
					{
						if (Integer.parseInt(args[1]) < 0)
						{
							sender.sendToChannel(sender.senderName + ": Command ID has to be positive.");
							return true;
						}
						else if (Integer.parseInt(args[1]) >= CommandRegistry.chatCommands.size())
						{
							sender.sendToChannel(sender.senderName + ": No command found with ID " + args[1]);
							return true;
						}
						else
						{
							CommandRegistry.chatCommands.remove(Integer.parseInt(args[1]));
							sender.sendToChannel(sender.senderName + ": Command successfully removed with ID " + args[1]);
							return true;
						}
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands remove <id> [reason]\"");
						return true;
					}
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands remove <id> [reason]\"");
				}
			}
			else if (args[0].toLowerCase().equals("help"))
			{
				if (args.length > 1)
				{
					if (args[1].toLowerCase().equals("options"))
					{
						sender.sendToChannel(sender.senderName + ": All options are either True or False. The currently implemented options are CHANNEL and PREFIX. If channel is true, command will only be triggered on a channel. If false, command has to be run in PM. Will work for both if not specified. If prefix is true, user has to supply the nick prefix for the command. Prefix set to false will not do anything.");
					}
					else if (args[1].toLowerCase().equals("arguments") || args[1].toLowerCase().equals("args"))
					{
						sender.sendToChannel(sender.senderName + ": Current pre-command arguments are: -e (Equals), -s (StartsWith), -n (EndsWith) or -c (Contains). Current post-command arguments are -r (Reply), -o (Options) and -d (Delay). At least one pre-command argument and reply argument are needed for the command to be valid.");
					}
					else if (args[1].toLowerCase().equals("delay"))
					{
						sender.sendToChannel(sender.senderName + ": Delay can be specified with a -d argument. Delay is in seconds and will wait that many seconds before replying next reply.");
						sender.sendToChannel("Example: \"-s %NICK -c count -r THREE -d 1 -r TWO -d 1 -r ONE -d 1 -r ZERO! :D\". When the user uses a \"count\" command, it will say three, wait a second, say two, wait a second, say one, etc.");
					}
					else if (args[1].toLowerCase().equals("colors"))
					{
						sender.sendToChannel(sender.senderName + ": Colors can be specified in commands by using a & symbol followed by the color. Current valid colors are RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE, BLACK, GREY, DARKRED, DARKGREEN, DARKBLUE, DARKPURPLE.");
						sender.sendToChannel("Current valid colors are RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE, BLACK, GREY, DARKRED, DARKGREEN, DARKBLUE, DARKPURPLE. Bold text can also be specified by using &BOLD.");
						sender.sendToChannel("An example of color use might be: \"" + IRCBot.getNick() + " say &RED&BOLD!! ALERT !!\" which would output " + ChatColors.BOLD.s + ChatColors.RED.s + "!! ALERT !!");
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands help <arguments/options/delay/colors>\"");
					}
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": To add chat command, use \"" + IRCBot.getNick() + " commands add <command>\".");
					sender.sendToChannel("Command arguments are -e (Equals), -s (StartsWith), -n (EndsWith) and -c (Contains). Use -r to specify reply. Must be present or command will be invalid.");
					sender.sendToChannel("Optional reply arguments are -d (Delay in seconds) and -o (Options). Current valid options are Channel (T/F) and Prefix (T/F).");
				}
			}
			else if (args[0].toLowerCase().equals("chat"))
			{
				sender.sendToChannel(sender.senderName + ": Command has been updated. Just use \"" + IRCBot.getNick() + " commands <add/remove/edit/list/help>\" now without the \"chat\" argument.");
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands <add/remove/edit/list/help>\"");
			}	
		}
		else
		{
			sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands <add/remove/edit/list/help>\"");
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
	
	public boolean canRegisterCommand(String command, ChannelSender sender)
	{
		if ((command.contains("-r") || command.contains("-o")) && (command.contains("-c") || command.contains("-s") || command.contains("-n") || command.contains("-e")))
		{
			return true;
		}
		else if (!(command.contains("-r") && command.contains("-o")))
		{
			sender.sendToChannel(sender.senderName + ": Command must have reply argument. Specify a -r argument and try again.");
		}
		else
		{
			sender.sendToChannel(sender.senderName + ": Command must have at least one detecting argument. Please specify at least one -c, -s, -n or -e argument and try again.");
		}
		return false;
	}

}
