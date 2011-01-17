/*
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

package org.sunspotworld.demo;

import com.sun.spot.peripheral.Spot;
import com.sun.spot.peripheral.SpotFatalException;
import com.sun.spot.peripheral.radio.I802_15_4_PHY;
import com.sun.spot.peripheral.radio.IProprietaryRadio;
import com.sun.spot.peripheral.radio.LowPanPacket;
import com.sun.spot.peripheral.radio.RSSI;
import com.sun.spot.peripheral.radio.RadioPacket;
import com.sun.spot.peripheral.radio.RadiogramProtocolManager;
import com.sun.spot.peripheral.radio.RadiostreamProtocolManager;
import com.sun.spot.peripheral.radio.mhrp.lqrp.Constants;
import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.resources.transducers.ITriColorLED;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.SwitchEvent;
import com.sun.spot.util.Utils;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.service.BootloaderListenerService;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * Reports radio traffic in an audible manner like a Geiger counter.
 * <ul>
 * <li> First just click speaker for each packet.
 * <li> Second use different clicks for data packets & acks
 * <li> Third use different clicks for each transmitting/originating SPOT
 * <li>  or distiguish by protocol: routing traffic, radiograms, radiostreams, etc...
 * </ul>
 * Display in LEDs:
 * <ul>
 * <li>  number of packets received in last N seconds
 * <li>  number of SPOTs transmitting/originating packets
 * <li>  RSSI over last N seconds
 * </ul>
 * Use buttons to change display modes.
 *
 * <p>
 * This application requires that the SPOT running it does not start up any
 * services that use the radio, e.g. OTA mode must be disabled.  It also
 * must be run on a SPOT, not as a host app, since it needs to access the
 * physical layer of the radio stack.
 *
 * Note: while in promiscuous listening mode the radio chip will not send
 * out any ACKs, so any Spot sending us a packet will never be notified it
 * was delivered---the sender will get a No ACK exception.
 *
 * @author Ron Goldman
 */
public class GeigerDemo extends MIDlet implements ISwitchListener {

    private static IProprietaryRadio radio = Spot.getInstance().getIProprietaryRadio();
    private static I802_15_4_PHY i802phyRadio = Spot.getInstance().getI802_15_4_PHY();

    private EDemoBoard demo;

    private ISwitch sw1;
    private ISwitch sw2;
    private ITriColorLEDArray leds;
    private ITriColorLED statusLED;
    private IToneGenerator spkr;

    private LEDColor red   = new LEDColor(50, 0, 0);
    private LEDColor green = new LEDColor(0, 20, 0);
    private LEDColor blue  = new LEDColor(0,  0, 50);
    private LEDColor white = new LEDColor(50, 50, 50);

    private static final int GEIGER_SOUND_MODE      = 0;
//    private static final int SENDER_SOUND_MODE      = 1;
//    private static final int ORIGINATOR_SOUND_MODE  = 2;
    private static final int PROTOCOL_SOUND_MODE    = 1;

    private static final int PACKETS_LED_MODE       = 0;
    private static final int SENDERS_LED_MODE       = 1;
    private static final int ORIGINATORS_LED_MODE   = 2;
    private static final int RSSI_LED_MODE          = 3;

    private int soundMode = 0;
    private int ledMode = 0;
    private boolean sw1Pressed = false;
    private boolean sw2Pressed = false;

    private int packetsReceived = 0;
    private int packetsNum = 0;
    private int sendersReceived = 0;
    private int originatorsReceived = 0;
    private int rssi = 0;

    private static final int MAX_SENDERS = 100;
    private int addr[] = new int[MAX_SENDERS];
    private int addrCnt[] = new int[MAX_SENDERS];
    private int orig[] = new int[MAX_SENDERS];
    private int origCnt[] = new int[MAX_SENDERS];

    /*
     * Note: cannot use MAC layer since API does not support promiscuous mode.
     */
    private void setupRadio() {
        radio.reset();
        int channel = Utils.getSystemProperty("radio.channel", Utils.getManifestProperty("DefaultChannelNumber", 26));
        System.out.println("  Radio set to channel " + channel);
        i802phyRadio.plmeSet(I802_15_4_PHY.PHY_CURRENT_CHANNEL, channel);
        int result = i802phyRadio.plmeSetTrxState(I802_15_4_PHY.RX_ON);
        if (I802_15_4_PHY.SUCCESS != result) {
            throw new SpotFatalException("State transition from trx_off to rx_on failed");
        }
    }

    public void readPackets() {
        RadioPacket rp = RadioPacket.getDataPacket();
        System.out.println("Listening for packets....");
        int overflowCnt = radio.getRxOverflow();
        while (true) {
            try {
                i802phyRadio.pdDataIndication(rp);  // receive new packet
                rp.decodeFrameControl();
                if (rp.isAck()) {
                    gotAck(rp);
                } else {
                    gotDataPacket(rp);
                }
                int o = radio.getRxOverflow();
                if (o > overflowCnt) {
                    overflowCnt = o;
                    System.out.println("--- radio buffer overflow: " + overflowCnt);
                }
            } catch (Exception ex) {
                System.out.println("Error in receive loop: " + ex);
                if (rp != null) {
                    if (rp.isAck()) {
                        System.out.println("  while processing an ACK packet");
                    } else {
                        System.out.println("  while processing packet from " + Long.toString(rp.getSourceAddress(), 16) +
                                " to " + Long.toString(rp.getDestinationAddress(), 16));
                    }
                }
            }
        }
    }

    private void gotAck(RadioPacket rp) {
        switch (soundMode) {
            case GEIGER_SOUND_MODE:
                if (!sw1Pressed) {
                    statusLED.setColor(red);
                    statusLED.setOn();
                }
                if (spkr != null) {
                    spkr.startTone(3846);
                }
                Utils.sleep(1);
                if (spkr != null) {
                    spkr.stopTone();
                }
                if (!sw1Pressed) {
                    statusLED.setOff();
                }
                break;
            case PROTOCOL_SOUND_MODE:
//            case SENDER_SOUND_MODE:
//            case ORIGINATOR_SOUND_MODE:
            default:
                // no sound
                break;
        }
    }

    private void gotDataPacket(RadioPacket rp) {
        packetsNum++;
        LowPanPacket lpp = new LowPanPacket(rp);
        int pAddr = (int) (rp.getSourceAddress() & 0x0FFFFFFFF);
        for (int i = 0; i < MAX_SENDERS; i++) {
            if (addr[i] == pAddr || addr[i] == 0) {
                addr[i] = pAddr;
                addrCnt[i] = period;
                break;
            }
        }
        int pOrig = lpp.isMeshed() ? (int) (lpp.getOriginatorAddress() & 0x0FFFFFFFF) : pAddr;
        for (int i = 0; i < MAX_SENDERS; i++) {
            if (orig[i] == pOrig || orig[i] == 0) {
                orig[i] = pOrig;
                origCnt[i] = period;
                break;
            }
        }

        int pause = 1;
        int tone = 1282;
        LEDColor color = white;
        switch (soundMode) {
            case GEIGER_SOUND_MODE:
                pause = 1;
                tone = 2564;
                color = green;
                break;
            case PROTOCOL_SOUND_MODE:
                if (!lpp.isFragged() || lpp.isFirstFrag()) {
                    int proto = lpp.getProtocol();
                    if (proto == RadiogramProtocolManager.PROTOCOL_NUMBER) {
                        tone = 3846;
                        color = red;
                    } else if (proto == RadiostreamProtocolManager.PROTOCOL_NUMBER) {
                        tone = 2564;
                        color = blue;
                    } else if (proto == Constants.LQRP_PROTOCOL_NUMBER) {
                        tone = 1923;
                        color = green;
//                        int AODVMessageType = (int)(0xFF & rp.getMACPayloadAt(lpp.getHeaderLength()));
//                        switch (AODVMessageType) {
//                            case Constants.RREQ_TYPE:
//                                tone = 1923;
//                                break;
//                            case Constants.RREP_TYPE:
//                                tone = 2198;
//                                break;
//                            case Constants.RERR_TYPE:
//                                tone = 2309;
//                                break;
//                            default:
//                                tone = 1538;
//                                break;
//                        }
                    }
                } else {
                    tone = 1538; // 1282;
                }
                pause = 2;
                break;
//            case SENDER_SOUND_MODE:
//            case ORIGINATOR_SOUND_MODE:
            default:
                // no sound
                break;
        }
        if (!sw1Pressed) {
            statusLED.setColor(color);
            statusLED.setOn();
        }
        if (spkr != null) {
            spkr.startTone(tone);
        }
        Utils.sleep(pause);
        if (spkr != null) {
            spkr.stopTone();
        }
        if (!sw1Pressed) {
            statusLED.setOff();
        }
    }

    private void chirp() {
        double tone[] = { 3846, 3077, 2882, 2564, 2309, 2198, 1923, 1709, 1647, 1538, 1443, 1399, 1282, 962 };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < tone.length; j++) {
                spkr.startTone(tone[j]);
                Utils.sleep(3);
                spkr.stopTone();
                Utils.sleep(100);
            }
            Utils.sleep(300);
        }
    }

    protected void startApp() throws MIDletStateChangeException {
        System.out.println("Geiger Counter Test Application");
        BootloaderListenerService.getInstance().start();       // Listen for downloads/commands over USB connection

        EDemoBoard edemo = EDemoBoard.getInstance();    // make sure eDemo initialized
        sw1 = (ISwitch) Resources.lookup(ISwitch.class, "SW1");
        sw2 = (ISwitch) Resources.lookup(ISwitch.class, "SW2");
        leds = (ITriColorLEDArray) Resources.lookup(ITriColorLEDArray.class);
        statusLED = leds.getLED(0);
        spkr = (IToneGenerator) Resources.lookup(IToneGenerator.class, "speaker");
        sw1.addISwitchListener(this);
        sw2.addISwitchListener(this);

        demo = EDemoBoard.getInstance();
        if (demo == null) {
            throw new SpotFatalException("Geiger requires an eDemoboard");
        } else if (demo.getHardwareRevision() < 8) {
            System.out.println("Geiger cannot click without a rev8 eDemoboard - just using LEDs");
        } else {
            chirp();
        }

        setSoundModeLED();
        new Thread("display"){
            public void run() {
                updateStats();
            }
        }.start();

        Thread.currentThread().setPriority(Thread.MAX_PRIORITY - 1);
        setupRadio();
        readPackets();
    }
    
    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }
    
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }

    private int period = 0;

    private void updateStats() {
        int pRcvd[] = new int[10];
        period = 0;
        packetsReceived = 0;
        for (int i = 0; i < MAX_SENDERS; i++) {
            addrCnt[i] = -1;
            origCnt[i] = -1;
        }
        updateLEDs();
        while (true) {
            Utils.sleep(1000);     // update LEDs every second
            int tmp = packetsNum;
            packetsNum = 0;
            packetsReceived += tmp - pRcvd[period]; // count for last 10 seconds
            pRcvd[period] = tmp;
            sendersReceived = 0;
            originatorsReceived = 0;
            int old = (period + 1) % 10;
            for (int i = 0; i < MAX_SENDERS; i++) {
                if (addr[i] > 0) {
                    if (addrCnt[i] == old) {
                        addrCnt[i] = -1;
                    } else if (addrCnt[i] >= 0) {
                        sendersReceived++;
                    }
                } else break;
            }
            for (int i = 0; i < MAX_SENDERS; i++) {
                if (orig[i] > 0) {
                    if (origCnt[i] == old) {
                        origCnt[i] = -1;
                    } else if (origCnt[i] >= 0) {
                        originatorsReceived++;
                    }
                } else break;
            }

            rssi = RSSI.getRSSI(i802phyRadio) + 50;
            if (rssi < 0) rssi = 0;
            updateLEDs();
            period = (period + 1) % 10;
        }
    }

    public void switchPressed(SwitchEvent evt) {
        if (evt.getSwitch() == sw1) {
            sw1Pressed = true;
            soundMode++;
            if (soundMode > PROTOCOL_SOUND_MODE) {
                soundMode = GEIGER_SOUND_MODE;
            }
            setSoundModeLED();
        } else {
            sw2Pressed = true;
            ledMode++;
            if (ledMode > RSSI_LED_MODE) {
                ledMode = PACKETS_LED_MODE;
            }
            switch (ledMode) {
            case PACKETS_LED_MODE:
                displayNumber(0x3f, white);
                break;
            case SENDERS_LED_MODE:
                displayNumber(0x3f, green);
                break;
            case ORIGINATORS_LED_MODE:
                displayNumber(0x3f, blue);
                break;
            case RSSI_LED_MODE:
                displayNumber(0x3f, red);
            }
        }
    }

    public void switchReleased(SwitchEvent evt) {
        if (evt.getSwitch() == sw1) {
            statusLED.setOff();
            sw1Pressed = false;
        } else {
            sw2Pressed = false;
            updateLEDs();
        }
    }

    private void setSoundModeLED() {
        switch (soundMode) {
            default:
                soundMode = GEIGER_SOUND_MODE;
            case GEIGER_SOUND_MODE:
                statusLED.setColor(green);
                statusLED.setOn();
                break;
            case PROTOCOL_SOUND_MODE:
                statusLED.setColor(red);
                statusLED.setOn();
                break;
//            case SENDER_SOUND_MODE:
//                statusLED.setColor(green);
//                statusLED.setOn();
//                break;
//            case ORIGINATOR_SOUND_MODE:
//                statusLED.setColor(blue);
//                statusLED.setOn();
//                break;
        }
    }

    private void updateLEDs() {
        if (!sw2Pressed) {
            switch (ledMode) {
                default:
                    ledMode = PACKETS_LED_MODE;
                case PACKETS_LED_MODE:
                    displayNumber(packetsReceived, white);
                    break;
                case SENDERS_LED_MODE:
                    displayNumber(sendersReceived, green);
                    break;
                case ORIGINATORS_LED_MODE:
                    displayNumber(originatorsReceived, blue);
                    break;
                case RSSI_LED_MODE:
                    displayNumber(rssi, red);
                    break;
            }
        }
    }

    /**
     * Display a number (base 2) in LEDs 1-7
     *
     * @param val the number to display
     * @param col the color to display in LEDs
     */
    private void displayNumber(int val, LEDColor col) {
        for (int i = 0, mask = 1; i < 7; i++, mask <<= 1) {
            leds.getLED(7-i).setColor(col);
            leds.getLED(7-i).setOn((val & mask) != 0);
        }
    }

    /**
     * Display a vU like level in LEDs 1-7
     *
     * @param val the level to display
     * @param max the maximum value expected
     * @param min the minimum value expected
     * @param col the color to display in LEDs
     */
    private void displayLevel(int val, int max, int min, LEDColor col) {
        int LEDS_TO_USE = 7;
        int MAX_LED = 7;
        int range = max - min + 1;
        int perLed = range / LEDS_TO_USE;
        int bucket = (val - min + 1) / perLed;
        int part = (val - min + 1) - bucket * perLed;
        for (int i = 0; i < LEDS_TO_USE; i++) {
            if (bucket >= i) {
                leds.getLED(MAX_LED-i).setColor(col);
                leds.getLED(MAX_LED-i).setOn();
            } else {
                leds.getLED(MAX_LED-i).setOff();
            }
        }
    }


}
