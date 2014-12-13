package so101.ircbot.maskbot.commands;

import java.util.Random;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.RedditManager;

public class Command5050 implements IBotCommand {

	@Override
	public String getCommandName()
	{
		return ".50/50";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length > 0 && args[0].toLowerCase().equals("help"))
		{
			sender.sendToChannel(sender.senderName + ": This command returns a random choice that you can choose to click that will either be one option or the other. WARNING: This command contains adult content and is NOT filtered!");
			return true;
		}
		if (RedditManager.data5050.TITLES.size() <= 0)
		{
			sender.sendToChannel("Sorry, " + sender.senderName + ". It looks like the 50/50 database is empty!");
			return true;
		}
		else
		{
			int r = new Random().nextInt(RedditManager.data5050.TITLES.size());
			sender.sendToChannel(sender.senderName + ": " + RedditManager.data5050.TITLES.get(r) + " - " + RedditManager.data5050.LINKS.get(r));
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {".ff", ".fiftyfifty",".5050"};
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
