package hw5.game;

import java.util.List;
import java.util.Random;

/**
 * An agent is an entity that can move around. Examples: player, NPCs, monsters.
 * You can add code here if you need something or you can make subclasses
 * of this. What's important here is that there's an ID (type). This is used by
 * the rendering system to determine which picture to draw for it.
 *
 * This is a bare bones class. You'll want to come up with better properties
 * to describe your people.
 *
 * @author baylor
 */
abstract public class Agent
{
	//--- What type of thing is this? Dwarf? Dragon? Debuttante?
	//--- Right now the rendering system uses this to determine which picture
	//---	to show when walking around on the map.
	public String type;

	public int x, y;        

	//--- Do you want everyone to have a name or just humans? If it's here,
	//---	everyone gets a name.
	public String name;

	//--- Every property in Agent is available to every type of agent.
	//---	The following are used for combat. If you aren't going to let the
	//---	player fight everything they see, you might want to move these
	//---	to a subclass.
	public int health;
	public int strength;
        public String race;

	public Agent(String type)
	{
		this.type = type;             
	}

	public int getAttackDamage()
	{                       
		Random dice = new Random();                
		int damage = dice.nextInt(30);                		               
                return damage;	
        }

	public boolean isDead()
	{
		return health <= 0;
	}

	/**
	 * The AI for the agent. This method will be called every turn.
	 * Anything the agent needs to decide for itself, such as where to walk
	 * or what attack to use, will be determined here.
	 *
	 * Because we don't know what the different subclasses will do, we're not
	 * going to write the method. Instead, we've marked it as abstract, forcing
	 * the child classes to write this.
	 */
	abstract public void think();

	@Override
	public String toString()
	{
		return type + " health=" + health + " str="+strength;
	}
}
