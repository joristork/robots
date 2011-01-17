/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.airstore.service;

import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;
import javax.microedition.io.Connector;
import javax.microedition.io.Datagram;
import javax.microedition.io.DatagramConnection;
import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.updating.DeferredCommand;
import org.sunspotworld.airstore.IAirStore;
import org.sunspotworld.airstore.connections.IAirStoreLocalConnSvr;
import org.sunspotworld.airstore.connections.IAirStoreConnection;
import org.sunspotworld.airstore.records.KeyNotFoundException;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordEntry;
import org.sunspotworld.airstore.records.RecordTemplate;

/**
 * AirStoreService implements the actual storage of Records. Applications communicate
 * with the Service via thier own instanc of AirStore. AirStore communicates "behind the scenes"
 * so applications are probably unaware this class even exists.
 *
 * When the service starts (see run()) it will of course contain no data, and so
 * needs to find out what is already in the air first. Hence the boolean isUpToDate is critical.
 * See requestUpdate() and startUpdateCheckThread(). Note that the calls to
 * the core API all hang on a wait if the isUpToDate is false.
 *
 *
 * @author randy
 */
public class AirStoreService implements IAirStore {


    public final static int PUT = 0;
    public final static int GETALLMATCHES = 1;
    public final static int TAKEALLMATCHES = 2;
    public final static int REQ_NOTIFY_WHEN_READY = 3;
    public final static int NOTIFY_UP_TO_DATE = 4;
    static int AIRSTORE_PORT = 124;
    private static boolean doPrints = false;
    private static AirStoreService instance;
    private static String addressString = null;
    private static boolean replaceFlag = false;

    private DatagramConnection rmtIn;         //  listens for data    (from radio)
    private DatagramConnection rmtOut;        //  broadcasts reports  (over radio)
    private IAirStoreLocalConnSvr localSvr;   //  Accepts local connection requests

    private Vector connections = new Vector(); // IAirStoreConnection instances. We collect them here so we can close them un shutdown.

    private Vector upToDateWaiters = new Vector(); //Connections that have requested to be notified when this service is up to date.

    private boolean isRunning = false;
    private boolean isUpToDate = false;     // Has been updated or not, see startUpdateCheckThread() and requestUpdate()
    private Vector records = new Vector();  // This is it, the actual data.
    int updateWaitCount = 0;                // incremented at the beginning, if no update is received after 3 tries, we start empty.
    private final Object upToDateMonitor    // This is used to wait for puts and gets until isUpToDate.
            = new Object();
    private DeferredCommand deferredCommands; //When awaiting an update, incomming commands from local clients are put in this linked list.
    
    public static AirStoreService getInstance() {
        if (instance == null) {
            instance = new AirStoreService(); 
        }
        return instance;
    }

    ////// CORE API
    //
    //

    /**
     *  Remove any entries with matching keys, then add the new record.
     *  NOTE: This policy should be made more flexible, to allow versions of similar
     *  records to stay around, or to allow records to stay a fized amount of time, etc, etc.
     */
    public void doPut(Record r, boolean isLocal) {
        print(" doPut(" + r + ") LOCAL ORIGIN = " + isLocal);
        if(isLocal && ! isUpToDate) {
            DeferredCommand dc = new DeferredCommand(PUT, r);
            addDeferredCommand(dc);
            dc.hang();
            return;
        }
        basicDoPut(r, isLocal);
    }

    public synchronized void basicDoPut(Record r, boolean isLocal){
        //Build an appropriate template for matching this record's keys.
        RecordTemplate rt = new RecordTemplate();
        Vector entries = r.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            RecordEntry re = (RecordEntry) entries.elementAt(i);
            rt.add(re.getKey());
        }

        Vector recs = new Vector();
        for (int i = 0; i < records.size(); i++) {
            recs.addElement(records.elementAt(i));
        }
        replaceFlag = false;
        //Use the above array to iterate, and remove any matching records.
        for (int i = 0; i < recs.size(); i++) {
            Record rec = (Record) recs.elementAt(i);
            if(rt.matches(rec)) {
                records.removeElement(rec);
                print("************************* doPut() > removing a record with matching keys: " + rec );
                replaceFlag = true;
                if(isUpToDate) ListenNotifyService.getInstance().notifyListenersReplace(rec,r);
            }
        }

        //Records with matching keys have been removed. Now add the record.
        records.addElement(r);

        //If PUT arises from the device sharing this radio, must notify other
        //participants over radio. If arising from over the air (some othe radio),
        // we must NOT notify other participants over radio!
        if(isLocal) broadcastPut(r);

        if (!replaceFlag && isUpToDate)
        ListenNotifyService.getInstance().notifyListenersPut(r);
    }
    //
    public void broadcastPut(Record r){
        print("*************************  doPut() > broadcasting: doPut( " + r + ")");
        try {
            Datagram dg = rmtOut.newDatagram(rmtOut.getMaximumLength());
            dg.writeByte(PUT);
            r.writeOn(dg);
            rmtOut.send(dg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(doPrints) printOut();
        print("");
    }
    //
    /**
     *  Get a vector of all the matches, without removing them from AirStore.
     *  HACK? if the record is null, return everything.
     *
     *  NOTE NOTE NOTE: Needs a isLocal flag so that during startup It can hang
     *  for local requests, which are likely misleading.
     *
     *  For now, assume ALL invocations are local, and therefore all hang until up to date.
     *
     * @param t
     * @return a vector of the matching Records
     */
    public  Vector doGetAllMatches(RecordTemplate t) {
        if(! isUpToDate) {
            DeferredCommand dc = new DeferredCommand(GETALLMATCHES, t);
            System.out.println("Adding a deferred command: " + dc);
            addDeferredCommand(dc);
            return dc.hang();
        }
        return basicDoGetAllMatches(t);
    }
    //
    public synchronized Vector basicDoGetAllMatches(RecordTemplate t) {
        Vector v = new Vector();
        if (t == null) {
            for (int i = 0; i < records.size(); i++) {
                v.addElement(records.elementAt(i));
            }
        } else {
            for (int i = 0; i < records.size(); i++) {
                Record r = (Record) records.elementAt(i);
                if (t.matches(r)) {
                    print(" doGetAllMatches(" + t + ") found a match.");
                    v.addElement(r);
                }
            }
        }
        //if(v.size() == 0) print("doGetAllMatches(" + t + ") + found no matches at all. Returning an empty result.");
        return v;
    }
    //
    public  Vector doTakeAllMatches(RecordTemplate t, boolean isLocal) {
//        if (isLocal) waitOnUpToDateMonitor(); //Don't want to give the wrong answer.
        if(isLocal && ! isUpToDate) {
            DeferredCommand dc = new DeferredCommand(TAKEALLMATCHES, t);
            addDeferredCommand(dc);
            return dc.hang();
        }
        return basicDoTakeAllMatches(t, isLocal);
    }
    //
    public synchronized Vector basicDoTakeAllMatches(RecordTemplate t, boolean isLocal) {
        print(" doTakeAllMatches (" + t + ")");
        Vector result = new Vector();
        Vector rv = new Vector();
        for (int i = 0; i < records.size(); i++) {
            rv.addElement(records.elementAt(i));
        }
        for (int i = 0; i < rv.size(); i++) {
            Record r = (Record) rv.elementAt(i);
            print("basicDoTakeAllMatches(" + t + ") comparing with " + r + " result " + t.matches(r));
            if (t.matches(r)) {
                print(" basicDoTakeAllMatches(" + t + ") found a match " + r + " and removing it");
                boolean removed = records.removeElement(r);
                if(! removed) System.err.println("basicDoTakeAllMatches(" + t + ") Hey, remove did'nt happen! ");
                result.addElement(r);
            }
        }
        if(result.size() == 0) print("basicDoTakeAllMatches(" + t + ") + found no matches at all. Returning an empty result.");
        if(doPrints) printOut();
        print("");
        if(isLocal) broadcastTakeAllMatches(t);
        for (int i = 0; i < result.size(); i++) {
            ListenNotifyService.getInstance().notifyListenersTake((Record) result.elementAt(i));
        }
        return result;
    }
    //
    public void broadcastTakeAllMatches(RecordTemplate t){
        try {
            Datagram dg = rmtOut.newDatagram(rmtOut.getMaximumLength());
            dg.writeByte(TAKEALLMATCHES);
            t.writeOn(dg);
            rmtOut.send(dg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(doPrints) printOut();
        print("");
    }
    //
    //
    ///// END CORE API


    public void run() {
        ListenNotifyService.getInstance().run();

        addressString = IEEEAddress.toDottedHex(RadioFactory.getRadioPolicyManager().getIEEEAddress());

        try {
            print(" openning out connection...");
            rmtOut = (DatagramConnection) Connector.open("radiogram://broadcast:" + AIRSTORE_PORT);
            print(" ... out connection openned");
            print(" openning in connection ...");
            rmtIn = (DatagramConnection) Connector.open("radiogram://:" + AIRSTORE_PORT);
            print("  ... in connection openned");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        isRunning = true;
        print(" run() > requesting update");
        requestUpdate();
        print(" run() > done requesting update");
        print(" run() > start update check thread");
        startUpdateCheckThread();
        print(" run() > done with update check thread");
        print(" run() > start listening for remote commands");
        startListeningForRmtCommands();  //...i.e.: from radio
        print(" run() > done: listening for remote commands should be started");
        print(" run() > start listening for local commands");
        startListeningForLocalCommands();//..i.e.; from connections
        print(" run() > done: listening for local commands should be started");
    }

    public void shutdown() {
        isRunning = false;
        for (int i = 0; i < connections.size(); i++) {
            ((IAirStoreConnection) connections.elementAt(i)).close();
        }
        localSvr.close();
        try {
            rmtIn.close();
            rmtOut.close();
            ListenNotifyService.getInstance().shutdown();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void addDeferredCommand(DeferredCommand dc){
        if(deferredCommands == null) {
            deferredCommands = dc;
        }
        else {
            deferredCommands.addLast(dc);
        }
    }

    public void executeDeferredCommands(){
        if( deferredCommands != null) deferredCommands.proceed();
    }

    public void waitOnUpToDateMonitor(){
        if (!isUpToDate) {
            synchronized (upToDateMonitor) {
                //See javadoc as to why this while is here..not logically required.
                while (!isUpToDate) {
                    try {
                        upToDateMonitor.wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public static void setDoPrints(boolean b) {
        doPrints = b;
    }

    public void print(String msg) {
        if (doPrints) {
            System.out.println("[AirStoreService] " + msg);
        }
    }

    public void printOut() {
        System.out.println("[AirStoreService]  __________ AirStore current state __(" + records.size() + " records)________");
        System.out.println("[AirStoreService] |");
        if (doPrints) {
            for (int i = 0; i < records.size(); i++) {
                System.out.println("[AirStoreService] | " + records.elementAt(i));
                //((Record) records.elementAt(i)).print();
            }
        }
        System.out.println("[AirStoreService] |");
        System.out.println("[AirStoreService] |______________________________________________________");
    }

    void startListeningForRmtCommands() {
        Thread t = new Thread() {

            public void run() {
                // print(" startListeningForRmtCommands> Starting remote radio listener Thread");
                Datagram dg;
                while (isRunning()) {
                    try {
                        dg = rmtIn.newDatagram(rmtIn.getMaximumLength());
                        rmtIn.receive(dg);
                        print("startListeningForRmtCommands() > inner Thread run() > $$$$$$$$$$$$$$$$$$$$$$$$$ received a broadcast datagram ");
                        byte command = dg.readByte();
                        switch (command) {
                            case PUT:
                                // print("startListeningForRmtCommands > got a PUT");
                                doPut(Record.readFrom(dg), false); //This came in over radio so is not local
                                break;
                            case GETALLMATCHES:
                                print("Got GETALLAMATCHES command from radio, don't know what to do with it.");
                                break;
                            case TAKEALLMATCHES:
                                doTakeAllMatches((RecordTemplate) RecordTemplate.readFrom(dg), false); //This came in over radio so is not local
                                break;
                            default:
                        }
                    } catch (IOException ex) {
                        if(ex.getMessage().indexOf("closed") == -1 ){
                           ex.printStackTrace();
                        }else {
                            print("startListeningForRmtCommands() > inner Thread run() > DatagramConnection spontaneously closed");
                        }
                    }
                }
                print(" startListeningForRmtCommands() thread exiting");
            }
        };
        t.start();
    }

    void startListeningForLocalCommands() {
        Thread t = new Thread() {

            public void run() {
                print("Starting local server Thread...");
                try {
                    if (RadioFactory.isRunningOnHost() || AirStore.useHostModeOnDevice()) {
                        localSvr = (IAirStoreLocalConnSvr) Class.forName("org.sunspotworld.airstore.connections.AirStoreHostInProcConnSvr").newInstance();
                        print(" startListeningForLocalCommands() >            ...instantiated on host version, localSvr = " + localSvr);
                    } else {
                        localSvr = (IAirStoreLocalConnSvr) Class.forName("org.sunspotworld.airstore.connections.AirStoreIICConnSvr").newInstance();
                        print(" startListeningForLocalCommands() >           ...instantiated on SPOT version, localSvr = " + localSvr);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex.toString());

                }
                while (isRunning) {
                    IAirStoreConnection conn = null;
                  //  print(" startListeningForLocalCommands > about to hang to call localSvr.accept()");
                    conn = localSvr.accept();
                    startLocalConnThread(conn);
                }
                print(" startListeningForLocalCommands() thread exiting");
            }
        };
        t.start();
    }

    public void startLocalConnThread(final IAirStoreConnection conn) {
        final AirStoreService self = this;
        Thread t = new Thread() {  public void run() {
                print("startLocalConnThread> run() > Local Connection Thread started.");
                boolean connected = true;
                while (connected && isRunning()) {
                    try {
                        conn.readAndForwardNextCommand(self);
                    } catch (IOException ex) {
                        connected = false;
                        System.err.println("[IAsirStoreService] Error, closing connection.");
                        ex.printStackTrace();
                    }
                }
                conn.close();
                print(" startLocalConnThread> run() > Local Connection closed, thread exited.");
        }};
        t.start();
    }

    public void requestUpdate() {
        isUpToDate = false;
        basicDoPut(getThisUpdateRequestRecord(), true); //true indicates local, therefore will be broadcast.
        updateWaitCount = 0;
    }

    /**
     *
     * Create a record for this object (unique to this address)
     * @return
     */
    public Record getThisUpdateRequestRecord() {
        Record r = new Record();
        r.set("meta", "updateReq");
        r.set("status", false);
        r.set(addressString, ""); //to serve as a unique key, so this record will not be overwritten by other addresses.
        return r;
    }

    /**
     *
     * Create a template suitable for retreiving any update request record.
     *
     * @return
     */
    public RecordTemplate getAnyUpdateRequestTemplate() {
        RecordTemplate rt = new RecordTemplate();
        rt.set("meta", "updateReq");
        rt.add("status");
        return rt;
    }

    /**
     *  Create a record suitable for retreiving only our update request record
     * 
     * @return
     */
    public RecordTemplate getThisUpdateRequestTemplate() {
        RecordTemplate rt = new RecordTemplate();
        rt.set("meta", "updateReq");
        rt.add("status");
        rt.add(addressString);
        return rt;
    }

    /**
     *  When the server starts, it is of course empty. So it stores (in the air) a special record whose "status" value
     *  is false (meaning this copy of the air has not been updated yet). It also sets an internal updateWaitCount to be 0.
     *
     * In this thread, this server wakes up and
     *      checks if it is waiting to be updated. (When just starting, it is so waiting of course)
     *      if it NOT up to date, 
     *          checks to see if its own request is present, with status value true
     *              If so it has been updated by someone. So it marks itself upToDate.
     *              If not it increments a waiting counter updateWaitCount.
     *                if the updateWaitCount exceeds threshold and there has not been an update
     *                    then the server removes the updateRequest record.
     *                    sets isUpToDate = true;
     *                      (No one did an update, and it has given up waiting for one.)
     *      if it IS up to date
     *           Checks to see if anyone else issued an updaterequest record.
     *              if so,
     *                  remove the updaterequest record, set the flag in it to true,
     *                  update the entire network by broadcasting the entire store contents.
     *                  and replace the updaterequest record.
     * Repeat.
     */
    public void startUpdateCheckThread() {
        Thread t = new Thread() {  public void run() {
            print("[startUpdateCheckThread()] Starting updateCheckThread");
            Random rand = new Random(RadioFactory.getRadioPolicyManager().getIEEEAddress());
            while (isRunning()) {
                int jitter = 500;
                int period = 2000;
                int sleep = (int) (period + (rand.nextDouble() - 0.5) * jitter);
                sleep = Math.max(sleep, 100);
                Utils.sleep(sleep);
                if (isUpToDate) {
                    Vector updateRequests = doGetAllMatches(getAnyUpdateRequestTemplate());
                    if (updateRequests.size() > 0) {
                    print("[startUpdateCheckThread()] Up to date. Found " + updateRequests.size() + " requests.");
                        //Now inform them each they are updated.
                        boolean needToAnnounce = false;
                        for (int i = 0; i < updateRequests.size(); i++) {
                            Record r = (Record) updateRequests.elementAt(i);
                            boolean rStatus = false;
                            try {
                                rStatus = r.getBoolean("status");
                            } catch (KeyNotFoundException ex) {
                                System.err.println("Oddly inconsistent record found: " + r);
                                ex.printStackTrace();
                            }
                            if (rStatus) {
                                //This record should be soon removed by the original creator. 
                                if(r.containsKey(addressString)) {
                                    System.err.println("WARNING: HEY! What is my update request doing in there. Removing it.");
                                    doTakeAllMatches(r.asRecordTemplate(), true);
                                }
                            } else {
                                needToAnnounce = true;
                                Record rc = r.clone();
                                rc.set("status", true);
                                print("[startUpdateCheckThread()] replacing update request record with " + rc);
                                doPut(rc, false); //False meaning so it will NOT (yet) be broadcast
                            }
                        }
                        if(needToAnnounce) announceEntireStore(); //here the above modified record is broadcast.
                    }
                } else {
                    //Check status of our request.
                    Vector updateRequests = basicDoGetAllMatches(getThisUpdateRequestTemplate());
                    Record ourRequest = null;
                    for (int i = 0; i < updateRequests.size(); i++) {
                        Record r = (Record) updateRequests.elementAt(i);
                        if(r.containsKey(addressString)) ourRequest = r;
                    }
                    print("> startUpdateCheckThread()] Found our request in store, it is " + ourRequest);
                    if (ourRequest == null) {
                        throw new RuntimeException("AirStore inconsitent in a hard to acheive way,");
                    } else {
                        boolean statusGo = false;
                        try {
                            statusGo = ourRequest.getBoolean("status");
                        } catch (KeyNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        updateWaitCount++;
                        if (statusGo || updateWaitCount> 2) {
                            //We have been upated, or got tired of waiting. Ready to go.
                            print(" > startUpdateCheckThread()] Decalring ourselves ready to go at updateWaitCount =  " + updateWaitCount);
                            isUpToDate = true;
                            print("[startUpdateCheckThread()] remove our request Record");
                            Vector ourRequests = basicDoTakeAllMatches(getThisUpdateRequestTemplate(), true); //true indicating local and thus requiring broadcast
                            print("[startUpdateCheckThread()] removed " + ourRequests.size() + " records from the store");
                            //There may be other threads waiting for this moment -- notify them
                            print("[startUpdateCheckThread()] Notifying all deferred commands. Hopefully they now surge on forward on their own.");
                            //synchronized(upToDateMonitor){upToDateMonitor.notifyAll();}

                            //Consistency check
                            if(ourRequests.size() != 1) System.err.println("[AirStreService > updateCheckThread] removed our request, but there were " + ourRequests.size() + " instances of it!!!" );
                            notifyAndClearUpToDateWaiters();
                            executeDeferredCommands();
                        }
                    }
                }
            }
            print(" updateCheckThread exiting.");
        }};
        t.start();
    }

    public synchronized void announceEntireStore(){
         //Make a copy of each record, place in an array, simply for safe iteration
        Record[] recs = new Record[records.size()];
        records.copyInto(recs);
        for (int i = 0; i < recs.length; i++) {
            doPut(recs[i], true); //true means local, and thus a resulting broadcast.
        }
    }

    public synchronized void addUpToDateWaiter(IAirStoreConnection c){
        if(isUpToDate){
            c.notifyUpToDate();
            return;
        }
        upToDateWaiters.addElement(c);
    }

    public synchronized void notifyAndClearUpToDateWaiters(){
        for (int i = 0; i < upToDateWaiters.size(); i++) {
            IAirStoreConnection c = (IAirStoreConnection)upToDateWaiters.elementAt(i);
            c.notifyUpToDate();
            if(! connections.contains(c)) {
                connections.addElement(c);
            }
        }
        upToDateWaiters = new Vector();
    }

    public static void main(String[] argv){
        if(doPrints) System.out.println("[AirStoreService] main> enterd");
        (AirStoreService.getInstance()).run();
    }

    /**
     * @return the isRunning boolean
     */
    public boolean isRunning() {
        return isRunning;
    }
}

