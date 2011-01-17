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
package com.sun.spot.wot.gateway;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.spot.wot.utils.PrettyPrint;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class Device {
    private static final int DEFAULT_RETRY_WAITTIME = 10000; // in milliseconds
    private String Id = null;
    private String homeURI = null;
    private String tmpURI = null;
    private DeviceForwarder.Type interactionType;
    private String address = null;
    private boolean usesCompression = false;

    private long lastHeardTime = 0L;
    private long lastCheckInTime = 0L;
    private int regLifetime = 0;
    private long awakeDuration = 0L;
    private long nextSleepDuration = 0L;
    private long inaccessibleUntil = 0L; // stores a duration (in ms since 1970/1/1
    // until which the SPOT is treated as inaccessible
    private ResourceCache resourceCache = new ResourceCache();
    private RequestQueue requestQueue = new RequestQueue();
    private PendingRequestHandler pendingRequestHandler = null;

    public Device(String Id, DeviceForwarder.Type type,
            boolean usesCompression, String address, int lifetime) {
        this.Id = Id;
        this.interactionType = type;
        this.usesCompression = usesCompression;
        this.address = address;
        this.lastHeardTime = System.currentTimeMillis();
        this.regLifetime = lifetime;
    }

    /**
     * @return the Id
     */
    public String getId() {
        return Id;
    }

    /**
     * @param Id the Id to set
     */
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     * @return the interactionType
     */
    public DeviceForwarder.Type getInteractionType() {
        return interactionType;
    }

    /**
     * @param interactionType the interactionType to set
     */
    public void setInteractionType(DeviceForwarder.Type type) {
        this.interactionType = type;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return whether the device is sleeping or not
     */
    public boolean isSleeping() {
        // Handle the special case for new devices that haven't
        // sent a check in message yet
        if (lastCheckInTime == 0L) return false;
        long now = System.currentTimeMillis();
        if ((now < (lastCheckInTime + awakeDuration)) ||
                (now > (lastCheckInTime + awakeDuration + nextSleepDuration))) {
            return false;
        }

        return true;
    }

    public long getRemainingSleepDuration() {
        return (lastCheckInTime + awakeDuration +
                nextSleepDuration - System.currentTimeMillis());
    }

    public long getRemainingAwakeDuration() {
        return (lastCheckInTime + awakeDuration - System.currentTimeMillis());
    }

    public boolean isInaccessible() {
        if (System.currentTimeMillis() > inaccessibleUntil) {
            inaccessibleUntil = 0L;
            return false;
        }
        
        return true;
    }
    
    public long getRemainingInaccessibleDuration() {
        return (inaccessibleUntil - System.currentTimeMillis());
    }

    /**
     * @return the usesCompression
     */
    public boolean usesCompression() {
        return usesCompression;
    }

    /**
     * @param usesCompression the usesCompression to set
     */
    public void setUsesCompression(boolean usesCompression) {
        this.usesCompression = usesCompression;
    }

    /**
     * @return the lastHeardTime
     */
    public long getLastHeardTime() {
        return lastHeardTime;
    }

    /**
     * @param lastHeardTime the lastHeardTime to set
     */
    public void setLastHeardTime(long lastHeardTime) {
        this.lastHeardTime = lastHeardTime;
    }

    /**
     * @return the resourceCache
     */
    public ResourceCache getResourceCache() {
        return resourceCache;
    }

    /**
     * @param resourceCache the resourceCache to set
     */
    public void setResourceCache(ResourceCache resourceCache) {
        this.resourceCache = resourceCache;
    }

    /**
     * @return the requestQueue
     */
    public RequestQueue getRequestQueue() {
        return requestQueue;
    }

    public int getRegLifetime() {
        return regLifetime;
    }
    
    public static String getInfoHdrs() {
        String result =
                "<th>" + "Id" + "</th>" +
                //"<th>" + "Connection" + "</th>" +
                "<th>" + "Service addr" + "</th>" +
                "<th>" + "Compression" + "</th>" +
                "<th>" + "Heard" + "</th>" +
                "<th>" + "Expiration" + "</th>" +
                "<th>" + "CheckIn" + "</th>" +
                "<th>" + "Status" + "</th>" +
                "<th>" + "Resource <br/> cache" + "</th>" +
                "<th>" + "Request <br/> queue" + "</th>";

        return result;
    }

    private String getStatus() {
        String result = "";
            if (isInaccessible()) {
                result += "Inaccessible <br/>for " +
                Utils.makeDurationEstimate(getRemainingInaccessibleDuration());
            } else if (isSleeping()) {
                result += "Sleeping for another " +
                        Utils.makeDurationEstimate(getRemainingSleepDuration());
            } else {
                result += "Awake";

                if (getRemainingAwakeDuration() > 0) {
                    result += "<br/>for " +
                            Utils.makeDurationEstimate(getRemainingAwakeDuration()) +
                            ", then asleep for " +
                            Utils.makeDurationEstimate(nextSleepDuration);
                }
            }
        return result;
    }
    
    public String getInfoAsHTMLRow() {
        String summary = "";
        String result =
                    "<td>" + "<a href=\"" + getId() + "/.well-known/r\">" + getId() +
                        "</a>" + "</td>" +
                    "<td>" + getAddress() + "<br/>" +
                    "(" + Utils.InteractionToString(getInteractionType()) +
                    ")" + "</td>" +
                    "<td>" + (usesCompression() + "").toUpperCase() + "</td>" +
                    "<td>" + 
                        Utils.makeDurationEstimate(System.currentTimeMillis() -
                            getLastHeardTime()) + " ago" + "</td>";
        result += "<td> In " + Utils.makeDurationEstimate((getLastHeardTime() +
                1000*getRegLifetime()) - System.currentTimeMillis()) + "</td>";
        
        if (lastCheckInTime == 0) {
            result += "<td>" + "" + "</td>" +
                    "<td>" + "Awake" + "</td>";
        } else {
            result += "<td>" +
                    Utils.makeDurationEstimate(System.currentTimeMillis() -
                        lastCheckInTime) + " ago" + "</td>";
            result += "<td>" + getStatus() + "</td>";
        }

        summary = resourceCache.getSummary();
        if (summary.equalsIgnoreCase("empty")) {
            result += "<td>" + "empty" + "</td>";
        } else {
            result += "<td>" + "<a href=\"/rc/" + getId ()+ "/\">" +
                 summary + "</a>" + "</td>";
        }

        summary = requestQueue.getSummary();
        if (summary.equalsIgnoreCase("empty")) {
            result += "<td>" + "empty" + "</td>";
        } else {
            result += "<td>" + "<a href=\"/rq/" + getId ()+ "/\">" +
                summary + "</a>" + "</td>";
        }
 
        return result;
    }

    public String getInfoAsJSON() {
        String summary = "";
        String result =
                    "\t{\n" +
                    "\t\t\"id\": \"" + getId() + "\",\n" +
                    "\t\t\"address\": \"" + getAddress() + "\",\n" +
                    "\t\t\"protocol\": \"" +
                    Utils.InteractionToString(getInteractionType()) + "\",\n" +
                    "\t\t\"compression\": " +
                    (usesCompression() + "").toLowerCase() + ",\n" +
                    "\t\t\"lastHeard\": " + getLastHeardTime() + ",\n" +
//                    "\t\tawakeTime: " + awakeDuration + ",\n" +
//                    "\t\tsleepTime: " + nextSleepDuration + ",\n" +
                    "\t\t\"expiry\": " + ((getLastHeardTime() +
                    1000*getRegLifetime()) - System.currentTimeMillis()) + ",\n" +
                    "\t\t\"lastCheckIn\": " + lastCheckInTime + ",\n" +
                    "\t\t\"status\": \"" + getStatus() + "\",\n" +
                    "\t\t\"resCache\": " + "\"/rc/" + getId() + "/\",\n" +
                    "\t\t\"resCacheSummary\": " + "\"" + resourceCache.getSummary() +
                    "\",\n" +
                    "\t\t\"reqQueue\": " + "\"/rq/" + getId() + "/\",\n" +
                    "\t\t\"reqQueueSummary\": " + "\"" + requestQueue.getSummary() +
                    "\"\n" +
                    "\t}";

        return result;
    }

    public HttpResponse getDeviceResponse(HttpRequest req) {
        HttpResponse res = new HttpResponse();
        DeviceForwarder di = null;
        byte[] reqBytes = new byte[2048];
        byte[] responseBytes = new byte[2048];
        int sndlen = 0;
        int rcvlen = 0;
        Date d = new Date();
        long now = 0L;

        try {
            sndlen = req.toByteArray(usesCompression(), reqBytes, 0);
            System.out.println(d + ": Forwarding request [" +
                    sndlen + " bytes]" + " to " + getId() + ":\n" +
                    "-----------------------------\n" +
                    PrettyPrint.prettyPrint(reqBytes, 0, sndlen) +
                    "\n-----------------------------");

            if (getInteractionType() == DeviceForwarder.Type.TCP6) {
                di = new TCP6Forwarder();
            } else if (getInteractionType() == DeviceForwarder.Type.UDP6) {
                di = new UDP6Forwarder();
//            } else if (getInteractionType() == DeviceForwarder.Type.RADIOGRAM){
//                di = new RadiogramForwarder();
//            } else if (getInteractionType() == DeviceForwarder.Type.RADIOSTREAM) {
//                di = new RadiostreamForwarder();
            } else {
                System.err.println("Device " + getId() + " uses unsupported " +
                        "interaction type " + getInteractionType());
                di = null;
            }

            if (di != null) {
                try {
                    rcvlen = di.sendAndGetResponse(getAddress(), reqBytes, 0,
                        sndlen, responseBytes, 0);
                } catch (Exception ex) {
//                    System.err.println("sendAndGetResponse for " + getId() +
//                            " caught " + ex);
//                    ex.printStackTrace();
                }
            }

            now = System.currentTimeMillis();
            d.setTime(now);
            if (rcvlen == 0) {
                System.out.println(d + ": Could not communicate with device " +
                        getId());
                if (inaccessibleUntil == 0) {
//                    System.out.println("Marked device inaccessible until " +
//                        new Date(System.currentTimeMillis() + DEFAULT_RETRY_WAITTIME));
                    inaccessibleUntil = System.currentTimeMillis() +
                        DEFAULT_RETRY_WAITTIME;
                }
                res.setStatus(HttpResponse.SC_GATEWAY_TIMEOUT);
                res.setHeader("Retry-After",
                        "" + (1 + DEFAULT_RETRY_WAITTIME/1000));
            } else {
                setLastHeardTime(now);
                System.out.println(d + ": Recieved response [" +
                        rcvlen + " bytes] from " + getId() + ":\n" +
                        "-----------------------------\n" +
                        PrettyPrint.prettyPrint(responseBytes, 0, rcvlen) +
                        "\n-----------------------------");
                res = HttpResponse.parse(responseBytes, 0, rcvlen);
                //res.setMaxAge(5); // XXX: This is only for testing
                getResourceCache().insert(req, res);
            }
        } catch (Exception e) {
//            System.err.println("Caught exception " + e +
//                    " on line 313 in Device.java");
//            Logger.getLogger(Device.class.getName()).log(Level.SEVERE, null, e);
            res.setStatus(HttpResponse.SC_INTERNAL_SERVER_ERROR);
        } finally {
            if (di != null) {
                di.close();
            }
        }

        return res;
    }

    public void handleCheckIn(int awakeDuration, int sleepDuration) {
        long now = System.currentTimeMillis();
        setLastHeardTime(now);
        lastCheckInTime = now;
        this.awakeDuration = awakeDuration * 1000;
        this.nextSleepDuration = sleepDuration * 1000;
        //notifyAll(); // activate any waiting threads ...
    }

    public void startPendingRequestHandler() {
        pendingRequestHandler = new PendingRequestHandler(this);
        pendingRequestHandler.start();
    }

    void stopPendingRequestHandler() {
        if (pendingRequestHandler != null) {
            pendingRequestHandler.markDone();
        }
    }
}
