/*
 * Copyright (c) 2007, 2008 Sun Microsystems, Inc.
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

package org.sunspotworld.demo;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.Vector;
import java.util.StringTokenizer;

/**
 * A simple class to store up to 10 minutes of telemetry data, display it on the screen, 
 * do some simple filtering of the data and read/write it to a file. 
 *
 * @author Ron Goldman<br>
 * Date: May 2, 2006<br>
 * Revised: August 3, 2007<br>
 * Revised: August 21, 2008<br>
 * Revised: August 1, 2010
 */
public class GraphView extends JPanel {

    private static final String VERSION_STRING = "Sun SPOT TelemetryDemo Version 2.0";

    /** Specifies the width of the y-axis display. */
    public static final int AXIS_WIDTH = 60;
    private static final double BORDER = 20;
    private static final int MSEC_PER_PIXEL = 20;           // define scale for zoom = 1
    private static final int MSEC_OF_DATA = 10 * 60 * 1000; // can record 10 minutes of data
    private static final int MSEC_PER_SAMPLE = 10;

    private static final int PREFERRED_HEIGHT = 500;
    private static final int MINIMUM_WIDTH = 800;
    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int MILLISECONDS_PER_MINUTE = 60 * MILLISECONDS_PER_SECOND;
    
    static private final Color G_COLOR = new Color(255, 140, 0); // Dark Orange
    static private final Color X_COLOR = new Color(0, 150, 0); // Medium Green
    static private final Color Y_COLOR = Color.BLUE;
    static private final Color Z_COLOR = Color.RED;

    private int orgX, orgY;         // Pixel coord of the X and Y origin of the graph.
    private int yMinPixels;         // Pixel coord of the minimal point on the Y axis
    private int borderX, borderY;   // Width/height of region outside graph in pixels.
    private double yMin, yMax;      // min and max data values (not pixels, data).
    private long xMin, xMax;
    private int indexMax;

    private double scaleZoomY = 1;  // factor to expand the y-axis by: 1, 2, 4, or 8
    private double scaleZoomX = 2;  // factor/2 to expand the x-axis by: 1/2 1, 2, or 4
    private double scaleX;          // pixels / millisecond
    private double scaleY;          // pixels / G's'
    private double gOffsetX = 0.0;
    private double gOffsetY = 0.0;
    private double gOffsetZ = -1.0;
    
    private String id = "";
    private int scale = 2;          // max G value in accelerometer range
    
    private long[] timeMS;          // reported latest time for each guy.

    private double[] yDataG;        // Raw data collected as it comes in
    private double[] yDataX;
    private double[] yDataY;
    private double[] yDataZ;

    private JPanel axisPanel = null;
    private double maxG = 0;
    private JLabel maxGLabel = null;
    private boolean showX = true;
    private boolean showY = true;
    private boolean showZ = true;
    private boolean showG = true;
    private boolean includeGravity = true;
    private JViewport port = null;
    private Rectangle viewRect = null;
    private boolean smooth = false;
    private boolean boxcar = true;
    private int filterWidth = 10;
    private int halfWindowSize = filterWidth / 2;
    private boolean fileData = false;
    private double rawThreshold = 0.0;
    private double threshold = 0.0;
    private boolean ignoreMsgPrinted = false;
        
    // make these global to keep some state about the current drawn panel
    private int left = 0;
    private int right = 0;


    /**
     * Creates a new instance of GraphPanel. 
     * Set up our data structures (= simple arrays) and define some constants.
     */
    public GraphView() {
        setBackground(Color.WHITE);
        borderY = 20;
        borderX = AXIS_WIDTH;
        int size = MSEC_OF_DATA / MSEC_PER_SAMPLE + 20;    // add some extra space
        timeMS = new long[size];
        yDataG = new double[size];
        yDataX = new double[size];
        yDataY = new double[size];
        yDataZ = new double[size];        
        indexMax = 0;
        setGraphingAttributes();
        setDisplaySize();
        repaint();
    }

    /**
     * Specify the panel displaying the Y-axis.
     *
     * @param ax the panel to be used to display the y-axis
     */
    public void setAxisPanel(JPanel ax) {
        axisPanel = ax;
        resetDisplaySize();
    }

    /**
     * Returns the range the accelerometer data was recorded using.
     *
     * @return the scale range
     */
    public int getScale() {
        return scale;
    }

    /**
     * Returns the height of the viewport displaying the graphed data.
     *
     * @return the height of the enclosing viewport
     */
    private int getContainerHeight() {
        return (port != null && port.getHeight() > 100) ? port.getHeight() : PREFERRED_HEIGHT;
    }

    private int getContainerWidth() {
        return (port != null && port.getWidth() > 100) ? port.getWidth() : MINIMUM_WIDTH;
    }

    
    /**
     * Update the display size of the graphed data. Called after changing the X zoom factor or
     * after changing the number of samples displayed.
     */
    private void setDisplaySize() {
        int oldWidth = getWidth();
        int oldHeight = getHeight();
        if (port != null) {
            viewRect = port.getViewRect();
        }
        int preferredWidth = (int)(scaleX * (timeMS[(indexMax > 0 ? (indexMax - 1) : 0)] - timeMS[0]) + BORDER);
        if (preferredWidth < MINIMUM_WIDTH) {
            preferredWidth = getContainerWidth();
        }
        int preferredHeight = getContainerHeight();
        if (scaleZoomY > 1) {
            preferredHeight = (int)(scaleZoomY * preferredHeight);
        }
        Dimension size = new Dimension(preferredWidth, preferredHeight);
        setMaximumSize(size);
        setPreferredSize(size);
        setSize(size);
        if (port != null) {
            int newWidth = getWidth();
            int newHeight = getHeight();
            long curXpos = viewRect.x;
            int curYpos = viewRect.y + viewRect.height / 2;
            port.setViewPosition(new Point((int)(curXpos * (newWidth - BORDER) / (oldWidth - BORDER)),
                                           curYpos * newHeight / oldHeight - viewRect.height / 2));
            viewRect = port.getViewRect();
            port.setViewSize(size);
        }
    }

    /**
     * Update the display size of the graphed data after the window is resized.
     */
    private void resetDisplaySize() {
        setGraphingAttributes();
        setDisplaySize();
        repaint();
        if (axisPanel != null) {
            axisPanel.setSize(axisPanel.getWidth(), getContainerHeight());
            axisPanel.repaint();
        }
    }


    /**
     * Set parameters for drawing graph appropriately.
     */
    private void setGraphingAttributes() {
        xMin = 0;                               // min sample array index used
        xMax = MSEC_OF_DATA;                    // max sample array index used.
        if (scaleZoomX == 0.0) { scaleZoomX = 1; }
        scaleX = scaleZoomX / (double) (2 * MSEC_PER_PIXEL);   // pixel / msec
        orgX = 0;

        yMax = scale * 1.75;  // was 3.5 for 2G & 10.0 for 6G
        yMin = -yMax;
        int ht = getContainerHeight();
        if (scaleZoomY > 1) {
            ht = (int)(scaleZoomY * ht);
        }
        yMinPixels = (ht - borderY);
        scaleY = (yMinPixels - borderY) / (yMax - yMin);
        orgY = (int)(yMinPixels + yMin * scaleY);
    }

    /**
     * Return the width that the current telemetry data occupies.
     * Used by printing to determine number of pages needed.
     *
     * @return the width (in pixels) that the current telemetry data occupies
     */
    public int getMaxWidth() {
        if (indexMax > 0) {
            return (int)((timeMS[indexMax - 1] - xMin) * scaleZoomX / 10) + borderX;
        } else {
            return (int)((xMax - xMin) * scaleZoomX / 10) + borderX;
        }
    }


    /* Routines to read & write telemetry data to a file */
    
    /**
     * Write the current acceleration telemetry data out to a file.
     *
     * @param file the file to write the data into
     * @return true if successful, false otherwise
     */
    public boolean writeData(File file) {
        boolean results = false;
        try {
            BufferedWriter logFile = new BufferedWriter(new FileWriter(file));
            logFile.write(VERSION_STRING + "\n");
            // save the scale & calibrated rest offsets in first sample written
            logFile.write(id + "," + timeMS[0] + "," + 0 + "," +
                              yDataX[0] + "," + yDataY[0] + "," + yDataZ[0] + "," +
                              scale + "," +
                              gOffsetX + "," + gOffsetY + "," + gOffsetZ + ",\n");
            for (int i = 1; i < indexMax; i++) {
                logFile.write(id + "," + timeMS[i] + "," + i + "," +
                              yDataX[i] + "," + yDataY[i] + "," + yDataZ[i] + ",\n");
            }
            results = true;
            logFile.close();
        } catch (IOException ex) {
            System.out.println("Error writing out file: " + ex);
        }
        return results;
    }
    
    /**
     * Read in acceleration telemetry data from a file.
     *
     * @param file the file to read the data from
     * @return true if successful, false otherwise
     */
    public boolean readTelemetryFile(File file) {
        boolean results = false;
        boolean firstLine = true;
        boolean oldFormat = true;
        int sc = scale;
        try {
            BufferedReader in = new BufferedReader(new FileReader(file));
            fileData = true;
            String str;
            while ((str = in.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    if (str.equals(VERSION_STRING)) {
                        oldFormat = false;
                        continue;           // skip to next line
                    } else {
                        oldFormat = true;
                    }
                }
                StringTokenizer stk = new StringTokenizer(str, ";,");
                String address = stk.nextToken();
                long t    = Long.parseLong(stk.nextToken());
                int index = Integer.parseInt(stk.nextToken());
                double x  = Double.parseDouble(stk.nextToken());
                double y  = Double.parseDouble(stk.nextToken());
                double z  = Double.parseDouble(stk.nextToken());
                double g;
                if (oldFormat) {
                    g = Double.parseDouble(stk.nextToken());
                } else {
                    g = Math.sqrt(x * x + y * y + z * z);
                }
                if (index == 0 && stk.hasMoreTokens()) {
                    if (oldFormat) {
                        boolean twoG = Boolean.parseBoolean(stk.nextToken());
                        sc = twoG ? 2 : 6;
                    } else {
                        sc = Integer.parseInt(stk.nextToken());
                    }
                    double gx = Double.parseDouble(stk.nextToken());
                    double gy = Double.parseDouble(stk.nextToken());
                    double gz = Double.parseDouble(stk.nextToken());
                    setGOffset(gx, gy, gz);
                }
                takeData(address, t, index, x, y, z, g, sc);
            }
            results = true;
            setDisplaySize();
            in.close();
        } catch (FileNotFoundException e) {
            System.out.println("Could not open file: " + file.getPath());
        } catch (IOException e) {
            System.out.println("Error reading data from file: " + file.getName() + " : " + e);
        }
        repaint();
        return results;
    }

    /**
     * Add new telemetry data from remote SPOT.
     *
     * @param id IEEE address of SPOT sending the data
     * @param tMS time in milliseconds when the data was recorded
     * @param index index of this reading
     * @param g the combined accelerations of all three dimensions (in gravities)
     * @param x the x-axis acceleration (in gravities)
     * @param y the y-axis acceleration (in gravities)
     * @param z the z-axis acceleration (in gravities)
     * @param scale the accelerometer scale
     */
    public void takeData (String id, long tMS, int index, double x, double y, double z, double g, int sc) {
        if (tMS > MSEC_OF_DATA || index >= timeMS.length) {
            if (!ignoreMsgPrinted) {
                System.out.println("Ignoring accelerometer data: not enough memory allocated");
                ignoreMsgPrinted = true;
            }
            return;  // ignore any more data than we can display
        }
        if (index < indexMax) {
            System.out.println("Index value has decreased!!! " + index + " was " + indexMax);
        }
        
        this.id = id;
        this.scale = sc;
        
        if (g > maxG){
            maxG = g;
            if (maxGLabel != null) {
                int imaxG = (int)(maxG * 100);
                maxGLabel.setText(Double.toString(imaxG / 100.0));
            }
        }
        
        indexMax = index;
        timeMS[index] = tMS;

        yDataG[index] = g;

        yDataX[index] = x;
        yDataY[index] = y;
        yDataZ[index] = z;

        if (!fileData) {
            if (viewRect != null && ((int)(orgX + (tMS - xMin) * scaleX)) >= (viewRect.x + viewRect.width)) {
                setDisplaySize();
                port.setViewPosition(new Point(viewRect.x + viewRect.width / 2, viewRect.y));
                viewRect = port.getViewRect();
            }
            repaint();
        }
    }

    
    /* Routines to connect with GUI components */
    
    /**
     * Connect us with the view port that is displaying us. Needed so we can 
     * auto scroll as data is entered.
     *
     * @param viewport the JViewport to scroll to control what data is displayed
     */
    public void setViewport(JViewport viewport) {
        port = viewport;
        viewRect = port.getViewRect();
        final GraphView gv = this;
        port.addComponentListener(new ComponentAdapter() {
            public void componentResized (ComponentEvent e) {
                gv.resetDisplaySize();
            }
        });
    }

    /**
     * Label to use to display maximum G force recorded.
     *
     * @param lab the label to use to display the maximum G force encountered
     */
    public void setMaxGLabel (JLabel lab) {
        maxGLabel = lab;
        int imaxG = (int)(maxG * 100);   // only show 2 decimal digits
        maxGLabel.setText(Double.toString(imaxG / 100.0));
    }

    
    /* Command routines called by the user via the GUI */
    
    /**
     * Flush any current data and clear the display.
     */
    public void clearGraph () {
        indexMax = 0;
        maxG = 0;
        port.setViewPosition(new Point(0, viewRect.y));
        viewRect = port.getViewRect();
        ignoreMsgPrinted = false;
        fileData = false;
        resetDisplaySize();
    }    

    /**
     * Enable/disable the display of the combined G forces.
     *
     * @param b true if the combined G forces should be displayed
     */
    public void setShowG (boolean b) {
        showG = b;
        repaint();
    }

    /**
     * Enable/disable the display of the x-axis G forces.
     *
     * @param b true if the x-axis G forces should be displayed
     */
    public void setShowX (boolean b) {
        showX = b;
        repaint();
    }

    /**
     * Enable/disable the display of the y-axis G forces.
     *
     * @param b true if the y-axis G forces should be displayed
     */
    public void setShowY (boolean b) {
        showY = b;
        repaint();
    }

    /**
     * Enable/disable the display of the z-axis G forces.
     *
     * @param b true if the z-axis G forces should be displayed
     */
    public void setShowZ (boolean b) {
        showZ = b;
        repaint();
    }

    /**
     * Enable/disable the smoothing of the data with a filter.
     *
     * @param b true if the data displayed should be smoothed.
     */
    public void setSmooth (boolean b) {
        smooth = b;
        repaint();
    }

    /**
     * Select which filter to use when smoothing the data.
     *
     * @param b true for the boxcar filter, false for the triangle filter
     */
    public void setFiltertype (boolean b) {
        boxcar = b;
        if (smooth) {
            repaint();
        }
    }
    
    /**
     * Specify the filter's window size.
     *
     * @param w the number of samples to use when filtering
     */
    public void setFilterWidth (int w) {
        filterWidth = w;
        if ((filterWidth % 2) == 1) {       // make sure filterWidth is even
            filterWidth++;
        }
        halfWindowSize = filterWidth / 2;
        if (smooth) {
            repaint();
        }
    }
    
    /**
     * Set the scale factor for the x-axis.
     *
     * @param s the scale / 2, so s = 1, means 1/2 size, s = 4 means double size
     */
    public void setZoomX (int s) {
        scaleZoomX = s;
        resetDisplaySize();
    }

    /**
     * Set the scale factor for the y-axis.
     *
     * @param s the scale, so s = 1, means normal size, s = 2 means double size
     */
    public void setZoomY (int s) {
        scaleZoomY = s;
        resetDisplaySize();
    }

    /**
     * Set the rest offsets used by the accelerometer.
     * Used when we need the display to include the force of gravity.
     *
     * @param gx the X-axis rest offset
     * @param gy the Y-axis rest offset
     * @param gz the Z-axis rest offset
     */
    public void setGOffset(double gx, double gy, double gz) {
        gOffsetX = gx;
        gOffsetY = gy;
        gOffsetZ = gz;
    }

    /**
     * Change the scale for the Y axis
     *
     * @param newScale
     */
    public void setScale(int newScale) {
        scale = newScale;
        resetDisplaySize();
    }
            
    /**
     * Specify whether the forces displayed should include gravity or not.
     *
     * @param includeGravity true if gravity should be shown
     */
    public void setIncludeGravity(boolean includeGravity) {
        this.includeGravity = includeGravity;
        repaint();
    }
    
    /**
     * Cause the display to be repainted. Also repaints the y-axis panel.
     */
    public void repaint() {
        super.repaint();
        if (axisPanel != null) {
            axisPanel.repaint();
        }
    }

    /**
     * Paint the X-axis & the G forces recorded. The Y-axis is now drawn in a separate panel.
     *
     * @param g the graphics component to use to paint things
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        // paintYaxis(g);               // now drawn in special axis panel
        paintXaxis(g);
        paintLegend(g);
        drawData(g);
    }

    /**
     * Paint a legend showing what color is used to draw each acceleration component.
     * Only paint the legend for forces that are being displayed.
     *
     * @param g the graphics component to use to paint things
     */
    private void paintLegend(Graphics g) {
        int xpos = orgX + 40;
        int x0 = xpos + 20;
        int x1 = x0 + 85;
        int ypos = 20;
        int y0 = ypos - 5;
        int ydelta = 15;
        g.setColor(Color.GRAY);
        g.drawString("Time in seconds", xpos, ypos);
        ypos += ydelta + 1;
        y0 += ydelta + 1;
        if (showX) {        // Draw X acceleration in Gs
            g.setColor(X_COLOR);
            g.drawString("aX", xpos, ypos);
            g.drawLine(x0, y0, x1, y0);
            ypos += ydelta;
            y0 += ydelta;
        }
        if (showY) {        // Draw Y acceleration in Gs
            g.setColor(Y_COLOR);
            g.drawString("aY", xpos, ypos);
            g.drawLine(x0, y0, x1, y0);
            ypos += ydelta;
            y0 += ydelta;
        }
        if (showZ) {        // Draw Z acceleration in Gs
            g.setColor(Z_COLOR);
            g.drawString("aZ", xpos, ypos);
            g.drawLine(x0, y0, x1, y0);
            ypos += ydelta;
            y0 += ydelta;
        }
        if (showG) {    // Draw the total acceleration in Gs
            g.setColor(G_COLOR);
            g.drawString("|a|", xpos - 1, ypos);
            g.drawLine(x0, y0, x1, y0);
            ypos += ydelta;
            y0 += ydelta;
        }
    }

    private int findX(long x, boolean prior) {
        // check the bounds
        if (x < timeMS[0] || indexMax == 0) return 0;
        if (x > timeMS[indexMax - 1]) return indexMax - 1;

        //  binary search to find the time in the array
        int size = indexMax / 2;
        int index = size;
        while (size >= 1 && index > 0 && index < indexMax) {
            size /= 2;
            if (timeMS[index] < x) {
                index += size;
            } else if (timeMS[index] > x) {
                index -= size;
            } else {
                break;  // found it
            }
        }

        // another bounds check? 
        if (index < 0) { index = 0; }
        if (index >= indexMax) { index = indexMax - 1; }
        
        // linear search the rest of the path ?   
        if (prior) {
            while (index > 0 && timeMS[index] >= x) { index--; }
        } else {
            while (index < (indexMax - 1) && timeMS[index] <= x) { index++; }
        }
        
        return index;
    }
    
    /**
     * Draw the total acceleration recorded minus the force of gravity.
     *
     * @param g the graphics component to use
     * @param i0 start of section to draw
     * @param i1 end of section to draw
     */
    private void drawGValues(Graphics g, int i0, int i1) {
        int x0, x1, y0, y1;
        long t0, t1;
        boolean samePixel = false;
        int ymin, ymax;
        ymin = ymax = 0;
        double x = (smooth ? smooth(i0, yDataX) : yDataX[i0]) + gOffsetX;
        double y = (smooth ? smooth(i0, yDataY) : yDataY[i0]) + gOffsetY;
        double z = (smooth ? smooth(i0, yDataZ) : yDataZ[i0]) + gOffsetZ;
        double a = Math.sqrt(x*x + y*y + z*z);
        t0 = timeMS[i0];
        x0 = (int) (orgX + (t0 - xMin) * scaleX);
        y0 = (int) ((double) orgY - a * scaleY);
        for (int i = i0 + 1; i <= i1; i++) {
            x = (smooth ? smooth(i, yDataX) : yDataX[i]) + gOffsetX;
            y = (smooth ? smooth(i, yDataY) : yDataY[i]) + gOffsetY;
            z = (smooth ? smooth(i, yDataZ) : yDataZ[i]) + gOffsetZ;
            a = Math.sqrt(x*x + y*y + z*z);
            t1 = timeMS[i];
            x1 = (int) (orgX + (t1 - xMin) * scaleX);
            y1 = (int) ((double) orgY - a * scaleY);
            if (x0 == x1) {
                if (!samePixel) {
                    samePixel = true;
                    ymin = ymax = y0;
                }
                if (y1 < ymin) { ymin = y1; }
                else if (y1 > ymax) { ymax = y1; }
            } else {
                if (samePixel) {
                    samePixel = false;
                    g.drawLine(x0, ymin, x0, ymax);
                }
                else {
                    drawVTick(g,t1,x1,y1);
                }
                g.drawLine(x0, y0, x1, y1);
            }
            t0 = t1;
            x0 = x1;
            y0 = y1;
        }
    }

    /**
     * Smooth the data using a simple boxcar or triangle filter.
     *
     * @param i the index of the data sample to compute
     * @param raw the array containing the data values
     * @return the smoothed value for point at index
     */
    private double smooth (int i, double[] raw) {
        if (indexMax <= 0) return 0;
        int i0 = i - halfWindowSize;
        int i1 = i + halfWindowSize;
        int wt = 1;
        double r = 0.0;
        for (int j = i0; j <= i1; j++) {
            r += wt * raw[(j <= 0) ? 0 : (j >= indexMax) ? (indexMax - 1) : j];
            if (!boxcar) {
                if (j < i) {
                    wt += 2;
                } else {
                    wt -= 2;
                }
            }
        }
        return r / (boxcar ? (filterWidth + 1) : (2 * halfWindowSize * (halfWindowSize + 1) + 1));
    }

    /**
     * Draw a small tick mark at each sample if zoomed in sufficiently
     */
    private void drawVTick(Graphics g, long t, int x, int y) {
        if (scaleZoomX < 16) return;

        int sz = 2;
        Color cache = g.getColor();
        g.setColor(Color.BLACK);
        g.drawLine(x,y-sz,x,y+sz);
        g.setColor(cache);
    }
    
    /**
     * Draw one of the accelerometer forces recorded.
     *
     * @param g the graphics component to use
     * @param i0 start of section to draw
     * @param i1 end of section to draw
     * @param raw an array of raw accelerometer data for this axis
     * @param gOff any offset due to gravity to display
     */
    private void drawDataValues(Graphics g, int i0, int i1, double[] raw, double gOff) {
        int x0, x1, y0, y1;
        long t0, t1;
        boolean samePixel = false;
        int ymin, ymax;
        ymin = ymax = 0;
        double r = (smooth ? smooth(i0, raw) : raw[i0]);
        t0 = timeMS[i0];
        x0 = (int) (orgX + (t0 - xMin) * scaleX);
        y0 = (int) ((double) orgY - (r + gOff) * scaleY);
        drawVTick(g,t0,x0,y0);
        for (int i = i0 + 1; i <= i1; i++) {
            r = (smooth ? smooth(i, raw) : raw[i]);
            t1 = timeMS[i];
            x1 = (int) (orgX + (t1 - xMin) * scaleX);
            y1 = (int) ((double) orgY - (r + gOff) * scaleY);
            if (x0 == x1) {
                if (!samePixel) {
                    samePixel = true;
                    ymin = ymax = y0;
                }
                if (y1 < ymin) { ymin = y1; }
                else if (y1 > ymax) { ymax = y1; }
            } else {
                if (samePixel) {
                    samePixel = false;
                    g.drawLine(x0, ymin, x0, ymax);
                }
                g.drawLine(x0, y0, x1, y1);
                drawVTick(g,t0,x0,y0);
            }
            t0 = t1;
            x0 = x1;
            y0 = y1;
        }
    }
    
    /**
     * Draw the accelerometer forces recorded.
     *
     * @param g the graphics component to use
     */
    private void drawData(Graphics g) {
        if (indexMax <= 0) return;
        Rectangle clip = g.getClipBounds();
        left = clip.x;
        right = clip.x + clip.width;
        if (clip.width < 60) {  // make it a little wider so labels get redrawn if scrolling
            left -= 30;
            right += 30;
        }
        long xLeft = (long)(xMin + (left - orgX) / scaleX);
        long xRight = (long)(xMin + (right - orgX) / scaleX);
        int i0 = findX(xLeft, true);
        int i1 = findX(xRight, false);        
        if (showG) {    // Draw the total acceleration in Gs
            g.setColor(G_COLOR);
            if (includeGravity) {
                drawDataValues(g, i0, i1, yDataG, 0);
            } else {
                drawGValues(g, i0, i1);
            }
        }
        if (showX) {        // Draw X acceleration in Gs
            g.setColor(X_COLOR);
            drawDataValues(g, i0, i1, yDataX, includeGravity ? 0 : gOffsetX);
        }
        if (showY) {        // Draw Y acceleration in Gs
            g.setColor(Y_COLOR);
            drawDataValues(g, i0, i1, yDataY, includeGravity ? 0 : gOffsetY);
        }
        if (showZ) {        // Draw Z acceleration in Gs
            g.setColor(Z_COLOR);
            drawDataValues(g, i0, i1, yDataZ, includeGravity ? 0 : gOffsetZ);
        }
    }

    /**
     * Draw the tick marks on the acceleration axis (= y-axis).
     * The y-axis is drawn in a separate display panel.
     *
     * @param g the graphics component to use
     */
    public void paintYaxis (Graphics g) {
        int y;
        int orgX = AXIS_WIDTH - 1;
        double ySize = yMax - yMin;
        int yTop = (port != null) ? port.getViewPosition().y : 0;
        
        // Y axis (line)
        g.drawLine(orgX, yMinPixels - yTop, orgX, yMinPixels - (int) (ySize * scaleY) - yTop);

        // Paint big deltas
        double dt = scale == 2 ? 1.0 : 2.0;
        if (scaleZoomY > 1) {
            dt = dt / scaleZoomY;
        }
        int dOff = (dt >= 0.5) ? 0 : (dt >= 0.25) ? 7 : 14;
        g.drawString("0 G", orgX - 30 - dOff, orgY - yTop + 4);
        for (double t = dt; t <= yMax; t = t + dt) {
            int yPos = (int) ((double) orgY - t * scaleY) - yTop;
            int yNeg = (int) ((double) orgY + t * scaleY) - yTop;
            int tOff = (t >= 10.0 && dOff == 0) ? 7 : 0;
            g.drawString((""  + t), orgX - 34 - dOff - tOff, yPos + 4);
            g.drawLine(orgX - 12, yPos, orgX, yPos);
            g.drawString(("-" + t), orgX - 42 - dOff - tOff, yNeg + 4);
            g.drawLine(orgX - 12, yNeg, orgX, yNeg);
        }
        
        // Paint smaller deltas
        dt = dt / 2.0;
        for (double t = dt; t <= yMax; t = t + dt) {
            int yPos = (int) ((double) orgY - t * scaleY) - yTop;
            int yNeg = (int) ((double) orgY + t * scaleY) - yTop;
            g.drawLine(orgX - 4, yPos, orgX, yPos);
            g.drawLine(orgX - 4, yNeg, orgX, yNeg);
        }
    }

    /**
     * Draw the tick marks on the time axis (= x-axis)
     *
     * @param g the graphics component to use
     */
    private void paintXaxis (Graphics g) {
        Rectangle clip = g.getClipBounds();
        int left = clip.x;
        int right = clip.x + clip.width;
        if (clip.width < 80) {  // make it a little wider so labels get redrawn if scrolling
            left -= 40;
            right += 40;
        }
        long xLeft = (long)(xMin + (left - orgX) / scaleX);
        long xRight = (long)(xMin + (right - orgX) / scaleX);
        if (xRight > xMax) {
            xRight = xMax;
            right = orgX + (int)((xMax - xMin) * scaleX);
        }
        if (rawThreshold > 0) {
            g.setColor(Color.LIGHT_GRAY);
            int th = (int)(scaleY * rawThreshold);
            g.drawLine(left, orgY - th, right, orgY - th);
            g.drawLine(left, orgY + th, right, orgY + th);            
            g.setColor(Color.BLACK);
        }
        if (threshold > 0) {
            g.setColor(Color.YELLOW);
            int th = (int)(scaleY * threshold);
            g.drawLine(left, orgY - th, right, orgY - th);
            g.drawLine(left, orgY + th, right, orgY + th);            
            g.setColor(Color.BLACK);
        }
        
        // X axis (line)
        // g.drawLine(orgX, orgY, orgX + (int)((xMax - xMin) * scaleZoomX / 2), orgY);
        g.drawLine(left, orgY, right, orgY);
        long xSize = xMax - xMin;
            
        int oneMinute = (int)(scaleX * MILLISECONDS_PER_MINUTE);
        int minutesShowing = (port != null ? port.getWidth() : 700) / ((oneMinute == 0) ? 1 : oneMinute);

        int dx = MILLISECONDS_PER_MINUTE;
        int sx = (int)(xLeft % dx);
        long x;
        for (x = xLeft + dx - sx; x <= xRight; x += dx) {
            int gx = orgX + (int)((x - xMin) * scaleX);
            int minute = (int)(x / MILLISECONDS_PER_MINUTE);
            g.drawString(minute + ":00", gx - 12, orgY + 25);
            g.drawLine(gx, orgY - 6, gx, orgY + 6);
        }

        int oneSecond = (int)(scaleX * MILLISECONDS_PER_SECOND);

        if (oneSecond > 0) {     // add marks for some seconds
            int secsPerMark = (oneSecond > 24) ? 1 : (oneSecond > 10) ? 10 : 30; 
            int secsPerTime = (oneSecond > 49) ? 1 : (oneSecond > 24) ? 5 : (oneSecond > 10) ? 10 : 30; 
            dx = MILLISECONDS_PER_SECOND;
            sx = (int)(xLeft % dx);
            for (x = xLeft + dx - sx; x <= xRight; x += dx) {
                int gx = orgX + (int)((x - xMin) * scaleX);
                int second = (int)(x / MILLISECONDS_PER_SECOND) % 60;
                String leading = (second >= 10) ? ":" : ":0";
                if (second % secsPerMark == 0) {
                    if (second > 0 && second % secsPerTime == 0) {
                        int minute = (int)(x / MILLISECONDS_PER_MINUTE);
                        g.drawString(minute + leading + second, gx - 12, orgY + 25);
                        g.drawLine(gx, orgY - 6, gx, orgY + 6);
                    } else {
                        g.drawLine(gx, orgY - 3, gx, orgY + 3);
                    }
                }
            }
        }

        if (scaleX > 0.04) {     // add marks for 500/250/100 milliseconds
            int msecsPerMark = (scaleX < 0.06) ? 500 : (scaleX < 0.11) ? 250 : 100; 
            dx = 50;
            sx = (int)(xLeft % 100);
            for (x = xLeft + dx - sx; x <= xRight; x += dx) {
                int gx = orgX + (int)((x - xMin) * scaleX);
                if (x % msecsPerMark == 0) {
                    g.drawLine(gx, orgY - 3, gx, orgY + 3);
                }
            }
        }
    }

}
