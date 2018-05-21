package hw5.pathfinding;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Whenever the user clicks on the form, this object hears about it and has
 * a chance to do something based on it. The form being clicked on has to
 * say it wants to use this class in order for it to hear click events.
 * The code for that looks like this:
 *     drawingPanel.addMouseListener(new MouseClickHandler(this));
 *
 * @author baylor
 */
public class MouseClickHandler extends MouseAdapter
{
	private PathFindingForm listener;

	public MouseClickHandler(PathFindingForm listener)
	{
		this.listener = listener;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		Point clickPosition = e.getPoint();
		int x = clickPosition.x;
		int y = clickPosition.y;

		//--- Call the listener and tell them where the click happened
//		listener.onClick(x, y);
	}
}
