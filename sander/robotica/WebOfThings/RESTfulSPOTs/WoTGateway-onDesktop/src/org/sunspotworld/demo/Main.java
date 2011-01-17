/*
 * Main.java
 *
 * Created on Jun 3, 2010 10:13:42 PM;
 */

package org.sunspotworld.demo;

import com.sun.spot.wot.gateway.RequestQueueApp;
import com.sun.spot.wot.gateway.MainApp;
import com.sun.spot.wot.NanoAppServer;
import com.sun.spot.wot.FileServer;
import com.sun.spot.wot.RHTTPHandler;
import com.sun.spot.wot.TCP6Handler;
import com.sun.spot.wot.UDP6Handler;
import com.sun.spot.wot.utils.PrettyPrint;
import com.sun.spot.wot.gateway.Constants;
import com.sun.spot.wot.gateway.DeviceExpirer;
import com.sun.spot.wot.gateway.UDP6Advertiser;
import com.sun.spot.ipv6.IPUtils;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.wot.TCPHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Sample Sun SPOT host application
 */
public class Main {
    private static byte[] advertisementBytes = null;
    
    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {
        Thread udp6AdvThread;
        Thread deviceExpirer;
        String spotlist = null;
        boolean autodiscovery = true;
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i].equalsIgnoreCase("-spots")) {
                    autodiscovery = false;
                    System.out.println("***** Auto-discovery is turned off *****");
                    spotlist = args[i+1];
                    System.out.println("Hardcoded SPOTs: " + spotlist);
                }
            }

            NanoAppServer ar = new NanoAppServer();
            ar.setDefaultApp(new MainApp("n=Relays requests to devices."));
            ar.registerApp("/rg",
                    new DeviceRegistrationApp("n=Handles device registration.\n" +
                    "spots=" + spotlist));
            ar.registerApp("/ci", new CheckInApp("n=Handles device check-in."));
            ar.registerApp("/rc", new ResourceCacheApp("n=Handles resource cache."));
            ar.registerApp("/rq", new RequestQueueApp("n=Handles request queue."));
            ar.registerApp("/doc", new FileServer("root=./htdocs\n" +
                    "allowdirectorylisting=true\nn=Handles requests for static content."));
            new TCPHandler(Constants.TCP_SVCPORT, ar).start();
            new TCP6Handler(Constants.TCP6_SVCPORT, ar).start();
            new UDP6Handler(Constants.UDP6_SVCPORT, ar).start();

            /* Before uncommenting this, make sure that the reverse
             * HTTP gateway is running on sensor.network.com at port 1234
             * (if you point your browser to http://sensor.network.com:1234,
             * you should see a Gateway Time Out message and replace xxxx below
             * with the last four hex digits in your basestation.
             */
//            new RHTTPHandler("http://sensor.network.com:1234/gw-xxxx",
//                    "/gw-xxxx".length(), ar).start();
            try {
                if (autodiscovery) {
                    initializeAdvertisement();
                    udp6AdvThread = new UDP6Advertiser(advertisementBytes, 20000);
                    udp6AdvThread.setPriority(Thread.MIN_PRIORITY);
                    udp6AdvThread.start();
                    deviceExpirer = new DeviceExpirer();
                    deviceExpirer.setPriority(Thread.MIN_PRIORITY);
                    deviceExpirer.start();
                }
            } catch (Exception ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void initializeAdvertisement() {
        String[] serviceAddresses = new String[3];
        long ourAddr = RadioFactory.getRadioPolicyManager().getIEEEAddress();

        serviceAddresses[0] = "udp://" +
                "[" + IPUtils.ieeeToIPv6Address(IEEEAddress.
                toDottedHex(ourAddr)).toString() + "]" +
                ":" + Constants.UDP6_SVCPORT;
        serviceAddresses[1] = "tcp://" +
                "[" + IPUtils.ieeeToIPv6Address(IEEEAddress.
                toDottedHex(ourAddr)).toString() + "]" +
                ":" + Constants.TCP6_SVCPORT;

        String address = "localhost";
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE,
                    null, e);
        }
        serviceAddresses[2] = "tcp://" + address + ":" + Constants.TCP_SVCPORT;
        System.out.println("Our IP address = " + address);

        // Populate the advertisement
        int tmp = 0;
        for (int i = 0; i < serviceAddresses.length; i++) {
            try {
                tmp += (1 + serviceAddresses[i].getBytes("UTF-8").length);
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null,
                        ex);
            }
        }

        advertisementBytes = new byte[1 + tmp]; // first byte is TTL
        tmp = 0;
        advertisementBytes[tmp++] = 60; // TTL is 60 seconds
        
        for (int i = 0; i < serviceAddresses.length; i++) {
            try {
                int len = serviceAddresses[i].getBytes("UTF-8").length;
                System.arraycopy(serviceAddresses[i].getBytes("UTF-8"), 0,
                        advertisementBytes, tmp, len);
                tmp += len;
                advertisementBytes[tmp++] = 0;
            } catch (UnsupportedEncodingException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        System.out.println("Advertisement: \n" +
                PrettyPrint.prettyPrint(advertisementBytes));
    }
}
