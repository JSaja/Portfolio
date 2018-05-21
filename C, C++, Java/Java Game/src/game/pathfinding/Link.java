package hw5.pathfinding;

/**
 *
 * @author baylor
 */
public class Link<T>
{
        private int cost;
        private PositionedNode<T> endPoint;   

	public Link(PositionedNode<T> endPoint, int cost)
	{
		 this.cost = cost;
                 this.endPoint  = endPoint;
	}

	public PositionedNode<T> getEndPoint()
	{
		return endPoint;
	}
	public void setEndPoint(PositionedNode<T> endPoint)
	{
		 this.endPoint = endPoint;
	}

	public int getCost()
	{
		return cost;
	}
	public void setCost(int cost)
	{
		this.cost = cost;
	}

	public boolean isLinkedTo(String nodeID)
	{
		return getEndPoint().getID().equals(nodeID);
	}
	public boolean isLinkedTo(PositionedNode<T> node)
	{
		return getEndPoint().equals(node);
	}

	@Override public String toString()
	{
		//throw new UnsupportedOperationException("Not yet implemented");
		return "to " + endPoint;
	}
}
