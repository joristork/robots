/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.records;

import com.sun.squawk.util.StringTokenizer;
import com.sun.squawk.util.UnexpectedException;
import java.io.DataInput;
import java.io.DataOutput;
import java.util.Vector;

/**
 *
 * @author randy
 */
public abstract class RecordEntryTypedArray extends RecordEntry {

    public void initializeValueFromString(String s) throws CannotParseException {
        Vector valVector = new Vector();
        s = s.trim();
        // s must be bracketed with curlies.
        if (!s.startsWith("{") || !s.endsWith("}")) {
            throw new CannotParseException("Must start with { and end with }");
        }
        s = s.substring(1, s.length() - 1); //Got rid of curlies
        StringTokenizer toks = new StringTokenizer(s, ",");
        while (toks.hasMoreElements()) {
            String tok = toks.nextToken().trim(); 
            if (!(tok.equals(",") || (tok.length()==0))) { //Discard "loose commas" and empty tokens
                if (tok.endsWith(",")) {
                    tok = tok.substring(0, tok.length() - 1);
                }
                try {
                    parseElementAddToVector(tok, valVector);
                } catch (NumberFormatException e) {
                    throw new CannotParseException("Trouble parsing " + tok);
                }
            }
        }
        moveElementsToValue(valVector);
    }

    abstract public void parseElementAddToVector(String token, Vector valVector) throws CannotParseException ;

    abstract public void moveElementsToValue(Vector valueVector);

    public void initializeValueFrom(DataInput s) {
    }

    public void copyValueInto(RecordEntry re) {
    }

    public void storeTypeOn(DataOutput s) {
    }

    public void storeValueOn(DataOutput s) {
    }

    public Object getValue() {
        return null; //Subclasses must implement this
    }

}
