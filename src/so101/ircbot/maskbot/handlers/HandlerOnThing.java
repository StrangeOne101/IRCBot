package so101.ircbot.maskbot.handlers;

import java.util.HashMap;
import java.util.Map;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class HandlerOnThing implements ICommandHandler 
{
	public Map<String, String> joinCommands = new HashMap<String, String>();
	public Map<String, String> leaveCommands = new HashMap<String, String>();
	
	
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
					if (args.length > 2)
					{
						if (args[0].toLowerCase().equals("onjoin"))
						{
							String s = Utils.formatArrayToString(args);
							s.replaceAll(args[0] + " ", "");
							s.replaceAll(args[1] + " ", "");
							s.replaceAll(args[2] + " ", "");
							//this.joinCommands.p
						}
					}
				}
			}
		}
		return false;
		
	}

}
