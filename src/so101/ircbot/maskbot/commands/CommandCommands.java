package so101.ircbot.maskbot.commands;

import java.util.ArrayList;
import java.util.List;

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
					if (args[1].toLowerCase().equals("add"))
					{
						if (args.length > 5)
						{
							String s = Utils.formatArrayToString(args);
							s = s.replaceFirst(args[0] + " ", "");
							s = s.replaceFirst(args[1] + " ", "");
							if (this.canRegisterCommand(s, sender))
							{
								CommandRegistry.registerChatCommand(s);
								sender.sendToChannel(sender.senderName + ": Command added with ID " + (CommandRegistry.chatCommands.size() - 1));
							}
						}
						else
						{
							if (args.length < 3)
							{
								sender.sendToChannel(sender.senderName + ": To add chat command, use \"" + IRCBot.getNick() + " commands chat add <command>\"");
								sender.sendToChannel("Command arguments are -e (Equals), -s (StartsWith), -n (EndsWith) and -c (Contains). Use -r to specify reply. Must be present or command will be invalid.");
							}
							else
							{
								sender.sendToChannel(sender.senderName + ": Looks like you don't have enough arguments. Please try again. Be sure to include at least one detecting argument (-c, -s, -n or -e) and a reply argument (-r)");
							}
						}
					}
					else if (args[1].toLowerCase().equals("list"))
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
					else if (args[1].toLowerCase().equals("edit"))
					{
						if (args.length > 2)
						{
							if (Utils.isInteger(args[2]))
							{
								if (Integer.parseInt(args[2]) < 0)
								{
									sender.sendToChannel(sender.senderName + ": Command ID has to be positive.");
									return true;
								}
								else if (Integer.parseInt(args[2]) >= CommandRegistry.chatCommands.size())
								{
									sender.sendToChannel(sender.senderName + ": No command found with ID " + args[2]);
									return true;
								}
								else
								{
									if (args.length < 6)
									{
										sender.sendToChannel(sender.senderName + ": Looks like you don't have enough arguments. Please try again. Be sure to include at least one detecting argument (-c, -s, -n or -e) and a reply argument (-r)");
										return true;
									}
									String s = Utils.formatArrayToString(args);
									s = s.replaceFirst(args[0] + " ", "");
									s = s.replaceFirst(args[1] + " ", "");
									s = s.replaceFirst(args[2] + " ", "");
									if (this.canRegisterCommand(s, sender))
									{
										List<String> l = new ArrayList<String>();
										
										for (int i = 0; i < CommandRegistry.chatCommands.size(); i++)
										{
											if (i != Integer.parseInt(args[2]))
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
										sender.sendToChannel(sender.senderName + ": Command successfully edited for command of ID " + args[2]);
									}									
									return true;
								}
							}
							else
							{
								sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands chat edit <id> <new command>\"");
								return true;
							}
						}
						else
						{
							sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands chat edit <id> <new command>\"");
						}
					}
					else if (args[1].toLowerCase().equals("remove"))
					{
						if (args.length > 2)
						{
							if (Utils.isInteger(args[2]))
							{
								if (Integer.parseInt(args[2]) < 0)
								{
									sender.sendToChannel(sender.senderName + ": Command ID has to be positive.");
									return true;
								}
								else if (Integer.parseInt(args[2]) >= CommandRegistry.chatCommands.size())
								{
									sender.sendToChannel(sender.senderName + ": No command found with ID " + args[2]);
									return true;
								}
								else
								{
									CommandRegistry.chatCommands.remove(Integer.parseInt(args[2]));
									sender.sendToChannel(sender.senderName + ": Command successfully removed with ID " + args[2]);
									return true;
								}
							}
							else
							{
								sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands chat remove <id> [reason]\"");
								return true;
							}
						}
						else
						{
							sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " commands chat remove <id> [reason]\"");
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
