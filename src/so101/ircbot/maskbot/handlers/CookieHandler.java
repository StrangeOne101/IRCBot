package so101.ircbot.maskbot.handlers;

import java.util.HashMap;
import java.util.Map;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CookieHandler implements ICommandHandler 
{
	/**Map of all stored cookies for players*/
	public Map<String, Integer> storedCookies = new HashMap<String, Integer>();
	
	@Override
	public String getCommandHandlerName() 
	{
		return "cookies";
	}

	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		if (!(message.toLowerCase().contains(IRCBot.getNick().toLowerCase())) && !IRCBot.getInstance().mutedChannels.contains(sender.channelName) && (message.toLowerCase().contains("cookie") || message.toLowerCase().contains("cookies")))
		{
			sender.sendToChannel("Cookies! +1!");
			this.storedCookies.put(sender.senderName, this.storedCookies.get(sender.senderName));
		}
		
		return false;
	}

}
