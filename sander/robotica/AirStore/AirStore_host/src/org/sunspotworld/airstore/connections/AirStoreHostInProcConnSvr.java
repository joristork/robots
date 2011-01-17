/*
 * AirStoreHostInProcConnSvr.java
 *
 * Created on Nov 24, 2008 4:41:53 PM;
 */

package org.sunspotworld.airstore.connections;

import com.sun.spot.util.Utils; 


 
/**
 * The task of serving up conncetions doesn't really exist on the host in the
 * case of the server being in the same process like this.
 */
public class AirStoreHostInProcConnSvr implements IAirStoreLocalConnSvr {

    private boolean hasAccepted = false;

    /**
     * The first time called, immediately return the sole instance of AirStoreHostInProcConnection.
     * After that, jsut ahng.
     * @return
     */
    public IAirStoreConnection accept() {
        if (!hasAccepted) {
            hasAccepted = true;
            return AirStoreHostInProcConnection.getInstance();
        } else {

            Utils.sleep(Long.MAX_VALUE);

            return AirStoreHostInProcConnection.getInstance();
        }
    }

    public void close() {
    }
}
