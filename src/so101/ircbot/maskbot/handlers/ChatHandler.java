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
			s = s.replaceAll("\\:", "");
			s = s.replaceAll(",", "");
			s = s.replaceAll("(?i)&ACTION", "\u0001ACTION");
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
				if (s1.startsWith("-") || args[args.length - 1].equals(s1))
				{
					if (!curFormat.equals(""))
					{
						switch (curFormat)
						{
						case "-c":
							contains.add(thingToLookFor);
							break;
						case "-e":
							equals.add(thingToLookFor);
							break;
						case "-s":
							startswith.add(thingToLookFor);
							break;
						case "-n":
							endswith.add(thingToLookFor);
							break;
						case "-r":
							reply = thingToLookFor + args[args.length - 1];
							break;
						case "-o":
							reply = thingToLookFor + args[args.length - 1];	
							break;
						}
					}
					if (!args[args.length - 1].equals(s1))
					{
						curFormat = s1.toLowerCase();
					}
					thingToLookFor = "";
				}
				else
				{
					thingToLookFor = thingToLookFor.equals("") ? s1 : thingToLookFor + " " + s1;
				}
			}
			if (IRCBot.getInstance().debugMode)
			{
				IRCBot.alertRoots(contains.toString());
				IRCBot.alertRoots(equals.toString());
				IRCBot.alertRoots(startswith.toString());
				IRCBot.alertRoots(endswith.toString());
			}
			for (String s2 : contains)
			{
				if (!message.toLowerCase().contains(this.formatStringForAll(s2.toLowerCase(), sender)))
				{
					flag = false;
				}
			}
			for (String s3 : equals)
			{
				if (!message.toLowerCase().equalsIgnoreCase(this.formatStringForAll(s3, sender)))
				{
					flag = false;
				}
			}
			for (String s4 : startswith)
			{
				if (!message.toLowerCase().startsWith(this.formatStringForAll(s4.toLowerCase(), sender)))
				{
					flag = false;
				}
			}
			for (String s5 : endswith)
			{
				if (!message.toLowerCase().endsWith(this.formatStringForAll(s5.toLowerCase(), sender)))
				{
					flag = false;
				}
			}
			
			if (reply.startsWith("~"))
			{
				reply.replaceFirst("~", "\u0001ACTION");
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
	
	/**Replaces all needed fields in string. Use string.split(" ") to give array.*/
	public String formatStringForAll(String[] message, ChannelSender sender)
	{
		String[] newS = message;
		for (int i = 0; i < message.length; i++)
		{
			newS[i] = this.replaceVariableForWorld(message[i], "nick", IRCBot.getNick());
			newS[i] = this.replaceVariableForWorld(message[i], "n", IRCBot.getNick());
			newS[i] = this.replaceVariableForWorld(message[i], "s", sender.senderName);
			newS[i] = this.replaceVariableForWorld(message[i], "c", sender.channelName);			
		}
		return Utils.formatArrayToString(newS);
	}
	
	/**Replaces all needed fields. ONLY USE FOR SINGLE WORDS!!*/
	public String formatStringForAll(String word, ChannelSender sender)
	{
		word = this.replaceVariableForWorld(word, "nick", IRCBot.getNick());
		word = this.replaceVariableForWorld(word, "n", IRCBot.getNick());
		word = this.replaceVariableForWorld(word, "s", sender.senderName);
		word = this.replaceVariableForWorld(word, "c", sender.channelName);
		return word;
	}
	
	public String replaceVariableForWorld(String originalWord, String var, String newvar)
	{
		String s = originalWord;
		if (originalWord.toLowerCase().contains("%" + var.toLowerCase()))
		{
			if (originalWord.lastIndexOf("%") > originalWord.indexOf("%")) //The word is has 2 % in is, with the var in middle
			{
				s = originalWord.replaceAll("(?i)%" + var + "%", newvar);
			}
			else
			{
				s = originalWord.replaceAll("(?i)%" + var, newvar);
			}
		}
		return s;
	}
}
