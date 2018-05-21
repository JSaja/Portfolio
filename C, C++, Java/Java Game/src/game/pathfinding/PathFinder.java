package hw5.pathfinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 *
 * @author baylor
 */
public class PathFinder
{
	public List<String> findShortestPath(String startNodeID, String endNodeID,
			PositionedGraph graph)
	{
		//--- The path we're building
		List<String> path = new LinkedList<>();
		//--- The list of nodes to search. Stored as search nodes, which have
		//---	the node as well as the node that came before it
		Queue<SearchNode> openList = new LinkedList<>();                
		//--- The list of nodes we've already searched and don't need to search again
		Map<String,SearchNode> closedList = new HashMap<>();                		                
		/*
		 * While you have nodes left to search
		 *	pick a node
		 *	if it's the goal, figure out the path you took and return that
		 *  if it's something you've already seen, ignore it
		 *  add its neighbors to the list of things to search in the future
		 *		probably want to store the neighbors as SearchNode
		 *		so that you can recreate the path later
		 */                
                SearchNode startNode = new SearchNode(startNodeID, graph.getNode(startNodeID), "");
                openList.add(startNode);
                while (!openList.isEmpty())
                {
                    SearchNode current = openList.poll(); 
                    if (current.id.equals(endNodeID))
                    {
                        return traceBackPath(current, closedList);
                    }
                    else
                    {
                        for (Link neighbor : (List<Link>) current.node.getNeighbors())
                        {    
                            if (!closedList.containsKey(neighbor.getEndPoint().id))// (//else
                            {
                                SearchNode temp = new SearchNode(neighbor.getEndPoint().id, neighbor.getEndPoint(), current.id);
                                openList.add(temp);
                                closedList.put(current.id, current);
                            }                                                                                                                 
                        }
                    }
                }               
                return null;
	}

	/**
	 * Calculate the list of nodes we went through to get to this node.
	 *
	 * @param goalNode Node we're on
	 * @param nodesWeSearched The closed list
	 * @return List of the IDs of the nodes we searched, first to last
	 */
	private List<String> traceBackPath(SearchNode goalNode,
			Map<String,SearchNode> nodesWeSearched)
	{           
            Stack pathStack = new Stack();
            
            while (goalNode != null)
            {
                pathStack.add(goalNode.id);
                goalNode = nodesWeSearched.get(goalNode.closestParentsID);
            }
            Collections.reverse(pathStack); 
            return pathStack;
        }

}
