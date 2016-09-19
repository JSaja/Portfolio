package hw5.pathfinding;

/**
 * Represents a 2D position in space. We use this rather than separate x and y
 * variables because it lets us return both from a method. Also, it makes it
 * easier to do "are these things at the same position?" checks.
 *
 * This class works, you don't need to modify it.
 *
 * @author baylor
 */
public class Position
{
	private int x;
	private int y;

	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX()
	{
		return x;
	}
	public void setX(int x)
	{
		this.x = x;
	}

	public int getY()
	{
		return y;
	}
	public void setY(int y)
	{
		this.y = y;
	}

	@Override
	public boolean equals(Object otherGuy)
	{
		if (! (otherGuy instanceof Position))
		{
			return false;
		}
		Position otherPosition = (Position)otherGuy;

		return (otherPosition.getX()==getX()) &&
			   (otherPosition.getY()==getY());
	}

	@Override
	public int hashCode()
	{
		return (x * 1000) + y;
	}

	@Override
	public String toString()
	{
		return "("+x+","+y+")";
	}
}
