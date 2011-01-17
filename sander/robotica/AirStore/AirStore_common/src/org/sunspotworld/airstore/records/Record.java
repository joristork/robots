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
 

package org.sunspotworld.airstore.records;

import com.sun.spot.util.Utils;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Vector;


/**
 *  A Record is the only thing that AirStore directly stores.
 * 
 *  A record is kind of like a dictionary or hashtable: It is set of RecordEntries. (A RecordEntry is a key-value pair.)
 *  NOTE: (For now anyway) we enforce that a Record can contain at most one RecordEntry with any given key.
 */
public class Record  {
   
   private Vector entries = new Vector(); //Should be a Set but Java has none, and I'm too resentful to make my own Set class.

   private static boolean doPrints = false;

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
     * This and subclasses use this to print a message only when doPrints is set true.
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

   public String toString(){
       String[] tokens = Utils.split(getClass().getName(), '.');
       String cn = tokens[tokens.length - 1];
       String s = cn + "[";
       for (int i = 0; i < entries.size(); i++) {
            s = s + entries.elementAt(i);
            s = s + ", ";
       }
       if(entries.size() > 0) s = s.substring(0, s.length() - 2);
       s = s + "]";
       return s;
   }

   /**
    *
    * To facilitate matching a record with another, turnthis record into a RecordTemplate
    * and do the match then.
    * @return
    */
   public RecordTemplate asRecordTemplate(){
       RecordTemplate t = new RecordTemplate();
       for (int i = 0; i < entries.size(); i++) {
          RecordEntry e = (RecordEntry)entries.elementAt(i);
          t.addEntry(e);
       }
       return t;
   }
   
   /**
    * two records are equal if each RecordEntry matches a record entry in the other.
    * @param r
    * @return
    */
   public boolean equals(Record r){
       return    r.asRecordTemplate().matches(this) && 
              this.asRecordTemplate().matches(r);
   }

   /**
    * Create an easy-to-read multi-line output of this record.
    */
    public void printOut() {
        System.out.println("|  ____________________________");
        System.out.println("|  |");
        for (int i = 0; i < entries.size(); i++) {
            System.out.println("|  | " + entries.elementAt(i));
        }
        System.out.println("|  |___________________________");
    }

   public static Record readFrom(DataInput s){
       if(doPrints) System.out.println("[Record].............  readFrom()> entered");
       Record r = new Record(); 
       int nRecordEntries = 0;
        try {
            nRecordEntries = s.readInt();

       if(doPrints) System.out.println("[Record].............  readFrom()>  n Entries = " + nRecordEntries);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       for(int i = 0; i < nRecordEntries; i++){
           RecordEntry re = RecordEntry.readFrom(s);
           r.addEntry(re);
           if(doPrints) System.out.println("[Record].............  readFrom()>  ... added RecordEntry = " + re);
       }
       return r;
   }
   
   public void writeOn(DataOutput s){
        try {
            s.writeInt(entries.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       for(int i = 0; i < entries.size(); i++){
           RecordEntry re =(RecordEntry) entries.elementAt(i);
           re.storeOn(s);
       } 
   }
   
   public Vector getEntries(){
       Vector v = new Vector();
       for(int i = 0; i < entries.size(); i++){
           v.addElement(entries.elementAt(i));
       }
       return v;
   }

   public Vector getKeys(){
       Vector v = new Vector();
       for(int i = 0; i < entries.size(); i++){
           v.addElement(((RecordEntry)entries.elementAt(i)).getKey());
       }
       return v;
   }

   public boolean containsKey(String k){
       Vector v = getKeys();
       for (int i = 0; i < v.size(); i++) {
           if(k.equals(v.elementAt(i))) return true;
       }
       return false;
   }

   public void addEntry(RecordEntry re){
       removeKey(re.getKey());
       entries.addElement(re);
   }

   /**
    * The clone contains cloned entires, AND cloned
    * values in those cloned entries (except for RecordEntryString:
    * It's expected that users of
    * AirStore do not side-effect the contents of Strings.)
    * @return the clone
    */
   public Record clone(){
       Record r = null;
        try {
            r = (Record) this.getClass().newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        Vector v = getEntries();
        for (int i = 0; i < v.size(); i++) {
           r.addEntry(((RecordEntry)v.elementAt(i)).clone());
       }
        return r;
   }
   
   /***** CONVENIENCE METHODS *****/
   
   /**
    * Remove the RecordEntry with the given key.
    * If no matchesEntry, do nothing.
    * 
    * @param k
    */
   public void removeKey(String k){
       Vector removals = new Vector();
       for(int i = 0; i < entries.size(); i++){
            if(((RecordEntry)entries.elementAt(i)).getKey().equals(k))
                removals.addElement(entries.elementAt(i));
       }
       for(int i = 0; i < removals.size(); i++){
            entries.removeElement(removals.elementAt(i));
       }
   }

    public boolean getBoolean(String key, boolean ifAbsent){
        try {
            boolean res = getBoolean(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public boolean getBoolean(String key) throws KeyNotFoundException {
        boolean result = false;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryBoolean)) {
                result = ((Boolean)((RecordEntryBoolean)re).getValue()).booleanValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    }

    public byte getByte(String key) throws KeyNotFoundException  {
        byte result = 0;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryByte)) {
                result = ((Byte)((RecordEntryByte)re).getValue()).byteValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    }

    public double getDouble(String key, double ifAbsent){
        try {
            double res = getDouble(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public double getDouble(String key) throws KeyNotFoundException  {
        double result = 0;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryDouble)) {
                result = ((Double)((RecordEntryDouble)re).getValue()).doubleValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    };

    public int getInt(String key, int ifAbsent){
        try {
            int res = getInt(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public int getInt(String key) throws KeyNotFoundException  {
        int result = 0;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryInt)) {
                result = ((Integer)((RecordEntryInt)re).getValue()).intValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    };

    public long getLong(String key, long ifAbsent){
        try {
            long res = getLong(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public long getLong(String key) throws KeyNotFoundException  {
        long result = 0;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryLong)) {
                result = ((Long)((RecordEntryLong)re).getValue()).longValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    };

   public String getString(String key, String ifAbsent){
        try {
            String res = getString(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }
    public String getString(String key) throws KeyNotFoundException  {
        String result = null;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryString)) {
                result = (String)((RecordEntryString)re).getValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    };

    public boolean[] getBooleanArray(String key, boolean[] ifAbsent){
        try {
            boolean[] res = getBooleanArray(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public boolean[] getBooleanArray(String key) throws KeyNotFoundException {
        boolean[] result = null;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryBooleanArray)) {
                result = (boolean[])((RecordEntryBooleanArray)re).getValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    }

    public byte[] getByteArray(String key, byte[] ifAbsent){
        try {
            byte[] res = getByteArray(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public byte[] getByteArray(String key) throws KeyNotFoundException {
        byte[] result = null;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryByteArray)) {
                result = (byte[])((RecordEntryByteArray)re).getValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    }

    public double[] getDoubleArray(String key, double[] ifAbsent){
        try {
            double[] res = getDoubleArray(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public double[] getDoubleArray(String key) throws KeyNotFoundException {
        double[] result = null;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryDoubleArray)) {
                result = (double[])((RecordEntryDoubleArray)re).getValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    }

    public int[] getIntArray(String key, int[] ifAbsent){
        try {
            int[] res = getIntArray(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public int[] getIntArray(String key) throws KeyNotFoundException {
        int[] result = null;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryIntArray)) {
                result = (int[])((RecordEntryIntArray)re).getValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    }

    public long[] getLongArray(String key, long[] ifAbsent){
        try {
            long[] res = getLongArray(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public long[] getLongArray(String key) throws KeyNotFoundException {
        long[] result = null;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryLongArray)) {
                result = (long[])((RecordEntryLongArray)re).getValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    }

    public String[] getStringArray(String key, String[] ifAbsent){
        try {
            String[] res = getStringArray(key);
            return res;
        } catch (KeyNotFoundException ex) {
            return ifAbsent;
        }
    }

    public String[] getStringArray(String key) throws KeyNotFoundException {
        String[] result = null;
        boolean found = false;
        for (int j = 0; j < entries.size(); j++) {
            RecordEntry re = (RecordEntry) entries.elementAt(j);
            if (re.getKey().equals(key) && (re instanceof RecordEntryStringArray)) {
                result = (String[])((RecordEntryStringArray)re).getValue();
                found = true;
            }
        }
        if(! found) throw new KeyNotFoundException();
        return result;
    }


   public void set(String key, boolean value){
       removeKey(key);
       RecordEntryBoolean r = new RecordEntryBoolean();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, boolean[] value){
       removeKey(key);
       RecordEntryBooleanArray r = new RecordEntryBooleanArray();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, byte value){
       removeKey(key);
       RecordEntryByte r = new RecordEntryByte();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, byte[] value){
       removeKey(key);
       RecordEntryByteArray r = new RecordEntryByteArray();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, double value){
       removeKey(key);
       RecordEntryDouble r = new RecordEntryDouble();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, double[] value){
       removeKey(key);
       RecordEntryDoubleArray r = new RecordEntryDoubleArray();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, int value){
       removeKey(key);
       RecordEntryInt r = new RecordEntryInt();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, int[] value){
       removeKey(key);
       RecordEntryIntArray r = new RecordEntryIntArray();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, long value){
       removeKey(key);
       RecordEntryLong r = new RecordEntryLong();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, long[] value){
       removeKey(key);
       RecordEntryLongArray r = new RecordEntryLongArray();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, String value){
       removeKey(key);
       RecordEntryString r = new RecordEntryString();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }
   
   public void set(String key, String[] value){
       removeKey(key);
       RecordEntryStringArray r = new RecordEntryStringArray();
       r.setKey(key);
       r.setValue(value);
       addEntry(r);
   }

   public void set(String key){
       removeKey(key);
       RecordEntryNull r = new RecordEntryNull();
       r.setKey(key); 
       addEntry(r);
   }
}
