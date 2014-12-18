package so101.ircbot.maskbot.commands;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class CommandJavaScript implements IBotCommand 
{
	
	public ScriptEngine manager = new ScriptEngineManager().getEngineByName("js");
	public Writer writer = new StringWriter();

	public CommandJavaScript() 
	{
		this.manager.getContext().setWriter(writer);
	}
	
	@Override
	public String getCommandName() 
	{
		return "javascript";
	}

	@Override
	public boolean execute(String[] args, final ChannelSender sender) 
	{		
		try 
		{
			manager.eval(Utils.formatArrayToString(args));
			if (!writer.toString().equals(""))
			{
				sender.sendToChannel("> " + writer.toString());
			}
			writer.flush();
			writer.close();
			writer = new StringWriter();
			this.manager.getContext().setWriter(writer);
			//sender.sendToChannel(">" + o.toString());
			//writer.close();
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
		return new String[] {"js"};
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
