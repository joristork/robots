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

import com.sun.squawk.util.StringTokenizer;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Vector;


/**
 *  Class for a simple key-value pair in which the value is an array of ints.
 *  The key (iherited) must be a string. 
 */
public class RecordEntryIntArray extends RecordEntryTypedArray {

    private int[] value;

    public Object getValue() {
        return value;
    }
    
    public void setValue(int[] val){
        value = val;
    }

    public void initializeValueFrom(DataInput s) {
        try {
            int len = s.readInt();
            value = new int[len];
            for (int i = 0; i < len; i++) {
                value[i] = s.readInt();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public String getTypeString(){
        return "int[]";
    }
  
    public void storeTypeOn(DataOutput s) {
        try {
            s.writeByte(TYPE_INTARRRAY);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
   public void storeValueOn(DataOutput s) {
        try {
            s.writeInt(value.length);
            for (int i = 0; i < value.length; i++) {
                s.writeInt(value[i]);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void copyValueInto(RecordEntry re) {
        //Caution: JME does not support clone,
        //even though its use will compile to bytecodes (Squawk/Java bug].
        int[] newArray = new int[value.length];
        System.arraycopy(value, 0, newArray, 0, value.length);
        ((RecordEntryIntArray) re).setValue(newArray);
    }

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
        if(! (re instanceof RecordEntryIntArray)){
            print("          = NO key match, type mismatch ");
            return false;
        }  //Key match, but value types don't -> false
        int[] ourArr = (int[]) getValue();
        int[] itsArr = (int[]) re.getValue();
        if(ourArr.length != itsArr.length) {
            print("          = YES key match, type match, arrays differ in length ");
            return false;
        }
        boolean same = true;
        for (int i = 0; i < itsArr.length; i++) {
            same = same && (itsArr[i] == ourArr[i]);
        }
        if(same){
            print("          = YES key match, type match, array length match, all ints == in order");
        } else{
            print("          = NO key match, type match, array length match, but 1 or more ints not == in order");
        }
        print("          =========");
        return same;
    }

    public String valueToString(){
        String s = "{";
        for (int i = 0; i < value.length; i++) {
            s = s + value[i];
            if(i < value.length - 1) s = s + ", ";
        }
        s = s + "}";
        return s;
    }

    /**
     * Helper method called from initializeValueFromString() ( see the super class).
     * @param token to be parsed into a single element
     * @param valVector holds the lastest resulting parsed value.
     * @throws org.sunspotworld.airstore.records.CannotParseException
     */
    public void parseElementAddToVector(String token, Vector valVector) throws CannotParseException {
        try {
            int val = Integer.parseInt(token);
            valVector.addElement(new Integer(val));
        } catch (NumberFormatException e) {
            throw new CannotParseException("Trouble parsing " + token);
        }
    }

    /**
     * Helper method called from initializeValueFromString() ( see the super class).
     * Simply moves the values from a vector into the instancee variable, an array.
     * @param valueVector
     */
    public void moveElementsToValue(Vector valueVector) {
        value = new int[valueVector.size()];
        for (int i = 0; i < value.length; i++) {
            value[i] = ((Integer) valueVector.elementAt(i)).intValue();
        }
    }
}
