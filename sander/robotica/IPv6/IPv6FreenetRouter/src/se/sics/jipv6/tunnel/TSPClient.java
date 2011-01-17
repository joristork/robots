/**
 * Copyright (c) 2009, Swedish Institute of Computer Science.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the Institute nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE INSTITUTE AND CONTRIBUTORS ``AS IS'' AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED.  IN NO EVENT SHALL THE INSTITUTE OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS
 * OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 *
 * This file is part of jipv6.
 *
 * $Id: $
 *
 * -----------------------------------------------------------------
 *
 *
 * Author  : Joakim Eriksson
 * Created :  mar 2009
 * Updated : $Date:$
 *           $Revision:$
 */
package se.sics.jipv6.tunnel;

import com.sun.spot.ipv6.IIPNetworkInterface;
import com.sun.spot.ipv6.IP;
import com.sun.spot.ipv6.IPv6Packet;
import com.sun.spot.ipv6.IPUtils;
import com.sun.spot.ipv6.Inet6Address;
import com.sun.spot.ipv6.NetworkException;
import com.sun.spot.ipv6.icmp.NeighborManager;
import com.sun.spot.ipv6.routing.IPRouteEntry;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TSPClient implements IIPNetworkInterface {

    public static final boolean DEBUG = false;
    public static final int DEFAULT_PORT = 3653;
    private static final byte[] VERSION = "VERSION=2.0.0\r\n".getBytes();
    private static final byte[] AUTH_PLAIN = "AUTHENTICATE PLAIN\r\b".getBytes();
    private static final byte[] AUTH_ANON = "AUTHENTICATE ANONYMOUS\r\b".getBytes();
    private byte linkAddress[] = new byte[]{0, 0, 0, 0, 0, 0, 0, 0};
    private byte myLinkLocalAddress[] = new byte[]{(byte) 0xfe, (byte) 0x80,
        0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f, 0x00};
    private byte[] myLocalSolicited = new byte[]{(byte) 0xff, 0x02, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0x01, (byte) 0xff, 0, 0, 0};
    private byte myAddress[] = new byte[]{0x0, 0x0, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
        0x09, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

    ;
    private byte myNetmask[];
    private int netmaskSize;
    private int keepalive;
    private String pingAddress;
    private Pinger pinger;

    public String getInterfaceName() {
        return getName();
    }

    public void sendPacket(IPv6Packet arg0, IPRouteEntry arg1) throws NetworkException {
//        System.out.println(arg0.debug());
        sendPacket(arg0);
    }

    public byte[] getGlobalAddress() {
        return myAddress;
    }

    public void setGlobalAddress(byte arg0[]) {
        System.out.println("[TSPClient] Setting my IPAddr to: " + IPUtils.addressToString(arg0));
        this.myAddress = arg0;
        IP.getInstance().addAddress(this.myAddress);
    }

    public int getNetmaskSize() {
        return netmaskSize;
    }

    public void setNetmaskSize(int arg0) {
        myNetmask = IPUtils.createNetmask(arg0);
        this.netmaskSize = arg0;
    }

    public byte[] getNetmask() {
        return this.myNetmask;
    }

    public void setSubnetPrefix(byte[] arg0, int arg1) {
        throw new IllegalArgumentException("Not supported yet.");
    }

    public byte[] getSubnetPrefix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getSubnetSize() {
        return netmaskSize;
    }

    public NeighborManager getNeighborManager() {
        return null;
    }

    enum WriterState {

        WAIT, STARTED, CAPABILITIES_RECEIVED, AUTHENTICATE_REQ_OK,
        TUNNEL_CONF_RECEIVED, TUNNEL_UP
    };

    enum ReaderState {

        CAP_EXPECTED, AUTH_ACK_EXPECTED, AUTH_OK_EXPECTED, TUNNEL_CONF_EXPECTED,
        TUNNEL_UP
    };
    private static final Pattern prefixPattern =
            Pattern.compile("(?m).+?<prefix (.+?)>(.+?)</prefix>");
    private static final Pattern myIPPattern =
            Pattern.compile("(?s).+?<client>.+?ipv6\">(.+?)</address>");
    private static final Pattern keepalivePattern =
            Pattern.compile("(?s).+?<keepalive interval=\"(.+?)\".+?ipv6\">(.+?)</address>.+?</keepalive>");
    private IP ipStack;
    WriterState writerState = WriterState.STARTED;
    ReaderState readerState = ReaderState.CAP_EXPECTED;
    DatagramSocket connection; //args[0], DEFAULT_PORT);
    DatagramPacket receiveP;
    InetAddress serverAddr;
    int seq = 0;
    private String user;
    private String password;
    private boolean userLoggedIn = false;

    public TSPClient(String host) throws SocketException, UnknownHostException {
        this(host, null, null);
    }

    public TSPClient(String host, String user, String password) throws SocketException, UnknownHostException {
        this.user = user;
        this.password = password;

        connection = new DatagramSocket();
        serverAddr = InetAddress.getByName(host);
        //connection.connect(serverAddr, DEFAULT_PORT);
        receiveP = new DatagramPacket(new byte[1280], 1280);
        /* create multicast solicited address */

        Runnable writer = new Runnable() {

            public void run() {
                try {
                    writer();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable reader = new Runnable() {

            public void run() {
                try {
                    reader();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(writer).start();
        new Thread(reader).start();
    }

    public String getName() {
        return "tsp";
    }

    public static TSPClient startTSPTunnel(String server, String user, String password) {
        try {
            TSPClient tunnel = new TSPClient(server, user, password);
            tunnel.setIPStack();
            tunnel.waitSetup();
            return tunnel;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setIPStack() {
        this.ipStack = IP.getInstance();
        if (DEBUG) {
            System.out.println("[TSPClient] IP stack set");
        }
    }

    public boolean isReady() {
        System.out.println("[TSPClient] Writer State - Tunnel UP");
        return writerState == WriterState.TUNNEL_UP;
    }
    int wWait = 0;

    private void writer() throws IOException, InterruptedException {
        System.out.println("Writer started. sending version...");
        while (true) {
            switch (writerState) {
                case STARTED:
                    sendPacket(VERSION, VERSION.length);
                    setReaderState(ReaderState.CAP_EXPECTED, WriterState.WAIT);
                    break;
                case WAIT:
                    Thread.sleep(100);
                    wWait++;
                    if (wWait > 10) {
                        System.out.println("Waited for " + wWait);
                    }
                    break;
                case CAPABILITIES_RECEIVED:
                    System.out.println("Writer: sending AUTH");
                    if (user == null) {
                        sendPacket(AUTH_ANON, AUTH_ANON.length);
                        setReaderState(ReaderState.AUTH_OK_EXPECTED, WriterState.WAIT);
                    } else {
                        sendPacket(AUTH_PLAIN, AUTH_PLAIN.length);
                        setReaderState(ReaderState.AUTH_ACK_EXPECTED, WriterState.WAIT);
                    }
                    break;
                case AUTHENTICATE_REQ_OK:
                    if (user == null || userLoggedIn) {
                        sendTunnelReq();
                        setReaderState(ReaderState.TUNNEL_CONF_EXPECTED, WriterState.WAIT);
                    } else {
                        // send login with user/pass!!!
                        sendAuth();
                        userLoggedIn = true;
                        setReaderState(ReaderState.AUTH_OK_EXPECTED, WriterState.WAIT);
                    }
                    break;
                case TUNNEL_CONF_RECEIVED:
                    String accept = "<tunnel action=\"accept\"></tunnel>\r\n";
                    accept = "Content-length: " + accept.length() + "\r\n" + accept;
                    sendPacket(accept.getBytes(), accept.length());
                    System.out.println("*** Tunnel UP!");
                    setReaderState(ReaderState.TUNNEL_UP, WriterState.TUNNEL_UP);
                    notifyReady();
                    break;
                case TUNNEL_UP:
                    /* all ok - do nothing but sleep.*/
                    Thread.sleep(100);
                    break;
                default:
                    System.out.println("In mode: " + writerState);
                    Thread.sleep(1000);
            }
        }
    }

    private synchronized void notifyReady() {
        notifyAll();
    }

    private void sendAuth() throws IOException {
        String auth = "\0" + user + "\0" + password + "\r\n";
        sendPacket(auth.getBytes(), auth.length());
    }

    private void sendTunnelReq() throws IOException {
        InetAddress myAddr = InetAddress.getLocalHost();
        byte[] addr = myAddr.getAddress();
        String myAddress = String.format("%d.%d.%d.%d",
                addr[0] & 0xff, addr[1] & 0xff, addr[2] & 0xff, addr[3] & 0xff);
        String router = "";
        if (user != null) {
            router = "<router><prefix length=\"64\"/></router>";
        }
        String tunnelConf =
                "<tunnel action=\"create\" type=\"v6udpv4\"><client><address type=\"ipv4\">" +
                myAddress + "</address><keepalive interval=\"30\"></keepalive>" + router +
                "</client></tunnel>\r\n";
        tunnelConf = "Content-length: " + tunnelConf.length() + "\r\n" +
                tunnelConf;
        sendPacket(tunnelConf.getBytes(), tunnelConf.length());
    }

    private void setReaderState(ReaderState rs, WriterState ws) {
        readerState = rs;
        writerState = ws;
        wWait = 0;
    }

    private void reader() throws IOException {
        while (true) {
            if (DEBUG) {
                System.out.println("Receiving packet...");
            }
            connection.receive(receiveP);
            if (DEBUG) {
                System.out.println("TSPClient: Packet received: " + receiveP.getLength());
            }
            byte[] data = receiveP.getData();
            if (DEBUG) {
                for (int i = 0, n = receiveP.getLength(); i < n; i++) {
                    if (i < 8 || writerState == WriterState.TUNNEL_UP) {
                        System.out.printf("%02x", data[i]);
                    } else {
                        System.out.print((char) data[i]);
                    }
                }
            }
            String sData = new String(data, 8, receiveP.getLength() - 8);
            if (DEBUG) {
                String[] parts = sData.split("\n");
                if ((parts.length > 1) && readerState != ReaderState.TUNNEL_UP) {
                    System.out.println("Response size: " + parts[0]);
                    System.out.println("Response code: " + parts[1]);
                }
            }
            switch (readerState) {
                case CAP_EXPECTED:
                    writerState = WriterState.CAPABILITIES_RECEIVED;
                    break;
                case AUTH_ACK_EXPECTED:
                    writerState = WriterState.AUTHENTICATE_REQ_OK;
                    break;
                case AUTH_OK_EXPECTED:
                    // Check if auth is really ok!!!
                    writerState = WriterState.AUTHENTICATE_REQ_OK;
                    break;
                case TUNNEL_CONF_EXPECTED:
                    if (user != null) {

//                        System.out.println("[TSPClient] Looking for prefix");

                        Matcher m = prefixPattern.matcher(sData);
                        if (m.find()) {
//                            System.out.println("Prefix: " + m.group(2) + " arg:" + m.group(1));
                            if (ipStack != null) {
                                byte[] prefix = getPrefix(m.group(2));
                                /* this is hardcoded for 64 bits for now */
//                                ipStack.setPrefix(prefix, 64);
//                                System.out.println("[TSPClient] Confguring Lowpan");
                                IIPNetworkInterface lowpan = ipStack.getInterface("ulowpan");
                                lowpan.setSubnetPrefix(prefix, 64);
                            }
                        }
                        m = myIPPattern.matcher(sData);
                        if (m.find()) {
                            if (ipStack != null) {
//                                System.out.println("### Got IP address: " + m.group(1));
                                byte[] prefix = getPrefix(m.group(1));
                                byte[] macAddr = new byte[8];
                                /// FIX THIS PART: RPS
//                                ipStack.makeLLAddress(prefix, macAddr);
//                                ipStack.setLinkLayerAddress(macAddr);
//                                ipStack.setIPAddress(prefix);

                                setGlobalAddress(prefix);
                                setNetmaskSize(64);
                            }
                        }
//                        System.out.println("[TSPClient] Look for keepalive");
                        m = keepalivePattern.matcher(sData);
                        if (m.find()) {
//                            System.out.println("[TSPClient] ### Got Keepalive: " + m.group(1) + ":" + m.group(2));
                            keepalive = Integer.parseInt(m.group(1));
                            pingAddress = m.group(2);

                        }
                    } else {
//                        System.out.println("[TSPClient] Looking for address");

                        Matcher m = myIPPattern.matcher(sData);
                        if (m.find()) {
                            if (ipStack != null) {
//                                System.out.println("### Got second IP address: " + m.group(1));
                                byte[] prefix = getPrefix(m.group(1));
                                byte[] macAddr = new byte[8];
                                /// FIX THIS PART: RPS
//                                ipStack.makeLLAddress(prefix, macAddr);
//                                ipStack.setLinkLayerAddress(macAddr);
//                                ipStack.setIPAddress(prefix);

//                                setAddress(prefix);
                            }
                        } else {
                            System.out.println("NOT MATCH!!!");
                        }
                        for (int i = 13; i < 16; i++) {
//                            myLocalSolicited[i] = myGlobalAddress[i];
                        }
                    }
                    writerState = WriterState.TUNNEL_CONF_RECEIVED;
                    break;
                case TUNNEL_UP:
                    if (DEBUG) {
                        System.out.println("*** Tunneled packet received!!!");
                    }
                    if (ipStack != null) {
                        IPv6Packet packet = new IPv6Packet(data, 0, IPv6Packet.HEADER_SIZE);
//                        packet.setBytes(data, 0, receiveP.getLength());
//                        packet.parsePacketData(packet);
//                        packet.netInterface = this;
                        ipStack.processIncomingPacket(packet, this);
                    }
                    break;
            }
        }
    }

    // handles format XXXX:XXXX:XXXX ...
    private byte[] getPrefix(String prefix) {
        prefix = prefix.trim();
        String[] parts = prefix.split(":");
        // each XXXX should be two bytes...
        byte[] prefixBytes = new byte[parts.length * 2];

        for (int i = 0; i < parts.length; i++) {
//            System.out.println("## Parsing: " + parts[i]);
            int val = Integer.parseInt(parts[i], 16);
            prefixBytes[i * 2] = (byte) (val >> 8);
            prefixBytes[i * 2 + 1] = (byte) (val & 0xff);
        }
        return prefixBytes;
    }

    private void sendPacket(byte[] packetData, int numBytes) throws IOException {
        byte[] pData;
        if (writerState != WriterState.TUNNEL_UP) {
            pData = new byte[8 + numBytes];
            pData[0] = (byte) (0xf0 | (seq >> 24) & 0xf);
            pData[1] = (byte) ((seq >> 16) & 0xff);
            pData[2] = (byte) ((seq >> 8) & 0xff);
            pData[3] = (byte) (seq & 0xff);

            long time = System.currentTimeMillis() / 1000;
            pData[4] = (byte) ((time >> 24) & 0xff);
            pData[5] = (byte) ((time >> 16) & 0xff);
            pData[6] = (byte) ((time >> 8) & 0xff);
            pData[7] = (byte) ((time >> 0) & 0xff);
            seq++;
            System.arraycopy(packetData, 0, pData, 8, numBytes);
        } else {
            pData = packetData;
        }
        DatagramPacket packet = new DatagramPacket(pData, pData.length, serverAddr, DEFAULT_PORT);
        connection.send(packet);

        if (DEBUG) {
            System.out.println("Packet sent... " + numBytes);
        }
    }

    public void sendPacket(IPv6Packet packet) {
        byte[] data = packet.getBytes();
        if (DEBUG) {
            System.out.println("Sending IPv6Packet on tunnel: ");
            System.out.print("Packet: ");
            System.out.println(packet.debug());
            System.out.println();
        }
        try {
            sendPacket(data, packet.getTotalLength());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        if (args.length == 1) {
            TSPClient tspc = new TSPClient(args[0]);
        } else if (args.length == 3) {
            TSPClient tspc = new TSPClient(args[0], args[1], args[2]);
        }
    }

    public synchronized boolean waitSetup() {
        if (!isReady()) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Start Pinger for: " + pingAddress);
        Inet6Address paddr = new Inet6Address(pingAddress);
        pinger = new Pinger(this, paddr.getAddress(), keepalive);
        new Thread(pinger).start();
        pinger.setDebug(DEBUG);
        return isReady();
    }

    public byte[] getLocalSolicitedAddress() {
        return myLocalSolicited;
    }

    public byte[] getLinkLayerAddress() {
        return linkAddress;
    }

    public byte[] getLinkLocalAddress() {
        return myLinkLocalAddress;
    }
}
