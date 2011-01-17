/*
Copyright (c) 2008-2010, Sun Microsystems
All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted provided that the following conditions are met:
Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.
Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.
Neither the name of Sun Microsystems nor the names of its contributors may
be used to endorse or promote products derived from this software without
specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.sunspotworld;

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.ILightSensor;
import com.sun.spot.resources.transducers.IAccelerometer3D;
import com.sun.spot.resources.transducers.ITemperatureInput;

import java.io.IOException;

import com.sun.spotx.yggdrasil.nodeevent.*;
import com.sun.spotx.yggdrasil.sensorframework.*;

/**
 * Simple sensor to gather data from edemo board
 * @author arshan
 * @author lachlan
 */
public class EDemoSensors extends Sensor {

    private ITemperatureInput temp;
    private ILightSensor light;
    private IAccelerometer3D accel;

    public double light_val = 0;
    public double temp_val = 0;
    public double accel_val = 0;

    public EDemoSensors() {
        super("eDemoSensors");
    }

    /*
     * user setup is called once when the sensor is initialized, use this method
     * to setup anything in your sensors that only need to happen that once
     */
    public void user_setup() {
        setDescription("a sensor to read a bunch of values from the eDemoBoard");
        setSetupTime(0);

        addSensorValue("temperature", "degrees C", 0, 500, 1, 0);
        addSensorValue("light", "photons", 0, 1000, 1, 0);
        addSensorValue("acceleration", "geees", -6, 6, .001, 0);

        if (onDevice) {
            temp = (ITemperatureInput)Resources.lookup(ITemperatureInput.class);
            light = (ILightSensor)Resources.lookup(ILightSensor.class);
            accel = (IAccelerometer3D)Resources.lookup(IAccelerometer3D.class);
        }

    }

    /*
     * power_on is called everytime the sensor is woken up from deep sleep
     */
    public void power_on() {
    }


    /*
     * sample is called every time the sensor is scheduled to 'run'. This schedule
     * is determined externally, usually by a Task.
     */
    public void sample() throws IOException {
        Sample sample = createSample();

        sample.writeDouble(getTemp());
        sample.writeDouble(getLight());
        sample.writeDouble(getAccel());

        sendSample(sample);
    }

    /**
     * read the values for this sensor back out from a data sample
     * @param sample
     * @throws java.io.IOException
     */
    public void read_sample(Sample sample) throws IOException {
        temp_val = sample.readDouble();
        light_val = sample.readDouble();
        accel_val = sample.readDouble();
    }

    /*
     * power_off is called when the node is ready to enter deep sleep
     */
    public void power_off() {
    }


    /*
     * Utility methods for this sensor
     *
     */
    public double getLight() throws IOException {
        if (onDevice) {
            return light.getAverageValue();
        }
        return light_val;
    }

    public double getTemp() throws IOException {
        if (onDevice) {
            return temp.getCelsius();
        }
        return temp_val;
    }

    public double getAccel() throws IOException {
        if (onDevice) {
            return accel.getAccel();
        }
        return accel_val;
    }
}
