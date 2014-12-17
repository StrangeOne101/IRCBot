package so101.ircbot.maskbot.managers;

import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;

import so101.ircbot.maskbot.SavedData;

public class DataManager 
{
	private static List<SavedData> DATA = new ArrayList<SavedData>(); 
	
	
	public static void registerData(SavedData data)
	{
		DATA.add(data);
	}
	
	public static JsonObject saveAllData(JsonObject object)
	{
		JsonObjectBuilder b = Json.createObjectBuilder();
		for (String s : object.keySet())
		{
			b.add(s, object.get(s));
		}
		for (SavedData d : DATA)
		{
			b.add(d.getDataTitle(), (JsonValue)d.saveData());
		}
		return object;
	}
	
	
}
