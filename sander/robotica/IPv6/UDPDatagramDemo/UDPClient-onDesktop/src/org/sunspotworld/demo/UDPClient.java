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
 *
 * Created on Jul 13, 2009 2:26:58 PM;
 */
package org.sunspotworld.demo;

import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.ipv6.IP;
import com.sun.spot.util.Utils;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;

/**
 * Sample Sun SPOT host application
 */
public class UDPClient {

    private static final int PORT_NUMBER = 8888;

    /**
     * contact remote target and read values
     */
    public void run(String target) {
        UDPConnection sendConn;
        String url;

        Utils.sleep(1000);  // Let IPv6 startup under us
        System.out.println("[APP] Sending data to = " + target);
        boolean isIP6Addr = (target.indexOf(":") > -1);
        if (isIP6Addr) {
            url = "udp://[" + target + "]:" + PORT_NUMBER;
        } else {
            // we hope it is an IEEEAddress or hostname
            url = "udp://" + target + ":" + PORT_NUMBER;
        }

        Datagram dg = null;
        try {
            System.out.println("[APP] URL: " + url);
            sendConn = (UDPConnection) Connector.open(url);
            dg = sendConn.newDatagram(sendConn.getMaximumLength());
            // Write the command into the datagram
            dg.writeUTF("SENDDATA");
            // Send the command to the server
            sendConn.send(dg);
            // Wait for a response
            sendConn.receive(dg);
            System.out.println("[APP] Response received");
            // Extract the contents
            int count = dg.readInt();
            String node = dg.readUTF();
            int light = dg.readInt();
            double temp = dg.readDouble();
            System.out.println("[APP] Previous Connections: " + count + "\tLast: " + node);
            System.out.println("[APP] Light: " + light + "\tTemperature: " + temp);


            sendConn.close();
        } catch (Exception e) {
            System.out.println("[APP] Unable to contact host: " + target);
            e.printStackTrace();
        }

        System.exit(0);
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("TCPClient: must provide target address");
            System.out.println("Usage: ant host-run -Dmain.args=<target address>");
            System.exit(0);
        }

        UDPClient app = new UDPClient();
        app.run(args[0]);
    }
}
