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

import com.sun.spot.service.BootloaderListenerService;
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
public class send extends javax.microedition.midlet.MIDlet {

    private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    IIOPin pins[] = EDemoBoard.getInstance().getIOPins();
    IIOPin send_pin = pins[EDemoBoard.D0];
    IIOPin send_data_pin = pins[EDemoBoard.D1];
    IIOPin receive_pin = pins[EDemoBoard.D2];
    IIOPin receive_data_pin = pins[EDemoBoard.D3];

    public int error_threshold = 20000;

    protected void startApp(){

        // Listen for downloads/commands over USB connection
        new com.sun.spot.service.BootloaderListenerService().getInstance().start();

        System.out.println("I'm about to rock that SPOT !");
        leds.setColor(LEDColor.RED);
        leds.setOn();

        send_pin.setAsOutput(true);
        send_data_pin.setAsOutput(true);
        receive_pin.setAsOutput(false);
        receive_data_pin.setAsOutput(false);

        send_pin.setHigh();
        send_data_pin.setHigh();

        int i = 0;
        while(true){
            switch( i++ % 3 ) {
                case 0: bitbang_send(9); break;
                case 1: bitbang_send(15); break;
                case 2: bitbang_send(0); break;
            }
        }

    }

    public int bitbang_send(int data) {
        System.out.println("Start send");
        int i;
        int error = 0;

        // select device
        send_pin.setHigh();
        send_data_pin.setHigh();

        while(!(receive_pin.isLow()&&receive_data_pin.isHigh()))
                if(++error == this.error_threshold)
                    return -1;

        send_pin.setLow();
        send_data_pin.setLow();
        Utils.sleep(20);

        for (i = 0; i < 8; i++) {
            //System.out.println("Data: " + data);

            //System.out.println("Bitmask: " + (data & 0x80));
            // consider leftmost bit
            // set line high if bit is 1, low if bit is 0
            if ((data & 0x80) > 0) {
                send_data_pin.setHigh();
                System.out.print("1");
            } else {
                send_data_pin.setLow();
                System.out.print("0");
            }

            Utils.sleep(10);
            send_pin.setHigh();
            System.out.println("");

            while (!(receive_pin.isHigh()))
                if(++error == this.error_threshold)
                    return -1;

            while (!(receive_pin.isLow()))
                if(++error == this.error_threshold)
                    return -1;

            send_pin.setLow();

            // shift byte left so next bit will be leftmost
            data <<= 1;
        }

        // deselect device
        send_data_pin.setLow();
        return 1;
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
