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

/**
 * Packet types for TelemetryDemo
 *
 * @author Ron Goldman
 * Date: January 15, 2007
 * Revised: August 1, 2007
 * Revised: August 1, 2010
 */
public interface PacketTypes {

    /** Port to use to locate the host application. */
    public static final String BROADCAST_PORT = "42";
    /** Port to use for sending commands and replies between the SPOT and the host application. */
    public static final String CONNECTED_PORT = "43";

    // Command & reply codes for data packets

    /** Client command to locate a display server. */
    public static final byte LOCATE_DISPLAY_SERVER_REQ  = 1;    // sent to display host (broadcast)
    /** Host command to indicate it is restarting. */
    public static final byte DISPLAY_SERVER_RESTART     = 2;    // sent to any clients (broadcast)

    /** Host command to indicate it is quitting. */
    public static final byte DISPLAY_SERVER_QUITTING    = 3;    // (direct p2p)
    /** Host command to request the current accelerometer scale & calibration. */
    public static final byte GET_ACCEL_INFO_REQ         = 4;
    /** Host command to specify the accelerometer scale to be used. */
    public static final byte SET_ACCEL_SCALE_REQ        = 5;
    /** Host command to request the accelerometer be calibrated. */
    public static final byte CALIBRATE_ACCEL_REQ        = 6;
    /** Host command to request accelerometer data be sent. */
    public static final byte SEND_ACCEL_DATA_REQ        = 7;
    /** Host command to request accelerometer data stop being sent. */
    public static final byte STOP_ACCEL_DATA_REQ        = 8;
    /** Host command to ping the remote SPOT and get the radio signal strength. */
    public static final byte PING_REQ                   = 9;
    /** Host command to blink the remote SPOT's LEDs. */
    public static final byte BLINK_LEDS_REQ             = 10;

    /** Host reply to indicate it is available. */
    public static final byte DISPLAY_SERVER_AVAIL_REPLY = 101;
    /** Client reply to indicate the current accelerometer scale & zero offsets. */
    public static final byte GET_ACCEL_INFO_REPLY       = 104;
    /** Client reply to return the accelerometer gains. */
    public static final byte GET_ACCEL_INFO2_REPLY      = 105;
    /** Client reply to indicate the current accelerometer scale being used. */
    public static final byte SET_ACCEL_SCALE_REPLY      = 106;
    /** Client reply to indicate the current accelerometer rest offsets. */
    public static final byte CALIBRATE_ACCEL_REPLY      = 107;
    /** Client reply with current accelerometer readings taken using the 2G scale. */
    public static final byte ACCEL_2G_DATA_REPLY        = 108;
    /** Client reply with current accelerometer readings taken using the 6G scale. */
    public static final byte ACCEL_6G_DATA_REPLY        = 109;
    /** Client reply to a ping includes the radio signal strength & battery level. */
    public static final byte PING_REPLY                 = 110;
    /** Client reply with any error message for the host to display. */
    public static final byte MESSAGE_REPLY              = 111;
    /** Client reply with current accelerometer readings taken using the 4G scale. */
    public static final byte ACCEL_4G_DATA_REPLY        = 112;
    /** Client reply with current accelerometer readings taken using the 8G scale. */
    public static final byte ACCEL_8G_DATA_REPLY        = 113;

}