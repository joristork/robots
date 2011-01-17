/*
 * InfraRedDemo.java
 *
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

import com.sun.spot.peripheral.Spot;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.SwitchEvent;
import com.sun.spot.sensorboard.peripheral.InfraRed;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.Utils;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;


/**
 * This is a simple application to use the InfraRed capabilities on the new rev8
 * Sun SPOT.  Turns your SPOT into a remote volume control for your TV.
 * <p>
 * A command is sent each time one of the switches is clicked (i.e. pressed
 * & released). By default SW1 sends a reduce volume command and SW2 sends
 * an increase volume command. Two commands are sent, one using NEC encoding and
 * the second using SONY encoding. The IR LED is located just below SW1. Be
 * careful not to block it with your finger when pressing the switches.
 * <p>
 * Note: When switch 2 is pressed no IR signal can be sent, so we wait until
 * the switch is released to send the command.
 * <p>
 * The LEDs display a local "volume" setting and if another SPOT or TV remote
 * sends NEC_VOL_PLUS or NEC_VOL_MINUS (or SONY_VOL_PLUS/SONY_VOL_MINUS) commands
 * the volume level will be modified appropriately. APPLE_PLUS & APPLE_MINUS
 * commands from an Apple IR Remote will also be recognized. The IR Detector is
 * located just below the H0 & H1 pins.
 *
 * @author: Ron Goldman
 *    Date: August 27, 2010
 */
public class InfraRedDemo extends MIDlet implements ISwitchListener {

    // some commands from the Apple IR Remote
    public static final long APPLE_PLUS    = 0x087ee110bL;  // Apple custom code ID = 0x87EE
    public static final long APPLE_MINUS   = 0x087ee110dL;  // address = 0x11
    public static final long APPLE_FORWARD = 0x087ee1107L;  // command = 0x0b, 0x0d, 0x07 & 0x08
    public static final long APPLE_BACK    = 0x087ee1108L;

    // some commands from a NEC TV remote:  ~address address ~command command
    public static final long NEC_VOL_PLUS   = 0x0bf40e51aL;  // ~address = 0xbf
    public static final long NEC_VOL_MINUS  = 0x0bf40e11eL;  //  address = 0x40
    public static final long NEC_CHAN_UP    = 0x0bf40e41bL;  // ~command = 0xe5, 0xe1, 0xe4 & 0xe0
    public static final long NEC_CHAN_DOWN  = 0x0bf40e01fL;  //  command = 0x1a, 0x1e, 0x1b & 0x1f

    // some commands from a SONY TV remote   addr<5> cmd<7>
    public static final int  SONY_VOL_PLUS  = 0x092;         // addr = 1  cmd = 0x12
    public static final int  SONY_VOL_MINUS = 0x093;         // addr = 1  cmd = 0x13
    public static final int  SONY_CHAN_UP   = 0x090;         // addr = 1  cmd = 0x10
    public static final int  SONY_CHAN_DOWN = 0x091;         // addr = 1  cmd = 0x11

    private static final boolean USE_TV_CMDS = true;   // set to false to send Apple Remote commands

    private ISwitch sw1, sw2;
    private InfraRed ir;
    private ITriColorLEDArray leds;


    // IR transmit methods

    private long getPlusCmd() {
        return USE_TV_CMDS ? NEC_VOL_PLUS : APPLE_PLUS;
    }

    private long getMinusCmd() {
        return USE_TV_CMDS ? NEC_VOL_MINUS : APPLE_MINUS;
    }

    public void switchPressed(SwitchEvent evt) {
        if (evt.getSwitch() == sw1) {
            leds.setRGB(20, 0, 0);
        } else {
            leds.setRGB(0, 0, 20);
        }
        leds.setOn();
    }

    public synchronized void switchReleased(SwitchEvent evt) {
        leds.setOff();
        long cmd, cmd2;
        if (evt.getSwitch() == sw1) {
            cmd = getMinusCmd();
            cmd2 = SONY_VOL_MINUS;
            System.out.println("Sending a minus command");
        } else {
            cmd = getPlusCmd();
            cmd2 = SONY_VOL_PLUS;
            System.out.println("Sending a plus command");
        }
        ir.writeNEC(cmd);
        Utils.sleep(100);
        writeSONY(cmd2);
        displayVolume();
    }

    // IR receive methods

    private static final boolean DISPLAY_IR_TIMING = false;

    private int volume = 3;
    private int lastCommand = 0;


    private void displayVolume() {
        leds.setRGB(0, 20, 0);
        for (int i = 0; i < 8; i++) {
            leds.getLED(i).setOn(i < volume);
        }
    }

    private void increaseVolume() {
        lastCommand = 1;
        if (volume < 8) {
            volume++;
            System.out.println("Volume increased to " + volume);
        } else {
            System.out.println("Volume already at maximum");
        }
    }

    private void decreaseVolume() {
        lastCommand = 2;
        if (volume > 0) {
            volume--;
            System.out.println("Volume decreased to " + volume);
        } else {
            System.out.println("Volume already at minimum");
        }
    }

    private boolean isAppleRemote(long word) {
        return (word & 0x0ffff0000L) == 0x087ee0000L;
    }

    public void readIRSensor() {
        try {
            byte[] val = ir.readIR();
            if (val == null || val.length == 0) {
                 System.out.println("No command read");
            } else {
                if (DISPLAY_IR_TIMING) {
                    System.out.println("   Command length = " + val.length);
                    for (int i = 0; i < val.length; i++) {
                        System.out.println((i < 10 ? "   " : "  ") + i + ":  " + (val[i] & 0x0ff) * 64);
                    }
                }
                long cmd = ir.decodeNEC(val);
                if (cmd > 0) {
                    System.out.println("NEC Command = " + Long.toString(cmd, 16));
                    lastCommand = 0;
                    int c = (int)(cmd & 0xff);
                    if (isAppleRemote(cmd)) {
                        if (c == (APPLE_PLUS & 0xff)) {
                            increaseVolume();
                        } else if (c == (APPLE_MINUS & 0xff)) {
                            decreaseVolume();
                        }
                    } else {
                        if (c == (NEC_VOL_PLUS & 0xff)) {
                            increaseVolume();
                        } else if (c == (NEC_VOL_MINUS & 0xff)) {
                            decreaseVolume();
                        }
                    }
                } else if (cmd == InfraRed.NEC_REPEAT && lastCommand > 0) {
                    System.out.println("  A repeat command");
                    if (lastCommand == 1) {
                        increaseVolume();
                    } else if (lastCommand == 2) {
                        decreaseVolume();
                    }
                } else if (cmd == InfraRed.NEC_BAD_FORMAT) {
                    cmd = decodeSONY(val);
                    lastCommand = 0;
                    if (cmd > 0) {
                        System.out.println("SONY Command = " + Long.toString(cmd, 16));
                        int c = (int) (cmd & 0x7f);
                        if (c == (SONY_VOL_PLUS & 0x7f)) {
                            increaseVolume();
                        } else if (c == (SONY_VOL_MINUS & 0x7f)) {
                            decreaseVolume();
                        }
                        Utils.sleep(100);   // ignore retransmits
                    }
                } else {
                    lastCommand = 0;
                }
            }
        } catch (InterruptedException iex) {
//            System.out.println("IR read interrupted by IR write");
        } catch (Exception ex) {
            System.out.println("Error reading IR sensor: " + ex);
        }
    }


    // SONY format

    public static final int SONY_INITIAL_HEADER = (2470 + 63) / 64;
    public static final int SONY_OFF            =  (556 + 63) / 64;
    public static final int SONY_ZERO           =  (644 + 63) / 64;
    public static final int SONY_ONE            = (1243 + 63) / 64;
    public static final int SONY_BAD_FORMAT     = -2;


    /**
     * Take an IR message specified by a byte array and decode it using the SONY format.
     * Typically passed the byte array returned by readIR().
     *
     * @param msg a byte array specifying pulse durations in 64 microsecond time periods
     * @return the decoded message value or
     *         SONY_BAD_FORMAT (= -2) if the message was not formatted properly
     */
    public int decodeSONY(byte val[]) {
        int result = SONY_BAD_FORMAT;           // assume bad format
        int len = val.length;
        if (Math.abs(val[0] - SONY_INITIAL_HEADER) <= (SONY_INITIAL_HEADER / 4)) {
            if (len == 25) {
                int word = 0;
                for (int i = 12; i > 0; i--) {
                    word <<= 1;
                    if (Math.abs(val[2 * i - 1] - SONY_OFF) > (SONY_OFF / 4)) {
                        System.out.println("decodeSONY: questionable off for bit " + i + " : " + (val[2 * i - 1] * 64));
                    }
                    int w = val[2 * i];
                    if (Math.abs(w - SONY_ONE) <= (SONY_ONE / 4)) {
                        word += 1;
                    } else if (Math.abs(w - SONY_ZERO) > (SONY_ZERO / 4)) {
                        System.out.println("decodeSONY: questionable on for bit " + i + " : " + (w * 64));
                    }
                }
                result = word & 0x0fff;
            } else {
                System.out.println("decodeSONY: Bad command length: " + len);
            }
        } else {
            System.out.println("decodeSONY: Initial pulse on not as expected: " + (val[0] * 64));
        }
        return result;
    }

    /**
     * Encode a number using the SONY format into a byte array that can be given to writeIR().
     *
     * @param cmd the number to encode
     * @return a byte array specifying the pulse durations of the message
     */
    public byte[] encodeSONY(long cmd) {
        byte val[] = new byte[25];
        int j = 0;
        val[j++] = (byte)SONY_INITIAL_HEADER;
        for (int i = 0; i < 12; i++) {
            val[j++] = (byte) SONY_OFF;
            val[j++] = (byte)(((cmd & 0x1) == 0) ? SONY_ZERO : SONY_ONE);
            cmd >>= 1;
        }
        return val;
    }

    public void writeSONY(long cmd) {
        byte msg[] = encodeSONY(cmd);
        for (int i = 0; i < 3; i++) {   // need to repeat twice w/ 45msec delay between sends
            ir.writeIR(msg);
            Utils.sleep(45);
        }
    }

    // MIDlet initialization

    protected void startApp() throws MIDletStateChangeException {
        // Listen for downloads/commands over USB connection
        new BootloaderListenerService().getInstance().start();
        Spot.getInstance().getSleepManager().disableDeepSleep();

        ir = (InfraRed) Resources.lookup(InfraRed.class);
        if (ir == null) {
            System.out.println("\nSorry this SPOT does not have an InfraRed sensor\n");
            notifyDestroyed();
        } else {
            System.out.println("\nSPOT InfraRed demo");
        }

        leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
        sw2 = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
        sw1.addISwitchListener(this);
        sw2.addISwitchListener(this);

        while (true) {
            displayVolume();
            readIRSensor();
        }
    }
    
    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }
    
    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // Only called if startApp throws any exception other than MIDletStateChangeException
    }
}
