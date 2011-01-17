/*
 * StartApplication.java
 *
 * Created on Oct 29, 2010 5:06:23 PM;
 */

package org.sunspotworld.demo;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.util.*;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 *
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public class HTTPDemo extends MIDlet {
    private ISwitch sw = null;
    private String postURL = getAppProperty("POST-URL");

    protected void startApp() throws MIDletStateChangeException {
        // The resource lookup API supports tags ... this gets us Switch 2.
        sw = (ISwitch) Resources.lookup(ISwitch.class, "SW2");

        System.out.println("*******************************************************\n" +
                "This is a simple application that posts its \n" +
                "current sensor readings whenever switch 2 is pressed. \n" +
                "The readings can be accessed at " + postURL + "\n" +
                "(this URL is configured in resources/META-INF/MANIFEST.MF)\n" +
                "This demo requires a socket-proxy to be running in the\n" +
                "vicinity of the SPOT. See the README file for details.\n" +
                "*******************************************************\n");

        // Define & bind an anonymous switchlistener to change color on switch press
        sw.addISwitchListener(new MySwitchListener(postURL));

        while (true) {
            Utils.sleep(100);
        }

        //notifyDestroyed();                      // cause the MIDlet to exit
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
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

