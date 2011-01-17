/*
 * RescueBehaviorStart.java
 * This module contains the behavior for the first leg of the field
 * from start until the yellow shortcut,
 * It ignores this and carries on over the entire field at a fast
 * speed. Modify this behavior to also handle the sharper corners 
 * later on.
 * 
 * Peter van Lith - 27-03-2006
 * 
 */

package javaBot.UVMRescue;

import com.muvium.apt.*;

public class RescueBehaviorStart extends Behavior {     
	
	int count = 0;
	static final int delay = 9;
	static final int speed = 80;
	
	public RescueBehaviorStart(JobotBaseController initJoBot,
			PeriodicTimer initServiceTick, int servicePeriod) {
		super(initJoBot, initServiceTick, servicePeriod);
	}
	
	public void doBehavior() {
		int fSl = joBot.getSensor(2); //"left" fieldsensor
		int fSr = joBot.getSensor(3); //"right" fieldsensor
		joBot.reportState(1);
		
		if (count > 0) {				// If delay counter is on
			count--;					// wait until zero
			return;
		}
		
		if(joBot.isColor(fSl, joBot.GREEN) && joBot.isColor(fSr, joBot.GREEN)) {  //If both sensors see green
			//you are still on the road
			joBot.vectorDrive(0,-speed,0);    //Carry on
//			System.out.print("Straight ");
		}
		else if(joBot.isColor(fSl, joBot.BLACK)) { //If left sensor sees black
				if(joBot.isColor(fSr, joBot.BLACK)) {  //And right one also
					System.out.println("Error! Both sensors are sensing black!");
			}
			joBot.drive(-100,-100,-100);	//Turn left
			count = delay;					// And continue for some cycles
//			System.out.print("Turn Left ");
		}
		else if(joBot.isColor(fSr, joBot.BLACK)) {  //If right sensor sees black
			joBot.drive(100,100,100);  	//Turn right
			count = delay;				// and continue some cycles
//			System.out.print("Turn Right ");
		}

//		System.out.print("L=");
//		System.out.print(fSl);
//		System.out.print(" R=");
//		System.out.println(fSr);
	}

}