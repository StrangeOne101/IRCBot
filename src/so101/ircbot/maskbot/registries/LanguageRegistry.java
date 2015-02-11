package so101.ircbot.maskbot.registries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.Log;
import so101.ircbot.maskbot.Utils;

public class LanguageRegistry
{
	private static Map<String, List<String>> lang = new HashMap<String, List<String>>();
	private static String fileLoc = "lang.properties";
	
	/**Load registry from file*/
	public static void loadData()
	{
		File file = new File(fileLoc);
		if (!file.exists())
		{
			IRCBot.log("Language file doesn't exist. Simply skipping.", Log.WARNING);
			return;
		}
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String s = reader.readLine();
			while (s != null)
			{
				if (!s.startsWith("#") && !s.equalsIgnoreCase("") && s.contains("="))
				{
					String lang = s.split("=")[0];
					String string = s.split("=")[1];
					if (lang.contains("."))
					{
						String lastThing = lang.split("\\.")[lang.split("\\.").length - 1];
						if (Utils.isInteger(lastThing))
						{
							String newlang = "";
							for (int i = 0; i < lang.split("\\.").length - 1; i++)
							{
								newlang = newlang.equalsIgnoreCase("") ? lang.split("\\.")[i] : newlang + "." + lang.split("\\.")[i];
							}
							lang = newlang;
						}
						//Add to registry
					}
				}
			}
			reader.close();
		}
		catch (FileNotFoundException e)
		{
			IRCBot.log("Language file not found while reading. How did this happen?????", Log.SEVERE);
		} 
		catch (IOException e) 
		{
			IRCBot.log("Error while reading language file!", Log.SEVERE);
			e.printStackTrace();
		}
	}
	
	/**Add string to registry for lang*/
	public static void addToRegistry(String lang, String string)
	{
		//Remove numbers if on the end
		String lastThing = lang.split("\\.")[lang.split("\\.").length - 1];
		if (Utils.isInteger(lastThing))
		{
			String newlang = "";
			for (int i = 0; i < lang.split("\\.").length - 1; i++)
			{
				newlang = newlang.equalsIgnoreCase("") ? lang.split("\\.")[i] : newlang + "." + lang.split("\\.")[i];
			}
			lang = newlang;
		}	
		//Create new list if none is found
		if (!LanguageRegistry.lang.containsKey(lang.toLowerCase()))
		{
			LanguageRegistry.lang.put(lang.toLowerCase(), new ArrayList<String>());
		}
		//Add to registry
		LanguageRegistry.lang.get(lang.toLowerCase()).add(string);
	}
	
	
}
