/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore;

import java.util.Vector;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;

/**
 * Minimal interface for any class that implements, forwards, or proxies to an AirStore service.
 *
 * For cenveince, the typical interface to AirStore (e.g.: see class AirStore) is
 * much wider than this, but it all comes down to these methods.
 *
 * @author randy
 */
public interface IAirStore {

    /*
     * Some implementations need to know if a put originates on the same VM, or is comeing remotely.
     * Other implementations will ignore the isLocal flag.
     */
    public void doPut(Record r, boolean isLocal);

    public Vector doGetAllMatches(RecordTemplate t);

    public Vector doTakeAllMatches(RecordTemplate t, boolean isLocal);

}
