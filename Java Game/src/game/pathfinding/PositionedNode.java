package hw5.pathfinding;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author baylor
 */
public class PositionedNode<T>
{
    T data;
    Position position;
    String id;    
    List<Link> neighbors = new LinkedList();
    
	public PositionedNode(String id, T data, Position position)
	{
		this.id   = id;
                this.data = data;
                this.position = position;
	}

	public boolean isLinkedTo(String id)
	{
	    for (Link<T> link : neighbors)
            {
                PositionedNode<T> node = link.getEndPoint();
                if (node.id.equals(id))
                {
                    return true;
                }
            }
            return false;
	}

	void removeLinkTo(String id)
	{
	    for (Link<T> link : neighbors)
            {
                PositionedNode<T> node = link.getEndPoint();
                if (node.id.equals(id))
                {
                    neighbors.remove(link);
                }
            }
	}

	//<editor-fold defaultstate="collapsed" desc="accessors">
	public T getData()
	{
		return data;
	}
	public void setData(T data)
	{
		this.data = data;
	}

	public String getID()
	{
		return id;
	}
	public void setID(String id)
	{
		this.id = id;
	}

	public List<Link> getNeighbors()
	{
		return neighbors;
	}

	public Position getPosition()
	{
		return position;
	}
	public void setPosition(Position position)
	{
		this.position = position;
	}
	//</editor-fold>

	//<editor-fold defaultstate="collapsed" desc="overrides">
	@Override
	public boolean equals(Object otherGuy)
	{
		if (! (otherGuy instanceof Position))
		{
			return false;
		}
		return toString().equals(otherGuy.toString());
	}

	@Override
	public int hashCode()
	{
		return getID().hashCode();
	}

	@Override
	public String toString()
	{
		return getID();
	}
	//</editor-fold>
}
