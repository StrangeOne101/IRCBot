package so101.ircbot.maskbot.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.json.stream.JsonParser;

import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.Log;

public class BookmarkManager 
{
	public static Map<String, List<Object[]>> bookmarks = new HashMap<String, List<Object[]>>();
	
	
	
	
	/**Save data to bookmarks.cfg*/
	public static void saveData()
	{
		File file = new File("config.cfg");
		if (!file.exists())
		{
			try 
			{
				file.createNewFile();
			} 
			catch (IOException e) {
				IRCBot.log("Error while creating bookmark save file!", Log.SEVERE);
			}
		}
		
		JsonObjectBuilder builder = Json.createObjectBuilder();
	}
	
}
