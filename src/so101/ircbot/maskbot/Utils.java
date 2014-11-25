package so101.ircbot.maskbot;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class Utils 
{
	public static String formatCommandString(String string)
	{
		return string.replaceAll(",", "").replaceAll("\\?", "").replaceAll("\\!", "").replaceAll("\\:", "");
	}
	
	/**Formats message so it no longer contains Nickname prefix*/
	public static String formatCommandPrefix(String message)
	{
		String[] tempArgs = message.toLowerCase().split(" ");
		if (tempArgs[0].startsWith(IRCBot.getNick().toLowerCase()))
	   	{
			message = message.replaceFirst(message.split(" ")[0] + " ", "");
	   	}
		return message;
	}
	
	public static boolean hasCommandPrefix(ChannelSender sender, String command)
	{
		boolean gotPrefix = false;
		String[] tempArgs = command.toLowerCase().split(" ");
		if (tempArgs[0].startsWith(IRCBot.getNick().toLowerCase()))
	   	{
			gotPrefix = true;
	   	}
		else if (!sender.channelName.startsWith("#"))
		{
			gotPrefix = true;
		}
		return gotPrefix;
	}
	
	/**Returns if integer or not*/
	public static boolean isInteger(String string)
	{
		try
		{
			Integer.parseInt(string);
			return true;
		}
		catch (NumberFormatException e)
		{}
		return false;
	}
	
	/**Strings together the array and returns the string as a whole*/
	public static String formatArrayToString(Object[] array)
	{
		String s = "";
		for (Object o : array)
		{
			s = s + o + (o.equals(array[array.length - 1]) ? "" : " ");
		}
		return s;
	}
	
	/**Replaces all %<n> for args*/
	public static String formatStringForArgs(String baseString, String[] args)
	{
		for (int i = 0; i < args.length; i++)
		{
			baseString.replaceAll("%" + (i + 1), args[i + 1]);
		}
		return baseString;
	}
	
	/**Replaces all %s, %n and %c for data (Sender name, Nickname of bot, Channel name)*/
	public static String formatStringForSender(String baseString, ChannelSender chansender)
	{
		return baseString.replaceAll("%NICK", IRCBot.getNick()).replaceAll("%s", chansender.senderName).replaceAll("%n", IRCBot.getNick()).replaceAll("%c", chansender.channelName).replaceAll("%bot", IRCBot.getNick());
	}
	
	/**Formats string by splitting array list with spaces, commas and "and"s for proper english*/
	public static String formatArrayToFancyString(Collection<String> coll)
	{
		String finalString = "";
		List<String> list = new ArrayList<String>();
		list.addAll(coll);
		if (list.size() > 0)
		{
			for (int i = list.size(); i > 0; i--)
			{
				if (i == list.size())
				{
					finalString = list.get(i - 1);
				}
				else if (i == list.size() - 1)
				{
					finalString = list.get(i - 1) + " and " + finalString;
				}
				else
				{
					finalString = list.get(i - 1) + ", " + finalString;
				}
			}
		}
		return finalString;
	}
}
