package so101.ircbot.maskbot.commands;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class CommandTell implements IBotCommand {

	public static List<TellEntry> dates = new ArrayList<TellEntry>();
	
	@Override
	public String getCommandName() 
	{
		return "tell";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length <= 1)
		{
			sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " tell <who> [when] <what>\"");
		}
		else
		{
			String to = args[0];
			String text = Utils.formatArrayToString(args).replaceFirst(to + " ", "");
			String[] newArgs = text.split(" ");
			boolean specifiesWhen = false;
			if (newArgs[0].equalsIgnoreCase("in") || translateStringToInt(newArgs[0]) != -1)
			{
				specifiesWhen = true;
				if (newArgs[0].equalsIgnoreCase("in"))
				{
					newArgs = Utils.formatArrayToString(newArgs).replaceFirst(newArgs[0] + " ", "").split(" ");
				}
			}
			
			if (!specifiesWhen)
			{
				IRCBot.getInstance().sendToIRC("PRIVMSG " + to + " :" + to + ": " + sender.senderName + " says \"" + text + "\"");
				sender.sendToChannel(sender.senderName + ": Done.");
			}
			else
			{
				float currNum = 0;
				boolean hasAtLeastOneTime = false;
				String fullMessage = "";
				boolean doneTime = false;
				int[] times = new int[7]; //seconds, mins, hours, days, week, month, year
				for (int i = 0; i < newArgs.length; i++)
				{
					String s = newArgs[i];
					if (s.endsWith(","))
					{
						if (s.endsWith(","))
						{
							s = s.substring(0, s.split("").length - 2);
						}
					}
					if ((s.equalsIgnoreCase("and") || s.equalsIgnoreCase("of")) && !doneTime)
					{
						//Do nothing :D / Skip
					}
					else if ((Utils.isInteger(s) || translateStringToInt(s) != -1) && !doneTime)
					//else if (currNum == 0 && !s.equalsIgnoreCase("and") && !s.equalsIgnoreCase("about"))
					{
						if (Utils.isInteger(s))
						{
							currNum += Integer.parseInt(s);
						}
						else if (translateStringToInt(s) != -1)
						{
							currNum += translateStringToInt(s);
						}
					}
					else if (isTimeKeyword(s) && !doneTime)
					{
						//Inset code here
						String s1 = s.endsWith("s") ? s.substring(0, s.split("").length - 2) : s;
						if (s1.equalsIgnoreCase("second") || s1.equalsIgnoreCase("sec"))
						{
							times[0] = (int) currNum;
						}
						else if (s1.equalsIgnoreCase("minute") || s1.equalsIgnoreCase("min"))
						{
							float f = currNum - (int)currNum;
							times[1] = (int) currNum;
							if (f != 0.0F)
							{
								times[0] = (int) (60 * f);
							}
						}
						else if (s1.equalsIgnoreCase("hour"))
						{
							float f = currNum - (int)currNum;
							times[2] = (int) currNum;
							if (f != 0.0F)
							{
								times[1] = (int) (60 * f);
							}
						}
						else if (s1.equalsIgnoreCase("day"))
						{
							float f = currNum - (int)currNum;
							times[3] = (int) currNum;
							if (f != 0.0F)
							{
								sender.sendToChannel(sender.senderName + ": Can you be a bit more specific than \"" + newArgs[i-1] + " " + s + "\"?");
								return true;
							}
						}
						else if (s1.equalsIgnoreCase("week"))
						{
							float f = currNum - (int)currNum;
							times[4] = (int) currNum;
							if (f != 0.0F)
							{
								sender.sendToChannel(sender.senderName + ": Can you be a bit more specific than \"" + newArgs[i-1] + " " + s + "\"?");
								return true;
							}
						}
						else if (s1.equalsIgnoreCase("month"))
						{
							float f = currNum - (int)currNum;
							times[5] = (int) currNum;
							if (f != 0.0F)
							{
								sender.sendToChannel(sender.senderName + ": Can you be a bit more specific than \"" + newArgs[i-1] + " " + s + "\"?");
								return true;
							}
						}
						else if (s1.equalsIgnoreCase("year"))
						{
							float f = currNum - (int)currNum;
							times[6] = (int) currNum;
							if (f != 0.0F)
							{
								sender.sendToChannel(sender.senderName + ": Can you be a bit more specific than \"" + newArgs[i-1] + " " + s + "\"?");
								return true;
							}
						}
						hasAtLeastOneTime = true;
						currNum = 0;
					}
					else if (!hasAtLeastOneTime && !doneTime)
					{
						sender.sendToChannel("Sorry, " + sender.senderName + ". But I don't know what you mean by \"" + s + "\"");
						return true;
					}
					else if (!doneTime && hasAtLeastOneTime && (s.equalsIgnoreCase("that") || s.equalsIgnoreCase("to")))
					{
						//Do nothing :D
					}
					else if (!s.equalsIgnoreCase("of") && !s.equalsIgnoreCase("and") && hasAtLeastOneTime)
					{
						doneTime = true;
						fullMessage = fullMessage.equals("") ? newArgs[i] : fullMessage + " " + newArgs[i];
					}
				}
				//Make the time in a long var
				Calendar deadline = Calendar.getInstance();
				int yy = deadline.get(Calendar.YEAR) + times[6];
				int MM = deadline.get(Calendar.MONTH) + times[5];
				int dd = deadline.get(Calendar.DAY_OF_MONTH) + times[3] + (times[4] * 7);
				int hh = deadline.get(Calendar.HOUR_OF_DAY) + times[2];
				int mm = deadline.get(Calendar.MINUTE) + times[1];
				int ss = deadline.get(Calendar.SECOND) + times[0];
				deadline.set(yy, MM, dd, hh, mm, ss);
				long endTime = deadline.getTime().getTime();
				
				TellEntry entry = new TellEntry(to, endTime, fullMessage, sender.senderName);
				dates.add(entry);
				
				sender.sendToChannel(sender.senderName + ": Will do.");
				
				if (IRCBot.getInstance().debugMode)
				{
					sender.sendToChannel("Debug: " + ss + " | " + mm + " | " + hh + " | " + dd + " | " + MM + " | " + yy + " || "
							+ times[0] + " | " + times[1] + " | " + times[2] + " | " + times[3] + " | " + times[4] + " | " + times[5] + " | " + times[6]);
				}
				//sender.sendToChannel("Sorry, " + sender.senderName + ". This feature is still a work in progress!");
			}
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return null;
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 0;
	}
	
	/**Translate human words into numbers that can be understood*/
	public float translateStringToInt(String string)
	{
		float i = 0;
		if (string.equalsIgnoreCase("one")) {i = 1;}
		else if (string.equalsIgnoreCase("two")) {i = 2;}
		else if (string.equalsIgnoreCase("three")) {i = 3;}
		else if (string.equalsIgnoreCase("thir")) {i = 3;}
		if (string.toLowerCase().startsWith("four")) {i = 4;}
		else if (string.toLowerCase().startsWith("five")) {i = 5;}
		else if (string.toLowerCase().startsWith("fif")) {i = 5;}
		else if (string.toLowerCase().startsWith("six")) {i = 6;}
		else if (string.toLowerCase().startsWith("seven")) {i = 7;}
		else if (string.toLowerCase().startsWith("eight")) {i = 8;}
		else if (string.toLowerCase().startsWith("eigh")) {i = 8;}
		else if (string.toLowerCase().startsWith("nine")) {i = 9;}
		else if (string.toLowerCase().startsWith("ten")) {i = 10;}
		if (string.equalsIgnoreCase("eleven")) {i = 11;}
		else if (string.equalsIgnoreCase("twelve")) {i = 12;}
		if (string.toLowerCase().endsWith("ty")) {i *= 10;}
		else if (string.toLowerCase().endsWith("teen")) {i += 10;}
		if (string.equalsIgnoreCase("twenty")) {i = 20;}
		if (string.equalsIgnoreCase("half")) {i = 0.5F;}
		else if (string.equalsIgnoreCase("quarter") || string.equalsIgnoreCase("quarters")) {i = 0.25F;}
		else if (string.equalsIgnoreCase("an") || string.equalsIgnoreCase("a")) {i = 1;}
				
		return i != 0 ? i : -1;
	}
	
	
	public boolean isTimeKeyword(String word)
	{
		word = word.endsWith("s") ? word.substring(0, word.split("").length - 2) : word;
		return word.equalsIgnoreCase("hour") || word.equalsIgnoreCase("second") || word.equalsIgnoreCase("sec")|| word.equalsIgnoreCase("minute") || word.equalsIgnoreCase("min") || word.equalsIgnoreCase("day") || word.equalsIgnoreCase("week") || word.equalsIgnoreCase("month") || word.equalsIgnoreCase("year");
	}

	public class TellEntry
	{
		private long time;
		private String user, message, sender;
		
		public TellEntry(String user, long time, String message, String sender)
		{
			this.user = user;
			this.time = time;
			this.message = message;
			this.sender = sender;
		}
		
		public String getMessage() {
			return message;
		}
		
		public String getUser() {
			return user;
		}
		
		public long getTime() {
			return time;
		}
		
		public String getSender() {
			return sender;
		}
	}
}
