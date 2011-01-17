/**
 * Created on Jun 25, 2005
 */
package javaBot;

/**
 * Represents all the boundaries surrounding the soccerfield
 */
public class SoccerWalls extends NonMovableObject
{
	/** Contains the lines wich represent the borders */
	Line[]	wall;

	/** Height of the field */
	double	height;

	/** Width of the field */
	double	width;

	/** How much speed is preserved when bouncing on the wall */
	double	wallBounceFactor	= 1;

	/**
	 * Creates a new SoccerWalls object.
	 * 
	 * @param name
	 *            name of the object
	 * @param height
	 *            heigth of the field
	 */
	public SoccerWalls(String name, double height)
	{
		super(name);
		this.location = new Location();
		this.height = height;
		resizeWalls();
	}
	
	public void resizeWalls()
	{
		this.height = World.HEIGHT;
		this.width = World.WIDTH;
		
		//Height:innerwidth ratio of RoboCup Jr. Soccerfield is 1830:1220

		double corner = (80 * height) / 1220;
		double offset = (45 * height) / 1220; /* 1/2 of corner */
		double leftside = (corner + offset);
		double rightside = World.WIDTH - (corner + offset);
		double nokeep = (385 * height) / 1220;

		wall = new Line[16];

		// walls
		wall[0] = new Line(new Location(rightside, 0), new Location(rightside, nokeep));
		wall[1] = new Line(new Location(rightside + corner, nokeep), new Location(rightside
				+ corner, height - nokeep));
		wall[2] = new Line(new Location(rightside, height - nokeep),
				new Location(rightside, height));
		wall[3] = new Line(new Location(leftside, height), new Location(leftside, height - nokeep));
		wall[4] = new Line(new Location(leftside - corner, height - nokeep), new Location(leftside
				- corner, nokeep));
		wall[5] = new Line(new Location(leftside, nokeep), new Location(leftside, 0));

		//horizontal pieces
		wall[6] = new Line(new Location(leftside - corner, nokeep), new Location(leftside, nokeep));
		wall[7] = new Line(new Location(leftside, height - nokeep), new Location(leftside - corner,
				height - nokeep));
		wall[8] = new Line(new Location(rightside, nokeep),
				new Location(rightside + corner, nokeep));
		wall[9] = new Line(new Location(rightside + corner, height - nokeep), new Location(
				rightside, height - nokeep));

		//diagonal corners
		wall[10] = new Line(new Location(leftside, corner), new Location(leftside + corner, 0));
		wall[11] = new Line(new Location(rightside - corner, 0), new Location(rightside, corner));
		wall[12] = new Line(new Location(rightside, height - corner), new Location(rightside
				- corner, height));
		wall[13] = new Line(new Location(leftside + corner, height), new Location(leftside, height
				- corner));
		
		//Top and Bottom line
		wall[14] = new Line(new Location(0,height), new Location(World.WIDTH,height));
		wall[15] = new Line(new Location(0,0), new Location(World.WIDTH,0));
	}

	/**
	 * Handle collisions with the surrounding walls.
	 * 
	 * @param object Do I collide with this object?
	 */
	public void collideWith(MovableObject object)
	{
		for (int i = 0; i < wall.length; i++)
		{
			Location pos = object.getLocation();
			double dist = wall[i].distancePointToLine(pos);
			double coef = wall[i].getAngle() + (Math.PI / 2);
			double rad = object.diameter / 2;

			if ((dist < rad) || (isObjectOutField(object, wall[i], i, rad)))
			{
				double elasticityFactor = 0.0;
				
				//TODO: Move following line to if statement!
				//object.setlocation is only needed when object is a ball.
				object.setLocation(new Location(pos.getX() + (rad * Math.cos(coef)), pos.getY()
						+ (rad * Math.sin(coef))));
				
				if(object instanceof Ball)
				{
					elasticityFactor = object.getElasticityFactor();
				}
				
				if (i < 6)
				{
					// Vertical
					object.setVelocityX(-object.getVelocityX() * elasticityFactor);
				}
				else if (i < 10)
				{
					// Horizontal
					object.setVelocityY(-object.getVelocityY() * elasticityFactor);
				}
				else if (i < 14)
				{
					// Corners
					double temp = object.getVelocityY();
					object.setVelocityY(object.getVelocityX() * elasticityFactor);
					object.setVelocityX(temp * elasticityFactor);
				}
				else if (i == 14)
				{
					// Top line
					double objectX = object.getLocation().getX();
					double objectY = object.getLocation().getY() - rad - 0.04;
					object.setLocation(new Location(objectX,objectY));
					object.setVelocityY(-object.getVelocityY() * elasticityFactor);
				}
				else if (i == 15)
				{
					// bottom line
					object.setVelocityY(-object.getVelocityY() * elasticityFactor);
				}

				object.setAccelerationX(object.getAccelerationX() * -elasticityFactor);
				break;
			}
		}
	}

	/**
	 * @param object Ball, Robot, every object who can not be out of the soccerfield
	 * @param currentWall
	 * @param lineNumber The number of wall, can be used to view which type of wall this is.
	 * @param objectRadius Radius of object
	 * @return true if ball is no longer in the soccer field
	 */
	public boolean isObjectOutField(MovableObject object, Line currentWall, int lineNumber,
			double objectRadius)
	{
		double objectX = object.getLocation().getX();
		double objectY = object.getLocation().getY();
		double wallStartX = currentWall.getP().getX();
		double wallStartY = currentWall.getP().getY();
		double wallEndX = currentWall.getQ().getX();
		double wallEndY = currentWall.getQ().getY();

		objectX += objectRadius;
		objectY += objectRadius;

		switch (lineNumber)
		{
			// Left walls
			case 4:
				return ((objectX < wallStartX) && (objectY > wallStartY) && (objectY < wallEndY));
			case 3:
			case 5:
				return ((objectX < wallStartX) && (objectY < wallStartY) && (objectY > wallEndY));

			// Right walls
			case 0:
			case 1:
			case 2:
				return ((objectX > wallStartX) && (objectY > wallStartY) && (objectY < wallEndY));

			// Upper goal walls
			case 7:
			case 9:
				return (objectY > wallEndY) && ((wallStartX > objectX) && (wallEndX < objectX));

			// lower goal walls
			case 6:
			case 8:
				return (objectY < wallEndY) && ((wallStartX < objectX) && (wallEndX > objectX));
			default:
				return false;
		}
	}

	/**
	 * Give sensor information to the distance sensors of a robot.
	 * 
	 * @param r
	 *            TODO PARAM: DOCUMENT ME!
	 * 
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double[] giveSensoryInformationTo(Robot r)
	{
		double[] returnvalue = r.newSensorValues();

		for (int i = 0; i < r.getSensors().length; i++)
		{
			if (r.getSensors()[i] instanceof DistanceSensor)
			{
				DistanceSensor ds = (DistanceSensor) (r.getSensors()[i]);

				// Calc wall distances
				for (int wallCounter = 0;  wallCounter < wall.length;  wallCounter++)
				{
					Line sl = ds.sensorLine(r.getLocation().getX(), r.getLocation().getY(),
							r.rotation);
					double d = ds.convertDistanceToValue(sl.intersectDist(wall[wallCounter]));

					// If the current object is closer to the sensor than the
					// current reading, overwrite it.
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
	 * @return null
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return null;
	}

	/**
	 * Return the wall object
	 * 
	 * @return wall Return the wall object
	 */
	public Line[] getWall()
	{
		return wall;
	}

}
