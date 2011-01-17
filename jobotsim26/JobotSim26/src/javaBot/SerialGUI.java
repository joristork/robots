package javaBot;

import java.io.DataInputStream;
import javax.swing.JFrame;
import java.io.ByteArrayInputStream;

public class SerialGUI extends JFrame
{

	private static final long	serialVersionUID	= 2L;
	private int					factor				= 20;
	private byte				byteArray[]			= new byte[256];
	private ImagePanel			panel;

	public SerialGUI()
	{
		super();
		Initialize();
	}

	/**
	 * Initialize environment
	 *
	 */
	private void Initialize()
	{
	
		// Set frame settings
		setSize(16 * factor, (16 * factor) + 10);
		setTitle("Mouse sensor representation");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add panel to draw on
		panel = new ImagePanel(byteArray, factor);
		getContentPane().add(panel);

		setVisible(true);
	}

	/**
	 * Read out the input data and show it on screen
	 * 
	 * @param Offset smilie scroll value (1 = 1 line down, 2 = 2 lines down, ..)
	 * 			For demo purposes only!
	 */
	public void DrawOutput()
	{
		panel.paintComponent(getContentPane().getGraphics());
	}


	// Read data from serial interface.
	public void setData(DataInputStream input)
	{
		byte inByte;

		try
		{
			int i = 0;

			/**wait for start sign*/
			// char 127 = [DELETE] 
			while ((inByte = input.readByte()) != (char) 127)
			{
//				this.byteArray[i] = (byte) (inByte & ((byte) 63));
				this.byteArray[i] = (byte) (inByte & ((byte) 63));
				i++;
			}
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		panel.setArray(byteArray);
	}
}