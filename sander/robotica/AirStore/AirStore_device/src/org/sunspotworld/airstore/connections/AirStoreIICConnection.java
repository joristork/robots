/*
 * AirStoreIICConnection.java
 *
 * Created on Nov 24, 2008 4:41:07 PM;
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
import java.util.Vector;
import org.sunspotworld.airstore.IAirStore;
//import org.sunspotworld.airstore.connections.IAirStoreConnection;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.service.AirStoreService;
import org.sunspotworld.airstore.utils.DataOutputStreamIIC;

/**
 * This class does TWO distinct functions, and perhaps could be split.
 * It acts as the "service side" end of a connection. Info from the Service to
 * all the clients pass over this conncetion, PLUS there is a read hanging for
 * each client.
 *
 * It also acts like a "cleint side" end of a coonection. The client issues AirStore commands
 * and they get pushed over this connection to the actual service.
 * 
 */
public class AirStoreIICConnection implements IAirStoreConnection  {

    private static boolean doPrints = false;

    private Channel channel;
    private IAirStore airStoreService; //Upstream service with which to forward info.
    private DataOutputStreamIIC dataout;

    public AirStoreIICConnection(){
    }

    public AirStoreIICConnection(Channel c){
        channel = c;
        if(doPrints) System.out.println("[AirStoreIICConnection] new() on channel = " + c);
    }

    /**
     * @return   doPrints -- for debugging or other monitoring
     */
    public static boolean getDoPrints() {
        return doPrints;
    }

    /**
     * @param aDoPrints  -- for debugging or other monitoring
     */
    public static void setDoPrints(boolean aDoPrints) {
        doPrints = aDoPrints;
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
     * request that we are notified when the service is up to date.
     */
    public void assureServiceStartedAndUpToDate() {
        try {
            //The getDataOut() will lead to starting AirStoreServie, if necessary.
            getDataOut().writeByte(AirStoreService.REQ_NOTIFY_WHEN_READY);
            getDataOut().flush();
            //The next call will hang until AirStoreService is up to date.
            DataInput envelopeRdStream = readNextEnvelopeCreateStream();
            int reply = envelopeRdStream.readInt();
            if(reply != AirStoreService.NOTIFY_UP_TO_DATE){
                System.err.println("What the &$#@ !? Expected AirStoreService to reply with " +
                        AirStoreService.NOTIFY_UP_TO_DATE +
                        " but got " + reply);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 
     * AirStoreService calls this when it has just become up to date. This connection was on a list waiting
     * for such notification.
     */
    public void notifyUpToDate() {
        try {
            getDataOut().writeInt(AirStoreService.NOTIFY_UP_TO_DATE);
            getDataOut().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
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

    public void tryStartingAirStoreService() {
        print(" tryStartingAirStoreService()> entered");
        Isolate iso = new Isolate("org.sunspotworld.airstore.service.AirStoreService",
                                  new String[0],
                                  null,
                                  Isolate.currentIsolate().getParentSuiteSourceURI());
        print(" tryStartingAirStoreService()> starting isolate....");
        iso.start();
        print(" tryStartingAirStoreService()>  ...isolate started");
        //Try hard to make sure the new context gets a chance to run...
        print(" tryStartingAirStoreService()>  yield then sleep 5 sec....");
        Thread.yield();
        Utils.sleep(5000);
        print(" tryStartingAirStoreService()> .... done sleeping, returning");
    }

    public DataOutputStreamIIC getDataOut() {
        if (dataout == null) {
            print(" getDataOut()> dataout == null. Instantiating a new DataOutputStreamIIC on channel = ");
            dataout = new DataOutputStreamIIC(getChannel());
        } else {
            print(" getDataOut()> dataout not null");
        }
        return dataout;
    }

    public void close() {
        getChannel().close();
    }

    /**
     * The AirStoreService calls this from within a thread it creates, with itself as the argument.
     * The purpose is to get messages coming in from the client side of this connection, and forward
     * them to the AirStoreService.
     * 
     * This method hangs on a read. When it can proceed it parses the command and calls the
     * corresponding method on the argument.
     * 
     * @param a
     * @throws java.io.IOException
     */
    public void readAndForwardNextCommand(IAirStore a) throws IOException {
        print(" readAndForwardNextCommand > entered.");
        DataInput envelopeStream = readNextEnvelopeCreateStream();
        byte cmd = envelopeStream.readByte();
        switch (cmd) {
            case AirStoreService.PUT:
                print(" readAndForwardNextCommand > case PUT > entered.");
                Record r = Record.readFrom(envelopeStream);
                print(" readAndForwardNextCommand > case PUT > calling doPut() on IAirStore implementor " + a);
                a.doPut(r, true); // Came in over IIC so is local
                break;
            case AirStoreService.GETALLMATCHES:
                print(" readAndForwardNextCommand > case GETALLMATCHES > entered.");
                RecordTemplate t1 = (RecordTemplate) RecordTemplate.readFrom(envelopeStream);
                print(" readAndForwardNextCommand > case GETALLMATCHES > calling doGetAllMatches() on IAirStore implementor " + a);
                Vector v = a.doGetAllMatches(t1);
                print(" readAndForwardNextCommand > case GETALLMATCHES > got  " + v.size() + " matches. Wiriting that int to getDataOut()");
                getDataOut().writeInt(v.size());
                for (int i = 0; i < v.size(); i++) {
                    Record rcd = (Record) v.elementAt(i);
                    print(" readAndForwardNextCommand > case GETALLMATCHES > about to write  " + rcd + " on getDataOut()");
                    rcd.writeOn(getDataOut());
                }
                getDataOut().flush();
                break;
            case AirStoreService.TAKEALLMATCHES:
                print(" readAndForwardNextCommand > case TAKEALLMATCHES > entered.");
                RecordTemplate t2 = (RecordTemplate) RecordTemplate.readFrom(envelopeStream);
                print(" readAndForwardNextCommand > case TAKEALLMATCHES > calling doTakeAllMatches() on IAirStore implementor " + a);
                Vector vec = a.doTakeAllMatches(t2, true); //True indicating local origin, thus requiring a broadcast;
                print(" readAndForwardNextCommand > case TAKEALLMATCHES > got  " + vec.size() + " matches. Wiriting that int to getDataOut()");
                getDataOut().writeInt(vec.size());
                for (int i = 0; i < vec.size(); i++) {
                    Record rcd = (Record) vec.elementAt(i);
                    print(" readAndForwardNextCommand > case TAKEALLMATCHES > about to write  " + rcd + " on getDataOut()");
                    rcd.writeOn(getDataOut());
                }
                getDataOut().flush();
                break;
            case AirStoreService.REQ_NOTIFY_WHEN_READY:
                //This command only comes to a service
                AirStoreService svc = (AirStoreService) a;
                svc.addUpToDateWaiter(this);
                break;
            default:
                System.err.println("[AirStoreIICConnection] Got goofy command byte = " + cmd + " so did nothing");
        }
    }

    /**
     * Client side methods
     *
     * @param r
     */
    public void doPut(Record r, boolean isLocal) {
        if(! isLocal) throw new RuntimeException("AirStore IIC copnenction got a non-local put");
        try {
            print(" doPut()> entered. Will write then flush.");
            getDataOut().writeByte(AirStoreService.PUT);
            r.writeOn(getDataOut());
            getDataOut().flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Vector doGetAllMatches(RecordTemplate rt) {
        print(" doGetAllMatches (" + rt + ") entered. Will write then flush");
        Vector reply = new Vector();
        try {
            getDataOut().writeByte(AirStoreService.GETALLMATCHES);
            rt.writeOn(getDataOut());
            getDataOut().flush();

            print(" doGetAllMatches (" + rt + ") > sent GETALLMATCHES command, will now await an Envelope.");
            DataInput envelopeRdStream = readNextEnvelopeCreateStream();
            print(" doGetAllMatches (" + rt + ") > got a DataInput back. Will read int and then that many Records ");
            int size = envelopeRdStream.readInt();
            print(" doGetAllMatches (" + rt + ") > size was  "+ size);
            for (int i = 0; i < size; i++) {
                Record rec = Record.readFrom(envelopeRdStream);
                reply.addElement(rec);
                print(" doGetAllMatches (" + rt + ") > created record  "+ rec);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        print(" doGetAllMatches (" + rt + ") > returning  " + reply + " of size " + reply.size());
        return reply;
    }

    public Vector doTakeAllMatches(RecordTemplate rt, boolean isLocal) {
        print(" doTakeAllMatches (" + rt + ") > entered. Will write then flush");
        Vector reply = new Vector();
        try {
            getDataOut().writeByte(AirStoreService.TAKEALLMATCHES);
            rt.writeOn(getDataOut());
            getDataOut().flush();

            print(" doTakeAllMatches (" + rt + ") > sent TAKEALLMATCHES command, will now await an Envelope.");
            DataInput envelopeRdStream = readNextEnvelopeCreateStream();
            print(" doTakeAllMatches (" + rt + ") > got a DataInput back. Will read int and then that many Records ");
            int size = envelopeRdStream.readInt();
            print(" doTakeAllMatches (" + rt + ") > size was  "+ size);
            for (int i = 0; i < size; i++) {
                Record rec = Record.readFrom(envelopeRdStream);
                reply.addElement(rec);
                print(" doTakeAllMatches (" + rt + ") > created record  "+ rec);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        print(" doTakeAllMatches (" + rt + ") > returning  "+ reply+ " of size " + reply.size());
        return reply;
    }

    public Channel getChannel(){
        if(channel == null) {
            setDefaultClientChannel();
        }
        return channel;
    }

    /**
     * Establish the basic IIC connection.
     *
     * Also: Lazily start the AirStoreService on this device if we find we are
     * unable to communicate. Start it and try a second time.
     */
    public void setDefaultClientChannel() {
        print(" setDefaultClientChannel()> entered");
        try {
            channel = Channel.lookup(AirStoreIICConnSvr.MAILBOX_NAME);
            print(" setDefaultClientChannel()> created channel = " + channel.getClass().getName());
        } catch (NoSuchMailboxException ex) {
            print(" setDefaultClientChannel()> no such mailbox, starting service = " );
            tryStartingAirStoreService();
            try {
                print(" setDefaultClientChannel()> service supposedly going, trying again to lookup" );
                channel = Channel.lookup(AirStoreIICConnSvr.MAILBOX_NAME);
                print(" setDefaultClientChannel()> second lookup worked, giving channel " + channel.getClass().getName() );
            } catch (NoSuchMailboxException ex1) {
                ex1.printStackTrace();
                print(" setDefaultClientChannel()> second try failed" );
            }
        }
    }

    public void notifyListenersPut(Record arg0) {
    }

    public void notifyListenersTake(RecordTemplate arg0) {
    }

    public void addListener(Channel arg0, RecordTemplate arg1) {
    }

    public void removeListener(RecordTemplate arg0) {
    }


}
