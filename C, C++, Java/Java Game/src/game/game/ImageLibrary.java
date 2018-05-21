package hw5.game;

import baylor.utils.DataLoader;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * Load images into a hashtable to make it fast to get at them later.
 * The list of images to load is based on a file this reads in when it's created.
 *
 * This class works, you don't need to change anything.
 *
 * @author baylor
 */
public class ImageLibrary
{
	//--- A better implementation would pass these in rather than hard code them
	String imageCatalogFileName = "maps/image names.txt";
	String imageDirectory = "images/";

	public HashMap<String, Image> images = new HashMap<>();

	public ImageLibrary()
	{
		loadImages();
	}

	private void loadImages()
	{
		List<String> lines = DataLoader.loadLinesFromFile(imageCatalogFileName);
		for (int i = 0; i < lines.size(); i++)
		{
			String line = lines.get(i);
			line = line.trim();
			if (!line.isEmpty() && !line.startsWith("#"))
			{
				String[] tokens = line.split(",");
				String key   = tokens[0].trim();
				Image  value = loadImage(tokens[1].trim());
				images.put(key, value);
			}
		}
	}

	private Image loadImage(String imageFileName)
	{
		String fqn = "";
		try
		{
			File f = new File(imageDirectory + imageFileName);
			fqn = f.getAbsolutePath();
			Image i  = ImageIO.read(f);
			return i;
		}
		catch (IOException ex)
		{
			System.out.println("loadImages error: " + fqn);
			System.out.println(ex);
		}
		//--- You'll only get here if there's an error
		return null;
	}
}
