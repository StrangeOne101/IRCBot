package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.games.MapGame;
import so101.ircbot.maskbot.games.Player;

public class CommandGames implements IBotCommand
{

	@Override
	public String getCommandName() 
	{
		return "games";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length == 0)
		{
			sender.sendToChannel(sender.senderName + ": Command usage is \"MaskBot games <play/list>\"");
			return true;
		}
		else if (args[0].toLowerCase().equals("list"))
		{
			sender.sendToChannel(sender.senderName + ": Current games available: MAP. Use \"MaskBot games play <game>\"");
			return true;
		}
		else if (args[0].toLowerCase().equals("play"))
		{
			if (args.length < 2)
			{
				sender.sendToChannel(sender.senderName + ": Current games available: MAP. Correct usage is   MaskBot games play <game>");
				return true;
			}
			else if (args[1].toLowerCase().equals("map"))
			{
				if (MapGame.instance.playerFiles.containsKey(sender.senderName))
				{
					sender.sendToChannel(sender.senderName + ": You are already in game! To pause game, use \"Maskbot game pause\"");
					return true;
				}
				MapGame.instance.playerFiles.put(sender.senderName, new Player(sender.senderName, sender.channelName));
				Player p = MapGame.instance.playerFiles.get(sender.senderName);
				sender.sendToChannel(sender.senderName + ": Now playing MAP game.");
				MapGame.instance.printLetter(p.posX, p.posY, p);
				return true;
				//MapGame.instance.travel(MapGame.instance.playerFiles.get(sender.senderName));
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Game not found");
				return true;
			}
		}
		else
		{
			sender.sendToChannel(sender.senderName + ": Command not found. Usage is \"MaskBot games <play/list>\"");
			return true;
		}
	}

	@Override
	public String[] getAliasis() 
	{
		return null;
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

}
