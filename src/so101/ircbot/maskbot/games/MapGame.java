package so101.ircbot.maskbot.games;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MapGame 
{
	public Map<String, Player> playerFiles = new HashMap<String, Player>();
	public Map<Integer, String> inputRegistry = new HashMap<Integer, String>();
	
	public static MapGame instance;
	
	
	public String[] map;
	
	public MapGame() 
	{
		map = new String[10];
		map[0] = "*D***EFB^^^^"; //* = desert, F = forest, - = river
		map[1] = "****E^^^GGB^"; //O = oasis, D = desert temple
		map[2] = "**PO*^^^GG!^"; //G = grass, B = big tree
		map[3] = "*****EB^^^^B"; //E = edge of forest/desert
		map[4] = "----*EF^GGGF"; //P = palm trees
		map[5] = "WWW----GGGGG"; //
		map[6] = "WWWWSBGFGGLG"; //W = water, C = drowning child
		map[7] = "WWHWWWS?GGFG"; //F = field of flowers, H = house
		map[8] = "WWWWWSGGGFGG"; //L = Lumber, ? = toolshed.
		map[9] = "WWWCWSFFGGGG"; // T = Trex, D = mysterios desert
		
		inputRegistry.put(0, "You found a desert temple. Do you want to explore it?\n");
		inputRegistry.put(1, "You found a beautiful oasis. Swim in it?\n");
		inputRegistry.put(2, "You find a child drowning in the water. Do you wish to save it?\n");
		inputRegistry.put(3, "You are in a forest with a big tree ahead of you. Do you wish to climb the tree?\n");
		inputRegistry.put(4, "An old woman is inside. She offers you a glass of water. Accept it?\n");
		inputRegistry.put(5, "You find a pile of wood in a field of grass. Take it?\n");
		
		inputRegistry.put(99, "Where do you wish to travel? North, south, east, west or quit.\n");
		MapGame.instance = this;
	}
	
	protected class TreeHouse
	{
		int x = -1;
		int y = -1;
		int owned = -1; //Owned = 0, nonowned = 1
		int stage = 0;
		boolean kittenPoster = false;
	}
	
	public boolean isYes(String string)
	{
		string = string.toLowerCase();
		if (string.toCharArray()[0] == 'y')
			return true;
		else
			return false;   
	}
	
	public boolean isNight(Player player)
	{
		return player.time >= 14 && player.time <= 23;
	}
	    
	    
	public boolean isDay(Player player)
	{
		return player.time >= 0 && player.time <= 11;
	}

	public void printLetter(int x, int y, Player p) {
	    String s = String.valueOf(map[y].toCharArray()[x]);
	    String output = "";
	    if (s.equals("*")) {// //If they are in the desert. Decrease hydration.
	        output += "You are in a massive desert\n";
	        p.hydration -= 5;
	        if (p.hydration == 0) {
	            output += "Your dead corpse starts to rot in the desert. (Game END)\n";
	            p.health = 0; }
	        else if (p.hydration <= 10)
	            output += "Your head starts to spin. You are about to pass out from lack of hydration.\n";
	        else if (p.hydration <= 20)
	        	output += "Your throat is very dry. You will need to find water soon.\n";
	        else if (p.hydration <= 30)
	            output += "Your quite thirsty.\n";
	    }     
	    else if (s.equals("D")) {// //If they find a desert temple
	        output += "You found a desert temple. Do you want to explore it?\n";
	        p.getNextQID = 0;
	    }        
	    else if (s.equals("O")) {// //If they find the oasis
	        output += "You found a beautiful oasis. Swim in it?\n";
	        p.getNextQID = 1;
	    }        
	    else if (s.equals("-"))////They find the river
	        output += "You cross a river.";
	        
	    else if (s.equals("S"))//They find a shore
	        output += "You are on a sandy shore.";
	        
	    else if (s.equals("W"))//They swim in the sea
	        output += "You find yourself swimming in the sea.";
	        
	    else if (s.equals("C")) {//They find a drowning child
	        output += "You find a child drowning in the water. Do you wish to save it?\n";
	        p.getNextQID = 2;
	    }
	    else if (s.equals("^")) {//If they in a forest
	        if (new Random().nextInt(7) == 0 && isDay(p))
	            output += "You are in a forest. You see a squirel in a nearby tree.";
	        else if (new Random().nextInt(6) == 0 && isDay(p))
	            output += "You are in a forest. You can hear the birds sing.";
	        else if (new Random().nextInt(4) == 0 && isNight(p))
	            output += "You are in a forest. It is very dark.";         
	        else if (new Random().nextInt(5) == 0 && isNight(p))
	            output += "You are in a forest. You can hear an owl hooting in the distance.";
	        else
	            output += "You are in a forest.";
	    }       
	    else if (s.equals("B")) output += "You are in a forest with a big tree ahead of you. Currently disabled, please try again later.\n";//{//If they find a big tree.
	        //output += "You are in a forest with a big tree ahead of you. Do you wish to climb the tree?\n";
	        /*p.getNextQID = 3;
	    boolean foundTreeHouse = false;
	        TreeHouse ownedTreeHouse = null;
	        TreeHouse nonOwnedTreeHouse = null;
	        for (int i1 = 0; i1 < 2; i1++) {
	            if (p.treeHouses[i1] != null && p.treeHouses[i1].owned == 1) {
	                foundTreeHouse = true;
	                nonOwnedTreeHouse = p.treeHouses[i1];}
	            else
	                ownedTreeHouse = p.treeHouses[i1];
	        }   
	        if (isYes() && new Random().nextInt(4) == 0 && nonOwnedTreeHouse == null) {
	            if (hydration <= 95)
	                hydration += 5
	            output += "You find a secret treehouse. Someone has been here before."
	            treeHouses.append(makeTreeHouse(x, y, false))
	        else if(foundTreeHouse == true && nonOwnedTreeHouse.x == x && nonOwnedTreeHouse.y == y)
	            output += "You find a secret treehouse. Seems to be the one you found before."
	        else if(isYes(i) && lumber == false)
	            if (hydration <= 95)
	                hydration += 5            
	            output += "You climb the tree. This tree would make a great treehouse!"
	        else if(isYes(i) && nonOwnedTreeHouse != null && nonOwnedTreeHouse.x != x && nonOwnedTreeHouse.y != y)
	            if (new Random().nextInt(0, 4) == 0)
	                output += "You climb the tree. There is an angry monkey throwing bananas at you!"
	            else
	                output += "You climb the tree. There's nothing here."
	        else if(isYes(i) && lumber == true && ownedTreeHouse == null)
	            i3 = input("You climb the tree. This tree would make a great treehouse! Make a treehouse with the wood you found?\n"
	            if (isYes(i3)) {
	                output += "You make a treehouse."
	                treeHouses.append(makeTreeHouse(x, y, true));}
	            else
	                output += "You climb down the tree."
	        else if(isYes(i) && ownedTreeHouse != null && ownedTreeHouse.x == x && ownedTreeHouse.y == y) {
	            i2 = input("You found your treehouse again. Hang a kitten poster on the wall?\n"
	            if (isYes(i2))
	                ownedTreeHouse.stage = 1
	                ownedTreeHouse.kittenPoster = true
	    
	        }
	    }*/
	    else if (s.equals("G")) {//In a field
	        if (isNight(p) && new Random().nextInt(3) == 0)
	            output += "You are in a field of grass. The stars shine brightly above.";
	        else if (isDay(p) && new Random().nextInt(3) == 0)
	            output += "You are in a field of grass. You can hear the crickets cherping.";
	        else   
	            output += "You are in a field of grass.";
	    }
	    else if (s.equals("H")) {//If they find a weird house
	        output += "You find an old house above the water. Do you wish to enter?\n";
	        p.getNextQID = 4; 
	    }            
	    else if (s.equals("F"))//Field of flowers
	        output += "You are in a field of flowers. They smell nice!";
	        
	    else if (s.equals("E"))//Edge of desert/forest
	        output += "You are on the edge of a forest and a desert.";
	        
	    else if (s.equals("L"))//Pile of lumber  
	    {
	        if (!p.lumber)
	        {
	            output += "You find a pile of wood in a field of grass. Take it?\n";
	            p.getNextQID = 5;
	        }
	        else
	            output += "You are in a field of grass.";
	    }
	         
	    else if (s.equals("?")) 
	    {
	    	output += "You find a toolshed. Go in?\n";
	    	p.getNextQID = 6;
	    }
	    
	    	/*//Toolshed
	    }
	        i2 = input("You find a toolshed. Go in?\n"
	        if (isYes(i2) && spade == false)
	            i3 = input("There is a spade. Take the spade?\n";
	            if (isYes(i3))
	                output += "You take the spade.";
	                spade = true;
	        else if(isYes(i2))
	            output += "It is empty."*/
	            
	    else if (s.equals("!")) output += "You find a forge. Temp disabled for now, sorry!\n";/*//Forge      
	        if (time % 5 == 0 && isDay(p))
	            if (isYes(input("You find a smelting forge. The forges are smoking hard. Go inside?\n"))
	                if (isYes(input("There is a dwarf working hard in the forge. Talk to him?\n"))
	                    if (riches >= 10 && sword != 2 && forgeTime == -1)
	                        if (isYes(input("The dwarf refuses to talk to you. Show him your loot?\n"))
	                            output += "The dwarf offers you a deal for some of your loot.";
	                            if (isYes(input("He says he will make you a light platinum sword for 10 gold coins. Accept the trade?\n"))
	                                output += "The dwarf takes 10 gold coins and tells you to come back in a few days.\nThe dwarf starts the forges again as you exit the building."
	                                riches -= 10
	                                forgeTime = 0
	                    if (forgeTime >= 20 && sword != 2)
	                        if (isYes(input("\"Oh, it's you. I got your sword. Still want it?\"\n"))
	                            output += "You obtain the light platnium sword.";
	                            sword = 2
	                        else
	                            output += "\"Fine. Come back when you want it.\"";
	                    else
	                        output += "The dwarf refuses to talk to you.\nHe says to come back when you have something worth of his time."
	                else
	                    output += "You leave the forge quietly."
	        else if(isNight(p) && sword == 0 && forgeTime == -1)
	            if (isYes(input("You find a smelting forge. Go inside?\n"))
	                if (isYes(input("There is dwarf sleeping in the forge. He is holding a blue sword. Steal the sword?\n"))
	                    if (new Random().nextInt(0,4) == 0)
	                        output += "You manage to steal the sword and exit the forge."
	                        sword = 1
	                        hasSword = true
	                    else
	                        output += "To attempt to take the sword but the dwarf awakes. Anger rages in his eyes as he wields the sword high and cuts off your head. You are dead."
	                        health = 0
	                else
	                    output += "You exit quietly."
	        else if(isNight(p))
	            if (isYes(input("You find a smelting forge. Go inside?\n"))  
	                output += "There is a dwarf sleeping in the forge. Better exit and leave him alone."
	        else
	            if (isYes(input("You find a smelting forge. It seems quiet. Go inside anyway?\n"))
	                print ("Is is empty."
	    */            
	    else ////Error in map
	        output += "You are forever lost...";
	    
	    p.sendToPlayerChannel(output);
	    
	    if (p.health != 0)
	        travel(p);    
	}
	
	public void getNextInput(Player p, String input)
	{
		//String output = "";
		switch (p.getNextQID)
		{
		case 0:
			if (isYes(input))
				p.sendToPlayerChannel("You find nothing...\n");
		case 1:
			if (isYes(input))
			{
				p.hydration = 100;
				p.sendToPlayerChannel("You take a dip and rehydrate yourself!\n");
			}  
		case 2:
			if (isYes(input))
				p.sendToPlayerChannel("The child drowns anyway...\n");
	        else
	        	p.sendToPlayerChannel("The child drowns.\n");
		case 4:
			if (isYes(input)) 
			{
	            //output += "An old woman is inside. She offers you a glass of water. Accept it?\n";
	            if (isYes(input)) 
	            {
	                if (new Random().nextInt(4) == 0) 
	                {
	                	p.sendToPlayerChannel("Oh no! The water was poisoned! Your eyes start to close and you feel your heart start to slow. You are dead.");
	                    p.health = 0;
	                }
	                else 
	                {
	                	p.sendToPlayerChannel("You feel greatly refreshed.");
	                    if (p.hydration <= 70)
	                        p.hydration += 30;
	                    else
	                         p.hydration = 100;
	                }
	            }
	            else
	            	p.sendToPlayerChannel("You refuse and the woman throws the glass at you. What a bitch!");
			}
		case 5:
			if (isYes(input))
			{
                p.lumber = true;
                p.sendToPlayerChannel("You take the wood.");
			}
            else
                p.lumber = false;
		case 99:
		    String d = input;
		    d = String.valueOf(d.toLowerCase().toCharArray()[0]);
		    if (d.equals("n")) 
		    {
		        if (p.posY > 0)
		            p.posY -= 1;
		        else 
		        {
		            p.sendToPlayerChannel("You end up going the wrong way and go south.");
		            p.posY += 1; 
		        } 
		    }
		    else if (d.equals("s")) 
		    {
		    	if (p.posY < 9)
		        p.posY += 1;
		        else 
		        {
		        	p.sendToPlayerChannel("You end up going the wrong way and go north.");
		        	p.posY -= 1; 
		        }
		    }
		    else if (d.equals("e"))
		    {
		        if (p.posX < 11)
		            p.posX += 1;
		        else 
		        {
		        	p.sendToPlayerChannel("You end up going the wrong way and go west.");
	                p.posX -= 1;
		        }
		            
		    }
		    else if (d.equals("w"))
		    {
		    	if (p.posX > 0)
		            p.posX -= 1;
		        else 
		        {
		            p.sendToPlayerChannel("You end up going the wrong way and go east.");
		            p.posX += 1;
		        }
		    }
		                
		    else if (d.equals("q"))
		    {
		        p.sendToPlayerChannel("You realise the map was in your pocket all along. You find the way out of this world and escape. (Game END)\n");
		        p.health = 0;
		        playerFiles.remove(p.playerNick);
		    }
		    else {
		    	String alert = " To talk without triggering commands, try using a # at the start.";
		        p.sendToPlayerChannel("\""+input.split(" ")[0]+"\" is not recognised as a direction." + (p.failedTypeAttempts >= 2 ? alert : ""));
		        p.failedTypeAttempts++;
		        travel(p);
		    }    
		    
		    
		}
		p.getNextQID = 99;
		printTime(p);
	    printLetter(p.posX, p.posY, p);
		
	}
	
	public void printTime(Player p)
	{
	    if (p.time == 12)
	        p.sendToPlayerChannel("The sun starts to descend from the sky.");
	    else if (p.time == 13)
	        p.sendToPlayerChannel("The sun sets and stars come out into the night.");
	    else if (p.time == 23) {
	        p.sendToPlayerChannel("The sun begins to rise and the night fades away.");
	        p.time = -1;
	    }
	    p.time += 1; 
	    /*if (isNight() == False and forgeTime >= 0 and sword != 2):
	        forgeTime += 1*/
	}
	
	public void travel(Player p)
	{
		if (p.getNextQID == 99)
		{
			p.sendToPlayerChannel("Where do you wish to travel? North, south, east, west or quit.\n");
		}
	}
}


