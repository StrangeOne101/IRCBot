package so101.ircbot.maskbot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import so101.ircbot.maskbot.handlers.ChatHandler;

public class CommandRegistry 
{
	/**List of registered commands*/
	protected static Map<String, IBotCommand> commandList = new HashMap<String, IBotCommand>();
	
	/**Registered command handlers*/
	public static List<ICommandHandler> commandHandlerList = new ArrayList<ICommandHandler>();
	
	/**List of commands to use in chat. E.g. Input = Maskbot, do you like Pie? Output = Yes :D*/
	public static List<String> chatCommands = new ArrayList<String>();
	
	public static ChatHandler chatHandler = new ChatHandler();
	
	public static boolean registerCommand(IBotCommand command)
	{
		if (!commandList.containsKey(command.getCommandName()))
		{
			commandList.put(command.getCommandName(), command);
			IRCBot.log("Command " + command.getCommandName().toUpperCase() + " registered", Log.INFO);
			if (!(command.getAliasis() == null || command.getAliasis() == new String[] {}))
			{
				for (int i = 0; i < command.getAliasis().length; i++)
				{
					commandList.put(command.getAliasis()[i], command);
					IRCBot.log("Command alias " + command.getAliasis()[i].toUpperCase() + " registered for command " + command.getCommandName().toUpperCase(), Log.INFO);
				}
			}
			return true;
		}
		IRCBot.log("Command " + command.getCommandName().toUpperCase() + " already registered!", Log.SEVERE);
		return false;
	}
	
	/**Register command handler to handle given commands. Command Handlers always run before seperate commands*/
	public static boolean registerCommandHandler(ICommandHandler command)
	{
		if (!commandHandlerList.contains(command))
		{
			commandHandlerList.add(command);
			IRCBot.log("Command handler " + command.getCommandHandlerName().toUpperCase() + " registered", Log.INFO);
			return true;
		}
		IRCBot.log("Command handler " + command.getCommandHandlerName().toUpperCase() + " already registered!", Log.SEVERE);
		return false;
	}
	
	/**Returns command of the given name, can be alias. Returns null if command not found.*/
	public static IBotCommand getCommand(String name)
	{
		
		if (commandList.containsKey(name))
		{
			return commandList.get(name);
		}
		return null;
	}
	
	public static void registerChatCommand(String command)
	{
		chatCommands.add(command);
	}
}
