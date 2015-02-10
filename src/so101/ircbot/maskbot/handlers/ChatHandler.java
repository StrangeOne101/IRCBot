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
			String curString = message;
			String curFormat = "";
			String thingToLookFor = "";
			String reply = "";
			boolean flag = true;
			boolean flag1 = false;
			List<String> contains = new ArrayList<String>();
			List<String> equals = new ArrayList<String>();
			List<String> startswith = new ArrayList<String>();
			List<String> endswith = new ArrayList<String>();
			List<Object> replies = new ArrayList<Object>();
			List<String> options = new ArrayList<String>();
			List<ChatArgument> arguments = new ArrayList<ChatArgument>();
			for (String s1 : args)
			{
				if ((s1.startsWith("-") && !s1.equals("-"))|| args[args.length - 1].equals(s1))
				{
					if (args[args.length - 1].equals(s1))
					{
						thingToLookFor = thingToLookFor.equals("") ? s1 : thingToLookFor + " " + s1;
					}
					else
					{
						if (s1.startsWith("-a"))
						{
							s1 = s1.replaceFirst("-a", "");
							if (!(s1.contains("[") && s1.contains("]")))
							{
								IRCBot.getInstance().getRoot().sendToSender(IRCBot.getInstance().getRoot().senderName + ": An error was detected while parsing chat command " + i + " - Argument -a in invalid format!");
								break;
							}
							s1 = s1.replaceFirst("\\[", ""); //Remove left bracket
							String s2 = s1.split("\\]")[0]; //Get everything in between
							//s1 = s1.split("\\]")[1]; //Whats left
							s1 = s1.replaceFirst("\\]", ""); //Whats left
							String s3 = "";
							if (s2.contains("-")) //If middle contains -
							{
								int i1 = Integer.parseInt(s2.split("\\-")[0]); //Left hand side of -
								int i2 = Integer.parseInt(s2.split("\\-")[1]); //Right hand side of -
								if (i1 > i2) //Switch ints if the first is greater than the other
								{
									int i3 = i2; //Create dummy
									i2 = i1; //Set second to first
									i1 = i3; //Set first to dummy (value of second)
								}
								for (int i4 = i1; i4 < i2; i4++) //Loop through arguments
								{
									if (args.length <= i4)
									{
										break;
									}
									else if (i4 >= 0)
									{
										s3 = s3 + (i4 == i1 ? "" : " ") + args[i4]; //Add the arguments
									}
								}								
							}
							else if (s2.contains("+")) //If middle contains +
							{
								int i1 = Integer.parseInt(s2.split("\\+")[0]); //Left hand side of +
								for (int i4 = i1; i4 < args.length; i4++) //Loop through arguments
								{
									if (i4 >= 0)
									{
										s3 = s3 + (i4 == i1 ? "" : " ") + args[i4];
									}
								}
							}
							else
							{
								s3 = args[Integer.parseInt(s2)];
							}
							
							if (flag1) //If post-arguments
							{
								s1 = s3;
							}
							else //If pre-arguments
							{
								curString = s3;
							}
						}
						else
						{
							curString = message;
						}
					}
					
					if (!curFormat.equals(""))
					{
						switch (curFormat)
						{
						case "-c":
							//contains.add(thingToLookFor);
							arguments.add(new ChatArgument(thingToLookFor, ArgumentType.CONTAINS, curString));
							break;
						case "-e":
							//equals.add(thingToLookFor);
							arguments.add(new ChatArgument(thingToLookFor, ArgumentType.EQUALS, curString));
							break;
						case "-s":
							//startswith.add(thingToLookFor);
							arguments.add(new ChatArgument(thingToLookFor, ArgumentType.STARTSWITH, curString));
							break;
						case "-n":
							//endswith.add(thingToLookFor);
							arguments.add(new ChatArgument(thingToLookFor, ArgumentType.ENDSWITH, curString));
							break;
						case "-r":
							//replies.add(new String(thingToLookFor));
							arguments.add(new ChatArgument(thingToLookFor, ArgumentType.REPLY, curString));
							flag1 = true;
							break;
						case "-o":
							//reply = thingToLookFor + args[args.length - 1];	
							//options.add(thingToLookFor);
							arguments.add(new ChatArgument(thingToLookFor, ArgumentType.OPTION, curString));
							flag1 = true;
							break;
						case "-d":
							//replies.add(new Double(thingToLookFor));
							arguments.add(new ChatArgument(thingToLookFor, ArgumentType.DELAY, curString));
							flag1 = true;
							break;
						}
						if (curFormat.startsWith("-a"))
						{
							arguments.add(new ChatArgument(thingToLookFor, ArgumentType.ARGS, curString));
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
			/*if (IRCBot.getInstance().debugMode)
			{
				IRCBot.alertRoots(contains.toString());
				IRCBot.alertRoots(equals.toString());
				IRCBot.alertRoots(startswith.toString());
				IRCBot.alertRoots(endswith.toString());
			}*/
			/*for (String s2 : contains)
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
			}*/
			
			for (ChatArgument a : arguments)
			{
				if (!a.isChatArgumentFound())
				{
					flag = false;
				}
				if (a.enumtype == ArgumentType.REPLY)
				{
					if (a.text.startsWith("~"))
					{
						a.text.replaceFirst("~", "\u0001ACTION");
						a.text = a.text + "\u0001";
					}
				}
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
				final List<ChatArgument> l = arguments;
				ThreadedProcess p = new ThreadedProcess() {
					
					@Override
					public void run() 
					{
						for (ChatArgument a : l)
						{
							if (a.enumtype == ArgumentType.REPLY)
							{								
								sender.sendToChannel(a.text);
							}
							else if (a.enumtype == ArgumentType.DELAY)
							{
								try 
								{
									Thread.sleep((long) (Double.parseDouble(a.text) * 1000L));
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
							/*else
							{
								IRCBot.alertRoots("%s: Something broke!");
							}*/
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
	
	public class ChatArgument
	{
		private ArgumentType enumtype;
		private String text;
		private String searchText;
		
		public ChatArgument(String string, ArgumentType type, String searchText) 
		{
			this.text = string;
			this.enumtype = type;
			this.searchText = searchText;
		}
		
		public void setString(String s)
		{
			this.text = s;
		}
		
		public boolean isPreArgument()
		{
			return this.enumtype.equals(ArgumentType.CONTAINS) || this.enumtype.equals(ArgumentType.STARTSWITH) || this.enumtype.equals(ArgumentType.ENDSWITH) || this.enumtype.equals(ArgumentType.EQUALS) ;
		}
		
		public boolean isPostArgument()
		{
			return this.enumtype.equals(ArgumentType.DELAY) || this.enumtype.equals(ArgumentType.OPTION) || this.enumtype.equals(ArgumentType.REPLY);
		}
		
		public boolean isChatArgumentFound()
		{
			if (this.enumtype.equals(ArgumentType.CONTAINS) && this.searchText.toLowerCase().contains(text.toLowerCase()))
			{
				return true;
			}
			else if (this.enumtype.equals(ArgumentType.STARTSWITH) && this.searchText.toLowerCase().startsWith(text.toLowerCase()))
			{
				return true;
			}
			else if (this.enumtype.equals(ArgumentType.ENDSWITH) && this.searchText.toLowerCase().endsWith(text.toLowerCase()))
			{
				return true;
			}
			else if (this.enumtype.equals(ArgumentType.EQUALS) && this.searchText.toLowerCase().equals(text.toLowerCase()))
			{
				return true;
			}
			else if (this.isPostArgument())
			{
				return true;
			}
			return false;
		}
	}
	
	public enum ArgumentType
	{
		CONTAINS,STARTSWITH,ENDSWITH,EQUALS,REPLY,OPTION,DELAY,ARGS;
	}
}