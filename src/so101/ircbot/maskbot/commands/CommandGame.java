package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.ConfigSettings;
import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.games.MapGame;
import so101.ircbot.maskbot.games.Player;

public class CommandGame implements IBotCommand {

	@Override
	public String getCommandName() 
	{
		return "game";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length > 0)
		{
			if (args[0].toLowerCase().equals("pause"))
			{
				if (MapGame.instance.playerFiles.containsKey(sender.senderName))
				{
					Player p = MapGame.instance.playerFiles.get(sender.senderName);
					if (!p.paused)
					{
						sender.sendToChannel(sender.senderName + ": Game paused. Use \"" + IRCBot.getInstance().nick + " game unpause\" to resume");
						p.paused = true;
						return true;
					}
					else 
					{
						sender.sendToChannel(sender.senderName + ": Game already paused. Use \"" + IRCBot.getInstance().nick + " game unpause\" to resume");
						return true;
					}
					
				}
				else
				{
					sender.sendToChannel(sender.senderName + ":You are not currrently in a game. Use \"" + IRCBot.getInstance().nick + " games play <game>\" to play a game");
					return true;
				}	
			}
			else if (args[0].toLowerCase().equals("unpause"))
			{
				if (MapGame.instance.playerFiles.containsKey(sender.senderName))
				{
					Player p = MapGame.instance.playerFiles.get(sender.senderName);
					if (p.paused)
					{
						sender.sendToChannel(sender.senderName + ": Game unpaused.");
						p.paused = false;
						sender.sendToChannel(MapGame.instance.inputRegistry.get(p.getNextQID));
						return true;
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Game already in action. Use \"" + IRCBot.getInstance().nick + " game pause\" to pause game");
						return true;
					}
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": You are not currrently in a game. Use \"" + IRCBot.getInstance().nick + " games play <game>\" to play a game");
					return true;
				}
			}
			else if (args[0].toLowerCase().equals("debug"))
			{
				if (MapGame.instance.playerFiles.containsKey(sender.senderName))
				{
					Player p = MapGame.instance.playerFiles.get(sender.senderName);
					//p.debug = p.debug ? false : true;
					String s = String.valueOf(MapGame.instance.map[p.posY].toCharArray()[p.posX]);
					sender.sendToChannel("PosX: " + p.posX + ", PosY: " + p.posY + ", Char at is \"" + s + "\", Time: " + p.time);
					//ConfigSettings.writePlayerSaveToFile(p);
					return true;
				}
				else
				{
					sender.sendToChannel(sender.senderName + ": You are not currrently in a game. Use \"" + IRCBot.getInstance().nick + " games play <game>\" to play a game");
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public String[] getAliasis() {
		// TODO Auto-generated method stub
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
