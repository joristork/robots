/*
 * Copyright (c) 2008 Sun Microsystems, Inc.
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

import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

/**
 * Create a new window to graph the sensor readings in.
 *
 * @author Ron Goldman
 */
public class SendDataPanel extends JPanel implements Runnable {

    private static final int MAX_SAMPLES = 10000;
    private int index = 0;
    private long[] time = new long[MAX_SAMPLES];
    private int[] val = new int[MAX_SAMPLES];
    private String address;


    public SendDataPanel(String ieee) {
        super();
        address = ieee;
        setBackground(new Color(255, 255, 255));
        setMinimumSize(new Dimension(280, 210));
        setPreferredSize(new Dimension(280, 210));
        new Thread(this, "SendData " + address).start();
    }

    public void run() {
        RadiogramConnection rcon = null;
        Radiogram rg;
        try {
            rcon = (RadiogramConnection) Connector.open("radiogram://" + address + ":67");
            rg = (Radiogram)rcon.newDatagram(128);

            while (true) {      // should really only loop while Solarium is displaying the SPOT
                // Read sensor sample received over the radio
                rcon.receive(rg);
                long t = rg.readLong();      // read time of the reading
                int v = rg.readInt();         // read the sensor value
                addData(t, v);
            }
        } catch (IOException ex) {
            System.err.println("Error with radio connection to SPOT " + address + ": " + ex);
        } finally {
            try {
                rcon.close();
            } catch (IOException ex) {
                // ignore.
            }
        }
    }

    public void addData(long t, int v) {
        time[index] = t;
        val[index++] = v;
        repaint();
    }
    
    // Graph the sensor values in the dataPanel JPanel
    public void paint(Graphics g) {
        super.paint(g);
        int left = getX() + 10;       // get size of pane
        int top = getY() + 7;
        int right = left + getWidth() - 20;
        int bottom = top + getHeight() - 10;
        
        int y0 = bottom - 20;                   // leave some room for margins
        int yn = top;
        int x0 = left + 25;
        int xn = right;
        double vscale = (yn - y0) / 800.0;      // light values range from 0 to 800
        double tscale = 1.0 / 5000.0;           // 1 pixel = 5 seconds = 5000 milliseconds
        
        // draw X axis = time
        g.setColor(Color.BLACK);
        g.drawLine(x0, yn, x0, y0);
        g.drawLine(x0, y0, xn, y0);
        int tickInt = 60 / 2;
        for (int xt = x0 + tickInt; xt < xn; xt += tickInt) {   // tick every 1 minute
            g.drawLine(xt, y0 + 5, xt, y0 - 5);
            int min = (xt - x0) / (60 / 2);
            g.drawString(Integer.toString(min), xt - (min < 10 ? 3 : 7) , y0 + 19);
        }
        
        // draw Y axis = sensor reading
        g.setColor(Color.BLUE);
        for (int vt = 200; vt <= 800; vt += 200) {         // tick every 200
            int v = y0 + (int)(vt * vscale);
            g.drawLine(x0 - 5, v, x0 + 5, v);
            g.drawString(Integer.toString(vt), x0 - 33, v + 5);
        }

        // graph sensor values
        int xp = -1;
        int vp = -1;
        for (int i = 0; i < index; i++) {
            int x = x0 + (int)((time[i] - time[0]) * tscale);
            int v = y0 + (int)(val[i] * vscale);
            if (xp > 0) {
                g.drawLine(xp, vp, x, v);
            }
            xp = x;
            vp = v;
        }
    }

}
