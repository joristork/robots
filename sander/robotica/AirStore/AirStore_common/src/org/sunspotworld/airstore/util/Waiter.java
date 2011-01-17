/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.util;

import java.util.Vector;
import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.IAirStoreListener;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;

/**
 *
 * @author randy
 */
public class Waiter implements IAirStoreListener {

    RecordTemplate rt;
    boolean isReady = false;

    public void notifyPut(Record r) {
        isReady = true;
        synchronized(this){ notifyAll();}
        AirStore.removeListener(this, rt);
    }

    public void notifyTake(Record r) {
    }

    public void notifyReplace(Record out, Record in) {
        isReady = true;
        synchronized(this){ notifyAll();}
        AirStore.removeListener(this, rt);
    }

    public Vector waitFor(RecordTemplate templ){
        rt = templ;
        Vector v = AirStore.getAllMatches(rt);
        if(v.isEmpty()){
            AirStore.addListener(this, rt);
            //The following 9 lines are the advertised way to say "this.wait()";
            synchronized(this){
                while(!isReady){
                    try {
                        wait();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
        return v;
    }
}
