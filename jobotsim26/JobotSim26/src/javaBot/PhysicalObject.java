package javaBot;

/**
 * Abstract class that should be extended by every object in the simulation
 * that should have any physical properties.
 */
public abstract class PhysicalObject
{
	/** The name of the object */
	public String					name;

	/** The graphical representation of this object in the GUI */
	public GraphicalRepresentation	graphicalRepresentation	= null;

	/** The absolute location in the world measured in meters */
	public Location					location;

	/**
	 * Diameter of the object in meters for now suppose that every object is a
	 * circle
	 */
	public double					diameter				= 0;

	/** Rotation of the object,in radians, clockwise is positive */
	public double					rotation				= 0;

	/** Mass of the object in kg */
	public double					mass;

	/**
	 * Creates a new PhysicalObject.
	 *
	 * @param name The name of the object
	 */
	public PhysicalObject(String name)
	{
		this.name = name;
		Debug.printInfo("Created physical Object: " + name);
	}

	/**
	 * Given the robot r return a array of sensor values the sensors of r will
	 * receive
	 *
	 * @param r The robot whose sensors you want to calculate the sensor values
	 *        of
	 *
	 * @return array of sensor values in ascending order of sensor number
	 */
	public abstract double[] giveSensoryInformationTo(Robot r);

	/**
	 * Returns a graphical object to represent this object. If the
	 * graphicalRepresentation has been set on the object it returns that
	 * object, otherwise is will call createGraphicalRepresentation(). This
	 * prevents the GraphicalRepresentation object being created at every
	 * frame
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public final GraphicalRepresentation getGraphicalRepresentation()
	{
		if (graphicalRepresentation == null)
		{
			graphicalRepresentation = createGraphicalRepresentation();
		}

		return graphicalRepresentation;
	}

	/**
	 * If no graphical representation has been created yet for the object this
	 * method is called and it should therefore be overridden by the subclass
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public abstract GraphicalRepresentation createGraphicalRepresentation();

	/**
	 * Returns the object's location in the world..
	 *
	 * @return Location representing world coordinates measured in meters
	 */
	public Location getLocation()
	{
		return location;
	}

	/**
	 * Set the object's location in the world..
	 *
	 * @param l TODO PARAM: DOCUMENT ME!
	 */
	public void setLocation(Location l)
	{
		location = l;
	}
	
	/**
	 * Returns the object's rotation in the world..
	 *
	 * @return Double representing the rotation
	 */
	public double getRotation()
	{
		return rotation;
	}

	/**
	 * Set the object's rotation in the world.
	 *
	 * @param rotation Rotation set for the object
	 */
	public void setRotation(double rotation)
	{
		this.rotation = rotation;
	}	

	/**
	 * toString, just to be nice
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public String toString()
	{
		return this.name;
	}
}
