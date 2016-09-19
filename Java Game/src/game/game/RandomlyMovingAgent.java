package hw5.game;

import java.util.List;
import java.util.Random;

/**
 * An agent that wanders the map at random.
 *
 * @author
 */
public class RandomlyMovingAgent
	extends Agent
{
	public RandomlyMovingAgent(String type)
	{
		super(type);
	}

	public void move()
	{		
                Random random = new Random();                        
                    int deltaX = random.nextInt(3) - 1;
                    int deltaY = random.nextInt(3) - 1;
                    int possibleX = this.x + deltaX;
                    int possibleY = this.y + deltaY;
                        if (!"^".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"T".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"~".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"B".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"C".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"D".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"O".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"M".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"d".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"s".equals(Game.getInstance().map.terrain[possibleX][possibleY]) &&
                            !"r".equals(Game.getInstance().map.terrain[possibleX][possibleY])                                
                           )                           
                        {
                            this.x = possibleX;
                            this.y = possibleY;
                        }                                                                 
	}
        

	@Override
	public void think()
	{
	}
}
