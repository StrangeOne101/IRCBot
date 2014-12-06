package so101.ircbot.maskbot;

import java.util.HashMap;
import java.util.Map;

public class DataRegistry 
{
	private static Map<String, Object> GLOBALVARS = new HashMap<String, Object>();
	private static Map<String, Object> LOCALVARS = new HashMap<String, Object>();
	
	/**Sets the local value of variable KEY. Warning: Local data is for temporary data only! It will not be saved after quit/restart!*/
	public static void setLocalVar(String key, Object value)
	{
		LOCALVARS.put(key.toUpperCase(), value);
	}
	
	/**Sets the global value of variable KEY. All global data will be saved to config on save. Use local data for temporary data.*/
	public static void setGlobalVar(String key, Object value)
	{
		LOCALVARS.put(key.toUpperCase(), value);
	}
	
	/**Gets the local variable from registry (Integer)*/
	public static int getLocalInt(String key)
	{
		return (int) LOCALVARS.get(key.toUpperCase());
	}
	
	/**Gets the local variable from registry (String). Local data will be lost on bot restart/quit! Use Global data instead for this.*/
	public static String getLocalString(String key)
	{
		return (String) LOCALVARS.get(key.toUpperCase());
	}
	
	/**Gets the local variable from registry (Double). Local data will be lost on bot restart/quit! Use Global data instead for this.*/
	public static double getLocalDouble(String key)
	{
		return (double) LOCALVARS.get(key.toUpperCase());
	}
	
	/**Gets the local variable from registry (Float). Local data will be lost on bot restart/quit! Use Global data instead for this.*/
	public static float getLocalFloat(String key)
	{
		return (float) LOCALVARS.get(key.toUpperCase());
	}
	
	/**Gets the local variable from registry (Boolean). Local data will be lost on bot restart/quit! Use Global data instead for this.*/
	public static boolean getLocalBoolean(String key)
	{
		return (boolean) LOCALVARS.get(key.toUpperCase());
	}
	
	/**Gets the local variable from registry (Object). Local data will be lost on bot restart/quit! Use Global data instead for this.*/
	public Object getLocalObject(String key)
	{
		return LOCALVARS.get(key);
	}
	
	/**Gets the global variable from registry (Integer).*/
	public static int getGlobalInt(String key)
	{
		return (int) GLOBALVARS.get(key.toUpperCase());
	}
	
	/**Gets the global variable from registry (String)*/
	public static String getGlobalString(String key)
	{
		return (String) GLOBALVARS.get(key.toUpperCase());
	}
	
	/**Gets the global variable from registry (Double)*/
	public static double getGlobalDouble(String key)
	{
		return (double) GLOBALVARS.get(key.toUpperCase());
	}
	
	/**Gets the global variable from registry (Float)*/
	public static float getGlobalFloat(String key)
	{
		return (float) GLOBALVARS.get(key.toUpperCase());
	}
	
	/**Gets the global variable from registry (Boolean)*/
	public static boolean getGlobalBoolean(String key)
	{
		return (boolean) GLOBALVARS.get(key.toUpperCase());
	}
	
	/**Gets the global variable from registry (Object)*/
	public Object getGlobalObject(String key)
	{
		return GLOBALVARS.get(key);
	}
	
	//TODO Save Global Data
}
