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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


/**
 *  Class for a simple key-value pair in which the value is a double.
 *  The key (iherited) must be a string. 
 */
public class RecordEntryDouble extends RecordEntry {

    private double value;

    public Object getValue() {
        return new Double(value);
    }
    
    public void setValue(double val){
        value = val;
    }

    public void initializeValueFromString(String s) throws CannotParseException {
        try{
            value = Double.parseDouble(s);
        } catch (NumberFormatException e){
            throw new CannotParseException();
        }
    }

    public void initializeValueFrom(DataInput s) {
        try {
            value = s.readDouble();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }

    public String getTypeString(){
        return "double";
    }

    public void storeTypeOn(DataOutput s) {
        try {
            s.writeByte(TYPE_DOUBLE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void storeValueOn(DataOutput s) {
        try {
            s.writeDouble(value);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void copyValueInto(RecordEntry re) {
        ((RecordEntryDouble) re).setValue(value);
    }
}
