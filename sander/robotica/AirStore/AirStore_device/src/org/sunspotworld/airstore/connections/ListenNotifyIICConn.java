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
 * Created on March 24, 2009 2:13:09 PM;
 */

package org.sunspotworld.airstore.connections;

import com.sun.spot.util.Utils;
import com.sun.squawk.Isolate;
import com.sun.squawk.io.mailboxes.AddressClosedException;
import com.sun.squawk.io.mailboxes.ByteArrayEnvelope;
import com.sun.squawk.io.mailboxes.Channel;
import com.sun.squawk.io.mailboxes.Envelope;
import com.sun.squawk.io.mailboxes.MailboxClosedException;
import com.sun.squawk.io.mailboxes.NoSuchMailboxException;
import java.io.*;
import org.sunspotworld.airstore.IAirStoreListener;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.utils.DataOutputStreamIIC;
import org.sunspotworld.airstore.service.ListenNotifyService;

/**
 * This channel is used in BOTH directions, so the API is a sum
 * of what it might be if there were seperate Client and Server side classes.
 *
 * It will help to understand which side (the ListenNotifyService side, or the
 * listener (client) side) is invoking which method.
 *
 * @author randy
 */
public class ListenNotifyIICConn implements IListenNotifyConnection {

    private static boolean doPrints = false;
    private Channel channel;
    private IAirStoreListener listener;
    private RecordTemplate template;
    private DataOutputStreamIIC dataout;

    public ListenNotifyIICConn() {
    }

    /**
     * Used by the ListenNotifyServie upon an accept, to construct the connection.
     * @param c
     */
    public ListenNotifyIICConn(Channel c) {
        channel = c;
        if (doPrints) {
            System.out.println("[ListenNotifyIICConnection] new() on channel = " + c);
        }
    }

    /**
     * @return   doPrints -- for debugging or other monitoring
     */
    public static boolean getDoPrints() {
        return doPrints;
    }

    /**
     * @param b  -- for debugging or other monitoring
     */
    public static void setDoPrints(boolean b) {
        doPrints = b;
    }

    /**
     * Print a message only when doPrints is set true.
     *
     * @param msg Message to appear on standard out.
     */
    public void print(String msg) {
        if (doPrints) {
            String[] tokens = Utils.split(getClass().getName(), '.');
            String cn = tokens[tokens.length - 1];
            System.out.println("[" + cn + "] " + msg);
        }
    }

    /**
     * Creates a DataInputStream around a single reply envelope. That
     * envelope is the next one returned by receiving off the IIC channel.
     *
     * Also: Lazily start the AirStoreService on this device if we find we are
     * unable to communicate.
     *
     * @return DataInputStream wrapped around the next reply envelope.
     */
    public DataInput readNextEnvelopeCreateStream() {
        Envelope env = null;
        try {
            env = getChannel().receive();// Hangs here until message arrives
        } catch (AddressClosedException ex) {
            ex.printStackTrace();
        } catch (MailboxClosedException ex) {
            ex.printStackTrace();
        }
        ByteArrayEnvelope dataEnv = (ByteArrayEnvelope) env;
        byte[] b = dataEnv.getData();
        print("readNextEnvelopeCreateStream() > got " + b.length + " bytes");
        return new DataInputStream(new ByteArrayInputStream(b));
    }

    /**
     * In theory this is no longer called as AirStoreService stars the ListenNotifyService
     * after it has started.
     *
     * FIX remove this method(?)
     */
    public void tryStartingAirStoreService() {
        print(" tryStartingListenNotifyService()> entered");
        Isolate iso = new Isolate("org.sunspotworld.airstore.service.AirStoreService",
                new String[0],
                null,
                Isolate.currentIsolate().getParentSuiteSourceURI());
        print(" tryStartingListenNotifyService()> starting isolate....");
        iso.start();
        print(" tryStartingListenNotifyService()>  ...isolate started");
        //Try hard to make sure the new context gets a chance to run...
        print(" tryStartingListenNotifyService()>  yield then sleep 5 sec....");
        Thread.yield();
        Utils.sleep(5000);
        print(" tryStartingListenNotifyService()> .... done sleeping, returning");
    }

    /**
     * wraps a stream around our channel, jsut so we can write data onto the channel.
     * @return
     */
    public DataOutputStreamIIC getDataOut() {
        if (dataout == null) {
            dataout = new DataOutputStreamIIC(getChannel());
        } else {
            print(" getDataOut()> dataout not null");
        }
        return dataout;
    }

    /**
     * Returns the channel, if channel is null, creates and sets channel to the default.
     * This normally happens on the client side.
     */
    public Channel getChannel() {
        if (channel == null) {
            setDefaultClientChannel();
        }
        return channel;
    }

    /**
     *  Creates and sets the channel, and if connection attempt failed, tries to
     *  start AirStoreService and reconnect. This should not ever happen.
     *
     */
    public void setDefaultClientChannel() {
        print(" setDefaultClientChannel()> entered");
        try {
            channel = Channel.lookup(ListenNotifyIICConnSvr.ListenNotifyMAILBOX_NAME);
        } catch (NoSuchMailboxException ex) {
            print(" setDefaultClientChannel()> no such mailbox, starting service = ");
            System.err.println("Unable to connect to IIC chanel. Atrting AirStore in desperation.");
            tryStartingAirStoreService();
            try {
                print(" setDefaultClientChannel()> service supposedly going, trying again to lookup");
                channel = Channel.lookup(ListenNotifyIICConnSvr.ListenNotifyMAILBOX_NAME);
                print(" setDefaultClientChannel()> second lookup worked, giving channel " + channel.getClass().getName());
            } catch (NoSuchMailboxException ex1) {
                ex1.printStackTrace();
                print(" setDefaultClientChannel()> second try failed");
            }
        }
    }

    /**
     * This is invoked on the Client Side. It is typically the first message, and
     * will lead to the creation of the communications infrastructure inside this
     * object.
     *
     * @param
     */
    public void addListener(IAirStoreListener a, RecordTemplate t) {
        listener = a;
        template = t;
        try {
            getDataOut().writeByte(ListenNotifyService.ADDLISTENER);
            t.writeOn(getDataOut());
            getDataOut().flush();
            listenForServerCommands();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This is invoked on the Client Side
     *
     * @param
     */
    public void removeListener() {
        try {
            getDataOut().writeByte(ListenNotifyService.REMOVELISTENER);
            getDataOut().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            //close(); this causes an error, as the far end has not yet read our last message...
            //FIX How to close an IIC connection when it is done. This is a bug in IIC connection
        }
    }

    /**
     * Waits for then reads next IIC envelope, and then calls the corresponding method in ListenNotifyService
     *
     * @param 
     */
    public void readAndForwardNextCommandServerSide(ListenNotifyService s) throws IOException {
        print(" readAndForwardNextCommand > entered.");
        DataInput envelopeStream = readNextEnvelopeCreateStream();
        byte cmd = envelopeStream.readByte();
        switch (cmd) {
            case ListenNotifyService.ADDLISTENER:
                print(" readAndForwardNextCommand > case ADDLISTENER > entered.");
                RecordTemplate t2 = (RecordTemplate) RecordTemplate.readFrom(envelopeStream);
                print(" readAndForwardNextCommand > case ADDLISTENER > calling addListener() on IAirStore implementor " + s);
                s.addListener(this, t2); // Came in over IIC so is local
                break;
            case ListenNotifyService.REMOVELISTENER:
                print(" readAndForwardNextCommand > case REMOVELISTENER > entered.");
//                RecordTemplate t3 = (RecordTemplate) RecordTemplate.readFrom(envelopeStream);
                print(" readAndForwardNextCommand > case ADDLISTENER > calling addListener() on IAirStore implementor " + s);
                s.removeListener(this);
                break;
            default:
                System.err.println("[AirStoreIICConnection] Got goofy command byte = " + cmd + " so did nothing");
        }
    }

    /**
     * Waits for then reads next IIC envelope, and then calls the corresponding method in the listener
     *
     * @param
     */
    public void readAndForwardNextCommandClientSide(IAirStoreListener listener) throws IOException {
        DataInput envelopeStream = readNextEnvelopeCreateStream();
        byte cmd = envelopeStream.readByte();
        Record r, out, in;
        switch (cmd) {
            case ListenNotifyService.NOTIFYPUT:
                r = (Record) Record.readFrom(envelopeStream);
                listener.notifyPut(r);
                break;
            case ListenNotifyService.NOTIFYTAKE:
                r = (Record) Record.readFrom(envelopeStream);
                listener.notifyTake(r);
                break;
            case ListenNotifyService.NOTIFYREPLACE:
                out = (Record) Record.readFrom(envelopeStream);
                in  = (Record) Record.readFrom(envelopeStream);
                listener.notifyReplace(out,in);
                break;
            default:
                System.err.println("[AirStoreIICConnection] Got goofy command byte = " + cmd + " so did nothing");
        }
    }

    /**
     * This is invoked on the Server Side
     *
     * @param
     */
    public void notifyPut(Record arg1) {
        try {
            getDataOut().writeByte(ListenNotifyService.NOTIFYPUT);
            arg1.writeOn(getDataOut());
            getDataOut().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This is invoked on the Server Side
     *
     * @param
     */
    public void notifyTake(Record arg0) {
        try {
            getDataOut().writeByte(ListenNotifyService.NOTIFYTAKE);
            arg0.writeOn(getDataOut());
            getDataOut().flush();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This is invoked on the Server Side
     *
     * @param
     */
    public void notifyReplace(Record out, Record in) {
        try { 
            getDataOut().writeByte(ListenNotifyService.NOTIFYREPLACE);
            out.writeOn(getDataOut());
            in.writeOn(getDataOut());
            getDataOut().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * This is invoked from addlistener method
     *
     * @param
     */
    public void listenForServerCommands() {
        (new Thread() { public void run() {
                print("startLocalConnThread> run() > Local Connection Thread started.");
                boolean connected = true;
                while (connected) {
                    try {
                        readAndForwardNextCommandClientSide(getListener());
                    } catch (IOException ex) {
                        connected = false;
                        System.err.println("[ListenNotifyService] Closing connection: encountered " + ex);
                        //ex.printStackTrace();
                    }
                }
                close();
                print(" startLocalConnThread> run() > Local Connection closed, thread exited.");
         };}).start();
    }

    public void close() {
        getChannel().close();
    }


    /**
     * @return the listener
     */
    public IAirStoreListener getListener() {
        return listener;
    }

    /**
     * @return the template
     */
    public RecordTemplate getRecordTemplate() {
        return template;
    }

    public void setRecordTemplate(RecordTemplate t){
        template = t;
    }
     public String toString(){
        return "a ListenNotifyIICConn";
    }

}

