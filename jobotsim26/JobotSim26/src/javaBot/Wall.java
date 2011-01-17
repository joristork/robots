/*
 * Created on 20 Feb 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * Simulated wall used for RoboCup junior simulation.
 **/
package javaBot;

import java.awt.Polygon;
import java.awt.Rectangle;


/**
 * Wall object
 * Painted with jlabel
 * is a moveable object
 * 
 *
 * @version $Revision: 1.1 $ last changed Feb 20, 2006
 */
public class Wall extends NonMovableObject implements IDragable
{
	// Info for the image to display for the wall
	private static final String	IMAGE_FILE			= Simulator
															.getRelativePath("./resources/wall.gif");
	private static final int	DIAMETER_IN_IMAGE	= 50;
	private Line[]				wall;

	private double				width;
	private double				height;
	private double				x;
	private double				y;

	/**
	 * Constructor creates default wall, set properties afterwards if required
	 */
	public Wall()
	{
		super("Wall");
		diameter = 0.1;

		double wallWidth = getWidth();
		double wallHeight = getHeight();
		double wallX = (World.WIDTH / Simulator.pixelsPerMeter) - (wallWidth / 2);
		double wallY = (World.HEIGHT / Simulator.pixelsPerMeter) - (wallHeight / 2);
		
		location = new Location(World.WIDTH/2, World.HEIGHT/2);
		
		Line[] lWall;
		lWall = new Line[4];

		lWall[0] = new Line(new Location(wallX, wallY), new Location(wallX + wallWidth, wallY));
		lWall[1] = new Line(new Location(wallX, wallY), new Location(wallX, wallY + wallHeight));
		lWall[2] = new Line(new Location(wallX, wallY + wallHeight), new Location(
				wallX + wallWidth, wallY + wallHeight));
		lWall[3] = new Line(new Location(wallX + wallWidth, wallY), new Location(wallX + wallWidth,
				wallY + wallHeight));

		wall = doRotate(lWall, getRotation());
	}

	/**
	 * Give wall information to robot
	 *
	 * @param r Robot who want to have the information
	 * @return returnValue Sensor information
	 */
	public double[] giveSensoryInformationTo(Robot r)
	{
		double[] returnValue = r.newSensorValues();
		int w;

		for (int i = 0; i < r.getSensors().length; i++)
		{
			if (r.getSensors()[i] instanceof DistanceSensor)
			{
				DistanceSensor ds = (DistanceSensor) (r.getSensors()[i]);

				// Calc wall distances
				for (w = 0; w < 4; w++)
				{
					Line sl = ds.sensorLine(r.getLocation().getX(), r.getLocation().getY(),
							r.getRotation());
					double d = ds.convertDistanceToValue(sl.intersectDist(wall[w]));

					// If the current object is closer to the sensor than the
					// current reading, overwrite it.
					if ((d > 0) && (d > returnValue[i]))
					{
						returnValue[i] = d;
					}
				}
			}
		}

		return returnValue;
	}

	/**
	 *
	 * @return Return a GUI representation of a wall
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new GraphicalRepresentation(this, IMAGE_FILE, DIAMETER_IN_IMAGE, true);
	}

	/**
	 * Set the location of the wall
	 *
	 * @param OrgLocation current location
	 */
	public void setLocation(Location OrgLocation)
	{
		location = OrgLocation;

		double wallWidth = getWidth();
		double wallHeight = getHeight();
		double wallX = getX();
		double wallY = getY();

		// create box
		Line[] lWall;
		lWall = new Line[4];
		lWall[0] = new Line(new Location(wallX, wallY), new Location(wallX + wallWidth, wallY));
		lWall[1] = new Line(new Location(wallX, wallY), new Location(wallX, wallY + wallHeight));
		lWall[2] = new Line(new Location(wallX, wallY + wallHeight), new Location(
				wallX + wallWidth, wallY + wallHeight));
		lWall[3] = new Line(new Location(wallX + wallWidth, wallY), new Location(wallX + wallWidth,
				wallY + wallHeight));

		// rotate box
		wall = doRotate(lWall, getRotation());
	}

	/**
	 * Rotate 4 lines, use rotation
	 *
	 * @param Orignele 4 lijnen array 
	 * @return New rotated lines
	 */
	private Line[] doRotate(Line[] origional, double dRotation)
	{
		double dX;
		double dY;
		double dRotate = 0;
		double iLength = 0;
		
		double wallWidth = getWidth();
		double wallHeight = getHeight();
		double wallX = getX();
		double wallY = getY();

		
		Location lStartPosition;
		Line[] lReturn = new Line[origional.length];

		// Rotate every line seperatly
		for (int iLineCounter = 0; iLineCounter < origional.length; iLineCounter++)
		{

			// Line length
			iLength = origional[iLineCounter].getLength();
			dRotate = dRotation;
			
			if (iLineCounter == 0)
			{
				// Set first line on startposition
				// Draw from new position with angle with origional line length
				dX = (wallX + (wallWidth / 2)); //center
				dY = (wallY + (wallHeight / 2)); //center

				lStartPosition = new Location(dX, dY);

				// Set middle point of object to left bottom
				lStartPosition = toBasicPoint(lStartPosition, dRotation);
			}
			else
			{
				// Second, Third, Fourth
				// Ask previous line end-position
				lStartPosition = lReturn[iLineCounter - 1].getQ();

				// Which line is this?
				// Then rotate the line number with 90 degrees
				// to provide the square characteristics
				dRotate = dRotate + (Deg2Rad(90 * iLineCounter));
			}
			
			lReturn[iLineCounter] = new Line(lStartPosition, dRotate, iLength);
		}
		return lReturn;
	}

	/**
	 * @param startPosition Location with startposition on the center of the object
	 * @param dRealRotation Rotation of object in Radialen
	 * @return
	 */
	private Location toBasicPoint(Location startPosition, double dRealRotation)
	{
		Line lBuffer; 
		double iObjectHeight; 
		double dAngle; 

		/**
		 * dAngle = Angle e,d (Line e --> d)
		 * 
		 * a-----b
		 * |\    |
		 * | \   |
		 * |  e  |
		 * |     |
		 * |     |
		 * c-----d
		 * 
		 */
		dAngle = Math.tan((getWidth() / 2) / (getHeight() / 2));

		// Length e --> a
		iObjectHeight = Math.sqrt(Math.pow(getHeight() / 2, 2) + Math.pow(getWidth() / 2, 2));

		// Create line from e to a
		lBuffer = new Line(new Location(0, 0), dRealRotation + dAngle + Deg2Rad(90), iObjectHeight); // baken

		// Create line from a to c
		lBuffer = new Line(new Location(lBuffer.getQ().getX(), lBuffer.getQ().getY()),
				dRealRotation + Deg2Rad(90 + 180), getHeight());

		// Set new location
		startPosition.setX(startPosition.getX() + lBuffer.getQ().getX());
		startPosition.setY(startPosition.getY() + lBuffer.getQ().getY());

		return startPosition;
	}

	/**
	 * Calculate grades to radius
	 *
	 * @param Deg grades
	 *
	 * @return Radius
	 */
	private double Deg2Rad(double Deg)
	{
		return Deg / (180 / Math.PI);
	}

	/**
	 * @return current height of wall
	 */
	public double getHeight()
	{
		height = (this.getGraphicalRepresentation().getHeight() - 20) / Simulator.pixelsPerMeter;
		return height;
	}

	/**
	 * @return current width of wall
	 */
	public double getWidth()
	{
		width = this.getGraphicalRepresentation().getWidth() / Simulator.pixelsPerMeter;
		width *= 0.22;
		return width;
	}

	/**
	 * @return current position x of wall
	 */
	public double getX()
	{
		x = location.getX() - getWidth() / 2;
		return x;
	} 

	/**
	 * @return current position y of wall
	 */
	public double getY()
	{
		y = location.getY() - getHeight() / 2;
		return y;
	}
	
	/**
	 * Function for setting the rotation of the wall
	 * Needed because the wall has to be recalculated
	 * @param rotation Rotation of wall
	 */
	public void setRotation(double rotation)
	{
		super.setRotation(rotation);
		setLocation(getLocation());
	}
	/**
	 * Handle collisions with the surrounding walls.
	 *
	 * @param object The object the collision will be calculated
	 */
	public void collideWith(MovableObject object)
	{
		Polygon p = new Polygon();
		for(int i = 0; i < wall.length; i++)
		{
			Location l = wall[i].getQ();
			int wallX = (int)(l.getX() * Simulator.pixelsPerMeter);
			int wallY = (int)(l.getY() * Simulator.pixelsPerMeter);
			p.addPoint(wallX, wallY);
		}
		
		Location l = object.getLocation();
		
		int objectX = (int)(l.getX() * Simulator.pixelsPerMeter);
		int objectY = (int)(l.getY() * Simulator.pixelsPerMeter);
		int objectDiameter = (int)(object.diameter * Simulator.pixelsPerMeter);
		
		Rectangle r = new Rectangle(objectX, objectY, objectDiameter, objectDiameter);
		
		if(p.intersects(r))
		{
			object.setAccelerationX(0);
			object.setAccelerationY(0);
			object.setVelocityX(0);
			object.setVelocityY(0);
		}
	}	
}
