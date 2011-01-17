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
import java.io.IOException;
import java.util.Vector;


/**
 *  A RecordTemplate is (possibly indirectly) created by the user for purposes
 *  of retrieving matching Records from AirStore.
 *  
 *  Matching is currently based on keys of the entries.  
 *   
 */
public class RecordTemplate extends Record {


    public RecordTemplate(){}

    /**
     * Create a RecordTemplate containing exactly one RecordEntryAny with the given key.
     * @param keyName
     */
    public RecordTemplate(String keyName){
        RecordEntryAny rea = new RecordEntryAny();
        rea.setKey(keyName);
        addEntry(rea);
    }

    /**
     * expect the caller may have to cast the return value to a RecordTemplate
     *
     * @param s
     * @return an instance of class RecordTemplate, but we can't state that in the signiature due
     *  to odd reality of static type declaration rules.
     */
    public static Record readFrom(DataInput s){
       RecordTemplate r = new RecordTemplate();
       int nRecordEntries = 0;
        try {
            nRecordEntries = s.readInt();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
       for(int i = 0; i < nRecordEntries; i++){
           RecordEntry re = RecordEntry.readFrom(s);
           r.addEntry(re);
       }
       return r;
   }
    /**
     * Return true if each RecordEntry in this RecordTemplate matches some RecordEntry in r.
     * If you read that carefully, you will notice that RecordTemplate with say,
     * just one RecordEntry can match a large Record with many RecordEntries.
     * An "empty" RecordTemplate matches any record.
     * @param r the record about which one wonders "does this template match r?"
     * @return true if there is a match, false otherwise.
     */
    public boolean matches(Record r){
        print("///////// TESTING A TEMPLATE AGAINST A RECORD ///////////////");
        Vector entires  = getEntries();
        Vector rEntries = r.getEntries();
        //Do all pairwise comparrisons.
        for (int i = 0; i < entires.size(); i++) {
            RecordEntry re  = (RecordEntry) entires.elementAt(i);
            boolean found = false;
            print("///////// TESTING MATCH OF " + re + " AND... "  );
            for (int j = 0; j < rEntries.size(); j++) {
                RecordEntry rre  = (RecordEntry) rEntries.elementAt(j);
                print("                             ... " + rre);
                if(rre.matchesEntry(re)) {
                    found = true;
                    print("///////// SO FAR SO GOOD TRUE");
                }
            }
            if(!found) print("///////// NO MATCH FOR: " + re + " RETURNING FALSE");
            else print("///////// THERE WAS A MATCH FOR " + re );
            if(!found) return false;
        } 
        return true;
    }

    public void add(String key){
       RecordEntryAny r = new RecordEntryAny();
       r.setKey(key);
       addEntry(r);
    }
    
}
