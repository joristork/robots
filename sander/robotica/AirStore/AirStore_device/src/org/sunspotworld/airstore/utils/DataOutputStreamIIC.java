/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.utils;

import com.sun.squawk.io.mailboxes.AddressClosedException;
import com.sun.squawk.io.mailboxes.ByteArrayEnvelope;
import com.sun.squawk.io.mailboxes.Channel;
import java.io.ByteArrayOutputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Wraps a ByteArrayOutputStream, and upon flush, gets its bytes,
 * puts them in an envolpe, and sends that over the given channel.
 * 
 * @author randy
 */
public class DataOutputStreamIIC implements DataOutput  {

    private static boolean doPrints = false;

    private Channel channel;

    ByteArrayOutputStream strm;
    DataOutputStream dos;

    public DataOutputStreamIIC(Channel c){
        if(doPrints) System.out.println("[DataOutputStreamIIC] instantiated on channel = " + c);
        channel = c;
        strm = new ByteArrayOutputStream();
        dos = new DataOutputStream(strm);
    }

    /**
     * @return   doPrints -- for debugging or other monitoring
     */
    public static boolean getDoPrints() {
        return doPrints;
    }

    /**
     * @param aDoPrints  -- for debugging or other monitoring
     */
    public static void setDoPrints(boolean aDoPrints) {
        doPrints = aDoPrints;
    }
    /**
     * Print a message only when doPrints is set true.
     *
     * @param msg Message to appear on standard out.
     */
    public void print(String msg) {
        if (doPrints) {
            System.out.println("[DataOutputStreamIIC] " + msg);
        }
    }

    public void flush(){
        synchronized(this) {
            byte[] b = strm.toByteArray();
            print(" flush > flushing " + b.length + " bytes. Sending in Envelope.");
            ByteArrayEnvelope e = new ByteArrayEnvelope(b);
            try {
                channel.send(e);
            } catch (AddressClosedException ex) {
                ex.printStackTrace();
            }
            strm = new ByteArrayOutputStream();
            dos = new DataOutputStream(strm);
        }
    }

    public void write(int b) throws IOException {
        dos.write(b);
        print(" ............... write(int b) " + b);
    }

    public void write(byte[] b) throws IOException {
       dos.write(b);
        print(" ............... write(byte[] b) " + b);
    }

    public void write(byte[] b, int off, int len) throws IOException {
        dos.write(b, off, len);
        print(" ............... writeBoolean " + b);
    }

    public void writeBoolean(boolean v) throws IOException {
        dos.writeBoolean(v);
        print(" ............... wrote " + v);
    }

    public void writeByte(int v) throws IOException {
        dos.writeByte(v);
        print(" ............... writeByte " + v);
    }

    public void writeChar(int v) throws IOException {
        dos.writeChar(v);
        print(" ............... writeChar " + v);
    }

    public void writeChars(String s) throws IOException {
        dos.writeChars(s);
        print(" ............... writeChars " + s);
    }

    public void writeDouble(double v) throws IOException {
        dos.writeDouble(v);
        print(" ............... writeDouble " + v);
    }

    public void writeFloat(float v) throws IOException {
        dos.writeFloat(v);
        print(" ............... writeFloat " + v);
    }

    public void writeInt(int v) throws IOException {
        dos.writeInt(v);
        print(" ............... writeInt " + v);
    }

    public void writeLong(long v) throws IOException {
        dos.writeLong(v);
        print(" ............... writeLong " + v);
    }

    public void writeShort(int v) throws IOException {
        dos.writeShort(v);
        print(" ............... writeShort " + v);
    }

    public void writeUTF(String s) throws IOException {
        dos.writeUTF(s);
        print(" ............... writeUTF " + s);
    }
}
