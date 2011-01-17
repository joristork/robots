/*
 * Created on Jun 7, 2004
 */
package javaBot;

import java.awt.Color;

/**
 * Represents the 4 walls surrounding the field in the GUI
 */
public class SurroundingWalls extends NonMovableObject
{
	/** Contains the 4 lines wich represent tte 4 walls */
	private Line[]	wall;

	/** Height of the field */
	private double	height;

	/** Width of the field */
	private double	width;

	/** How much speed is preserved when bouncing on the wall */
	private double	wallBounceFactor	= 1;

	/**
	 * Creates a new SurroundingWalls object.
	 *
	 * @param name name of the object
	 * @param width WIDTH of the field
	 * @param height heigth of the field
	 */
	SurroundingWalls(String name, double height, double width)
	{
		super(name);

		this.height = height;
		this.width = width;
		this.location = new Location();
		wall = new Line[4];
		
		resizeWalls(height, width);
	}

	/**
	 * Handle collisions with the surrounding walls.
	 *
	 * @param object TODO PARAM: DOCUMENT ME!
	 */
	public void collideWith(MovableObject object)
	{
		//methode is copy pasted from old code it'inst conform nature laws at all!
		if (object.getLocation().getX() < (object.diameter / 2))
		{
			object.getLocation().setX(object.diameter / 2);
			object.setVelocityX(Math.abs(object.getVelocityX()) * object.getElasticityFactor());
			object.setAccelerationX(object.getAccelerationX() * -object.getElasticityFactor());
		}

		if (object.getLocation().getY() < (object.diameter / 2))
		{
			object.getLocation().setY(object.diameter / 2);
			object.setVelocityY(Math.abs(object.getVelocityY()) * object.getElasticityFactor());
			object.setAccelerationX(object.getAccelerationX() * -object.getElasticityFactor());
		}

		if (object.getLocation().getX() > (height - (object.diameter / 2)))
		{
			object.getLocation().setX(height - (object.diameter / 2));
			object.setVelocityX(-Math.abs(object.getVelocityX()) * object.getElasticityFactor());
			object.setAccelerationX(object.getAccelerationX() * -object.getElasticityFactor());
		}

		if (object.getLocation().getY() > (width - (object.diameter / 2)))
		{
			object.getLocation().setY(width - (object.diameter / 2));
			object.setVelocityY(-Math.abs(object.getVelocityY()) * object.getElasticityFactor());
			object.setAccelerationX(object.getAccelerationX() * -object.getElasticityFactor());
		}
	}

	/**
	 * Give sensor information to the distance sensors of a robot.
	 *
	 * @param r TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double[] giveSensoryInformationTo(Robot r)
	{
		double[] returnvalue = r.newSensorValues();
		int sens;
		int w;
		Location pos;
		double angle;

		for (int i = 0; i < r.getSensors().length; i++)
		{
			if (r.getSensors()[i] instanceof DistanceSensor)
			{
				DistanceSensor ds = (DistanceSensor) (r.getSensors()[i]);

				// Calc wall distances
				for (w = 0; w < 4; w++)
				{
					Line sl = ds.sensorLine(r.getLocation().getX(), r.getLocation().getY(),
							r.rotation);
					
  					double d = ds.convertDistanceToValue(sl.intersectDist(wall[w]));

					// If the current object is closer to the sensor than the
					// current reading, overwrite it.
					//if (i==0) Debug.printDebug(d + " > " + returnvalue[i]);    
					if ((d > 0) && (d > returnvalue[i]))
					{
						returnvalue[i] = d;
					}
				}
			}
		}

		return returnvalue;
	}

	/**
	 * Return nothing for the graphicalRepresentation
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return null;
	}

	public void resizeWalls(double newWidth, double newHeight)
	{
		this.height = newHeight;
		this.width = newWidth;
		
		// Values are converted to pixels in LineGraphicalRepresentation.java
		// TODO: Warning: mind that heightInMeters uses this.width!!!
		// this is because these values are passed incorrectly to the constructor.
		double heightInMeters = this.width;
		double widthInMeters = this.height;
		
		// These values are used to set the LinePane's size.
		// TODO: Warning: heightInPixels DOES use this.height! This code was build
		// on the incorrect height & width.
		int heightInPixels = (int)(this.height * Simulator.pixelsPerMeter);
		int widthInPixels = (int)(this.width * Simulator.pixelsPerMeter);
		
		// Breedte en Hoogte van het linesPanel zijn in de code omgedraaid
		if (Line.getLinePane() != null)
			Line.getLinePane().setSize(heightInPixels, widthInPixels);
		
		wall[0] = new Line(new Location(0, 0), new Location(widthInMeters, 0));
		wall[1] = new Line(new Location(0, 0), new Location(0, heightInMeters));
		wall[2] = new Line(new Location(widthInMeters, heightInMeters), new Location(0, heightInMeters));
		wall[3] = new Line(new Location(widthInMeters, 0), new Location(widthInMeters, heightInMeters));
		
		wall[0].setLineColor(Color.WHITE);
		wall[1].setLineColor(Color.WHITE);
		wall[2].setLineColor(Color.WHITE);
		wall[3].setLineColor(Color.WHITE);
	}
}
