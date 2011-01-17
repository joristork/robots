/*
* Copyright (c) 2006-2010 Sun Microsystems, Inc.
* Copyright (c) 2010 Oracle
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
 **/

package org.sunspotworld.airstore.demos;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.util.Utils;
import java.io.IOException;
import javax.microedition.midlet.MIDletStateChangeException;
import org.sunspotworld.airstore.AirStore;

/**
 *  Accel.java
 */

public class Accel extends javax.microedition.midlet.MIDlet {

    protected void startApp() throws MIDletStateChangeException {
        IAccelerometer3D accel = (IAccelerometer3D)Resources.lookup(IAccelerometer3D.class);

        AirStore.put("Accel Help", "Started. Storing x and y acceleration components as x and y");

        int aX    = 0, aY    = 0;
        int oldAX = 0, oldAY = 0;

        while (true) {
            try {
                aX = (int) Math.abs(50.0 + (accel.getAccelX() * 50.0));
                aY = (int) Math.abs(50.0 + (accel.getAccelY() * 50.0));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(aX != oldAX) AirStore.put("x", aX);
            oldAX = aX;
            if(aY != oldAY) AirStore.put("y", aY);
            oldAY = aY;
            Utils.sleep(333);
        }
    }

    protected void pauseApp() {
    }

    protected void destroyApp(boolean arg) throws MIDletStateChangeException {
    }
}
