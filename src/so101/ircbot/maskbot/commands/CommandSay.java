package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandSay implements IBotCommand
{

	@Override
	public String getCommandName() 
	{
		return "say";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		String s = "";
		for (int i = 0; i < args.length; i++)
		{
			s = s + args[i] + " ";
		}
		sender.sendToChannel(s);
		return false;
	}

	@Override
	public String[] getAliasis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

}
