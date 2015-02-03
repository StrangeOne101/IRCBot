package so101.ircbot.maskbot.commands;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.Utils;

public class CommandJavaScript implements IBotCommand 
{
	
	public ScriptEngine manager = new ScriptEngineManager().getEngineByName("js");
	public Writer writer = new StringWriter();
	
	public String curChannel;

	public CommandJavaScript() 
	{
		this.manager.getContext().setWriter(writer);
		try 
		{
			this.manager.eval("function getNick() {return \"" + IRCBot.getNick() + "\";}");
			this.manager.put("IRCBot", new JSAccessClass());
			this.manager.put("so101", "Permission denied.");
			//this.manager.eval("");
		} 
		catch (ScriptException e) 
		{
		}
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
			curChannel = sender.channelName;
			manager.eval(Utils.formatArrayToString(args));
			for (String s : writer.toString().split("\n"))
			{
				if (!writer.toString().equals(""))
				{
					sender.sendToChannel("> " + s);
				}
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
			if (e.getLocalizedMessage().split("\\:").length > 0)
			{
				s = e.getLocalizedMessage().split("\\:")[e.getLocalizedMessage().split("\\:").length - 1].split("\\(")[0];
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
	
	public class JSAccessClass
	{
		@Override
		public String toString() 
		{
			return IRCBot.getNick() + ", IRCBot. Developed by StrangeOne101, Nov 2014.";
		}
		
		public void send(String line)
		{
			IRCBot.getInstance().sendToIRC("PRIVMSG " + curChannel + " :" + line);
		}
		
		public void send(String line, String user)
		{
			IRCBot.getInstance().sendToIRC("PRIVMSG " + user + " :" + line);
		}
		
		public ChannelSender getRoot()
		{
			return IRCBot.getInstance().getRoot();
		}
		
		public String help()
		{
			return "Possible methods for IRCBot are send(line), send(line, user), getRoot() and help().";
		}
	}
}
