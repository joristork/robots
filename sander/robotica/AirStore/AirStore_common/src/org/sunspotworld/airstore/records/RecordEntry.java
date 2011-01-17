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

/**
 *  Abstact class for a simple key-value pair. Key must be a string.
 */
public abstract class RecordEntry {

    /**
     * These firelds are intended only to be used to serialize the RecordEntry subclass name
     * more efficiently than using the String that is the class name.
     */
    public final static int TYPE_BOOLEAN      = 0;
    public final static int TYPE_BOOLEANARRAY = 1;
    public final static int TYPE_BYTE         = 2;
    public final static int TYPE_BYTEARRAY    = 3;
    public final static int TYPE_DOUBLE       = 4;
    public final static int TYPE_DOUBLEARRAY  = 5;
    public final static int TYPE_INT          = 6;
    public final static int TYPE_INTARRRAY    = 7;
    public final static int TYPE_LONG         = 8;
    public final static int TYPE_LONGARRAY    = 9;
    public final static int TYPE_STRING       = 10;
    public final static int TYPE_STRINGARRAY  = 11;
    public final static int TYPE_NULL         = 12;
  //  public final static int TYPE_TEMPLATE     = 12; //No longer used?
    public final static int TYPE_ANY          = 13;  //Used only by (possibly some) RecordEntries in a RecordTemplate (for more generous matching)

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

    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String k) {
        key = k;
    }

    public String toString() {
        return getKey() + ":" + valueToString();
    }

    public String getTypeString(){
        return "?";
    }

    public String valueToString(){ 
        return getValue()== null ? null: getValue().toString();
    }
    
/**
 * This is the method one would call from outside, to create a RecordEntry or
 * appropriate subclass from the given stream.
 * 
 * @param s the stream
 * @return the fully instatiated instace of the proper type.
 */
    public static RecordEntry readFrom(DataInput s) {
        int type;
        RecordEntry r = null;
        try {
            type = s.readByte();
             if(doPrints) System.out.println("[RecordEntry] ,,,,,,,,,,,,,,,,,,,,,,,,,,,,,   ... type byte  = " + type);
             switch(type){
                case TYPE_BOOLEAN:      r =new RecordEntryBoolean();      r.initializeFrom(s); break;
                case TYPE_BOOLEANARRAY: r =new RecordEntryBooleanArray(); r.initializeFrom(s); break;
                case TYPE_BYTE:         r =new RecordEntryByte();         r.initializeFrom(s); break;
                case TYPE_BYTEARRAY:    r =new RecordEntryByteArray();    r.initializeFrom(s); break;
                case TYPE_DOUBLE:       r =new RecordEntryDouble();       r.initializeFrom(s); break;
                case TYPE_DOUBLEARRAY:  r =new RecordEntryDoubleArray();  r.initializeFrom(s); break;
                case TYPE_INT:          r =new RecordEntryInt();          r.initializeFrom(s); break;
                case TYPE_INTARRRAY:    r =new RecordEntryIntArray();     r.initializeFrom(s); break;
                case TYPE_LONG:         r =new RecordEntryLong();         r.initializeFrom(s); break;
                case TYPE_LONGARRAY:    r =new RecordEntryLongArray();    r.initializeFrom(s); break;
                case TYPE_STRING:       r =new RecordEntryString();       r.initializeFrom(s); break;
                case TYPE_STRINGARRAY:  r =new RecordEntryStringArray();  r.initializeFrom(s); break;
                case TYPE_ANY:          r =new RecordEntryAny();          r.initializeFrom(s); break;
                case TYPE_NULL:         r =new RecordEntryNull();         r.initializeFrom(s); break;
             }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return r;
    }
    
    /**
     * The type byte has already been read at the time this is called.
     * See readFrom.
     * @param s
     */
    public void initializeFrom(DataInput s){
        initializeKeyFrom(s);
        initializeValueFrom(s); // was null. Why?
        print(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,   ... value =    " + getValue());
    }

    public void initializeKeyFrom(DataInput s){
        try {
            key = s.readUTF();
            print(",,,,,,,,,,,,,,,,,,,,,,,,,,,,,   ... key = readUTF =  " + key);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Parse the "usual" human readable String representation into a value.
     *
     * Notionally the inverse of valueToString(), see above.
     *
     * Arrayed classes should accept comma seperated tokens wrapped by curlies if
     * this is how valuetoString so represents the array.
     * if ththe.
     * @param val The human readable form.
     */
    public abstract void initializeValueFromString(String val) throws CannotParseException;

    public abstract void initializeValueFrom(DataInput s);
     
    public void storeOn(DataOutput s) {
        storeTypeOn(s);
        storeKeyOn(s);
        storeValueOn(s);
    }
    
    public void storeKeyOn(DataOutput s){
        try {
            s.writeUTF(key);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public RecordEntry clone(){
        RecordEntry re = null;
        try {
            re = (RecordEntry) this.getClass().newInstance();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
        re.setKey(getKey());
        copyValueInto(re);
        return re;
    }

    /**
     * Purely a workaround to handle non-object data so clone() works.
     * Thank you Java.
     * Now every subclass must implement, since there is no setValue() here.
     * Should be private, but Java won't allow.
     * Thank you Java.
     * @param re Intended use is re is a clone whose key is set, but whose value is not yet.
     */
    public abstract void copyValueInto(RecordEntry re);
    
    /**
     * Subclasses use the values of the "enumeration" fields such as TYPE_BOOLEAN
     * etc. to indicate which class is being serialized. The approriate enumeration
     * is simply stored directly on the the given stream.
     * 
     * @param s the stream on which to encode the approriate type indicator.
     */
    public abstract void storeTypeOn(DataOutput s);
    
    public abstract void storeValueOn(DataOutput s);

    /**
     * "Boxes" up the value inside an appropriate object if value is not itself an object.
     * @return
     */
    public abstract Object getValue();

    public boolean matchesEntry(RecordEntry re){
        print("          = match " + this + " === against ==== " + re);
        print("          =");
        if(! re.getKey().equals(this.getKey())){ 
            print("          = NO: key mismatch " + re.getKey() + " vs " + this.getKey());
            return false;
        }  //Keys don't match -> false
        if(re instanceof RecordEntryAny ){
            print("          = Yes: key match, and ANY ");
            return true;
        }   //Keys do match, and value is "any" -> true
        if(! re.getClass().equals(this.getClass())){
            print("          = NO key match, type mismatch ");
            return false;
        }  //Key match, but value types don't -> false
        boolean result = getValue().equals(re.getValue());
        if(result)
            print("          = YES key match, type match, value match ");
        else
            print("          = NO key match, type match, but value mismatch ");
        print("          =========");
        return    result;                //Keys match, value types match, -> depends on equality of values.
    }
}
