package so101.ircbot.maskbot.handlers;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class HandlerOnThing implements ICommandHandler 
{

	@Override
	public String getCommandHandlerName() 
	{
		return "ONTHING";
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
				if (args[0].toLowerCase().equals("onjoin") || args[0].toLowerCase().equals("onleave"))
				{
					
				}
			}
		}
		return false;
		
	}

}
