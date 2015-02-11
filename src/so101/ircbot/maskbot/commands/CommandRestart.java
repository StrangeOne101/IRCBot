package so101.ircbot.maskbot.commands;

import java.io.IOException;

import so101.ircbot.maskbot.BotRestarter;
import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.registries.LanguageRegistry;

public class CommandRestart implements IBotCommand 
{

	@Override
	public String getCommandName() 
	{
		return "restart";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		sender.sendToChannel(LanguageRegistry.getLangForString("bot.general.reload"));
		try 
		{
			BotRestarter.restartApplication(null);
		} 
		catch (IOException e) 
		{
			sender.sendToChannel("Error: Failed to restart bot");;
		}
		return true;
	}

	@Override
	public String[] getAliasis() {
		
		return new String[] {"reboot", "healthyself", "reload"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		// TODO Auto-generated method stub
		return 2;
	}

}
