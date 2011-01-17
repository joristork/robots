/*
 * Copyright (c) 2009, Sun Microsystems
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * 
 * Neither the name of the Sun Microsystems nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */

package com.sun.spot.wot.gateway;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class PendingRequestHandler extends Thread {
    Device dev = null;
    boolean done = false;

    PendingRequestHandler(Device dev) {
        this.dev = dev;
    }

    public void markDone() {
        done = true;
    }

    public void run() {
//        System.out.println("Starting pendingRequest handler for device " +
//                dev.getId());

        while (!done) {
            // get next unsatisfied request and handle it
//            synchronized (dev) {
                RequestQueueEntry rqe = dev.getRequestQueue().getNextPendingEntry();
                if (rqe != null) {
//                    System.out.println("Handling pending request ..." + rqe);
                    while (dev.isSleeping()) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(PendingRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    };
                    HttpResponse res = dev.getDeviceResponse(rqe.getRequest());
                    if (res.getStatus() != HttpResponse.SC_INTERNAL_SERVER_ERROR) {
                        dev.getRequestQueue().markComplete(rqe.getRequestId(),
                                res);
                    } else {
                        // device is probably sleeping
                        long waitPeriod = dev.getRemainingSleepDuration();
                        if (waitPeriod <= 0) {
                            waitPeriod = 0;
                            System.out.println("Adjusted wait period to 0");
                        }
                        System.err.println("Error communicating with device " +
                                dev.getId() + ", pausing for " +
                                waitPeriod + " ...");
                        try {
                            Thread.sleep(waitPeriod);
//
//                        try {
//                            dev.wait(waitPeriod);
//                            System.out.println("Thread for device " +
//                                    dev.getId() + " resuming ...");
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(PendingRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                        } catch (InterruptedException ex) {
                            Logger.getLogger(PendingRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                        }
//


//                        try {
//                            dev.wait(waitPeriod);
//                            System.out.println("Thread for device " +
//                                    dev.getId() + " resuming ...");
//                        } catch (InterruptedException ex) {
//                            Logger.getLogger(PendingRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
//                        }
                    }
                } else {
//                    System.out.println("No pending entry for device " +
//                            dev.getId() + ", pausing ...");
//                    try {
//                        dev.wait();
//                        System.out.println("Thread for device " +
//                                dev.getId() + " resuming ...");
//                    } catch (InterruptedException ex) {
//                        Logger.getLogger(PendingRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
//                    }
                }
            try {
//            }
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PendingRequestHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

}
