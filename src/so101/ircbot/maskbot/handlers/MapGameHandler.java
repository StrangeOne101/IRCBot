package so101.ircbot.maskbot.handlers;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.games.MapGame;
import so101.ircbot.maskbot.games.Player;

public class MapGameHandler implements ICommandHandler 
{

	@Override
	public String getCommandHandlerName() 
	{
		return "MapGameHandler";
	}

	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		if (MapGame.instance.playerFiles.containsKey(sender.senderName))	
		{
			Player p = MapGame.instance.playerFiles.get(sender.senderName);
			p.lastChannel = sender.channelName;
			if (!p.paused)
			{
				if (message.startsWith("~") || !sender.channelName.startsWith("#"))
				{
					if (message.startsWith("~"))
					{
						message = message.replaceFirst("~", "");
					}
					MapGame.instance.getNextInput(p, message);
					return true;
				}
			}
		}			
		return false;
	}

}
