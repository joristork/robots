/*
 * TCPClient.java
 *
 * Created on Jul 13, 2009 2:26:58 PM;
 */
package org.sunspotworld.demo;

import com.sun.spot.io.j2me.tcp.TCPConnection;
import com.sun.spot.util.Utils;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.io.Connector;

/**
 * Sample Sun SPOT host application
 */
public class TCPClient {

    private static final int PORT_NUMBER = 8888;

    /**
     * contact remote target and read values
     */
    public void run(String target) {
        TCPConnection sendConn;
        DataOutputStream tout = null;
        DataInputStream tin = null;


        String url;

        Utils.sleep(1000);  // Let IPv6 startup under us
        System.out.println("[APP] Sending data to = " + target);
        boolean isIP6Addr = (target.indexOf(":") > -1);
        if (isIP6Addr) {
            url = "tcp://[" + target + "]:" + PORT_NUMBER;
        } else {
            // we hope it is an IEEEAddress or hostname
            url = "tcp://" + target + ":" + PORT_NUMBER;
        }

        try {
            System.out.println("[APP] URL: " + url);
            sendConn = (TCPConnection) Connector.open(url);
            sendConn.setTimeout(3000);
            tout = sendConn.openDataOutputStream();
            tin = sendConn.openDataInputStream();

            int count = tin.readInt();
            String node = tin.readUTF();
            int light = tin.readInt();
            double temp = tin.readDouble();
            System.out.println("[APP] Previous Connections: " + count + "\tLast: " + node);
            System.out.println("[APP] Light: " + light + "\tTemperature: " + temp);

            tin.close();
            tout.close();
            sendConn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("TCPClient: must provide target address");
            System.out.println("Usage: ant host-run -Dmain.args=<target address>");
            System.exit(0);
        }
        
        TCPClient app = new TCPClient();
        app.run(args[0]);
    }
}
