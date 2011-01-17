/*
 * Copyright (c) 2006-2010 Sun Microsystems, Inc.
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
 * This example show you how to use basic radio functionality.
 * You need to change the ADDRESS String before deploying it to the SPOTS.
 * 
 * @author: David Mercier <david.mercier@sun.com>
 */

package org.sunspotworld.demo;

import javax.microedition.midlet.MIDletStateChangeException;

public class RadioInOutSample extends javax.microedition.midlet.MIDlet {

	/* SUGGESTION TO USER: you could broadcast to sync with another SPOT and to retrieve
	 * its address */
	private final String REMOTE_SPOT_ADDRESS = "0014.4F01.0000.0221";
	private DataInputOutputStreamConnection rConnection = null;
	private RadioOutputStreamConnection rosConnection = null;
	
	protected void startApp() throws MIDletStateChangeException {
		// Listen for downloads/commands over USB connection
		new com.sun.spot.service.BootloaderListenerService().getInstance().start();
		String recv = null;
		int iRecv = 0;
		rConnection = new DataInputOutputStreamConnection();
		rosConnection = new RadioOutputStreamConnection();
		
		System.out.println("I'm about to rock that SPOT !");
		
		/* SUGGESTION TO USER: you could broadcast to sync to another SPOT and to retrieve
		 * its address */
		rConnection.connect(REMOTE_SPOT_ADDRESS);
		rosConnection.connect(REMOTE_SPOT_ADDRESS);
		System.out.println("I'm connected");
		
		rConnection.startSendingThread();
		rosConnection.startSendingThread();
		
		/* Main loop of the application */
		while(true) {
			recv = rConnection.receive();
			System.out.println(recv);
			
			iRecv = rosConnection.receive();
			System.out.println(iRecv);
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void pauseApp() {
	}

        /**
         * Called if the MIDlet is terminated by the system.
         * I.e. if startApp throws any exception other than MIDletStateChangeException,
         * if the isolate running the MIDlet is killed with Isolate.exit(), or
         * if VM.stopVM() is called.
         *
         * It is not called if MIDlet.notifyDestroyed() was called.
         *
         * @param unconditional If true when this method is called, the MIDlet must
         *    cleanup and release all resources. If false the MIDlet may throw
         *    MIDletStateChangeException  to indicate it does not want to be destroyed
         *    at this time.
         */
        protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
	}

}