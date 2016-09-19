package hw5.pathfinding;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A undirected graph where all nodes have (x,y) coordinates. Like a normal
 * graph but easier to draw.
 *
 * You may obviously re-use your graph from the lab. You do not have to
 * implement any of these methods - we're only going to test PathFinder.
 * But you obviously can't use the path finding form if you don't support these.
 *
 * @author baylor
 */
public class PositionedGraph<T>
{
    Map<String, PositionedNode> graphMap = new HashMap<>(); 
    
	public void addNode(String id, T data, Position position)
	{
		graphMap.put(id, new PositionedNode(id, data, position)); 
	}

	public void addLink(String startID, String endID, int cost)
	{
		PositionedNode<T> startNode = getNode(startID);
                PositionedNode<T> endNode = getNode(endID);
                
                if (startNode == null || endNode == null)
                {
                    return;
                }	
                Link<T> linkEnd = new Link(endNode, cost);
                startNode.neighbors.add(linkEnd);
                
                Link<T> linkStart = new Link(startNode, cost);
                endNode.neighbors.add(linkStart);
	}

	public List<Link> getNeighbors(String id)
	{
		PositionedNode<T> node = graphMap.get(id);
                return node.neighbors;
                
	}

	public PositionedNode<T> getNode(String id)
	{
		return graphMap.get(id);
	}

	public List<PositionedNode<T>> getNodes()
	{
		LinkedList listOfNodes = new LinkedList();
                for (PositionedNode<T> node : graphMap.values())
                {
                    listOfNodes.add(node);
                }
                return listOfNodes;
	}

	public T getNodeData(String id)
	{
		return (T) graphMap.get(id).data;
	}

	public List<String> getNodeIDs()
	{
            LinkedList listOfIDs = new LinkedList();
            for (String id : graphMap.keySet())
            {
                listOfIDs.add(id);
            }
            return listOfIDs;
	}

	public Position getNodePosition(String id)
	{
		PositionedNode<T> node = graphMap.get(id);
                return node.position;
	}

	public void remove(String id)
	{
	     PositionedNode<T> aNode = getNode(id); 
             
             //--- Anyone who was pointing to us now has to stop
             for (PositionedNode<T> node: graphMap.values()) 
             {
                //remove entire link of each neighbor 
                if (node.isLinkedTo(id))
                {
                    node.removeLinkTo(id);
                }                 
             }
             //--- Now that no one cares about us anymore, time to say goodbye                  
            graphMap.remove(id);
	}

	//<editor-fold defaultstate="collapsed" desc="positional stuff">
	public int getMaxX()
	{
	    int maxX = Integer.MIN_VALUE;
            for (PositionedNode<T> node : graphMap.values())
            {
                if (node.position.getX() > maxX)
                {
                    maxX = node.position.getX();
                }
            }
            return maxX;
	}

	public int getMaxY()
	{
            int maxY = Integer.MIN_VALUE;
            for (PositionedNode<T> node : graphMap.values())
            {
                if (node.position.getY() > maxY)
                {
                    maxY = node.position.getY();
                }
            }
            return maxY;
	}

	public int getMinX()
	{
	    int minX = Integer.MAX_VALUE;
            for (PositionedNode<T> node : graphMap.values())
            {
                if (node.position.getX() < minX)
                {
                    minX = node.position.getX();
                }
            }
            return minX;
	}

	public int getMinY()
	{
	    int minY = Integer.MAX_VALUE;
            for (PositionedNode<T> node : graphMap.values())
            {
                if (node.position.getY() < minY)
                {
                    minY = node.position.getY();
                }
            }
            return minY;
	}
	//</editor-fold>
}
