package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.DictionaryManager;
import so101.ircbot.maskbot.Utils;

public class CommandDictionary implements IBotCommand
{

	@Override
	public String getCommandName() 
	{
		return "dictionary";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length == 0)
		{
			sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " dict <add/remove/list> [dictionary] [word]\"");
			return true;
		}
		if (args[0].equals("list"))
		{
			if (args.length >= 2)
			{
				if (!DictionaryManager.dictionaries.containsKey(args[1].toLowerCase()))
				{
					sender.sendToChannel(sender.senderName + ": No words were found for dictionary \"" + args[1] + "\"");
					return true;
				}
				sender.sendToChannel(DictionaryManager.dictionaries.get(args[1].toLowerCase()).size() + " words found: " + Utils.formatArrayToFancyString(DictionaryManager.dictionaries.get(args[1].toLowerCase())));
			}
			else
			{
				sender.sendToChannel(DictionaryManager.dictionaries.size() + " dictionaries avaliable: " + Utils.formatArrayToFancyString(DictionaryManager.dictionaries.keySet()));
			}
		}
		else if (args[0].equals("add"))
		{
			if (args.length >= 3)
			{
				if (DictionaryManager.dictionaries.containsKey(args[1].toLowerCase()))
				{
					DictionaryManager.addToDictionary(args[2].toLowerCase(), args[1].toLowerCase());
					sender.sendToChannel(sender.senderName + ": Word added.");
				}
				else
				{
					DictionaryManager.addToDictionary(args[2].toLowerCase(), args[1].toLowerCase());
					sender.sendToChannel(sender.senderName + ": Dictionary registered and word added.");
				}
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " dict add <dictionary> <word>\"");
			}
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"dict"};
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
