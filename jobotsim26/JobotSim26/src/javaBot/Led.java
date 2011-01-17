/*
 * Created on 20 Feb 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 */
package javaBot;

/**
 */
import java.awt.Color;

/**
 * Represents a basic led for use on the robot and is displayed in the
 * RobotGUI.
 */
public class Led extends Actor
{
	// The led's color
	private Color	color;

	/**
	 * Creates a new led with the specified color.
	 *
	 * @param color the led's color
	 * @param def the current value
	 */
	public Led(Color color, int def)
	{
		super("led", null, 0);
		this.color = color;
		this.setValue(def);
	}

	/**
	 * Creates a new led with the specified color.
	 *
	 * @param color the led's color
	 */
	public Led(Color color)
	{
		super("led", null, 0);
		this.color = color;
		this.setValue(1.0);
	}

	/**
	 * Overrides setValue from abstract actor class The led can either be on or
	 * off, so only two settings are possible
	 *
	 * @param value
	 */
	public void setValue(double value)
	{
		if (value > 0.0)
		{
			super.setValue(1.0);
		}
		else
		{
			super.setValue(0.0);
		}
	}

	/**
	 * @return Returns the color.
	 */
	public Color getColor()
	{
		return color;
	}
}
