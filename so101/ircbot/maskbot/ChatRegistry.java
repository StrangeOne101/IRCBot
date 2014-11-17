package so101.ircbot.maskbot;

import java.util.HashMap;
import java.util.Map;

public class ChatRegistry 
{
	protected static Map<String[], String[]> chatLines = new HashMap<String[], String[]>();
	
	public static String getReplyForMessage(String message)
	{
		
		return "";
	}
	
	
	public static void registerLine(String[] triggerWords, String reply)
	{
		chatLines.put(triggerWords, new String[] {reply});
	}
	
	public static void registerLine(String[] triggerWords, String[] replies)
	{
		chatLines.put(triggerWords, replies);
	}
}
