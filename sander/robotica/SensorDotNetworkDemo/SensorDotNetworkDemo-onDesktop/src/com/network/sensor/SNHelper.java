/*
 * Copyright (c) 2009, Sun Microsystems
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
 */

package com.network.sensor;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Helper class to create a datastream and insert sensor sample data into it.
 * Please refer to the API documentation at http://sensor.network.com/rest for
 * details.
 *
 * Let's say you wish to define a datastream called myOfficePlant with two
 * values light and moisture. Here's what your code might look like:
 *
 * <code>
 * private static final String datastreamCreationURI =
 *            "http://sensor.network.com/rest/resources/datastreams";
 *    private static final String mySensorNetworkAPIKey = "YourAPIKeyHere";
 *    private static final String myDatastreamName = "myOfficePlant";
 *    private static final String myDatastreamDescription =
 *           "Light and moisture readings from my office plant";
 *   private static final String mySensorNodeId = "CC124-XF8";
 *
 *    // Here we assume that light is an integer value (measured in lumens) and
 *    // moisture is a float (measured in a cm/month)
 *    private static final String[] tag = {"light", "moisture"};
 *    private static final String[] valNames = {"light", "moisture"};
 *    private static final String[] valTypes = {"int", "float"};
 *    private static final String[] valUnits = {"lumens", "cm/month"};
 *    private static final String[] nulls = { null, null };
 *
 *
 *     public static void main(String[] args) throws IOException {
 *         String xml = SNHelper.makeDatastreamXML(myDatastreamName,
 *                 myDatastreamDescription, tag,
 *                 false, (float) 0.0, (float) 0.0, (float) 0.0,
 *                 0, valNames, valTypes, valUnits, nulls, nulls);
 *         String datastreamURI = null;
 *
 *         datastreamURI = SNHelper.createDatastream(datastreamCreationURI,
 *                 mySensorNetworkAPIKey, xml);
 *
 *         int lightReading = 0;
 *         float moistureReading = (float) 0.0;
 *         String[] sampleValues = new String[2];
 *
 *         while (true) {
 *
 *             // add code here to get the sensor readings into the two variables
 *             // lightReading and moistureReading
 *
 *             sampleValues[0] = "" + lightReading;
 *             sampleValues[1] = "" + moistureReading;
 *             xml = SNHelper.makeSampleDataXML(mySensorNodeId,
 *                     System.currentTimeMillis(), sampleValues);
 *             SNHelper.insertData(datastreamURI, mySensorNetworkAPIKey, xml);
 *
 *             // wait an appropriate amount
 *
 *         }
 *     }
 *
 *
 * </code>
 * @author vgupta
 */
public class SNHelper {
    private static String makeTimestamp(Calendar cal) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT:00"));
        StringBuffer sb = sdf.format(cal.getTime(), new StringBuffer(),
                new FieldPosition(0));
        //System.out.println("Timestamp is " + result);
        return sb.toString();
    }

    /**
     * Makes a timestamp from milliseconds.
     *
     * @param milliseconds Number of milliseconds since Jan 1, 1970 UTC
     * @return a string containing the timestamp in XML datetime format
     */
    public static String makeTimestamp(long milliseconds) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(milliseconds));
        return makeTimestamp(cal);
    }


    /**
     * Makes an XML description (as specified by the schema at
     * http://sensor.network.com/rest/api/examples/datastream.xsd.jsp) which can
     * be POSTed to create a new datastream.
     *
     * @param name Datastream name.
     * @param description Datastream description.
     * @param tag An array of strings used to tag the datastream. The intent is
     *            to allow users to search for datastreams by tags.
     * @param mediaURI A URI for an image or other media associated with the
     *            datastream
     * @param mediaType a string indicating the type of media at the URI, e.g.
     *            "image", "video", "audio", "other"
     * @param category a primary category for this datastream, e.g.
     *            "environment", "energy", "telemetry", "healthAndFitness" etc.
     * @param hasLocation A boolean indicating whether or not the subsequent
     *            three location parameters are valid.
     * @param lat Latitude of the location associated with the datastream.
     * @param lng Longitude of the location associated with the datastream.
     * @param ele Elevation of the location associated with the datastream.
     * @param samplingPeriod Sampling period at which a sensor is expected to
     *            feed the datastream. For aperiodic datastreams, specify zero.
     * @param values An array of DatastreamValue elements specifying
     *            the name, type, unit, min and max for the values in a data
     *            sample.
     * @return a string containing the XML description of the datastream.
     */
    public static String makeDatastreamXML(String name,
            String description, String[] tag, String mediaURI,
            String mediaType, String category,
            boolean hasLocation, float lat, float lng, float ele,
            long samplingPeriod, DatastreamValue[] values) {
        // Note: we do not perform any error checking here, e.g. ensuring
        // that lat is between -90.0 and 90.0 etc.
        String dataStreamXMLDescription = "<?xml version=\"1.0\"?>\n" +
                "<datastream>\n" +
                "\t<name>" + name + "</name>\n" +
                "\t<description>" + description + "</description>\n";

        // Add optional tags
        if (tag != null) {
            for (int i = 0; i < tag.length; i++) {
                dataStreamXMLDescription += "\t<tag>" + tag[i] + "</tag>\n";
            }
        }

        if ((mediaURI != null) && !mediaURI.trim().equals("")) {
            dataStreamXMLDescription += "\t<mediaURI>" +
                    mediaURI + "</mediaURI>\n";
            dataStreamXMLDescription += "\t<mediaType>" +
                    mediaType + "</mediaType>\n";
        }

        dataStreamXMLDescription += "\t<category>" +
                    category + "</category>\n";
        
        // Add optional location
        if (hasLocation) {
            dataStreamXMLDescription += "\t<location>\n" +
                    "\t\t<lat>" + lat + "</lat>\n" +
                    "\t\t<lng>" + lng + "</lng>\n" +
                    "\t\t<ele>" + ele + "</ele>\n" +
                    "\t</location>\n";
        }

        // Add optional sampling period
        if (samplingPeriod > 0) {
            dataStreamXMLDescription += "\t<samplingPeriod>" +
                    samplingPeriod + "</samplingPeriod>\n";
        }
        
        // Finally, add the values. The arrays valueName, valueType
        // valueUnit, valueMin and valueMax should have the same length
        for (int i = 0; i < values.length; i++) {
            dataStreamXMLDescription += "\t<value>\n";
            dataStreamXMLDescription += "\t\t<name>" + values[i].getName() +
                    "</name>\n";
            dataStreamXMLDescription += "\t\t<type>" + values[i].getType() +
                    "</type>\n";
            dataStreamXMLDescription += "\t\t<units>" + values[i].getUnit() +
                    "</units>\n";
            if (values[i].getMin() != null) {
                dataStreamXMLDescription += "\t\t<min>" + values[i].getMin() +
                        "</min>\n";
            }
            if (values[i].getMax() != null) {
                dataStreamXMLDescription += "\t\t<max>" + values[i].getMax() +
                        "</max>\n";
            }
            dataStreamXMLDescription += "\t</value>\n";
        }
        dataStreamXMLDescription += "</datastream>\n";

        return dataStreamXMLDescription;
    }

    /**
     * Makes an XML description of a sample data (as specified by the schema at
     * http://sensor.network.com/rest/api/examples/sampledata.xsd.jsp) which can
     * be POSTed to insert a new data sample into a previously created
     * datastream.
     *
     * @param nodeId Identifier of the sensor that generated this sample.
     * @param stime Time (in milliseconds since Jan 1, 1970 UTC) when the
     *              sample was created.
     * @param values An array of value readings. These values must match the
     *               type and range specified at the time of creating the
     *               datastream in which this sample is to be inserted.
     * @return An XML description of the sample data
     */
    public static String makeSampleDataXML(String nodeId, long stime,
            String[] values) {
        String sampleDataXML = "<?xml version=\"1.0\"?>\n" +
                "<sampleData>\n" +
                "\t<sensorNodeId>" + nodeId + "</sensorNodeId>\n" +
                "\t<timestamp>" + makeTimestamp(stime) +
                "</timestamp>\n";

        for (int i = 0; i < values.length; i++) {
            sampleDataXML += "\t<value>" + values[i] + "</value>\n";
        }

        sampleDataXML += "</sampleData>\n";
        return sampleDataXML;
    }

    /**
     * Attempts to create a new datastream.
     *
     * @param registrationUri The URL at which the datastream description is to
     * be posted. For sensor.network, this URI would be
     * http://sensor.network.com/rest/resources/datastreams
     * @param APIKey Authentication token for datastream creation. An API Key
     * can be obtained by registering at http://sensor.network.com/rest
     * @param xmlDescription XML description of the datastream.
     * @return a URI corresponding to the newly created datastream or null if
     * datastream creation fails.
     * @throws IOException
     */
    public static String createDatastream(String registrationUri,
            String APIKey, String xmlDescription) throws IOException {
        URL datastreamRegistrationURL = null;
        HttpURLConnection conn = null;
        OutputStream os = null;

        String result = null;
        byte[] bytesToPost = xmlDescription.getBytes();
        String error = null;

        try {
            datastreamRegistrationURL = new URL(registrationUri);
            // Here we assume that we don't need an HTTP Proxy to access
            // Sensor.Network
            conn = (HttpURLConnection) datastreamRegistrationURL.openConnection();
            // The API to create datastreams returns an HTTP 303 (See Other)
            // "redirect" to the existing datastream if a user attempts to
            // create a datastream with a duplicate name. We wish to be able
            // to report that redirect instead of following it blindly
            conn.setInstanceFollowRedirects(false);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-SensorNetworkAPIKey", APIKey);
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Content-Length", bytesToPost.length + "");

            os = conn.getOutputStream();
            // Post the XML description
            os.write(bytesToPost);

            System.out.println("Got back: " + conn.getResponseCode() +
                    " (" + conn.getResponseMessage() + ")");
            // See what response we got back
            switch (conn.getResponseCode()) {
                case HttpURLConnection.HTTP_CREATED:
                case HttpURLConnection.HTTP_SEE_OTHER:
                    // Save the URI for the newly created datastream
                    if (conn.getResponseCode() == HttpURLConnection.HTTP_SEE_OTHER) {
                        System.out.println("Warning: a datastream with this " +
                                "name exists already.");
                    }
                    result = conn.getHeaderField("Location");
                    break;

                case HttpURLConnection.HTTP_UNAUTHORIZED:
                    error = "Authentication required. Be sure to" +
                            " use a valid API Key obtained by registering " +
                            "at sensor.network.com. The API Key used for " +
                            "this request is \"" + APIKey +
                            "\".";
                    break;

                case HttpURLConnection.HTTP_BAD_REQUEST:
                    error = "Bad request. Either you have already " +
                            "created a datastream with the same name or the " +
                            "data you POSTed is malformed.";
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            throw new IOException("Encountered " + e.getMessage() +
                    " in registerDatastream");
        } finally {
            if (os != null) {
                os.close();
            }
        }

        if (error != null) throw new IOException(error);

        return result;
    }

    /**
     * Attempts to insert sensor data sample into a datastream.
     *
     * @param datastreamURI URI of the datastream as returned by
     * registerDatastream().
     * @param APIKey Authentication token for data insertion. An API Key
     * can be obtained by registering at http://sensor.network.com/rest
     * @param sampleDataXML XML description of the sensor sample data.
     * @throws IOException in case of errors
     */
    public static void insertData(String datastreamURI, String APIKey,
            String sampleDataXML) throws IOException {
        URL datastreamURL = null;
        HttpURLConnection conn = null;
        OutputStream os = null;
        String error = null;
        byte[] bytesToPost = sampleDataXML.getBytes();

        try {
            datastreamURL = new URL(datastreamURI + "/data");
            // Here we assume that we don't need an HTTP Proxy to access
            // Sensor.Network
            conn = (HttpURLConnection) datastreamURL.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("X-SensorNetworkAPIKey", APIKey);
            conn.setRequestProperty("Content-Type", "application/xml");
            conn.setRequestProperty("Content-Length", bytesToPost.length + "");

            os = conn.getOutputStream();
            // Post the XML description
            os.write(bytesToPost);

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                error = "Data insertion failed. Got " + conn.getResponseCode() +
                    " (" + conn.getResponseMessage() + ")";
            }
        } catch (Exception e) {
            throw new IOException("Encountered " + e.getMessage() +
                    " in insertSampleData.");
        } finally {
            if (os != null) {
                os.close();
            }
        }

        if (error != null) throw new IOException(error);
    }
}
