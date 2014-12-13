package so101.ircbot.maskbot.commands;

import java.util.Random;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.RedditManager;

public class CommandPics implements IBotCommand {

	@Override
	public String getCommandName()
	{
		return ".pic";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length > 0 && args[0].toLowerCase().equals("help"))
		{
			sender.sendToChannel(sender.senderName + ": This command returns a random pic to brighten your day :)");
			return true;
		}
		else if (args.length > 0 && args[0].toLowerCase().equals("fetch"))
		{
			RedditManager.dataPics.fetchData();
			sender.sendToChannel(sender.senderName + ": Data fetched.");
		}
		if (RedditManager.dataPics.TITLES.size() <= 0)
		{
			sender.sendToChannel("Sorry, " + sender.senderName + ". It looks like the pic database is empty! D:");
		}
		else
		{
			int r = new Random().nextInt(RedditManager.dataPics.TITLES.size());
			sender.sendToChannel(sender.senderName + ": " + RedditManager.dataPics.TITLES.get(r) + " - " + RedditManager.dataPics.LINKS.get(r));
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {".pics"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return false;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 0;
	}

}
