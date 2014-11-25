package so101.ircbot.maskbot;

import java.util.HashMap;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class BotSettings 
{
	protected Map<String, Object> BOT_CRESIDENTIALS = new HashMap<String, Object>();
	public final String nicknameDefault = "__INSERT_NICK_HERE__";
	public final String userDefault = "__INSERT_USER_HERE__";
	public final String serverDefault = "irc.esper.net";
	public final int portDefault = 6667;
	
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
	
	public boolean isCresidentialsValid()
	{
		if (!BOT_CRESIDENTIALS.containsKey("NICK") || !BOT_CRESIDENTIALS.containsKey("USER"))
		{
			return false;
		}
		if (BOT_CRESIDENTIALS.get("NICK").equals("") || BOT_CRESIDENTIALS.get("NICK").equals(nicknameDefault))
		{
			return false;
		}
		if (BOT_CRESIDENTIALS.get("USER").equals("") || BOT_CRESIDENTIALS.get("USER").equals(userDefault))
		{
			return false;
		}
		return true;
	}
	
	public JsonObject buildRequiredVars()
	{
		JsonObjectBuilder builder = Json.createObjectBuilder();
		builder.add("NICK", nicknameDefault);
		builder.add("USER", userDefault);
		builder.add("PASS", "");
		builder.add("SERVER", serverDefault);
		builder.add("PORT", portDefault);
		return builder.build();
	}
}
