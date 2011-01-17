/*
* Copyright (c) 2006 Sun Microsystems, Inc.
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to 
* deal in the Software without restriction, including without limitation the 
* rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
* sell copies of the Software, and to permit persons to whom the Software is 
* furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in 
* all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING 
* FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
* DEALINGS IN THE SOFTWARE.
 **/       



/**
 * @author: David Mercier <david.mercier@sun.com>
 *
 * This class provides 2 connections through the RadiostreamConnection
 * The first one is used with DataInputStream - DataOutputStream
 * The second one is used with RadioInputStream - RadioOutputStream
 */


package org.sunspotworld.demo;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.io.Connector;

import com.sun.spot.io.j2me.radiostream.RadioOutputStream;
import com.sun.spot.io.j2me.radiostream.RadiostreamConnection;
import com.sun.spot.peripheral.NoRouteException;
import com.sun.spot.peripheral.TimeoutException;

public class DataInputOutputStreamConnection {

	private final int TIMEOUT = 2000;
	private final int PORT = 33;
	private final int ROS_PORT = 34;

	private boolean connected = false;
	private RadiostreamConnection conn = null;
	private RadiostreamConnection connROS = null;
	private DataInputStream dis = null;
	private DataOutputStream dos = null;
	private RadioOutputStream ros = null;
	
	public DataInputOutputStreamConnection() {
	}
    
	public void connect(String address) {
		connected = false;
		try {
			conn = (RadiostreamConnection)Connector.open("radiostream://" + address + ":" + PORT);
			conn.setTimeout(TIMEOUT);
		} catch (Exception e1) {
			e1.printStackTrace();
			conn = null;
		}

//		connected = false;
//		while(!connected) {
//			try {
//				connROS = (RadiostreamConnection)Connector.open("radiostream://" + address + ":" + ROS_PORT);
//				connROS.setTimeout(TIMEOUT);
//				connected = true;
//			} catch (Exception e1) {
//				e1.printStackTrace();
//				try {
//					conn.close();
//				} catch (IOException e) {
//				}
//			}
//		}
		
		String tmp = null;
		
		try {
			dis = (DataInputStream)conn.openDataInputStream();
			dos = (DataOutputStream)conn.openDataOutputStream();
//			ros = (RadioOutputStream)connROS.openOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(!connected) {
			try {
				dos.writeUTF("GO");
				dos.flush();
			} catch (IOException e1) {
				//e1.printStackTrace();
				System.out.println("Exception on writeUTF");
			}
			
			try {
				tmp = dis.readUTF();
			} catch (TimeoutException e) {
				System.out.println("Timeout... other end is not responding");
			} catch (IOException e1) {
				//e1.printStackTrace();
				System.out.println("Exception on readUTF");
			}
			
			if(tmp != null) {
				if(tmp.equalsIgnoreCase("GO")) {
					connected = true;
					System.out.println("Connected to " + address);
				} else {
					connected = false;
					System.out.println("NOT connected to " + address);
				}
			}
		}
	}
	
	public void closeConnection() {
		if(connected == false)
			return;
		connected = false;
		try {
			dos.close();
			dis.close();
//			ros.close();
			conn.close();
//			connROS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void send(String message, int value) throws IOException, NoRouteException {
//    	ros.write(value);
//    	ros.flush();
    	dos.writeUTF(message);
    }
    
    public String receive() {
    	String recv = null;
    	try {
			recv = dis.readUTF();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			recv = "nothing received...";
		}
		return recv;
    }
    
    public void startSendingThread() {
        Runnable r = new Runnable(){
            public void run() {
            	conn.setTimeout(500);
        		while(connected) {
        			try {
						send("Ya!", (int)System.currentTimeMillis());
					} catch (NoRouteException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					
        			try {
        				Thread.sleep(100);
        			} catch (InterruptedException e) {
        				e.printStackTrace();
        			}
        		}
        	};
        };
        (new Thread(r)).start();
    }
}
