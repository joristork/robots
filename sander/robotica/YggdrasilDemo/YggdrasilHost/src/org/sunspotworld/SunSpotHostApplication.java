/*
 * SunSpotHostApplication.java
 *
 * Created on Sep 22, 2008 3:35:50 PM;
 */
package org.sunspotworld;

import com.sun.spot.util.IEEEAddress;
import com.sun.spotx.yggdrasil.nodeevent.Event;
import com.sun.spotx.yggdrasil.nodeevent.EventListener;
import com.sun.spotx.yggdrasil.nodeevent.SampleType;
import com.sun.spotx.yggdrasil.nodeevent.Sample;
import com.sun.spotx.yggdrasil.sensorframework.Sensor;
import com.sun.spotx.yggdrasil.sensorframework.SensorFramework;
import java.io.IOException;
import java.util.Date;

/**
 * Sample Sun SPOT host application
 */
public class SunSpotHostApplication {

    public void run() {

        /*
         * Enable Yggdrasil default logging
         */
        // LoggerFactory.enableDefaultLogging();

        // register a new event handler that just prints some traffic
        SensorFramework.getInstance().registerNodeEventListener(
                new EventListener() {

                    public void handleNodeEvent(Event evt) {
                        System.out.println("---------");
                        System.out.println("event arrived : " + new Date(System.currentTimeMillis()));
                        System.out.println("event time    : " + new Date(evt.getTimestamp()));
                        System.out.println("event type    : " + evt.getType());

                        switch (evt.getType()) {
                            case Event.DATA:
                                Sample sample = new Sample(evt);
                                System.out.println("Sample received from: " + IEEEAddress.toDottedHex(sample.getSender()));

                                Sensor sensor = SensorFramework.getInstance().findSensor(sample);

                                /*
                                 * This loop is an example of how a user could dissassemble a
                                 * Sample event with no particular knowledge of the contents
                                 *
                                 * The more specialized knowledge of the contents is meant to be
                                 * attained from the Sensor Metadata
                                 *
                                 */
                                if (sensor != null) {
                                    System.out.println("");
                                    System.out.println("Name: " + sensor.getName());
                                    System.out.println("Description: " + sensor.getDescription());
                                    for (int x = 0; x < sensor.getNumberValues(); x++) {
                                        System.out.println("Value Name: " + sensor.getValueName(x));
                                        try {
                                            switch (sensor.getValueSignature(x)) {
                                                case SampleType.BOOLEAN:
                                                    System.out.println(sample.readBoolean());
                                                    break;
                                                case SampleType.BYTE:
                                                    System.out.println(sample.readByte());
                                                    break;
                                                case SampleType.CHAR:
                                                    System.out.println(sample.readChar());
                                                    break;
                                                case SampleType.CHARS:
                                                    while (sample.readChar() != '\0') {
                                                        //read til the terminator
                                                    }
                                                    break;
                                                case SampleType.FLOAT:
                                                    System.out.println(sample.readFloat());
                                                    break;
                                                case SampleType.INT:
                                                    System.out.println(sample.readInt());
                                                    break;
                                                case SampleType.UTF:
                                                    System.out.println(sample.readUTF());
                                                    break;
                                                case SampleType.LONG:
                                                    System.out.println(sample.readLong());
                                                    break;
                                                case SampleType.SHORT:
                                                    System.out.println(sample.readShort());
                                                    break;
                                                case SampleType.DOUBLE:
                                                    System.out.println(sample.readDouble());
                                                    break;
                                                default:
                                                    System.out.println("unknown value type : " + sensor.getValueType(x));
                                            }
                                            System.out.println("");
                                        } catch (IOException ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                } else {
                                    System.out.println("Could not locate sensor");
                                }


                                break;

                            case Event.ANNOUNCE:
                                System.out.println("heard an announce from " + IEEEAddress.toDottedHex(evt.getSender()));
                                break;

                            case Event.NOTIFY:
                                System.out.println("heard a notify from " + IEEEAddress.toDottedHex(evt.getSender()));
                                break;
                        }
                    }
                }, Event.NULL);


    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {
        SunSpotHostApplication app = new SunSpotHostApplication();
        app.run();
    }
}
