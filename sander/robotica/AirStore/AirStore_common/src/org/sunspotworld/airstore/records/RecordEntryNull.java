/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.records;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * A record that has a key but no value.
 *
 * This may be slightly different than having a value that is null.
 *
 * @author randy
 */
public class RecordEntryNull extends RecordEntry {


    public void initializeValueFromString(String s) throws CannotParseException {
    }

    public void initializeValueFrom(DataInput s) {
        //Pretty easy to impiment.
    }

    public String getTypeString(){
        return "null";
    }

    public void storeTypeOn(DataOutput s) { try {
            s.writeByte(TYPE_NULL);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Value for RecordEntryNull is empty.
     * @param s
     */
    public void storeValueOn(DataOutput s) {
    }

    public Object getValue() {
        return null;
    }

    public boolean matchesEntry(RecordEntry re){
        if( re instanceof RecordEntryAny) return re.getKey().equals(this.getKey());
        if(! (re instanceof RecordEntryNull)) return false;
        return re.getKey().equals(this.getKey());
    }

    public String toString() {
        return getKey() + ":";
    }

    public void copyValueInto(RecordEntry re) {
        //What value?
    }

}
