package hw5.game;

/**
 *
 * @author
 */
public class Player extends Agent
{
	public Player(String type)
	{
		super(type);
	}

	/**
	 * The player doesn't need an AI routine, they control the character.
	 * This method, therefore, does nothing.
	 */
	@Override
	public void think()	{ }

	@Override
	public String toString()
	{
		return name;
	}
}
