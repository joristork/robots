/**
 * Created on Jun 02, 2004  
 * Copyright: (c) 2006  
 * Company: Dancing Bear Software
 */
package javaBot.clients;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

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
public class UVMJobotManagerClient extends Thread
{
	private int				serialPort		= 1225;
	private String			joBotAddress	= "127.0.0.1";
	private OutputStream	out;

	/**
	 * Attempts to log-on with the serialPort
	 *
	 * @param initSerialPort
	 * @param initJoBotServerAddress
	 */
	public UVMJobotManagerClient(int initSerialPort, String initJoBotServerAddress)
	{
	}

	/**
	 * Start Thread
	 */
	public void run()
	{
		try
		{
			Socket connection = new Socket(joBotAddress, serialPort);

			out = connection.getOutputStream();

			PrintWriter write = new PrintWriter(out);
			write.println("init JoBot");
		}
		catch (Exception e)
		{
			System.out.println("Failed to connect");
		}
	}

	/**
	 * @return joBotAddress
	 */
	public String getJoBotAddress()
	{
		return joBotAddress;
	}

	/**
	 * @param joBotAddress
	 */
	public void setJoBotAddress(String joBotAddress)
	{
		this.joBotAddress = joBotAddress;
	}

	/**
	 * @return serialPort
	 */
	public int getSerialPort()
	{
		return serialPort;
	}

	/**
	 * @param serialPort 
	 */
	public void setSerialPort(int serialPort)
	{
		this.serialPort = serialPort;
	}
}
