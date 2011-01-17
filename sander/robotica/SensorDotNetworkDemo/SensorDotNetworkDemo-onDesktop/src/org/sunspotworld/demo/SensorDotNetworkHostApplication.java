/*
 * SensorDotNetworkHostApplication.java
 *
 * Created on Sep 25, 2009 5:41:04 PM;
 */

package org.sunspotworld.demo;

import com.network.sensor.SNHelper;
import com.network.sensor.DatastreamValue;
import com.sun.spot.io.j2me.radiogram.*;

import com.sun.spot.util.Utils;
import java.io.*;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.microedition.io.*;


/**
 * Sample Sun SPOT host application
 */
public class SensorDotNetworkHostApplication {
    private static final int HOST_PORT = 65;
    // The SAMPLE_PERIOD value here must be consistent with the same value in
    // ExtendedSensorSampler.java in the onSPOT part of this application
    private static final int SAMPLE_PERIOD = 15 * 60 * 1000;  // in milliseconds

    // Be sure to bump up the version number if you change the packet format
    private static final byte PACKET_FORMAT_VERSION = (byte) 0x01;

    private static String ServiceAddress = "sensor.network.com";
    // Please register at Sensor.Network to obtain an API key (a Base-64
    // encoded string) and replace this value with your own key
    private static String mySensorNetworkAPIKey = "YourAPIKeyHere";
    private static String SensorNetworkCreationURI =
            "http://" + ServiceAddress + "/rest/resources/datastreams";
    private static final String datastreamPrefix = "SNDemo_";
    private static final String datastreamDescriptionPrefix =
            "Sensor readings from SensorDotNetworkDemo on SPOT ";


    // Here we maintain a mapping from SPOT address to the corresponding
    // datastream URL. Each SPOT posts sensor data to its own stream.
    Properties spotAddrToDataStreamMapping = new Properties();
    private static String mediaURI = "http://blogs.sun.com/cparty/resource/2_dia/sun_spot.jpg";
    private static String mediaType = "image";
    private static String category = "telemetry";

    private static DatastreamValue[] dsValues = {
        // name, type, unit, min, max
        new DatastreamValue("Light", DatastreamValue.INT_TYPE, "lumens", null, null),
        new DatastreamValue("Temperature", DatastreamValue.FLOAT_TYPE, "Celsius", null, null),
        new DatastreamValue("Battery", DatastreamValue.INT_TYPE, "%", null, null),
        new DatastreamValue("USB_Voltage", DatastreamValue.FLOAT_TYPE, "mV", null, null),
        new DatastreamValue("Up_Time", DatastreamValue.INT_TYPE, "ms", null, null),
        new DatastreamValue("Shallow_Sleep_Time", DatastreamValue.INT_TYPE, "ms", null, null),
        new DatastreamValue("Deep_Sleep_Time", DatastreamValue.INT_TYPE, "ms", null, null),
        new DatastreamValue("Deep_Sleep_Count", DatastreamValue.INT_TYPE, "", null, null)       
    };

    private static String[] tag = {
        "", // place for the SPOT's address
        "SNDemo",
        "light",
        "temperature",
        "USB voltage",
        "battery",
        "sleep",
    };

    private String getStreamURI(String spotAddress) {
        String result = null;

        result = spotAddrToDataStreamMapping.getProperty(spotAddress);
        if (result != null) return result;
        try {
            // Here we register a datastream with Sensor.Network
            // Please refer to the API Docs at http://sensor.network.com/rest
            tag[0] = spotAddress;
            String xml = SNHelper.makeDatastreamXML(
                        datastreamPrefix + spotAddress.substring(10,19),
                        datastreamDescriptionPrefix + spotAddress,
                        tag,
                        mediaURI, mediaType, category,
                        false, (float) 0.0, (float) 0.0, (float) 0.0, // no location
                        // to register a location, change the line above to
                        // true, latitude, longitude, elevation (in meters)
                        SAMPLE_PERIOD, // periodic datastream reporting
                        dsValues);

            System.out.println("Datastream XML:\n" + xml);
            result = SNHelper.createDatastream(SensorNetworkCreationURI,
                    mySensorNetworkAPIKey, xml);
        } catch (IOException ex) {
            System.err.println("Encountered error. " + ex.getMessage() +
                    " when creating datastream.");
        }

        if (result != null) {
            spotAddrToDataStreamMapping.setProperty(spotAddress, result);
        }

        return result;
    }

    private void run() throws Exception {
        RadiogramConnection rCon;
        Datagram dg;
        DateFormat fmt = DateFormat.getTimeInstance();
        String xml = null;
        String[] values = new String[dsValues.length];
        String addr = null;
        byte version = 0x00;
        long sampleTime = 0L;
        short lightReading = 0;
        float tempCelsius = (float) 0.0;
        byte batteryPct = (byte) 0;
        float vUSB = (float) 0.0;
        long upTime = 0L;
        long shallowSleepTime = 0L;
        long deepSleepTime = 0L;
        int deepSleepCnt = 0;
        String datastreamURI = null;

        try {
            // Open up a server-side broadcast radiogram connection
            // to listen for sensor readings being sent by different SPOTs
            rCon = (RadiogramConnection) Connector.open("radiogram://:" + HOST_PORT);
            dg = rCon.newDatagram(rCon.getMaximumLength());
        } catch (Exception e) {
             System.err.println("Caught " + e.getMessage() +
                     " when creating broadcast radiogram connection.");
             throw e;
        }

        // Main data collection loop
        while (true) {
            try {
                // Read sensor sample received over the radio
                rCon.receive(dg);
                addr = dg.getAddress().toUpperCase();  // read sender's Id
                version = dg.readByte();
                System.out.print(prettyPrintTime(System.currentTimeMillis()) + 
                        " | " + "Src: " + addr + " v" + version + " ");

                if (version == PACKET_FORMAT_VERSION) {
                    sampleTime = dg.readLong();
                    lightReading = dg.readShort();
                    tempCelsius = dg.readFloat();
                    batteryPct = dg.readByte();
                    vUSB = dg.readFloat();
                    upTime = dg.readLong();
                    shallowSleepTime = dg.readLong();
                    deepSleepTime = dg.readLong();
                    deepSleepCnt = dg.readInt();

                    if ((datastreamURI = getStreamURI(addr)) != null) {
                        values[0] = lightReading + "";
                        values[1] = tempCelsius + "";
                        values[2] = batteryPct + "";
                        values[3] = vUSB + "";
                        values[4] = upTime + "";
                        values[5] = shallowSleepTime + "";
                        values[6] = deepSleepTime + "";
                        values[7] = deepSleepCnt + ""; 
                        
                        xml = SNHelper.makeSampleDataXML(addr, sampleTime, 
                                values);
                        SNHelper.insertData(datastreamURI,
                            mySensorNetworkAPIKey, xml);
                        System.out.print("| [");
                        for (int i = 0; i < values.length - 1; i++) {
                            System.out.print(values[i] + ", ");
                        }
                        System.out.print(values[values.length - 1]);
                        System.out.print("] @ " + datastreamURI);
                    }
                }

            } catch (Exception e) {
                System.err.println("Caught " + e +
                        " while reading/posting sensor samples.");
                System.err.println("Ignoring as a temporary error");
            }

            System.out.println("");
        }
    }

    private static String prettyPrintTime(long milliseconds) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        StringBuffer sb = sdf.format(new Date(milliseconds), new StringBuffer(),
                new FieldPosition(0));
        return sb.toString();
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) throws Exception {
        int j = 0;
        while (j < args.length - 1) {
            if (args[j].equalsIgnoreCase("-apikey")) {
                mySensorNetworkAPIKey = args[j+1];
                j += 2;
            } else if (args[j].equalsIgnoreCase("-addr")) {
                ServiceAddress = args[j+1];
                SensorNetworkCreationURI = "http://" + ServiceAddress +
                        "/rest/resources/datastreams";
                j += 2;
            } else {
                j++;
            }
        }

        System.out.println("*************************************************\n" +
                           " Starting SensorDotNetworkDemo-onDesktop. This \n" +
                           " application listens for sensor samples from   \n" +
                           " neighboring Sun SPOTs and posts them on \n" +
                           " http://sensor.network.com/ \n" +
                           "**************************************************\n");
        Utils.sleep(2000);
        if (mySensorNetworkAPIKey.equals("YourAPIKeyHere")) {
            System.err.println("ERROR: No valid API Key configred to access " +
                    "sensor.network.\nYou must register for an account at " +
                    "http://sensor.network.com/\nto obtain an API Key and " +
                    "configure it in build.properties.\nExiting.");
            System.exit(-1);
        }
        
        SensorDotNetworkHostApplication app = new SensorDotNetworkHostApplication();
        app.run();
    }
}

