package so101.ircbot.maskbot.commands.dex;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class Pokedex 
{
	public static Map<String, PokeEntry> entriesByName = new HashMap<String, PokeEntry>();
	
	public static Map<String, PokeEntry> entriesById = new HashMap<String, PokeEntry>();
	
	public static void fetchData(ChannelSender sender)
	{
		final String URL = "http://pokemondb.net";
		final String SUBURL = "/pokedex/national";
		String gen = "";
		try 
		{
			Document doc = Jsoup.connect(URL + SUBURL).get();
			Elements pokemon = doc.getElementsByClass("infocard-tall");
			for (int i = 0; i < 721; i++)
			{
				Element box = pokemon.get(i);
				String name = box.getElementsByClass("ent-name").get(0).text();
				String url = URL + box.getElementsByTag("a").get(0).attr("href");
				String id = box.getElementsByTag("small").get(0).text().replaceFirst("#", "");
				String types;
				Element typeElement = box.getElementsByClass("aside").get(0);
				String type1 = typeElement.getElementsByTag("a").get(0).text();
				String type2 = typeElement.getElementsByTag("a").size() == 2 ? " and " + typeElement.getElementsByTag("a").get(1).text() : "";
				types = type1 + type2;
				//sender.sendToChannel("Return " + (i + 1) + ": " + name + ", " + "#" + id + ", " + url + ", " + types);
				if (Integer.parseInt(id) >= 1 && Integer.parseInt(id) <= 151) gen = "I";
				if (Integer.parseInt(id) >= 152 && Integer.parseInt(id) <= 251) gen = "II";
				if (Integer.parseInt(id) >= 252 && Integer.parseInt(id) <= 386) gen = "III";
				if (Integer.parseInt(id) >= 387 && Integer.parseInt(id) <= 493) gen = "IV";
				if (Integer.parseInt(id) >= 494 && Integer.parseInt(id) <= 649) gen = "V";
				if (Integer.parseInt(id) >= 650 && Integer.parseInt(id) <= 721) gen = "VI";
				if (Integer.parseInt(id) >= 722) gen = "VII+";
				PokeEntry entry = new PokeEntry(name, id, types, url, gen);
				entriesById.put(id, entry);
				entriesByName.put(name.toLowerCase(), entry);
			}
		} 
		catch (IOException e) 
		{
			if (sender != null)
			{
				sender.sendToChannel("Error: failed to fetch from " + URL + SUBURL);
			}
		}
	}
}
