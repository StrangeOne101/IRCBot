package so101.ircbot.maskbot;

import so101.ircbot.maskbot.IRCBot.ChannelSender;

public abstract class Schedule
{
	public ChannelSender sender;
	
	public abstract boolean run();
	
	public Schedule(ChannelSender sender) {
		this.sender = sender;
	}
}