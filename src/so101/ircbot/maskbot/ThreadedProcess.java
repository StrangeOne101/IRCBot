package so101.ircbot.maskbot;

public abstract class ThreadedProcess implements Runnable
{
	/**The local thread*/
	private Thread thread;
	
	@Override
	/**What code to run when the process starts*/
	public abstract void run();
	
	/**Start the process */
	public void start()
	{
		thread = new Thread(this);
		thread.start();
	}
	
	/**Returns thread.*/
	public Thread getThread()
	{
		return thread;
	}
}
