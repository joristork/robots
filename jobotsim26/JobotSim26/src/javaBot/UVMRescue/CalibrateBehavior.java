package javaBot.UVMRescue;

/*
 * Created on Aug 13, 2004
 * The Calibratebehavior (dip=1) class sits on the base Behavior class
 * Reads the sensors and print the value to the output stream
 * @author Peter van Lith
 *
 */
import com.muvium.apt.PeriodicTimer;

/**
 * Created on 20-02-2006 Copyright: (c) 2006 Company: Dancing Bear Software
 *
 * @version $Revision$ last changed 20-02-2006 TODO CLASS: DOCUMENT ME!
 */
public class CalibrateBehavior extends Behavior
{
	/**
	 * Creates a new CalibrateBehavior object.
	 *
	 * @param initSensor TODO PARAM: DOCUMENT ME!
	 * @param initServiceTick TODO PARAM: DOCUMENT ME!
	 * @param servicePeriod TODO PARAM: DOCUMENT ME!
	 */
	public CalibrateBehavior(JobotBaseController initSensor, PeriodicTimer initServiceTick,
			int servicePeriod)
	{
		super(initSensor, initServiceTick, servicePeriod);
		sensor = initSensor;
	}

	JobotBaseController	sensor;

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void doBehavior()
	{
		int sl = sensor.getSensor(2);
		int sr = sensor.getSensor(3);

		System.out.print("L=");
		System.out.print(joBot.getColorName(sl));
		System.out.print(" R=");
		System.out.println(joBot.getColorName(sr));
	}
}
