package so101.ircbot.maskbot.handlers;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.PermissionsManager;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class GeneralCommandHandler implements ICommandHandler {

	@Override
	public String getCommandHandlerName() 
	{
		return "Say|Msg|Self Handler";
	}

	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		if (Utils.hasCommandPrefix(sender, message) && PermissionsManager.getUserHasPermission(sender, 0))
		{
			message = Utils.formatCommandPrefix(message);
			String[] args = message.split(" ");
			if (args.length > 0)
			{
				String arg0 = Utils.formatCommandString(args[0]);
				if (args.length > 1)
				{
					//args[1] = Utils.formatCommandString(args[1]);
				}
				
				if (arg0.equals("say") || arg0.equals("shout"))
				{
					if (args.length > 1)
					{
						String s = "";
						for (int i = 1; i < args.length; i++)
						{
							s = s + args[i] + " ";
						}
						s.replaceAll(":", "\\:");
						sender.sendToChannel(s);
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + "\" say <message>");
					}
					return true;
				}
				else if (arg0.equals("msg") || arg0.equals("message"))
				{
					if (args.length > 1)
					{
						String s = "";
						for (int i = 2; i < args.length; i++)
						{
							s = s + args[i] + " ";
						}
						s.replaceAll(":", "\\:");
						sender.sendToServer("PRIVMSG " + args[1] + " :" + s);
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + "\" msg <user> <message>");
					}
					return true;
				}
				else if (arg0.equals("self") || arg0.equals("action"))
				{
					if (args.length > 1)
					{
						String s = "";
						for (int i = 1; i < args.length; i++)
						{
							s = s + args[i] + " ";
						}
						s.replaceAll(":", "\\:");
						sender.sendToChannel("\u0001ACTION " + s);
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + "\" self <message>");
					}
					return true;
				}
			}
		}
		return false;
	}

}
