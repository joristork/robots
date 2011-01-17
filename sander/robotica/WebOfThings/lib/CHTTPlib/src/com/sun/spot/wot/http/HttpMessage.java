/*
 * Copyright (c) 2010, Sun Microsystems
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

package com.sun.spot.wot.http;

import com.sun.spot.wot.utils.PrettyPrint;
import com.sun.spot.util.Properties;
import com.sun.squawk.util.StringTokenizer;
import com.sun.squawk.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;

/**
 *
 * @author Vipul Gupta <vipul.gupta@sun.com>
 * @author Poornaprajna Udupi <poornaprajna.udupi@sun.com>
 */
public abstract class HttpMessage {
    protected static int MAX_COMPRESSED_REQUEST_SIZE = 256;
    protected static int MAX_HEADER_SIZE = 256;
    protected static int MAX_HEADERS_SIZE = 1024;

    protected static final byte[] REQUEST_START = { 0x68, 0x36 }; // "h6"
    protected static final byte[] RESPONSE_START = { 0x48, 0x36 }; // "H6"

    private static final byte DELETE_METHOD = 0x44; // D
    private static final byte GET_METHOD = 0x47; // G
    private static final byte HEAD_METHOD = 0x48; // H
    private static final byte POST_METHOD = 0x50; // P
    private static final byte PUT_METHOD = 0x55; // U
    private static final byte TRACE_METHOD = 0x54; // T

    private static final byte HDR_ACCEPT = 0x01; // u2 val: mime type code
    private static final byte HDR_AGE = 0x06; // u2 val: delta age in seconds
    private static final byte HDR_ALLOW = 0x07;
    private static final byte HDR_AUTHORIZATION = 0x08;
    private static final byte HDR_AWAKE_TIME = 0x09; // u2 val: seconds
    private static final byte HDR_CACHE_CONTROL = 0x0A; // u2 val: max-age in seconds
    private static final byte HDR_CONTENT_LENGTH = 0x0E; // u2 val: bytes
    private static final byte HDR_CONTENT_TYPE = 0x11; // u2 val: mime type
    private static final byte HDR_LOCATION = 0x1F;
    private static final byte HDR_RETRY_AFTER = 0x26; // u2 val: seconds, used with 202 code
    private static final byte HDR_SLEEP_TIME = 0x29; // u2 val: seconds
    private static final byte HDR_TRANSACTION_ID = 0x2B; // u2 val: upper case hex str
    private static final byte HDR_WWW_AUTHENTICATE = 0x33;
    protected static final byte HDR_END = 0x00;

    private static final byte[] MIME_APPLICATION_OCTET_STREAM = { (byte) 0xA0, 0x01 }; // used for arbitrary binary files
    private static final byte[] MIME_TEXT_PLAIN = { (byte) 0xB0, 0x01 }; // charset implied to be UTF-8
    private static final byte[] MIME_TEXT_HTML = { (byte) 0xB0, 0x02 }; // charset implied to be UTF-8
    private static final byte[] MIME_TEXT_XML = { (byte) 0xB0, 0x03 }; // charset implied to be UTF-8
    private static final byte[] MIME_TEXT_CSV = { (byte) 0xB0, 0x04 }; // charset implied to be UTF-8

    private Properties headers = new Properties();
    private byte[] body = null;
    // true if message was parsed from a a compressed format
    private boolean compressed = false; 
    private int maxAge = Integer.MIN_VALUE; // uninitialized

    /**
     * Returns an enumeration of all the header names this request contains. If
     * the request has no headers, this method returns an empty enumeration.
     * Some servlet containers do not allow servlets to access headers using
     * this method, in which case this method returns null
     *
     * @return an enumeration of all the header names sent with this request;
     * if the request has no headers, an empty enumeration; if the servlet
     * container does not allow servlets to use this method, null
     */
    public Enumeration getHeaderNames() {
        return headers.propertyNames();
    }

    /**
     * Returns the value of the specified request header as a String. If the
     * request did not include a header of the specified name, this method
     * returns null. If there are multiple headers with the same name, this
     * method returns the first head in the request. The header name is case
     * insensitive. You can use this method with any request header.
     *
     * @param name a String specifying the header name
     * @return a String containing the value of the requested header, or null
     * if the request does not have a header of that name
     */
    public String getHeader(String name) {
        return headers.getProperty(name.toLowerCase());
    }

    // returns previous value if one existed or null (see HashTable)
    public String setHeader(String name, String value) {
        return (String) headers.setProperty(name.toLowerCase(), value);
    }

    public void setBody(byte[] body, int start, int len) {
        this.body = new byte[len];
        System.arraycopy(body, start, this.body, 0, len);
        setHeader("Content-Length", "" + len);
    }

    public void setBody(byte[] body) {
        setBody(body, 0, body.length);
    }

    public byte[] getBody() {
        return this.body;
    }

    protected int addBody(byte[] buf, int start) {
        if (body != null) {
            System.arraycopy(body, 0, buf, start, body.length);
            return body.length;
        } else {
            return 0;
        }
    }

    protected static boolean matchBytes(byte[] arg1, int start1,
            byte[] arg2, int start2, int len) {
        for (int i = 0; i < len; i++) {
            if (arg1[start1 + i] != arg2[start2 + i]) return false;
        }

        return true;
    }

    protected int readUntilEOF(InputStream is, byte[] buf, int start)
            throws IOException {
        int idx = start;
        int data = 0;
        while ((data = is.read()) != -1) {
            buf[idx++] = (byte) (data & 0xff);
        }

        return idx - start;
    }

    protected static byte encodeMethod(String method) throws EncodingException {
        byte result = 0;

        if (method.equalsIgnoreCase("GET")) {
            result = GET_METHOD;
        } else if (method.equalsIgnoreCase("POST")) {
            result = POST_METHOD;
        } else if (method.equalsIgnoreCase("PUT")) {
            result = PUT_METHOD;
        } else if (method.equalsIgnoreCase("DELETE")) {
            result = DELETE_METHOD;
        } else if (method.equalsIgnoreCase("HEAD")) {
            result = HEAD_METHOD;
        } else if (method.equalsIgnoreCase("TRACE")) {
            result = TRACE_METHOD;
        }

        if (result != 0) return result;
        throw new EncodingException("Unsupported method " + method);
    }

    protected static int encodeMethod(String method, byte[] buf, int start) {
        int idx = start;

        try {
            buf[idx++] = encodeMethod(method);
        } catch (EncodingException ex) {
            System.err.println("Caught " + ex + " in encodeMethod.");
        }

        return (idx - start);
    }

    protected static String decodeMethod(byte encodedMethod) throws DecodingException {
        String result = null;

        switch (encodedMethod) {
            case GET_METHOD:
                result = "GET";
                break;
            case POST_METHOD:
                result = "POST";
                break;
            case PUT_METHOD:
                result = "PUT";
                break;
            case DELETE_METHOD:
                result = "DELETE";
                break;
            case HEAD_METHOD:
                result = "HEAD";
                break;
            case TRACE_METHOD:
                result = "TRACE";
                break;
            default:
                throw new DecodingException("Unsupported method " +
                        encodedMethod);
        }

        return result;
    }

    protected static byte encodeStatus(int code) {
        byte result = 0;

        result = (byte) ((((code / 100) << 5) | (code % 100)) & 0xff);
        return result;
    }

    protected static int decodeStatus(byte code) {
        int result = 0;

        result = ((code & 0xe0) >> 5) * 100 + (code & 0x1f);
        return result;
    }

    protected static byte[] encodeMimeType(String mimeType) {
        byte[] result = null;

        if (mimeType.equalsIgnoreCase("application/octet-stream")) {
            result = MIME_APPLICATION_OCTET_STREAM;
        } else if (mimeType.equalsIgnoreCase("text/plain")) {
            result = MIME_TEXT_PLAIN;
        } else if (mimeType.equalsIgnoreCase("text/html")) {
            result = MIME_TEXT_HTML;
        } else if (mimeType.equalsIgnoreCase("text/xml")) {
            result = MIME_TEXT_XML;
        } else if (mimeType.equalsIgnoreCase("text/csv")) {
            result = MIME_TEXT_CSV;
        } else {
//            System.err.println("Ignoring unsupported MIME type " + mimeType);
        }

        return result;
    }

    protected static int encodeMimeType(String mimeType, byte[] buf, int start) {
        if (start == buf.length - 1) {
            return 0;
        }

        byte[] result = encodeMimeType(mimeType);
        if (result == null) {
            return 0;
        }

        buf[start] = result[0];
        buf[start + 1] = result[1];
        return 2;
    }

    protected static int encodeMimeTypes(String mimeTypes, byte[] buf, int start) {
        int idx = start;
        if (start == buf.length - 1) {
            return 0;
        }

//        String[] mimeType = mimeTypes.split(",");
//        for (int i = 0; i < mimeType.length; i++) {
//            int tmp = mimeType[i].indexOf(";");
//            if (tmp < 0) {
//                idx += encodeMimeType(mimeType[i], buf, idx);
//            } else {
//                idx += encodeMimeType(mimeType[i].substring(0, tmp), buf, idx);
//            }
//
//        }
        StringTokenizer st = new StringTokenizer(mimeTypes, ",");
        while (st.hasMoreTokens()) {
            String mimeType = st.nextToken();
            int tmp = mimeType.indexOf(";");
            if (tmp < 0) {
                idx += encodeMimeType(mimeType, buf, idx);
            } else {
                idx += encodeMimeType(mimeType.substring(0, tmp), buf, idx);
            }
        }

        return (idx - start);
    }

    protected static String decodeMimeType(byte[] buf, int start)
            throws DecodingException {
        String result = null;
        int mimeEncoding = ((buf[start] & 0xff) << 8) | (buf[start + 1] & 0xff);
        switch (mimeEncoding) {
            case 0xA001:
                result = "application/octet-stream";
                break;
            case 0xB001:
                result = "text/plain";
                break;
            case 0xB002:
                result = "text/html";
                break;
            case 0xB003:
                result = "text/xml";
                break;
            case 0xB004:
                result = "text/csv";
                break;
        }

        if (result != null) return result;
        throw new DecodingException("Could not decode MIME type");
    }

    protected static int encodeIntegerStr(String intVal, int radix, byte[] buf, int start) {
        if (start == buf.length - 1) return 0;
        try {
            int val = Integer.parseInt(intVal, radix);
            buf[start] = (byte) ((val >> 8) & 0xff);
            buf[start + 1] = (byte) (val & 0xff);
            return 2;
        } catch (NumberFormatException nfe) {
            System.err.println("Caught " + nfe + " while parsing " + intVal +
                    " in encodeIntegerStr");
        }

        return 0;
    }

    protected static int encodeHexIntegerStr(String hexVal, byte[] buf, int start) {
        return encodeIntegerStr(hexVal, 16, buf, start);
    }

    protected static int encodeDecimalIntegerStr(String decimalVal, byte[] buf, int start) {
        return encodeIntegerStr(decimalVal, 10, buf, start);
    }

    protected static int decodeInteger(byte[] buf, int start, int len) {
        int val = ((buf[start] << 8) & 0xff) | (buf[start + 1] & 0xff);
        return val;
    }

    protected static int encodeNullTerminatedString(String str, byte[] buf, int start) {
        int idx = start;

        byte[] strBytes = str.getBytes(); // Java ME won't let us specify "UTF-8"
        System.arraycopy(strBytes, 0, buf, idx, strBytes.length);
        idx += strBytes.length;

        // terminate string with 0x00
        buf[idx++] = 0x00;
        return (idx - start);
    }

//    protected static String decodeNullTerminatedString(byte[] buf, int start) {
//        int idx = start;
//
//        while (buf[idx] != 0x00) idx++;
//        return new String(buf, start, (idx - start));
//    }

    protected static String readNullTerminatedString(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        int i;

        while ((i = is.read()) > 0) {
            sb.append((char) i); // XXX won't work for Internationalized strings
        }

        // We've already consumed the NULL character
        return sb.toString();
    }

    protected static int encodeHeader(String name, String value, byte[] buf, int start) throws EncodingException {
        int idx = start;
        int tmp1 = 0;
        int tmp2 = 0;

        if (name.equalsIgnoreCase("accept")) {
            buf[idx++] = HDR_ACCEPT;
            if ((tmp1 = encodeMimeTypes(value, buf, idx)) == 0) {
                idx--;
            } else {
                idx += tmp1;
            }
        } else if (name.equalsIgnoreCase("age")) {
            buf[idx++] = HDR_AGE;
            idx += encodeDecimalIntegerStr(value, buf, idx);
        } else if (name.equalsIgnoreCase("allow")) {
            buf[idx++] = HDR_ALLOW;
            StringTokenizer st = new StringTokenizer(value, ",");
            while (st.hasMoreTokens()) {
                idx += encodeMethod(st.nextToken().trim(), buf, idx);
            }
        } else if (name.equals("authorization")) {
            buf[idx++] = HDR_AUTHORIZATION;
            idx += encodeNullTerminatedString(value, buf, idx);
        } else if (name.equals("awake-time")) {
            buf[idx++] = HDR_AWAKE_TIME;
            idx += encodeDecimalIntegerStr(value, buf, idx);
        } else if (name.equalsIgnoreCase("cache-control")) {
            buf[idx++] = HDR_CACHE_CONTROL;
            tmp1 = value.indexOf("max-age=");
            if (tmp1 < 0) {
                idx--;
            } else {
                tmp2 = value.indexOf(" ", tmp1);
                if (tmp2 < 0) {
                    tmp2 = value.length();
                }

                idx += encodeDecimalIntegerStr(value.substring(tmp1 +
                        "max-age=".length(), tmp2), buf, idx);
            }
        } else if (name.equalsIgnoreCase("content-length")) {
            buf[idx++] = HDR_CONTENT_LENGTH;
            idx += encodeDecimalIntegerStr(value, buf, idx);
        } else if (name.equalsIgnoreCase("content-type")) {
            buf[idx++] = HDR_CONTENT_TYPE;
            if ((tmp1 = encodeMimeType(value, buf, idx)) == 0) {
                idx--;
            } else {
                idx += tmp1;
            }
        } else if (name.equals("location")) {
            buf[idx++] = HDR_LOCATION;
            idx += encodeNullTerminatedString(value, buf, idx);
        } else if (name.equals("retry-after")) {
            buf[idx++] = HDR_RETRY_AFTER;
            idx += encodeDecimalIntegerStr(value, buf, idx);
        } else if (name.equals("sleep-time")) {
            buf[idx++] = HDR_SLEEP_TIME;
            idx += encodeDecimalIntegerStr(value, buf, idx);
        } else if (name.equals("transaction-id")) {
            buf[idx++] = HDR_TRANSACTION_ID;
            idx += encodeHexIntegerStr(value, buf, idx);
        } else if (name.equals("www-authenticate")) {
            buf[idx++] = HDR_WWW_AUTHENTICATE;
            idx += encodeNullTerminatedString(value, buf, idx);
        } else {
            // System.err.println("Skipping over unsupported header " + name);
            //throw new EncodingException("Unsupported header " + name);
        }

        return (idx - start);
    }

    protected int encodeHeaders(byte[] buf, int start) throws EncodingException {
        int idx = start;

        for (Enumeration e = getHeaderNames(); e.hasMoreElements();) {
                String hdr = (String) e.nextElement();
                idx += encodeHeader(hdr, getHeader(hdr), buf, idx);
        }
        buf[idx++] = HDR_END;

        return (idx - start);
    }

//    protected int decodeAndAddHeader(byte[] buf, int start) throws DecodingException {
//        int idx = start;
//        int ival = 0;
//        int hdr = 0;
//        String sval = "";
//        String tmp = null;
//
//        switch (hdr = buf[idx++]) {
//            case HDR_ACCEPT:
//                while ((buf[idx] == (byte) 0xA0) || (buf[idx] == (byte) 0xB0)) {
//                    try {
//                        tmp = decodeMimeType(buf, idx);
//                        if (sval.length() != 0) {
//                            sval += ",";
//                        }
//                        sval += tmp;
//                    } catch (DecodingException ex) {
//                        System.err.println("Caught " + ex +
//                                " while decoding MIME type " +
//                                Integer.toHexString((buf[idx] & 0xff) << 8 +
//                                (buf[idx + 1] & 0xff)));
//                    }
//                    idx += 2;
//                }
//                this.setHeader("Accept", "" + sval);
//                //System.out.println("Accept: " + sval);
//                break;
//
//            case HDR_AGE:
//                ival = decodeInteger(buf, idx, 2);
//                this.setHeader("Age", "" + ival);
//                //System.out.println("Age: " + ival);
//                idx += 2;
//                break;
//
//            case HDR_ALLOW:
//                String meth = null;
//                while (true) {
//                    try {
//                        meth = decodeMethod(buf[idx]);
//                        if (!sval.equals("")) {
//                            sval += ", ";
//                        }
//                        sval += meth;
//                        idx++;
//                    } catch (DecodingException ex) {
//                        break;
//                    }
//                }
//
//                this.setHeader("Allow", sval);
//                //System.out.println("Allow: " + sval);
//                break;
//
//            case HDR_AWAKE_TIME:
//                ival = decodeInteger(buf, idx, 2);
//                this.setHeader("Awake-Time", "" + ival);
//                //System.out.println("Awake-Time: " + ival);
//                idx += 2;
//                break;
//
//            case HDR_CACHE_CONTROL:
//                ival = decodeInteger(buf, idx, 2);
//                this.setHeader("Cache-Control", "max-age=" + ival);
//                //System.out.println("Cache-Control: " + "max-age=" + ival);
//                idx += 2;
//                break;
//
//            case HDR_CONTENT_LENGTH:
//                ival = decodeInteger(buf, idx, 2);
//                this.setHeader("Content-Length", "" + ival);
//                //System.out.println("Content-Length: " + ival);
//                idx += 2;
//                break;
//
//            case HDR_CONTENT_TYPE:
//                sval = decodeMimeType(buf, idx);
//                this.setHeader("Content-Type", sval);
//                //System.out.println("Content-Type: " + sval);
//                idx += 2;
//                break;
//
//            case HDR_LOCATION:
//                ival = idx;
//                while (buf[idx] != 0x00) idx++;
//                sval = new String(buf, ival, (idx - ival));
//
//                this.setHeader("Location", sval);
//                //System.out.println("Location: " + sval);
//                idx += 1; // move past 0x00
//                break;
//
//            case HDR_RETRY_AFTER:
//                ival = decodeInteger(buf, idx, 2);
//                this.setHeader("Retry-After", "" + ival);
//                //System.out.println("Retry-After: " + ival);
//                idx += 2;
//                break;
//
//            case HDR_SLEEP_TIME:
//                ival = decodeInteger(buf, idx, 2);
//                this.setHeader("Sleep-Time", "" + ival);
//                //System.out.println("Sleep-Time: " + ival);
//                idx += 2;
//                break;
//
//            case HDR_TRANSACTION_ID:
//                ival = decodeInteger(buf, idx, 2);
//                this.setHeader("Transaction-Id", Integer.toHexString(ival));
//                //System.out.println("Transaction-Id: " + Integer.toHexString(ival));
//                idx += 2;
//                break;
//
//            default:
//                throw new DecodingException("Can't decode header " + hdr);
//        }
//
//        return (idx - start);
//    }

    private int parseShort(InputStream is) throws IOException {
        byte[] buf = new byte[2];
        buf[0] = (byte) is.read();
        buf[1] = (byte) is.read();
        //System.out.println("Prasing short:\n" + PrettyPrint.prettyPrint(buf));
        return ((buf[0] & 0xff) << 8) + (buf[1] & 0xff);
    }
    
    private void parseHeaders(InputStream is) throws IOException, DecodingException {
        int ival = 0;
        int i;
        int hdr = 0;
        int next = 0;
        String sval = "";
        String tmp = null;
        StringBuffer sb = null;
        byte[] buf = new byte[2];

        hdr = is.read();
        //System.out.println("hdr is " + hdr);
        while (hdr != 0x00) {
            switch (hdr) {
                case HDR_ACCEPT:
                    sval = "";
                    while (true) {
                        next = is.read() & 0xff;
                        //System.out.println("next in ACCEPT is " + next);
                        if ((next != 0xA0) && (next != 0xB0)) break;
                        buf[0] = (byte) next;
                        buf[1] = (byte) is.read();
                        //System.out.println("buf is " + PrettyPrint.prettyPrint(buf));
                        try {
                            tmp = decodeMimeType(buf, 0);
                            if (sval.length() != 0) {
                                sval += ",";
                            }
                            sval += tmp;
                        } catch (DecodingException ex) {
                            System.err.println("Caught " + ex +
                                    " while decoding MIME type " +
                                    Integer.toHexString((buf[0] & 0xff) << 8 +
                                    (buf[1] & 0xff)));
                        }
                    }
                    this.setHeader("Accept", "" + sval);
                    //System.out.println("Accept: " + sval);
                    break;

                case HDR_AGE:
                    ival = parseShort(is);
                    this.setHeader("Age", "" + ival);
                    //System.out.println("Age: " + ival);
                    break;

                case HDR_ALLOW:
                    String meth = null;
                    sval = "";
                    while (true) {
                        next = is.read();
                        try {
                            meth = decodeMethod((byte) next);
                            if (!sval.equals("")) {
                                sval += ", ";
                            }
                            sval += meth;
                        } catch (DecodingException ex) {
                            break;
                        }
                    }

                    this.setHeader("Allow", sval);
                    //System.out.println("Allow: " + sval);
                    break;

                case HDR_AUTHORIZATION:
                    sb = new StringBuffer();
                    while ((i = is.read()) != 0x00) {
                        sb.append((char) i);
                    }
                    sval = sb.toString();

                    this.setHeader("Authorization", sval);
                    //System.out.println("Authorization: " + sval);
                    break;

                case HDR_AWAKE_TIME:
                    ival = parseShort(is);
                    this.setHeader("Awake-Time", "" + ival);
                    //System.out.println("Awake-Time: " + ival);
                    break;

                case HDR_CACHE_CONTROL:
                    ival = parseShort(is);
                    this.setHeader("Cache-Control", "max-age=" + ival);
                    //System.out.println("Cache-Control: " + "max-age=" + ival);
                    break;

                case HDR_CONTENT_LENGTH:
                    ival = parseShort(is);
                    this.setHeader("Content-Length", "" + ival);
                    //System.out.println("Content-Length: " + ival);
                    break;

                case HDR_CONTENT_TYPE:
                    buf[0] = (byte) is.read();
                    buf[1] = (byte) is.read();
                    sval = decodeMimeType(buf, 0);
                    this.setHeader("Content-Type", sval);
                    //System.out.println("Content-Type: " + sval);
                    break;

                case HDR_LOCATION:
                    sb = new StringBuffer();
                    while ((i = is.read()) != 0x00) {
                        sb.append((char) i);
                    }
                    sval = sb.toString();

                    this.setHeader("Location", sval);
                    //System.out.println("Location: " + sval);
                    break;

                case HDR_RETRY_AFTER:
                    ival = parseShort(is);
                    this.setHeader("Retry-After", "" + ival);
                    //System.out.println("Retry-After: " + ival);
                    break;

                case HDR_SLEEP_TIME:
                    ival = parseShort(is);
                    this.setHeader("Sleep-Time", "" + ival);
                    //System.out.println("Sleep-Time: " + ival);
                    break;

                case HDR_TRANSACTION_ID:
                    ival = parseShort(is);
                    this.setHeader("Transaction-Id", Integer.toHexString(ival));
                    //System.out.println("Transaction-Id: " + Integer.toHexString(ival));
                    break;

                case HDR_WWW_AUTHENTICATE:
                    sb = new StringBuffer();
                    while ((i = is.read()) != 0x00) {
                        sb.append((char) i);
                    }
                    sval = sb.toString();

                    this.setHeader("WWW-Authenticate", sval);
                    //System.out.println("WWW-Authenticate: " + sval);
                    break;
                    
                default:
                    throw new DecodingException("Can't decode header " + hdr);
            }

            if ((hdr != HDR_ALLOW) && (hdr != HDR_ACCEPT)) {
                hdr = is.read();
                //System.out.println("Reading hdr after switch " + hdr);
            } else {
                hdr = next;
            }
        }
    }

    public int encodeHeadersAndBody(boolean compress, byte[] buf, int start)
            throws EncodingException {
        int idx = start;

        if (compress) {
            //System.out.println("Encoding headers");
            idx += encodeHeaders(buf, idx);
        } else {
            StringBuffer sb = new StringBuffer();

            //System.out.println("Encoding headers");
            Enumeration hdrs = getHeaderNames();
            while (hdrs.hasMoreElements()) {
                String hdrName = (String) hdrs.nextElement();
                String hdrValue = getHeader(hdrName);
                //System.out.println("Encoding " + hdrName + ": " + hdrValue);
                sb.append(hdrName);
                sb.append(":");
                sb.append(hdrValue);
                sb.append("\r\n");
            }
            sb.append("\r\n");

            byte[] hdrBytes;
            hdrBytes = sb.toString().getBytes(); // Can't specify "UTF-8" in Java ME
            System.arraycopy(hdrBytes, 0, buf, idx, hdrBytes.length);
            idx += hdrBytes.length;
        }

        if (body != null) {
            //System.out.println("Adding body");
            System.arraycopy(body, 0, buf, idx, body.length);
            idx += body.length;
        } else {
            //System.out.println("No body");
        }

        return (idx - start);
    }

//    protected void parseHeadersAndBody(byte[] buf, int start, int len) throws DecodingException {
//        int idx = start;
//
//        if (isCompressed()) {
//            // Reading headers ...
//            //System.out.println("Reading headers ...");
//            while (buf[idx] != 0x00) {
//                //System.out.println("Decoding header buf[" + idx + "]=" + buf[idx]);
//                idx += decodeAndAddHeader(buf, idx);
//            }
//            idx++; // move it past 0x00 to start of body
//        } else {
//            String line;
//
//            try {
//                // Examine the headers
//                BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf, idx, len)));
//                while ((line = in.readLine()) != null) {
//                    if (line.equals("")) {
//                        break; // Done with headers
//                    }
//                    // Split header name and value
//                    int colonPos = line.indexOf(":");
//                    if (colonPos > 0) {
//                        String hdrName = line.substring(0, colonPos).trim();
//                        String hdrVal = line.substring(colonPos + 1).trim();
//                        setHeader(hdrName, hdrVal);
//                    } else {
//                        System.err.println("Couldn't parse header in line " + line);
//                    }
//                }
//                in.close();
//                idx = 9; // skip over "HTTP/x.x "
//                while ((buf[idx - 3] != 0x0d) || (buf[idx - 2] != 0x0a) ||
//                        (buf[idx - 1] != 0x0d) || (buf[idx] != 0x0a)) {
//                    idx++;
//                }
//                idx++; // move it to start of body
//            } catch (IOException ex) {
//                throw new DecodingException(ex.getMessage() + " in parseHeaderAndBody");
//            }
//        }
//
//        // Read the body ...
//        if ((idx - start) < len) {
//            // Add body
//            //System.out.println("Reading body ..." +
//            //        new String(buf, idx, len - (idx - start))); // "UTF-8"
//            setBody(buf, idx, len - (idx - start));
//        } else {
//            //System.out.println("No body!");
//        }
//    }

    protected void parseHeadersAndBody(InputStream is) throws DecodingException {
        int i;
        byte[] buf = new byte[MAX_HEADERS_SIZE];
        int idx = 0;

        if (isCompressed()) {
            try {
                parseHeaders(is);
            } catch (IOException ex) {
                throw new DecodingException("Caught " + ex +
                        " while parsing compressed headers.");
            }
        } else {
            String line;
            //System.out.println("Parsing uncompressed headers ... ");
            try {
                // Examine the headers
                idx = -1;
                do {
                    i = is.read();
                    buf[++idx] = (byte) i;
                } while ((idx < 4 ||
                        (buf[idx - 3] != 0x0d) || (buf[idx - 2] != 0x0a) ||
                        (buf[idx - 1] != 0x0d) || (buf[idx] != 0x0a)) &&
                        ((buf[0] != 0x0d) || (buf[1] != 0x0a)));
//                System.out.println("Header bytes " +
//                        PrettyPrint.prettyPrint(buf, 0, idx + 1));
                BufferedReader in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(buf, 0, idx)));
                while ((line = in.readLine()) != null) {
                    if (line.equals("")) {
                        break; // Done with headers
                    }
                    // Split header name and value
                    int colonPos = line.indexOf(":");
                    if (colonPos > 0) {
                        String hdrName = line.substring(0, colonPos).trim();
                        String hdrVal = line.substring(colonPos + 1).trim();
                        setHeader(hdrName, hdrVal);
                    } else {
                        System.err.println("Couldn't parse header in line " + line);
                    }
                }
                in.close();
            } catch (IOException ex) {
                throw new DecodingException(ex.getMessage() + " in parseHeaderAndBody");
            }
        }

        int clen = 0;
        String clenStr = getHeader("Content-Length");
        if (clenStr != null) {
            try {
                clen = Integer.parseInt(clenStr);
            } catch (NumberFormatException nfe) {
                System.err.println("Could not parse content length <" +
                        getHeader("Content-Length") + ">");
            }
        }

        if (clen > 0) {
            // Read the body ...
            buf = new byte[clen];
            int j = 0;
            try {
                while (j < clen) {
                    buf[j++] = (byte) is.read();
                }
            } catch (IOException ex) {
                throw new DecodingException("Caught " + ex +
                        " while reading message body.");
            }
            setBody(buf, 0, clen);
        } else {
            //System.out.println("No body!");
        }
    }

    public String toString() {
        String result = null;
        StringBuffer sb = new StringBuffer();

        Enumeration hdrs = getHeaderNames();
        while (hdrs.hasMoreElements()) {
            String hdrName = (String) hdrs.nextElement();
            String hdrValue = getHeader(hdrName);
            sb.append(hdrName);
            sb.append(":");
            sb.append(hdrValue);
            sb.append("\r\n");
        }
        sb.append("\r\n");

        result = sb.toString();

        if ((body != null) && (body.length > 0)) {
           result += PrettyPrint.prettyPrint(body) + "\r\n";
        }

        return result;
    }

    public long getTime() {
        long result = -1;
        String RFC1123_DATE_PATTERN = "EEE, dd MMM yyyy HH:mm:ss zzz";

        // Read the date header and return milliseconds since UTC Jan 1, 1970
        String dateHdr = getHeader("date");
        if (dateHdr != null) {
            // XXX add parsng code here ...
        }

        return result;
    }

    // Compute this based on the max-age directive in the Cache-Control header
    public int getMaxAge() {
        if (maxAge != Integer.MIN_VALUE) return maxAge;

        String cacheCtrlHdr = getHeader("cache-control");
        if (cacheCtrlHdr == null) return 0;
        StringTokenizer st = new StringTokenizer(cacheCtrlHdr, ",");
        while (st.hasMoreTokens()) {
            String cacheDirective = st.nextToken();
            if (cacheDirective.startsWith("max-age=")) {
                try {
                    maxAge = Integer.parseInt(cacheDirective.substring("max-age=".length()));
                } catch (Exception e) {
                    System.err.println("Caught " + e +
                            " in getMaxAge parsing <" + cacheDirective + "> " +
                            "in " + cacheCtrlHdr);
                    maxAge = 0;
                }
            }
        }

        if (maxAge == Integer.MIN_VALUE) maxAge = 0;

        return maxAge;
    }

    public void setMaxAge(int age) {
        maxAge = age;
    }

    /**
     * @return the compressed
     */
    public boolean isCompressed() {
        return compressed;
    }

    /**
     * @param compressed the compressed to set
     */
    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }
}