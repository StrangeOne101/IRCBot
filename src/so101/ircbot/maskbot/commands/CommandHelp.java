package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandHelp implements IBotCommand {

	@Override
	public String getCommandName() 
	{
		return "help";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		sender.sendToChannel("List of commands: author cookies dictionary config javascript math raw real recover reboot quit perm mute shutup sudo unmute channels games help say msg self .dex .bing .random .lagtest .pic .ping .5050 !mew !mcavatars");
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
