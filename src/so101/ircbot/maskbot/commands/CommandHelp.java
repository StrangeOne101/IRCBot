package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.registries.LanguageRegistry;

public class CommandHelp implements IBotCommand {

	public CommandHelp() 
	{
		LanguageRegistry.addDefaultLang("bot.command.help", "List of commands: author cookies dictionary config javascript math raw real recover reboot quit notes perm mute shutup sudo unmute channels games help say msg lang self .dex .bing .random .lagtest .pic .ping .5050 !mew !mcavatars");		
	}
	
	@Override
	public String getCommandName() 
	{
		return "help";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.help"));
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"?", "halp"};
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

}
