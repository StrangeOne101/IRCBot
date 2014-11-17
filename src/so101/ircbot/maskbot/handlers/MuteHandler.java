package so101.ircbot.maskbot.handlers;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class MuteHandler implements ICommandHandler 
{

	@Override
	public String getCommandHandlerName() 
	{
		return "Mute|Unmute";
	}

	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		message = Utils.formatCommandString(Utils.formatCommandPrefix(message));
		String[] args = message.split(" ");
		if (args.length > 0)
		{
			if (args[0].equals("mute") || args[0].equals("shutup") || args[0].equals("shut") || args[0].equals("shatup"))
			{
				if (IRCBot.getInstance().mutedChannels.contains(sender.channelName))
				{
					sender.sendToChannel("I was already quiet... >.>");
				}
				else
				{
					sender.sendToChannel("Sorry, I'll shut up >.>");
					IRCBot.getInstance().mutedChannels.add(sender.channelName);
				}
				return true;
			}
			else if (args[0].equals("unmute"))
			{
				if (IRCBot.getInstance().mutedChannels.contains(sender.channelName))
				{
					sender.sendToChannel("Danke, " + sender.senderName + " :3");
					IRCBot.getInstance().mutedChannels.remove(sender.channelName);
				}
				else
				{
					sender.sendToChannel("I was muted...?");
				}
				return true;
			}
		}
		
		return false;
	}

}
