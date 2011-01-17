/*
 * Created on Feb 20, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * Represents the floor in a RoboCup junior game.
 * It has a gradient printed on it so the robot can determine
 * it's approximate location using the FieldSensor
 */
package javaBot;

import java.awt.Image;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Represents the floor in a RoboCup junior game.  It has a gradient printed on
 * it so the robot can determine  it's approximate location using the
 * FieldSensor
 * @see javaBot.RescueField - almost exact the same
 * @see javaBot.DanceFloor
 */
public class PlayingField extends FieldSensorInformation
{
	
	/**
	 * Constructs a PlayingField object
	 */
	public PlayingField()
	{
		super("PlayingField");

		Simulator.IMAGE_FILE = Simulator.getRelativePath("./resources/field.gif");
		File file = new File(Simulator.IMAGE_FILE);
		try
		{
			img = ImageIO.read(file);
			Simulator.IMAGE = img;		}
		catch (Exception e)
		{
			Debug.printError("An error occurred in PlayingField: " + e.toString());
		}

		// Set the location of the field in the middle of the gui 
		this.location = new Location(World.WIDTH / 2, World.HEIGHT / 2);
	}

	/**
	 * Returns the image of the map so the FieldSensor can use it.
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public Image getImage()
	{
		return img;
	}

	/**
	 * It is impossible to collide with the floor currently (no jumping robots)
	 * therefore this method doesn't return any collision information
	 *
	 * @param object The object to not collide with
	 */
	public void collideWith(MovableObject object)
	{
	}

	/**
	 * Create a graphical object to represent the floor
	 *
	 * @return GraphicalRepresentation of the playingField
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new PlayingFieldGraphicalRepresentation(this);
	}
}


/**
 * Visual representation of the PlayingField
 */
class PlayingFieldGraphicalRepresentation extends GraphicalRepresentation
{
	Image image = null;

	/**
	 * Creates a new MazeGraphicalRepresentation object.
	 *
	 * @param obj The object to create a representation for
	 */
	public PlayingFieldGraphicalRepresentation(PhysicalObject obj)
	{
		super(obj);
		
		Simulator.IMAGE_FILE = Simulator.getRelativePath("./resources/field.gif");
		setSize(Simulator.meterToPixel(World.WIDTH), Simulator.meterToPixel(World.HEIGHT));
		setOpaque(false);
		setVisible(true);
		
		width = getWidth();
		height = getHeight();
		
		layer = Simulator.BACKGROUND_LAYER;
		File file = new File(Simulator.IMAGE_FILE);
		try
		{
			image = ImageIO.read(file);
			Simulator.IMAGE = image;		}
		catch (Exception e)
		{
			Debug.printError("Error loading playingField image " + Simulator.IMAGE_FILE + ": " + e.toString());
			e.printStackTrace();
		}
	}

	/**
	 * Draw the playingfield on the graphics object. Will be done 25 times a second.
	 *
	 * @param g java.awt.Graphics object
	 */
	public void paintComponent(java.awt.Graphics g)
	{
		g.drawImage(image,0,0, width, height,this);
	}
	
	public void setWidth(int newWidth) 
	{
		width = newWidth;
		setSize(width, getHeight());
	}
	public void setHeight(int newHeight)
	{
		height = newHeight;
		setSize(getWidth(), height);
	}
}
