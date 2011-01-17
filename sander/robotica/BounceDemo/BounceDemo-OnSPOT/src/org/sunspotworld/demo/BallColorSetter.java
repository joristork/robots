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

package org.sunspotworld.demo;

import com.sun.spot.util.IEEEAddress;
import com.sun.spot.peripheral.radio.RadioFactory;
import java.util.Vector;
import javax.microedition.io.Datagram;

/**
 * An odd class whose puropse is to set the color the ball on this device so it
 * is unique among the other two devices (so it can only work for three max.)
 * This will happen when other devices are discoverd to be sending a
 * "PingForColor" message. The addresses of discovered devices is used to select
 * a color.
 * This addess is bigger than the other two addresses, in the middle of, or less
 * than the other two. The color is set accordingly. Once the color is set for
 * this application, it is not changed.
 * @author randy
 */
public class BallColorSetter {
    
    /**
     * The object who is told that ball color should be changed due to the presence of other devices.
     */
    private SPOTBounce client;
    /**
     * colletion of discovered addresses
     */
    private Vector addresses;
    /**
     * address of this device
     */
    private long thisAddressAsNumber;
    
    public BallColorSetter() {
        init();
    }
    
    /**
     * initialize a new instance
     */
    public void init(){
        thisAddressAsNumber = RadioFactory.getRadioPolicyManager().getIEEEAddress();
       // String addr = System.getProperty("IEEE_ADDRESS"); 
       // thisAddressAsNumber = new IEEEAddress(addr).asLong();
        addresses = new Vector();
    }
    
    /**
     * add the given address to the collection.
     * @param address the address to add
     */
    public void collectAddress(long address){
        if(addresses.contains(new Long(address))) return;
        addresses.addElement(new Long(address));
        assessAndInformClient();
    }
    
    /**
     * Figure ot what the situation is wrt this address vs. the others,
     * and inform the client of the resulting position, so that it can set
     * the initial ball color, if possible.
     */
    public void assessAndInformClient(){
        long total = 0;
        for (int i = 0; i < addresses.size(); i++) {
            long a = (long) ((Long) addresses.elementAt(i)).longValue();
            if( a > thisAddressAsNumber) total = total + 1;
            if( a < thisAddressAsNumber) total = total - 1;
        }
        if(addresses.size() > 0){
            boolean thatsEnoughOfThat = addresses.size() >= 2;
            client.initialBallColorCue(total, thatsEnoughOfThat);
        } 
    }
    
    /**
     * accessor for the client.
     * @return the client of this peculiar service.
     */
    public SPOTBounce getClient() {
        return client;
    }
    
    /**
     * sets the client
     * @param client The client with who this object communicates about the ball color stuff.
     */
    public void setClient(SPOTBounce client) {
        this.client = client;
    }
}
