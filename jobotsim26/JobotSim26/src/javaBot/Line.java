package javaBot;

import java.awt.Color;

/**
 * Used for calculating distances and angles, can be visualized in the GUI.
 * Created mosty by sensors and surrounding World objects.
 */
public class Line
{
	/** The base point */
	private Location							p;

	/** The second point */
	private Location							q;

	/** treat line as infinitely long? */
	private boolean								ignoreLineEnds	= false;

	/** Color, in case it should be changed */
	private Color								lineColor		= Color.RED;

	/** Used to draw lines in the gui */
	public static LineGraphicalRepresentation	linePane;

	/**
	 * Creates a line between two given locations
	 *
	 * @param p The base point
	 * @param q Second point
	 */
	public Line(Location p, Location q)
	{
		this.p = p;
		this.q = q;
		LineGraphicalRepresentation.lines.add(this);
	}

	/**
	 * Creates a new Line object from a point at a given angle and length.
	 *
	 * @param p The base point
	 * @param angle The angle of the line
	 * @param length The length of the line
	 */
	public Line(Location p, double angle, double length)
	{
		this.p = p;

		//Calculate the position of the second point
		q = new Location(p.getX() + (Math.cos(angle) * length), p.getY()
				+ (Math.sin(angle) * length));

		LineGraphicalRepresentation.lines.add(this);
	}

	/**
	 * Get the angle of this line object
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double getAngle()
	{
		double dx = q.getX() - p.getX();
		double dy = q.getY() - p.getY();

		return Math.atan2(dy, dx);
	}

	/**
	 * Get the length of this line object (possibly infinity)
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double getLength()
	{
		if (ignoreLineEnds)
		{
			return Double.POSITIVE_INFINITY;
		}

		double dx = q.getX() - p.getX();
		double dy = q.getY() - p.getY();

		return Math.sqrt((dx * dx) + (dy * dy));
	}

	/**
	 * Calculate the distance from a point to this line
	 *
	 * @param loc Location of the point
	 *
	 * @return distance in meters
	 */
	public double distancePointToLine(Location loc)
	{
		// Construct a line perpendicular to the current line        
		Line tempLine = new Line(loc, getAngle() + (Math.PI / 2), 0.3);
		tempLine.ignoreLineEnds = true;
		tempLine.lineColor = java.awt.Color.BLUE;

		double dist = Math.abs(tempLine.intersectDist(this));

		// Hide the line
		LineGraphicalRepresentation.lines.remove(tempLine);

		// Get the distance to the intersection of tempLine and the current line
		return dist ;//-0.04;
	}

	/**
	 * Initialize the graphical representation.
	 */
	public static void init()
	{
		if (linePane == null)
		{
			linePane = new LineGraphicalRepresentation();
		}
	}

	/**
	 * Calculate the intersection point with line b
	 *
	 * @param b Line to intersect with
	 *
	 * @return the location where the lines cross
	 */
	public Location intersect(Line b)
	{
		Location intsec = new Location(
				((p.getX() * Math.cos(b.getAngle()) * Math.sin(getAngle())) - (Math.cos(getAngle()) * (((p
						.getY() - b.p.getY()) * Math.cos(b.getAngle())) + (b.p.getX() * Math.sin(b
						.getAngle())))))
						/ Math.sin(getAngle() - b.getAngle()),
				((b.p.getY() * Math.cos(b.getAngle()) * Math.sin(getAngle())) - (((p.getY() * Math
						.cos(getAngle())) + ((b.p.getX() - p.getX()) * Math.sin(getAngle()))) * Math
						.sin(b.getAngle())))
						/ Math.sin(getAngle() - b.getAngle()));

		// Check if the intersection is in fact within the length of both lines
		if (checkLocationInLine(intsec))
		{
			return intsec;
		}

		return null;
	}

	/**
	 * Check if a point is within a line segment
	 *
	 * @param l the point to check
	 *
	 * @return true or false
	 */
	public boolean checkLocationInLine(Location l)
	{
		if (ignoreLineEnds == false)
		{
			if (l == null)
			{
				Debug.printError("Cannot compare to null object");

				return false;
			}

			double minX = Math.min(p.getX(), q.getX()) - 0.001;
			double minY = Math.min(p.getY(), q.getY()) - 0.001;

			double maxX = Math.max(p.getX(), q.getX()) + 0.001;
			double maxY = Math.max(p.getY(), q.getY()) + 0.001;

			//Debug.printDebug(minX + " " + minY + " " + maxX + " " + maxY + " " + l.toString() + " " + this.toString());
			if ((l.getX() >= minX) && (l.getX() <= maxX) && (l.getY() >= minY)
					&& (l.getY() <= maxY))
			{
				return true;
			}

			return false;
		}

		return true;
	}

	/**
	 * Calculate the distance from a line through one point to  the
	 * intersection with another line
	 *
	 * @param b Line to intersect with
	 *
	 * @return distance in meters (or infinity)
	 */
	public double intersectDist(Line b)
	{
		// Get intersection point
		Location intsec = intersect(b);

		// No intersection at all
		if (intsec == null)
		{
			return Double.POSITIVE_INFINITY;
		}

		//Debug.printDebug(toString() + " " + b.toString() + " Found intsec at " + intsec.toString());
		// Check if the intersection is in fact within the length of both lines
		if (!b.checkLocationInLine(intsec))
		{
			return Double.POSITIVE_INFINITY;
		}

		return -(((p.getY() - b.p.getY()) * Math.cos(b.getAngle())) + ((-p.getX() + b.p.getX()) * Math
				.sin(b.getAngle())))
				/ Math.sin(getAngle() - b.getAngle());
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param b TODO PARAM: param description
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double intersectDist2(Line b)
	{
		Location intsec = intersect(b);

		if (intsec == null)
		{
			return Double.POSITIVE_INFINITY;
		}

		if (!b.checkLocationInLine(intsec) || !this.checkLocationInLine(intsec))
		{
			return Double.POSITIVE_INFINITY;
		}

		return Math.sqrt(((p.getY() - intsec.getY()) * (p.getY() - intsec.getY()))
				+ ((p.getX() - intsec.getX()) * (p.getX() - intsec.getX())));
	}

	/**
	 * Return string representation of this object
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public String toString()
	{
		return "Line of " + getLength() + " meters through " + p.toString() + " at an angle of "
				+ (getAngle() / Math.PI) + " PI to " + q.toString();
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the linePane.
	 */
	public static LineGraphicalRepresentation getLinePane()
	{
		return linePane;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param linePane The linePane to set.
	 */
	public static void setLinePane(LineGraphicalRepresentation linePane)
	{
		Line.linePane = linePane;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the ignoreLineEnds.
	 */
	public boolean isIgnoreLineEnds()
	{
		return ignoreLineEnds;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param ignoreLineEnds The ignoreLineEnds to set.
	 */
	public void setIgnoreLineEnds(boolean ignoreLineEnds)
	{
		this.ignoreLineEnds = ignoreLineEnds;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the lineColor.
	 */
	public Color getLineColor()
	{
		return lineColor;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param lineColor The lineColor to set.
	 */
	public void setLineColor(Color lineColor)
	{
		this.lineColor = lineColor;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the p.
	 */
	public Location getP()
	{
		return p;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param p The p to set.
	 */
	public void setP(Location p)
	{
		this.p = p;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @return Returns the q.
	 */
	public Location getQ()
	{
		return q;
	}

	/**
	 * DOCUMENT ME!
	 *
	 * @param q The q to set.
	 */
	public void setQ(Location q)
	{
		this.q = q;
	}
}
