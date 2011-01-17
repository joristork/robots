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
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author vgupta
 */
public class NanoAppServer {
    private Hashtable WebApps = new Hashtable(); // Java ME (SPOTs?) doesn't
    // have Hashmap. Use HashTable to map URI path to WebApplication
    private WebApplication defaultApp = null;
    private MetaApp metaApp = null;
    private boolean started = false;
    private Vector threads = new Vector();
    /**
     * Creates new application registry.
     */
    public NanoAppServer() {
        try {
            metaApp = new MetaApp("description: Meta-information about this device.");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        metaApp.setNanoAppServer(this);
        registerApp("/.well-known", metaApp);
    }

    protected WebApplication findMatchingAppAndAdjustPath(HttpRequest req) {
        WebApplication wa = null;
        String uri = req.getPathInfo();

        if ((uri == null) || !uri.startsWith("/")) {
            return null;
        }

        // Extract the first path segment in the URI
        int idx = uri.substring(1).indexOf("/");
        if (idx > 0) {
            uri = uri.substring(0, idx + 1);
        }

        // Find the matching URI ...
        //System.out.println("Matchin uri <" + uri + ">");
        wa = (WebApplication) WebApps.get(uri);        
        if (wa != null) { // We matched the registered URI ...
            // adjust path ...
            req.setPathInfo(req.getPathInfo().
                        substring(wa.getProperty("uri").length()));
            return wa;
        }
        
        // Try matching a short URI ...
        //System.out.println("Trying to match a short URI ...");
        Enumeration uris = WebApps.keys();
        WebApplication wa1 = null;
        while (uris.hasMoreElements()) {
            String nextURI = (String) uris.nextElement();
            String shortURI = null;
            wa1 = (WebApplication) WebApps.get(nextURI);
            if ((shortURI = wa1.getProperty("sh")) != null) {
                //System.out.println("Short URI is " + shortURI);
                if (shortURI.equalsIgnoreCase(uri.substring(1))) { // remove the leading slash
                    // adjust path ...
                    req.setPathInfo(req.getPathInfo().
                        substring(1 + wa1.getProperty("sh").length()));
                        // the extra 1 is for the leading slash
                    return wa1;
                }
            }
        }

        return defaultApp;
    }

    /**
     * Registers a webapplication to handle part of the URL subspace, e.g.
     * registerApp("/light", new LightApp()) cause all HTTP methods involving
     * any URL starting with "/light" to be directed to the LightApp
     * WebApplication for processing.
     *
     * @param path The URL prefix to be handled by the specified WebApplication
     * @param app  The WebApplication responsible for handling the specified
     *             URL prefix
     * @see WebApplication
     */
    public void registerApp(String path, WebApplication app) {
        if ((path != null) && (app != null)) {
            WebApplication prevApp = (WebApplication) WebApps.get(path);
            if (prevApp != null) {
                System.err.println("Path <" + path + "> already " +
                        "registered, overriding the mapping");
            }
            app.setProperty("uri", path);
            WebApps.put(path, app);
        }
    }

    /**
     * Configures a default WebApplication which handles requests to URLs
     * that do not match any of the other registered WebApplications. It is
     * not necessary to specify a default application and if none is specified
     * HTTP requests to unmatched URLs result in a response code of 404
     * (Not Found).
     * 
     * @param app  The default WebApplication responsible for handling any unmatched
     *             URLs
     * @see WebApplication
     */
    public void setDefaultApp(WebApplication app) {
        defaultApp = app;
    }

    protected HttpResponse getResponse(HttpRequest req) {
        HttpResponse res = new HttpResponse();
        WebApplication wa = null;
        int code = 0;
        Date d = new Date();

        System.out.println(d.toString() +
                ": NanoAppServer received request:\n" +
                "-----------------------------\n" +
                req +
                "-----------------------------");
        wa = findMatchingAppAndAdjustPath(req);
        if (wa != null) {
            try {
//                req.setPathInfo(req.getPathInfo().
//                        substring(wa.getProperty("uri").length()));
                if (wa.isAuthorized(req)) {
                    res = wa.processRequest(req);
                } else {
                        res.setStatus(HttpResponse.SC_UNAUTHORIZED);
                        res.setHeader("WWW-Authenticate", "Basic realm=\"" +
                            wa.getProperty("realm") + "\"");
                }
            } catch (Exception e) {
                System.err.println("Caught exception " + e.getMessage());
                e.printStackTrace();
                res.setStatus(HttpResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            //System.out.println("Matching webapplication is null");
            res.setStatus(HttpResponse.SC_NOT_FOUND);
        }

        // Attach a body with status information if body is null
        if (res.getBody() == null) {
            code = res.getStatus();
            res.setHeader("Content-Type", "text/plain");
            res.setBody((HttpResponse.getStatusCodeType(code) +
                    ": " + code + " " +
                    HttpResponse.getStatusString(code)).getBytes());
        }

        d.setTime(System.currentTimeMillis());
        System.out.println(d.toString() +
                ": NanoAppServer sent response:\n" +
                "-----------------------------\n" +
                res +
                "-----------------------------");

        return res;
    }

//    private String getAppsListHTML() {
//        Enumeration e = WebApps.keys();
//        StringBuffer sb = new StringBuffer();
//        while (e.hasMoreElements()) {
//            String key = (String) e.nextElement();
//            WebApplication wa = (WebApplication) WebApps.get(key);
//            if (wa.getProperty("uri").endsWith("well-known")) {
//                sb.append("<tr><td>" + wa.getProperty("uri") + "</td>" +
//                        "<td>" + wa.getProperty("description") + "</td></tr>\n");
//            } else {
//                sb.append("<tr><td><a href=\".." + wa.getProperty("uri") + "\">" +
//                        wa.getProperty("uri") + "</a></td>" +
//                        "<td>" + wa.getProperty("description") + "</td></tr>\n");
//            }
//        }
//
//        return sb.toString();
//    }

    private String getAppsList(String queryStr, String format) {
        Enumeration e = WebApps.keys();
        StringBuffer sb = new StringBuffer();
        String pkey = null;
        String pval = null;

//        System.out.println("Query string is " + queryStr);
        if (format.equalsIgnoreCase("html")) {
            sb.append("<table>\n<tr><th>URL</th><th>Name</th></tr>\n");
        }
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            WebApplication wa = (WebApplication) WebApps.get(key);
            if (wa.matchesQuery(queryStr)) {
                pkey = wa.getProperty("uri");
                if (pkey.endsWith("well-known")) {
                    continue;
                }
                sb.append(wa.getPropertiesStr(format));
            }
        }
        if (format.equalsIgnoreCase("html")) {
            sb.append("</table>");
        }
        return sb.toString();
    }

    /**
     * Adds a new thread. All of the threads associated with a NanoAppServer
     * are started when the start method is called on the NanoAppServer.
     * @param handler
     */
    public void addThread(Thread thread) {
        threads.addElement(thread);
        if (started) {
            thread.start();
        }
    }

    public void start() {
        Enumeration e = threads.elements();
        while (e.hasMoreElements()) {
            Thread t = (Thread) e.nextElement();
            t.start();
        }
        started = true;
    }
    
    class MetaApp extends WebApplication {
        NanoAppServer nas = null;

        public void init() {
        }

        public MetaApp(String propStr) throws IOException {
            super(propStr);
        }

        public HttpResponse processRequest(HttpRequest req)
                throws IOException {
            HttpResponse resp = new HttpResponse();
            String bodyStr = "";
            if (req.getPathInfo().equalsIgnoreCase("/r")) {
                if (nas != null) {
                    bodyStr = nas.getAppsList(req.getQueryString(), "html");
                }

                if (!bodyStr.equals("")) {
                    resp.setStatus(HttpResponse.SC_OK);
                    resp.setHeader("Content-Type", "text/html");
                    resp.setHeader("Cache-Control", "max-age=60"); // list of resources won't change very fast
                    resp.setBody(bodyStr.getBytes());
                } else {
                    resp.setStatus(HttpResponse.SC_NOT_FOUND);
                }
//            } else if (req.getPathInfo().equalsIgnoreCase("/") ||
//                    req.getPathInfo().equalsIgnoreCase("")) {
//                // Send redirect to resource list instead of NOT FOUND
//                resp.setStatus(HttpResponse.SC_MOVED_PERMANENTLY);
//                resp.setHeader("Location", "r");
            } else {
                resp.setStatus(HttpResponse.SC_NOT_FOUND);
            }
            
            return resp;
        }

        void setNanoAppServer(NanoAppServer nas) {
            this.nas = nas;
        }
    }
}