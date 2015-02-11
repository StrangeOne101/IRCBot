package so101.ircbot.maskbot.commands;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.managers.PermissionsManager;
import so101.ircbot.maskbot.registries.LanguageRegistry;

public class CommandLanguage implements IBotCommand 
{

	public CommandLanguage() 
	{
		LanguageRegistry.addDefaultLang("bot.command.lang.usage", "%s: Command usage is \"%n lang <add/edit/remove> <lang> <string>\". Modifies language strings used in the bot.");
		LanguageRegistry.addDefaultLang("bot.command.lang.incorrect1", "%s: Lang must be in format of bot.something.sub_something, etc.");
		LanguageRegistry.addDefaultLang("bot.command.lang.incorrect2", "%s: Lang must have at least 3 breaks! E.g. bot.command.dummy.help");
		LanguageRegistry.addDefaultLang("bot.command.lang.incorrect3", "%s: You must use lang in format of \"bot.custom.%n.<whatever>\"!");
		LanguageRegistry.addDefaultLang("bot.command.lang.incorrect4", "Sorry, %s. You don't have permission to use a \"common\", \"general\" or \"command\" sub-lang.");
		LanguageRegistry.addDefaultLang("bot.command.lang.incorrect5", "Sorry, %s. You don't have permission to edit %1 lang!");
		LanguageRegistry.addDefaultLang("bot.command.lang.success", "%s: Lang added.");
		
	}
	
	@Override
	public String getCommandName() 
	{
		return "language";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length == 0 || args[0].equalsIgnoreCase("help") || args.length <= 2)
		{
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.lang.usage"));
		}
		else if (args[0].equalsIgnoreCase("add"))
		{
			if (validateLang(args[1], sender))
			{
				String s = Utils.formatArrayToString(args).replaceFirst(args[0] + " ", "").replaceFirst(args[1] + " ", "");
				LanguageRegistry.addLang(args[1].toLowerCase(), s);
				sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.lang.success"));
			}
		}
		else if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("edit"))
		{
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.common.wip"));
		}
		else
		{
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.lang.usage"));
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"lang"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 1;
	}
	
	public boolean validateLang(String lang, ChannelSender sender)
	{
		String[] s = lang.split("\\.");
		if (!lang.contains(".") || (!s[0].equalsIgnoreCase("bot") && !PermissionsManager.getUserHasPermission(sender, 3)))
		{
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.lang.incorrect1"));
		}
		else if (s.length <= 2)
		{
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.lang.incorrect2"));
		}
		else if (!PermissionsManager.getUserHasPermission(sender, 1) && (!s[0].equalsIgnoreCase("bot") || !s[1].equalsIgnoreCase("custom") || !s[2].equalsIgnoreCase(sender.senderName)))
		{
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.lang.incorrect3"));
		}
		else if (PermissionsManager.getUserPermission(sender) == 2 && (s[1].equalsIgnoreCase("common") || s[1].equalsIgnoreCase("command") || s[1].equalsIgnoreCase("general")))
		{
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.lang.incorrect4"));
		}
		else if (PermissionsManager.getUserPermission(sender) == 2 && s[1].equalsIgnoreCase("custom") && !s[2].equalsIgnoreCase(sender.senderName))
		{
			String s1 = s[2] + "'";
			s1 = s1.endsWith("s") ? "" : "s";
			sender.sendToChannel(LanguageRegistry.getLangForString("bot.command.lang.incorrect4").replaceFirst("%1", s1));
		}
		else
		{
			return true;
		}
		return false;
	}

}
