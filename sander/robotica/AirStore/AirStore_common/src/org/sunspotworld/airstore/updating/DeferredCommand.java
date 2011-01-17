/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.updating;

import java.util.Vector;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.service.AirStoreService;

/**
 * A DeferredCommand is only used during updating (when an AirStore replica believes
 * it may be out of sync with others.)
 *
 * DeferredCommands form a linked list, so they can be kept in order for
 * proper execution when the time comes (when updating has been completed).
 *
 * During updating a DeferredCommand is created for every incoming local command.
 * It is important that commands coming in from other replicas are NOT deferred,
 * as that is how the local AirStore replica becomes up to date.
 * 
 * @author randy
 */
public class DeferredCommand {
    private int command; //See AirStoreService class static constants
    private DeferredCommand nextCommand;
    private Vector args = new Vector();
    private boolean isWaiting = true;

    public DeferredCommand(int c, Vector v){
        isWaiting = true;
        command = c;
        args = v;
    }

    public DeferredCommand(int c, Record r){
        isWaiting = true;
        command = c;
        args.addElement(r);
    }

    public Vector hang() {
        synchronized (this) {
            while (isWaiting) {
                try {
                    wait(); //wait() is a special call that gives up the monitor on this and hangs until notify() or notifyAll();.
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
        //System.out.println("[DeferredCommand > hang()] Escaped beyond wait(), about to exectute: " + this);
        return execute();
    }

    public void proceed(){
       // System.out.println("[DeferredCommand > proceed()] Proceed called on " + this);
        synchronized (this) {
            isWaiting = false;
            notifyAll();
        }
    }

    /**
     *  Execute this command, AND the next in order.
     *  So goes all the way down the list recursively.
     *
     * @return Return vaule from the call, or an empty Vector for voids.
     */
    public Vector execute(){
        Vector reply = new Vector();
        switch(command){
            case AirStoreService.PUT:
                AirStoreService.getInstance().doPut(getFirstArg(), true);
                break;
            case AirStoreService.GETALLMATCHES:
                reply = AirStoreService.getInstance().doGetAllMatches((RecordTemplate) getFirstArg());
                break;
            case AirStoreService.TAKEALLMATCHES:
                reply = AirStoreService.getInstance().doTakeAllMatches((RecordTemplate) getFirstArg(), true);
                break;
        }
        // Our side effects to AirStore are complete, now let the next in the list execute.
        // System.out.println("[DefferedCommand > execute()] got reply = " + reply);
        if(nextCommand != null) nextCommand.proceed();
        // Notice that the return values up the list happen in reverse order.
        // It is assuemd this is not a problem
        // as they are all returning into distinct threads anyway.
        return reply;
    }

    /**
     *
     * Add a new DeferredCommand to the end of the list.
     *
     * @param c
     */
    public void addLast(DeferredCommand c){
        //System.out.println("[DeferredCommand > addLast] Adding " + c + " to " + this);
        if(nextCommand == null){
            nextCommand = c;
        } else {
            nextCommand.addLast(c);
        }
    }

    public String toString(){
        String s = "";
        switch(command){
            case AirStoreService.PUT:
                s = "PUT: ";
                break;
            case AirStoreService.GETALLMATCHES:
                s = "GETALLMATCHES: ";
                break;
            case AirStoreService.TAKEALLMATCHES:
                s = "GETALLMATCHES: ";
                break;
        }
        s += getFirstArg();
        if(args.size() > 1) s += ", ...";
        return s;
    }
    /**
     * convenience method
     */
    public Record getFirstArg(){
        return (Record) args.elementAt(0);
    }

}
