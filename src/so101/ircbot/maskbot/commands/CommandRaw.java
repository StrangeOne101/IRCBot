package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.InputMessage;
import so101.ircbot.maskbot.Schedule;
import so101.ircbot.maskbot.Scheduler;

public class CommandRaw implements IBotCommand {
	
	@Override
	public String getCommandName() 
	{
		return "raw";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender)
	{
		if (args.length < 1)
		{
			sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " raw <command>\"");
			return true;
		}
		if (args[0].equals("debug"))
		{
			sender.sendToChannel("Debug: " + Scheduler.schedules.size() + ", " + Scheduler.latestMessages.size() + ", " + Scheduler.latestMessages.get(10).nick);
			return true;
		}
		String s = "";
		for (int i = 0; i < args.length; i++)
		{
			s = s + args[i] + " ";
		}
		
		Scheduler.schedules.add(new Schedule(sender) {
			
			@Override
			public boolean run() 
			{
				for (int i = 0; i < Scheduler.latestMessages.size(); i++)
				{
					InputMessage m = Scheduler.latestMessages.get(i);
					if (m.user.contains("esper.net"))
					{
						this.sender.sendToChannel(this.sender.senderName + ", returned with: " + m.message);
						return true;
					}
				}
				return false;
			}
		});
		sender.sendToServer(s);
		//IRCBot.getInstance().nextReturnFromServer = sender.senderName + ", return was: ";
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"dcmd"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 2;
	}

}
