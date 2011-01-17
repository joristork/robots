/*
 * Copyright (c) 2009, Sun Microsystems
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the Sun Microsystems nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.sun.spot.wot.gateway;

import java.io.IOException;
import javax.microedition.io.Connector;
import com.sun.spot.io.j2me.udp.UDPConnection;
import com.sun.spot.io.j2me.udp.UDPDatagram;
import com.sun.spot.ipv6.Inet6Address;
import java.util.Date;
import com.sun.spot.wot.utils.PrettyPrint;

/**
 *
 * @author vgupta
 */
class UDP6Forwarder implements DeviceForwarder {
    private UDPConnection conn = null;

    public void openConn(String addr) {
        try {
//            System.out.println("UDPForwarder opening connection to addr: " + addr);
            conn = (UDPConnection) Connector.open("udp://" + addr);
            conn.setTimeout(15000);
        } catch (IOException ex) {
            ex.printStackTrace();
            conn = null;
            System.err.println("Failed to open datagram connection to " + addr);
        } finally {

        }
    }

    public void closeConn() {
        try {
            if (conn != null) conn.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public int sendAndGetResponse(String addr, byte[] sndBuf, int off1, int len1, byte[] rcvBuf, int off2) throws IOException {
        Date d = new Date();
        openConn(addr);
        UDPDatagram dg = (UDPDatagram) conn.newDatagram(conn.getMaximumLength());
        UDPDatagram idg = (UDPDatagram) conn.newDatagram(conn.getMaximumLength());

        // First send the data
        dg.write(sndBuf, off1, len1);
        d.setTime(System.currentTimeMillis());
//        System.out.println(d.toString() + ", Sending data in UDP6Forwarder to " +
//                addr);
//                new Inet6Address(idg.getDstAddresss()).toString() + ", port " + dg.getDstPort());
        conn.send(dg);
//        System.out.println("Sent data [len=" + len1 + "]:\n" +
//                PrettyPrint.prettyPrint(sndBuf, off1, len1));
        d.setTime(System.currentTimeMillis());
//        System.out.println(d.toString() + ", Awaiting response in UDP6Forwarder");
        conn.receive(idg);
        d.setTime(System.currentTimeMillis());
//        System.out.println(d.toString() + ", Received data in UDP6Forwarder from " +
//                new Inet6Address(idg.getSrcAddress()).toString() + ", port " + idg.getSrcPort());
        byte[] data = idg.getData();
        System.arraycopy(data, 0, rcvBuf, off2, data.length);
//        System.out.println("Reading data, off=" + off2);
//        System.out.println("Received data [len=" + data.length + "]:\n" +
//                    PrettyPrint.prettyPrint(rcvBuf, off2, data.length));

        //closeConn();
        return data.length;
    }

    public void close() {
        closeConn();
    }
}
