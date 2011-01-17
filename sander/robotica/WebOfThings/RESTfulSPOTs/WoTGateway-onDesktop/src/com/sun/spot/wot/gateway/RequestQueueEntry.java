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

import java.util.Date;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public class RequestQueueEntry {
    private String requestId = null;
    private HttpRequest request = null;
    private HttpResponse response = null;
    private long requestReceivedTime = -1;
    private long requestCompletionTime = -1;

    RequestQueueEntry(String requestid, long rcvTime, HttpRequest req) {
        this.requestId = requestid;
        requestReceivedTime = rcvTime;
        request = req;        
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        //sb.append("-------------------------------\n");
        sb.append("Id: " + getRequestId() + "\n");
        sb.append("Request: " + getRequest().getMethod() + " " +
                getRequest().getPathInfo() + "\n");
        sb.append("Recieved: " + new Date(getRequestReceivedTime()) + "\n");

        if (getResponse() == null) {
            sb.append("Status: " + "Pending" + "\n");
        } else {
            sb.append("Status: " + "Completed (" +
                    HttpResponse.getStatusString(getResponse().getStatus()) +
                    ") \n");
            sb.append("Completed: " + new Date(getRequestCompletionTime()) + "\n");
        }
        sb.append("-------------------------------\n");
        
        return sb.toString();
    }

    /**
     * @return the requestId
     */
    public String getRequestId() {
        return requestId;
    }

    /**
     * @return the request
     */
    public HttpRequest getRequest() {
        return request;
    }

    /**
     * @return the response
     */
    public HttpResponse getResponse() {
        return response;
    }

    /**
     * @return the requestReceivedTime
     */
    public long getRequestReceivedTime() {
        return requestReceivedTime;
    }

    /**
     * @return the requestCompletionTime
     */
    public long getRequestCompletionTime() {
        return requestCompletionTime;
    }

    /**
     * @param requestCompletionTime the requestCompletionTime to set
     */
    public void setRequestCompletionTime(long requestCompletionTime) {
        this.requestCompletionTime = requestCompletionTime;
    }

    /**
     * @param response the response to set
     */
    public void setResponse(HttpResponse response) {
        this.response = response;
    }
}
