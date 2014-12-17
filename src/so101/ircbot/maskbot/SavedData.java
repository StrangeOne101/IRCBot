package so101.ircbot.maskbot;

import so101.ircbot.maskbot.managers.DataManager;

public abstract class SavedData 
{
	public SavedData() 
	{
		DataManager.registerData(this);
	}
	
	public abstract Object saveData();
	
	public abstract String getDataTitle();
}
