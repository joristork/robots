package javaBot.UVMSpeech;

import com.muvium.apt.PeriodicTimer;

/**
 * FindBallBehavior rotates untill the ball is noticed.
 * If the ball isn't found within 5 seconds the robot moves
 * randomly to another spot on the field.
 *
 * @version $Revision: 0.1 $ last changed Jun 13, 2006
 */
public class FindBallBehavior extends Behavior
{
	private final static int SEGMENT 		= 100; 	  	//Rotation Segment
	private final static int FRONT_SENSOR 	= 0; 		//Frontal Reflection Sensor Index
//	private final static int LEFT_SENSOR 	= 1; 		//Left Reflection Sensor Index
//	private final static int RIGHT_SENSOR 	= 2; 		//Right Reflection Sensor Index
	private final static int IR_SENSOR_L 	= 3; 		//Left IR Sensor Index
	private final static int IR_SENSOR_R 	= 4; 		//Right IR Sensor Index

	private static int time = 0;
	private static int hold = 0;
	
	/**
	 * Creates a new Behavior object.
	 *
	 * @param initJoBot TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public FindBallBehavior(JobotBaseController initJoBot, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initJoBot, initServiceTick, servicePeriod);
	}
	
	public void doBehavior() 
	{
		//Rotates untill the ball is noticed
		rotateAndSearch();
		//Aims for the ball once it's noticed
		aimAtBall();
	}
	
	public void rotateAndSearch()
	{
		if(!glimpseOfBall(IR_SENSOR_L) && !glimpseOfBall(IR_SENSOR_R))
		{
			if(time > 110) 	// if 5 seconds have passed and ball isn't
			{				// found move randomly to next field area.
				moveToNext();
				hold++;
				if(hold > 100)
				{
					hold = time = 0;
				}
			}
			else
			{
				getJoBot().vectorDrive(0,20,SEGMENT);
				time++;
			}
		}
	}
	
	//Aims at ball with it's front reflection sensor, so that the distance
	//to the ball can be calculated.
	public void aimAtBall()
	{
		if(!glimpseOfBall(FRONT_SENSOR))
		{
				if(glimpseOfBall(IR_SENSOR_R) && !glimpseOfBall(IR_SENSOR_L))
					getJoBot().vectorDrive(0,20,SEGMENT);
				else if(!glimpseOfBall(IR_SENSOR_R) && glimpseOfBall(IR_SENSOR_L))
					getJoBot().vectorDrive(0,20,-SEGMENT);
		}
		else if(glimpseOfBall(IR_SENSOR_L) 	&& glimpseOfBall(IR_SENSOR_R))
			getJoBot().vectorDrive(0,0,0);
	}
	
	
	//returns true if ball has been seen in the given sensor,
	//othewise false.
	public boolean glimpseOfBall(int Sensor)
	{
		if(getJoBot().getSensor(Sensor) > 10)
		{
			System.out.println("Glimpse of Ball!!!");
			return true;
		}
		else
			return false;
	}
	
	public void moveToNext()
	{
		getJoBot().vectorDrive(0, 100, 0);
	}
}