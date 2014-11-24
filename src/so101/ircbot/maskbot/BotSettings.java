package so101.ircbot.maskbot;

import java.util.HashMap;
import java.util.Map;

public class BotSettings 
{
	protected Map<String, Object> BOT_CRESIDENTIALS = new HashMap<String, Object>();
	
	public boolean setGolbalBotVariable(String variable, Object object)
	{
		if (BOT_CRESIDENTIALS.containsKey(variable))
		{
			return false;
		}
		BOT_CRESIDENTIALS.put(variable, object);
		return true;
	}
	
	public Object getBotGlobalVariable(String var)
	{
		if (BOT_CRESIDENTIALS.containsKey(var))
		{
			return BOT_CRESIDENTIALS.get(var);
		}
		return null;
	}
}
