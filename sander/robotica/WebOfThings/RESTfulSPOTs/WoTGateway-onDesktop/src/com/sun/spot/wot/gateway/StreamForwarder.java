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


import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.utils.PrettyPrint;


/**
 *
 * @author vgupta
 */
public abstract class StreamForwarder implements DeviceForwarder {
    protected InputStream is = null; 
    protected OutputStream os = null;

    public abstract void openConn(String addr);
    public abstract void closeConn();

    public void close() {
        closeConn();
    }
    
    public int sendAndGetResponse(String addr,
            byte[] sndBuf, int off1, int len1, byte[] rcvBuf, int off2) {
        int cnt = 0;
        try {
//            System.out.println("Connecting to " + addr);
            openConn(addr);

            int tmp = 0;
            int off = off2;


            // First send the data
            os.write(sndBuf, off1, len1);
            //os.write(0); // XXX Won't work for compression
            os.flush();
            //System.out.println("Sent data [len=" + len1 + "]:\n" +
            //        PrettyPrint.prettyPrint(sndBuf, off1, len1));

            HttpResponse resp = new HttpResponse();
            resp.parse(is);
            cnt = resp.toByteArray(true, rcvBuf, off2);
//            System.out.println("StreamForwarder received: \n" +
//                    PrettyPrint.prettyPrint(rcvBuf, off2, cnt));
//            System.out.println("Reading data, off=" + off);
//            while ((tmp = is.read()) !=  -1) {
//                System.out.println("Got " + (char) tmp);
//                rcvBuf[off++] = (byte) (tmp & 0xff);
//                cnt++;
//            }
        } catch (Exception ex) {
            Logger.getLogger(StreamForwarder.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception ex) {
                Logger.getLogger(StreamForwarder.class.getName()).log(Level.SEVERE, null, ex);
            }
            closeConn();
        }

        return cnt;
    }
}

