package so101.ircbot.maskbot.commands;

import java.util.Random;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandReply implements IBotCommand 
{
	public String command;
	public String[] reply;
	public boolean requirePrefix = false;
	
	public CommandReply(String command, String reply) 
	{
		this.command = command;
		this.reply = new String[] {reply};
	}
	
	public CommandReply(String command, String reply, boolean prefix) 
	{
		this.command = command;
		this.reply = new String[] {reply};
		this.requirePrefix = prefix;
	}
	
	public CommandReply(String command, String[] reply) 
	{
		this.command = command;
		this.reply = reply;
	}
	
	public CommandReply(String command, String[] reply, boolean prefix) 
	{
		this.command = command;
		this.reply = reply;
		this.requirePrefix = prefix;
	}
	
	@Override
	public String getCommandName() 
	{
		return this.command;
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		sender.sendToChannel(this.reply[new Random().nextInt(this.reply.length)]);
		return requirePrefix;
	}

	@Override
	public String[] getAliasis() 
	{
		return null;
	}

	@Override
	public boolean requirePrefix() 
	{
		return this.requirePrefix;
	}

	@Override
	public int getPermissionLevel() {
		// TODO Auto-generated method stub
		return -1;
	}

}
