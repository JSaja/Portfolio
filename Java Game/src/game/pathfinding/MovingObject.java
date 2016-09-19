package hw5.pathfinding;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 * An object that moves from one location to another.
 * This class is part of the GUI, not the graph or path finder.
 * As such, we're giving you this code. It works fine, no need to change anything.
 *
 * @author baylor
 */
public class MovingObject implements ActionListener
{
	private PathFindingForm listener;
	private Timer timer;
	private float realX, realY;
	private int endX, endY;
	private float xPerTimeStep, yPerTimeStep;

	public String   name;
	public int x, y;
	public int updateFrequencyInMS = 15, movementTimeInMS = 500;

	public MovingObject(String name, PathFindingForm listener)
	{
		this.name     = name;
		this.listener = listener;
	}

	public void setPath(int startX, int startY, int endX, int endY)
	{
		//--- Allows us to move fractions of a pixel
		realX = startX;
		realY = startY;

		x = startX;
		y = startY;
		this.endX = endX;
		this.endY = endY;
		int horizontalDistanceToMove = endX - startX;
		int verticalDistanceToMove   = endY - startY;
		float xPerMS = (float)horizontalDistanceToMove / movementTimeInMS;
		float yPerMS = (float)verticalDistanceToMove   / movementTimeInMS;
		xPerTimeStep = xPerMS * updateFrequencyInMS;
		yPerTimeStep = yPerMS * updateFrequencyInMS;
	}

	public void start()
	{
		if (null != timer)
		{
			timer.stop();
		}

		timer = new Timer(updateFrequencyInMS, this);
		timer.start();
	}
	public void stop()
	{
		timer.stop();
		listener.onMoveEnd();
	}

	/**
	 * The timer will call this method every however often we told it.
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		realX += xPerTimeStep;
		realY += yPerTimeStep;
		x = Math.round(realX);
		y = Math.round(realY);
		listener.updatePosition();
		if ((Math.abs(x-endX) < 2) && (Math.abs(y-endY) < 2))
		{
			stop();
		}
	}
}
