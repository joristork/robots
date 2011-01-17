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

package com.sun.spot.wot;

import com.sun.spot.util.Properties;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Enumeration;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 */
public abstract class WebApplication {
    String defaultPropStr = "uri:/\n" +
            "realm: default realm\n" +
            "tags:\n";

    Properties defaultProps = new Properties();
    Properties props = null;

    public abstract void init();

    public WebApplication() {
        try {
            defaultProps.load(new ByteArrayInputStream(defaultPropStr.getBytes()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        props = new Properties(defaultProps);
        init();
    }
    
    public WebApplication(String propertiesString) {
        try {
            defaultProps.load(new ByteArrayInputStream(defaultPropStr.getBytes()));
            props = new Properties(defaultProps);
            this.props.load(new ByteArrayInputStream(propertiesString.getBytes()));
            init();
        } catch (IOException ex) {
            System.err.println("WebApplication constructor caught " + ex);
            ex.printStackTrace();
        }
    }

    Properties getProperties() {
        return props;
    }

    String getPropertiesStr(String format) {
        StringBuffer sb = new StringBuffer();
        String pkey = getProperty("uri");
        String pval = null;

        if (format.equalsIgnoreCase("text")) {
            sb.append("<" + pkey + ">");
            Enumeration pkeys = getProperties().keys();
            while (pkeys.hasMoreElements()) {
                pkey = (String) pkeys.nextElement();
                pval = props.getProperty(pkey);
                if (pkey.equalsIgnoreCase("uri") || pval == null) {
                    continue;
                }
                sb.append(";" + pkey + "=" + pval);
            }
            sb.append("\n");
        } else {
            String name = props.getProperty("n");
            String desc = props.getProperty("d");
            String shortURL = props.getProperty("sh");

            sb.append("<tr>");
            sb.append("<td><a href=\".." + pkey + "\">" + pkey +
                    "</a>" +
                    ((shortURL == null) ? "" : 
                        (", <a href=\"../" + shortURL + "\">/" + shortURL + "</a>")) +
                    "</td>");

            if (desc != null) {
                sb.append("<td><a href=\"" + desc + "\">" +
                        ((name != null) ? name : desc) +
                        "</a></td>");
            } else {
                sb.append("<td>" + ((name != null) ? name : "")  + "</td>");
            }

            sb.append("</tr>\n");
        }

        return sb.toString();
    }

    private boolean valuesMatch(String val, String qval) {
        
        if (val.equalsIgnoreCase(qval)) return true;

        return false;
    }

    boolean matchesQuery(String queryStr) {
        if (queryStr == null) return true;
        int idx = queryStr.indexOf('=');
        if (idx < 0) return false;
        String qkey = queryStr.substring(0, idx);
        String qval = queryStr.substring(idx + 1);
        String val = getProperty(qkey);

        if (val != null) {
            return valuesMatch(val, qval);
        } 
        
        return false;
    }
    
    public String getProperty(String key) {
        String result = props.getProperty(key);

        return result;
    }

    public void setProperty(String key, String value) {
        props.setProperty(key, value);
    }

    public boolean isAuthorized(HttpRequest request) {
        return true;
    }

    public abstract HttpResponse processRequest(HttpRequest request)
            throws IOException;


    // Add a bunch of helper methods here? e.g URI encoding/decoding,
    // parameter parsing etc or maybe have them in a separate URI class
}
