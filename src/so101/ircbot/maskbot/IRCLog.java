package so101.ircbot.maskbot;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IRCLog 
{
	public static IRCLog INSTANCE;
	private PrintStream out;
	
	public Thread thread;
	
	public long sleepState = 30 * 1000; //Seconds
	
	private static String currentLogFile;
	
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
			currentLogFile = "logs/log_" + dateFormat.format(date) + ".txt";
			out = new ConsolePrintStream(new FileOutputStream(file, true), System.out);
			System.setOut(out);
			//thread = new Thread(new ThreadedLogger());
			//thread.start();
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
		//IRCLog.forceSaveLog();
	}
	
	/*public class ThreadedLogger implements Runnable
	{

		@Override
		public void run() 
		{
			IRCLog.forceSaveLog();
			try 
			{
				Thread.sleep(sleepState);
			} 
			catch (InterruptedException e) 
			{
				IRCBot.alertRoots("%s: There was a thread interuption while sleeping in thread LOGGER");
			}
		}
		
	}
	
	public static void forceSaveLog()
	{
		try 
		{
			Writer output;
			output = new BufferedWriter(new FileWriter(currentLogFile));
			for (String s : IRCBot.getInstance().threadedLogs)
			{
				output.append(s);
				output.append(System.lineSeparator());
			}
			output.close();
			IRCBot.getInstance().threadedLogs.clear();
		} 
		catch (FileNotFoundException e) 
		{
			IRCBot.alertRoots("%s: There was a severe error where the log file cannot be saved because it does not exist.");
		}
		catch (IOException e)
		{
			IRCBot.alertRoots("%s: There was a severe error while saving to the log file that is unknown (IOException).");
		}
	}*/
	
	public class ConsolePrintStream extends PrintStream 
	{
        private final PrintStream second;

        public ConsolePrintStream(OutputStream main, PrintStream second) {
            super(main);
            this.second = second;
        }

        /**
         * Closes the main stream. 
         * The second stream is just flushed but <b>not</b> closed.
         * @see java.io.PrintStream#close()
         */
        @Override
        public void close() 
        {
            super.close();
            second.close();
        }

        @Override
        public void flush() {
            super.flush();
            second.flush();
        }

        @Override
        public void write(byte[] buf, int off, int len) {
            super.write(buf, off, len);
            second.write(buf, off, len);
        }

        @Override
        public void write(int b) {
            super.write(b);
            second.write(b);
        }

        @Override
        public void write(byte[] b) throws IOException {
            super.write(b);
            second.write(b);
        }
    }
}
