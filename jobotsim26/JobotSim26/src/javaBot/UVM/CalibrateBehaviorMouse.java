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
package javaBot.UVM;

import com.muvium.apt.PeriodicTimer;
import com.muvium.driver.opticalMouse.Agilent2051;

/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 *
 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
 */
public class CalibrateBehaviorMouse extends Behavior
{
	private Agilent2051	mouse;

	/**
	 * Creates a new CalibrateBehaviorMouse object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public CalibrateBehaviorMouse(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
		mouse = new Agilent2051();
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		int i = 0;
		int s;
		int s1;

		// 		Retrieve the first three sensor values and 
		//		determine the highest value
		s = joBot.getSensor(0);
		s1 = joBot.getSensor(1);

		if (s1 > s)
		{
			s = s1;
			i = 1;
		}

		s1 = joBot.getSensor(2);

		if (s1 > s)
		{
			s = s1;
			i = 2;
		}

		// 		Include this only when IR sensor is present
		s1 = joBot.getSensor(3);
		s = 0;

		if (s1 > s)
		{
			s = s1;
			i = 3;
		}

		// 		Include this only when Floor sensor is present
		s1 = joBot.getSensor(4);

		if (s1 > s)
		{
			s = s1;
			i = 4;
		}

		s1 = joBot.getSensor(1);
		i = 1;

		if (s > 0)
		{
			System.out.print("S");
			System.out.print(i);
			System.out.print("=");
			System.out.println(s);

			if (i > 2)
			{
				joBot.setStatusLeds(false, true, true); // Show IR detected
			}
			else
			{
				joBot.setStatusLeds(i == 0, i == 2, i == 1); // Show distance detected
			}
		}
		else
		{ // Reset lamps if no input read
			joBot.setStatusLeds(false, false, false);
		}

		joBot.drive(0, 0, 0);

		//		int x = 0;
		//		int y = 0;
		//		
		//		
		//		mouse.writeReg( Agilent2051.A2051_CONFIG_BITS , 25); //Pixel Dump
		//        
		//        for(int i = 0 ; i < 256; i++){
		//            int address = mouse.getRegister( Agilent2051.A2051_DATA_OUT_UPPER ) ;
		//            int data = mouse.getRegister( Agilent2051.A2051_DATA_OUT_LOWER ) ;
		//            int inc = 0;
		//            while( (data & 0x80 ) != 0){
		//                data = mouse.getRegister( Agilent2051.A2051_DATA_OUT_LOWER );
		//                inc++;
		//            }
		//            String s=  "\r\n" + String.valueOf( address ) + "," + String.valueOf( data );
		//            
		//            System.out.print( s );
		//            
		//
		//        }
		//        
		//        if( mouse.hasMoved() ){
		//            x += mouse.getDX();
		//            y += mouse.getDY();
		//            
		//            System.out.print( "x = " );
		//            System.out.print(  x );
		//            System.out.print( " y = " );
		//            System.out.print(  y );
		//            System.out.print(  "\r\n" );
		//        }
		//        
		//        joBot.drive(0, 100, -100);
		joBot.getCoordinates();
	}
}
