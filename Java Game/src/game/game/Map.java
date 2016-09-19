package hw5.game;

import baylor.utils.DataLoader;
import java.util.HashMap;
import java.util.List;

/**
 * Map model. Stores map properties including the different layers and
 * whether given spots are places where people can walk.
 *
 * All the map loading stuff works. If you want more map functionality,
 * you can add it here.
 *
 * This is a Model, not a View, which means it's non-visual. It does not
 * draw the map. The View class (in this case, the map form) does that.
 *
 * @author baylor
 */
public class Map
{
	private String mapsDirectoryName = "maps/";

	// Maps are represented (x,y) / (column, row) / (width, height)
	public String[][] terrain;	// grass, water, etc.
	public String[][] items;	// treasure, potions, etc.

	//--- Keep track of which types of terrain we can/cannot walk on
	//--- Technically, we're only tracking what we CAN'T walk on right now
	public HashMap<String,Boolean> passibility = new HashMap<>();

	//<editor-fold defaultstate="collapsed" desc="constructors and accessors">
	public Map(String mapName)
	{
		loadPassabilityInformation();
		loadMap(mapName);
	}

	private void loadPassabilityInformation()
	{
		String fileName  = "impassible terrain.txt";
		List<String> lines = DataLoader.loadLinesFromFile(mapsDirectoryName + fileName);
		for (int i = 0; i < lines.size(); i++)
		{
			String line = lines.get(i);
			line = line.trim();
			if (!line.isEmpty() && !line.startsWith("#"))
			{
                            String[] tokens = line.split(",");
				String key = tokens[0].trim();
				passibility.put(key, false);                               
			}
		}
	}

	public void loadMap(String mapName)
	{
		String terrainMapName = mapName + ".terrain.map";
		terrain = MapLoader.getMap(mapsDirectoryName + terrainMapName);

		String itemMapName = mapName + ".items.map";
		items = MapLoader.getMap(mapsDirectoryName + itemMapName);

		//--- Just to be clean, convert any "." in the item map to nulls
		for (int x=0; x < getWidth(); x++)
		{
			for (int y=0; y < getHeight(); y++)
			{
				if (items[x][y].equals("."))
				{
					items[x][y] = null;
				}
			}
		}
	}

	public int getWidth()
	{
		return terrain.length;
	}
	public int getHeight()
	{
		return terrain[0].length;
	}
	//</editor-fold>
	


	//<editor-fold defaultstate="collapsed" desc="Object overrides">
	@Override
	public String toString()
	{
		//--- This is technically really, really inefficient but it's easy
		//---	so for now we're going to do this
		String output = "";

		//--- Important to loop through rows THEN columns
		//--- Otherwise it will draw sideways
		for (int y = 0; y < getHeight(); y++)
		{
			for (int x = 0; x < getWidth(); x++)
			{
				output += terrain[x][y];
			}
			output += System.lineSeparator();
		}
		return output;
	}
	//</editor-fold>
}
