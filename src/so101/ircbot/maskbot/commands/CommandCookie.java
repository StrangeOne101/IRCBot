package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.CommandRegistry;
import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.handlers.CookieHandler;

public class CommandCookie implements IBotCommand {

	@Override
	public String getCommandName() 
	{
		return "cookies";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		CookieHandler handler = (CookieHandler) CommandRegistry.commandHandlerList.get(0);
		if (args.length > 0)
		{
			if (handler.storedCookies.containsKey(args[0]))
			{
				sender.sendToChannel(sender.senderName + ": " + handler.storedCookies.get(args[0]) + " cookies");
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": User not found!");
			}
		}
		else
		{
			if (handler.storedCookies.containsKey(sender.senderName))
			{
				sender.sendToChannel(sender.senderName + ": " + handler.storedCookies.get(sender.senderName) + " cookies");
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": 0 cookies");
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
	public int getPermissionLevel() {
		// TODO Auto-generated method stub
		return 0;
	}

}
