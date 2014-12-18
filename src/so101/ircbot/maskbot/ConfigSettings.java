package so101.ircbot.maskbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue.ValueType;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

import so101.ircbot.maskbot.games.MapGame;
import so101.ircbot.maskbot.games.Player;
import so101.ircbot.maskbot.handlers.CookieHandler;
import so101.ircbot.maskbot.managers.PermissionsManager;

public class ConfigSettings 
{
	protected static final String PATH = "config.cfg";
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
				IRCBot.log("Something went wrong: " + e.getMessage(), Log.SEVERE);
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
		
		JsonObjectBuilder cookieBuilder = Json.createObjectBuilder();
		for (String j : ((CookieHandler)CommandRegistry.commandHandlerList.get(0)).storedCookies.keySet())
		{
			cookieBuilder.add(j, ((CookieHandler)CommandRegistry.commandHandlerList.get(0)).storedCookies.get(j));
		}
		JsonObject cookies = cookieBuilder.build();
		
		JsonObjectBuilder globalVarBuilder = Json.createObjectBuilder();
		for (String s9 : IRCBot.getInstance().management.BOT_CRESIDENTIALS.keySet())
		{
			if (IRCBot.getInstance().management.BOT_CRESIDENTIALS.get(s9) instanceof String)
			{
				globalVarBuilder.add(s9, (String)IRCBot.getInstance().management.BOT_CRESIDENTIALS.get(s9));
			}
			else if (IRCBot.getInstance().management.BOT_CRESIDENTIALS.get(s9) instanceof Integer)
			{
				globalVarBuilder.add(s9, (Integer)IRCBot.getInstance().management.BOT_CRESIDENTIALS.get(s9));
			}
			else if (IRCBot.getInstance().management.BOT_CRESIDENTIALS.get(s9) instanceof Boolean)
			{
				globalVarBuilder.add(s9, (Boolean)IRCBot.getInstance().management.BOT_CRESIDENTIALS.get(s9));
			}
			else
			{
				IRCBot.log("Tried to save global variable of unknown type! (Var \"" + s9 + "\" with type " + IRCBot.getInstance().management.BOT_CRESIDENTIALS.get(s9).getClass() + ")", Log.WARNING);
			}
			
		}
		JsonObject vars = globalVarBuilder.build();
		
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
				.add("Cookies", cookies)
				.add("MapGamePlayers", mapGamePlayers)
				.add("GlobalVars", vars)
				.build();
		FileWriter writer;
		try 
		{        
	        writer = new FileWriter(file);
			Map<String, Object> properties = new HashMap<String, Object>(1);
	        properties.put(JsonGenerator.PRETTY_PRINTING, true);
	        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
			JsonWriter jsonWriter = writerFactory.createWriter(writer);
			jsonWriter.writeObject(json);
			jsonWriter.close();
			writer.close();
			//JsonWriter jsonwriter = Json.createWriter(writer);
			//jsonwriter.writeObject(json);
			//jsonwriter.close();
			//writer.close();
		} 
		catch (IOException e) 
		{
			IRCBot.log("Something went wrong while writing to save file! " + e.getMessage(), Log.SEVERE);
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
				JsonObject jObjectGlobalVars = json.getJsonObject("GlobalVars");
				reader.close();
				
				//Debug
				IRCBot.log("Config file: " + json.toString(), Log.INFO);
				
				if (jArrayChannels != null)
				{
					for (int i = 0; i < jArrayChannels.size(); i++)
					{
						IRCBot.getInstance().channels.add(jArrayChannels.getString(i));
						IRCBot.getInstance().currentChannels.add(jArrayChannels.getString(i));
					}
				}
				
				if (jArrayMutedChannels != null)
				{
					for (int i = 0; i < jArrayMutedChannels.size(); i++)
					{
						IRCBot.getInstance().mutedChannels.add(jArrayMutedChannels.getString(i));
					}
				}
				
				if (jArrayChatCmds != null)
				{
					for (int i = 0; i < jArrayChatCmds.size(); i++)
					{
						CommandRegistry.chatCommands.add(jArrayChatCmds.getString(i));
					}
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
				
				if (jObjectGlobalVars != null)
				{
					for (String l : jObjectGlobalVars.keySet())
					{
						ValueType type = jObjectGlobalVars.get(l).getValueType();
						if (type.equals(ValueType.STRING))
						{
							IRCBot.getInstance().management.setGolbalBotVariable(l, jObjectGlobalVars.getString(l));
						}
						else if (type.equals(ValueType.NUMBER))
						{
							IRCBot.getInstance().management.setGolbalBotVariable(l, jObjectGlobalVars.getInt(l));
						}
						else if (type.equals(ValueType.FALSE) || type.equals(ValueType.TRUE))
						{
							IRCBot.getInstance().management.setGolbalBotVariable(l, jObjectGlobalVars.getBoolean(l));
						}
						else if (type.equals(ValueType.NULL))
						{
							IRCBot.getInstance().management.setGolbalBotVariable(l, null);
						}
						else
						{
							IRCBot.log("Tried to save global var of unknown type! (Var " + l + " with type " +  jObjectGlobalVars.get(l).getClass(), Log.WARNING);
						}
						
					}
					
					//If username and nickname aren't found
					if (!IRCBot.getInstance().management.isCresidentialsValid())
					{
						lackOfInfoShutdown();
					}
				}
				else
				{
					//Debug
					IRCBot.log("Global vars not found.", Log.INFO);
					lackOfInfoShutdown();
				}

				if (jObjectPerms != null)
				{
					for (String j : jObjectPerms.keySet())
					{
						PermissionsManager.permissionTable.put(j, jObjectPerms.getInt(j));
					}
				}
				reader.close();
			} 
			catch (FileNotFoundException e) {} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		else
		{
			//Debug
			IRCBot.log("Didn't find config file.", Log.INFO);
			lackOfInfoShutdown();
		}
	}
	
	private static void lackOfInfoShutdown()
	{
		File file = new File("config.cfg");
		IRCBot.log("Cresidentials required for bot not found!", Log.SEVERE);
		IRCBot.log("Bot will not attempt to connect to server and will shutdown shortly!", Log.SEVERE);
		IRCBot.log("Config file will be regenerated with required fields. Password field is optional and is not required.", Log.INFO);
		IRCBot.log("Shutting down bot...", Log.INFO);
		
		FileWriter writer;
		try 
		{
			writer = new FileWriter(file);
			Map<String, Object> properties = new HashMap<String, Object>(1);
	        properties.put(JsonGenerator.PRETTY_PRINTING, true);
	        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
			JsonWriter jsonWriter = writerFactory.createWriter(writer);
			jsonWriter.writeObject(Json.createObjectBuilder().add("GlobalVars", IRCBot.getInstance().management.buildRequiredVars()).build());
			jsonWriter.close();
			writer.close();
			
			IRCLog.INSTANCE.shutdownLogger();
			IRCBot.getInstance().enabled = false;
		} 
		catch (IOException e) 
		{
		}
	}
	
	public static boolean writeFile(JsonObject object, File file)
	{
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
		} catch (IOException e) {
			IRCBot.log("Error while writing object to file \"" + file.getName() + "\"!", Log.SEVERE);
			return false;
		}
		Map<String, Object> properties = new HashMap<String, Object>(1);
        properties.put(JsonGenerator.PRETTY_PRINTING, true);
        JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
		JsonWriter jsonWriter = writerFactory.createWriter(writer);
		jsonWriter.writeObject(object);
		jsonWriter.close();
		try {
			writer.close();
		} catch (IOException e) {
			IRCBot.log("Error while writing object to file \"" + file.getName() + "\"!", Log.SEVERE);
			return false;
		}
		return true;
	}
}
