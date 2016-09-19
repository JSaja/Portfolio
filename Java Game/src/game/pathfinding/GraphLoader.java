package hw5.pathfinding;

import baylor.utils.DataLoader;
import java.util.List;

/**
 * Load a graph from a file. The file is of the format:
 *   Lines describing nodes:
 *     ID, x, y
 *   Lines describing links:
 *     link, startID, endID
 *
 * This class works, you don't need to change anything here.
 * @author baylor
 */
public class GraphLoader
{
	static public PositionedGraph loadGraph(String fileName)
	{
		List<String> lines = DataLoader.loadLinesFromFile(fileName);
		PositionedGraph graph = new PositionedGraph();

		for (String line : lines)
		{
			line = line.trim();
			//--- Ignore blank lines and comments
			if (line.isEmpty() || line.startsWith("#"))
			{
				continue;
			}
			String[] tokens = line.split(",");
			tokens[0] = tokens[0].trim().toLowerCase();

			if (tokens[0].equals("link"))
			{
				// link, A, B
				String startNodeName = tokens[1].trim().toLowerCase();
				String endNodeName   = tokens[2].trim().toLowerCase();
				graph.addLink(startNodeName, endNodeName, 0);
			}
			else
			{
				// A, 10, 10
				String nodeName = tokens[0];
				int x = Integer.parseInt(tokens[1].trim());
				int y = Integer.parseInt(tokens[2].trim());
				graph.addNode(nodeName, null, new Position(x, y));
			}
		}

		return graph;
	}
}
