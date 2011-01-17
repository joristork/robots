package javaBot;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.PixelGrabber;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision$
 * last changed 03-07-2006
 *
 * TODO CLASS: DOCUMENT ME! 
 */
public abstract class FieldSensorInformation extends NonMovableObject
{
	private String	name;
	protected Image img;
	
	// Mouse sensor resolution is 400 counts per inch, 1m = 39.37"
	final double scale = 400 * 39.37;
	
	/**
	 * Creates a new FieldSensorInformation object.
	 *
	 * @param name TODO PARAM: DOCUMENT ME!
	 */
	public FieldSensorInformation(String name)
	{
		super("field");
		this.name = name;
	}

	/**
	 * Returns the image of the map
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public abstract Image getImage();

	/**
	 * Returns the value of the pixel on the various fields according to the
	 * robot's position if the robot possesses a fieldsensor. This version also
	 * returns the distance the robot has moved, if the robot posesses a mouse
	 * sensor.
	 *
	 * @param r TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double[] giveSensoryInformationTo(Robot r)
	{
		double[] returnValue = r.newSensorValues();

		try
		{
		    Sensor[] sar = r.getSensors();
			// Loop through the robot's sensor to locate a FieldSensor (and MouseSensor)
			for (int i = 0; i < sar.length; i++)
			{
				if (sar[i] instanceof FieldSensor)
				{
					PixelGrab pixelGrab = new PixelGrab(Simulator.IMAGE);
					Sensor s = sar[i];
					Location sensorLocation = getSensorLocation(r, s); 
					
//					Location robotLocation = r.getLocation();
//					Location relativeSensorLocation = s.getPosition();
//					Location sensorLocation = new Location(robotLocation.getX()
//							+ relativeSensorLocation.getX(), robotLocation.getY()
//							+ relativeSensorLocation.getY());
					
					Point location = Simulator.toPixelCoordinates(sensorLocation);
//					int pg = pixelGrab.handlepixels(getImage(), location.x, location.y, 1, 1);
					int pg = pixelGrab.handlePixels(getImage(), location.x, location.y, 8, 8);
					returnValue[i] = (double) pg;					

					//Debug.printInfo("Pixelvalue("+location.x+","+location.y+")="+pg);
					
				} else if (sar[i] instanceof DXMouseSensor
		        		&& sar[i+1] instanceof DYMouseSensor){
				    DXMouseSensor sx = (DXMouseSensor)sar[i];
				    DYMouseSensor sy = (DYMouseSensor)sar[i+1];
					
					Location sensorLocation = getSensorLocation(r,sx);
					double dx = scale*(sensorLocation.getX() - sx.getLastLocation());
					double dy = scale*(sensorLocation.getY() - sy.getLastLocation());
					sx.setLastLocation(sensorLocation.getX());
					sy.setLastLocation(sensorLocation.getY());
					
					sx.setDistance((int)dx,(int)dy);
					returnValue[i]   = dx;
					returnValue[i+1] = dy;
				}
			}

			return returnValue;
		}
		catch (Exception e)
		{
			Debug.printError("An error occurred in " + name + ": " + e.toString());
		}

		return returnValue;
	}
	
	public Location getSensorLocation(Robot r, Sensor s)
	{
		double diam = Math.sqrt(Math.pow(s.getPosition().getX(), 2)
				+ Math.pow(s.getPosition().getY(), 2));
		double posX	= r.getLocation().getX();
		double posY = r.getLocation().getY();
		posX += Math.cos(s.getAngle() + r.getRotation()) * diam; 
		posY += Math.sin(s.getAngle() + r.getRotation()) * diam;
//		System.out.println("X=" + posX + ",Y=" + posY);
		return new Location(posX, posY);
	}
	

	/**
	 * Rescales the field sensor information image
	 * @param width: <strong>absolute<strong> width of Image
	 * @param heigth: <strong>absolute<strong> width of Image
	 */
	public void rescaleImage(int width, int heigth)
	{
		img=img.getScaledInstance(width,heigth,Image.SCALE_DEFAULT);	
	}
}
