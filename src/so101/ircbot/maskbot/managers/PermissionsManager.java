package so101.ircbot.maskbot.managers;

import java.util.HashMap;
import java.util.Map;

import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.registries.LanguageRegistry;

public class PermissionsManager 
{
	public static Map<String, Integer> permissionTable = new HashMap<String, Integer>();
	
	public static final String INVALID_PERM = "Insufficient Privileges";
	
	/**Set the permission level of a user*/
	public static void setUserPermission(String nick, int perm)
	{
		if (permissionTable.containsKey(nick))
		{
			permissionTable.remove(nick);
		}
		permissionTable.put(nick, perm);
	}
	
	static 
	{
		if (IRCBot.getNick().equalsIgnoreCase("MaskBot"))
		{
			permissionTable.put("Strange", 4);
			permissionTable.put("StrangeOne101", 3);
		}
	}
	
	/**Get if the sender specified has AT LEAST the specified permission. So a user with a permission of 2 would return true if asked for anything like 1, 2, 0, -1, etc, but not 3.*/
	public static boolean getUserHasPermission(ChannelSender sender, int perm)
	{
		int userPerm = 0;
		if (permissionTable.containsKey(sender.senderName.toLowerCase()))
		{
			userPerm = permissionTable.get(sender.senderName.toLowerCase());
		}
		if (userPerm < perm)
		{
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.common.noperm"));
			return false;
		}
		return true;
	}
	
	/**Returns the permission of a user*/
	public static int getUserPermission(ChannelSender sender)
	{
		int userPerm = 0;
		if (permissionTable.containsKey(sender.senderName.toLowerCase()))
		{
			userPerm = permissionTable.get(sender.senderName.toLowerCase());
		}
		return userPerm;
	}
}
