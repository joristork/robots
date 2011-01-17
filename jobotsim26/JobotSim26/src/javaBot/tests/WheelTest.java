/*
 * Created on Mar 14, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot.tests;

import javaBot.Actor;
import javaBot.Location;
import javaBot.Wheel;
import junit.framework.TestCase;

public class WheelTest extends TestCase {
	
	public void testPercentageSpeed()
	{
		Actor actor = new Wheel(0.0, new Location(10,20), 2 );

		actor.setValue( 25.0 );
		
		double retreivedSpeed = actor.getPercentageSpeed();
		double expectedSpeed = Math.abs( ( actor.getValue() / ((Wheel)actor).getMaxSpeed() ) * 100.0 );
		
		
		assertTrue( "Percentage is correctly calculated" , retreivedSpeed == expectedSpeed );

	}
}
