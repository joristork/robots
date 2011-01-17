package javaBot;

/**
 * Simulated IR emitting ball used for RoboCup junior simulation. Instances of
 * the IRSensor can pick up the IR  signal emitted by the ball.
 */
public class Victim extends MovableObject implements IDragable
{
	// Info for the image to display on the ball
	private static final String	IMAGE_FILE			= Simulator
															.getRelativePath("./resources/rescue.gif");
	private static final int	DIAMETER_IN_IMAGE	= 100;
	private static final double	VICTIM_FRICTION		= 10.0;
	private static final double	VICTIM_DIAMETER		= 0.16;
	private static final double	VICTIM_MASS			= 1.0;
	private static final double	VICTIM_ELASTICITY	= 0.1;

	/**
	 * Constructor creates default ball, set properties afterwards if required
	 */
	public Victim()
	{
		super("victim", VICTIM_FRICTION, Math.random() * World.WIDTH, Math.random() * World.HEIGHT,
				VICTIM_DIAMETER, VICTIM_MASS);
		setElasticityFactor(VICTIM_ELASTICITY);
		setVelocityX(0);
		setVelocityY(0);
	}

	/**
	 * Send DistanceSensor information to robot
	 *
	 * @param r TODO PARAM: DOCUMENT ME!
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public double[] giveSensoryInformationTo(Robot r)
	{
		if (r == null)
		{
			return new double[] {0};
		}

		// Get default values for return array
		double[] returnValue = r.newSensorValues();

		// Give distance sensor info
		returnValue = super.giveSensoryInformationTo(r);

		return returnValue;
	}

	/**
	 * Return a GUI representation of a ball
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new GraphicalRepresentation(this, IMAGE_FILE, DIAMETER_IN_IMAGE, true);
	}
}
