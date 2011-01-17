/*
 * Created on Mar 14, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 * 
 * @version $Revision: 1.1 $
 */
package javaBot.tests;

import javaBot.Actor;
import javaBot.JoBotServoMotor;
import junit.framework.TestCase;

public class JobotServoMotorTest extends TestCase {

	public void testJoBotServoMotorSpeedS3003()
	{
		Actor actor = new JoBotServoMotor(1,1,1,1,"S3003");

		actor.setValue( 3 );
		
		double retreivedSpeed = actor.getPercentageSpeed();
		double expectedSpeed = Math.abs( ( actor.getValue() / ((JoBotServoMotor)actor).getMaxSpeed() ) * 100.0 );
		
		assertTrue( "Percentage is correctly calculated" , retreivedSpeed == expectedSpeed );
	}
	
	public void testJoBotServoMotorSpeedS148()
	{
		Actor actor = new JoBotServoMotor(1,1,1,1,"S148");

		actor.setValue( 3 );
		
		double retreivedSpeed = actor.getPercentageSpeed();
		double expectedSpeed = Math.abs( ( actor.getValue() / ((JoBotServoMotor)actor).getMaxSpeed() ) * -100.0 );
		
		assertTrue( "Percentage is correctly calculated" , retreivedSpeed == expectedSpeed );
	}
	
}
