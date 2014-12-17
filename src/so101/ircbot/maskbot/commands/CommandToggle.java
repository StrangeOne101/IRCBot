package so101.ircbot.maskbot.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.JsonValue;

import so101.ircbot.maskbot.IBotCommand;
import so101.ircbot.maskbot.IRCBot;
import so101.ircbot.maskbot.SavedData;
import so101.ircbot.maskbot.Utils;
import so101.ircbot.maskbot.IRCBot.ChannelSender;
import so101.ircbot.maskbot.handlers.ChannelHandler;

public class CommandToggle implements IBotCommand 
{
	public List<ToggleOption> optionList = new ArrayList<ToggleOption>();
	public Map<String, Object> toggleOptions = new HashMap<String, Object>();
	
	abstract class ToggleOption
	{		
		public ToggleOption() 
		{
			toggleOptions.put(this.getOptionName(), this.defaultValue());
		}
		
		public abstract String getOptionName();
		public abstract void onToggle(Object value);
		public abstract Object defaultValue();
	}
	
	
	public CommandToggle()
	{
		optionList.add(new ToggleOption() {

			@Override
			public String getOptionName() 
			{
				return "debug";
			}

			@Override
			public void onToggle(Object value) 
			{
				IRCBot.getInstance().debugMode = Boolean.parseBoolean(value.toString());
			}

			@Override
			public Object defaultValue() 
			{
				return false;
			}
			
		});
		optionList.add(new ToggleOption() {
			
			public SavedData data = new SavedData() {

				@Override
				public Object saveData() 
				{
					return toggleOptions.get(getOptionName());
				}

				@Override
				public String getDataTitle() 
				{
					return "TestingMode";
				}
				
			};
			
			@Override
			public String getOptionName() 
			{
				return "testingmode";
			}

			@Override
			public void onToggle(Object value) 
			{
				boolean b = Boolean.parseBoolean(value.toString());
				if (b)
				{
					for (String channel : IRCBot.getInstance().currentChannels)
					{
						if (!IRCBot.getInstance().testingChannels.contains(channel))
						{
							ChannelHandler.leaveChannel(channel);
						}
					}
				}
				else
				{
					for (String channel : IRCBot.getInstance().channels)
					{
						if (!IRCBot.getInstance().currentChannels.contains(channel))
						{
							ChannelHandler.joinChannel(channel);
						}
					}
				}
			}

			@Override
			public Object defaultValue() 
			{
				return false;
			}
		});
	}
	
	@Override
	public String getCommandName() 
	{
		return "toggle";
	}

	@Override
	public boolean execute(String[] args, ChannelSender sender) 
	{
		if (args.length < 1 || args[0].toLowerCase().equals("help") || !toggleOptions.containsKey(args[0].toLowerCase()))
		{
			sender.sendToChannel(sender.senderName + ": Toggle options are DEBUG and TESTINGMODE");
			return true;
		}
		
		Object o = toggleOptions.get(args[1].toLowerCase());
		
		if (Utils.isInteger(args[1]))
		{
			if (o instanceof Integer || o instanceof Double || o instanceof Float)
			{
				if (args[1].contains("."))
				{
					toggleOptions.put(args[0].toLowerCase(), Double.parseDouble(args[1]));
					sender.sendToChannel(sender.senderName + ": " + args[0].toUpperCase() + " updated to " + args[1].toUpperCase());
					this.updateOption(args[0], args[1]);
				}
				else
				{
					toggleOptions.put(args[0].toLowerCase(), Integer.parseInt(args[1]));
					sender.sendToChannel(sender.senderName + ": " + args[0].toUpperCase() + " updated to " + args[1].toUpperCase());
					this.updateOption(args[0], args[1]);
				}
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Option type cannot be changed from integer/double!");
			}
		}
		else if (args[1].toLowerCase().equals("false") || args[1].toLowerCase().equals("true"))
		{
			if (o instanceof Boolean)
			{
				toggleOptions.put(args[0].toLowerCase(), Boolean.parseBoolean(args[1]));
				sender.sendToChannel(sender.senderName + ": " + args[0].toUpperCase() + " updated to " + args[1].toUpperCase());
				this.updateOption(args[0], args[1]);
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Option type cannot be changed from boolean!");
			}
		}
		else
		{
			String s = Utils.formatArrayToString(args);
			if (o instanceof String)
			{
				toggleOptions.put(args[0].toLowerCase(), s);
				sender.sendToChannel(sender.senderName + ": " + args[0].toUpperCase() + " updated to \"" + s + "\"");
				this.updateOption(args[0], args[1]);
			}
			else
			{
				sender.sendToChannel(sender.senderName + ": Option type cannot be changed from String!");
			}
		}
		
		return true;
	}

	@Override
	public String[] getAliasis() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean requirePrefix() 
	{
		return true;
	}

	@Override
	public int getPermissionLevel() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public void updateOption(String option, Object value)
	{
		for (int i = 0; i < optionList.size(); i++)
		{
			if (this.optionList.get(i).getOptionName().toLowerCase().equals(option.toLowerCase()))
			{
				this.optionList.get(i).onToggle(value);
				break;
			}
		}
	}
}
