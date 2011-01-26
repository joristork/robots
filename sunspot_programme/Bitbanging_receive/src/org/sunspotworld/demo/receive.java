/*
 * Copyright (c) 2007-2010 Sun Microsystems, Inc.
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

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.*;
import com.sun.spot.sensorboard.*;

import com.sun.spot.util.Utils;

import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author: David Mercier <david.mercier@sun.com>
 *
 * This simple demo shows you how to use the radio to broadcast
 * some data to any listening SPOT(s).
 *
 * There is one thread (startSenderThread) that sends data on a particular
 * channel.
 * There is a second thread (startReceiverThread) that receives
 * data on that same channel.
 *
 */
public class receive extends javax.microedition.midlet.MIDlet {

    private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
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
        new com.sun.spot.service.BootloaderListenerService().getInstance().start();

        System.out.println("I'm about to rock that SPOT !");
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
