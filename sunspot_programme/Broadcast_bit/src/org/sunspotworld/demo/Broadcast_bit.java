/*****************************************************************************
 * TEAM AMERICA!!                                                            *
 *                                                                           *
 *****************************************************************************/

package org.sunspotworld.demo;

import java.io.IOException;

import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.spot.io.j2me.radiogram.RadiogramConnection;

import com.sun.spot.util.Utils;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.*;
import com.sun.spot.sensorboard.*;


/**
 * A class that determines the direction by measuring the tilt of this sunspot.
 *
 * If there is a change, send it to another sunspot. Print the direction on
 * the leds.
 */
public class Broadcast_bit extends javax.microedition.midlet.MIDlet {
    /*
     * Initialize the leds and the accelerometer sensors.
     */
    private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    private IAccelerometer3D accel = (IAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
    /*
     * The direction states...
     */
    private static final int DRIVE = 0;
    private static final int REVERSE = 1;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    private static final int STOP = 4;

    /*
     * angle of tilting before it comes into anohter state than STOP.
     */
    private static final int ANGLE = 25;

    int result_old = 0;
    int result = -1;

    protected void startApp() throws MIDletStateChangeException {

        System.out.println("I'm gonna send your ass to heaven.");
        /*
         * Listen for downloads/commands over USB connection
         */
        new com.sun.spot.service.BootloaderListenerService().getInstance().start();
        /*
         * Start the tilt thread that determines the state by measuring the
         * tilt. And start a threat that sends the direction state.
         */
        startTilt();
        startSenderThread();
    }

    /**
     * The Tilt thread measures the current tilt and converts it to a direction.
     * The direction is set globally so that the send threat can send it.
     */
    synchronized public void startTilt() {
        new Thread() {

            public void run() {
                int tiltX = 0;
                int tiltY = 0;
                while (true) {
                    try {
                        /*
                         * Measure the degrees.
                         */
                        tiltX = (int) Math.toDegrees(accel.getTiltX());
                        tiltY = (int) Math.toDegrees(accel.getTiltY());
                        result = STOP;
                        /*
                         * If the Sunspot is in a bigger sideways tilt than a
                         * frontal tilt. If none of the tilts is more than 15,
                         * the Sunspot is in a STOP state.
                         */
                        if ((Math.abs(tiltX) > Math.abs(tiltY)) && Math.abs(tiltX) > ANGLE) {
                            if (tiltX < 0) {
                                result = RIGHT;

                            } else {
                                result = LEFT;

                            }
                        } else if ((Math.abs(tiltX) < Math.abs(tiltY)) && Math.abs(tiltY) > ANGLE) {
                            if (tiltY > 0) {
                                result = DRIVE;

                            } else {
                                result = REVERSE;

                            }
                        }
                        /*
                         * Set the result on the leds.
                         */
                        set_Leds(result);

                    } catch (IOException ex) {
                    }
                }
            }
        }.start();
    }

    /**
     * Send the direction state to the other sunspot if there is a change in
     * direction.
     */
    synchronized public void startSenderThread() {
        new Thread() {

            public void run() {
                /*
                 * We create a DatagramConnection
                 */
                DatagramConnection dgConnection = null;
                Datagram dg = null;
                try {
                    /*
                     * The Connection is a broadcast so we specify it in the creation string
                     */
                    dgConnection = (DatagramConnection) Connector.open("radiogram://broadcast:37");
                    /*
                     * Then, we ask for a datagram with the maximum size allowed
                     */
                    dg = dgConnection.newDatagram(dgConnection.getMaximumLength());
                } catch (IOException ex) {
                    System.out.println("Could not open radiogram broadcast connection");
                    return;
                }
                while (true) {
                    try {
                        /*
                         * If there is a change in direction, send the direction
                         * in the form of a integer to a other Sunspot.
                         */
                        if (result != result_old) {
                            result_old = result;
                            dg.reset();
                            dg.writeInt(result);
                            dgConnection.send(dg);
                            System.out.println("Broadcast is going through");
                        }

                    } catch (IOException ex) {
                    }
                }
            }
        }.start();
    }

    /**
     * Put a appropriate combination on the leds according to the received
     * direction.
     * @param drive
     */
    public void set_Leds(int drive) {
        switch (drive) {
            case DRIVE:
                for (int i = 0; i < 2; i++) {
                    leds.getLED(i).setOff();
                }
                for (int i = 6; i < 8; i++) {
                    leds.getLED(i).setOff();
                }
                for (int i = 2; i < 6; i++) {
                    leds.getLED(i).setColor(LEDColor.GREEN);
                    leds.getLED(i).setOn();
                }

                break;


            case REVERSE:
                for (int i = 0; i < 2; i++) {
                    leds.getLED(i).setOff();
                }
                for (int i = 6; i < 8; i++) {
                    leds.getLED(i).setOff();
                }
                for (int i = 2; i < 6; i++) {
                    leds.getLED(i).setColor(LEDColor.ORANGE);
                    leds.getLED(i).setOn();
                }
                break;


            case LEFT:
                for (int i = 0; i < 4; i++) {
                    leds.getLED(i).setOff();
                }

                for (int i = 4; i < 8; i++) {
                    leds.getLED(i).setColor(LEDColor.BLUE);
                }
                for (int i = 4; i < 8; i++) {
                    leds.getLED(i).setOn();
                }
                break;

            case RIGHT:
                for (int i = 4; i < 8; i++) {
                    leds.getLED(i).setOff();
                }

                for (int i = 0; i < 4; i++) {
                    leds.getLED(i).setColor(LEDColor.WHITE);
                }
                for (int i = 0; i < 4; i++) {
                    leds.getLED(i).setOn();
                }
                break;

            case STOP:
                for (int i = 0; i < 3; i++) {
                    leds.getLED(i).setOff();
                }
                for (int i = 5; i < 8; i++) {
                    leds.getLED(i).setOff();
                }
                leds.getLED(3).setColor(LEDColor.RED);
                leds.getLED(3).setOn();
                leds.getLED(4).setColor(LEDColor.RED);
                leds.getLED(4).setOn();
                break;
        }

    }
    
    /**
     * Not used.
     */
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
