package so101.ircbot.maskbot;

public enum ChatColors 
{
	RED("\u000204"), 
	DARKRED("\u000205"),
	ORANGE("\u000207"), 
	YELLOW("\u000208"),
	GREEN("\u000209"),
	DARKGREEN("\u000203"),
	CYAN("\u000210"),
	BLUE("\u000211"),
	DARKBLUE("\u000212"),
	PURPLE("\u000213"),
	DARKPURPLE("\u000206"),
	GREY("\u000214"),
	BLACK("\u000201"),
	;
	
	ChatColors(String s) 
	{
		this.s = s;
	}
	
	public String s;
}
