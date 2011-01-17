/*
 * Created on Aug 13, 2004 
 * The Calibratebehavior (dip=1) class sits on the base Behavior class
 * It keeps the servos to their neutral position 
 * Reads the sensors and sets the status leds to
 * indicate whisch sensor is read
 * To ease testing it reacts only to close objects
 * @author Peter van Lith
 *  
 */
package javaBot.Junior.Soccer;

import com.muvium.apt.*;

public class IdleBehavior extends Behavior {

	public IdleBehavior(JobotBaseController initJoBot,
			PeriodicTimer initServiceTick, int servicePeriod) {
		super(initJoBot, initServiceTick, servicePeriod);
	}

	public void doBehavior() {
		getJoBot().setStatusLeds(false, false, false);				
		getJoBot().drive(0, 0);
	}
}