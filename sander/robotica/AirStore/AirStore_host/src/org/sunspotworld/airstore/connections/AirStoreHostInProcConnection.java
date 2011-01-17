/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.connections;

import com.sun.spot.util.Utils;
import java.io.IOException; 
import java.util.Vector;
import org.sunspotworld.airstore.IAirStore;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.service.AirStoreService;

/**
 *
 * @author randy
 */
public class AirStoreHostInProcConnection implements IAirStoreConnection {
    
    static private AirStoreHostInProcConnection instance = new AirStoreHostInProcConnection();
    
    public static AirStoreHostInProcConnection getInstance(){
        return instance;
    }

    public void close() {
        //Nothing to do.
    }

    public void readAndForwardNextCommand(IAirStore arg0) throws IOException {
        Utils.sleep(1000);
    }

    public void doPut(Record r, boolean isLocal) {
        //This isRunning check shouldn't be necessary in theory.
        if( ! AirStoreService.getInstance().isRunning())AirStoreService.main(new String[0]);
         AirStoreService.getInstance().doPut(r, isLocal);
    }

    public Vector doGetAllMatches(RecordTemplate rt) {
        //This isRunning check shouldn't be necessary in theory.
        if( ! AirStoreService.getInstance().isRunning())AirStoreService.main(new String[0]);
        return AirStoreService.getInstance().doGetAllMatches(rt);
    }

    public Vector doTakeAllMatches(RecordTemplate rt, boolean isLocal) {
        //This isRunning check shouldn't be necessary in theory.
        if( ! AirStoreService.getInstance().isRunning())AirStoreService.main(new String[0]);
        return AirStoreService.getInstance().doTakeAllMatches(rt, isLocal);
    }

    public void assureServiceStartedAndUpToDate() {
        if( ! AirStoreService.getInstance().isRunning())AirStoreService.main(new String[0]);
        AirStoreService.getInstance().addUpToDateWaiter(this);
        synchronized(this){
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void notifyUpToDate() {
        synchronized(this){ notifyAll();}
    }

    

}
