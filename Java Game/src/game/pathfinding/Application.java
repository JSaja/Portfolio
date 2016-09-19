package hw5.pathfinding;

/**
 *
 * @author baylor
 */
public class Application
{
	public static void main(String args[])
	{
//		String mapName = "maps/simplestMap.txt";
//		String mapName = "maps/mapA.txt";
		String mapName = "maps/mapB.txt";
		PositionedGraph graph = GraphLoader.loadGraph(mapName);
		PathFindingForm form = new PathFindingForm(graph);
		form.setVisible(true);
	}
}
