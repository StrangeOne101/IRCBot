package so101.ircbot.maskbot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IRCLog 
{
	public static IRCLog INSTANCE;
	private PrintStream out;
	
	public IRCLog()
	{
		INSTANCE = this;
		
		try 
		{
			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
			Date date = new Date();
			File file = new File("logs/log_" + dateFormat.format(date) + ".txt");
			File dir = new File("logs/");
			dir.mkdirs();
			file.createNewFile();
			out = new PrintStream(new FileOutputStream(file, true));
			System.setOut(out);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{  
			e.printStackTrace();  
		}
	}
	
	public void shutdownLogger()
	{
		if (this.out != null)
		{
			this.out.close();
		}
	}
}
