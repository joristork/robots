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
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

/**
 *
 * @author vgupta
 */
public class TCP6Forwarder extends StreamForwarder {
    Socket conn = null;
    StreamConnection sconn = null;
    public void openConn(String addr) {
        try {
            String[] parts = addr.split(":");
//            String[] parts = {
//                "fe80:0:0:0:0214:4f01:0000:5317",
//                "8888"
//            };
            if (parts.length == 1) {
//                System.out.println("Opening socket on " + addr + "...");
                conn = new Socket(addr, 80);
                is = conn.getInputStream();
                os = conn.getOutputStream();
            } else if (parts.length == 2) {
//                System.out.println("Opening socket on " + addr + "...");
                conn = new Socket(parts[0], Integer.parseInt(parts[1]));
                is = conn.getInputStream();
                os = conn.getOutputStream();
            } else {
                System.out.println("Opening tcp://" + addr + "...");
                sconn = (StreamConnection) Connector.open("tcp://" + addr);
                is = sconn.openInputStream();
                os = sconn.openOutputStream();
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(TCP6Forwarder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TCP6Forwarder.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException ex) {
            Logger.getLogger(TCP6Forwarder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void closeConn() {
        try {
            if (conn != null) {
                conn.close();
            }
            if (sconn != null) {
                sconn.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(TCP6Forwarder.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
