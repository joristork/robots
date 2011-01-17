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
/**
 * Parts of this file use code from NanoHTTPD which has the following copyright
 * notice.
 * Copyright © 2001,2005-2010 Jarno Elonen <elonen@iki.fi>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * - Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 * - The name of the author may not be used to endorse or promote products
 *   derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO
 * EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.sun.spot.wot;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.StringTokenizer;
import java.net.URLEncoder;
import java.util.Hashtable;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.wot.http.HttpResponse;

/**
 *
 * @author vgupta
 *
 */
public class FileServer extends WebApplication {
    boolean allowDirectoryListing = false;
    File rootDir = null;
    
    /**
     * Common mime types for dynamic content
     */
    public static final String MIME_PLAINTEXT = "text/plain",
            MIME_HTML = "text/html",
            MIME_DEFAULT_BINARY = "application/octet-stream";

    public FileServer(String properties) {
        super(properties);
        rootDir = new File(getProperty("root"));
        allowDirectoryListing = (getProperty("allowdirectorylisting") != null);
        if (!rootDir.isDirectory()) {
            rootDir = null;
        }
    }


    /**
     * Hashtable mapping (String)FILENAME_EXTENSION -> (String)MIME_TYPE
     */
    private static Hashtable theMimeTypes = new Hashtable();

    static {
        StringTokenizer st = new StringTokenizer(
                "htm		text/html " +
                "html		text/html " +
                "txt		text/plain " +
                "asc		text/plain " +
                "css		text/css " +
                "js		text/javascript " +
                "gif		image/gif " +
                "jpg		image/jpeg " +
                "jpeg		image/jpeg " +
                "png		image/png " +
                "ico		image/vnd.microsoft.icon " +
                "mp3		audio/mpeg " +
                "m3u		audio/mpeg-url " +
                "pdf		application/pdf " +
                "doc		application/msword " +
                "ogg		application/x-ogg " +
                "zip		application/octet-stream " +
                "exe		application/octet-stream " +
                "class		application/octet-stream ");
        while (st.hasMoreTokens()) {
            theMimeTypes.put(st.nextToken(), st.nextToken());
        }
    }

    /**
     * URL-encodes everything between "/"-characters.
     * Encodes spaces as '%20' instead of '+'.
     */
    private String encodeUri(String uri) {
        String newUri = "";
        StringTokenizer st = new StringTokenizer(uri, "/ ", true);
        while (st.hasMoreTokens()) {
            String tok = st.nextToken();
            if (tok.equals("/")) {
                newUri += "/";
            } else if (tok.equals(" ")) {
                newUri += "%20";
            } else {
                //newUri += URLEncoder.encode(tok);
                // For Java 1.4 you'll want to use this instead:
                try {
                    newUri += URLEncoder.encode( tok, "UTF-8" );
                } catch ( UnsupportedEncodingException uee ) {
                    uee.printStackTrace();
                }
            }
        }
        return newUri;
    }

    public HttpResponse processRequest(HttpRequest req) throws IOException {
        HttpResponse resp = new HttpResponse();
        // Make sure we won't die of an exception later
        if (rootDir == null) {
            resp.setStatus(HttpResponse.SC_INTERNAL_SERVER_ERROR);
            return resp;
        }

        String uri = req.getPathInfo();
        uri = uri.trim().replace(File.separatorChar, '/');
        if (uri.indexOf('?') >= 0) {
            uri = uri.substring(0, uri.indexOf('?'));
        }

        // Prohibit getting out of current directory
        if (uri.startsWith("..") || uri.endsWith("..") ||
                uri.indexOf("../") >= 0) {
            resp.setStatus(HttpResponse.SC_FORBIDDEN);
            return resp;
        }
       
        File f = new File(rootDir, uri);
        if (!f.exists()) {
            resp.setStatus(HttpResponse.SC_NOT_FOUND);
            return resp;
        }

        // List the directory, if necessary
        if (f.isDirectory()) {
            // Browsers get confused without '/' after the
            // directory, send a redirect.
            if (!uri.endsWith("/")) {
                uri += "/";
                resp.setStatus(HttpResponse.SC_MOVED_PERMANENTLY);
                resp.setHeader("Location", uri);
                return resp;
            }

            // First try index.html and index.htm
            if (new File(f, "index.html").exists()) {
                f = new File(rootDir, uri + "/index.html");
            } else if (new File(f, "index.htm").exists()) {
                f = new File(rootDir, uri + "/index.htm");
            } // No index file, list the directory
            else if (allowDirectoryListing) {
                String[] files = f.list();
                String msg = "<html><body><h1>Directory " + uri + "</h1><br/>";

                if (uri.length() > 1) {
                    String u = uri.substring(0, uri.length() - 1);
                    int slash = u.lastIndexOf('/');
                    if (slash >= 0 && slash < u.length()) {
                        msg += "<b><a href=\"" + uri.substring(0, slash + 1) + "\">..</a></b><br/>";
                    }
                }

                for (int i = 0; i < files.length; ++i) {
                    File curFile = new File(f, files[i]);
                    boolean dir = curFile.isDirectory();
                    if (dir) {
                        msg += "<b>";
                        files[i] += "/";
                    }

                    msg += "<a href=\"" + encodeUri(uri + files[i]) + "\">" +
                            files[i] + "</a>";

                    // Show file size
                    if (curFile.isFile()) {
                        long len = curFile.length();
                        msg += " &nbsp;<font size=2>(";
                        if (len < 1024) {
                            msg += curFile.length() + " bytes";
                        } else if (len < 1024 * 1024) {
                            msg += curFile.length() / 1024 + "." + (curFile.length() % 1024 / 10 % 100) + " KB";
                        } else {
                            msg += curFile.length() / (1024 * 1024) + "." + curFile.length() % (1024 * 1024) / 10 % 100 + " MB";
                        }

                        msg += ")</font>";
                    }
                    msg += "<br/>";
                    if (dir) {
                        msg += "</b>";
                    }
                }

                resp.setStatus(HttpResponse.SC_OK);
                resp.setHeader("Content-Type", "text/html");
                resp.setBody(msg.getBytes());
                return resp;
            } else {
                resp.setStatus(HttpResponse.SC_FORBIDDEN);
                return resp;
            }
        }

        try {
            // Get MIME type from file name extension, if possible
            String mime = null;
            int dot = f.getCanonicalPath().lastIndexOf('.');
            if (dot >= 0) {
                mime = (String) theMimeTypes.get(f.getCanonicalPath().substring(dot + 1).toLowerCase());
            }
            if (mime == null) {
                mime = MIME_DEFAULT_BINARY;
            }

            // Support (simple) skipping:
            long startFrom = 0;
//            String range = header.getProperty("range");
//            if (range != null) {
//                if (range.startsWith("bytes=")) {
//                    range = range.substring("bytes=".length());
//                    int minus = range.indexOf('-');
//                    if (minus > 0) {
//                        range = range.substring(0, minus);
//                    }
//                    try {
//                        startFrom = Long.parseLong(range);
//                    } catch (NumberFormatException nfe) {
//                    }
//                }
//            }

            FileInputStream fis = new FileInputStream(f);
            fis.skip(startFrom);


            resp.setStatus(HttpResponse.SC_OK);
            resp.setHeader("Content-Type", mime);

            byte[] fileBytes = new byte[(int) f.length()];
            byte[] buff = new byte[2048];
            int cnt = 0;
            if (fis != null) {
                while (true) {
                    int read = fis.read(buff, 0, 2048);
                    if (read <= 0) {
                        break;
                    }
                    System.arraycopy(buff, 0, fileBytes, cnt, read);
                    cnt += read;
                    //out.write(buff, 0, read);
                }
            }
//            out.flush();
//            out.close();
            if (fis != null) {
                fis.close();
            }

            resp.setBody(fileBytes, 0, cnt);
            return resp;
//            Response r = new Response(HTTP_OK, mime, fis);
//            r.addHeader("Content-length", "" + (f.length() - startFrom));
//            r.addHeader("Content-range", "" + startFrom + "-" +
//                    (f.length() - 1) + "/" + f.length());
//            return r;
        } catch (IOException ioe) {
                resp.setStatus(HttpResponse.SC_FORBIDDEN);
                return resp;
        }
    }

    @Override
    public void init() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
