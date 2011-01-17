/*
 * created on 08-06-2005
 */
package javaBot;

import java.awt.Image;
import java.io.File;

import javax.imageio.ImageIO;

/**
 *
 * Respresents the floor in a roboCup junior rescue game. Copy of Playingfield!
 * TODO: extract common Playingfield/RescueField properties into common baseclass
 * @see javaBot.PlayingField - almost the same
 * 
 */
public class RescueField extends FieldSensorInformation
{

	/**
	 * Constructs a RescueField object
	 */
	public RescueField()
	{
		super("RescueField");

		Simulator.IMAGE_FILE	= Simulator.getRelativePath("./resources/rescueveldsimul.gif");
		File file = new File(Simulator.IMAGE_FILE);
		try
		{
			img = ImageIO.read(file);
			Simulator.IMAGE = img;		}
		catch (Exception e)
		{
			Debug.printError("An error occurred in RescueField: " + e.toString());
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
	 * @return GraphicalRepresentation of the RescueField
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new RescueFieldGraphicalRepresentation(this);
	}

}

/**
 * Visual representation of the rescueField
 */
class RescueFieldGraphicalRepresentation extends GraphicalRepresentation
{
	Image image = null;
	/**
	 * Creates a new RescueGraphicalRepresentation object.
	 *
	 * @param obj The object to create a representation for
	 */
	public RescueFieldGraphicalRepresentation(PhysicalObject obj)
	{
		super(obj);
		Simulator.IMAGE_FILE	= Simulator.getRelativePath("./resources/rescueveldsimul.gif");
		setSize(Simulator.meterToPixel(World.WIDTH), Simulator.meterToPixel(World.HEIGHT));
		setOpaque(false);
		setVisible(true);
		layer = Simulator.BACKGROUND_LAYER;
		width = getWidth();
		height = getHeight();
		File file = new File(Simulator.IMAGE_FILE);
		try
		{
			image = ImageIO.read(file);
			Simulator.IMAGE = image;		}
		catch (Exception e)
		{
			Debug.printError("Error loading rescueField image " + Simulator.IMAGE_FILE + ": " + e.toString());
			e.printStackTrace();
		}

	}

	/**
	 * Draw the rescuefield on the graphics object. Will be done 25 times a second.
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

