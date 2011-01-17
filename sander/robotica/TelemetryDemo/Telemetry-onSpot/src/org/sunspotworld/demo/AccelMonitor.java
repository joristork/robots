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
 */

package org.sunspotworld.demo;

import org.sunspotworld.demo.util.PacketHandler;
import org.sunspotworld.demo.util.PacketReceiver;
import org.sunspotworld.demo.util.PacketTransmitter;
import org.sunspotworld.demo.util.PeriodicTask;

import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.io.j2me.radiogram.Radiogram;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.IMeasurementInfo;
import com.sun.spot.resources.transducers.IMeasurementRange;
import com.sun.spot.resources.transducers.MeasurementRangeEvent;
import com.sun.spot.sensorboard.peripheral.LIS3L02AQAccelerometer;
import com.sun.spot.sensorboard.peripheral.MMA7455LAccelerometer;
import com.sun.spot.sensorboard.hardware.ADT7411;
import com.sun.spot.util.Utils;

import java.io.IOException;

/**
 * Routines to control and read data from the SPOT's accelerometer and send them
 * via Radiogram packets to a display program running on a host computer.
 *<p>
 * The actual task scheduling is done by the PeriodicTask parent class.
 *<p>
 * Packets from the host application are received by the PacketReceiver class and
 * then dispatched to this class to respond to. The replies are sent back via the
 * PacketTransmitter class. The commands are defined in the PacketTypes class.
 * <p>
 * @author Ron Goldman<br>
 * Date: May 8, 2006<br>
 * Revised: August 1, 2007<br>
 * Revised: August 1, 2010
 *
 * @see PeriodicTask
 * @see PacketHandler
 * @see PacketReceiver
 * @see PacketTransmitter
 * @see PacketTypes
 */
public class AccelMonitor extends PeriodicTask implements PacketHandler, PacketTypes {
    
    // Definitions for raw accelerometer packets
    private static final int ACC_HEADER_SIZE = 8 + 1;   // time (long) + num samples (byte)
    private static final int ACC_SAMPLE_SIZE = 4 * 2;   // delta t + acc_x + acc_y + acc_z (all shorts)
    private static final int ACC_SAMPLES_PER_PACKET = 
            (PacketTransmitter.SINGLE_PACKET_PAYLOAD_SIZE - ACC_HEADER_SIZE) / ACC_SAMPLE_SIZE;

    private static double zeroOffsets8[][] = { { 128, 128, 128 },
                                               { 128, 128, 128 },
                                               { 128, 128, 128 } };

    private static double gains8[][] = { { 64, 64, 64 },
                                         { 32, 32, 32 },
                                         { 64, 64, 64 } };

    private static double restOffsets[][];

    private IAccelerometer3D acc;
    private LIS3L02AQAccelerometer acc6;
    private MMA7455LAccelerometer acc8;
    private ITriColorLED led;
    private byte[] packetHdr = { ACCEL_2G_DATA_REPLY, ACCEL_6G_DATA_REPLY };
    private byte[] packetHdr8 = { ACCEL_2G_DATA_REPLY, ACCEL_4G_DATA_REPLY, ACCEL_8G_DATA_REPLY };

    private TelemetryMain main;
    private PacketTransmitter xmit;
    private Radiogram currentPkt = null;
    private int currentSample = 0;
    private long startTime;
    private boolean rev8 = false;
    private int[] scales;
    private int currentScale = 0;

    /**
     * Create a new accelerometer controller.
     *
     * @param m reference to the main program getting commands from the host display
     * @param sampleInterval how often to sample the accelerometer, in milliseconds
     */
    public AccelMonitor(TelemetryMain m, int sampleInterval, ITriColorLED led) {
        super(3, sampleInterval, Thread.MAX_PRIORITY);
        main = m;
        this.led = led;
        acc = (IAccelerometer3D)Resources.lookup(IAccelerometer3D.class);
        if (acc instanceof MMA7455LAccelerometer) {
            rev8 = true;
            acc8 = (MMA7455LAccelerometer)acc;
            restOffsets = new double[][] { { 128.0, 128.0, 192.0 },
                                           { 128.0, 128.0, 160.0 },
                                           { 128.0, 128.0, 144.0 } };
        } else {
            rev8 = false;
            acc6 = (LIS3L02AQAccelerometer)acc;
            restOffsets = acc6.getZeroOffsets();
            double[][] gains = acc6.getGains();
            restOffsets[0][2] += gains[0][2];       // add in the nominal 1G Z value
            restOffsets[1][2] += gains[1][2];
            ADT7411 adt = (ADT7411) Resources.lookup(ADT7411.class);
            if (adt != null) adt.setFastConversion(true);	// need to sample faster than 58Hz
        }
        if (acc instanceof IMeasurementRange) {
            ((IMeasurementRange) acc).setCurrentRange(0);   // start using lowest scale
            int n = ((IMeasurementRange) acc).getNumberRanges();
            scales = new int[n];
            for (int i = 0; i < n; i++) {
                scales[i] = (int)((IMeasurementRange) acc).getMaxValue(i);
            }
            currentScale = ((IMeasurementRange) acc).getCurrentRange();
        } else if (acc instanceof IMeasurementInfo) {
            scales = new int[1];
            scales[0] = (int)((IMeasurementInfo)acc).getMaxValue();
            currentScale = 0;
        }
    }
    
    
    /**
     * Get the PacketTransmitter & PacketReceiver to use to talk with the host.
     * Register the commands this class handles.
     *
     * @param xmit the PacketTransmitter to send packets
     * @param rcvr the PacketReceiver that will receive commands from the host and dispatch them to handlePacket()
     */
    public void setPacketConnection (PacketTransmitter xmit, PacketReceiver rcvr) {
        this.xmit = xmit;
        
        rcvr.registerHandler(this, GET_ACCEL_INFO_REQ);
        rcvr.registerHandler(this, SET_ACCEL_SCALE_REQ);
        rcvr.registerHandler(this, CALIBRATE_ACCEL_REQ);
        rcvr.registerHandler(this, SEND_ACCEL_DATA_REQ);
        rcvr.registerHandler(this, STOP_ACCEL_DATA_REQ);
    }

    /**
     * Callback from PacketReceiver when a new command is received from the host.
     * Note only the commands associated with the accelerometer are handled here.
     *
     * @param type the command
     * @param pkt the radiogram with any other required information
     */
    public void handlePacket(byte type, Radiogram pkt) {
        try {
            switch (type) {
                case GET_ACCEL_INFO_REQ:
                    getAccInfo();
                    break;
                case SET_ACCEL_SCALE_REQ:
                    setScale(pkt.readByte());
                    led.setRGB(currentScale > 1 ? 30 : 0, 30, currentScale > 0 ? 0 : 60);
                    led.setOn();        // green = 2G, blue-green = 6G
                    Utils.sleep(200);
                    led.setOff();
                    break;
                case CALIBRATE_ACCEL_REQ:
                    if (!isRunning()) {
                        led.setRGB(0,0,50);     // Blue = calibrating
                        led.setOn();
                        calibrate();
                        led.setOff();
                    }
                    break;
                case SEND_ACCEL_DATA_REQ:
                    start();
                    led.setRGB(currentScale > 1 ? 30 : 0, 30, currentScale > 0 ? 0 : 60);
                    led.setOn();        // green = 2G, blue-green = 6G
                    break;
                case STOP_ACCEL_DATA_REQ:
                    stop();
                    led.setOff();
                    break;
            }
        } catch (IOException ex) {
            main.closeConnection();
        }
    }

    /**
     * Send a packet to inform host of current accelerometer settings.
     * Send possible scale values & current scale. Send zero offsets, gains & rest offsets.
     */
    public void getAccInfo() {
        try {
            double offsets[][];
            int n;
            Radiogram dg = xmit.newDataPacket(GET_ACCEL_INFO_REPLY);
            dg.writeByte(scales.length);
            for (int i = 0; i < scales.length; i++) {
                dg.writeByte(scales[i]);
            }
            dg.writeByte(currentScale);

            if (rev8) {
                offsets = zeroOffsets8;
                n = 3;
            } else {
                offsets = acc6.getZeroOffsets();
                n = 2;
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 3; j++) {
                    dg.writeDouble(offsets[i][j]);
                }
            }
            xmit.send(dg);

            dg = xmit.newDataPacket(GET_ACCEL_INFO2_REPLY);
            if (rev8) {
                offsets = gains8;
            } else {
                offsets = acc6.getGains();
            }
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 3; j++) {
                    dg.writeDouble(offsets[i][j]);
                }
            }
            xmit.send(dg);

            dg = xmit.newDataPacket(CALIBRATE_ACCEL_REPLY);
            offsets = restOffsets;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < 3; j++) {
                    dg.writeDouble(offsets[i][j]);
                }
            }
            xmit.send(dg);
        } catch (IOException ex) {
            // ignore errors - display server can repeat request if need be
        }
    }
    
    /**
     * Set the accelerometer scale to use.
     * Will send a packet to acknowledge the request. 
     * The reply will include the new scale or
     * be 0 if an invalid scale was requested.
     *
     * @param b the scale to use; currently = 2, 4, 6 or 8
     */
    public void setScale(byte b) {
        try {
            boolean ok = false;
            Radiogram dg = xmit.newDataPacket(SET_ACCEL_SCALE_REPLY);
            for (int i = 0; i < scales.length; i++) {
                if (b == scales[i]) {
                    currentScale = i;
                    ((IMeasurementRange)acc).setCurrentRange(i);
                    ok = true;
                }
            }
            if (ok) {
                dg.writeByte(b);
            } else {
                dg.writeByte(0);
            }
            xmit.send(dg);
        } catch (IOException ex) {
            // ignore errors - display server can repeat request if need be
        }
    }

    /**
     * Have the accelerometer calculate the current rest offsets.
     * Send a packet back to the host with the 6 offset values.
     */
    public void calibrate () {
        try {
            try {
                for (int sc = 0; sc < scales.length; sc++) {
                    if (scales.length > 1) {
                        ((IMeasurementRange) acc).setCurrentRange(sc);
                    }
                    Utils.sleep(100);         // give it time to settle
                    long aveX = 0;
                    long aveY = 0;
                    long aveZ = 0;
                    for (int i = 0; i < 25; i++) {
                        int[] raw = (rev8 ? acc8.getRawAccelValues() : acc6.getRawAccelValues());
                        aveX += raw[0];
                        aveY += raw[1];
                        aveZ += raw[2];
                        Utils.sleep(20);
                    }
                    restOffsets[sc][0] = aveX / 25.0;
                    restOffsets[sc][1] = aveY / 25.0;
                    restOffsets[sc][2] = aveZ / 25.0;
                    if (rev8) {
                        for (int i = 0; i < 3; i++) {
                            restOffsets[sc][i] += 128.0;
                        }
                    }
                }
            } finally {                      // restore original scale if needed
                if (scales.length > 1) {
                    ((IMeasurementRange) acc).setCurrentRange(currentScale);
                }
            }

            Radiogram dg = xmit.newDataPacket(CALIBRATE_ACCEL_REPLY);
            for (int i = 0; i < scales.length; i++) {
                for (int j = 0; j < 3; j++) {
                    dg.writeDouble(restOffsets[i][j]);
                }
            }
            xmit.send(dg);
        } catch (IOException ex) {
            // ignore errors - display server can repeat request if need be
        }
    }


    ///////////////////////////////////
    //
    // PeriodicTask overridden methods
    //
    ///////////////////////////////////
    
    /**
     * Routine called when task execution is about to start up.
     */
    public void starting() {
        currentPkt = null;
        System.out.println("Starting accelerometer sampler");
    }
    
    /**
     * Routine called when task execution is finished.
     */
    public void stopping() {
        try {
            if (currentPkt != null) {
                double offsets[][] = rev8 ? zeroOffsets8 : acc6.getZeroOffsets();
                int deltaT = (int) (System.currentTimeMillis() - startTime);
                for (int i = currentSample; i < ACC_SAMPLES_PER_PACKET; i++) {
                    currentPkt.writeShort(deltaT + i);
                    for (int j = 0; j < 3; j++) {      // fill out final packet
                        currentPkt.writeShort((int)offsets[currentScale][j]);
                    }
                }
                xmit.send(currentPkt);
                currentPkt = null;
            }
        } catch (IOException ie) {
            // ignore
        }
        System.out.println("Stopping accelerometer sampler");
    }


    /**
     * Called once per task period to pack up accelerometer readings.
     * When the packet is full it is queued for transmission.
     */
    public void doTask() {
        try {
            if (currentPkt == null) {
                startTime = System.currentTimeMillis();
                currentPkt = xmit.newDataPacket((rev8 ? packetHdr8 : packetHdr)[currentScale]);
                currentPkt.writeLong(startTime);
                currentPkt.writeByte(ACC_SAMPLES_PER_PACKET);
                currentSample = 0;
            }
            currentPkt.writeShort((int) (System.currentTimeMillis() - startTime));
            int raw[];
            if (rev8) {
                raw = acc8.getRawAccelValues();
                currentPkt.writeShort(raw[0] + 128);
                currentPkt.writeShort(raw[1] + 128);
                currentPkt.writeShort(raw[2] + 128);
            } else {
                raw = acc6.getRawAccelValues();
                currentPkt.writeShort(raw[0]);
                currentPkt.writeShort(raw[1]);
                currentPkt.writeShort(raw[2]);
            }
            if (++currentSample >= ACC_SAMPLES_PER_PACKET) {
                xmit.send(currentPkt);
                currentPkt = null;
            }
        } catch (IOException ie) {
            main.queueMessage("IO exception: " + ie.toString());
        }
    }

    /** temporary fix until IService interface fixed */
    public void setName(String who){};
}
