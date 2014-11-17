package so101.ircbot.maskbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import so101.ircbot.maskbot.games.MapGame;
import so101.ircbot.maskbot.games.Player;

public class ConfigSettings 
{
	protected static final String PATH = "config.yml";
	protected static final String PLAYERSAVES = "players.yml";
	
	public ConfigSettings() 
	{
		
	}
	
	@SuppressWarnings("unchecked")
	public static void saveData()
	{
		File file = new File("config.cfg");
		if (!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) 
			{
				IRCBot.log("Something went wrong: " + e.getMessage(), Level.SEVERE);
			}
		}		
		
		Object[] lists = {IRCBot.getInstance().channels, IRCBot.getInstance().mutedChannels, CommandRegistry.chatCommands};
		JsonArray[] arrays = new JsonArray[3];
		
		for (int i = 0; i < lists.length; i++)
		{
			JsonArrayBuilder array = Json.createArrayBuilder();	
			for (String s : ((List<String>)(lists[i])))
			{
				array.add(s);
			}
			arrays[i] = array.build();
		}
		
		JsonObjectBuilder permBuilder = Json.createObjectBuilder();
		for (String j : PermissionsManager.permissionTable.keySet())
		{
			permBuilder.add(j.toLowerCase(), PermissionsManager.permissionTable.get(j));
		}
		JsonObject perms = permBuilder.build();
		
		JsonObjectBuilder playersBuilder = Json.createObjectBuilder();
		for (String k : MapGame.instance.playerFiles.keySet())
		{
			JsonObject player = Json.createObjectBuilder()
					.add("Nick", MapGame.instance.playerFiles.get(k).playerNick)
					.add("Health", MapGame.instance.playerFiles.get(k).health)
					.add("QID", MapGame.instance.playerFiles.get(k).getNextQID)
					.add("Hydration", MapGame.instance.playerFiles.get(k).hydration)
					.add("Lumber", MapGame.instance.playerFiles.get(k).lumber)
					.add("PosX", MapGame.instance.playerFiles.get(k).posX)
					.add("PosY", MapGame.instance.playerFiles.get(k).posY)
					.add("Time", MapGame.instance.playerFiles.get(k).time)
					.build();
			playersBuilder.add(k, player);
		}
		JsonObject mapGamePlayers = playersBuilder.build();
		
		JsonObject json = Json.createObjectBuilder()
				.add("ChatCommands", arrays[2])
				.add("Channels", arrays[0])
				.add("MutedChannels", arrays[1])
				.add("Permissions", perms)
				.add("MapGamePlayers", mapGamePlayers)
				.build();
		FileWriter writer;
		try 
		{
			writer = new FileWriter(file);
			JsonWriter jsonwriter = Json.createWriter(writer);
			jsonwriter.writeObject(json);
			jsonwriter.close();
			writer.close();
		} 
		catch (IOException e) 
		{
			IRCBot.log("Something went wrong while writing to save file! " + e.getMessage(), Level.SEVERE);
			e.printStackTrace();
		}
	}
	
	public static void loadData()
	{
		File file = new File("config.cfg");
		if (file.exists())
		{
			FileReader reader;
			try
			{
				reader = new FileReader(file);
				JsonReader jsonReader = Json.createReader(reader);
				JsonObject json = jsonReader.readObject();
				JsonArray jArrayChannels = json.getJsonArray("Channels");
				JsonArray jArrayMutedChannels = json.getJsonArray("MutedChannels");
				JsonArray jArrayChatCmds = json.getJsonArray("ChatCommands");
				JsonObject jObjectPerms = json.getJsonObject("Permissions");
				JsonObject jObjectMGPlayers = json.getJsonObject("MapGamePlayers");
				
				for (int i = 0; i < jArrayChannels.size(); i++)
				{
					IRCBot.getInstance().channels.add(jArrayChannels.getString(i));
					IRCBot.getInstance().currentChannels.add(jArrayChannels.getString(i));
				}
				
				for (int i = 0; i < jArrayMutedChannels.size(); i++)
				{
					IRCBot.getInstance().mutedChannels.add(jArrayMutedChannels.getString(i));
				}
				
				for (int i = 0; i < jArrayChatCmds.size(); i++)
				{
					CommandRegistry.chatCommands.add(jArrayChatCmds.getString(i));
				}
				
				try 
				{
					for (String k : jObjectMGPlayers.keySet())
					{
						JsonObject player = jObjectMGPlayers.getJsonObject(k);
						Player p = new Player(k, "");
						p.getNextQID = player.getInt("QID");
						p.health = player.getInt("Health");
						p.hydration = player.getInt("Hydration");
						p.lumber = player.getBoolean("Lumber");
						p.time = player.getInt("Time");
						p.posX = player.getInt("PosX");
						p.posY = player.getInt("PosY");		
					}
				}
				catch (NullPointerException e) {}
				
				try 
				{
					for (String j : jObjectPerms.keySet())
					{
						PermissionsManager.permissionTable.put(j, jObjectPerms.getInt(j));
					}
				}
				catch (NullPointerException e) {}
				
			} 
			catch (FileNotFoundException e) {}
		}
	}
}
