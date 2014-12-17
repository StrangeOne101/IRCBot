package so101.ircbot.maskbot.handlers;

import java.util.ArrayList;
import java.util.List;

import so101.ircbot.maskbot.CommandRegistry;
import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.ThreadedProcess;
import so101.ircbot.maskbot.Utils;

public class ChatHandler implements ICommandHandler 
{

	@Override
	public String getCommandHandlerName() 
	{
		return "ChatHandler";
	}

	@Override
	public boolean onCommand(String message, final ChannelSender sender) 
	{
		for (int i = 0; i < CommandRegistry.chatCommands.size(); i++)
		{
			String s = Utils.formatStringForSender(CommandRegistry.chatCommands.get(i), sender);
			//s = s.replaceAll("\\:", "");
			//s = s.replaceAll(",", "");
			//s = s.replaceAll("&ACTION", "\u0001ACTION");
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
			List<Object> replies = new ArrayList<Object>();
			List<String> options = new ArrayList<String>();
			for (String s1 : args)
			{
				if (s1.startsWith("-") || args[args.length - 1].equals(s1))
				{
					if (args[args.length - 1].equals(s1))
					{
						thingToLookFor = thingToLookFor + " " + s1;
					}
					
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
							replies.add(new String(thingToLookFor));
							break;
						case "-o":
							//reply = thingToLookFor + args[args.length - 1];	
							options.add(thingToLookFor);
							break;
						case "-d":
							replies.add(new Double(thingToLookFor));
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
			
			/*if (reply.startsWith("~"))
			{
				reply = reply.replaceFirst("~", "");
			}*/
			if (IRCBot.getInstance().debugMode)
			{
				IRCBot.alertRoots(contains.toString());
				IRCBot.alertRoots(equals.toString());
				IRCBot.alertRoots(startswith.toString());
				IRCBot.alertRoots(endswith.toString());
			}
			for (String s2 : contains)
			{
				if (!message.toLowerCase().contains(s2.toLowerCase()))
				{
					flag = false;
				}
			}
			for (String s3 : equals)
			{
				if (!message.toLowerCase().equalsIgnoreCase(s3.toLowerCase()))
				{
					flag = false;
				}
			}
			for (String s4 : startswith)
			{
				if (!message.toLowerCase().startsWith(s4.toLowerCase()))
				{
					flag = false;
				}
			}
			for (String s5 : endswith)
			{
				if (!message.toLowerCase().endsWith(s5.toLowerCase()))
				{
					flag = false;
				}
			}
			
			if (reply.startsWith("~"))
			{
				reply.replaceFirst("~", "\u0001ACTION");
				reply = reply + "\u0001";
			}
			
			if (flag)
			{
				/*if (reply.equals(""))
				{
					IRCBot.getInstance().sendToIRC("PRIVMSG Strange :Strange: An error was found in chatmessage #" + i + " as no reply was found.");
					return false;
				}*/
				
				final List<Object> replies_ = replies;
				final int a = i;
				ThreadedProcess p = new ThreadedProcess() {
					
					@Override
					public void run() 
					{
						for (Object o : replies_)
						{
							if (o instanceof String)
							{
								sender.sendToChannel(o.toString());
							}
							else if (o instanceof Integer || o instanceof Double)
							{
								try 
								{
									Thread.sleep((long) (Double.parseDouble(o.toString()) * 1000L));
								} 
								catch (NumberFormatException e) 
								{
									IRCBot.getInstance().sendToIRC("PRIVMSG Strange :Strange: An error was found in chatmessage #" + a + " as delay could not be parsed.");
								} 
								catch (InterruptedException e) 
								{
									IRCBot.getInstance().sendToIRC("PRIVMSG Strange :Strange: An error was found in chatmessage #" + a + " as the reply thread was interupted!");
								}
							}
							else
							{
								IRCBot.alertRoots("%s: Something broke!");
							}
						}
					}
				};
				p.start();
				//sender.sendToChannel(reply);
				return true;
			}
			
		}
		return false;
	}
	
	public String replaceVariableForWorld(String originalWord, String var, String newvar)
	{
		String s = originalWord;
		if (originalWord.toLowerCase().contains("%" + var.toLowerCase()))
		{
			if (originalWord.lastIndexOf("%") > originalWord.indexOf("%")) //The word is has 2 % in is, with the var in middle
			{
				s = originalWord.replaceAll("%(?i)" + var + "%", newvar);
			}
			else
			{
				s = originalWord.replaceAll("%(?i)" + var, newvar);
			}
		}
		return s;
	}
}
