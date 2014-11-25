package so101.ircbot.maskbot;

public enum Log 
{
	INFO("INFO"),
	LOG("LOG"),
	WARNING("WARNING"),
	SEVERE("SEVERE"),
	DEBUG("DEBUG");
	
	private String n;
	
	Log(String s)
	{
		n = s;
	}
	
	@Override
	public String toString() 
	{
		return n;
	}
}
