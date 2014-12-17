package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.managers.PermissionsManager;

public class CommandSudo implements IBotCommand 
{

	@Override
	public String getCommandName() 
	{
		return "sudo";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length < 1)
		{
			sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " sudo <user> <command>\"");
			return true;
		}
		if (!PermissionsManager.permissionTable.containsKey(args[0].toLowerCase()) || (PermissionsManager.permissionTable.get(args[0].toLowerCase()) < PermissionsManager.permissionTable.get(sender.senderName.toLowerCase())))
		{
			if (args.length > 1)
			{
				IRCBot.getInstance().processLineFromServer(":" + args[0] + "!~__NOUSER__@__NOSERVER__ PRIVMSG " + sender.channelName + " :" + Utils.formatArrayToString(args).replaceFirst(args[0] + " ", ""));
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " sudo <user> <command>\"");
			}
		}
		else
		{
			sender.sendToChannel(sender.senderName + ": You can not sudo someone with equal or higher permission than you!");
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
	public int getPermissionLevel() 
	{
		return 3;
	}

}
