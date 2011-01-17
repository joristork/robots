/*
 * Copyright (c) 2007 Sun Microsystems, Inc.
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

package org.sunspotworld.demo.util;

import com.sun.spot.io.j2me.radiogram.Radiogram;

/**
 * Interface used by a class wanting to be called back when packets are received
 * with a packet type that the class has registered an interest in receiving. The
 * contents of the first byte of the packet determines its type.
 *
 * @author Ron Goldman<br>
 * Date: July 31, 2007
 *
 * @see PacketReceiver
 */
public interface PacketHandler {
    
    /**
     * Application specific callback routine to receive packets that the class
     * has registered with the PacketReceiver.
     *
     * @param type the packet type = the first byte in the packet
     * @param pkt the radiogram packet received
     */
    void handlePacket(byte type, Radiogram pkt); 

}
