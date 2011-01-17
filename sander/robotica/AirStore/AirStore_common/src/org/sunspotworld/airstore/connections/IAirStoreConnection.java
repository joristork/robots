/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.connections;

import java.io.IOException;
import org.sunspotworld.airstore.IAirStore;


/**
 *
 * @author randy
 */
public interface IAirStoreConnection extends IAirStore {

    public void close();

    public void readAndForwardNextCommand(IAirStore a) throws IOException;

    /**
     * attempt to conenect to AirStoreService. Start it if necessary.
     * Wait for reurn message that it is started AND up to date.
     */
    public void assureServiceStartedAndUpToDate();

    /**
     * Service notifies client that it is now up to date.
     */
    public void notifyUpToDate();

}
