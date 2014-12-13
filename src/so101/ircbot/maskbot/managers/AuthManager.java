package so101.ircbot.maskbot.managers;

import so101.ircbot.maskbot.IRCBot;

public class AuthManager 
{
	public class AuthToken implements Runnable
	{
		public String userNick;
		public String userId;
		public String userHost;
		
		public int ticks;
		public boolean valid;
		
		@Override
		public void run() 
		{
			this.ticks++;
			if (ticks >= 1800)
			{
				this.valid = false;
				IRCBot.getInstance().sendToIRC("PRIVMSG " + this.userNick + " :Auth token expired. Please reauthorize.");
				return;
			}
			
			try 
			{
				Thread.sleep(100L);
			} 
			catch (InterruptedException e) 
			{
			}
		}
		
	}
}
