package so101.ircbot.maskbot.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;

import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.Log;

public class DictionaryManager 
{
	public static Map<String, List<String>> dictionaries = new HashMap<String, List<String>>();
	public static Map<String, String> aliases = new HashMap<String, String>();
	
	protected static final String DICTIONARIES = "dictionary.cfg"; 
	
	public static void addToDictionary(String word, String dictionary)
	{
		String dict = dictionary.toLowerCase();
		
		if (aliases.containsKey(dictionary.toLowerCase()))
		{
			dict = aliases.get(dictionary.toLowerCase());
		}
		//TODO FIx
		if (!dictionaries.containsKey(dictionary.toLowerCase()) && !aliases.containsKey(dictionary.toLowerCase()))
		{
			dictionaries.put(dictionary.toLowerCase(), new ArrayList<String>());
		}
		
		
		
		dictionaries.get(dict).add(word);
	}
	
	public static void saveData()
	{
		IRCBot.log("Saving Dictionaries...", Log.INFO);
		File file = new File(DICTIONARIES);
		if (!file.exists())
		{
			try {
				file.createNewFile();
				IRCBot.log("Dictionary Config not found: creating.", Log.INFO);
			} catch (IOException e) 
			{
				IRCBot.log("Something went wrong: " + e.getMessage(), Log.SEVERE);
			}
		}		
		
		JsonObjectBuilder aliasesObjBuilder = Json.createObjectBuilder();
		for (String s : aliases.keySet())
		{
			aliasesObjBuilder.add(s, aliases.get(s));
		}
		JsonObject aliasesObj = aliasesObjBuilder.build();

		JsonObjectBuilder dictObjBuilder = Json.createObjectBuilder();
		for (String s : dictionaries.keySet())
		{
			JsonArrayBuilder currentDict = Json.createArrayBuilder();
			for (String s1 : dictionaries.get(s))
			{
				currentDict.add(s1);
			}
			dictObjBuilder.add(s, currentDict.build());
		}
		JsonObject dictionaries = dictObjBuilder.build();
		
		
		JsonObject json = Json.createObjectBuilder()
				.add("Dictionaries", dictionaries)
				.add("Aliases", aliasesObj)
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
			IRCBot.log("Something went wrong while writing to save file! " + e.getMessage(), Log.SEVERE);
			e.printStackTrace();
		}
		IRCBot.log("Dictionaries Saved.", Log.INFO);
	}
	
	public static void loadData()
	{
		IRCBot.log("Loading Dictionaries...", Log.INFO);
		File file = new File(DICTIONARIES);
		if (file.exists())
		{
			FileReader reader;
			try
			{
				reader = new FileReader(file);
				JsonReader jsonReader = Json.createReader(reader);
				JsonObject json = jsonReader.readObject();
				JsonObject jObjAliases = json.getJsonObject("Aliases");
				JsonObject jObjDictionaries = json.getJsonObject("Dictionaries");
				
				for (String s : jObjAliases.keySet())
				{
					aliases.put(s, jObjAliases.getString(s));
				}
				
				for (String s1 : jObjDictionaries.keySet())
				{
					JsonArray array = jObjDictionaries.getJsonArray(s1);
					dictionaries.put(s1, new ArrayList<String>());
					for (int i = 0; i < array.size(); i++)
					{
						dictionaries.get(s1).add(array.getString(i));
					}
				}				
			} 
			catch (FileNotFoundException e) {}
			IRCBot.log("Dictionaries Loaded.", Log.INFO);
		}
		else
			IRCBot.log("Dictionaries not found. Doesn't matter, will continue anyway.", Log.INFO);
	}
}
