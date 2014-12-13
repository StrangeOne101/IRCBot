package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandBookmarks implements IBotCommand
{

	@Override
	public String getCommandName() 
	{
		return "bookmark";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"bookmarks"};
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
