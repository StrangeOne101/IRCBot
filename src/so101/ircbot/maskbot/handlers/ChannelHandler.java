package so101.ircbot.maskbot.handlers;

import java.util.List;

import so101.ircbot.maskbot.ICommandHandler;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.managers.PermissionsManager;
import so101.ircbot.maskbot.Utils;

public class ChannelHandler implements ICommandHandler 
{
	
	@Override
	public String getCommandHandlerName() 
	{
		return "ChannelJoin|ChannelLeave";
	}

	public ChannelHandler() 
	{
		
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
				if (args[0].toLowerCase().equals("channel") || args[0].toLowerCase().equals("channels"))
				{
					if (args.length > 1 && (args[1].toLowerCase().equals("leave") || args[1].toLowerCase().equals("part")))
					{
						if (args.length < 3 || sender.channelName == "") //If in PM (channel name is nothing in PM), they must type argument
						{
							sender.sendToChannel(sender.senderName + ", please specify a channel to leave");
						}
						else if (args.length < 3) //Leave the channel specified
						{
							leaveChannel(args[2]);
						}
						else //Leave the current channel
						{
							leaveChannel(args[2]);
						}
					}
					else if (args.length > 1 && args[1].toLowerCase().equals("join"))
					{
						if (args.length > 2 && args[2] != "")
						{
							joinChannel(args[2]);
						}
						else
						{
							sender.sendToChannel(sender.senderName + ", please specify a channel to join");
						}
					}
					else if (args.length > 1 && args[1].toLowerCase().equals("add"))
					{
						if (PermissionsManager.permissionTable.containsKey(sender.senderName.toLowerCase()) && PermissionsManager.permissionTable.get(sender.senderName.toLowerCase()) >= 2)
						{
							if (args.length > 2 && args[2] != "")
							{
								joinChannel(args[2]);
								if (!IRCBot.getInstance().channels.contains(args[2]))
								{
									IRCBot.getInstance().channels.add(args[2]);
								}
							}
							else
							{
								sender.sendToChannel(sender.senderName + ", please specify a channel to join");
							}
						}
						else
						{
							sender.sendToChannel(sender.senderName + ": " + PermissionsManager.INVALID_PERM);
						}
					}
					else if (args.length > 1 && args[1].toLowerCase().equals("remove"))
					{
						if (PermissionsManager.permissionTable.containsKey(sender.senderName.toLowerCase()) && PermissionsManager.permissionTable.get(sender.senderName.toLowerCase()) >= 2)
						{
							if (args.length < 3 && sender.channelName == "") //If in PM (channel name is nothing in PM), they must type argument
							{
								sender.sendToChannel(sender.senderName + ", please specify a channel to leave");
							}
							else if (args.length < 3) //Leave the channel specified
							{
								leaveChannel(args[2]);
								if (IRCBot.getInstance().channels.contains(args[2]))
								{
									IRCBot.getInstance().channels.remove(args[2]);
								}
							}
							else //Leave the current channel
							{
								leaveChannel(args[2]);
								if (IRCBot.getInstance().channels.contains(args[2]))
								{
									IRCBot.getInstance().channels.remove(args[2]);
								}
							}
						}
						else
						{
							sender.sendToChannel(sender.senderName + ": " + PermissionsManager.INVALID_PERM);
						}
					}
					else if (args.length > 1 && args[1].toLowerCase().equals("list"))
					{
						String s1 = "";						
						List<String> list = IRCBot.getInstance().currentChannels;
						
						for (int i = list.size(); i > 0; i--)
						{
							if (i == list.size())
							{
								s1 = list.get(i - 1);
							}
							else if (i == list.size() - 1)
							{
								s1 = list.get(i - 1) + " and " + s1;
							}
							else
							{
								s1 = list.get(i - 1) + ", " + s1;
							}
						}
						int c = IRCBot.getInstance().currentChannels.size();
						if (c == 0)
						{
							sender.sendToChannel(sender.senderName + ": Connected to no channels");
						}
						else
						{
							sender.sendToChannel("Connected to " + c + " channels: " + s1);
						}
					}
					else
					{
						sender.sendToChannel(sender.senderName + ": Command usage is \"" + IRCBot.getNick() + " channels <join/leave/list>\"");
					}
					return true;
				}
				else if (args[0].toLowerCase().equals("leave") || args[0].toLowerCase().equals("part"))
				{
					if (args.length < 2 || sender.channelName == "") //If in PM (channel name is nothing in PM), they must type argument
					{
						sender.sendToChannel(sender.senderName + ", please specify a channel to leave");
					}
					else if (args.length < 2) //Leave the channel specified
					{
						leaveChannel(args[1]);
					}
					else //Leave the current channel
					{
						leaveChannel(args[1]);
					}
				}
				else if (args[0].toLowerCase().equals("join"))
				{
					if (args.length > 1 && args[1] != "")
					{
						joinChannel(args[1]);
					}
					else
					{
						sender.sendToChannel(sender.senderName + ", please specify a channel to join");
					}
				}
			}
			
		}
		return false;
	}
	
	/**Joins channel*/
	protected static void joinChannel(String channel)
	{
		String chan = channel;
		if (!channel.startsWith("#"))
		{
			chan = "#" + channel;
		}
		IRCBot.getInstance().sendToIRC("JOIN " + chan);
		if (!IRCBot.getInstance().currentChannels.contains(chan))
		{
			IRCBot.getInstance().currentChannels.add(chan);
		}
	}
	
	/**Leaves channel*/
	protected static void leaveChannel(String channel)
	{
		String chan = channel;
		if (!channel.startsWith("#"))
		{
			chan = "#" + channel;
		}
		IRCBot.getInstance().sendToIRC("PART :" + chan);
		if (IRCBot.getInstance().currentChannels.contains(chan))
		{
			IRCBot.getInstance().currentChannels.remove(chan);
		}
	}

}
