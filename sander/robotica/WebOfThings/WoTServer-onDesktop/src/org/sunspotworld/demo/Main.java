/*
 * Main.java
 *
 * Created on May 14, 2010 2:33:11 PM;
 */

package org.sunspotworld.demo;

import com.sun.spot.wot.NanoAppServer;
import com.sun.spot.wot.RHTTPHandler;
import com.sun.spot.wot.TCPHandler;
import java.io.IOException;

/**
 * Sample Sun SPOT host application
 */
public class Main {
    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {
        NanoAppServer nas = new NanoAppServer();

        nas.registerApp("/echo", new EchoApp("n=Echo Service\nsh=e\nct=0"));
        nas.registerApp("/date", new DateApp("n=Date Service\nd=http://en.wikipedia.org/wiki/Date\nsh=d\nct=0"));
        //ar.setDefaultApp(new DateApp());

        try {
            new TCPHandler(9999, nas).start();
            // Before uncommenting this line, make sure that a reverse HTTP
            // gateway is running on sensor.network.com at port 1234. Also,
            // replace xxxx with the last four hex digits of your basestation's
            // address (xxxx just needs to be a unique string that won't conflict
            // with the string chosen by any another user who may be running an
            // RHHTPHandler that connects up to sensor.network.com:1234)
//            new RHTTPHandler("http://sensor.network.com:1234/xxxx",
//                    "/xxxx".length(), nas).start();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
