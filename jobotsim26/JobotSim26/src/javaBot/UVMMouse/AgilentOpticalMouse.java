/*
 * @(#)AgilantMous.java Created on Nov 25, 2005
 *
 * Copyright 2003 to 2005 James Caska, Muvium.com, Infology Pty Ltd.
 * All Rights Reserved. Use is subject to license terms.
 *
 */
package javaBot.UVMMouse;

import com.muvium.UVMRunnable;
import com.muvium.driver.opticalMouse.Agilent2051;

/**
 * Created on 20-02-2006
 * Copyright: (c) 2006
 * Company: Dancing Bear Software
 *
 * @version $Revision$
 * last changed 20-02-2006
 *
 * TODO CLASS: DOCUMENT ME! 
 */
public class AgilentOpticalMouse extends UVMRunnable
{
	/**
	 * TODO METHOD: DOCUMENT ME!
	 *
	 * @param mouse TODO PARAM: param description
	 */
	private void track(Agilent2051 mouse)
	{
		int x = 0;
		int y = 0;

		while (true)
		{
			if (mouse.hasMoved())
			{
				x += mouse.getDX();
				y += mouse.getDY();

				System.out.print("x = ");
				System.out.print(x);
				System.out.print(" y = ");
				System.out.print(y);
				System.out.print("\r\n");
			}
		}
	}

	/**
	 * TODO METHOD: DOCUMENT ME!
	 */
	public void run()
	{
		Agilent2051 mouse = new Agilent2051();

		while (true)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (Exception e)
			{}

			;

			mouse.writeReg(Agilent2051.A2051_CONFIG_BITS, 25); //Pixel Dump

			for (int i = 0; i < 256; i++)
			{
				int address = mouse.getRegister(Agilent2051.A2051_DATA_OUT_UPPER);
				int data = mouse.getRegister(Agilent2051.A2051_DATA_OUT_LOWER);
				int inc = 0;

				while ((data & 0x80) != 0)
				{
					data = mouse.getRegister(Agilent2051.A2051_DATA_OUT_LOWER);
					inc++;
				}

				String s = "\r\n" + String.valueOf(address) + "," + String.valueOf(data);

				System.out.print(s);
			}
		}

		/*

		 System.out.print(  System.currentTimeMillis() );
		 System.out.print(  "\r\n" );
		 //  System.out.print(  mouse.getProduct() );
		 //  System.out.print(  "\r\n" );

		 System.out.print(  mouse.getRegister( Agilent2051.A2051_CONFIG_BITS ) );
		 System.out.print(  "\r\n" );

		 System.out.print(  mouse.getRegister( Agilent2051.A2051_MOTION_REGISTER ) );
		 System.out.print(  "\r\n" );

		 System.out.print(  mouse.getRegister( Agilent2051.A2051_DELTA_X) );
		 System.out.print(  "\r\n" );

		 System.out.print(  mouse.getRegister( Agilent2051.A2051_DELTA_Y) );
		 System.out.print(  "\r\n" );

		 System.out.print(  mouse.getRegister( Agilent2051.A2051_AVERAGE_PIXEL) );
		 System.out.print(  "\r\n" );

		 System.out.print(  mouse.getRegister( Agilent2051.A2051_MAX_PIXEL  ) );
		 System.out.print(  "\r\n" );


		 System.out.print(  mouse.getRegister( Agilent2051.A2051_SHUTTER_UPPER  ) );
		 System.out.print(  "\r\n" );

		 System.out.print(  mouse.getRegister( Agilent2051.A2051_SHUTTER_LOWER) );
		 System.out.print(  "\r\n" );

		 System.out.print(  mouse.getRegister( Agilent2051.A2051_SQUAL) );
		 System.out.print(  "\r\n" );
		 */
	}
}
