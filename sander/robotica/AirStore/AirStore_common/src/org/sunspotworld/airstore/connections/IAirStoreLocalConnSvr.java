/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.connections;

/**
 *
 * @author randy
 */
public interface IAirStoreLocalConnSvr {

    public IAirStoreConnection accept();

    public void close();
}
