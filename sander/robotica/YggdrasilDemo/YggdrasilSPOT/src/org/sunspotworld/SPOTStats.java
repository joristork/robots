package org.sunspotworld;

/*
 * Copyright (c) 2008, Sun Microsystems
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met: 
 * 
 * Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution. 
 * 
 * Neither the name of the Sun Microsystems nor the names of its contributors 
 * may be used to endorse or promote products derived from this software without 
 * specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */


import com.sun.spot.peripheral.IBattery;
import com.sun.spot.peripheral.Spot;
import com.sun.spotx.yggdrasil.nodeevent.EventFactory;
import com.sun.spotx.yggdrasil.nodeevent.Sample;
import com.sun.spotx.yggdrasil.sensorframework.Sensor;
import com.sun.spotx.yggdrasil.sensorframework.SensorFramework;
import com.sun.squawk.GC;
import java.io.IOException;

/**
 * Sensor to take some stats of the SPOTs
 * @author arshan
 */
public class SPOTStats extends Sensor {

    IBattery battery = null;

    public SPOTStats() {
        super("SPOTStats");
    }


    public void user_setup() {

        setDescription("Statistics about operating conditions of the Sun SPOT");

        addSensorValue("battery_voltage", "volts", 0, 0xFFFF, .1, 0);
        addSensorValue("usb_voltage", "volts", 0, 0xFFFF, .1, 0);
        addSensorValue("ext_voltage", "volts", 0, 0xFFFF, .1, 0);
        addSensorValue("charge_current", "milliamps", 0, 0xFFFF, .1, 0);

        addSensorValue("total_allocated", "bytes", 0, 0xFFFF, .1, 0);
        addSensorValue("free_memory", "bytes", 0, 0xFFFF, .1, 0);
        addSensorValue("allocated_memory", "bytes", 0, 0xFFFF, .1, 0);
        addSensorValue("last_gc", "ms", 0, 0xFFFF, .1, 0);

        addSensorValue("battery_level", "percentage", 0, 0xFFFF, 1, 0);
        addSensorValue("battery_state", "enum", 0, 0xFF, 1, 0);
        
        addSensorValue("uptime", "ms", 0, 0xFFFFF, 1, 0);
        addSensorValue("deepsleepcount", "count", 0, 0xFFFFF, 1, 0);
        addSensorValue("deepsleep", "ms", 0, 0xFFFFF, 1, 0);
        addSensorValue("shallowsleep", "ms", 0, 0xFFFFF, 1, 0);

    }


    public void power_on(){
    }

    public void sample() throws IOException {

        Sample sample = createSample();

        if ( battery == null )  battery = Spot.getInstance().getPowerController().getBattery();
        
        sample.writeInt(Spot.getInstance().getPowerController().getVbatt());
        sample.writeInt(Spot.getInstance().getPowerController().getVusb());
        sample.writeInt(Spot.getInstance().getPowerController().getVext());
        sample.writeInt(Spot.getInstance().getPowerController().getIcharge());

        sample.writeLong(GC.getCollector().getBytesAllocatedTotal());
        sample.writeLong(Runtime.getRuntime().freeMemory());
        sample.writeLong(Runtime.getRuntime().totalMemory());
        sample.writeLong(GC.getCollector().getLastGCTime());

        sample.writeInt(battery.getBatteryLevel());
        sample.writeInt(battery.getState());

        sample.writeLong(Spot.getInstance().getSleepManager().getUpTime());
        sample.writeLong(Spot.getInstance().getSleepManager().getDeepSleepCount());
        sample.writeLong(Spot.getInstance().getSleepManager().getTotalDeepSleepTime());
        sample.writeLong(Spot.getInstance().getSleepManager().getTotalShallowSleepTime());

        sendSample(sample);
        
    }

    public void read_sample(Sample sample) throws IOException {
        sample.readInt();
        sample.readInt();
        sample.readInt();
        sample.readInt();

        sample.readLong();
        sample.readLong();
        sample.readLong();
        sample.readLong();

        sample.readInt();
        sample.readInt();

        sample.readLong();
        sample.readLong();
        sample.readLong();
        sample.readLong();
    }

    public void power_off() {
        
    }

}
