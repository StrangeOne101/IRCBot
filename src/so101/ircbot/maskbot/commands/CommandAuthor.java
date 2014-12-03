package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandAuthor implements IBotCommand {

	@Override
	public String getCommandName() 
	{
		return "author";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		sender.sendToChannel(sender.senderName + ": I was developed by StrangeOne101, Nov 2014. Code is avalaible at https://github.com/StrangeOne101/IRCBot");
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"dev", "developer"};
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
