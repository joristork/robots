/*
 * SunSpotHostApplication.java
 *
 * Created on Apr 5, 2009 5:37:15 PM;
 */

package org.sunspotworld.airstore.demos.scratch;

import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.IAirStoreListener;
import org.sunspotworld.airstore.records.KeyNotFoundException;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;


/**
 * Sample Sun SPOT host application
 */
public class SunSpotHostApplication implements IAirStoreListener {

    Scratch  scratch;
    String[] scratchVarNames = {"aX", "aY", "aZ", "aTotal", "light", "temp", "sw0", "sw1"};

    public void run() {

        scratch = new Scratch(); 

        AirStore.addListener(this, new RecordTemplate());

        while (true) {
            ScratchMessage msg = scratch.readMsg();
            System.out.println("scratch message was " + msg);
            if (msg.getMessageType() == ScratchMessage.SENSOR_UPDATE_MSG) {
                int val = Integer.parseInt(msg.getValue());
                AirStore.put(msg.getName(), val);
            }
        } 
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) {
        SunSpotHostApplication app = new SunSpotHostApplication();
        app.run();
    }

    public void notifyPut(Record r) {
        try {
            String varName = getScratchVar(r);
            if(varName != null){
                scratch.updateMsg(varName, "" + r.getInt(varName));
            }
        } catch (KeyNotFoundException ex) {
            Logger.getLogger(SunSpotHostApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void notifyTake(Record arg0) {
        
    }

    public void notifyReplace(Record out, Record r) {
        try {
            String varName = getScratchVar(r);
            if (varName != null) {
                scratch.updateMsg(varName, "" + r.getInt(varName));
            }
        } catch (KeyNotFoundException ex) {
            Logger.getLogger(SunSpotHostApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String getScratchVar(Record r){
        Vector keys = r.getKeys();
        for (int i = 0; i < keys.size(); i++) {
            String key = (String) keys.elementAt(i);
            for (int j = 0; j < scratchVarNames.length; j++) {
                String var = scratchVarNames[j];
                if(key.startsWith(var)) return key;
            }
        }
        return null;
    }
}
