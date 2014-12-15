package so101.ircbot.maskbot.handlers;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class LeaveHandler implements ICommandHandler {

	@Override
	public String getCommandHandlerName() 
	{
		return "LEAVE HANDLER";
	}

	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		if (message.toLowerCase().contains(IRCBot.getNick().toLowerCase()))
		{
			if (message.toLowerCase().contains("leave"))
			{
				ChannelHandler.leaveChannel(sender.channelName);
				if (IRCBot.getInstance().channels.contains(sender.channelName))
				{
					IRCBot.getInstance().channels.remove(sender.channelName);
				}
				return true;
			}
			
			if (message.toLowerCase().contains("off"))
			{
				String m = message.toLowerCase();
				if (m.contains("fuck") || m.contains("piss"))
				{
					ChannelHandler.leaveChannel(sender.channelName);
					if (IRCBot.getInstance().channels.contains(sender.channelName))
					{
						IRCBot.getInstance().channels.remove(sender.channelName);
					}
					return true;
				}
			}
			
			if (message.toLowerCase().contains("go") && message.toLowerCase().contains("away"))
			{
				ChannelHandler.leaveChannel(sender.channelName);
				if (IRCBot.getInstance().channels.contains(sender.channelName))
				{
					IRCBot.getInstance().channels.remove(sender.channelName);
				}
				return true;
			}
		}
		return false;
	}

}
