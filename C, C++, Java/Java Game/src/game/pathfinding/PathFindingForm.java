package hw5.pathfinding;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.Timer;

/**
 * Visualize a path finding solution on an arbitrary graph.
 * This form works - you don't have to modify anything.
 * 
 * @author baylor
 */
public class PathFindingForm extends javax.swing.JFrame
{
	//<editor-fold defaultstate="collapsed" desc="properties">
	private int nodeImageWidth  = 32;	// pixels
	private int nodeImageHeight = 32;

	private int globalXOffset = 0;	// How much to shift over so minX,minY is top left corner
	private int globalYOffset = 0;
	private int xBuffer = 32, yBuffer = 32;

	Timer timer;
	Map<String,Image> images = new HashMap<>();
	BufferedImage backBufferContainer;
	Graphics backBuffer;
	float widthScalingFactor, heightScalingFactor;

	PositionedGraph graph;
	List<String> path;
	MovingObject actor;
	int indexOfCurrentNode = 0;
	//</editor-fold>

	public PathFindingForm(PositionedGraph graph)
	{
		initComponents();
		setLocationRelativeTo(null);
		this.graph = graph;

		//--- Not going to use the mouse for this demo but if you wanted to,
		//---	here's how to start
//		drawingPanel.addMouseListener(new MouseClickHandler(this));

		path = new LinkedList<>();
		path.add("s");
		path.add("e");

		loadImages();
		drawScene();
		repaint();
	}

	private void calculatePath()
	{
		PathFinder searcher = new PathFinder();

		String startNodeID = startNodeTextField.getText().toLowerCase().trim();
		String endNodeID   = goalNodeTextField.getText().toLowerCase().trim();
		path = searcher.findShortestPath(startNodeID, endNodeID, graph);
		if (null == path)
		{
			pathLabel.setText("Couldn't find a path");
			return;
		}
		String pathString = "Path: ";
		for (String nodeID : path)
		{
			pathString += nodeID + "->";
		}
		pathString = pathString.substring(0, pathString.length()-2);
		pathLabel.setText(pathString);
	}

	private void drawActor()
	{
		if (null == actor)
		{
			return;
		}

		int upperLeftCornerX = getLocalX(actor.x)-32;
		int upperLeftCornerY = getLocalY(actor.y)-32;
		backBuffer.drawImage(images.get(actor.name),
				upperLeftCornerX, upperLeftCornerY, null);
	}

	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		Graphics frontBuffer = drawingPanel.getGraphics();
		frontBuffer.drawImage(backBufferContainer, 0, 0, null);
	}

	private void drawBackground()
	{
		Image background = images.get("background");
		background = background.getScaledInstance(
				backBufferContainer.getWidth(),
				backBufferContainer.getHeight(),
				BufferedImage.SCALE_SMOOTH);
		backBuffer.drawImage(background, 0, 0, null);
	}

	private void drawLink(Position start, Position end)
	{
		//--- Make the positions fit in the window
		//--- If we don't scale these, they could go off the screen :(
		int sx = getLocalX(start.getX());
		int sy = getLocalY(start.getY());
		int ex = getLocalX(end.getX());
		int ey = getLocalY(end.getY());

		//--- OK, draw the line
		backBuffer.setColor(Color.blue);
		backBuffer.drawLine(sx, sy, ex, ey);
	}

	private void drawMap()
	{
		calculateScalingFactor();

		//--- Draw the links first so the nodes can cover the start and end points
		//--- Otherwise we'll see lines draw on top of the nodes. Ick.
		List<PositionedNode> nodes = graph.getNodes();
		for (PositionedNode node : nodes)
		{
			Position startPosition = node.getPosition();

			//--- If we don't specify a type for the node (e.g., PositionedNode<String>)
			//---	the following won't work (error: Object, not Link)
			//--- Solution: Grab the collection before iterating over it
			// for (Link connection : node.getNeighbors())
			List<Link> links = node.getNeighbors();
			for (Link connection : links)
			{
				Position endPosition = connection.getEndPoint().getPosition();
				drawLink(startPosition, endPosition);
			}
		}

		//--- draw the nodesByID
		for (PositionedNode node : nodes)
		{
			String imageID = "node";

			//--- Different images for the start and end nodes
			if (node.getID().equalsIgnoreCase(path.get(0)))
			{
				imageID = "start";
			}
			else if (node.getID().equalsIgnoreCase(path.get(path.size()-1)))
			{
				imageID = "goal";
			}

			drawNode(node, imageID);
		}
	}

	private void drawNode(PositionedNode node, String imageID)
	{
		Image nodeImage = images.get(imageID);

		int x = getLocalX(node.getPosition().getX());
		int y = getLocalY(node.getPosition().getY());

		//--- Convert from center to top left corner since that's how it's drawn
		x -= (nodeImageWidth/2);
		y -= (nodeImageHeight/2);

		backBuffer.drawImage(nodeImage,	x, y, null);
		backBuffer.drawString(node.getID(), x, y-2);
	}

	private void drawScene()
	{
		createBackBuffer();
		drawBackground();
		drawMap();
		drawActor();
		repaint();
	}

	//<editor-fold defaultstate="collapsed" desc="support methods">
	private void createBackBuffer()
	{
		backBufferContainer = new BufferedImage(
								drawingPanel.getWidth(),
								drawingPanel.getHeight(),
								BufferedImage.TYPE_INT_RGB);
		backBuffer = backBufferContainer.getGraphics();
	}

	private void calculateScalingFactor()
	{
		int maxNodeX = graph.getMaxX();
		int maxNodeY = graph.getMaxY();
		int minNodeX = graph.getMinX();
		int minNodeY = graph.getMinY();

		//--- Nodes might not start at (0,0), might start at (50,75) or (-20,-80)
		//--- Don't want to draw wasted space so let's figure out how much
		//---	we need to move in each direction to make the nodes move to the sides
		globalXOffset = 0 - minNodeX;
		globalYOffset = 0 - minNodeY;

		//--- Scale the global coordinates to the local coordiante system
		int globalGraphWidth  = maxNodeX - minNodeX + 1;
		int globalGraphHeight = maxNodeY - minNodeY + 1;
		//--- Make sure the graph has some width and height. When all nodes
		//---	are on a line, width/height=1 and scaling gets screwy
		if (globalGraphWidth < 10)
		{
			globalGraphWidth = 50;
		}
		if (globalGraphHeight < 10)
		{
			globalGraphHeight = 50;
		}

		int localGraphWidth  = drawingPanel.getWidth();
		int localGraphHeight = drawingPanel.getHeight();
		//-- Leave off the buffer areas, make them scale in the rest of it
		localGraphWidth  -= 2 * xBuffer;
		localGraphHeight -= 2 * yBuffer;

		widthScalingFactor  = (float)localGraphWidth / globalGraphWidth;
		heightScalingFactor = (float)localGraphHeight / globalGraphHeight;
	}

	/**
	 * Nodes are defined as being at a certain point, which is in graph-space.
	 * This needs to be scaled to fit to a pixel coordinate inside the drawing
	 * canvas, which is in local-space.
	 * @param globalX
	 * @return
	 */
	private int getLocalX(int globalX)
	{
		int offsetX = globalX + globalXOffset;
		int scaledX = (int)(offsetX * widthScalingFactor);
		int scaledShiftedX = scaledX + xBuffer;
		return scaledShiftedX;
	}
	private int getLocalY(int globalY)
	{
		int y = globalY + globalYOffset;
		int scaledY = (int)(y * heightScalingFactor);
		int scaledShiftedY = scaledY + yBuffer;
		return scaledShiftedY;
	}

	private void loadImages()
	{
		loadImage("background", drawingPanel.getWidth(), drawingPanel.getHeight());
		loadImage("node", nodeImageWidth, nodeImageHeight);
		loadImage("start", nodeImageWidth, nodeImageHeight);
		loadImage("goal", nodeImageWidth, nodeImageHeight);
		loadImage("actor", nodeImageWidth*2, nodeImageHeight*2);
	}

	private void loadImage(String name, int width, int height)
	{
		String directory = "images/";
		String fqn       = "";

		try
		{
			fqn = directory + name + ".png";
			BufferedImage image = ImageIO.read(new File(fqn));
			Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			images.put(name, scaled);
		}
		catch(FileNotFoundException e)
		{
			System.out.println("File not found: " + fqn);
		}
		catch(Exception e)
		{
			System.out.println("last file: " + fqn);
			System.out.println(e);
		}
	}
	//</editor-fold>

	private void moveActorOnPath(String startingNodeID, String goalNodeID)
	{
		PositionedNode startingNode = graph.getNode(startingNodeID);
		PositionedNode goalNode     = graph.getNode(goalNodeID);
		int sx = startingNode.getPosition().getX();
		int sy = startingNode.getPosition().getY();
		int ex = goalNode.getPosition().getX();
		int ey = goalNode.getPosition().getY();
		actor.setPath(sx,sy,ex,ey);
		actor.start();
	}

	public void onMoveEnd()
	{
		indexOfCurrentNode++;
		int indexOfDestination = indexOfCurrentNode+1;
		if (indexOfDestination >= path.size())
		{
			return;
		}
		String currentNodeID   = path.get(indexOfCurrentNode);
		String nextNodesID     = path.get(indexOfDestination);
		moveActorOnPath(currentNodeID, nextNodesID);
	}

	public void updatePosition()
	{
		drawScene();
	}

	//<editor-fold defaultstate="collapsed" desc="generated stuff">
	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        drawingPanel = new javax.swing.JPanel();
        findPathButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        startNodeTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        goalNodeTextField = new javax.swing.JTextField();
        pathLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Let's go to a picnic!");

        drawingPanel.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout drawingPanelLayout = new javax.swing.GroupLayout(drawingPanel);
        drawingPanel.setLayout(drawingPanelLayout);
        drawingPanelLayout.setHorizontalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        drawingPanelLayout.setVerticalGroup(
            drawingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );

        findPathButton.setMnemonic('F');
        findPathButton.setText("Find Path");
        findPathButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                findPathButtonActionPerformed(evt);
            }
        });

        jLabel1.setText("Start:");

        startNodeTextField.setText("S");

        jLabel2.setText("End:");

        goalNodeTextField.setText("E");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(drawingPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(findPathButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startNodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(goalNodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(drawingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(findPathButton)
                    .addComponent(jLabel1)
                    .addComponent(startNodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(goalNodeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 14, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void findPathButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_findPathButtonActionPerformed
    {//GEN-HEADEREND:event_findPathButtonActionPerformed
		calculatePath();
		if (null == path || path.isEmpty())
		{
			pathLabel.setText("Path not found");
			return;
		}
		actor = new MovingObject("actor", this);
		indexOfCurrentNode = 0;
		String startingPointID = path.get(indexOfCurrentNode);
		PositionedNode startingPoint = graph.getNode(startingPointID);
		actor.x = startingPoint.getPosition().getX();
		actor.y = startingPoint.getPosition().getY();

		moveActorOnPath(path.get(0), path.get(1));
    }//GEN-LAST:event_findPathButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel drawingPanel;
    private javax.swing.JButton findPathButton;
    private javax.swing.JTextField goalNodeTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JTextField startNodeTextField;
    // End of variables declaration//GEN-END:variables

	//</editor-fold>
}
