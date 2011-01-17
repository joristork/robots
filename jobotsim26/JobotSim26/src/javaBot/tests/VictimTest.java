/*
 * Created on March 24, 2004 
 * Copyright: (c) 2006 
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */

package javaBot.tests;

/**
 * @version $Revision: 1.1 $ last changed Feb 14, 2006
 */

import javaBot.Jobot;
import javaBot.JobotJr;
import javaBot.Location;
import javaBot.Victim;
import javaBot.World;
import junit.framework.TestCase;

public class VictimTest extends TestCase
{
	private Victim	victim;

	/**
	 * Test method for Victim.giveSensoryInformationTo using different Robot objects as the parameter.
	 *
	 */
	public void testGiveSensorInfo()
	{
		victim = new Victim();
		victim.location = new Location(70, 100);

		double[] values1 = victim.giveSensoryInformationTo(new Jobot());
		double[] values2 = victim.giveSensoryInformationTo(new Jobot("TestJobot", 100, 100));
		double[] values3 = victim.giveSensoryInformationTo(new JobotJr("JobotJr", World.GRID_SIZE / 2, World.HEIGHT - (World.GRID_SIZE / 2)));
		double[] values4 = victim.giveSensoryInformationTo(new JobotJr("TestJobot", 100, 100));
		
		//	Victim doesn't have a DistanceSector, so all values should be 0
		for (int i = 0; i < values1.length; i++)
			assertEquals((double) values1[i], 0, 0);
		for (int i = 0; i < values2.length; i++)
			assertEquals((double) values2[i], 0, 0);
		for (int i = 0; i < values3.length; i++)
			assertEquals((double) values3[i], 0, 0);
		for (int i = 0; i < values4.length; i++)
			assertEquals((double) values4[i], 0, 0);		
	}
	
	/**
	 * Test method for Victim.giveSensoryInformationTo using a null value instead of a Robot.
	 *
	 */
	public void testGiveSensorInfoWithNull()
	{
		victim = new Victim();
		victim.location = new Location(70, 100);

		double[] values = victim.giveSensoryInformationTo(null);

		// When a null value is passed, method should still return 0 and not throw an exception
		for (int i = 0; i < values.length; i++)
			assertEquals((double) values[i], 0, 0);
	}
}
