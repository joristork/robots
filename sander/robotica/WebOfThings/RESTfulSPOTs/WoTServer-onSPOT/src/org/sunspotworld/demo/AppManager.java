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

package org.sunspotworld.demo;

import com.sun.spot.wot.WebApplication;
import com.sun.spot.imp.MIDletDescriptor;
import java.io.IOException;
import com.sun.spot.wot.http.HttpResponse;
import com.sun.spot.wot.http.HttpRequest;
import com.sun.spot.imp.MIDletSuiteDescriptor;
//import com.sun.spot.peripheral.ota.IsolateManager; XXX commented out for emulator
import com.sun.spot.util.Properties;
import com.sun.squawk.VM;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author vgupta
 */
public class AppManager extends WebApplication {
    public void init() {};

    public AppManager(String str) {
        super(str);
    }
    
    public HttpResponse processRequest(HttpRequest request) throws IOException {
        HttpResponse response = new HttpResponse();
        String respStr = null;
        String path = null;

        if (request.getMethod().equalsIgnoreCase("GET")) {
            path = request.getPathInfo();

            response.setStatus(HttpResponse.SC_OK);
            response.setHeader("Content-Type", "text/plain");
            response.setHeader("Cache-Control", "max-age=10");
            response.setBody(getAppsResponse().toString().getBytes());

            return response;
        } else {
            response.setStatus(HttpResponse.SC_METHOD_NOT_ALLOWED);
            response.setHeader("Allow", "GET");

            return response;
        }
    }

    private StringBuffer getAppsResponse() throws IOException {
        StringBuffer sb = new StringBuffer();
    	MIDletSuiteDescriptor[] suites = MIDletSuiteDescriptor.getAllInstances();
    	sb.append("{\nsuites: [\n");
    	for (int i = 0; i < suites.length; i++) {
            sb.append("\t{\n");
            sb.append("\t\tid: \"" + suites[i].getURI() + "\",\n");
            sb.append("\t\tsize: " + suites[i].getLength() + ",\n");
            sb.append("\t\tmodified: " + suites[i].getLastModified() + ",\n");
            sb.append("\t\tsrc: \"" + suites[i].getSourcePath() + "\",\n");
//            dos.writeUTF(suites[i].getURI());
//            dos.writeUTF(suites[i].getVendor());
//            dos.writeUTF(suites[i].getName());
//            dos.writeUTF(suites[i].getVersion());
//            dos.writeUTF(suites[i].getSourcePath());
//            dos.writeInt(suites[i].getLength());
//            dos.writeLong(suites[i].getLastModified());
            MIDletDescriptor[] mds = suites[i].getMIDletDescriptors();
            sb.append("\t\tmidlets: [\n");
            for (int j = (mds.length - 1); j >= 0; j--){
                sb.append("\t\t\t{\n");
                sb.append("\t\t\t\tno: " + mds[j].getNumber() + ",\n");
                sb.append("\t\t\t\tclass: \"" + mds[j].getClassName() + "\"\n");
                if (j == 0) {
                    sb.append("\t\t\t}\n");
                } else {
                    sb.append("\t\t\t},\n");
                }                
            }
            sb.append("\t\t]\n");
            if (i == suites.length - 1) {
                sb.append("\t}\n");
            } else {
                sb.append("\t},\n");
            }
        }
        sb.append("\t],\n");

        sb.append("isolates: [\n");
        // XXX Commented out for emulator
//        Properties prop = IsolateManager.getAllIsolateStatus();
//        if (prop != null) {
//            for (Enumeration e = prop.propertyNames(); e.hasMoreElements();) {
//                String key = (String) e.nextElement();
//                if (key == null) continue;
//                String value = prop.getProperty(key);
//                sb.append("\t\t{\n" +
//                        "\t\t\tid: \"" + key + "\",\n" +
//                        "\t\t\tstatus: \"" + value + "\",\n" +
//                        "\t\t}");
//                if (e.hasMoreElements())
//                    sb.append(",\n");
//                else
//                    sb.append("\n");
//            }
//        }
        sb.append("\t]\n");
        sb.append("}");
        return sb;
    }

    private byte[] createSuiteManifestResponse(String suiteId) throws Exception {
    	ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	DataOutputStream dos = new DataOutputStream(baos);
    	Hashtable manifestPropertiesOfSuite;
        try {
            manifestPropertiesOfSuite = VM.getManifestPropertiesOfSuite(suiteId);
        } catch (Error e1) {
            throw new Exception("Could not read manifest for " + suiteId +
                    ". " + e1.getMessage());
        }
        dos.writeShort(manifestPropertiesOfSuite.size());
        Enumeration manifestKeys = manifestPropertiesOfSuite.keys();
        while (manifestKeys.hasMoreElements()) {
            String key = (String) manifestKeys.nextElement();
            dos.writeUTF(key);
            dos.writeUTF((String) manifestPropertiesOfSuite.get(key));
        }

        return baos.toByteArray();
    }
}
