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

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
class RequestQueue {
    private static Random rand = new Random();
    HashMap requestMap = new HashMap();
    Queue requests = new LinkedList();
    int pending = 0;
    int completed = 0;

    private String generateId() {
        long val = rand.nextLong();

        if (val < 0) val = 0L - val;
        // XXX: Make sure this is really unique (add device Id and/or time)
        return "" + val;
    }

    synchronized String insertRequest(HttpRequest req) {
        String id = generateId();
        RequestQueueEntry rqe = new RequestQueueEntry(id,
                System.currentTimeMillis(), req);

        if (requests.offer(rqe)) {
            requestMap.put(id, rqe);
            pending++;
            return id;
        }

        return null;
    }

    synchronized void markComplete(String id, HttpResponse res) {
        RequestQueueEntry rqe = (RequestQueueEntry) requestMap.get(id);
        if (rqe != null) {
//            System.out.println("Marking request " + id + " completed ...");
            rqe.setResponse(res);
            rqe.setRequestCompletionTime(System.currentTimeMillis());
            pending--;
            completed++;
        } else {
            System.err.println("Request " + id + " not found ...");
        }
    }

    synchronized RequestQueueEntry findRequest(String id) {
       RequestQueueEntry rqe = (RequestQueueEntry) requestMap.get(id);
       return rqe;
    }

    synchronized RequestQueueEntry removeRequest(String id) {
        RequestQueueEntry rqe = (RequestQueueEntry) requestMap.get(id);
        if (rqe != null) {
            if (requests.poll() != null) {
                if (rqe.getResponse() == null) {
                    pending--;
                } else {
                    completed--;
                }
                requestMap.remove(id);
            }
        }

        return rqe;
    }

    synchronized public String getSummary() {
        StringBuffer sb = new StringBuffer();
        int size = requests.size();
        if (size == 0)
            sb.append("empty");
        else {
            sb.append(size + " entries" + "<br/>");            
            sb.append("Pending: " + pending + "<br/>");
            sb.append("Completed: " + completed);
        }

        return sb.toString();
    }

    synchronized public String toString() {
        StringBuffer sb = new StringBuffer();

        Iterator i = requests.iterator();
        while (i.hasNext()) {
            RequestQueueEntry rqe = (RequestQueueEntry) i.next();
            sb.append(rqe.toString());
        }

        return sb.toString();
    }

    synchronized RequestQueueEntry getNextPendingEntry() {        
        Iterator i = requests.iterator();
        while (i.hasNext()) {
            RequestQueueEntry result = (RequestQueueEntry) i.next();
            if (result.getResponse() == null) return result;
        }

        //System.out.println("getNextpending returned null");
        return null;
    }

}
