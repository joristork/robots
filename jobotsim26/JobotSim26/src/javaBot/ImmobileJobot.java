package javaBot;

/**
 * Simulated immobile joBot, used for RoboCup junior Rescue simulation.
 */
public class ImmobileJobot extends MovableObject implements IDragable
{
	// Info for the image to display on the jobot
	private static final String	IMAGE_FILE			= Simulator
															.getRelativePath("./resources/jobot.gif");
	private static final int	DIAMETER_IN_IMAGE	= 100;

	/**
	 * Constructor creates default immobile jobot, set properties afterwards if
	 * required
	 */
	public ImmobileJobot()
	{
		super("StaticJobot", 0.1, Math.random() * World.WIDTH, Math.random() * World.HEIGHT, 0.18,
				15);
		setElasticityFactor(0.1);
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
		// Get default values for return array
		double[] returnValue = r.newSensorValues();

		// Give distance sensor info
		returnValue = super.giveSensoryInformationTo(r);

		return returnValue;
	}

	/**
	 * Return a GUI representation of a joBot
	 *
	 * @return $returnType$ TODO RETURN: return description
	 */
	public GraphicalRepresentation createGraphicalRepresentation()
	{
		return new GraphicalRepresentation(this, IMAGE_FILE, DIAMETER_IN_IMAGE);
	}
}
