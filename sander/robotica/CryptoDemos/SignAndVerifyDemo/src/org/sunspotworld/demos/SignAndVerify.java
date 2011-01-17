/*
 * Copyright (c) 2009-2010 Sun Microsystems, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 */

package org.sunspotworld.demos;

import com.sun.spot.util.*;
import com.sun.spot.security.InvalidKeyException;
import com.sun.spot.security.NoSuchAlgorithmException;
import com.sun.spot.security.PrivateKey;
import com.sun.spot.security.PublicKey;
import com.sun.spot.security.Signature;
import com.sun.spot.security.SignatureException;
import com.sun.spot.security.implementation.ECKeyImpl;
import com.sun.spot.security.implementation.ECPrivateKeyImpl;
import com.sun.spot.security.implementation.ECPublicKeyImpl;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * The startApp method of this class is called by the VM to start the
 * application.
 * 
 * The manifest specifies this class as MIDlet-1, which means it will
 * be selected for execution.
 */
public class SignAndVerify extends MIDlet {
    // For ECC signatures using SECP160R1, the signature will be around 43 bytes
    private static int MAX_SIGNATURE_LEN = 50;
    private String dataString = "The fragrance always remains " +
            "in the hand that gives the rose.";

    private byte[] signature = new byte[MAX_SIGNATURE_LEN];
    private PublicKey pub = null;
    private PrivateKey priv = null;
    private Signature sig = null;

    private void initialize() {
        try {
            // First we create a key pair that'll be used to sign and verify
            // Here we generate ECC keys over the only supported curve
            // SECP160R1 indicated by the zero passed in to the key constructor
            ECPublicKeyImpl publicKey = new ECPublicKeyImpl(0);
            ECPrivateKeyImpl privateKey = new ECPrivateKeyImpl(0);
            ECKeyImpl.genKeyPair(publicKey, privateKey);
            pub = publicKey;
            priv = privateKey;

            // Next we initialize the signature object
            sig = Signature.getInstance("SHA1WITHECDSA");
        } catch (Exception e) {
            System.out.println("Caught " + e.getMessage() +
                    " in initialize().");
        }
    }

    // Sign the data using the specified private key. Leave the signature
    // in the buffer specified as the last argument and return the signature
    // length
    private int signData(byte[] data, PrivateKey privKey, byte[] signature) {
        try {
            sig.initSign(privKey);
            sig.update(data, 0, data.length);
            return sig.sign(signature, 0, signature.length);
        } catch (Exception e) {
            System.out.println("Caught " + e.getMessage() +
                    " in signData().");
        }

        return 0;
    }

    // Verify the signature  Leave the signature
    // in the buffer specified as the last argument and return the signature
    // length
    private boolean verifyData(byte[] data, PublicKey pubKey,
            byte[] signature, int siglen) {
        try {
            sig.initVerify(pubKey);
            sig.update(data, 0, data.length);
            boolean val = sig.verify(signature, 0, siglen);
            return val;
        } catch (Exception e) {
            System.out.println("Caught " + e.getMessage() +
                    " in verifyData().");
        }

        return false;
    }

    protected void startApp() throws MIDletStateChangeException {
        System.out.println("SignAndVerify: This demo shows how to use " +
                "digital signatures on a Sun SPOT.");
        
	// Listen for downloads/commands over USB connection
	new com.sun.spot.service.BootloaderListenerService().getInstance().start();

        System.out.println("Generating public/private key pair ...");
        initialize();

        System.out.println("Signing data ...");
        byte[] data = dataString.getBytes();
        int sigLen = signData(data, priv, signature);

        System.out.print("Verifying signature on unmodified data " +
                "(this should succeed) ... ");
        System.out.println(verifyData(data, pub, signature, sigLen) ? "successful" :
            "failed");

        // Let's delibrately modify the data by fliipping the least significant
        // bit in the first byte
        data[0] = (byte) (data[0] ^ 0x01);
        System.out.print("Verifying signature on modified data " +
                "(this should fail) ... ");

        System.out.println(verifyData(data, pub, signature, sigLen) ? "successful" :
            "failed");

        System.out.println("Done.");
    }

    protected void pauseApp() {
        // This is not currently called by the Squawk VM
    }

    /**
     * Called if the MIDlet is terminated by the system.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
