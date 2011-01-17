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

package org.sunspotworld.demo;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.spot.util.CRC;
import com.sun.squawk.VM;

import org.sunspotworld.adder.Adder;

public class AdderTest extends MIDlet {
	protected void startApp() throws MIDletStateChangeException {
		// Listen for downloads/commands over USB connection
		new com.sun.spot.service.BootloaderListenerService().getInstance().start();

		// Use a class from the library extension
		System.out.println("2 plus 2 is " + Adder.add(2, 2));
		
		// Use a class from the main library
		System.out.println("CRC of 2,3,4 is " + CRC.crc(new byte[] { 2, 3, 4 }, 0, 3));
		
		// Display the value of a library manifest property
		System.out.println("My favourite snake is a " + VM.getManifestProperty("FavouriteSnake"));
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

	protected void pauseApp() {
	}
}