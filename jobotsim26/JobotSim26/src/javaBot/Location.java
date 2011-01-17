package javaBot;

/**
 * Represents a point in a 2d space
 */
public class Location
{
	/** the x coordinate of this point */
	private double	x;

	/** the y coordinate of this point */
	private double	y;

	/**
	 * Creates a new Location object.
	 */
	Location()
	{
	}

	/**
	 * Creates a new Location object based on an x,y coordinate.
	 *
	 * @param x x coordinate
	 * @param y y coordinate
	 */
	public Location(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	/**
	 * Adds both x and y coordinates of point p to this point's coordinates and
	 * returns the result.
	 *
	 * @param p the point to add to this point
	 *
	 * @return the addition of the two points
	 */
	public Location add(Location p)
	{
		return new Location(x + p.x, y + p.y);
	}

	/**
	 * Substracts both x and y coordinates of point p from this point's
	 * coordinates and returns the result.
	 *
	 * @param p the point to substract from this point
	 *
	 * @return the substraction of the two points
	 */
	public Location substract(Location p)
	{
		return new Location(x - p.x, y - p.y);
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return x
	 */
	public double getX()
	{
		return x;
	}

	/**
	 * Get the y value of this location
	 *
	 * @return y
	 */
	public double getY()
	{
		return y;
	}

	/**
	 * Set the x value of this location
	 *
	 * @param d new x
	 */
	public void setX(double d)
	{
		x = d;
	}

	/**
	 * Set the y value of this location
	 *
	 * @param d new y
	 */
	public void setY(double d)
	{
		y = d;
	}

	/**
	 * Get the distance between two locations
	 *
	 * @param p location to determine distance to
	 *
	 * @return distance in meters
	 */
	public double distanceTo(Location p)
	{
		return Math.sqrt(((p.x - x) * (p.x - x)) + ((p.y - y) * (p.y - y)));
	}

	/**
	 * Return string representation of this object
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public String toString()
	{
		return "Location object at [" + x + "," + y + "]";
	}
}
