package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class CommandMeReply implements IBotCommand 
{
	public String command;
	
	/***/
	public CommandMeReply(String s) 
	{
		this.command = s;
	}
	
	@Override
	public String getCommandName() 
	{
		return this.command;
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length > 0)
		{
			
			String s = Utils.formatArrayToString(args);
			s = Utils.formatStringForSender(s, sender);
			s.replaceAll(":", "\\:");
			sender.sendToChannel("\u0001ACTION " + command + "s " + s);
		}
		else
		{
			String s = Utils.formatArrayToString(args);
			s = Utils.formatStringForSender(s, sender);
			s.replaceAll(":", "\\:");
			sender.sendToChannel("\u0001ACTION " + command + "s " + sender.senderName);
		}
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
		return true;
	}

	@Override
	public int getPermissionLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

}
