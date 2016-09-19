package hw5.pathfinding;

/**
 * Stores information about a node we have used in a search. In addition to
 * holding the node and the ID we used to store the node in the closed list,
 * it holds the ID of the immediate previous node we used to get here. We'll
 * use that data if we ever want to trace back how we got to this point.
 *
 * This class has everything it needs, you don't need to change anything.
 * 
 * @author baylor
 */
public class SearchNode
{
	public String id, closestParentsID;
	public PositionedNode   node;

	public SearchNode(String id, PositionedNode node, String closestParentsID)
	{
		this.id = id;
		this.closestParentsID = closestParentsID;
		this.node = node;
	}
              

	@Override
	public String toString()
	{
		return id;
	}
}
