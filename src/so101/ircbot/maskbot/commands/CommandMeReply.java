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
		for (int i = 0; i < args.length; i++)
		{
			if (args[i].toLowerCase().equals("me") || args[i].toLowerCase().equals("meh"))
			{
				args[i] = sender.senderName;
			}
			else if (args[i].toLowerCase().equals("my") || args[i].toLowerCase().equals("mah"))
			{
				args[i] = sender.senderName + "'" + (sender.senderName.endsWith("s") ? "" : "s");
			}
			else if (args[i].toLowerCase().equals("yourself"))
			{
				args[i] = IRCBot.getNick();
			}
		}
		if (args.length > 0)
		{
			String s = Utils.formatArrayToString(args);
			s = Utils.formatStringForSender(s, sender);
			s.replaceAll(":", "\\:");
			s.replaceAll("me ", "\\:");
			sender.sendToChannel("\u0001ACTION " + command + "s " + s + "\u0001");
		}
		else
		{
			String s = Utils.formatArrayToString(args);
			s = Utils.formatStringForSender(s, sender);
			s.replaceAll(":", "\\:");
			sender.sendToChannel("\u0001ACTION " + command + "s " + sender.senderName + "\u0001");
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
