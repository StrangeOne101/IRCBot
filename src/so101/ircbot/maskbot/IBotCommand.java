package so101.ircbot.maskbot;

public interface IBotCommand
{
	/**The registered name of the command. What is used to trigger command.*/
	public abstract String getCommandName();
	
	/**Execute the command with args and sender*/
	public abstract boolean execute(String args[], IRCBot.ChannelSender sender);
	
	/**Get other names for command. Usage is "return new String[] {"name1", "name2"}"*/
	public abstract String[] getAliasis();
	
	/**Whether the bot's name needs to be said at the start for the command to trigger.*/
	public boolean requirePrefix();
	
	/**Get what required permission level is needed to run command. 0 = default, 1 = something, 
	 * 2 = something, 3 = admin, 4 = root admin, 5 = bot*/
	public int getPermissionLevel();
}
