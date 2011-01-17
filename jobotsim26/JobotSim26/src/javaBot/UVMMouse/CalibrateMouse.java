/*
 * Created on Feb 14, 2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision: 1.1 $
 *
 * The Calibratebehavior (dip=1) class sits on the base Behavior class
 * It keeps the servos to their neutral position
 * Reads the sensors and sets the status leds to
 * indicate whisch sensor is read
 * To ease testing it reacts only to close objects
 *
 */
package javaBot.UVMMouse;

import com.muvium.apt.PeriodicTimer;
import com.muvium.driver.opticalMouse.Agilent2051;
import com.muvium.io.PortIO;
/**
 * Mouse Sensor Calibration
 * 
 * When using a PixelDump with an inline reset, data is read OK
 * When eliminating the reset no valid data is read
 * The reset MUST include the Power Down (PD) hard reset otherwise
 * no reset is performed and the timing must be OK.
 * When resetting all the time and getting the ID nothing happens.
 * When using MouseTrack with continuous reset, we get x=0,y=0
 * After removing the reset, we get x=0,y=40
 * 
 * When not properly reset output of dump is 0,0,87,0,0,
 * Should not show 87, since it is faulty
 */
public class CalibrateMouse extends Behavior {
	/**
	 * Creates a new CalibrateBehavior object.
	 */
	public CalibrateMouse(JobotBaseController initJoBot,
			PeriodicTimer initServiceTick, int servicePeriod) {
		super(initJoBot, initServiceTick, servicePeriod);
		mouse = getJoBot().mouse;
//1		getJoBot().resetSensor();  		// No proper init
//2		getJoBot().resetSensor(0x10);   // 
//3		getJoBot().resetSensor(0x90);   // 
//4		getJoBot().resetSensor(0x11);   // 
//5		getJoBot().resetSensor(0x91);   //
		}

	Agilent2051 mouse;
	int state = -1;
	
	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior() {
		int i = 0;
		int j = 0;
	
		if (state == -1) {
//			getJoBot().sleep(1000);
// Single init tests
//1			getJoBot().resetSensor();  		// No proper init
//2			getJoBot().resetSensor(0x10);   // No proper init
//3			getJoBot().resetSensor(0x90);   // No proper init
// Continuous init tests
//1			getJoBot().resetSensor();  		// Proper init no movement
//2			getJoBot().resetSensor(0x10);   // Proper init no movement
//3			getJoBot().resetSensor(0x90);   // Proper init no movement
//4			getJoBot().resetSensor(0x11);   // Proper init no movement
//5			getJoBot().resetSensor(0x91);   // Proper init no movement
// Reset command only continuous
//1			mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, 0x10); // No proper init
//2			mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, 0x90); // No proper init
//3			mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, 0x11); // No proper init
			
			if (mouse.getRegister(Agilent2051.A2051_PRODUCT_ID) != 2) {
				getJoBot().resetSensor(0x11);
			}
			state = -1;}
		
//		getJoBot().pixelDump();
//		getJoBot().mouseTrack();
		
		 System.out.print(  "Product=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_PRODUCT_ID ) );
		 System.out.print(  "\r\n" );
		 
		 System.out.print(  "Config=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_CONFIG_BITS ) );
		 System.out.print(  "\r\n" );

		 System.out.print(  "Avg-Pix=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_AVERAGE_PIXEL) );
		 System.out.print(  "\r\n" );

		 System.out.print(  "Max-Pix=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_MAX_PIXEL  ) );
		 System.out.print(  "\r\n" );

		 System.out.print(  "Shutter=" );
		 i = mouse.getRegister( Agilent2051.A2051_SHUTTER_UPPER ) * 256;
		 i =+ mouse.getRegister( Agilent2051.A2051_SHUTTER_LOWER);
		 System.out.print(i);
		 System.out.print(  "\r\n" );

		 System.out.print(  "Squal=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_SQUAL) );
		 System.out.print(  "\r\n" );
		 
//1		 getJoBot().setStatusLeds(false, false, false, true);  // No influence
//2		 int dip = (PortIO.getPort(PortIO.PORTB) >> 4) ^ 0x0F; // No influence
		 
		 System.out.print(  "Motion=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_MOTION_REGISTER ) );
		 System.out.print(  "," );

		 System.out.print(  "Delta-X=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_DELTA_X) );
		 System.out.print(  "," );

		 System.out.print(  "Delta-Y=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_DELTA_Y) );
		 System.out.print(  "\r\n" );

		 System.out.print(  "Product=" );
		 System.out.print(  mouse.getRegister( Agilent2051.A2051_PRODUCT_ID ) );
		 System.out.print(  "\r\n" );
	}
}
