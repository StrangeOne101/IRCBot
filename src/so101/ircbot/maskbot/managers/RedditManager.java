package so101.ircbot.maskbot.managers;

import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.Log;
import so101.ircbot.maskbot.RedditParser;

public class RedditManager 
{
	/**Data for .5050 command*/
	public static RedditParser data5050;
	/**Data for .pics command*/
	public static RedditParser dataPics;
	
	public static void fetchData()
	{
		IRCBot.log("Fetching data for .5050 command...", Log.INFO);
		data5050 = new RedditParser("http://reddit.com/r/fiftyfifty");
		IRCBot.log("Data fetched.", Log.INFO);
		IRCBot.log("Fetching data for .pics command...", Log.INFO);
		dataPics = new RedditParser("http://reddit.com/r/pics");
		IRCBot.log("Data fetched.", Log.INFO);
	}
}
