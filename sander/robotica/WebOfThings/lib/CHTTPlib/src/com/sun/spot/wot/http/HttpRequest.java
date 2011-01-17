/*
 * Copyright (c) 2010, Sun Microsystems
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 * Neither the name of Sun Microsystems nor the names of its contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package com.sun.spot.wot.http;

import com.sun.squawk.util.StringTokenizer;
import com.sun.squawk.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.sun.spot.wot.utils.PrettyPrint;

/**
 *
 * @author Poornaprajna Udupi <poornaprajna.udupi@sun.com>
 * @author Vipul Gupta <vgupta@sun.com>
 */
public class HttpRequest extends HttpMessage {

    private String method;
    private String queryString = null;
    private String pathInfo = null;

    /**
     * Returns the name of the HTTP method with which this request was made,
     * for example, GET, POST, or PUT. Same as the value of the CGI variable
     * REQUEST_METHOD.
     *
     * @return a String specifying the name of the method with which this
     * request was made
     */
    public String getMethod() {
        return method; // should we copy?
    }

    public void setMethod(String method) {
        this.method = method; // should we copy?
    }

    /**
     * Returns the query string that is contained in the request URL after the
     * path. This method returns null if the URL does not have a query string.
     * Same as the value of the CGI variable QUERY_STRING.
     * @return a String containing the query string or null if the URL contains
     * no query string. The value is not decoded by the container.
     */
    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    /**
     * Returns any extra path information associated with the URL the client
     * sent when it made this request. The extra path information follows the
     * servlet path but precedes the query string and will start with a "/"
     * character.
     * This method returns null if there was no extra path information.
     * Same as the value of the CGI variable PATH_INFO.
     *
     * @return a String, decoded by the web container, specifying extra path
     * information that comes after the servlet path but before the query
     * string in the request URL; or null if the URL does not have any extra
     * path information
     */
    public String getPathInfo() {
        return pathInfo;
    }

    public void setPathInfo(String pathInfo) {
        this.pathInfo = pathInfo;
    }

    public void removeFirstPathSegment() {
        // Remove the first segment in the path
        int slashPos = pathInfo.indexOf("/", 1);
        if (slashPos > 1) {
            pathInfo = pathInfo.substring(slashPos);
        } else {
            pathInfo = "/";
        }
    }

    public int toByteArray(boolean compress, byte[] buf, int start)
            throws EncodingException {
        int idx = start;
        String urlStr = null;

        urlStr = pathInfo + ((queryString == null) ? "" : "?" + queryString);

        if (compress) {
            // Start with the request prefix
            //System.out.println("Adding prefix");
            System.arraycopy(REQUEST_START, 0, buf, 0, REQUEST_START.length);
            idx += REQUEST_START.length;

            //System.out.println("Encoding method " + getMethod());
            buf[idx++] = encodeMethod(getMethod());

            //System.out.println("Encoding uri " + urlStr);
            idx += encodeNullTerminatedString(urlStr, buf, idx);
        } else {
            // Simply serialize
            StringBuffer sb = new StringBuffer();

            // Encode the method
            //System.out.println("Encoding method " + getMethod());
            sb.append(getMethod());
            sb.append(" ");

            //System.out.println("Encoding uri " + urlStr);
            sb.append(urlStr);
            sb.append(" HTTP/1.1");
            sb.append("\r\n");

            byte[] tmp = sb.toString().getBytes();
            System.arraycopy(tmp, 0, buf, idx, tmp.length);
            idx += tmp.length;
        }

        // Encode headers and body
        idx += encodeHeadersAndBody(compress, buf, idx);

        return (idx - start);
    }

//    public void parseByteArray(byte[] buf, int start, int len) throws DecodingException {
//        int idx = start;
//        String uri = null;
//
//        if (matchBytes(buf, idx, REQUEST_START, 0, REQUEST_START.length)) {
//            setCompressed(true);
//
//            // We need to deserialize a compressed request
//            idx += 2;
//
//            // Read the method
//            //System.out.println("Reading method");
//            setMethod(decodeMethod(buf[idx++]));
//
//            // Read the URI
//            //System.out.println("Reading URI");
//            uri = decodeNullTerminatedString(buf, idx);
//            while (buf[idx] != 0x00) idx++;
//        } else {
//            // Deserialize an uncompressed request
//            String line = null;
//
//            setCompressed(false);
//            BufferedReader in = new BufferedReader(new
//                    InputStreamReader(new ByteArrayInputStream(buf, idx, len)));
//            try {
//                if ((line = in.readLine()) != null) {
//                    StringTokenizer st = new StringTokenizer(line, " ");
//                    if (st.countTokens() >= 2) {
//                        setMethod(st.nextToken());
//                        uri = st.nextToken();
//                    } else {
//                        throw new DecodingException("Request starts with " + line);
//                    }
//                }
//            } catch (IOException ex) {
//                throw new DecodingException(ex.getMessage() + " in HttpRequest.parse");
//            }
//
//            // Move index past the first line
//            idx = 5;
//            while ((buf[idx - 1] != '\r') || (buf[idx] != '\n')) {
//                idx++;
//            }
//        }
//
//        // Split URI into path and query components
//        int tmp = uri.indexOf("?");
//        if (tmp < 0) {
//            setPathInfo(new String(uri));
//            setQueryString(null);
//        } else {
//            setPathInfo(uri.substring(0, tmp));
//            setQueryString(uri.substring(tmp + 1,
//                    uri.length()));
//        }
//
//        idx++;
//        parseHeadersAndBody(buf, idx, (len - (idx - start)));
//    }

    public String toString() {
        String result = null;

        result = getMethod() + " " + getPathInfo();
        if (getQueryString() != null) {
            result += "?" + getQueryString();
        }
        result += "\r\n";

        result += super.toString();

        return result;
    }

    public static HttpRequest parse(byte[] buf, int start, int len) throws DecodingException {
        HttpRequest result = new HttpRequest();
        result.parse(new ByteArrayInputStream(buf, start, len));
        return result;
    }

    public void parse(InputStream is) throws DecodingException {
        boolean isCompressed = false;
        String uri = null;
        //System.out.println("Parsing request ...");
        byte[] buf = new byte[MAX_HEADER_SIZE];
        int idx = 0;

        try {
            for (int i = 0; i < REQUEST_START.length; i++) {
                buf[idx++] = (byte) is.read();
            }
            isCompressed = matchBytes(buf, 0,
                    REQUEST_START, 0, REQUEST_START.length);
        } catch (IOException ioe) {
            throw new DecodingException("Inputstream ended while matching " +
                    "REQUEST_START");
        }

        if (isCompressed) {
            setCompressed(true);
            try {
                // Read the method
                //System.out.println("Reading method");
                setMethod(decodeMethod((byte) is.read()));
                // Read the URI
                //System.out.println("Reading URI");
                uri = readNullTerminatedString(is);
            } catch (IOException ex) {
                throw new DecodingException("Could not read compressed " +
                        "HTTP request");
            }
        } else {
            // Deserialize an uncompressed request
            String line = null;

            //System.out.println("Reading uncompressed request ...");
            setCompressed(false);
            try {
                idx--;
                do {
                    buf[++idx] = (byte) is.read();
                } while ((idx < 2) || (buf[idx - 1] != 0x0d) || (buf[idx] != 0x0a));
                //System.out.println("Buffer is: " + PrettyPrint.prettyPrint(buf, 0, idx + 1));
                BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf, 0, idx)));
                if ((line = in.readLine()) != null) {
                    //System.out.println("Read line ... " + line);
                    StringTokenizer st = new StringTokenizer(line, " ");
                    if (st.countTokens() >= 2) {
                        setMethod(st.nextToken());
                        uri = st.nextToken();
                    } else {
                        throw new DecodingException("Request starts with " + line);
                    }
                }
                in.close();
            } catch (IOException ex) {
                System.out.println("Caught " + ex + " in HttpRequest.parse()");
                throw new DecodingException(ex.getMessage() + " in HttpRequest.parse");
            }
        }

        //System.out.println("Splitting URI  ... [" + uri + "]");
        // Split URI into path and query components
        int tmp = uri.indexOf("?");
        if (tmp < 0) {
            setPathInfo(new String(uri));
            setQueryString(null);
        } else {
            setPathInfo(uri.substring(0, tmp));
            setQueryString(uri.substring(tmp + 1,
                    uri.length()));
        }

        //System.out.println("Parsing headers and body  ...");
        parseHeadersAndBody(is);
    }
}
