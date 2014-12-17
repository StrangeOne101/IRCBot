package so101.ircbot.maskbot.managers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.Log;

public class PingManager
{
	private static SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSSS");
	
	public static String getCurrentTime()
	{
		return format.format(new Date());
	}
	
	/**Returns difference in seconds. Will return -1F is error occurred while processing*/
	public static double getTimeDifference(String date1, String date2)
	{
		Date d1, d2;
		try 
		{
			d1 = format.parse(date1);
			d2 = format.parse(date2);
			
			long milli = d1.getTime() - d2.getTime();
			return ((double)milli / 1000L);
		} 
		catch (ParseException e) 
		{
			IRCBot.log("Couldn't parse time while processing difference! (" + date1 + ", " + date2 + ")", Log.SEVERE);
		}
		
		return -1F;
	}
}
