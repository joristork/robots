/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.connections;

import com.sun.squawk.io.mailboxes.Channel;
import com.sun.squawk.io.mailboxes.MailboxInUseException;
import com.sun.squawk.io.mailboxes.ServerChannel;
import java.io.IOException;

/** 
 *
 * @author randy
 */
public class AirStoreIICConnSvr implements IAirStoreLocalConnSvr { 

    private static boolean doPrints = false;
    public static final String MAILBOX_NAME = "AirStoreIICServer";

    private ServerChannel serverChannel;
 /**
     * @return   doPrints -- for debugging or other monitoring
     */
    public static boolean getDoPrints() {
        return doPrints;
    }

    /**
     * @param aDoPrints  -- for debugging or other monitoring
     */
    public static void setDoPrints(boolean aDoPrints) {
        doPrints = aDoPrints;
    }

     /**
     * Print a message only when doPrints is set true.
     *
     * @param msg Message to appear on standard out.
     */
    public void print(String msg) {
        if (doPrints) {
            System.out.println("[AirStoreIICConnSvr] " + msg);
        }
    }

    public IAirStoreConnection accept() {
        print(" accept > Starting IIC accept...");
        if (serverChannel == null) {
            try {
                serverChannel = ServerChannel.create(MAILBOX_NAME);
                print(" accept >  ... no IIC serverChannel, created: serverChannel = " + serverChannel);
            } catch (MailboxInUseException ex) {
                throw new RuntimeException(ex.toString());
            }
        } else {
                print(" accept >  ... IIC serverChannel already exists: serverChannel = " + serverChannel);
        }
        print(" accept >  ... serverChannel.isOpen() = " + serverChannel.isOpen());
        print(" accept >  ... about to hang on IIC serverChannel.accept");
        Channel aChannel = null;
        try {
            aChannel = serverChannel.accept();
            print(" accept >  ... got past the accept(). aChannel.isOpen() = " + aChannel.isOpen());
//            if (!aChannel.isOpen()) {
//                throw new RuntimeException("IIC channel was created in closed state.");
//            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        print(" accept >  ... retunring a new AirStoreIICConnection on aChannel = " + aChannel);
        return new AirStoreIICConnection(aChannel);
    }

    public void close() {
        serverChannel.close();
    }

    
}
