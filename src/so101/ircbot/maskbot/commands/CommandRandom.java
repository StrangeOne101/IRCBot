package so101.ircbot.maskbot.commands;

import java.util.Random;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.PermissionsManager;
import so101.ircbot.maskbot.registries.RandomRegistry;

public class CommandRandom implements IBotCommand {

	@Override
	public String getCommandName() 
	{
		return ".random";
	}
 //
	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length > 0)
		{
			if (args[0].equals("-o"))
			{
				if (!PermissionsManager.getUserHasPermission(sender, 1))
				{
					return true;
				}
				if (args.length > 1)
				{
					if (args.length > 2)
					{
						
					}
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": Invalid optional arguments");
				}
				return true;
			}
			String seed = "";
			for (String s : args)
			{
				seed = seed + " " + s;
			}
			sender.sendToChannel(sender.senderName + ": " + RandomRegistry.getRandom(seed, new Random()));
			if (IRCBot.getInstance().debugMode)
			{
				sender.sendToChannel("Seed = true, arg0 = " + args[0]);
			}
			return true;
		}
		sender.sendToChannel(sender.senderName + ": " + RandomRegistry.getRandom("", new Random()));
		if (IRCBot.getInstance().debugMode)
		{
			sender.sendToChannel("Seed = false");
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {".randthing"};
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
