package so101.ircbot.maskbot;

public enum ChatColors 
{
	RED("\u000304"), 
	DARKRED("\u000305"),
	ORANGE("\u000307"), 
	YELLOW("\u000308"),
	GREEN("\u000309"),
	DARKGREEN("\u000303"),
	CYAN("\u000310"),
	BLUE("\u000311"),
	DARKBLUE("\u000312"),
	PURPLE("\u000313"),
	DARKPURPLE("\u000306"),
	GREY("\u000314"),
	BLACK("\u000301"),
	BOLD("\u0002"),
	RESET("\u0003")
	;
	
	ChatColors(String s) 
	{
		this.s = s;
	}
	
	public String s;
}
