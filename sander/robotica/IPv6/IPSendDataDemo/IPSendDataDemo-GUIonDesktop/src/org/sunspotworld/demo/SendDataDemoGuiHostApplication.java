/*
 * DatabaseDemoHostApplication.java
 *
 * Copyright (c) 2008-2009 Sun Microsystems, Inc.
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
 */

package org.sunspotworld.demo;

import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.io.j2me.udp.UDPDatagram;
import com.sun.spot.ipv6.IP;
import com.sun.spot.peripheral.ota.OTACommandServer;
import javax.microedition.io.*;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


/**
 * This application is the 'on Desktop' portion of the SendDataDemo. 
 * This host application collects sensor samples sent by the 'on SPOT'
 * portion running on neighboring SPOTs and graphs them in a window. 
 *   
 * @author Vipul Gupta
 * modified Ron Goldman
 */
public class SendDataDemoGuiHostApplication {
    // Broadcast port on which we listen for sensor samples
    private static final int HOST_PORT = 67;
    private JTextArea status;
    private String[] addresses = new String[8];
    private DataWindow[] plots = new DataWindow[8];
    
    private void setup() {
        JFrame fr = new JFrame("Send Data Host App");
        status = new JTextArea();
        JScrollPane sp = new JScrollPane(status);
        fr.add(sp);
        fr.setSize(360, 200);
        fr.validate();
        fr.setVisible(true);
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = null;
            plots[i] = null;
        }
    }
    
    private DataWindow findPlot(String addr) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == null) {
                String ieee = addr;
                status.append("Received packet from SPOT: " + ieee + "\n");
                addresses[i] = addr;
                plots[i] = new DataWindow(ieee);
                final int ii = i;
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        plots[ii].setVisible(true);
                    }
                });
                return plots[i];
            }
            if (addr.equalsIgnoreCase(addresses[i])) {
                return plots[i];
            }
        }
        return plots[0];
    }
    
    private void run() throws Exception {
       UDPConnection rCon;
       UDPDatagram dg;
        try {
            // Open up a server-side broadcast UDP connection
            // to listen for sensor readings being sent by different SPOTs
            rCon = (UDPConnection) Connector.open("udp://:" + HOST_PORT);
            dg = (UDPDatagram)rCon.newDatagram(rCon.getMaximumLength());
        } catch (Exception e) {
             System.err.println("setUp caught " + e.getMessage());
             throw e;
        }

        status.append("Listening...\n");

        // Main data collection loop
        while (true) {
            try {
                // Read sensor sample received over the radio
                rCon.receive(dg);
                DataWindow dw = findPlot(dg.getAddress());
//                DataWindow dw = findPlot(dg.getAddressAsLong());
                long time = dg.readLong();      // read time of the reading
                int val = dg.readInt();         // read the sensor value
                dw.addData(time, val);
            } catch (Exception e) {
                System.err.println("Caught " + e +  " while reading sensor samples.");
                throw e;
            }
        }
    }
    
    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) throws Exception {
        // register the application's name with the OTA Command server & start OTA running
        IP.main(null);

	System.out.println("[APP] Please ignore [radiogram] message.  Port 8 used for OTA command server.");
        // Opens a radiogram connection on port 8, for OTA use
        OTACommandServer.start("SendDataDemo-GUI");

        SendDataDemoGuiHostApplication app = new SendDataDemoGuiHostApplication();
        app.setup();
        app.run();
    }
}
