/*
 * StartApplication.java
 *
 * Created on Jun 30, 2009 2:02:28 PM;
 */
package org.sunspotworld.demo;

import com.sun.spot.io.j2me.tcp.TCPConnection;
import com.sun.spot.ipv6.Inet6Address;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.*;

import java.io.DataOutputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 * 
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public class TCPServer extends MIDlet {

    private static final int PORT_NUMBER = 8888;
    private ILightSensor lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
    private ITemperatureInput tempSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
    private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    private Inet6Address connected, lastConnected = null;
    int connectCount = 0;

    private void setLEDs(int r, int g, int b) {
        for (int i = 0; i < 8; i++) {
            leds.getLED(i).setRGB(r, g, b);
            leds.getLED(i).setOn();
        }
    }

    protected void startApp() throws MIDletStateChangeException {
        BootloaderListenerService.getInstance().start(); // monitor the USB (if connected) and recognize commands from host
        TCPConnection conn = null;
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        System.out.println("Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
        while (true) {
            setLEDs(0, 0, 100); // Blue == waiting
            System.out.println("[APP] Beginning wait on port " + PORT_NUMBER);
            try {
                conn = (TCPConnection) Connector.open("tcp://:" + PORT_NUMBER);
                connected = conn.getRemoteAddress();

            } catch (IOException ex) {
                ex.printStackTrace();
                continue;
            }
            setLEDs(100, 0, 0);  // red == connected/busy
            System.out.println("[APP] Connect completed.  Starting Thread");
            DoTCP t = new DoTCP(conn);
            t.start();
            try {
                t.join();
                connectCount++;
                lastConnected = connected;
                Thread.sleep(300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // notifyDestroyed();                      // cause the MIDlet to exit
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
        for (int i = 0; i < 8; i++) {
            leds.getLED(i).setOff();
        }
    }

    private class DoTCP extends Thread {

        TCPConnection conn;

        public DoTCP(TCPConnection c) {
            this.conn = c;
            c.setTimeout(3000);
        }

        public void run() {
            DataOutputStream dos = null;

            try {
                dos = conn.openDataOutputStream();

                // Do the real work here
                // Number of connections we've served prior to this one
                dos.writeInt(connectCount);
                if (lastConnected != null) {
                    dos.writeUTF(lastConnected.toString());
                } else {
                    dos.writeUTF("None");
                    // Light sensor reading
                }
                dos.writeInt(lightSensor.getValue());
                // Temperature sensor reading
                dos.writeDouble(tempSensor.getFahrenheit());
                dos.flush();

                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
