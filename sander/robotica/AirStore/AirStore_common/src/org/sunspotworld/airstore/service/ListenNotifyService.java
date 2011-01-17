/*
 * Copyright 2007-2008 Sun Microsystems, Inc. All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
 * 
 * This code is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2
 * only, as published by the Free Software Foundation.
 * 
 * This code is distributed alreadyIn the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License version 2 for more details (a copy is
 * included alreadyIn the LICENSE file that accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License
 * version 2 along with this work; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA
 * 
 * Please contact Sun Microsystems, Inc., 16 Network Circle, Menlo
 * Park, CA 94025 or visit www.sun.com if you need additional
 * information or have any questions.
 *
 * Created on Mar 24, 2009 2:13:09 PM;
 */

package org.sunspotworld.airstore.service;

import com.sun.spot.peripheral.radio.RadioFactory;
import java.io.IOException;
import java.util.Vector;
import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.connections.IListenNotifyConnection;
import org.sunspotworld.airstore.connections.IListenNotifyLocalConnSvr;

/**
 * The ListenNotifyService allows applications to be notified of AirStre state changes
 * (i.e.: put, take, or replace).
 *
 * Note that "get" operations are NOT currently notified, as the state of AirStore is
 * not changed by a get. Notifying a get would require remote communciations as well,
 * and so could cause significant performance side effects (e.g.: a battery load on
 * wireless devices). Put, take or replace notifies require no such remote communciations.
 *
 * Clients can listen for a particular RecordTemplate to be notified only when a
 * matching record is put or taken in AirStore.
 *
 * Applications communicate with the ListenNotifyService via AirStore. AirStore 
 * in turn communicates "behind the scenes" so applications are normally unaware
 * this class exists.
 *
 * When the AirStoreService starts (see run()),it also starts ListenNotifyService. 
 */

public class ListenNotifyService {

    public final static int ADDLISTENER = 0;
    public final static int REMOVELISTENER = 1;
    public final static int NOTIFYLISTENERSPUT = 2;
    public final static int NOTIFYLISTENERSTAKE = 3;
    public final static int NOTIFYTAKE = 4;
    public final static int NOTIFYPUT = 5;
    public final static int NOTIFYREPLACE = 6;
    private boolean isRunning = false;
    private static boolean doPrints = false;
    IListenNotifyLocalConnSvr localSvr;   //Accepts local connection requests
    private static ListenNotifyService instance;
    private Vector listenerConnections = new Vector(); //Keeps track of all the Listeners

    public static ListenNotifyService getInstance() {
        if (instance == null) {
            instance = new ListenNotifyService();
        }
        return instance;
    }

    public void run() {
        print("ListenNotifyService Started ");
        isRunning = true;
        startListeningForLocalCommands(); // Listen for commands from elswhere in this VM (e.g.: addListener, removeListener).
    }

    void shutdown() {
        isRunning = false;
        print(" shutdown entered");
        for (int i = 0; i < listenerConnections.size(); i++) {
            IListenNotifyConnection lnc = (IListenNotifyConnection) listenerConnections.elementAt(i);
            lnc.close();
        }
        localSvr.close();
    }

    public void print(String msg) {
        if (doPrints) {
            System.out.println("[ListenNotifyService] " + msg);
        }
    }

    public static void setDoPrints(boolean b) {
        doPrints = b;
    }

    /**
     *
     * This "accepts" connections. Note it does not add the resulting connection to the
     * internal list until the client sends over the addListener message.
     *
     */
    void startListeningForLocalCommands() {
        print(" startListeningForLocalCommands() > entered");
        Thread t = new Thread() {

            public void run() {
                print("Starting local server Thread...");
                try {
                    if (RadioFactory.isRunningOnHost() || AirStore.useHostModeOnDevice()) {
                        localSvr = (IListenNotifyLocalConnSvr) Class.forName("org.sunspotworld.airstore.connections.ListenNotifyHostInProcConnSvr").newInstance();
                        print(" startListeningForLocalCommands() >            ...instantiated on host version, localSvr = " + localSvr);
                    } else {
                        localSvr = (IListenNotifyLocalConnSvr) Class.forName("org.sunspotworld.airstore.connections.ListenNotifyIICConnSvr").newInstance();
                        print(" startListeningForLocalCommands() >           ...instantiated on SPOT version, localSvr = " + localSvr);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex.toString());
                }
                while (isRunning()) {
                    IListenNotifyConnection conn = null;
                    conn = localSvr.listenaccept();
                    // New connection. But do not add it to our internal list
                    // until the add listener command arrives.
                    print("Server side: accepted a connection");
                    startLocalConnThread(conn);
                }
                print(" startListeningForLocalCommands() exited");
            }
        };
        t.start();
    }

    public void startLocalConnThread(final IListenNotifyConnection conn) {
        final ListenNotifyService self = this;
        Thread t = new Thread() { public void run() {
                print("Server side:startLocalConnThread> run() > Local Connection Thread started.");
                print("startLocalConnThread> run() > Local Connection Thread started.");
                boolean connected = true;
                while (connected && isRunning()) {
                    try {
                        conn.readAndForwardNextCommandServerSide(self);
                    } catch (IOException ex) {
                        connected = false;
                        System.err.println("[IAsirStoreService] Encountered " + ex + ", closing connection");
                    }
                }
                conn.close();
                print(" startLocalConnThread> run() > Local Connection closed, thread exited.");
            } ; };
        t.start();
    }

    /**
     * A listener is represented in this class as an implementor of IListenNotifyConnection
     * @param lnc Representing the listener, a channel for communicating with the listener
     * @param t   The template with chich to filter notifications.
     */
    public void addListener(IListenNotifyConnection lnc, RecordTemplate t) {
        lnc.setRecordTemplate(t);           //Each connection has a single record template.
        listenerConnections.addElement(lnc);
        print("added a listener" + lnc + " with template = " + t);
    }

    public void removeListener(IListenNotifyConnection lnc) {
        if(listenerConnections.contains(lnc)) {
            listenerConnections.removeElement(lnc);
            print("removeListener() removed " + lnc);
        } else {
            print("removeListener() ... no such listener present, NO-OP.");
        }
    }

    /**
     *  Notify interested applications that a Put occurred.
     */
    public void notifyListenersPut(Record r) {
        for (int i = 0; i < listenerConnections.size(); i++) {
            IListenNotifyConnection lnc = (IListenNotifyConnection) listenerConnections.elementAt(i);
            if(lnc.getRecordTemplate().matches(r)) lnc.notifyPut(r);
        }
    }

    /**
     *  Notify interested applications that a Take occurred.
     */
    public void notifyListenersTake(Record r) {
        for (int i = 0; i < listenerConnections.size(); i++) {
            IListenNotifyConnection lnc = (IListenNotifyConnection) listenerConnections.elementAt(i);
            if(lnc.getRecordTemplate().matches(r)) lnc.notifyTake(r);
        }
    }

    /**
     * In a replace(), the out and in records have identical sets of keys.
     * This is logically equivalent to a notifyTake(out) and notifyPut(in).
     * It is thus an optimiaztion, one that is welcomed, for example, by certain GUI clients.
     *
     * Most application will not be interested in the distinciotn between getting this event and
     * getting a notifyTake(out) and a notifyPut(in). They can simply implement
     * notifyReplace() with calls to notifyTake(out) and notifyPut(in).
     *
     * @param out the record being replaced
     * @param in the noew record.
     */
     public void notifyListenersReplace(Record out, Record in) {
        print("notifying interested among " + listenerConnections.size() + " listeners replace(" + out + " replaced by " + in + ")");
        for (int i = 0; i < listenerConnections.size(); i++) {
            IListenNotifyConnection lnc = (IListenNotifyConnection) listenerConnections.elementAt(i);
            if(lnc.getRecordTemplate().matches(out)){
                print("     .......... found an interested listener, notifying of replace(,)");
                lnc.notifyReplace(out, in);
            }
        }
     }

    public boolean isRunning() {
        return isRunning;
    }
}
 
         
      



