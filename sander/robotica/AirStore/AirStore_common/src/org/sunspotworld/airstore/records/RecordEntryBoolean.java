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
 *  Class for a simple key-value pair in which the value is a boolean.
 *  The key (iherited) must be a string. 
 */
public class RecordEntryBoolean extends RecordEntry {

    private boolean value;

    public Object getValue() {
        return new Boolean(value);
    }
    
    public void setValue(boolean val){
        value = val;
    }

    public void initializeValueFromString(String s) throws CannotParseException {
        if(s.equalsIgnoreCase("true")) value = true;
        else if(s.equalsIgnoreCase("false")) value = false;
        else throw new CannotParseException();
    }

    public void initializeValueFrom(DataInput s) {
        try {
            value = s.readBoolean();
        } catch (IOException ex) {
            ex.printStackTrace();
        } 
    }

    public String getTypeString(){
        return "boolean";
    }

    public void storeTypeOn(DataOutput s) {
        try {
            s.writeByte(TYPE_BOOLEAN);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void storeValueOn(DataOutput s) {
        try {
            s.writeBoolean(value);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void copyValueInto(RecordEntry re) {
        ((RecordEntryBoolean) re).setValue(value);
    }

}
