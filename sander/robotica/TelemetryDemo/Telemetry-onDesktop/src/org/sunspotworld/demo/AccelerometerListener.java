/*
 * Copyright (c) 2007 Sun Microsystems, Inc.
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

import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.peripheral.NoAckException;

import java.io.*;
import javax.microedition.io.*;

import javax.swing.*;

/**
 * Simple example class to locate a remote service (on a SPOT), to connect to it
 * and send it a variety of commands. In this case to set or calibrate the SPOT's
 * accelerometer and to return a stream of accelerometer telemetry information. 
 *
 * @author Ron Goldman<br>
 * Date: May 2, 2006<br>
 * Modified: August 1, 2010
 */
public class AccelerometerListener extends Thread implements PacketTypes {
        
    private double zeroOffsets[][] = { { 465, 465, 465 }, { 465, 465, 465 } };
    private double gains[][]       = { { 186.2, 186.2, 186.2 }, { 62.07, 62.07, 62.07 } };
    private double restOffsets[][] = { { 465, 465, 465 + 186 }, { 465, 465, 465 + 62 } };  // w/SPOT sitting flat Z up
    
    private String ieee;
    private RadiogramConnection conn = null;
    private Radiogram xdg = null;
    private boolean running = true;
    private long timeStampOffset = -1;
    private int index = 0;
    private int scaleInUse = 0;
    private int scales[] = { 2, 4, 8 };
    
    private GraphView graphView = null;
    private TelemetryFrame guiFrame = null;

    /**
     * Create a new AccelerometerListener to connect to the remote SPOT over the radio.
     */
    public AccelerometerListener (String ieee, TelemetryFrame fr, GraphView gv) {
        this.ieee = ieee;
        guiFrame = fr;
        graphView = gv;
    }
    
    /**
     * Report which scales the accelerometer supports.
     *
     * @return an array of the scales
     */
    public int[] getScales () {
        return scales;
    }

    /**
     * Report which scale the accelerometer is using.
     *
     * @return offset into scales array
     */
    public int getCurrentScale () {
        return scaleInUse;
    }

    /**
     * Update the GUI with the current connection status.
     */
    private void updateConnectionStatus (boolean isConnected) {
        if (guiFrame != null) {
            final String status;
            final boolean connectedp = isConnected;
            final TelemetryFrame fr = guiFrame;
            if (isConnected) {
                status = "Connected";
            } else {
                status = "Not Connected";
            }
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    fr.setConnectionStatus(connectedp, status);
                }
            });
        }
    }

    /**
     * Send a request to the remote SPOT to report on which accelerometer scale it is using.
     */
    public void doGetScale () {
        sendCmd(GET_ACCEL_INFO_REQ);
    }

    
    /**
     * Send a request to the remote SPOT to set which accelerometer scale it will use.
     *
     * @param val the scale to use: (2 or 6) or (2, 4 or 8)
     */
    public void doSetScale (int val) {
        if (conn != null) {
            try {
                xdg.reset();
                xdg.writeByte(SET_ACCEL_SCALE_REQ);
                xdg.writeByte(val);
                conn.send(xdg);
            } catch (NoAckException nex) {
                updateConnectionStatus(false);
            } catch (IOException ex) {
                // ignore any other problems
            }
        }
    }                                               

    /**
     * Send a request to the remote SPOT to calibrate the accelerometer.
     */
    public void doCalibrate () {
        doGetScale();
        sendCmd(CALIBRATE_ACCEL_REQ);
    }

    /**
     * Send a request to the remote SPOT to start or stop sending accelerometer readings.
     *
     * @param sendIt true to start sending, false to stop
     * @param gView the GraphView display to pass the data to
     */
    public void doSendData (boolean sendIt, GraphView gView) {
        graphView = gView;
        setGOffsets();
        sendCmd(sendIt ? SEND_ACCEL_DATA_REQ : STOP_ACCEL_DATA_REQ);
    }

    /**
     * Send a request to the remote SPOT to report on radio signal strength.
     */
    public void doPing() {
        sendCmd(PING_REQ);
    }

    /**
     * Send a request to the remote SPOT to blink its LEDs.
     */
    public void doBlink() {
        sendCmd(BLINK_LEDS_REQ);
    }

    /**
     * Send a request to the remote SPOT to reconnect.
     */
    public void reconnect() {
        updateConnectionStatus(false);
        sendCmd(DISPLAY_SERVER_RESTART);
    }

    /**
     * Stop running. Also notify the remote SPOT that we are no longer listening to it.
     */
    public void doQuit () {
        if (conn != null) {
            sendCmd(DISPLAY_SERVER_QUITTING);
            try {
                conn.close();
            } catch (IOException ex) {
                System.out.println("Error closing connection to SPOT " + ieee);
            }
            conn = null;
        }
        running = false;
    }

    /**
     * Send a simple command request to the remote SPOT.
     *
     * @param cmd the command requested
     */
    private void sendCmd (byte cmd) {
        if (conn != null) {
            try {
                xdg.reset();
                xdg.writeByte(cmd);
                conn.send(xdg);
            } catch (NoAckException nex) {
                updateConnectionStatus(false);
            } catch (IOException ex) {
                // ignore any other problems
            }
        }
    }

    /**
     * Routine to reset after old data has been cleared from the GUI display.
     */
    public void clear () {
        index = 0;
        timeStampOffset = -1;
    }

    /**
     * Process telemetry data sent by remote SPOT. 
     * Pass the data gathered to the GraphView to be displayed.
     *
     * @param dg the packet containing the accelerometer data
     * @param scale the accelerometer range that was used to collect the data
     */
    private void receive (Datagram dg, int scale) {
        boolean skipZeros = (index == 0);
        int scaleInUse = 0;
        for (int i = 0; i < scales.length; i++) {
            if (scales[i] == scale) {
                scaleInUse = i;
                break;
            }
        }
        try {
            String address = dg.getAddress();
            long timeStamp = dg.readLong();
            if (timeStampOffset <= 0) {
                timeStampOffset = timeStamp;
                timeStamp = 0;
            } else {
                timeStamp -= timeStampOffset;
            }
            int sampleSize = dg.readByte();         // Number of SensorData contained in the datagram
            for (int i = 0; i < sampleSize; i++) {
                int deltaT = dg.readShort();
                long sampleTime = timeStamp + (deltaT & 0x0ffffL);
                int xValue = dg.readShort();
                int yValue = dg.readShort();
                int zValue = dg.readShort();

                if (skipZeros &&        // Ignore leading values until they become > 1/4 G
                    ((Math.abs(xValue - (int)restOffsets[scaleInUse][0]) > gains[scaleInUse][0] / 4) ||
                     (Math.abs(yValue - (int)restOffsets[scaleInUse][1]) > gains[scaleInUse][1] / 4) ||
                     (Math.abs(zValue - (int)restOffsets[scaleInUse][2]) > gains[scaleInUse][2] / 4))) {
                    skipZeros = false;
                    timeStampOffset += sampleTime;
                    timeStamp = -deltaT;
                    sampleTime = 0;
                }

                if (!skipZeros) {
                    double x  = (xValue - zeroOffsets[scaleInUse][0]) / gains[scaleInUse][0];        // Convert to G's
                    double y  = (yValue - zeroOffsets[scaleInUse][1]) / gains[scaleInUse][1];
                    double z  = (zValue - zeroOffsets[scaleInUse][2]) / gains[scaleInUse][2];

                    double g = Math.sqrt(x*x + y*y + z*z);      // Square vector of the total Gs

                    graphView.takeData(address, sampleTime, index, x, y, z, g, scales[scaleInUse]);
                    index++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setGOffsets() {
        if (graphView != null) {
            graphView.setGOffset((zeroOffsets[scaleInUse][0] - restOffsets[scaleInUse][0]) / gains[scaleInUse][0],
                                 (zeroOffsets[scaleInUse][1] - restOffsets[scaleInUse][1]) / gains[scaleInUse][1],
                                 (zeroOffsets[scaleInUse][2] - restOffsets[scaleInUse][2]) / gains[scaleInUse][2]);
        }
    }
        
    /**
     * Main runtime loop to connect to a remote SPOT.
     * Do not call directly. Call start() instead.
     */
    public void run () {
        running = true;
        try {
            conn = (RadiogramConnection) Connector.open("radiogram://" + ieee + ":" + CONNECTED_PORT);
            Radiogram rdg = (Radiogram) conn.newDatagram(conn.getMaximumLength());
            xdg = (Radiogram) conn.newDatagram(10); // we never send more than 1 or 2 bytes
            index = 0;
            timeStampOffset = -1;
            while (running) {
                try {
                    conn.receive(rdg);            // wait until we receive a reply
                } catch (IOException ex) {
                    continue;
                }
                byte packetType = rdg.readByte();
                switch (packetType) {
                    case GET_ACCEL_INFO_REPLY:
                        int n = rdg.readByte();
                        scales = new int[n];
                        System.out.print("Accelerometer scales: ");
                        for (int i = 0; i < n; i++) {
                            scales[i] = rdg.readByte();
                            System.out.print(scales[i] + " ");
                        }
                        System.out.println();
                        scaleInUse = rdg.readByte();
                        System.out.println("Accelerometer scale is set to " + scales[scaleInUse] + "G");
                        guiFrame.setScale(scales[scaleInUse]);
                        updateConnectionStatus(true);
                        System.out.println("Accelerometer zero offsets:");
                        zeroOffsets = new double[scales.length][3];
                        for (int i = 0; i < scales.length; i++) {
                            System.out.print("  " + scales[i] + "G: ");
                            for (int j = 0; j < 3; j++) {
                                zeroOffsets[i][j] = (int) rdg.readDouble();
                                System.out.print(zeroOffsets[i][j] + (j < 2 ? ", " : ""));
                            }
                            System.out.println();
                        }
                        break;
                    case GET_ACCEL_INFO2_REPLY:
                        System.out.println("Accelerometer gains:");
                        gains = new double[scales.length][3];
                        for (int i = 0; i < scales.length; i++) {
                            System.out.print("  " + scales[i] + "G: ");
                            for (int j = 0; j < 3; j++) {
                                gains[i][j] = (int) rdg.readDouble();
                                System.out.print(gains[i][j] + (j < 2 ? ", " : ""));
                            }
                            System.out.println();
                        }
                        break;
                    case SET_ACCEL_SCALE_REPLY:
                        int newScale = rdg.readByte();
                        if (newScale > 0) {
                            for (int i = 0; i < scales.length; i++) {
                                if (scales[i] == newScale) {
                                    scaleInUse = i;
                                    break;
                                }
                            }
                            System.out.println("Accelerometer scale now set to " + scales[scaleInUse] + "G");
                        } else {
                            System.out.println("Invalid Accelerometer scale requested!");
                        }
                        setGOffsets();
                        guiFrame.setScale(scales[scaleInUse]);
                        break;
                    case CALIBRATE_ACCEL_REPLY:
                        System.out.println("Accelerometer rest offsets:");
                        restOffsets = new double[scales.length][3];
                        for (int i = 0; i < scales.length; i++) {
                            System.out.print("  " + scales[i] + "G: ");
                            for (int j = 0; j < 3; j++) {
                                restOffsets[i][j] = (int) rdg.readDouble();
                                System.out.print(restOffsets[i][j] + (j < 2 ? ", " : ""));
                            }
                            System.out.println();
                        }
                        setGOffsets();
                        break;
                    case ACCEL_2G_DATA_REPLY:
                        receive(rdg, 2);
                        break;
                    case ACCEL_4G_DATA_REPLY:
                        receive(rdg, 4);
                        break;
                    case ACCEL_6G_DATA_REPLY:
                        receive(rdg, 6);
                        break;
                    case ACCEL_8G_DATA_REPLY:
                        receive(rdg, 8);
                        break;
                    case PING_REPLY:
                        System.out.println("Ping reply:  (linkQuality : corr : rssi)");
                        System.out.println("   host->spot: " + rdg.readInt() + " : " + rdg.readInt() + " : " + rdg.readInt());
                        System.out.println("   spot->host: " + rdg.getLinkQuality() + " : " + rdg.getCorr() + " : " + rdg.getRssi());
                        System.out.println("   spot battery voltage: " + rdg.readInt() + " mv");
                        break;
                    case MESSAGE_REPLY:
                        String str = rdg.readUTF();
                        System.out.println("Message from sensor: " + str);
                        break;
                }
            }
        } catch (Exception ex) {
            System.out.println("Error communicating with remote Spot: ");
            ex.printStackTrace();
        } finally {
            try {
                updateConnectionStatus(false);
                if (conn != null) {
                    xdg.reset();
                    xdg.writeByte(DISPLAY_SERVER_QUITTING);        // packet type
                    conn.send(xdg);                                // broadcast it
                    conn.close();
                    conn = null;
                }
            } catch (IOException ex) { /* ignore */ }
        }
    }

}
