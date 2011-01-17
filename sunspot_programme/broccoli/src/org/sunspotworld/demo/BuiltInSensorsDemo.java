/*
 * Copyright (c) 2006-2010 Sun Microsystems, Inc.
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

package org.sunspotworld.demo;

/*
 * BuiltInSensorsDemo.java
 *
 * Illustrates use of the on-board sensors.
 * Intended as a starting template for building your own app.
 * For radio, see the RadioStrength demo.
 *
 *    This app waits for you to hold down SW1. While down, LED1 will
 *    Show green if shaken left to right.
 *    Once the button is released, it will show the temperature
 *    on LED1 (either red or blue), and the light level on LED2 in green.
 *
 * author: Randy Smith
 * date: August 2, 2006
 */

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.service.BootloaderListenerService;
import com.sun.spot.util.Utils;

import java.io.IOException;

// MIDlets
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.sun.spot.util.Utils;

// Transport
import com.sun.spot.io.j2me.radiogram.RadiogramConnection;
import com.sun.spot.peripheral.radio.RadioFactory;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import org.sunspotworld.demo.utilities.RadioDataIOStream;

// Switches
import com.sun.spot.resources.transducers.SwitchEvent;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;

public class BuiltInSensorsDemo extends MIDlet implements ISwitchListener {
    private ITriColorLEDArray leds
        = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);

    ISwitch sw1, sw2; //Switches on the demo sensor board.

    protected int counter = 0;

    // The lowest channel for passing the ball between spots. See
    // generate3AvailablePortNumbers().
    private static int MIN_CHANNEL = 43;

    // True after you press a Left or Right button, but before it has
    // established some connection.
    boolean seekingConnectLeft, seekingConnectRight;

    boolean listeningForAPartner;

    // Used for broadcasting
    DatagramConnection dgBConn;

    // Used to pass a ball to another SPOT
    RadioDataIOStream leftIOStream, rightIOStream;

    long thisAddressAsNumber;

    private RadiogramConnection connListenPartner;

    public void start() {
        initialize();
    }

    public void initialize() {
        seekingConnectLeft = seekingConnectRight = false;
        listeningForAPartner = false;
        thisAddressAsNumber = RadioFactory.getRadioPolicyManager().getIEEEAddress();

        // Picks up a chatter on the broadcast channel.
        //startListenForAPartnerThread();
    }

    /**
     * Main application loop.
     */
    private void run() throws IOException {
        for(int i = 0; i < leds.size(); i++){
            // Enable the LED and set it to black
            leds.getLED(i).setOn();
            leds.getLED(i).setRGB(0,0,0);
        }
        leds.getLED(0).setColor(LEDColor.TURQUOISE);

        // Initialise swtiches
        sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
        sw2 = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
        sw1.addISwitchListener(this);
        sw2.addISwitchListener(this);

        new Thread(){
            public void run(){
                while(true){
                    updateLEDS(counter);
                    Utils.sleep(50);
                }
            }
        }.start();
    }

    // Update the various LEDs based on a value.
    public void updateLEDS(int counter) {
        for(int i = 0; i < counter; i++) {
            leds.getLED(i).setColor(LEDColor.TURQUOISE);
        }
        for(int i = counter; i < 8; i++) {
            leds.getLED(i).setRGB(0, 0, 0);
        }
    }

    /**
     * Callback for when the switch state changes from pressed to released.
     */
    public void switchReleased(SwitchEvent se) {
    }

    /**
     * Callback for when the switch state changes from released to pressed.
     */
    public void switchPressed(SwitchEvent se) {
        if (se.getSwitch() == sw1) {
            sw1Closed();
        } else {
            sw2Closed();
        }
    }

    /**
     * The switches toggle between states.
     * If the associated left/right end is not open, start invitations to
     * establishing a connection.
     * If in the process of starting a connection for the NON-associated end
     * (right/left) but none is yet established, just exit immediately.
     * If already in the process of inviting a connection but none is yet
     * established, quit that process.
     *
     **/
    synchronized public void sw1Closed(){
        counter = Math.max(counter - 1, 0);
    }
    synchronized public void sw2Closed(){
        counter = Math.min(counter + 1, 8);
    }

    /**
     * The rest is boiler plate code, for Java ME compliance startApp() is the
     * MIDlet call that starts the application.
     */
    protected void startApp() throws MIDletStateChangeException {
        // Listen for downloads/commands over USB connection
        BootloaderListenerService.getInstance().start();
        try {
            run();
        } catch (IOException ex) {
            // A problem in reading the sensors.
            ex.printStackTrace();
        }
    }

    /**
     * This will never be called by the Squawk VM.
     */
    protected void pauseApp() {
    }

    /**
     * Called if the MIDlet is terminated by the system. I.e. if startApp
     * throws any exception other than MIDletStateChangeException, if the
     * isolate running the MIDlet is killed with Isolate.exit(), or if
     * VM.stopVM() is called.
     *
     * It is not called if MIDlet.notifyDestroyed() was called.
     */
    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        for (int i = 0; i < leds.size(); i++){
            // turn off all LEDs
            leds.getLED(i).setOff(); } }

}
