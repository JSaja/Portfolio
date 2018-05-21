package hw5.game;

/**
 * An item an agent can wear. For the homework, this should modify the
 * agent's statistics. For example, a sword might increase their attack +5.
 *
 * @author baylor
 */
public class EquipableItem
{
	String id;		// Used to determine which image to draw
	String name;	// Descriptive name

	public EquipableItem(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
