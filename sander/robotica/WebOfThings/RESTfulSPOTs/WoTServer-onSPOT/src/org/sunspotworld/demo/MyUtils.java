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

import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.squawk.util.StringTokenizer;
import java.io.IOException;
import java.util.Date;

/**
 *
 * @author vgupta
 */
public class MyUtils {
    public static void setColor(ITriColorLED led, String rgbStr) throws IOException {
        StringTokenizer st = new StringTokenizer(rgbStr, ",");
        if (st.countTokens() != 3) {
            throw new IOException("Incorrect number of values");
        }

        int rgb[] = new int[3];
        String val = "";
        try {
            for (int i = 0; i < 3; i++) {
                val = st.nextToken();
                rgb[i] = Integer.parseInt(val);
                if (rgb[i] < 0 || rgb[i] > 255) {
                    throw new IOException("Value " + val + " out of " +
                            "range (0-255)");
                }
            }
        } catch (NumberFormatException nfe) {
            throw new IOException("Value " + val + " not a number.");
        }

        led.setRGB(rgb[0], rgb[1], rgb[2]);
        led.setOn();
    }

    public static String getTimeStamp() {
        Date d = new Date();

        return (d.toString() + ": ");
    }
}

