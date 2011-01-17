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

package org.sunspotworld.demo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import com.sun.spot.io.j2me.tcp.TCPConnection;
import com.sun.spot.io.j2me.udp.UDPConnection;
import java.util.Date;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import com.sun.spot.wot.http.DecodingException;
import com.sun.spot.wot.http.EncodingException;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;

/**
 *
 * @author vgupta
 */
public class HttpClient {
    String connStr = null;
    UDPConnection udpConn = null;
    TCPConnection tcpConn = null;
    boolean useTCP = false;
    boolean useCompression = false;

    public HttpClient(String connection, boolean useCompression) throws IOException {
        this.connStr = connection;
        this.useCompression = useCompression;
        if (connStr.startsWith("udp://")) {
            udpConn = (UDPConnection) Connector.open(connStr);
            udpConn.setTimeout(10000);
            useTCP = false;
        } else if (connStr.startsWith("tcp://")) {
            tcpConn = (TCPConnection) Connector.open(connStr);
            useTCP = true;
        } else {
            throw new IOException("Unsupported connection type in " +
                    connection);
        }
    }

    public HttpResponse sendData(HttpRequest req) throws IOException {
        HttpResponse resp = new HttpResponse();
        byte[] dataOut = new byte[WoTConstants.MAX_HTTPMSG_SIZE];
        int dataOutLength = -1;

        try {
            dataOutLength = req.toByteArray(useCompression, dataOut, 0);
        } catch (EncodingException ex) {
            ex.printStackTrace();
            throw new IOException("Could not encode request");
        }

        if (useTCP) {
            OutputStream os = tcpConn.openOutputStream();
            InputStream is = tcpConn.openInputStream();
            os.write(dataOut, 0, dataOutLength);
            try {
                resp.parse(is);
            } catch (DecodingException ex) {
                ex.printStackTrace();
                throw new IOException("Could not decode response");
            }
        } else {
            Datagram dg = udpConn.newDatagram(udpConn.getMaximumLength());
            dg.write(dataOut, 0, dataOutLength);
            Date date = new Date(System.currentTimeMillis());
//            System.out.println("On " + date + ", sending UDP data to " + connStr);
            udpConn.send(dg);
//            System.out.println("Waiting to receive UDP data from " + connStr);
            dg.reset();
            udpConn.receive(dg);
            byte[] dataIn = dg.getData();
            int dataInLength = dataIn.length;
            try {
                resp = HttpResponse.parse(dataIn, 0, dataInLength);
            } catch (DecodingException ex) {
                ex.printStackTrace();
                throw new IOException("Could not decode response");
            }
        }
        return resp;
    }

    void shutDown() {
        try {
            if (connStr.startsWith("udp")) {
//                System.out.println("Closing UDP connection");
                udpConn.close();
            }
            if (connStr.startsWith("tcp")) {
//                System.out.println("Closing TCP connection");
                tcpConn.close();
            }
        } catch (IOException ioe) {
            System.out.println("Caught " + ioe +
                    " in side HttpClient.shutDown");
            ioe.printStackTrace();
        }
    }
}
