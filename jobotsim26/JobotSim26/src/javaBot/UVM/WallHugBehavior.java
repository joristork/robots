/*
 * Created on Aug 13, 2004
 * The WallHugbehavior (8) class sits on the Behavior base class
 * WallHug tries to keep the robot at a fixed distance from the wall
 * It uses just one sensor for this.
 * An improved version of this behavior could use all sensors and
 * first try to find out which side is closest to the wall
 * @author James Caska
 */
package javaBot.UVM;

/**
 * The WallHug behavior is a state machine that tries to keep the robot at a
 * fixed distance from the wall, using only two sensors
 */
import com.muvium.apt.PeriodicTimer;

/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 *
 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
 */
public class WallHugBehavior extends Behavior
{
	/**
	 * Creates a new WallHugBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public WallHugBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		int s1 = 0;
		int s2 = 0;
		int vy = 0; // forward/backward
		int vr = 0; // positive is clockwise

		final int READING_TO_CONTROL = 75;
		final int READING_TO_CHANGE = 40;

		// Find minumum distance
		s1 = joBot.getSensor(1) - READING_TO_CONTROL;
		s2 = joBot.getSensor(2) - READING_TO_CONTROL;
		vy = (s2 + s1);
		vr = (s2 - s1);

		if (vy > 50)
		{
			vy = READING_TO_CHANGE;
		}

		if (vy < -50)
		{
			vy = -READING_TO_CHANGE;
		}

		if (vr > 50)
		{
			vr = READING_TO_CHANGE;
		}

		if (vr < -50)
		{
			vr = -READING_TO_CHANGE;
		}
		
		if ( s1 <= -75){
			joBot.vectorDrive(90, -40, -40);

		}
		else{

			joBot.vectorDrive(90, vy, -vr);
		}
		joBot.setStatusLeds(false, joBot.getSensor(2) > READING_TO_CONTROL,
				joBot.getSensor(1) > READING_TO_CONTROL);
	}
}
