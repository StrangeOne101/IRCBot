package so101.ircbot.maskbot;

import java.util.ArrayList;
import java.util.List;

public class Scheduler
{
	public static List<Schedule> schedules = new ArrayList<Schedule>();
	public static List<InputMessage> latestMessages = new ArrayList<InputMessage>(); 
	
	public static void run()
	{
		for (int i = 0; i < schedules.size(); i++)
		{
			if (schedules.get(i).run())
			{
				schedules.remove(i);
			}
		}
		
		for (int i1 = 0; i1 < latestMessages.size(); i1++)
		{
			//latestMessages.remove(i1);
		}
	}
}
