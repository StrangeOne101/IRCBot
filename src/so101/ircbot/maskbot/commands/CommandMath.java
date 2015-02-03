package so101.ircbot.maskbot.commands;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;

public class CommandMath implements IBotCommand 
{
	public ScriptEngine manager = new ScriptEngineManager().getEngineByName("js");

	public CommandMath() 
	{
		manager.put("so101", null);
	}
	
	@Override
	public String getCommandName() 
	{
		return "math";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		Writer writer = new StringWriter();
		try 
		{
			manager.getContext().setWriter(writer);
			manager.eval("print(" + Utils.formatArrayToString(args) + ")");
			
			String s = writer.toString();
			if (s.equals("")) {s = "0";}
			sender.sendToChannel("> " + s);
			writer.flush();
			writer.close();
		} 
		catch (ScriptException e) 
		{
			String s = e.getLocalizedMessage();
			if (e.getLocalizedMessage().split("\\:").length > 2)
			{
				s = e.getLocalizedMessage().split("\\:")[2].split("\\(")[0];
			}
			
			sender.sendToChannel(sender.senderName + ": Parsing error - " + s);
		}
		catch (IOException e) 
		{
			sender.sendToChannel(sender.senderName + ": Something went wrong while getting return!");
		}
		return true;
	}

	@Override
	public String[] getAliasis() 
	{
		return new String[] {"maths"};
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() 
	{
		return 0;
	}

}
