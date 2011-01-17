/*
 * Created on juni 19, 2006
 * 
 * @version 1.1
 */
package javaBot.UVMSoccer;

import com.muvium.apt.PeriodicTimer;

/**
 *	This is a test behavior for the ground sensor.
 *	It's hasn't been completed.
 *
 * @version $Revision: 1.1 $ last changed Feb 14, 2006
 */
public class GoToWhiteBehavior extends Behavior
{
	/**
	 * Creates a new FleeBehavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	
	// The Sensors
//	private final static int FRONT_SENSOR = 0; 	//Frontal Reflection Sensor Index
//	private final static int LEFT_SENSOR = 1; 	//Left Reflection Sensor Index
//	private final static int RIGHT_SENSOR = 2; 	//Right Reflection Sensor Index
//	private final static int IR_SENSOR_L = 3; 	//Left IR Sensor Index
//	private final static int IR_SENSOR_R = 4; 	//Right IR Sensor Index
	private final static int FLOOR_SENSOR = 5; 	//Floor Sensor Index
	
	// Movement Directions
	private final static int FORWARD = 100;		//Forward movement
	private final static int REVERSE = -100;	//Backward movement
	private final static int STOP = 0;			//No movement
	private final static int TURN_RIGHT = 30;	//Turning Right
	private final static int TURN_LEFT = -30;	//Turning Left
	
	private static int 	count = 0;
	
	// Initial Movement Parameters
	private static int 	turn = 0;
	private static int	speed = 0;
//	private static int	acttr = turn;
	private static int	i = 0;
	private static int	j = 0;
	private static int	s[] = {0,0,0,0};
//	private static int	maxs = 0;
	
	public GoToWhiteBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{

		if(getJoBot().getSensor(FLOOR_SENSOR) >70)
		{
			switch (count)
			{
				case 0:
					i = getJoBot().getSensor(FLOOR_SENSOR);
					speed = FORWARD;
					turn = STOP;
					break;
				case 1:
					j = getJoBot().getSensor(FLOOR_SENSOR);
					s[0] = (j-i);
					speed = REVERSE;
					turn = STOP;
					System.out.println("I: " + i + " J: " + j);				
					break;
				case 2:
					speed = STOP;
					turn = STOP;
					s[1] = getJoBot().getSensor(FLOOR_SENSOR)-j;
					System.out.println("S1: " + s[0] + " S2: " + s[1]);
					if(s[0] == 0 && s[1] ==0) //vertical
					{
						turn = TURN_RIGHT;
					}
					if(s[0] == 18 && s[1] == -18) //horizontal towards white
					{
						speed = STOP;
						turn = STOP;
					}
					if(s[0] == -18 && s[1] == 18) //horizontal towards black
					{
						//TODO turn around
					}
					if(s[0] > -20 && s[1] < 20) //SSWEST
					{
						turn = TURN_LEFT;
					}
					break;
				case 3:
					speed = STOP;
					turn = STOP;
					count = -1;
					break;
				default:
//					System.out.println("Count = " + count);
					break;
			}
			getJoBot().vectorDrive(0,speed,turn);
			count++;
		}
		else
			getJoBot().vectorDrive(0,0,0);
	}
}
