package so101.ircbot.maskbot.handlers;

import java.util.ArrayList;
import java.util.List;

import so101.ircbot.maskbot.CommandRegistry;
import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class ChatHandler implements ICommandHandler 
{

	@Override
	public String getCommandHandlerName() 
	{
		return "ChatHandler";
	}

	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		for (int i = 0; i < CommandRegistry.chatCommands.size(); i++)
		{
			String s = CommandRegistry.chatCommands.get(i);
			s = Utils.formatStringForSender(s, sender).toLowerCase();
			/** Contains: -c
			 *  Equals -e
			 *  StartsWith -s
			 *  EndsWith -n
			 *  Result: -r
			 * */
			String[] args = s.split(" ");
			String curFormat = "";
			String thingToLookFor = "";
			String reply = "";
			boolean flag = true;
			List<String> contains = new ArrayList<String>();
			List<String> equals = new ArrayList<String>();
			List<String> startswith = new ArrayList<String>();
			List<String> endswith = new ArrayList<String>();
			for (String s1 : args)
			{
				if (s1.startsWith("-"))
				{
					if (!curFormat.equals(""))
					{
						switch (curFormat)
						{
						case "-c":
							contains.add(thingToLookFor);
						case "-e":
							equals.add(thingToLookFor);
						case "-s":
							startswith.add(thingToLookFor);
						case "-n":
							endswith.add(thingToLookFor);
						case "-r":
							reply = thingToLookFor;
						case "-o":
							reply = thingToLookFor;	
						}
					}
					curFormat = s1;
					
				}
				else
				{
					thingToLookFor = thingToLookFor.equals("") ? s1 : thingToLookFor + " " + s1;
				}
			}
			for (String s2 : contains)
			{
				if (!message.toLowerCase().contains(s2))
				{
					flag = false;
				}
			}
			for (String s3 : equals)
			{
				if (!message.toLowerCase().equals(s3))
				{
					flag = false;
				}
			}
			for (String s4 : startswith)
			{
				if (!message.toLowerCase().startsWith(s4))
				{
					flag = false;
				}
			}
			for (String s5 : endswith)
			{
				if (!message.toLowerCase().startsWith(s5))
				{
					flag = false;
				}
			}
			
			if (flag)
			{
				if (reply.equals(""))
				{
					IRCBot.getInstance().sendToIRC("PRIVMSG Strange :Strange: An error was found in chatmessage #" + i + " as no reply was found.");
					return false;
				}
				sender.sendToChannel(reply);
				return true;
			}
			
		}
		return false;
	}
}
