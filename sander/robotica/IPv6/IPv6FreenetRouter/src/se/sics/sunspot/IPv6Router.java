/*
 * Author: Joakim Eriksson, SICS
 * IPv6 router for the Sun SPOT
 * 
 * Will use some "binary" protocol for communication with a host app.?
 * 
 * Will handle HC01 compressed IPv6 packets over 6lowpan (802.15.4).
 */
package se.sics.sunspot;

import com.sun.spot.ipv6.IIPNetworkInterface;
import com.sun.spot.ipv6.IP;
//import javax.microedition.io.*;



import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import se.sics.jipv6.tunnel.TSPClient;

/**
 * 
 * @author: Joakim Eriksson
 */
public class IPv6Router {

    private IP ipStack;
    char[] hex = "0123456789ABCDEF".toCharArray();

    /* TSPClient info */
    private String user;
    private String pwd;
    private String host;
    private TSPClient tunnel = null;

    public IPv6Router(String host, String user, String pwd) {
        this.host = host;
        this.user = user;
        this.pwd = pwd;

    }

    private void run() throws Exception {
        long ieeeAddress = RadioFactory.getRadioPolicyManager().getIEEEAddress();
        IEEEAddress address = new IEEEAddress(ieeeAddress);

        System.out.println("Starting IPv6Router application on " + address + " ...");

        ipStack = IP.getInstance();

        tunnel = TSPClient.startTSPTunnel(host, user, pwd);
        ipStack.registerInterface(tunnel, true);

        System.out.println("IPv6 Router started");
        IP.getInstance().dumpState();
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) throws Exception {
        IP.main(args);
        if (args.length < 2) {
            System.out.println("Please give host, username and password as arguments.");
            return;
        }

        IPv6Router app = new IPv6Router(args[0], args[1], args[2]);
//        IIPNetworkInterface iface = IP.getInstance().getInterface("ulowpan");
//        if (iface != null) {
//            System.out.println("[IPv6Router] Change lowpan subnet settings");
//        } else {
//            System.out.println("[IPv6Router] Error changing subnet settings");
//
//        }
        app.run();


    }
}
