package so101.ircbot.maskbot;

import java.util.HashMap;
import java.util.Map;

import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class PermissionsManager 
{
	public static Map<String, Integer> permissionTable = new HashMap<String, Integer>();
	
	public static void setUserPermission(String nick, int perm)
	{
		if (permissionTable.containsKey(nick))
		{
			permissionTable.remove(nick);
		}
		permissionTable.put(nick, perm);
	}
	
	static {
		permissionTable.put("Strange", 4);
		permissionTable.put("StrangeOne101", 3);
	}
	
	public static boolean getUserHasPermission(ChannelSender sender, int perm)
	{
		int userPerm = 0;
		if (permissionTable.containsKey(sender.senderName.toLowerCase()))
		{
			userPerm = permissionTable.get(sender.senderName.toLowerCase());
		}
		if (userPerm < perm)
		{
			sender.sendToChannel(sender.senderName + ": Insufficient Privileges");
			return false;
		}
		return true;
	}
}
