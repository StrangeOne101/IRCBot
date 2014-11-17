package so101.ircbot.maskbot.commands.dex;

public class PokeEntry
{
	public String name;
	public String id;
	public String types;
	public String url;
	public String gen;
	
	public PokeEntry(String name, String id, String types, String url, String gen)
	{
		this.name = name;
		this.id = id;
		this.types = types;
		this.url = url;
		this.gen = gen;
	}
}