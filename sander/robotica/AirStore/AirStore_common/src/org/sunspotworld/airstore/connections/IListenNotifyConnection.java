/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.connections;

import java.io.IOException;
import org.sunspotworld.airstore.IAirStoreListener;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.service.ListenNotifyService;

/**
 *
 * @author Veda
 */
public interface IListenNotifyConnection extends IAirStoreListener {

    public void close();

    public void listenForServerCommands();

    public void notifyPut(Record r );

    public void notifyReplace(Record rec, Record r);

    public void notifyTake(Record r);

    public void readAndForwardNextCommandClientSide(IAirStoreListener l) throws IOException;

    public void readAndForwardNextCommandServerSide(ListenNotifyService s) throws IOException;

    public void addListener(IAirStoreListener a , RecordTemplate t);

    public void removeListener();

    public IAirStoreListener getListener();

    public void setRecordTemplate(RecordTemplate rt);

    public RecordTemplate getRecordTemplate();

}
