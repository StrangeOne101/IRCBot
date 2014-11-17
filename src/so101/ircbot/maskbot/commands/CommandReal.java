package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.CmdAction;
import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class CommandReal implements IBotCommand {

	@Override
	public String getCommandName() 
	{
		return "real";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (sender.channelName.startsWith("#"))
		{
			sender.sendToChannel(sender.senderName + ": I recommend using this command in a personal message.");
		}
		if (args.length > 0)
		{
			String s = Utils.formatArrayToString(args);
			return CmdAction.runCommand(s, sender);
		}
		sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getInstance().nick + " real <command>\"");
		return false;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"realcmd", "rcmd"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 4;
	}

}
