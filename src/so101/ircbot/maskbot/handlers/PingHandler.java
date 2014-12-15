package so101.ircbot.maskbot.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.ThreadedProcess;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.PingManager;

public class PingHandler implements ICommandHandler 
{	
	private Map<Integer, List<Double>> pingResultTimes = new HashMap<Integer, List<Double>>();
	private Map<Integer, List<String>> pingResultsString = new HashMap<Integer, List<String>>();
	//private Map<String, Integer> ids = new HashMap<String, Integer>();
	
	
	@Override
	public String getCommandHandlerName() 
	{
		return "PING";
	}

	@Override
	public boolean onCommand(String message, final ChannelSender sender) 
	{
		String[] args = message.split(" ");
		if (args.length > 0 && args[0].toLowerCase().equals(".ping"))
		{
			sender.sendToServer("PRIVMSG " + IRCBot.getNick() + " :PING " + sender.senderName + " " + sender.channelName + " " + PingManager.getCurrentTime().toString());
			return true;
		}
		else if (args.length > 0 && (args[0].toLowerCase().equals(".pingtest") || args[0].toLowerCase().equals(".lagtest")))
		{
			int pingCount = 5;
			double delay = 2;
			
			int id_ = pingResultTimes.size();
			final int id = id_;
			
			if (args.length > 1)
			{
				if (args[1].toLowerCase().equals("help"))
				{
					sender.sendToChannel(sender.senderName + ": Pings the server over time and returns any unusual delay times if any found. Command is only used to detect server lag. Usage is \".lagtest [count] [delay]\".");
					return true;
				}
				else
				{
					if (!Utils.isInteger(args[1]) || Integer.parseInt(args[1]) < 1)
					{
						sender.sendToChannel(sender.senderName + ": Ping count must be a valid, positive number!");
						return true;
					}
					pingCount = Integer.parseInt(args[1]);
					
					if (args.length > 2)
					{
						if (!Utils.isInteger(args[2]) || Integer.parseInt(args[2]) < 1)
						{
							sender.sendToChannel(sender.senderName + ": Ping delay must be a valid, positive number!");
							return true;
						}
						delay = Double.parseDouble(args[2]);
					}
				}
			}
			if (pingResultTimes.containsKey(id))
			{
				sender.sendToChannel(sender.senderName + ": Lag test already in progress.");
				return true;
			}
			final int pingCount_ = pingCount;
			final double delay_ = delay;
			//pingtestresults = new ArrayList<Double>();
			//pingString = new ArrayList<String>();
		
			pingResultsString.put(id, new ArrayList<String>());
			pingResultTimes.put(id, new ArrayList<Double>());
			sender.sendToChannel(sender.senderName + ": Pinging...");
			ThreadedProcess process = new ThreadedProcess() 
			{
				@Override
				public void run() 
				{
					for (int i = 0; i < pingCount_; i++)
					{
						sender.sendToServer("PRIVMSG " + IRCBot.getNick() + " :PINGTEST " + i + " " + sender.senderName + " " + sender.channelName + " " + PingManager.getCurrentTime().toString() + " " + id);
						try 
						{
							Thread.sleep((long) (1000L * delay_));
						} 
						catch (InterruptedException e) 
						{
							sender.sendToChannel(sender.senderName + ": Thread was interupted while sleeping. Some pings may have failed to send!");
						}
					}
				}
			};
			process.start();
			ThreadedProcess process1 = new ThreadedProcess() 
			{
				@Override
				public void run() 
				{
					try 
					{
						Thread.sleep((long) (((pingCount_ * delay_) + 2L) * 1000L));
						int results = pingResultTimes.get(id).size();
						List<String> l = new ArrayList<String>();
						String s = "";
						double timeTaken = PingManager.getTimeDifference(PingManager.getCurrentTime(), pingResultsString.get(id).get(0).split(" ")[4]);
						for (double d : pingResultTimes.get(id))
						{
							if (d > 0.5D)
							{
								l.add(String.valueOf(d));
							}
						}
						if (l.size() > 0)
						{
							s = "(" + Utils.formatArrayToFancyString(l) + ").";
						}
						else
						{
							s = ".";
						} // Unusual 
						sender.sendToChannel(sender.senderName + ": " + results + "/" + pingCount_ + " results returned with " + l.size() + " unusual results" + s + " Took " + timeTaken + " seconds.");
						pingResultTimes.remove(id);
						pingResultsString.remove(id);
					} 
					catch (InterruptedException e) 
					{
						sender.sendToChannel(sender.senderName + ": Thread was interupted while sleeping, ping failed!");
						pingResultTimes.remove(id);
						pingResultsString.remove(id);
					}
				}
			};
			process1.start();
			return true;
		}
		
		
		//Process the message sent to self
		if (sender.channelName.equals(IRCBot.getNick()) && sender.senderName.equals(IRCBot.getNick()))
		{
			if (args.length > 1 && args[0].toLowerCase().equals("ping"))
			{
				double delay = PingManager.getTimeDifference(PingManager.getCurrentTime(), args[3]);
				sender.sendToServer("PRIVMSG " + args[2] + " :" + args[1] + ": Current delay is at " + delay + " seconds");
				return true;
			}
			else if (args.length > 1 && args[0].toLowerCase().equals("pingtest"))
			{
				int id = Integer.parseInt(args[4]);
				double delay = PingManager.getTimeDifference(PingManager.getCurrentTime(), args[3]);
				pingResultTimes.get(id).add(delay);
				pingResultsString.get(id).add(message);
			}
		}
		return false;
	}

}
