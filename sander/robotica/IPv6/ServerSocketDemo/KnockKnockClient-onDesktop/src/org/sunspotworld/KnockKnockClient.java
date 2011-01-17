/*
 * SunSpotHostApplication.java
 *
 * Created on Apr 30, 2010 1:41:26 PM;
 */
package org.sunspotworld;

import com.sun.spot.ipv6.IP;
import com.sun.spot.ipv6.UnknownHostException;
import com.sun.spot.ipv6.tcp.Socket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
//import java.net.Socket;
//import java.net.UnknownHostException;

/**
 * Sample Sun SPOT host application
 */
public class KnockKnockClient {

    private static String readln(InputStream in) {
        String s = "";
        int i;
        try {

            while (((i = in.read()) != -1) && (i != '\n') && (i != '\r')) {
                s += (char) i;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return s;

    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {

        Socket kkSocket = null;
        PrintWriter out = null;
        InputStream ins = null;

        try {
            kkSocket = new Socket(args[0], 4444);
            out = new PrintWriter(kkSocket.getOutputStream(), true);
            ins = kkSocket.getInputStream();
            System.out.println("[APP] Buffered Reader Created");
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (IOException io) {
            io.printStackTrace();
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String fromServer;
        String fromUser;
        try {
            while ((fromServer = readln(ins)) != null) {
                System.out.println("[APP]Server: " + fromServer);
                if (fromServer.equals("Bye.")) {
                    break;
                }
                fromUser = stdIn.readLine();
                if (fromUser != null) {
                    System.out.println("[APP] Client:" + fromUser + ":");
                    out.println(fromUser);
                    out.flush();
                }
            }
        } catch (Exception e) {
            System.out.println("[APP] Conversational Error");
            e.printStackTrace();
        }


        try {
            out.close();
            ins.close();
            stdIn.close();
            kkSocket.close();
        } catch (IOException ex) {
            System.out.println("Error cleaning up sockets");
        }

    }
}
