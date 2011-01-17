/*
 * StartApplication.java
 *
 * Created on Apr 23, 2010 1:13:12 PM;
 */
package org.sunspotworld;

import com.sun.spot.ipv6.tcp.ServerSocket;
import com.sun.spot.ipv6.tcp.Socket;
import com.sun.spot.util.*;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 * 
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public class KnockKnock extends MIDlet {

    protected void startApp() throws MIDletStateChangeException {
        new BootloaderListener().start();   // monitor the USB (if connected) and recognize commands from host
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(4444);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        System.out.println("[APP] Socket opened and listening");
        while (listening) {
            try {
                new KKMultiServerThread((Socket) serverSocket.accept()).start();
                System.out.println("[APP] Connection started.");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("[APP] Done, closing up");
        try {
            serverSocket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


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
