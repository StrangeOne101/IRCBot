package so101.ircbot.maskbot.commands;

import java.util.ArrayList;
import java.util.List;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.commands.dex.PokeEntry;
import so101.ircbot.maskbot.commands.dex.Pokedex;

public class CommandDex implements IBotCommand {

	@Override
	public String getCommandName() 
	{
		return ".dex";
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
					if (args[1].equals("fetch"))
					{
						Pokedex.fetchData(sender);
						sender.sendToChannel(sender.senderName + ": Done.");
					}
					else if (args[1].equals("clear"))
					{
						Pokedex.entriesById.clear();
						Pokedex.entriesByName.clear();
						sender.sendToChannel(sender.senderName + ": Done.");
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Invalid argument");
					}
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": Insufficient arguments");
				}
			}
			else
			{
				//sender.sendToChannel(sender.senderName + ": Error 404 - Not found");
				if (Pokedex.entriesByName.containsKey(args[0].toLowerCase()))
				{
					PokeEntry entry = Pokedex.entriesByName.get(args[0].toLowerCase());
					sender.sendToChannel(this.outputFromPokeEntry(entry));
				}
				else if (args[0].startsWith("#") || args[0].startsWith("0") || args[0].startsWith("1") ||
						args[0].startsWith("2") || args[0].startsWith("3") || args[0].startsWith("4") ||
						args[0].startsWith("5") || args[0].startsWith("6") || args[0].startsWith("7") ||
						args[0].startsWith("8") || args[0].startsWith("9"))
				{
					String arg = args[0];
					if (args[0].startsWith("#"))
					{
						arg = arg.replaceFirst("#", "");
					}
					while (arg.length() < 3)
					{
						arg = "0" + arg;
					}
					
					if (Pokedex.entriesById.containsKey(arg))
					{
						PokeEntry entry = Pokedex.entriesById.get(arg);
						sender.sendToChannel(this.outputFromPokeEntry(entry));
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Pokemon not found");
					}
				}
				else
				{
					List<String> list = new ArrayList<String>();
					String finalString = "";
					for (int i = 0; i < Pokedex.entriesByName.size(); i++)
					{
						if (i % 50 == 0)
						{
							try {Thread.sleep(50L);} catch (InterruptedException e) {}
						}
						
						PokeEntry localEntry = Pokedex.entriesByName.get(((String)Pokedex.entriesByName.keySet().toArray()[i]));
						if (localEntry.name.contains(args[0]))
						{
							list.add(localEntry.name + " (#" + localEntry.id + ")");
						}
					}
					if (list.size() > 0)
					{
						for (int i = list.size(); i > 0; i--)
						{
							if (i == list.size())
							{
								finalString = list.get(i - 1);
							}
							else if (i == list.size() - 1)
							{
								finalString = list.get(i - 1) + " and " + finalString;
							}
							else
							{
								finalString = list.get(i - 1) + ", " + finalString;
							}
						}
						finalString = list.size() + " pokemon found: " + finalString;
						sender.sendToChannel(finalString);
						return true;
					}
					sender.sendToChannel(sender.senderName + ": No results found");
				}
			}
		}
		else
		{
			sender.sendToChannel(sender.senderName + ": Usage is \".dex/.poke <pokemon>\"");
		}
		return true;
	}
	
	protected String outputFromPokeEntry(PokeEntry entry)
	{
		return entry.name + " #" + entry.id + ": A Gen " + entry.gen + " " + entry.types + " type pokemon. URL: "+ entry.url;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {".poke", "pokedex"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return false;
	}

	@Override
	public int getPermissionLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

}
