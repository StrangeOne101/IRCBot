package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.ConfigSettings;
import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandConfig implements IBotCommand {

	@Override
	public String getCommandName() 
	{
		return "config";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length > 0)
		{
			if (args[0].toLowerCase().equals("load"))
			{
				ConfigSettings.loadData();
				sender.sendToChannel(sender.senderName + ": Data loaded.");
			}
			else if (args[0].toLowerCase().equals("save"))
			{
				ConfigSettings.saveData();
				sender.sendToChannel(sender.senderName + ": Data saved.");
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " config <save/load>\"");
			}
		}
		else
		{
			sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " config <save/load>\"");
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"data"};
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
