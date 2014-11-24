package so101.ircbot.maskbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import so101.ircbot.maskbot.commands.*;
import so101.ircbot.maskbot.commands.dex.Pokedex;
import so101.ircbot.maskbot.games.MapGame;
import so101.ircbot.maskbot.games.Player;
import so101.ircbot.maskbot.handlers.ChannelHandler;
import so101.ircbot.maskbot.handlers.ChatHandler;
import so101.ircbot.maskbot.handlers.CookieHandler;
import so101.ircbot.maskbot.handlers.GeneralCommandHandler;
import so101.ircbot.maskbot.handlers.MapGameHandler;
import so101.ircbot.maskbot.handlers.MuteHandler;
import so101.ircbot.maskbot.handlers.PermHander;

public class IRCBot
{
	protected static IRCBot instance;
	
	public String nick = "MaskBot";
	private String username = "MaskBot|StrangeBots";
	//private String password = "";//"StrangeBotCo";
	private String host = "irc.esper.net";
	
	protected BotSettings management;
	
	protected List<String> threadedLogs = new ArrayList<String>();
	
	private int port = 6667;
	
	private static Socket chatSocket;
	private static BufferedReader fromServer;
	private static PrintWriter toServer;
	
	public List<Runnable> onLoadThreads = new ArrayList<Runnable>();
	
	public boolean enabled = true;
	private boolean shutdown = false;
	private boolean connected = false;
	
	public static Level DEBUG = new Level("DEBUG", -1) {private static final long serialVersionUID = 1L;};
	private Level LOG = new Level("LOG", -2) {private static final long serialVersionUID = 36L;};
	
	public String nextReturnFromServer;
	
	public List<String> mutedChannels = new ArrayList<String>();
	public List<String> channels = new ArrayList<String>();
	
	public List<String> currentChannels = new ArrayList<String>();
	
	public boolean debugMode = false;
	
	public String LASTUSEDCHANNEL;
	
	public static void main(String[] args) 
	{
		if (args.length > 0)
		{
			
		}
		new IRCBot();
	}
	
	/**Class to allow easy sending of info to specified channel*/
	public class ChannelSender
	{
		public String channelName;
		public String senderName;
		public void sendToChannel(String line)
		{
			if (channelName == "")
			{
				sendToIRC("PRIVMSG " + senderName + " :" + line);
				return;
			}
			sendToIRC("PRIVMSG " + this.channelName +" :" + line);
		}
		
		public void sendToServer(String line)
		{
			sendToIRC(line);
		}
		
		public void sendToSender(String line)
		{
			sendToIRC("PRIVMSG " + senderName + " :" + line);
		}
	}
	
	/**Main Bot Class*/
	public IRCBot() 
	{
		this.instance = this;
		this.management = new BotSettings();
		new IRCLog();
		IRCBot.log("Loading data from config", Level.INFO);
		ConfigSettings.loadData();
		
		if (!this.enabled)
		{
			return;
		}
		IRCBot.log("Data loaded.", Level.INFO);
		IRCBot.log("Starting IRC Bot MaskBot", Level.INFO);
		this.connect();
		IRCBot.log("Starting local thread", Level.INFO);
		IRCBot.log("Just kidding, local thread removed for testing", DEBUG);
		//this.localThread = new Thread(this);
		//this.localThread.setDaemon(true);
		//this.localThread.start();
		
		IRCBot.log("Giving espernet cresidentials", Level.INFO);
		//sendToIRC("PASS " + password);
		sendToIRC("NICK " + nick);
		sendToIRC("USER " + username + " 0 * : Strange's Mask Bot");
		
		IRCBot.log("Initing Map Game Data", Level.INFO);
		new MapGame();
		
		IRCBot.log("Loading Dictionary Data", Level.INFO);
		DictionaryManager.loadData();
		IRCBot.log("Dictionary Data Loaded.", Level.INFO);
		
		IRCBot.log("Registering Commands", Level.INFO);
		//CommandRegistry.registerCommand(new CommandJoin());
		CommandRegistry.registerCommand(new CommandQuit());
		CommandRegistry.registerCommand(new CommandReply("peeka", "Boo!"));
		CommandRegistry.registerCommand(new CommandReply("pika", "chuuuuuuuu!"));
		CommandRegistry.registerCommand(new CommandReply("penis", "attack"));
		CommandRegistry.registerCommand(new CommandReply("pi", "3.1415927"));
		CommandRegistry.registerCommand(new CommandReply("beep", "Boop!"));
		CommandRegistry.registerCommand(new CommandReply("MaskBot play", "If you're looking to play a game, try \"MaskBot games play <game>\""));
		CommandRegistry.registerCommand(new CommandReply(".bing", "You're trying to use bing... You know that?"));
		CommandRegistry.registerCommand(new CommandReply(".squid", "So you like squids, eh? http://i.imgur.com/daYZgQL.jpg"));
		CommandRegistry.registerCommand(new Command5050());
		CommandRegistry.registerCommand(new CommandSay());
		//CommandRegistry.registerCommand(new CommandLeave());
		CommandRegistry.registerCommand(new CommandGame());
		CommandRegistry.registerCommand(new CommandGames());
		CommandRegistry.registerCommand(new CommandHelp());
		CommandRegistry.registerCommand(new CommandRaw());
		CommandRegistry.registerCommand(new CommandDex());
		CommandRegistry.registerCommand(new CommandCookie());
		CommandRegistry.registerCommand(new CommandRestart());
		CommandRegistry.registerCommand(new CommandRandom());
		CommandRegistry.registerCommand(new CommandDebug());
		CommandRegistry.registerCommand(new CommandRecover());
		CommandRegistry.registerCommand(new CommandReal());
		CommandRegistry.registerCommand(new CommandDictionary());
		CommandRegistry.registerCommand(new CommandCommands());
		
		CommandRegistry.registerCommandHandler(new CookieHandler());
		CommandRegistry.registerCommandHandler(new MuteHandler());
		CommandRegistry.registerCommandHandler(new ChannelHandler());
		CommandRegistry.registerCommandHandler(new GeneralCommandHandler());
		CommandRegistry.registerCommandHandler(new PermHander());
		CommandRegistry.registerCommandHandler(new MapGameHandler());
		CommandRegistry.registerCommandHandler(new ChatHandler());
		
		IRCBot.log("Grabbing Pokedex Data", Level.INFO);
		Pokedex.fetchData(null);
		IRCBot.log("Data mapped.", Level.INFO);
		
		IRCBot.log("Attempting to get info from server...", Level.INFO);
		this.run();

	}
	
	/** Get IRC Bot instance from static*/
	public static IRCBot getInstance()
	{
		return IRCBot.instance;
	}
	
	/**Once fully connected to espernet, join channels*/
	public void onConnect() 
	{
		for (Runnable r : this.onLoadThreads)
		{
			r.run();
		}
		
		for (String s : this.channels)
		{
			IRCBot.log("Joining " + s, Level.INFO);
			this.sendToIRC("JOIN " + s);
		}
		IRCLog.forceSaveLog();
		//ConfigSettings.testYaml();
		
	}
	
	public void onDisconnect()
	{
		IRCBot.log("Saving Data", Level.INFO);
		ConfigSettings.saveData();
		DictionaryManager.saveData();
		IRCBot.log("Data Saved.", Level.INFO);
		
	}

	/**Connect to espernet*/
	private void connect()
	{
		try
		{
			IRCBot.log("Connecting to espernet", Level.INFO);
			chatSocket = new Socket(host, port);
			fromServer = new BufferedReader(new InputStreamReader(chatSocket.getInputStream()));
            toServer = new PrintWriter(new OutputStreamWriter(chatSocket.getOutputStream()));
		}
		catch(IOException e)
		{
			IRCBot.log("Connection refused to: " + host + ":" + port, Level.SEVERE);
		}
	}
	
	public String getConsoleInput()
	{
		try{
		    BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		    String s = bufferRead.readLine();
	 
		    return s;
		}
		catch(IOException e)
		{
			return null;
		}
	}

	/**Send to espernet*/
	public void sendToIRC(String line)
	{
		IRCBot.toServer.print(line + "\r\n");
		IRCBot.toServer.flush();
		IRCBot.log("Bot --> Server: " + line, LOG);
	}

	/**Close current connections*/
	public void closeConnections()
	{
		IRCBot.log("Closing connections, exiting.", Level.INFO);
		this.sendToIRC("EXIT :QUIT Bot closed.");
		
		try
		{
			chatSocket.close();
			fromServer.close();
			toServer.close();
		}
		catch(IOException e)
		{
			IRCBot.log("Error occured while closing connections: " + e.toString(), Level.SEVERE);
		}
	
		chatSocket = null;
		fromServer = null;
		toServer = null;
		connected = false;
		shutdown = true;
		IRCBot.log("IRC Connections quit.", Level.INFO);
		IRCLog.INSTANCE.shutdownLogger();
	}

	public void run() 
	{
		try
		{
			if (!shutdown)
			{
				String line = fromServer.readLine();
				if (line != null)
				{
					processLineFromServer(line);
					this.threadedLogs.add(line);
				}
				else
				{
					// a null line indicates that our server closed the connection
					IRCBot.log("READ a null line", Level.WARNING);
					IRCBot.log("That means server has closed the connection", Level.WARNING);
					IRCBot.log("Or something wrong happened in the network", Level.WARNING);
					closeConnections();
						
					try
					{
						Thread.sleep(5000);
					}
					catch(InterruptedException e)
					{
					}
				
					enabled = false;
					//threadOn = false;
					// Now let's connect again
					BotRestarter.restartApplication(new Runnable() {

						@Override
						public void run() 
						{
							
						}});
				}
			}
		}	
		catch(IOException e)
		{
			System.out.println("Read Exception from Server. Exception is something like:");
			System.out.println(e);
			enabled = false;
		}
		try 
		{
			Thread.sleep(100L);
		} 
		catch (InterruptedException e) {}
			
			
		while (enabled && !shutdown)
		{		
			run();
		}
		if (connected)
		{
			IRCBot.log("System Shutting Down", Level.INFO);
			this.closeConnections();
		}
		return;		
	}

	public void processLineFromServer(String line)
	{
		IRCBot.log("Server --> Bot: " + line, LOG);
		
		IRCParser parser = new IRCParser(line);
		String command = parser.getCommand();
		InputMessage m = new InputMessage();
		m.channel = parser.getMiddle();
		m.message = parser.getTrailing();
		m.nick = parser.getNick();
		m.user = parser.getUser();
		Scheduler.latestMessages.add(m);
		Scheduler.run();
		if (command.equals("PING"))
		{
			this.sendToIRC("PONG :" + parser.getTrailing());
		}
		else if (command.equals("NICK") && parser.nick.equals(this.nick))
		{
			this.nick = parser.getTrailing();
		}
		else if (command.equals("INVITE"))
		{
			int perm = 0;
			if (PermissionsManager.permissionTable.containsKey(parser.getNick().toLowerCase()))
			{
				perm = PermissionsManager.permissionTable.get(parser.getNick().toLowerCase());
			}
			
			if (perm >= 2)
			{
				if (!IRCBot.getInstance().channels.contains(parser.getTrailing()))
				{
					IRCBot.getInstance().channels.add(parser.getTrailing());
				}
			}	
			
			IRCBot.getInstance().sendToIRC("JOIN " + parser.getTrailing());
			if (!IRCBot.getInstance().currentChannels.contains(parser.getTrailing()))
			{
				IRCBot.getInstance().currentChannels.add(parser.getTrailing());
			}
		}
		else if (command.equals("PRIVMSG"))
		{
			String destination = parser.getMiddle();
			
			String messageIRC = parser.getTrailing();
			String fullMessage = messageIRC;
			String sender = parser.getNick();
			//Whether commands that need a prefix will work.
			boolean gotPrefix = false;
			String[] tempArgs = messageIRC.toLowerCase().split(" ");
			if (destination.startsWith("#") && tempArgs[0].startsWith(this.nick.toLowerCase()))
		   	{
				//If in channel and user uses prefix, then enable
				messageIRC = messageIRC.replaceFirst(messageIRC.split(" ")[0] + " ", "");
				gotPrefix = true;
		   	}
			else if (destination.startsWith(this.nick))
			{
				//If user is in PRIVMSG, then prefixes arent required
				//IRCBot.log("TEST WORKED", DEBUG);
				gotPrefix = true;
			}
			ChannelSender csender = new ChannelSender();
			csender.channelName = !destination.equals(this.nick) ? destination : parser.getNick();
			csender.senderName = parser.getNick();
			this.LASTUSEDCHANNEL = csender.channelName;
			try
			{
			//Run message through command handlers to see if it gets picked up
			//Used for both regular things and complex commands
			for (int i = 0; i < CommandRegistry.commandHandlerList.size(); i++)
			{
				if (CommandRegistry.commandHandlerList.get(i).onCommand(fullMessage, csender))
				{
					return;
				}
			}
			
			//Run through commands
			if (CommandRegistry.getCommand(Utils.formatCommandString(messageIRC.toLowerCase().split(" ")[0])) != null && (CommandRegistry.getCommand(Utils.formatCommandString(messageIRC.split(" ")[0]).toLowerCase()).requirePrefix() == gotPrefix || gotPrefix))
			{
				String cmd = Utils.formatCommandString(messageIRC.split(" ")[0]);
				String temp = messageIRC.replaceFirst(cmd, "");
				temp = temp.startsWith(" ") ? temp.replaceFirst(" ", "") : temp;
				String[] a = temp.split(" ").length == 1 && temp.split(" ")[0].equals("") ? new String[] {} :  temp.split(" ");
				cmd = cmd.toLowerCase();
				IRCBot.log("\"" + messageIRC + "\"", DEBUG);
				
				if (! PermissionsManager.getUserHasPermission(csender, CommandRegistry.getCommand((cmd)).getPermissionLevel()))
				{
					return;
				}
				
				if (CommandRegistry.getCommand((cmd)).execute(a, csender))
				{
					return;
				}
			}
			}
			catch (Exception e) //Try catch random crashes
			{
				IRCBot.log("WARNING: ERROR FOUND. REBOOTING AND TRYING TO REPORT BACK", Level.SEVERE);
				IRCBot.getInstance().sendToIRC("PRIVMSG " + this.LASTUSEDCHANNEL + " :ERROR: Crash detected while processing command!! Log saved to CRASH.txt");
				DateFormat dateFormat = new SimpleDateFormat("HH:mma (dd/MM/yy)");
				Date date = new Date();
				IRCBot.getInstance().sendToIRC("PRIVMSG Strange :Strange: a crash was deteced at " + dateFormat.format(date));
				IRCBot.getInstance().sendToIRC("PRIVMSG Strange :" + e.toString());
				IRCBot.getInstance().sendToIRC("PRIVMSG Strange :" + e.getStackTrace()[0]);
				IRCBot.getInstance().sendToIRC("PRIVMSG Strange :" + e.getStackTrace()[1]);
				IRCBot.getInstance().sendToIRC("PRIVMSG Strange :" + e.getStackTrace()[2]);
				File file = new File("CRASH.txt");
				if (!file.exists()) 
				{
					try {file.createNewFile();} catch (IOException e1) {}
				}
				try 
				{
					PrintWriter writer = new PrintWriter(file);
					e.printStackTrace(writer);
					writer.close();
				} catch (IOException e1) {}
				e.printStackTrace();
				IRCLog.forceSaveLog();
			}
			boolean flag = false;
		}
		else
		{
			if (line.endsWith("+i") && line.contains("MODE") && line.contains(this.nick))
			{
				this.connected = true;
				this.onConnect();
				
				return;
			}
		}
	}

	/**Log line with valid level*/
	public static void log(String line, Level level)
	{
		System.out.println("[" + level.toString() + "] " + line);
	}
	
	/**Returns the nickname of the bot.*/
	public static String getNick()
	{
		return (String) IRCBot.getInstance().management.getBotGlobalVariable("NICK").toString();
	}
	
	/**Alerts all root users the following String*/
	public static void alertRoots(String string)
	{
		for (String s : PermissionsManager.permissionTable.keySet())
		{
			if (PermissionsManager.permissionTable.get(s) >= 4)
			{
				String s1 = string.replaceAll("%s", s);
				IRCBot.getInstance().sendToIRC("PRIVMSG " + s + " :" + s1);
			}
		}
	}
}
