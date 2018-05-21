package hw5.game;

/**
 * An action. For example, in combat this might represent the action
 * "attack with sword", "cast magic missile" or "use healing potion".
 * This is an extremely bare bones version, you want to write something
 * more sophisticated for your game.
 *
 * @author
 */
public class Action
{
	String name;
	int damage, heal;

	public Action(String name, int damage, int heal)
	{
		this.name = name;
		this.damage = damage;                
                this.heal = heal;               
	}

	@Override
	public String toString()
	{
		return name;
	}
}
