/*
 * Copyright (c) 2010 Oracle.
 * Copyright (c) 2007 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
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

import com.sun.spot.peripheral.Spot;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.SwitchEvent;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.util.*;

import java.io.*;
import javax.microedition.io.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * A simple MIDlet that pairs two SPOTs so each controls the others LEDs.
 * The left switch (SW1) sends a message to set the color of the LEDs of
 * its paired SPOT and the right switch (SW2) to count in binary in its LEDs.
 *
 * Messages received from the other SPOT controls the LEDs of this SPOT.
 *
 * Even addresses pair with smaller odd addresses, & odd addresses pair
 * with larger even addresses
 */
public class UnicastCount extends MIDlet implements ISwitchListener {

    private static final int CHANGE_COLOR = 1;
    private static final int CHANGE_COUNT = 2;

    private ITriColorLEDArray leds = (ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class);
    private ISwitch sw1 = (ISwitch)Resources.lookup(ISwitch.class, "SW1");
    private ISwitch sw2 = (ISwitch)Resources.lookup(ISwitch.class, "SW2");
    private int count = -1;
    private int color = 0;
    private LEDColor[] colors = { LEDColor.RED, LEDColor.GREEN, LEDColor.BLUE };
    private RadiogramConnection conn = null;
    private Radiogram xdg;
    
    private void showCount(int count, int color) {
        for (int i = 7, bit = 1; i >= 0; i--, bit <<= 1) {
            if ((count & bit) != 0) {
                leds.getLED(i).setColor(colors[color]);
                leds.getLED(i).setOn();
            } else {
                leds.getLED(i).setOff();
            }
        }
    }
    
    private void showColor(int color) {
        for (int i = 0; i < 8; i++) {
            leds.setColor(colors[color]);
            leds.setOn();
        }
    }
    
    protected void startApp() throws MIDletStateChangeException {
        System.out.println("Broadcast Counter MIDlet");
        showColor(color);
        sw1.addISwitchListener(this);
        sw2.addISwitchListener(this);
        try {
            long address = Spot.getInstance().getRadioPolicyManager().getIEEEAddress();
            if ((address & 1) == 0) {
                address -= 1;           // even addresses pair with smaller odd addresses
            } else {
                address += 1;           // odd addresses pair with larger even addresses
            }
            conn = (RadiogramConnection)Connector.open("radiogram://" + IEEEAddress.toDottedHex(address) + ":123");
            xdg = (Radiogram)conn.newDatagram(20);
            Radiogram rdg = (Radiogram)conn.newDatagram(20);
            while (true) {
                try {
                    conn.receive(rdg);
                    int cmd = rdg.readInt();
                    int newCount = rdg.readInt();
                    int newColor = rdg.readInt();
                    if (cmd == CHANGE_COLOR) {
                        System.out.println("Received packet from " + rdg.getAddress());
                        showColor(newColor);
                    } else {
                        showCount(newCount, newColor);
                    }
                } catch (IOException ex) {
                    System.out.println("Error receiving packet: " + ex);
                    ex.printStackTrace();
                }
            }
        } catch (IOException ex) {
            System.out.println("Error opening connections: " + ex);
            ex.printStackTrace();
        }
    }

    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }

    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // Only called if startApp throws any exception other than MIDletStateChangeException
    }

    public void switchReleased(SwitchEvent evt) {
        int cmd;
        if (evt.getSwitch() == sw1) {
            cmd = CHANGE_COLOR;
            if (++color >= colors.length) { color = 0; }
            count = -1;
        } else {
            cmd = CHANGE_COUNT;
            count++;
        }
        try {
            xdg.reset();
            xdg.writeInt(cmd);
            xdg.writeInt(count);
            xdg.writeInt(color);
            conn.send(xdg);
        } catch (IOException ex)  {
            System.out.println("Error sending packet: " + ex);
            ex.printStackTrace();
        }
    }

    public void switchPressed(SwitchEvent evt) {
    }
}
