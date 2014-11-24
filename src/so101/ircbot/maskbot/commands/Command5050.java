package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class Command5050 implements IBotCommand {

	@Override
	public String getCommandName()
	{
		return ".50/50";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		sender.sendToChannel(sender.senderName + ": Coming Soon!");
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
