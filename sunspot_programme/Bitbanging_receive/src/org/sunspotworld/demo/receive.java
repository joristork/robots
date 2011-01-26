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
    int error = 0;

    protected void startApp() {
        // Listen for downloads/commands over USB connection
        new com.sun.spot.service.BootloaderListenerService().getInstance().start();

        System.out.println("I'm about to rock that SPOT !");
        leds.setColor(LEDColor.MAUVE);
        leds.setOn();



        //startSenderThread();
        //startReceiverThread();
        send_pin.setAsOutput(true);
        send_data_pin.setAsOutput(true);
        receive_pin.setAsOutput(false);
        receive_data_pin.setAsOutput(false);

        send_pin.setLow();
        send_data_pin.setLow();



        while (true) {
            if(receive_pin.isHigh()&&receive_data_pin.isHigh()){
                System.out.println("Start receive.");
                int c = bitbang_receive();
                System.out.println(c);
            }
//            if(send_pin.getState()){
//                send_pin.setLow();
//            }
//            else{
//                send_pin.setHigh();
//                Utils.sleep(500);
//                send_pin.setLow();
//            }
        }

    }
    public int bitbang_receive() {
        int index = 7;
        int received = 0;
        send_pin.setLow();
        send_data_pin.setHigh();
        Utils.sleep(20);
        for (int i = 0; i < 8; i++) {
            while (receive_pin.isLow()) {
                error++;
                if(error == 2000){
                    error = 0;
                    return -1;
                }
            }
            error = 0;
            send_data_pin.setLow();
            received += (receive_data_pin.isHigh() ? pow(2, index) : 0);

            send_pin.setHigh();

            Utils.sleep(20);
            send_pin.setLow();

            index--;
            Utils.sleep(20);
        }
        return received;
    }



//    public int bitbang_receive() {
//        int index = 8;
//        int received = 0;
//        char result;
//        send_pin.isLow();
//        send_data_pin.isHigh();
//        System.out.println("Test 1");
//        System.out.println("D0: " + receive_pin.isHigh() + "|| D1: " + receive_data_pin.isHigh());
//        for (int i = 0; i < 8; i++) {
//            while (receive_pin.isLow()) {
//                System.out.println("Test 2");
//                System.out.println("D0: " + receive_pin.isHigh() + "|| D1: " + receive_data_pin.isHigh());
//                Utils.sleep(5000);
//            }
//            received += (receive_data_pin.isHigh() ? pow(2, index) : 0);
//
//            send_pin.setHigh();
//            send_data_pin.setLow();
//            Utils.sleep(20);
//            send_pin.setLow();
//
//            index--;
//        }
//        result = (char) received;
//        return received;
//    }

    protected void pauseApp() {
    }

    public int pow(int i, int p) {
        int result = (p==0) ? 1 : 2;
        for (int j = 1; j < p; j++) {
            result *= i;
        }
        return result;
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
