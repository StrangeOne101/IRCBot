package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.PermissionsManager;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandRecover implements IBotCommand 
{

	@Override
	public String getCommandName() 
	{
		return "recover";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (sender.senderName.equals("Strange"))
		{
			sender.sendToChannel(sender.senderName + ": Sorry about that. Permission restored.");
			if (PermissionsManager.permissionTable.containsKey(sender.senderName))
			{
				PermissionsManager.permissionTable.remove(sender.senderName);
			}
			PermissionsManager.permissionTable.put(sender.senderName, 4);
		}
		else if (sender.senderName.equals("StrangeOne101") || sender.senderName.equals("Anna_28"))
		{
			sender.sendToChannel(sender.senderName + ": Sorry about that. Permission restored.");
			if (PermissionsManager.permissionTable.containsKey(sender.senderName))
			{
				PermissionsManager.permissionTable.remove(sender.senderName);
			}
			PermissionsManager.permissionTable.put(sender.senderName, 3);
		}
		else
		{
			sender.sendToSender(sender.senderName + ": Denied.");
		}
		return true;
	}

	@Override
	public String[] getAliasis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		return Integer.MIN_VALUE;
	}

}
