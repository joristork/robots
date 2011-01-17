/*
 * Copyright (c) 2008 Sun Microsystems, Inc.
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
 */

package org.sunspotworld;

import com.sun.spot.solarium.spotworld.participants.*;
import com.sun.spot.solarium.spotworld.common.*;
import com.sun.spot.solarium.gui.views.*;
import com.sun.spot.solarium.gui.*;
import java.awt.Dimension;
import java.awt.Point;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 * Proxy to a SunSPOT running the Send Data Demo.
 *
 * @author Ron Goldman
 */
public class SendDataSpot extends ESpot {

    private SendDataPanel tpane;
    
    public SendDataSpot() {
        super();
    }
 
    public SendDataSpot(String s) {
        super(s);
        tpane = new SendDataPanel(s);
    }

    public SendDataPanel getSendDataPanel() {
        return tpane;
    }

}
