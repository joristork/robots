/*
 * Copyright (c) 2006 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 **/
package org.sunspotworld.airstore.connections;

import com.sun.spot.util.Utils;
import java.io.IOException;
import org.sunspotworld.airstore.IAirStoreListener;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.service.AirStoreService;
import org.sunspotworld.airstore.service.ListenNotifyService;

/**
 * This class is instantiated when the ListenNotifyservice is on the host in the
 * same process as the clients of that service. However, the class is not
 * really uses as a channel, bacause it can talk directly to the service.
 * @author randy
 */
public class ListenNotifyHostInProcConnection implements IListenNotifyConnection {

    static private ListenNotifyHostInProcConnection instance = new ListenNotifyHostInProcConnection();
    private IAirStoreListener listener;
    private RecordTemplate recordTemplate;

    public void close() {
    }

    /**
     * See comment on this class. This method is not used in this class as a
     * channel is not necessary. If and when the method is called, it should
     * sleep to avoid returnng immediately  and eating up CPU
     * @param arg0
     * @throws IOException
     */
    public void readAndForwardNextCommandClientSide(IAirStoreListener arg0) throws IOException {
        Utils.sleep(100000);
    }

    /**
     * See comment on this class. This method is not used in this class as a
     * channel is not necessary. If and when the method is called, it should
     * sleep to avoid returnng immediately and eating up CPU
     * @param arg0
     * @throws IOException
     */
    public void readAndForwardNextCommandServerSide(ListenNotifyService arg0) throws IOException {
        Utils.sleep(10000);
    }

    public void addListener(IAirStoreListener asl, RecordTemplate t) {
        listener = asl;
        if (!ListenNotifyService.getInstance().isRunning()) {
            AirStoreService.main(new String[0]);
        }
        ListenNotifyService.getInstance().addListener(this, t);

    }

    public void notifyPut(Record arg0) {
        listener.notifyPut(arg0);
    }

    public void notifyTake(Record arg0) {
        listener.notifyTake(arg0);
    }

    public void removeListener() {
        if (!ListenNotifyService.getInstance().isRunning()) {
            AirStoreService.main(new String[0]);
        }
        ListenNotifyService.getInstance().removeListener(this);

    }

    public void listenForServerCommands() {
    }

    public void notifyReplace(Record out, Record in) {
        listener.notifyReplace(out, in);
    }

    /**
     * @return the recordTemplate
     */
    public RecordTemplate getRecordTemplate() {
        return recordTemplate;
    }

    /**
     * @param recordTemplate the recordTemplate to set
     */
    public void setRecordTemplate(RecordTemplate recordTemplate) {
        this.recordTemplate = recordTemplate;
    }

    /**
     * @return the listener
     */
    public IAirStoreListener getListener() {
        return listener;
    }

    public String toString(){
        return "a ListenNotifyHostInProcConnection";
    }
}
