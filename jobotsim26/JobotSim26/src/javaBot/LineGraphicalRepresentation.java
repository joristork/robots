package javaBot;

import java.util.Vector;
import javax.swing.border.LineBorder;

/**
 * 
 * Keeps a vector list of ALL the Line objects that are created and paints them on updates.
 * The Simulator clears the list on every simulator screen refresh (aka frame).
 * <br>This class in in fact one JLabel with the size of the World. 
 * PaintComponent on this JLabel will paint all Lines in the vector. 
 * 
 * @version $Revision$
 * last changed 23-02-2006
 *
 * 
 *
 * @see javaBot.Line 
 * @see Simulator#update()
 *   
 */
class LineGraphicalRepresentation extends javax.swing.JLabel
{
	/** Show lines or not */
	public static boolean	showLines	= true;

	/** All the lines to draw this frame */
	public static Vector	lines		= new Vector();

	/**
	 * Create a new lineGraphicalRepresentation, in fact one JLabel with the size of the World.
	 */
	public LineGraphicalRepresentation()
	{
		super("lineLabel");
		setSize(Simulator.meterToPixel(World.WIDTH), Simulator.meterToPixel(World.HEIGHT));
		setOpaque(false);
		setVisible(true);
		setBorder(LineBorder.createBlackLineBorder());
	}

	/**
	 * Paint the LineGraphicalRepresentation by drawing all Lines on this JLabel.
	 *
	 * @param g standard Graphics object
	 */
	protected void paintComponent(java.awt.Graphics g)
	{
		if (showLines == true)
		{	
			for (int i = 0; i < lines.size(); i++)
			{
				Line l = (Line) lines.get(i);

				// Draw all the lines that have been created
				g.setColor(l.getLineColor());

				// Get current point
				int x1 = Simulator.meterToPixel(l.getP().getX());
				int y1 = Simulator.meterToPixel(World.HEIGHT - l.getP().getY());

				// Get second point
				int x2 = Simulator.meterToPixel(l.getQ().getX());
				int y2 = Simulator.meterToPixel(World.HEIGHT - l.getQ().getY());

				g.drawLine(x1, y1, x2, y2);
			}
		}
	}
}
