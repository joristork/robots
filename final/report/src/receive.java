/*
 * Source code of the receiving Sun SPOT. This source code is part of our Cobot
 * project (for the robotics course on the University of Amsterdam).
 *
 * Authors: Lucas Swartsenburg, Harm Dermois
 **/
package org.sunspotworld.demo;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.*;
import com.sun.spot.sensorboard.*;

import com.sun.spot.util.Utils;

import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * This class uses the radio to receive broadcasted data from any Sun SPOT.
 */
public class receive extends javax.microedition.midlet.MIDlet {

    private ITriColorLEDArray leds =
        (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    IIOPin pins[] = EDemoBoard.getInstance().getIOPins();
    IIOPin send_pin = pins[EDemoBoard.D2];
    IIOPin send_data_pin = pins[EDemoBoard.D3];
    IIOPin receive_pin = pins[EDemoBoard.D0];
    IIOPin receive_data_pin = pins[EDemoBoard.D1];

    private static final int DRIVE = 1;
    private static final int REVERSE = 2;
    private static final int LEFT = 3;
    private static final int RIGHT = 4;
    private static final int STOP = 5;

    public int sleep_interval = 50;
    public int error_threshold = 2000;

    protected void startApp() {
        // Listen for downloads/commands over USB connection
        new com.sun.spot.service.BootloaderListenerService()
                .getInstance().start();

        leds.setColor(LEDColor.MAUVE);
        leds.setOn();

        send_pin.setAsOutput(true);
        send_data_pin.setAsOutput(true);
        receive_pin.setAsOutput(false);
        receive_data_pin.setAsOutput(false);

        send_pin.setLow();
        send_data_pin.setLow();

        for(;;) {
            if(receive_pin.isHigh() && receive_data_pin.isHigh()) {
                System.out.println("Start receive.");
                int c = bitbang_receive();
                if(c==DRIVE){
                    System.out.println("DRIVE");
                }
                if(c==REVERSE){
                    System.out.println("REVERSE");
                }
                if(c==LEFT){
                    System.out.println("LEFT");
                }
                if(c==RIGHT){
                    System.out.println("RIGHT");
                }

                System.out.println(c);
            }
        }
    }

    public int bitbang_receive() {
        int received = 0;
        int error = 0;

        send_pin.setLow();
        send_data_pin.setHigh();

        Utils.sleep(this.sleep_interval);

        for (int i = 7; i >= 0; i--) {
            while (receive_pin.isLow())
                if(++error > this.error_threshold)
                    return -1;
            error = 0;

            received |= receive_data_pin.isHigh() ? 1 << i : 0;

            send_pin.setHigh();

            Utils.sleep(this.sleep_interval);
            send_pin.setLow();

            Utils.sleep(this.sleep_interval);
        }
        send_data_pin.setLow();
        return received;
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
    protected void destroyApp(boolean unconditional) {
    }
}