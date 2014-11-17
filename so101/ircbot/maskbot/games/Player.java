package so101.ircbot.maskbot.games;

import java.util.Random;

import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.games.MapGame.TreeHouse;

public class Player 
{
	public int posX = 3 + new Random().nextInt(6);
	public int posY = 4 + new Random().nextInt(5);
	public int time;
	public int health = 100;
	public int hydration = 80; //100% = full, 50% small thrist
	public boolean lumber = false;
	
	int sword = 0;
	int forgeTime = -1;
	TreeHouse[] treeHouses = new TreeHouse[2];
	
	
	public int getNextQID = 99;
	public int failedTypeAttempts = 0;
	public boolean paused = false;
	public boolean debug = false;
	
	public String playerNick;
	public String lastChannel;

	
	public Player(String nick, String channel) 
	{
		this.playerNick = nick;
		this.lastChannel = channel;
	}
	
	public void sendToPlayerChannel(String line)
	{
		if (this.lastChannel == "" || this.lastChannel == IRCBot.getInstance().nick)
		{
			IRCBot.getInstance().sendToIRC("PRIVMSG " + this.playerNick + " :" + line);
		}
		else
		{
			IRCBot.getInstance().sendToIRC("PRIVMSG " + this.lastChannel + " :" + line);
		}
	}
}
