package so101.ircbot.maskbot.handlers;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.PingManager;

public class PingHandler implements ICommandHandler 
{

	@Override
	public String getCommandHandlerName() 
	{
		return "PING";
	}

	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		String[] args = message.split(" ");
		if (args.length > 0 && args[0].toLowerCase().equals(".ping"))
		{
			sender.sendToServer("PRIVMSG " + IRCBot.getNick() + " :PING " + sender.senderName + " " + sender.channelName + " " + PingManager.getCurrentTime().toString());
			return true;
		}
		
		//Process the message sent to self
		if (sender.channelName.equals(IRCBot.getNick()) && sender.senderName.equals(IRCBot.getNick()))
		{
			if (args.length > 1 && args[0].toLowerCase().equals("ping"))
			{
				double delay = PingManager.getTimeDifference(PingManager.getCurrentTime(), args[3]);
				sender.sendToServer("PRIVMSG " + args[2] + " :" + args[1] + ": Current delay is at " + delay + " seconds");
				return true;
			}
		}
		return false;
	}

}
