/*
 * Copyright (c) 2010 Oracle.
 * Copyright (c) 2007 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this soft
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copiesware and associated documentation
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.resources.transducers.IOutputPin;
import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.sensorboard.*;

import com.sun.spot.io.j2me.radiogram.*;

import com.sun.spot.util.Utils;

import java.io.*;
import javax.microedition.io.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * A simple MIDlet that uses the left switch (SW1) to broadcast a message
 * to set the color of the LEDs of any receiving SPOTs and the right 
 * switch (SW2) to count in binary in its LEDs.
 *
 * Messages received from the other SPOTs control the LEDs of this SPOT.
 */
public class BroadcastCount extends MIDlet {

    private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    IAccelerometer3D accelerometer = (IAccelerometer3D) Resources.lookup(IAccelerometer3D.class);
    double rot;
    private IOutputPin outs[] = EDemoBoard.getInstance().getOutputPins();
    private IAnalogInput ins[] = EDemoBoard.getInstance().getAnalogInputs();

    private void showCount(int count) {
        leds.setOff();
        if (count != 0) {
            if (count > 0) {
                for (int i = 4; i < count + 4; i++) {
                    leds.getLED(i).setColor(LEDColor.RED);
                    leds.getLED(i).setOn();
                }
            } else if (count < 0) {
                for (int i = 0; i < Math.abs(count); i++) {
                    leds.getLED(3 - i).setColor(LEDColor.RED);
                    leds.getLED(3 - i).setOn();
                }
            }
        }
    }

    protected void startApp() throws MIDletStateChangeException {
        new com.sun.spot.service.BootloaderListenerService().getInstance().start();
        System.out.println("Broadcast Counter MIDlet");
        read_package();
        write_package();
        int g = 0;

        IIOPin[] dPins = EDemoBoard.getInstance().getIOPins();

                dPins[EDemoBoard.D0].setAsOutput(true);
                dPins[EDemoBoard.D0].setHigh();
                dPins[EDemoBoard.D4].setAsOutput(true);
                dPins[EDemoBoard.D4].setHigh();
                for (int i = 1; i < 4; i++) {
                    dPins[i].setAsOutput(false);
                }
        double t;
        try {
            t = ins[0].getVoltage();

        while (true) {
            EDemoBoard.getInstance().getIOPins()[EDemoBoard.D0].isHigh();
            Utils.sleep(5000);
            for (int i = 1; i < 4; i++) {
                System.out.println(i + "D: " + dPins[i].isHigh() + " voltage : " + t);
            }
            
        }
        } catch (IOException ex) {
            ex.printStackTrace();
        }



    }

    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // Only called if startApp throws any exception other than MIDletStateChangeException
    }

    public void read_package() {
        new Thread() {

            public void run() {
                RadiogramConnection rx = null;
                Datagram rdg = null;

                try {
                    rx = (RadiogramConnection) Connector.open("radiogram://:37");
                    // Then, we ask for a datagram with the maximum size allowed
                    rdg = rx.newDatagram(rx.getMaximumLength());
                } catch (IOException e) {
                    System.out.println("Could not open radiogram receiver connection");
                    e.printStackTrace();
                    return;
                }
                try {
                    double prev_rot = 0;
                    while (true) {
                        leds.getLED(Math.abs(7)).setColor(LEDColor.WHITE);
                        leds.getLED(Math.abs(7)).setOn();
                        leds.getLED(Math.abs(0)).setColor(LEDColor.WHITE);
                        leds.getLED(Math.abs(0)).setOn();
                        rot = accelerometer.getTiltX();

                        if (rot >= prev_rot + (Math.PI / 8) || rot <= prev_rot - (Math.PI / 8)) {
                            prev_rot = rot;
                        }
                        rx.receive(rdg);
                        int cmd = rdg.readInt();
                        //System.out.println("Received packet from " + rdg.getAddress() + ": " + cmd);
                        showCount(cmd);
                    }
                } catch (IOException ex) {
                    System.out.println("Error opening connections: " + ex);
                    ex.printStackTrace();
                }
            }
        }.start();
    }

    public void write_package() {
        new Thread() {

            public void run() {
                // We create a DatagramConnection
                DatagramConnection tx = null;
                Datagram xdg = null;
                try {
                    // The Connection is a broadcast so we specify it in the creation string
                    tx = (DatagramConnection) Connector.open("radiogram://broadcast:37");
                    // Then, we ask for a datagram with the maximum size allowed
                    xdg = tx.newDatagram(tx.getMaximumLength());
                } catch (IOException ex) {
                    System.out.println("Could not open radiogram broadcast connection");
                    ex.printStackTrace();
                    return;
                }
                while (true) {
                    leds.getLED(Math.abs(7)).setColor(LEDColor.WHITE);
                    leds.getLED(Math.abs(7)).setOn();
                    leds.getLED(Math.abs(0)).setColor(LEDColor.WHITE);
                    leds.getLED(Math.abs(0)).setOn();
                    try {
                        double rot = accelerometer.getAccelX();
                        int cmd = (int) (rot / 0.392699082);
                        xdg.reset();
                        xdg.writeInt(cmd);
                        tx.send(xdg);
                        //System.out.println("Send packet to " + xdg.getAddress() + ": " + cmd);
                    } catch (IOException ex) {
                        System.out.println("Error sending packet: " + ex);
                        ex.printStackTrace();
                    }

                    Utils.sleep(50);
                }

            }
        }.start();
    }

    public void pin_tests() {
        new Thread() {

            public void run() {
                // We create a DatagramConnection
            }
        }.start();
    }
}
