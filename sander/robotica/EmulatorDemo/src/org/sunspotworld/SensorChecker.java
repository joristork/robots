/*
 * Copyright (c) 2010 Oracle.
 * Copyright (c) 2007 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.Condition;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.IAnalogInput;
import com.sun.spot.resources.transducers.IConditionListener;
import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.resources.transducers.IInputPinListener;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.IMeasurementRange;
import com.sun.spot.resources.transducers.IOutputPin;
import com.sun.spot.resources.transducers.ISwitch;
import com.sun.spot.resources.transducers.ISwitchListener;
import com.sun.spot.resources.transducers.ITemperatureInput;
import com.sun.spot.resources.transducers.ITriColorLEDArray;
import com.sun.spot.resources.transducers.LEDColor;
import com.sun.spot.resources.transducers.LightSensorEvent;
import com.sun.spot.resources.transducers.SensorEvent;
import com.sun.spot.resources.transducers.SwitchEvent;
import com.sun.spot.resources.transducers.InputPinEvent;

import com.sun.spot.sensorboard.EDemoBoard;
import com.sun.spot.sensorboard.peripheral.ADT7411Event;
import com.sun.spot.sensorboard.peripheral.IADT7411ThresholdListener;
import com.sun.spot.sensorboard.peripheral.LightSensor;  
import com.sun.spot.util.Utils;

import java.io.IOException;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * This MIDlet demonstrates how the Virtual Sensor Panel can be used 
 * in the SPOT World Emulator to interact with a virtual SPOT.
 * The LEDs on the virtual SPOT display the value read from one
 * of the SPOT's sensors.
 *
 * There are four different modes:
 *
 * 1. Display the light sensor reading in white
 * 2. Display the temperature sensor reading in red.
 * 3. Display the analog input A0 in green.
 * 4. Display the Z acceleration in blue.
 *
 * Pushing the left switch (SW1) advances to the next mode.
 * The current mode is shown by setting one of H0-H3 to high.
 *
 * Also D0 is set as an output and the application sets it
 * to mirror the value that D1 is set to.
 *
 * @author Ron Goldman
 */
public class SensorChecker extends MIDlet implements IADT7411ThresholdListener, 
        ISwitchListener, IInputPinListener, IConditionListener {
    
    private IOutputPin outs[] = EDemoBoard.getInstance().getOutputPins();
    private ILightSensor light = (ILightSensor)Resources.lookup(ILightSensor.class);
    private IAnalogInput analogIn = (IAnalogInput)Resources.lookup(IAnalogInput.class, "A0");
    private IIOPin d0 = (IIOPin)Resources.lookup(IIOPin.class, "D0");
    private IIOPin d1 = (IIOPin)Resources.lookup(IIOPin.class, "D1");
    private ITemperatureInput temp = (ITemperatureInput)Resources.lookup(ITemperatureInput.class, "location=eDemoboard");
    private ISwitch sw1 = (ISwitch)Resources.lookup(ISwitch.class, "SW1");
    private IAccelerometer3D accel = (IAccelerometer3D)Resources.lookup(IAccelerometer3D.class);
    private ITriColorLEDArray leds = (ITriColorLEDArray)Resources.lookup(ITriColorLEDArray.class);
    private LEDColor colors[] = { LEDColor.WHITE, LEDColor.RED, LEDColor.GREEN, LEDColor.BLUE };
    private int mode = 0;
    Condition lightCheck = null;
    
    protected void startApp() throws MIDletStateChangeException {
        sw1.addISwitchListener(this);
        if (light instanceof com.sun.spot.sensorboard.peripheral.LightSensor) {
            com.sun.spot.sensorboard.peripheral.LightSensor lt = (com.sun.spot.sensorboard.peripheral.LightSensor)light;
            lt.addIADT7411ThresholdListener(this);
            lt.setThresholds(10, 740);
            lt.enableThresholdEvents(true);
        } else {
            // no hardware thresholding so use software condition monitoring
            lightCheck = new Condition(light, this, 10 * 1000) { // check every 10 seconds
                public boolean isMet(SensorEvent evt) throws IOException {
                    int val = ((LightSensorEvent)evt).getValue();
                    if (val <= 10 || val >= 740) {
                        stop();         // can stop checking once threshold exceeded
                        return true;    // condition met: notify listeners
                    } else {
                        return false;   // condition not met
                    }
                }
            };
            lightCheck.start();    // start monitoring the condition
        }
        outs[mode].setHigh();
        d0.setAsOutput(true);
        d1.addIInputPinListener(this);
        if (accel instanceof IMeasurementRange) {
            int range = ((IMeasurementRange)accel).getNumberRanges();
            ((IMeasurementRange) accel).setCurrentRange(range - 1);
        }
        while (true) {
            try {
                int bars = 0;
                switch (mode) {
                    case 0:
                        bars = light.getValue() / 84;     // [0, 750] = [0, 8]
                        break;
                    case 1:
                        bars = (int)(temp.getFahrenheit() + 40.0) / 20;
                        break;
                    case 2:
                        bars = (int)(analogIn.getVoltage() * 3.0);
                        break;
                    case 3:
                        double accZ = accel.getAccelZ();
                        // System.out.println("Z acceleration = " + accZ);
                        bars = (int)((accZ + 6) / 1.333);
                        break;
                }
                leds.setColor(colors[mode]);
                for (int i = 0; i < 8; i++) {
                    leds.getLED(i).setOn(i < bars);
                }
                Utils.sleep(200);
            } catch (IOException ex) {
                // ignore sensor errors
            }
        }
    }
    
    protected void pauseApp() {
        // This will never be called by the Squawk VM
    }
    
    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
        // Only called if startApp throws any exception other than MIDletStateChangeException
    }

    /**
     * Rev8 sensorboard callback for when the light sensor value goes above or below
     * the specified thresholds.
     *
     * @param sensor the sensor being monitored.
     * @param condition the condition doing the monitoring.
     */
    public void conditionMet(SensorEvent evt, Condition condition) {
        System.out.println("Light threshold exceeded: " + ((LightSensorEvent)evt).getValue());
        Utils.sleep(2000);
        lightCheck.start();
    }

    /**
     * Rev6 sensorboard callback for when the light sensor value goes above or below
     * the specified thresholds.
     * 
     * @param light the ILightSensor that has crossed a threshold.
     * @param val the current light sensor reading.
     */
    public void thresholdExceeded(ADT7411Event evt) {
        System.out.println("Light threshold exceeded: " + evt.getValue());
        Utils.sleep(2000);
        ((LightSensor)evt.getSensor()).enableThresholdEvents(true);      // re-enable notification
    }

    /**
     * Callback for when the light sensor thresholds are changed.
     * 
     * @param light the ILightSensor that had its threshold values changed.
     * @param low the new light sensor low threshold value.
     * @param high the new light sensor high threshold value.
     */
    public void thresholdChanged(ADT7411Event evt) {
    }

    public void switchReleased(SwitchEvent evt) {
        outs[mode].setHigh(false);
        if (++mode > 3) { 
            mode = 0;
        }
        outs[mode].setHigh(true);
    }

    public void switchPressed(SwitchEvent evt) {
    }

    public void pinSetLow(InputPinEvent evt) {
        d0.setLow();
    }

    public void pinSetHigh(InputPinEvent evt) {
        d0.setHigh();
    }
}
