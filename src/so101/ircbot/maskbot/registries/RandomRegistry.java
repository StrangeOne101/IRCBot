package so101.ircbot.maskbot.registries;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRegistry 
{
	private static List<String> things = new ArrayList<String>();
	private static List<String> adverbs = new ArrayList<String>();
	
	static 
	{
		things.add("sheep");   things.add("cow");   things.add("creeper");
		things.add("pig");   things.add("unicorn");   things.add("ball");
		things.add("penis");   things.add("cat");   things.add("dog");
		things.add("iPhone");   things.add("car");   things.add("rat");
		things.add("book");   things.add("spellbook");   things.add("mirror");
		things.add("moon");   things.add("sun");   things.add("flower");
		things.add("planet");   things.add("star");   things.add("wall");
		things.add("elephant");   things.add("bull");   things.add("brick");
		things.add("apple");   things.add("coconut");   things.add("banana");
		things.add("fish");   things.add("grape");   things.add("jar of testicles");
		things.add("chicken feet");   things.add("shark");   things.add("squid");
		things.add("octopus");   things.add("alien");   things.add("meteor");
		things.add("dummy");   things.add("monkey testicles");   things.add("sheep");
		things.add("bus");   things.add("helicopter");   things.add("jet");
		things.add("cloud");   things.add("fence");   things.add("plank");
		things.add("bungee");   things.add("wand");   things.add("stalker");
		things.add("potato");   things.add("ingot");   things.add("jar of dirt");
		things.add("ship");   things.add("landshark");   things.add("baby");
		things.add("UFO (Unidentified Fucking Object)");
		
		adverbs.add("rainbow colored");  adverbs.add("red");  adverbs.add("blue");  adverbs.add("green");
		adverbs.add("mossy");  adverbs.add("broken");  adverbs.add("golden");  adverbs.add("old");
		adverbs.add("run-down");  adverbs.add("horrible");  adverbs.add("purple");  adverbs.add("hairy");
		adverbs.add("smelly");  adverbs.add("giant");  adverbs.add("small");  adverbs.add("average");
		adverbs.add("killer");  adverbs.add("midget");  adverbs.add("freaky");  adverbs.add("discusting");
		adverbs.add("awful");  adverbs.add("soft");  adverbs.add("yummy");  adverbs.add("slimy");
		adverbs.add("orange");  adverbs.add("yellow");  adverbs.add("pink");  adverbs.add("roman");
		adverbs.add("micro");  adverbs.add("massive");  adverbs.add("unstable");  adverbs.add("dangerous");
		adverbs.add("roundish");  adverbs.add("squareish");  adverbs.add("(insert some random shape here)ish");  adverbs.add("shiny");
		adverbs.add("sparkly");  adverbs.add("bright");  adverbs.add("gloomy");  adverbs.add("giddy");
		adverbs.add("unlucky");  adverbs.add("cursed");  adverbs.add("dark");  adverbs.add("light");
		adverbs.add("twisted");  adverbs.add("curvy");  adverbs.add("explosive");  adverbs.add("diabolical");
	}
	
	public static void addThing(String thing)
	{
		things.add(thing.toLowerCase());
	}
	
	public static void addAdverb(String adverb)
	{
		adverbs.add(adverb.toLowerCase());
	}
	
	public static String getRandom(String seed, Random r)
	{
		Random rand = r;
		rand.setSeed(r.nextLong());
		//rand.setSeed(new Random().nextLong());
		String finalS = "";
		if (!seed.isEmpty())
		{
			long seedL = 0L;
			for (int i = 0; i < seed.length(); i++)
			{
			      seedL += (int)seed.charAt(i);
			}   
			rand.setSeed(seedL);
		}
		int n = rand.nextInt(14) + 1;
		finalS = n + " ";
		int n1 = rand.nextInt(4);
		int n2 = rand.nextInt(adverbs.size());
		int n20 = rand.nextInt(adverbs.size());
		int n21 = rand.nextInt(adverbs.size());
		int n3 = rand.nextInt(things.size());
		finalS = finalS + adverbs.get(n2) + " ";
		finalS = finalS + adverbs.get(n20) + " ";
		if (n1 == 0)
			finalS = finalS + adverbs.get(n21) + " ";
		
		String thing = things.get(n3);
		if (n != 1)
		{
			String c = String.valueOf(thing.toCharArray()[thing.toCharArray().length - 1]);
			String newThing = "";
			for (int i = 0; i < thing.toCharArray().length - 1; i++)
			{
				newThing = newThing + thing.toCharArray()[i];
			}
			switch (c)
			{
			case "s":
				newThing += "s'";
				break;
			case "y":
				newThing += "ies";
				break;
			default:
				newThing += c + "s";
				break;
			}
			thing = newThing;
		}
		finalS = finalS + thing + " ";
		return finalS;
	}
}
