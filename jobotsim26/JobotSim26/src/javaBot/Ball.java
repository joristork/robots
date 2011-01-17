/*
 * Created on Jun 18, 2004
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * Simulated IR emitting ball used for RoboCup junior simulation. Instances of the IRSensor can pick up the IR
 * signal emitted by the ball.
 */
package javaBot;

/**
 * DOCUMENT ME!
 *
 * @version $Revision: 1.1 $ last changed Feb 17, 2006
 */
public class Ball extends MovableObject implements IDragable
{
	// Info for the image to display on the ball
	private static final String	IMAGE_FILE			= Simulator
															.getRelativePath("./resources/ball.gif");
	private static final int	DIAMETER_IN_IMAGE	= 100;

	/**
	 * Constructor creates default ball, set properties afterwards if required
	 */
	public Ball()
	{
		super("ball", 0.5, Math.random() * World.WIDTH, Math.random() * World.HEIGHT, 0.1, 1.);
		setElasticityFactor(0.5);
	}

	/**
	 * Checks if any of the objects in the field has an IR sensor and  if so,
	 * it gives an approximate value to the sensor based on the distance
	 * between the sensor and the ball.
	 *
	 * @param r TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double[] giveSensoryInformationTo(Robot r)
	{
		// Get default values for return array
		double[] returnValue = r.newSensorValues();

		// First give distance sensor some info
		returnValue = super.giveSensoryInformationTo(r);

		// Loop through the robot's sensor to locate an IRSensor
		for (int i = 0; i < r.getSensors().length; i++)
		{
			if (r.getSensors()[i] instanceof IRSensor)
			{
				IRSensor irs = (IRSensor) r.getSensors()[i];

				// Check if sensor has this object within it's field of vision
				double retval = irs.checkFOV(r.getLocation(), r.rotation, this.getLocation());

				if (retval > 0)
				{
					// Debug.printDebug("IR distance " + retval);
					returnValue[i] = retval;
				}
			}
		}

		return returnValue;
	}

	/**
	 * Return a GUI representation of a ball
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new GraphicalRepresentation(this, IMAGE_FILE, DIAMETER_IN_IMAGE);
	}

}
