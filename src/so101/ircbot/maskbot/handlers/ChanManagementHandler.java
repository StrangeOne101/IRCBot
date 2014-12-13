package so101.ircbot.maskbot.handlers;

import java.util.List;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.PermissionsManager;
import so101.ircbot.maskbot.Utils;

public class ChanManagementHandler implements ICommandHandler 
{
	
	@Override
	public String getCommandHandlerName() 
	{
		return "ChannelManagement";
	}
	
	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		if (Utils.hasCommandPrefix(sender, message))
		{
			message = Utils.formatCommandString(Utils.formatCommandPrefix(message));
			String[] args = message.split(" ");
			if (args.length > 0)
			{
				if (args[0].toLowerCase().equals("flags"))
				{
					if (PermissionsManager.getUserHasPermission(sender, 2))
					{
						if (args.length > 2)
						{
							sender.sendToServer("PRIVMSG ChanServ :flags " + sender.channelName + " " + args[1] + " " + args[2]);
						}
						else
						{
							sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " flags <user> <flags>\"");
						}
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": " + PermissionsManager.INVALID_PERM);
					}
					return true;
				}
			}
			
		}
		return false;
	}
	
	/**Joins channel*/
	protected static void joinChannel(String channel)
	{
		String chan = channel;
		if (!channel.startsWith("#"))
		{
			chan = "#" + channel;
		}
		IRCBot.getInstance().sendToIRC("JOIN " + chan);
		if (!IRCBot.getInstance().currentChannels.contains(chan))
		{
			IRCBot.getInstance().currentChannels.add(chan);
		}
	}
	
	/**Leaves channel*/
	protected static void leaveChannel(String channel)
	{
		String chan = channel;
		if (!channel.startsWith("#"))
		{
			chan = "#" + channel;
		}
		IRCBot.getInstance().sendToIRC("PART :" + chan);
		if (IRCBot.getInstance().currentChannels.contains(chan))
		{
			IRCBot.getInstance().currentChannels.remove(chan);
		}
	}

}
