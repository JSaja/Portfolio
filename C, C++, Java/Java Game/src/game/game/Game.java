package hw5.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author baylor
 */
public class Game
{
	public Map    map;
	public Player player;
	List<Agent>   agents = new LinkedList<>();
        int[][] playerSpot;        
        int coinCount = 0;
        int keyCount = 0;
        int stepCount = 0;
        Enemy possibleEnemy;
        int killCount;
        int orbCount;
        int potionCount;
        int lavaStepCount;
        int cutPP = 8;
        int splashPP = 20;
        int selfDestructPP = 1;
        int leechSeedPP = 8;
        int autoKillPP = 1;
        String agentBoss = "";
        int distance = 0;
	//--- If we want Game to be a singleton, we need this
	static private Game instance;
        Enemy someEnemy = new Enemy("someEnemy");        

	//<editor-fold defaultstate="collapsed" desc="constructors and initilization">
	/**
	 * Constructor. Since we've decided to make the class a singleton,
	 * we made the constructor private so that no one can create a new
	 * instance. This class doesn't need to be a singleton, deal with it
	 * however you want.
	 */
	private Game()
	{
		//--- Load a map
		map = new Map("main");

		//--- Create a player, stick him in the top left corner                                
		player = new Player("player");
                
		player.x = 2;
		player.y = 2;

		//--- Create enemies
		Enemy someBadGuy = new Enemy("dragon");
		someBadGuy.x = 4;
		someBadGuy.y = 4;
		someBadGuy.health = 100;   
                someBadGuy.name = "dragon";
		agents.add(someBadGuy);                

		someBadGuy = new Enemy("giant");
		someBadGuy.x = 7;
		someBadGuy.y = 7;
		someBadGuy.health = 90;
                someBadGuy.name = "giant";
		agents.add(someBadGuy);               

		someBadGuy = new Enemy("whitewalker");
		someBadGuy.x = 8;
		someBadGuy.y = 12;
		someBadGuy.health = 80;
                someBadGuy.name = "whitewalker";
		agents.add(someBadGuy);

		someBadGuy = new Enemy("redTank");
		someBadGuy.x = 10;
		someBadGuy.y = 3;
		someBadGuy.health = 110;
                someBadGuy.name = "redTank";
		agents.add(someBadGuy);

		someBadGuy = new Enemy("Genji");
		someBadGuy.x = 4;
		someBadGuy.y = 16;
		someBadGuy.health = 20;
                someBadGuy.name = "Genji";                
		agents.add(someBadGuy); 
                                
	}        

	/**
	 * Since this is a singleton, this is the only way to get a Game object.
	 * @return
	 */
	static public Game getInstance()
	{
		//--- Lazy loading
		if (null == instance)
		{
			instance = new Game();
		}
		return instance;
	}
	//</editor-fold>
        
    public void move(Direction direction)
    {    
        switch(direction)
        {
            case Up:
                moveUp();
                break;
            case Down:
                moveDown();
                break;
            case Right:
                moveRight();
                break;
            case Left:
                moveLeft();
                break;
        }                
    }
    
    public void moveEnemies()
    {
        for (Agent agent : agents)
        {
            if (agent instanceof Enemy && !agent.name.equals("robotboss"))
            {
                Enemy e = (Enemy) agent;                
                e.move();                        
            }           
        }
    }
    
    public boolean shouldFight()
    {
        for (Agent agent : agents)
        {          
            //Check for battle            
            
            int absX = Math.abs(player.x - agent.x);
            int absY = Math.abs(player.y - agent.y);
            distance = absX + absY;  
//            if (distance <= 1 && agent.name.equals("robotboss"))
//            {
//                agentBoss = agent.name;
//            }
            if (distance <= 1)
            {
                someEnemy = (Enemy) agent;
                return true;                 
           }                
         }
        return false;        
    }
        
   
    public void moveUp() 
    {          
            if (player.y - 1 >= 0 && isLegalMove(player.x, player.y -1))
            {
                player.y--; // actually update the position.
                stepCount++;
               // System.out.println("(" + player.x + "," + player.y + ")");                   
            }
            else
            {
                System.out.println("You cannot make this move");  
            }
        }
        
    public void moveDown()
    {    
        if (player.y + 1 < instance.map.getHeight() && isLegalMove(player.x, player.y +1))
        {
            player.y += 1; // actually update the position.
            stepCount++;
            //System.out.println("(" + player.x + "," + player.y + ")");                              
        }
        else
        {
            System.out.println("You cannot make this move");   
        }
    }
    
    public void moveRight()
    {      
        if (player.x + 1 < instance.map.getWidth() && isLegalMove(player.x+1, player.y))
        {            
            player.x += 1; // actually update the position.
            stepCount++;
            //System.out.println("(" + player.x + "," + player.y + ")");                                  
        }
        else
        {
            System.out.println("You cannot make this move");         
        }
    }
    
    public void moveLeft()
    {   
        if (player.x - 1 >= 0 && isLegalMove(player.x-1, player.y))
        {         
            player.x -= 1; // actually update the position.
            stepCount++;
            //System.out.println("(" + player.x + "," + player.y + ")");                        
        }
        else
        {
            System.out.println("You cannot make this move");           
        }
    }
    
    public boolean isLegalMove(int newX, int newY)
    {  
            boolean bool = true;            
	    if (map.terrain[newX][newY].equals("^") || 
                map.terrain[newX][newY].equals("T") ||
                map.terrain[newX][newY].equals("~") ||
                map.terrain[newX][newY].equals("B") ||
                map.terrain[newX][newY].equals("C"))                 
	   {
	        bool = false;
                return bool;
	   }
           else if (map.items[newX][newY] != null)
            {
                if (map.items[newX][newY].equals("Y"))
                {
                    if (keyCount >= 1)
                    {
                        bool = true;
                        map.items[newX][newY] = null;
                        return bool;
                    }
                    else
                    {
                        bool = false;
                        return bool;
                    }
                }
                else if (map.items[newX][newY].equals("Z"))
                {
                    if (keyCount >= 2)
                        {
                        bool = true;
                        map.items[newX][newY] = null;
                        return bool;
                    }
                    else
                    {
                        bool = false;
                        return bool;
                    }
                }
                else if (map.items[newX][newY].equals("W"))
                {
                    if (coinCount >= 10)
                    {
                        bool = false;
                        map.items[newX][newY] = "w";                        
                        return bool;                        
                    }                    
                    else
                    {
                        bool = false;
                        return bool;
                    }
                }
            } 
            return bool; 
    }
                  
    public void updateTerrainHealth()
    {
        // check for lava damage
        if (map.terrain[player.x][player.y].equals("l"))
        {
            player.health -= 5;
            lavaStepCount++;
        }      
        else if (map.terrain[player.x][player.y].equals("r"))
        {
            player.health -= player.health;
        }
        else if (map.terrain[player.x][player.y].equals("s"))
        {
            player.health -= player.health;
        }
    }    
    
    public void updateCoin()
    {
        if (map.items[player.x][player.y] != null)
        {
        if (map.items[player.x][player.y].equals("g"))
        {
           coinCount ++;            
           map.items[player.x][player.y] = null;           
        }
        }
    }
    
    public void updatePotionsAndOrbs()
    { 
        Random random = new Random();
        int aRandom = random.nextInt(5);
        if (agents.size() == 5) // to handle null pointer when rem
        {
            aRandom = random.nextInt(5);
        }
        else if (agents.size() == 4)
        {
            aRandom = random.nextInt(4);
        }
        else if (agents.size() == 3)
        {
            aRandom = random.nextInt(3);
        }
        else if (agents.size() == 2)
        {
            aRandom = random.nextInt(2);
        }
        else if (agents.size() == 1)
        {
            aRandom = random.nextInt(1);
        }
        
        if (map.items[player.x][player.y] != null)
        {
            if (map.items[player.x][player.y].equals("p"))
            {
                player.health += 25;
                map.items[player.x][player.y] = null;
                potionCount++;
                
            }
            else if (map.items[player.x][player.y].equals("2"))
            {
                player.strength += 1;
                map.items[player.x][player.y] = null;
                potionCount++;
            }
            else if (map.items[player.x][player.y].equals("o"))
            {
                orbCount++;
                killCount++;                               
                    if (agents.get(aRandom) != null)
                    {
                    agents.remove(aRandom);
                    }
                
                    map.items[player.x][player.y] = null;
            }
        }
    }    
         
    public void updateKeys()
    {
        if (map.items[player.x][player.y] != null)
        {
            if (map.items[player.x][player.y].equals("k"))
            {
                keyCount++;
                map.items[player.x][player.y] = null;                
            }
        }
    }
    
    public void shouldTeleport() 
    {
        if (map.terrain[player.x][player.y].equals("O"))
       {
               System.out.println("Load a new Map");  
               player.x = 1;
               player.y = 2;
               instance.map.loadMap("cave");  
               for (Agent agent : agents)
               {
                   if (agent.name != null)
                   {
                       if (agent.name.equals("dragon"))
                       {                           
                           agent.x = 6;
                           agent.y = 3;
                       }
                       else if (agent.name.equals("giant"))
                       {
                           agent.x = 9;
                           agent.y = 11;
                       }
                       else if (agent.name.equals("whitewalker"))
                       {
                           agent.x = 6;
                           agent.y = 15;
                       }
                       else if (agent.name.equals("redTank"))
                       {
                           agent.x = 16;
                           agent.y = 7;
                       }
                       else if (agent.name.equals("Genji"))
                       {
                           agent.x = 10;
                           agent.y = 14;
                       }                                                            
                   }
               }                             
       }       
       if (map.terrain[player.x][player.y].equals("M"))
       {
           System.out.println("You've hit the magic portal");
           instance.map.loadMap("sky");
           player.x = 1; // arbitrary value for now
           player.y = 13; // arbitrary value for now
           for (Agent agent : agents)
               {
                   if (agent.name != null)
                   {
                       if (agent.name.equals("dragon"))
                       {
                           agent.x = 7;
                           agent.y = 11;
                       }
                       else if (agent.name.equals("giant"))
                       {
                           agent.x = 12;
                           agent.y = 14;
                       }
                       else if (agent.name.equals("whitewalker"))
                       {
                           agent.x = 5;
                           agent.y = 5;
                       }
                       else if (agent.name.equals("redTank"))
                       {
                           agent.x = 9;
                           agent.y = 6;
                       }
                       else if (agent.name.equals("Genji"))
                       {
                           agent.x = 17;
                           agent.y = 6;
                       }
                   }
               }
                Enemy robotboss = new Enemy("robotboss");
		robotboss.x = 18;
		robotboss.y = 2;
		robotboss.health = Integer.MAX_VALUE;
                robotboss.name = "robotboss";
		agents.add(robotboss);  
       }
    }
    
    public void isPlayerDead()
    {        
        if (player.health <= 0)
        {
            GameOverForm gameOver = new GameOverForm(null, true);   
            gameOver.setVisible(true);
            System.out.println("You're dead");
        }
    }
     
    public void didPlayerWin()
    {
        if (map.items[player.x][player.y-1] != null)
        {
             if (map.items[player.x][player.y-1].equals("W") ||
                 map.items[player.x][player.y-1].equals("w"))
                    {
                        if (coinCount >= 10)
                        {
                        map.items[player.x][player.y-1] = "w";
                        map.items[player.x-1][player.y-1] = "*";
                        map.items[player.x+1][player.y-1] = "*";
                        //pop up a form similar to you dead
                        GameWonForm gameWon = new GameWonForm(null, true); 
                        gameWon.setWinningTextLabel(player.race ,player.name, stepCount, player.health, killCount);
                        gameWon.setAndDisplayScore(coinCount, stepCount, killCount, orbCount, potionCount, lavaStepCount);
                        // score formula : ((coins * 15) / steps) * 150)
                                //        +((enemies * 15) + (orbandpotions * 10) - (lavastepCount * 15)
                        gameWon.setVisible(true);
                        }
                    }
        }
    }
     
    public void isEnemyDead()
    {
        for (int i = 0; i < agents.size(); i++)
        {
            Agent agent = agents.get(i);
            if (agent.isDead())
            {
                agents.remove(agent);
                killCount++;
            }
        }
    }
}
