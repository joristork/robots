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
 * Created on Nov 12, 2008 2:13:09 PM;
 */
package org.sunspotworld.airstore;

import com.sun.spot.peripheral.radio.RadioFactory;
import java.util.Vector;
import org.sunspotworld.airstore.connections.IAirStoreConnection;
import org.sunspotworld.airstore.connections.IListenNotifyConnection;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordEntry;
import org.sunspotworld.airstore.records.RecordEntryInt;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.util.Waiter;

/**
 *
 *  The purpose of the AirStore class is to 
 *    1] provide the programmer with a wide, convenient interface to the core IAirStore API, and
 *    2] forward these core API messages over an appropriate connection
 *          to the central AirStoreService soleInstance on the device (or host).
 *          The connections must be different on differnt devices
 *          (e.g.: a Sun SPOT vs a host). See initConnection()
 * 
 *  Notionally, AirStore retains a set of Records. The API lets you store and retrieve data in the store.
 *
 *  The main API is static, though there is a single soleInstance that does the work.
 * 
 *  There are many convenience static "put()"methods that create and store Records automatically.
 * 
 *  Records are retrieved based on matchng against a RecordTemplate.
 *  There are convenience static "getFoo(key)" methods for many types Foo. These create a template for you,
 *  and return just the value of the RecordEntry with the given key.
 *
 */
public class AirStore implements IAirStore {

    private static AirStore soleInstance;

    private static boolean useHostModeOnDevice = false;

    /**
     * All calls to AirStore funnel through this single soleInstance.
     *
     * NOTE the first time this is called on a device or host, it
     * will lead to AirStoreService being started, and waiting to become
     * up to date before returning. THIS MAY TAKE SEVERAL SECONDS!
     *
     * Consequently any code added to this class should always address the istance
     * through getInstance(), never directly accessing the soleInstance variable.
     *
     * @return the soleInstance
     */
    public static AirStore getInstance() {
        //The existence of an instance assumes its connection is in place and
        //that AirStoreService is running.
        if(soleInstance == null) {
            soleInstance = new AirStore();
            //Possibly a several second wait here.
            soleInstance.getConnection().assureServiceStartedAndUpToDate();
        }
        return soleInstance;
    }

    /**
     * @return the useHostModeOnDevice
     *
     * For some applications (e.g.: the Emulator) thee may be performance
     * reasons (or other reasons, I suppose) to avoid using Inter-Isolate Communication.
     * Set this boolean to use host mode whn on device.
     */
    public static boolean useHostModeOnDevice() {
        return useHostModeOnDevice;
    }

    public static void setUseHostModeOnDevice(boolean b) {
        useHostModeOnDevice = b;
    }

    private IAirStoreConnection connection;
    private static boolean doPrints = false;
    private IListenNotifyConnection myConnection;
    private Vector listenerConnections = new Vector();

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

    public static void setAllDoPrints(boolean p) {
        setDoPrints(p);
        Record.setDoPrints(p);
        RecordEntry.setDoPrints(p);
    //TO-DO find which classes are loaded as providing the connection
    //(host or device connection classes) and implement the calls to the
    //setDoPrints(p) there as well.
    }

    /**
     * Print a message only when doPrints is set true.
     *
     * @param msg Message to appear on standard out.
     */
    public void print(String msg) {
        if (doPrints) {
            System.out.println("[AirStore] " + msg);
        }
    }

    public Vector waitForInt(String key){
        RecordTemplate rt = new RecordTemplate();
        RecordEntry re = new RecordEntryInt();
        re.setKey(key);
        rt.addEntry(re);
        return waitFor(rt);
    }

    /**
     * Wait (thread hangs) here until AirStore contains a record
     * containing a RecordEntry of any type, whose key mateches the
     * given parameter.
     * @param key
     * @return
     */
    public Vector waitFor(String key){
        return waitFor(new RecordTemplate(key));
    }

    /**
     * Wait (thread hangs) here until AirStore contains a record
     * matching the given RecordTemplate
     * @param rt
     * @return
     */
    public Vector waitFor(RecordTemplate rt){
       return new Vector();
    }

    ////// CORE IAirStore API ////////////////
    //
    //
    public synchronized void doPut(Record r, boolean isLocal) {
        if (r instanceof RecordTemplate) {
            throw new RuntimeException("Don't put RecordTemplates in AirStore. Just Records!");
        }
        getConnection().doPut(r, isLocal);
        Thread.yield(); //The put may not imemdiately happen unless the reading thread on the far end of the connection runs.

    }

    public synchronized Vector doGetAllMatches(RecordTemplate t) {
        return getConnection().doGetAllMatches(t);
    }

    public synchronized Vector doTakeAllMatches(RecordTemplate t, boolean isLocal) {
        return getConnection().doTakeAllMatches(t, isLocal);
    }
    //
    // Replicated IAirStore API as statics, for consistency and convenience:
    //
    public static void put(Record r) {
        getInstance().doPut(r, true); // true = this is a local put.
    }
    /**
     *
     * @param t = the template against which all records are matched.
     * @return A Vector of the matches.
     */
    public static Vector getAllMatches(RecordTemplate t) {
        return getInstance().doGetAllMatches(t);
    }

    public static Vector takeAllMatches(RecordTemplate t) {
        return getInstance().doTakeAllMatches(t, true); //the true indicates isLocal thus implying a broadcast
    }
    //
    //
    ///// END CORE IAirStore API //////////////

     /**
     * Returns the first matching record found, or null if none.
     *
     * @param t = the template against which all records are matched.
     * @return the first found matching Record.
     */
    public static Record getMatch(RecordTemplate t) {
        Vector v = getAllMatches(t);
        if (v.isEmpty()) {
            return null;
        } else {
            return (Record) v.elementAt(0);
        }
    }

    public IAirStoreConnection getConnection() {
        if (connection == null) {
            initConnection();
        }
        return connection;
    }

    // code for newlistener connection
    public IListenNotifyConnection getNewListenNotifyConnection() {
        IListenNotifyConnection c = null;
        try {
            if (RadioFactory.isRunningOnHost() || AirStore.useHostModeOnDevice()) {
                c = (IListenNotifyConnection) Class.forName("org.sunspotworld.airstore.connections.ListenNotifyHostInProcConnection").newInstance();
                print(" initConnection> instantiated new connection: On host, so using AirStoreHostInProcConnection");
            } else {
                c = (IListenNotifyConnection) Class.forName("org.sunspotworld.airstore.connections.ListenNotifyIICConn").newInstance();
                print(" initConnection> instantiated new connection: On device, so using ListenNotifyIICConnection");
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        return c;
    }

    public void initConnection() {
        print(" initConnection> entered.");

        try {
            if (RadioFactory.isRunningOnHost()|| AirStore.useHostModeOnDevice()) {
                connection = (IAirStoreConnection) Class.forName("org.sunspotworld.airstore.connections.AirStoreHostInProcConnection").newInstance();
                print(" initConnection> instantiated new connection: On host, so using AirStoreHostInProcConnection");
            } else {
                connection = (IAirStoreConnection) Class.forName("org.sunspotworld.airstore.connections.AirStoreIICConnection").newInstance();
                print(" initConnection> instantiated new connection: On device, so using AirStoreIICConnection");
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

  
    /***** CONVENIENCE STATIC METHODS *****/
    public static Vector takeAll(String key) {
        RecordTemplate t = new RecordTemplate(key);
        return takeAllMatches(t);
    }

    public static void put(String key, boolean value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, byte value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, double value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, int value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, long value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, String value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, boolean[] value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, byte[] value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, double[] value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, int[] value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, long[] value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    public static void put(String key, String[] value) {
        Record r = new Record();
        r.set(key, value);
        put(r);
    }

    /**
     * Go through all Records' RecordEntries looking for the first one whose key
     * equals() the given key. Return this RecordEntry's value;
     *
     * @param key
     * @return
     */
    public static Object getValue(String key) {
        RecordTemplate rt = new RecordTemplate(key);
        Record r = getMatch(rt);
        if (r == null) {
            return null;
        }
        Vector entries = r.getEntries();
        for (int i = 0; i < entries.size(); i++) {
            RecordEntry re = (RecordEntry) entries.elementAt(i);
            if (re.getKey().equals(key)) {
                return re.getValue();
            }
        }
        return null;
    }

    /**
     * NOTE on the (current) semantics of getFoo(key):
     *
     * The caller is asserting that the ONLY matching entries
     * with the given key are of type Foo.
     *
     * If there is a Record with an entry matching "key" whose
     * type is NOT Foo, that entry may be returned as the first
     * match. The attempt to cast to Foo will then fail.
     *
     * If this is a concern, use other methods of retrieval especially
     * recommended is getFoo(key, ifAbsentValue), see below.
     *
     * FIX: this is probably not the best semantics(?)
     * We could throw a special exception, but then the calling
     * code has all that verbose try catch crud.
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
            return ((Boolean) getValue(key)).booleanValue();
    }

    public static byte getByte(String key) {
        return ((Byte) getValue(key)).byteValue();
    }

    public static double getDouble(String key) {
        return ((Double) getValue(key)).doubleValue();
    }

    public static int getInt(String key) {
        return ((Integer) getValue(key)).intValue();
    }

    public static long getLong(String key) {
        return ((Long) getValue(key)).longValue();
    }

    public static String getString(String key) {
        return (String) getValue(key);
    }

    public static boolean[] getBooleanArray(String key) {
        return (boolean[]) getValue(key);
    }

    public static byte[] getByteArray(String key) {
        return (byte[]) getValue(key);
    }

    public static double[] getDoubleArray(String key) {
        return (double[]) getValue(key);
    }

    public static int[] getIntArray(String key) {
        return (int[]) getValue(key);
    }

    public static long[] getLongArray(String key) {
        return (long[]) getValue(key);
    }

    public static String[] getStringArray(String key) {
        return (String[]) getValue(key);
    }

    public static boolean getBoolean(String key, boolean ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return ((Boolean) val).booleanValue();
    }

    public static byte getByte(String key, byte ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return ((Byte) val).byteValue();
    }
    public static double getDouble(String key, double ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return ((Double) val).doubleValue();
    }

    public static int getInt(String key, int ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return ((Integer) val).intValue();
    }

    public static long getLong(String key, long ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return ((Long) val).longValue();
    }

    public static String getString(String key, String ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return (String) val;
    }

    public static boolean[] getBooleanArray(String key, boolean[] ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return (boolean[]) val;
    }

    public static byte[] getByteArray(String key, byte[] ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return (byte[]) val;
    }

    public static double[] getDoubleArray(String key, double[] ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return (double[]) val;
    }

    public static int[] getIntArray(String key, int[] ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return (int[]) val;
    }

    public static long[] getLongArray(String key, long[] ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return (long[]) val;
    }

    public static String[] getStringArray(String key, String[] ifAbsent) {
        Object val = getValue(key);
        if (val == null) {
            return ifAbsent;
        }
        return (String[]) val;
    }

    /**
     * Add the argument to the list of listeners. Listen to ALL puts, replaces,and takes.
     */
    public static void addListener(IAirStoreListener a){
        addListener(a, new RecordTemplate()); //empty template matches everything.
    }

    /**
     * Add the argument to the list of listeners. Listen to puts, replace, and takes of
     * records matching the given template.
     */
    public static void addListener(IAirStoreListener a, RecordTemplate t) {
        getInstance().doAddListener(a, t);
    }

    public void doAddListener(IAirStoreListener a, RecordTemplate t) {
        IListenNotifyConnection conn = getNewListenNotifyConnection();
        conn.addListener(a, t);
        listenerConnections.addElement(conn);
    }

    /**
     * The application is no longer interested.
     */
    public static void removeListener(IAirStoreListener a, RecordTemplate t) {
        getInstance().doRemoveListener(a, t);
    }

    public void doRemoveListener(IAirStoreListener a, RecordTemplate t) {
        IListenNotifyConnection[] conns = new IListenNotifyConnection[listenerConnections.size()];
        listenerConnections.copyInto(conns);
        for (int i = 0; i < conns.length; i++) {
            IListenNotifyConnection lnc = conns[i];
            if(a == lnc.getListener() && t.equals(lnc.getRecordTemplate())){
                lnc.removeListener();
                listenerConnections.removeElement(lnc);
            }
        }
    }

}