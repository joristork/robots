/*
 * Copyright 2006-2009 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 *
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * only, as published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included in the LICENSE file that accompanied this code).
 *
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 *
 * Please contact Sun Microsystems, Inc., 16 Network Circle, Menlo
 * Park, CA 94025 or visit www.sun.com if you need additional
 * information or have any questions.
 */
package org.sunspotworld.demo;

/*
 * StartApplication.java
 *
 * Created on Jun 30, 2009 2:02:28 PM;
 */
import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.io.j2me.udp.UDPDatagram;
import com.sun.spot.ipv6.IPUtils;
import com.sun.spot.ipv6.Inet6Address;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.*;

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
public class UDPServer extends MIDlet {

    private static final int PORT_NUMBER = 8888;
    private ILightSensor lightSensor = (ILightSensor) Resources.lookup(ILightSensor.class);
    private ITemperatureInput tempSensor = (ITemperatureInput) Resources.lookup(ITemperatureInput.class);
    private ITriColorLEDArray leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
    private byte connected[], lastConnected[] = null;
    int connectCount = 0;

    private void setLEDs(int r, int g, int b) {
        for (int i = 0; i < 8; i++) {
            leds.getLED(i).setRGB(r, g, b);
            leds.getLED(i).setOn();
        }
    }

    protected void startApp() throws MIDletStateChangeException {
        BootloaderListenerService.getInstance().start(); // monitor the USB (if connected) and recognize commands from host
        UDPConnection conn = null;
        UDPDatagram dg, rdg = null;
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        System.out.println("[APP] Our radio address = " + IEEEAddress.toDottedHex(ourAddr));
        String request = null;
        try {
            conn = (UDPConnection) Connector.open("udp://:" + PORT_NUMBER);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        while (true) {
            setLEDs(0, 0, 100); // Blue == waiting
            dg = (UDPDatagram) conn.newDatagram(conn.getMaximumLength());
            dg.reset();
            try {
                conn.receive(dg);
                setLEDs(100, 0, 0);  // red == connected/busy
                request = dg.readUTF();
                if (request.equalsIgnoreCase("SENDDATA")) {
                    connected = dg.getSrcAddress();
                    Inet6Address caddr = new Inet6Address(connected);
                    System.out.println("[APP] Request received from " + caddr + "  Processing...");
                    // Create datagram large enough for the response
                    // rewrite the destination address
                    rdg = (UDPDatagram) conn.newDatagram(conn.getMaximumLength());
                    rdg.setDstAddress(connected);
                    rdg.setDstPort(dg.getSrcPort());
                    // Populate the data
                    createResponse(rdg);
                    System.out.println("[APP] Sending response");
                    // Send the response
                    conn.send(rdg);
                    // remember this transaction for later reports
                    connectCount++;
                    lastConnected = connected;
                } else {// Else - ignore and grab the next request
                    System.out.println("[APP] Request received.  Bad command - Ignoring...");
                }
            } catch (IOException e) {
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
        for (int i = 0; i <
                8; i++) {
            leds.getLED(i).setOff();
        }
    }

    private void createResponse(UDPDatagram rdg) {
        try {

            // reset the datagram
            rdg.reset();

            // Do the real work here
            // Number of connections we've served prior to this one
            rdg.writeInt(connectCount);
            if (lastConnected != null) {
                rdg.writeUTF(IPUtils.addressToString(lastConnected));
            } else {
                rdg.writeUTF("None");
                // Light sensor reading
            }
            rdg.writeInt(lightSensor.getValue());
            // Temperature sensor reading
            rdg.writeDouble(tempSensor.getFahrenheit());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
