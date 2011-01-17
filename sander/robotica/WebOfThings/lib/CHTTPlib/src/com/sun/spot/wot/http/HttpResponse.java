/*
 * Copyright (c) 2009, Sun Microsystems
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

import com.sun.squawk.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import com.sun.squawk.util.StringTokenizer;
import java.io.InputStream;
import com.sun.spot.wot.utils.PrettyPrint;

/**
 *
 * @author Poornaprajna Udupi <poornaprajna.udupi@sun.com>
 * @author Vipul Gupta <vipul.gupta@sun.com>
 */
public class HttpResponse extends HttpMessage {
    
    public static final int	SC_ACCEPTED = 202;
    public static final int	SC_BAD_GATEWAY = 502;
    public static final int	SC_BAD_REQUEST = 400;
    public static final int	SC_CONFLICT = 409;
    public static final int	SC_CONTINUE = 100;
    public static final int	SC_CREATED = 201;
    public static final int	SC_EXPECTATION_FAILED = 417;
    public static final int	SC_FORBIDDEN = 403;
    public static final int	SC_FOUND = 302;
    public static final int	SC_GATEWAY_TIMEOUT = 504;
    public static final int	SC_GONE = 410;
    public static final int	SC_HTTP_VERSION_NOT_SUPPORTED = 505;
    public static final int	SC_INTERNAL_SERVER_ERROR = 500;
    public static final int	SC_LENGTH_REQUIRED = 411;
    public static final int	SC_METHOD_NOT_ALLOWED = 405;
    public static final int	SC_MOVED_PERMANENTLY = 301;
    public static final int	SC_MOVED_TEMPORARILY = 302;
    public static final int	SC_MULTIPLE_CHOICES = 300;
    public static final int	SC_NO_CONTENT = 204;
    public static final int	SC_NON_AUTHORITATIVE_INFORMATION = 203;
    public static final int	SC_NOT_ACCEPTABLE = 406;
    public static final int	SC_NOT_FOUND = 404;
    public static final int	SC_NOT_IMPLEMENTED = 501;
    public static final int	SC_NOT_MODIFIED = 304;
    public static final int	SC_OK = 200;
    public static final int	SC_PARTIAL_CONTENT = 206;
    public static final int	SC_PAYMENT_REQUIRED = 402;
    public static final int	SC_PRECONDITION_FAILED = 412;
    public static final int	SC_PROXY_AUTHENTICATION_REQUIRED = 407;
    public static final int	SC_REQUEST_ENTITY_TOO_LARGE = 413;
    public static final int	SC_REQUEST_TIMEOUT = 408;
    public static final int	SC_REQUEST_URI_TOO_LONG = 414;
    public static final int	SC_REQUESTED_RANGE_NOT_SATISFIABLE = 416;
    public static final int	SC_RESET_CONTENT = 205;
    public static final int	SC_SEE_OTHER = 303;
    public static final int	SC_SERVICE_UNAVAILABLE = 503;
    public static final int	SC_SWITCHING_PROTOCOLS = 101;
    public static final int	SC_TEMPORARY_REDIRECT = 307;
    public static final int	SC_UNAUTHORIZED = 401;
    public static final int	SC_UNSUPPORTED_MEDIA_TYPE = 415;
    public static final int	SC_USE_PROXY = 305;

    private int status;
    private int age = Integer.MIN_VALUE; // uninitialized

    /**
     * Sets the status code for this response. This method is used to set the
     * return status code when there is no error (for example, for the status
     * codes SC_OK or SC_MOVED_TEMPORARILY). If there is an error, and the
     * caller wishes to invoke an error-page defined in the web application,
     * the sendError method should be used instead.
     *
     * The container clears the buffer and sets the Location header,
     * preserving cookies and other headers.
     *
     * @param sc - the status code
     */
    public void setStatus(int sc) {
        status = sc;
    }

    public int getStatus() {
        return status;
    }

    // Compute this based on the age header (TODO: or the date header)
    public int getAge() {
        if (age != Integer.MIN_VALUE) return age;

        try {
            String ageHdr = getHeader("age");
            age = Integer.parseInt(ageHdr);
        } catch (Exception e) {
//            System.out.println("Caught " + e + " in getAge parsing " +
//                    getHeader("age"));
            age = 0;
        }

        return age;
    }
    
    public static String getStatusString(int statusCode) {
        String result = null;
        switch (statusCode) {
            case SC_SWITCHING_PROTOCOLS:
                result = "Switching Protocols";
                break;
            case SC_OK:
                result = "OK";
                break;
            case SC_CREATED:
                result = "Created";
                break;
            case SC_ACCEPTED:
                result = "Accepted";
                break;
            case SC_NO_CONTENT:
                result = "No Content";
                break;
            case SC_MOVED_PERMANENTLY:
                result = "Moved Permanently";
                break;
            case SC_SEE_OTHER:
                result = "See Other";
                break;
            case SC_TEMPORARY_REDIRECT:
                result = "Temporary Redirect";
                break;
            case SC_BAD_REQUEST:
                result = "Bad Request";
                break;
            case SC_UNAUTHORIZED:
                result = "Unauthorized";
                break;
            case SC_FORBIDDEN:
                result = "Forbidden";
                break;
            case SC_NOT_FOUND:
                result = "Not Found";
                break;
            case SC_METHOD_NOT_ALLOWED:
                result = "Method Not Allowed";
                break;
            case SC_REQUEST_TIMEOUT:
                result = "Request Time-out";
                break;
            case SC_CONFLICT:
                result = "Conflict";
                break;
            case SC_GONE:
                result = "Gone";
                break;
            case SC_UNSUPPORTED_MEDIA_TYPE:
                result = "Unsupported Media Type";
                break;
            case SC_INTERNAL_SERVER_ERROR:
                result = "Internal Server Error";
                break;
            case SC_NOT_IMPLEMENTED:
                result = "Not Implemented";
                break;
            case SC_SERVICE_UNAVAILABLE:
                result = "Service Unavailable";
                break;
            case SC_GATEWAY_TIMEOUT:
                result = "Gateway time out";
                break;
            case SC_HTTP_VERSION_NOT_SUPPORTED:
                result = "HTTP Version not supported";
                break;
        }

        return result;
    }

    public static String getStatusCodeType(int statusCode) {
        if (statusCode < 200) {
            return "Informational Msg";
        } else if (statusCode < 300) {
            return "Successful";
        } else if (statusCode < 400) {
            return "Redirection";
        } else if (statusCode < 500) {
            return "Client Error";
        } else {
            return "Server Error";
        }
    }
    
    public int toByteArray(boolean compress, byte[] buf, int start)
            throws EncodingException {
        int idx = start;

        if (compress) {
            // Start with the response prefix
            //System.out.println("Adding prefix");
            System.arraycopy(RESPONSE_START, 0, buf, 0, RESPONSE_START.length);
            idx += RESPONSE_START.length;

            //System.out.println("Encoding status " + getStatus());
            buf[idx++] = encodeStatus(getStatus());
        } else {
            // Simply serialize
            StringBuffer sb = new StringBuffer();

            // Encode the method
            int code = getStatus();
            //System.out.println("Encoding status " + code);
            sb.append("HTTP/1.1 " + code + " " + getStatusString(code));
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
//
//        if (matchBytes(buf, start, RESPONSE_START, 0, RESPONSE_START.length)) {
//            setCompressed(true);
//
//            // We need to deserialize a compressed response
//            idx += 2;
//
//            // Read the status code
//            //System.out.println("Reading status code");
//            setStatus(decodeStatus(buf[idx++]));
//        } else {
//            // Deserialize an uncompressed response
//            String line = null;
//
//            setCompressed(false);
//            BufferedReader in = new BufferedReader(new
//                    InputStreamReader(new ByteArrayInputStream(buf, idx, len)));
//            try {
//                if ((line = in.readLine()) != null) {
//                    StringTokenizer st = new StringTokenizer(line, " ");
//                    if (st.countTokens() >= 2) {
//                        st.nextToken(); // skip over "HTTP/1.1"
//                        int code = Integer.parseInt(st.nextToken());
//                        setStatus(code);
//                    } else {
//                        throw new DecodingException("Response starts with " + line);
//                    }
//                }
//            } catch (IOException ex) {
//                throw new DecodingException(ex.getMessage() + " in HttpResponse.parse");
//            }
//            // Move index past the first line
//            idx = 5;
//            while ((buf[idx - 1] != '\r') || (buf[idx] != '\n')) idx++;
//            idx++;
//        }
//
//        // Read headers and body
//        parseHeadersAndBody(buf, idx, (len - (idx - start)));
//    }

    public String toString() {
        String result = null;

        result = getStatus() + " " + getStatusString(getStatus()) + "\r\n";
        result += super.toString();

        return result;
    }

    public static HttpResponse parse(byte[] buf, int start, int len) throws DecodingException {
        HttpResponse result = new HttpResponse();
//        result.parseByteArray(buf, start, len);
        result.parse(new ByteArrayInputStream(buf, start, len));
        return result;
    }

    public void parse(InputStream is) throws DecodingException {
        boolean isCompressed = false;
        byte tmp;
        byte[] buf = new byte[MAX_HEADER_SIZE];
        int idx = 0;

        try {
            for (int i = 0; i < RESPONSE_START.length; i++) {
                buf[idx++] = (byte) is.read();
            }
            isCompressed = matchBytes(buf, 0,
                    RESPONSE_START, 0, RESPONSE_START.length);
        } catch (IOException ioe) {
            throw new DecodingException("Inputstream ended while matching " +
                    "RESPONSE_START");
        }
        
        if (isCompressed) {
            setCompressed(true);
            try {
                // Read the status code
                //System.out.println("Reading status code");
                tmp = (byte) is.read();
                setStatus(decodeStatus(tmp));
            } catch (IOException ex) {
                throw new DecodingException("Could not read compressed " +
                        "HTTP response");
            }
        } else {
            // Deserialize an uncompressed response
            String line = null;

            setCompressed(false);
            try {
                idx--;
                do {
                    buf[++idx] = (byte) is.read();
                } while ((idx < 2) || (buf[idx - 1] != 0x0d) || (buf[idx] != 0x0a));
                //System.out.println("Buffer is: " + PrettyPrint.prettyPrint(buf, 0, idx + 1));
                BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf, 0, idx)));

                if ((line = in.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(line, " ");
                    if (st.countTokens() >= 2) {
                        st.nextToken(); // skip over "HTTP/1.1"
                        int code = Integer.parseInt(st.nextToken());
                        setStatus(code);
                    } else {
                        throw new DecodingException("Response starts with " + line);
                    }
                }
            } catch (IOException ex) {
                throw new DecodingException(ex.getMessage() + " in HttpResponse.parse");
            }
        }

        // Read headers and body
        parseHeadersAndBody(is);
    }
}
