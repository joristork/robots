package javaBot;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ImagePanel extends JPanel
{

	private static final long	serialVersionUID	= 1L;

	//Holds the data to be displayed
	private byte[]				byteArray;

	//The dimensions of this JPanel 
	private int					width;
	private int					height;

	// the factor by which we are going to enlarge each pixel 
	private int					blockSize;

	public ImagePanel()
	{
		super();
	}

	/**
	 * Sets the byte array up to date.
	 * 
	 * @param byteArray
	 */
	public void setArray(byte[] byteArray)
	{
		this.byteArray = byteArray;
	}

	/** 
	 * @param byteArray the byte array we are going to display
	 * @param factor the factor by which we are going to enlarge each pixel 
	 */
	public ImagePanel(byte[] byteArray, int factor)
	{
		super();
		this.width = 16 * factor;
		this.height = 16 * factor;
		this.blockSize = factor;
		this.byteArray = byteArray;

		this.setSize(width, height);
	}

	/**
	 * This method paints the component!
	 * So basicly we customized this to our own needs, that to paint the pixels from
	 * the byteArray to the panel's content pane.
	 * 
	 * @param g this parameter is the reference to the graphics object
	 * 
	 */
	public void paintComponent(Graphics g)
	{
		int index = 0;
		int greyFactor = 4;
		int msSleep = 100; // For demo purposes!

		// Loop out the bytearray
		// Paint every grey-value per byte
		// Source: http://www.avagotech.com/pc/downloadDocument.do?id=4570

		/**
		 * Last Pixel
		 *  |
		 *  V
		 * FF EF DF CF BF AF 9F 8F 7F 6F 5F 4F 3F 2F 1F 0F
		 * FE EE DE CE BE AE 9E 8E 7E 6E 5E 4E 3E 2E 1E 0E
		 * FD ED DD CD BD AD 9D 8D 7D 6D 5D 4D 3D 2D 1D 0D
		 * FC EC DC CC BC AC 9C 8C 7C 6C 5C 4C 3C 2C 1C 0C
		 * FB EB DB CB BB AB 9B 8B 7B 6B 5B 4B 3B 2B 1B 0B
		 * FA EA DA CA BA AA 9A 8A 7A 6A 5A 4A 3A 2A 1A 0A
		 * F9 E9 D9 C9 B9 A9 99 89 79 69 59 49 39 29 19 09
		 * F8 E8 D8 C8 B8 A8 98 88 78 68 58 48 38 28 18 08
		 * F7 E7 D7 C7 B7 A7 97 87 77 67 57 47 37 27 17 07
		 * F6 E6 D6 C6 B6 A6 96 86 76 66 56 46 36 26 16 06
		 * F5 E5 D5 C5 B5 A5 95 85 75 65 55 45 35 25 15 05
		 * F4 E4 D4 C4 B4 A4 94 84 74 64 54 44 34 24 14 04
		 * F3 E3 D3 C3 B3 A3 93 83 73 63 53 43 33 23 13 03
		 * F2 E2 D2 C2 B2 A2 92 82 72 62 52 42 32 22 12 02
		 * F1 E1 D1 C1 B1 A1 91 81 71 61 51 41 31 21 11 01
		 * F0 E0 D0 C0 B0 A0 90 80 70 60 50 40 30 20 10 00 
		 * 												^
		 * 												|
		 * 												First Pixel
		 */
		for (int Line = 15; Line >= 0; Line--)
		{
			for (int Column = 15; Column >= 0; Column--)
			{
				index = (Line * 16) + Column;
				g.setColor(new Color(byteArray[index] * greyFactor, byteArray[index] * greyFactor,
						byteArray[index] * greyFactor));
				g.fillRect(Column * blockSize, Line * blockSize, blockSize, blockSize);
			}
		}

		/**
		 * Geniest piece of simulator,
		 * made by Maarten and Jakob! :D
		 * 
		 * For demo purposes!
		 **/
		//Interrupts the display 
		try
		{
			Thread.sleep(msSleep);
		}
		catch (InterruptedException e)
		{
			System.out.print("Exception in thread");
		}
	}
}
