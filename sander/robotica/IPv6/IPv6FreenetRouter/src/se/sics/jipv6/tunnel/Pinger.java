/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package se.sics.jipv6.tunnel;

import com.sun.spot.ipv6.IIPNetworkInterface;
import com.sun.spot.ipv6.IPUtils;
import com.sun.spot.ipv6.NetworkException;
import com.sun.spot.ipv6.icmp.ICMP6Packet;
import com.sun.spot.ipv6.icmp.ICMPv6;
import com.sun.spot.ipv6.icmp.IICMPEchoListener;
import com.sun.spot.util.Queue;
import com.sun.spot.util.Utils;

class Pinger implements Runnable, IICMPEchoListener {

    private IIPNetworkInterface iface;
    private int keepalive;
    private byte address[];
    private boolean connected = false;
    private Queue packets;
    private ICMPv6 icmp;
    private boolean debug = false;


    Pinger(IIPNetworkInterface i, byte addr[], int time) {
        iface = i;
        keepalive = time;
        address = addr;
        packets = new Queue();
        icmp = ICMPv6.getInstance();
        if (debug) {
            System.out.println("[Pinger] Created " + keepalive + " second ping thread to " + IPUtils.addressToString(address));
        }
        connected = true;
    }

    public void run() {
        int echoId = 6565;
        int seq = 0;
        byte[] data = new byte[8];
        long grace, next, rest;


        System.out.println("[Pinger] Starting keepalive ping");



        Utils.sleep(5 * 1000);  // A quick 5 second pause on the first one to let things settle
        grace = (keepalive * 1000 / 10); // (10%)
        while (connected) {
            next = System.currentTimeMillis() + (keepalive * 1000) - grace;
            try {
                Utils.writeBigEndLong(data, 0, System.currentTimeMillis());
                icmp.addICMPEchoListener(this, echoId);
                if (debug) {
                    System.out.println("[Pinger] Sending heartbeat ...");
                }
                icmp.sendEchoRequest(iface, address, seq, echoId, data);

            } catch (NetworkException ex) {
                System.out.println("[Pinger] Unable to send echo request");
            }
            boolean mine = false;
            while (!mine && connected) {
                ICMP6Packet p = (ICMP6Packet) packets.get(1000);

                if ((p != null)) {
                    mine = (p.getSequenceNo() == seq) && (p.getEchoId() == echoId);
                    if (debug) {
                        System.out.println("[Pinger] Got answer: " + p.getSequenceNo() + "/" + p.getEchoId() +
                                " elapsed time is: " + (System.currentTimeMillis() -
                                Utils.readBigEndLong(p.getEchoData(), 0)));
                    }
                }
                connected = System.currentTimeMillis() < next;
            }
            seq++;
            rest = next - System.currentTimeMillis();
            if (rest <= 0 ) rest = 0;

            System.out.println("[Pinger] Ping complete, Going back to sleep for " + rest);
            Utils.sleep(rest);
        }
    }

    protected void setDebug(boolean val) {
        debug = val;
    }

    public void notifyPacket(ICMP6Packet p) {
        packets.put(p);
    }

}
