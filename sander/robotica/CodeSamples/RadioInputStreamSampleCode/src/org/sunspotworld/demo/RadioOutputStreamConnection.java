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
 */


package org.sunspotworld.demo;

import java.io.IOException;

import javax.microedition.io.Connector;

import com.sun.spot.io.j2me.radiostream.RadioInputStream;
import com.sun.spot.io.j2me.radiostream.RadioOutputStream;
import com.sun.spot.io.j2me.radiostream.RadiostreamConnection;
import com.sun.spot.peripheral.NoRouteException;
import com.sun.spot.peripheral.TimeoutException;

public class RadioOutputStreamConnection {

	private final int TIMEOUT = 2000;
	private final int PORT = 34;

	private boolean connected = false;
	private RadiostreamConnection connROS = null;
	private RadioInputStream ris = null;
	private RadioOutputStream ros = null;
	
	public RadioOutputStreamConnection() {
	}
    
	public void connect(String address) {
		int tmp = -1;
		
		connected = false;
		try {
			connROS = (RadiostreamConnection)Connector.open("radiostream://" + address + ":" + PORT);
			connROS.setTimeout(TIMEOUT);
		} catch (Exception e1) {
			e1.printStackTrace();
			connROS = null;
		}

		try {
			ris = (RadioInputStream)connROS.openInputStream();
			ros = (RadioOutputStream)connROS.openOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		while(!connected) {
			try {
				ros.write(0);
				ros.flush();
			} catch (IOException e1) {
				//e1.printStackTrace();
				System.out.println("Exception on writeUTF");
			}
			
			try {
				tmp = ris.read();
			} catch (TimeoutException e) {
				System.out.println("Timeout... other end is not responding");
			} catch (IOException e1) {
				//e1.printStackTrace();
				System.out.println("Exception on readUTF");
			}
			
			if(tmp == 0) {
				connected = true;
				System.out.println("Connected to " + address);
			} else {
				connected = false;
				System.out.println("NOT connected to " + address);
			}
		}
	}
	
	public void closeConnection() {
		if(connected == false)
			return;
		connected = false;
		try {
			ros.close();
			ris.close();
			connROS.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

    public void send(int value) throws IOException, NoRouteException {
    	ros.write(value);
    }
    
    public int receive() {
    	int recv = 0;
    	try {
			recv = ris.read();
			System.out.println("Signal Strength (RSSI) = " + ris.getRssi());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			recv = -666;
		}
		return recv;
    }
    
    public void startSendingThread() {
        Runnable r = new Runnable(){
            public void run() {
            	connROS.setTimeout(500);
        		while(connected) {
        			try {
						send((int)System.currentTimeMillis());
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
