/*
 * Created on Jun 14, 2004
 */
package javaBot.agents;

import javaBot.Debug;
import javaBot.Jobot;
import javaBot.LightBot;
import javaBot.ReflectionSensor;
import javaBot.Robot;

/**
 * 
 * The BallLover agent wants to go to "distToBall" distance to an IR emitting source
 */
public class BallLover extends JobotAgent
{
	/** distance the ball lover wants to keep to the ball (this can be 0!) */
	private double	distToBall;
	
	// preferred distance to ball
	private final static double PREF_DIST_TO_BALL = 0.08;

	// Which way was I turning again?
	private double	turningSide	= 1;

	/**
	 * Creates a new BallLover object.
	 */
	public BallLover()
	{
		super();
	}

	/**
	 * construct a new BallLover
	 * 
	 * @param robot the robot this agent is working on
	 *        
	 */
	public BallLover(Robot robot)
	{
		super(robot);
	}

	/**
	 * start the thread
	 */
	public void run()
	{
		// if running is set false the thread must end
		setRunning(true);
		// use a random preferred distance to the ball 
		//distToBall = Math.random();
		distToBall = PREF_DIST_TO_BALL; // this seems to keep them alert
		Debug.printDebug(getRobot().name + " preferred distance: " + distToBall);

		if (getRobot() == null)
		{   // this should never happen!
			return;
		}

		// Check if the ballLover is started for the lightBot. This is the only bot
		// that has the correct configuration.
		if(getRobot() instanceof LightBot) {			
			try
			{
				while (isRunning())
				{
					heartbeat();
					startLoving();
					sleep(500);
				}
			}
			catch (Exception e)
			{
				Debug.printError("Error in BallLover: " + e.toString());
				e.printStackTrace();
			}
		} else {
			Debug.printInfo("This robot does not support the BallLover. Only the LightBot has the correct Sensor configuration.");
		}
	}

	// Start looking for a ball to love
	public void startLoving() throws Exception
	{
		findBall();
	}

	// Try to locate an IR source
	private void findBall() throws Exception
	{
		// Determine turning direction
		if (getRobot().getSensors()[1].getValue() > 10)
		{ // left
			turningSide = 1;
		}

		if (getRobot().getSensors()[2].getValue() > 10)
		{ // right
			turningSide = -1;
		}

		while (isRunning())
		{
			vectorDrive(0, 0, (turningSide * Math.PI) / 2); // rotate some
			sleep(20);
			// if the "south" (Y-direction) sensor sees something, stop turning
			if (getRobot().getSensors()[0].getValue() > 20)
			{
				break;
			}
		}

			driveTowardsBall();
	}

	/** Go towards the ball. sensor 0 and 3 are the south-sensors and point towards the ball (if it's still there)
	 * todo: more documentation on this method
	 * @throws Exception 
	 */
	private void driveTowardsBall() throws Exception
	{
		Debug.printDebug("driveTowardsBall() Bot " + getRobot().name);
		while (isRunning())
		{
			// If we are to far from the ball (detected with the IRSensor) the reflection sensor can't detect  the ball
			// the loop will be ended.
			if (getRobot().getSensors()[0].getValue() < 200) {
				Debug.printDebug("Bot " + getRobot().name + " IRsensor[0] < 200 Value=" + getRobot().getSensors()[0].getValue());
				break;
			}
			// The ball is in reach of the reflectionsensor so the distance can be calculated
			ReflectionSensor rs = (ReflectionSensor)getRobot().getSensors()[3];
			double svalue = rs.getValue();
			double dist = rs.convertValueToDistance(svalue);
			// If the distance is zero this means that the ball is detected by the IRSensor but not in front of the Reflectionsensor
			// Check if the preffered distance is reached
			if ((dist < distToBall)&& (dist != 0))
			{
				Debug.printDebug("Bot " + getRobot().name + " ReflSensor[3] distance=" + dist + " value=" + svalue);
				break;
			}
			// Try to align the ball in front of the reflectionSensor
			vectorDrive(0, -0.6, 0); // move south (y-negative)
			sleep(100); // if this is too long you will drive too far!
			vectorDrive(0, 0, 0); // stop moving to check sensors again
		}

		// While I'm happy, do nothing and show the green led
		getRobot().getLeds()[Jobot.GREEN_LED].setValue(1.0);
		
		// The robot is happy when the ball is in front of him
		ReflectionSensor rs = (ReflectionSensor)getRobot().getSensors()[3];
		while (isRunning() && (getRobot().getSensors()[0].getValue() > 200)
				&& (rs.convertValueToDistance(rs.getValue()) < distToBall))
		{
			Debug.printDebug("Bot " + getRobot().name + " is happy now");
			sleep(100);
		}

		getRobot().getLeds()[Jobot.GREEN_LED].setValue(0.0);
	}
}
