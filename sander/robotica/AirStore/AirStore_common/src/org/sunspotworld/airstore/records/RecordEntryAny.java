/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Matches any type, and any value.
 *
 * @author randy
 */
public class RecordEntryAny extends RecordEntry {

    public void initializeValueFromString(String s) throws CannotParseException {
        //Any doesn't need to represent a value
    }

    public void initializeValueFrom(DataInput s) {
        //Any doesn't need to represent a value
    }

    public String getTypeString(){
        return "*";
    }

    public void storeTypeOn(DataOutput s) {
        try {
            s.writeByte(TYPE_ANY);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void storeValueOn(DataOutput s) {
        //Any doesn't need to represent a value
    }

    public Object getValue() {
        return null;
    }

    public boolean matchesEntry(RecordEntry re){
        return re.getKey().equals(this.getKey());
    }

    public void copyValueInto(RecordEntry re) {
        //Any doesn't need to represent a value
    }
}
