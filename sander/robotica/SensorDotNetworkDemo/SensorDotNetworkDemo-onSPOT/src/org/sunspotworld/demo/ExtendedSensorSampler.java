/*
 * ExtendedSensorSampler.java
 *
 * Created on Sep 25, 2009 4:28:09 PM;
 */

package org.sunspotworld.demo;

import com.sun.spot.peripheral.Spot;
import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.peripheral.ITriColorLED;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.peripheral.IBattery;
import com.sun.spot.peripheral.IPowerController;
import com.sun.spot.peripheral.ISleepManager;
import com.sun.spot.sensorboard.io.IScalarInput;
import com.sun.spot.sensorboard.peripheral.ITemperatureInput;
import com.sun.spot.util.*;

import java.util.Calendar;
import java.util.Date;
import javax.microedition.io.*;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * This application is the 'on SPOT' portion of the SensorDotNetworkDemo. It
 * periodically samples multiple sensor values on the SPOT (light, temperature,
 * battery etc.) and transmits it to a desktop application (the 'on Desktop'
 * portion of the SensorDotNetworkDemo) which, in turn, posts those values
 * at http://sensor.network.com (registration required). See the README file
 * for additional details.
 *
 * @author: Vipul Gupta
 */
public class ExtendedSensorSampler extends MIDlet {
    private static final int HOST_PORT = 65;
    // The SAMPLE_PERIOD value here must be consistent with the same value in
    // SensorDotNetworkHostApplication.java in the onDesktop part of this
    // application
    private static final int SAMPLE_PERIOD = 15 * 60 * 1000;  // in milliseconds

    // Be sure to bump up the version number if you change the packet format
    private static final byte PACKET_FORMAT_VERSION = (byte) 0x01;
    
    private static ITriColorLED[] leds = EDemoBoard.getInstance().getLEDs();

    protected void startApp() throws MIDletStateChangeException {
        RadiogramConnection rCon = null;
        Datagram dg = null;
        long now = 0L;
        IScalarInput lightSensor =  EDemoBoard.getInstance().getLightSensor();
        ITemperatureInput tempSensor = EDemoBoard.getInstance().getADCTemperature();
        IPowerController pCtrl = Spot.getInstance().getPowerController();
        IBattery battery = pCtrl.getBattery();
        ISleepManager sleepMgr = Spot.getInstance().getSleepManager();
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        short lightReading = 0;
        float tempCelsius = (float) 0.0;
        byte batteryPct = (byte) 0;
        float vUSB = (float) 0.0;
        long upTime = 0L;
        long shallowSleepTime = 0L;
        long deepSleepTime = 0L;
        int deepSleepCnt = 0;

	// Listen for downloads/commands over USB connection
	new com.sun.spot.service.BootloaderListenerService().getInstance().start();
        System.out.println("************************************************" +
                "*********\n" +
                "ExtendedSensorSampler started on SPOT " +
                IEEEAddress.toDottedHex(ourAddr) + "\n" +
                "*********************************************************\n");
        // Allow the SPOT to deep sleep whenever possible
        sleepMgr.enableDeepSleep(true);
        try {
            // Open up a broadcast connection to the host port
            // where the 'on Desktop' portion of this demo is listening
            rCon = (RadiogramConnection) Connector.open("radiogram://broadcast:" +
                    HOST_PORT);
            dg = rCon.newDatagram(70);  // only sending 48 bytes of data
        } catch (Exception e) {
            System.err.println("Caught " + e + " in connection initialization.");
            notifyDestroyed();
        }

        now = System.currentTimeMillis();
        System.out.println(getTime(now) + " | Sampling every " +
                SAMPLE_PERIOD + " ms, waiting ~" +
                (SAMPLE_PERIOD - (now % SAMPLE_PERIOD)) + " ms");
        now = System.currentTimeMillis();
        Utils.sleep(SAMPLE_PERIOD - (now % SAMPLE_PERIOD));
        while (true) {
            try {
                // Get the current time and sensor reading
                now = System.currentTimeMillis();

                // Flash an LED to indicate a sampling event
                leds[7].setRGB(255, 255, 255);
                leds[7].setOn();

                // Collect sensor sample
                lightReading = (short) lightSensor.getValue();
                tempCelsius = (float) tempSensor.getCelsius();
                batteryPct = (byte) (battery.getBatteryLevel() & 0x7f);
                vUSB = pCtrl.getVusb();
                upTime = sleepMgr.getUpTime();
                shallowSleepTime = sleepMgr.getTotalShallowSleepTime();
                deepSleepTime = sleepMgr.getTotalDeepSleepTime();
                deepSleepCnt = sleepMgr.getDeepSleepCount();

                // Package the time and readings into a radio datagram and send
                // The packet format is as follows: version (1 byte)
                // sampleTime (8 bytes), light (2 bytes), temperature (4 bytes),
                // battery percentage (1 byte), USB voltage (4 bytes), 
                // uptime (8 bytes), timeInShallowSleep (8 bytes),
                // timeInDeepSleep (8 bytes), deepSleepCount (4 bytes)
                dg.reset();
                dg.writeByte(PACKET_FORMAT_VERSION);
                dg.writeLong(now);
                dg.writeShort(lightReading);
                dg.writeFloat(tempCelsius);
                dg.writeByte(batteryPct);
                dg.writeFloat(vUSB);
                dg.writeLong(upTime);
                dg.writeLong(shallowSleepTime);
                dg.writeLong(deepSleepTime);
                dg.writeInt(deepSleepCnt);
                rCon.send(dg);

                System.out.println(getTime(now) + " | " +
                        "Light:" + lightReading + ", " +
                        "Temp:" + tempCelsius + ", " +
                        "Battery:" + batteryPct + ", " +
                        "vUSB:" + vUSB + ", " +
                        "upTime:" + upTime + ", " +
                        "shallowSleep:" + shallowSleepTime + ", " +
                        "deepSleep:" + deepSleepTime + ", " +
                        "deepSleepCnt:" + deepSleepCnt
                        );

                leds[7].setOff();

                // Go to sleep to conserve battery
                Utils.sleep(SAMPLE_PERIOD - (System.currentTimeMillis() - now));
            } catch (Exception e) {
                System.err.println("Caught " + e + " while collecting/sending sensor sample.");
            }
        }
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * I.e. if startApp throws any exception other than MIDletStateChangeException,
     * if the isolate running the MIDlet is killed with Isolate.exit(), or
     * if VM.stopVM() is called.
     * 
     * It is not called if MIDlet.notifyDestroyed() was called.
     *
     * @param unconditional If true when this method is called, the MIDlet must
     *    cleanup and release all resources. If false the MIDlet may throw
     *    MIDletStateChangeException  to indicate it does not want to be destroyed
     *    at this time.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
        for (int i = 0; i < 8; i++) {
            leds[i].setOff();
        }
    }

    // Pretty print the time
    public static String getTime(long millis) {
        String result = "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        int year = cal.get(Calendar.YEAR);
        int month = 1 + cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int min = cal.get(Calendar.MINUTE);
        int sec = cal.get(Calendar.SECOND);

        result = year + "-" + ((month < 10)? "0": "") + month + "-" +
                ((day < 10) ? "0" : "") + day + " " + hour + ":" +
                ((min < 10) ? "0" : "") + min + ":" +
                ((sec < 10) ? "0" : "") + sec;

        return result;
    }
}