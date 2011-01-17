/*
 * @(#)ToneGenerator.java Created on Aug 19, 2005
 *
 * Copyright 2003 to 2005 James Caska, Infology Pty Ltd. All Rights Reserved
 * Use is subject to license terms.
 *
 */
package javaBot.UVMSoccer;

import com.muvium.apt.PWM;
import com.muvium.apt.PeripheralFactory;
import com.muvium.io.PortIO;

/**
 * <p>
 * <b>NOTES:</b><br>
 * 
 * <ul>
 * <li>
 * Version Aug 19, 2005 ( Created )
 * </li>
 * </ul>
 * </p>
 *
 * @author James Caska
 *
 * @see
 */
public class ToneGenerator
{
	public static final int		TONE_X			= 0;
	public static final int		TONE_C			= 1;
	public static final int		TONE_D			= 2;
	public static final int		TONE_E			= 3;
	public static final int		TONE_F			= 4;
	public static final int		TONE_G			= 5;
	public static final int		TONE_A			= 6;
	public static final int		TONE_B			= 7;
	public static final int		TONE_C1			= 8;
	public static final int		NumberOfTones	= 9;

	private PWM					pwm;
	private PeripheralFactory	factory;

	/**
	 * Creates a new ToneGenerator object.
	 *
	 * @param fact TODO PARAM: DOCUMENT ME!
	 */
	public ToneGenerator(PeripheralFactory fact)
	{
		factory = fact;
	}

	///These cannot be parametric -- are statically computed  parameters for clock speed etc for the device
	//A Dynamic frequency calculator to internal settings is on the TODO list. Due to the way the scalor
	//etc works internally you don't get a smooth  from adjusting frequency settings
	//the recommended way to acheive smooth audio from the PWM unit is duty modulation -- has anti-jitter
	//features etc when used with duty modulation not available for frequency setting modulation
	// pwm = factory.createPWM( PWM.PWM_CHANNEL4,  10000 );
	private PWM getPWM(int ToneId)
	{
		switch (ToneId)
		{
			case TONE_C:
				return factory.createPWM(PWM.PWM_CHANNEL5, 2093);

			case TONE_D:
				return factory.createPWM(PWM.PWM_CHANNEL5, 2349);

			case TONE_E:
				return factory.createPWM(PWM.PWM_CHANNEL5, 2637);

			case TONE_F:
				return factory.createPWM(PWM.PWM_CHANNEL5, 2794);

			case TONE_G:
				return factory.createPWM(PWM.PWM_CHANNEL5, 3136);

			case TONE_A:
				return factory.createPWM(PWM.PWM_CHANNEL5, 3520);

			case TONE_B:
				return factory.createPWM(PWM.PWM_CHANNEL5, 3951);

			case TONE_C1:
				return factory.createPWM(PWM.PWM_CHANNEL5, 4186);

			default:
				return factory.createPWM(PWM.PWM_CHANNEL5, 0);
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param ToneId TODO PARAM: param description
	 */
	public void playTone(int ToneId)
	{
		if (pwm != null)
		{
			pwm.stop();
		}

		if (ToneId == 0)
		{
			return;
		}

		pwm = getPWM(ToneId);

		try
		{
			pwm.start();
			PortIO.setTris(0xEF, PortIO.PORTG);
		}
		catch (Exception e)
		{}

		pwm.setDuty(pwm.getDutyRange() / 2); // This gives a square wave
	}
}
