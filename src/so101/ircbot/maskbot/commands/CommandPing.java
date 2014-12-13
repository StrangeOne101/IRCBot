package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandPing implements IBotCommand 
{

	@Override
	public String getCommandName() 
	{
		return ".ping";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
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
		return false;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 0;
	}

}
