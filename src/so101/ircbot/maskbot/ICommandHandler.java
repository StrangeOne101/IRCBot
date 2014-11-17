package so101.ircbot.maskbot;

import so101.ircbot.maskbot.IRCBot.ChannelSender;

public interface ICommandHandler 
{
	/**Name of command handler*/
	public abstract String getCommandHandlerName();
	
	/**When a user talks. Should return true when command has been handled and no more commands need to be processed.*/
	public abstract boolean onCommand(String message, ChannelSender sender);
}
