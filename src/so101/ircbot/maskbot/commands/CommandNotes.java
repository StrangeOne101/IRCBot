package so101.ircbot.maskbot.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.jsoup.helper.StringUtil;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class CommandNotes implements IBotCommand
{
	public static Map<String, List<String>> notes = new HashMap<String, List<String>>();
	
	
	@Override
	public String getCommandName() 
	{
		return "notes";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length < 1)
		{
			sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " notes <add/remove> <note>\" or \"" + IRCBot.getNick() + " notes <[keyword]/[number]/random>\" or \"" + IRCBot.getNick() + " notes <user> [note]\"");
		}
		if (args[0].toLowerCase().equals("add"))
		{
			if (args.length < 2)
			{
				sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " notes add <note>\"");
			}
			else
			{
				if (!notes.containsKey(sender.senderName.toLowerCase()))
				{
					notes.put(sender.senderName.toLowerCase(), new ArrayList<String>());
				}
				String s = Utils.formatArrayToString(args).replaceFirst(args[0] + " ", "");
				notes.get(sender.senderName.toLowerCase()).add(s);
				sender.sendToChannel(sender.senderName + ": Note added.");
			}
		}
		else if (args[0].toLowerCase().equals("random") || args[0].toLowerCase().equals("rand"))
		{
			if (!notes.containsKey(sender.senderName.toLowerCase()))
			{
				sender.sendToChannel(sender.senderName + ": No notes found!");
			}
			else
			{
				int j = notes.get(sender.senderName.toLowerCase()).size();
				int i = new Random().nextInt(j);
				sender.sendToChannel(sender.senderName + ": " + notes.get(sender.senderName.toLowerCase()).get(i) + " (Note " + (i + 1) + "/" + j + ")");
			}
		}
		else if (args[0].toLowerCase().equals("remove"))
		{
			if (args.length < 2)
			{
				sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " notes remove <notenumber>\"");
			}
			else if (Utils.isInteger(args[1]))
			{
				if (!notes.containsKey(sender.senderName.toLowerCase()))
				{
					sender.sendToChannel(sender.senderName + ": No note found to remove!");
				}
				else
				{
					int i = Integer.parseInt(args[1]);
					if (i < 0 || i >= notes.get(sender.senderName.toLowerCase()).size())
					{
						sender.sendToChannel(sender.senderName + ": No note found to remove!");
					}
					else
					{
						notes.get(sender.senderName.toLowerCase()).remove(i);
						sender.sendToChannel(sender.senderName + ": Note removed.");
					}
				}
				
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Note must be a number!");
			}
		}
		else if (Utils.isInteger(args[0]))
		{
			int i = Integer.parseInt(args[0]);
			int j = notes.get(sender.senderName.toLowerCase()).size();
			if (!notes.containsKey(sender.senderName.toLowerCase()))
			{
				sender.sendToChannel(sender.senderName + ": No notes found!");
			}
			else if (i < 0 || i >= j)
			{
				sender.sendToChannel(sender.senderName + ": No note found!");
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": " + notes.get(sender.senderName.toLowerCase()).get(i) + " (Note " + (i + 1) + "/" + j + ")");
			}
		}
		else if (notes.containsKey(args[0].toLowerCase()) && ((args.length < 2 || Utils.isInteger(args[1]))))
		{
			int j = notes.get(args[0].toLowerCase()).size();
			int i = args.length >= 2 ? Integer.parseInt(args[1]) : new Random().nextInt(j);
			if (i < 0 || i >= j)
			{
				sender.sendToChannel(sender.senderName + ": No note found!");
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": " + notes.get(args[0].toLowerCase()).get(i) + " (Note " + (i + 1) + "/" + j + ")");
			}
		}
		else
		{
			if (!notes.containsKey(sender.senderName.toLowerCase()))
			{
				sender.sendToChannel(sender.senderName + ": No notes found!");
			}
			boolean flag = false;
			for (int i = 0; i < notes.get(sender.senderName.toLowerCase()).size(); i++)
			{
				if (notes.get(sender.senderName.toLowerCase()).get(i).toLowerCase().contains(Utils.formatArrayToString(args).toLowerCase()))
				{
					sender.sendToChannel(sender.senderName + ": " + notes.get(sender.senderName.toLowerCase()).get(i) + " (Note " + (i + 1) + "/" + notes.get(sender.senderName.toLowerCase()).size() + ")");
					flag = true;
					break;
				}
			}
			if (!flag)
			{
				sender.sendToChannel(sender.senderName + ": No notes found!");
			}
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
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
		return 0;
	}

}
