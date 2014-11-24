package so101.ircbot.maskbot.handlers;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.PermissionsManager;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class PermHander implements ICommandHandler 
{

	@Override
	public String getCommandHandlerName() 
	{
		return "PermHandler";
	}

	@Override
	public boolean onCommand(String message, ChannelSender sender) 
	{
		if (Utils.hasCommandPrefix(sender, message))
		{
			message = Utils.formatCommandString(Utils.formatCommandPrefix(message));
			String[] args = message.split(" ");
			if (args.length > 0)
			{
				if (args[0].equals("perm") || args[0].equals("perms") || args[0].equals("permissions"))
				{
					if (PermissionsManager.permissionTable.containsKey(sender.senderName.toLowerCase()) && PermissionsManager.permissionTable.get(sender.senderName.toLowerCase()) >= 2)
					{
						if (args.length > 1)
						{
							if (args.length > 2)
							{
								if (Utils.isInteger(args[2]))
								{
									if (Integer.parseInt(args[2]) > PermissionsManager.permissionTable.get(sender.senderName))
									{
										sender.sendToChannel(sender.senderName + ": You can not set a permission level to something higher than your own");
										return true;
									}
									if (PermissionsManager.permissionTable.containsKey(args[1].toLowerCase()))
									{
										PermissionsManager.permissionTable.remove(args[1].toLowerCase());
									}
									PermissionsManager.permissionTable.put(args[1].toLowerCase(), Integer.parseInt(args[2]));
									sender.sendToChannel(sender.senderName + ": Permission level for " + args[1] + " set.");
								}
								else
								{
									sender.sendToChannel(sender.senderName + ": Invalid argument \"" + args[2] + "\"");
								}
							}
							else
							{
								if (PermissionsManager.permissionTable.containsKey(args[1].toLowerCase()))
								{
									sender.sendToChannel(sender.senderName + ": Permission level for " + args[1] + " is " + PermissionsManager.permissionTable.get(args[1].toLowerCase()));
								}
								else
								{
									sender.sendToChannel(sender.senderName + ": Permission level for " + args[1] + " not found");
								}
							}
						}
						else
						{
							sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getInstance().nick + " perms <user> [perm]\"");
						}
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Insufficient Privileges");
					}
					return true;
				}
			}
		}
		return false;
	}

}
