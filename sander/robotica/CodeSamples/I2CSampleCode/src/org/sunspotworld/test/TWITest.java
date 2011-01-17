/*
 * Copyright (c) 2008-2010 Sun Microsystems, Inc.
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
 **/

package org.sunspotworld.test;

import com.sun.spot.peripheral.II2C;
import com.sun.spot.resources.Resources;
import com.sun.spot.util.Utils;
import java.io.IOException;

import javax.microedition.midlet.*;

/**
 * Experimental Code for using the eDemo TWI module
 * This uses the PICKit Serial I2C Demo Board manufactured by Microchip Technology Inc.
 * Connect the +V on the PICKit to +3V on the eDemo, SDA to D2, SCK to D3 and GND to GND
 *
 * Please refer to the "I2C on the eDemoboard" Application Note for more information.
 *
 * Modified January 29. 2009 to use final I2C SPOT API
 */
public class TWITest extends MIDlet {

    private final static byte SLA_MCP9801  = (byte) 0x92;
    private final static byte SLA_MCP3221  = (byte) 0x9A;
    private final static byte SLA_TC1321   = (byte) 0x90;
    private final static byte SLA_24LC02B  = (byte) 0xA0;
    private final static byte SLA_MCP23008 = (byte) 0x40;

    // set WRITE_ME to true to write to memory
    // change it false and reload the code to show read from non-volatile memory	
    private final static boolean WRITE_ME = true;

    private II2C i2c = (II2C) Resources.lookup(II2C.class, "location=eDemoboard");
    // if using the I2C lines from the SPOT processor board then set:
    // i2c = (II2C) Resources.lookup(II2C.class, "location=on SPOT");

    /**
     * Reads the temperature in centigrade from MCP9801 on a PICkit board
     */
    private double getTemperature() {
        byte[] rcv = new byte[2];
        try {
            i2c.read(SLA_MCP9801, 0, 1, rcv, 0, 2);     // command byte = 0
        } catch (IOException e) {
            System.out.println("getTemperature: " + e);
        }
        int temp = (int) (rcv[0] << 8 | (rcv[1] & 0xFF));
        return ((double) temp) / 256.0;
    }

    /**
     * reads the ADC value from MCP3221 12bit ADC
     * adjusting trimmer pot on PICkit changes the ADC value from 0 to 4095
     * @return adc value Vin = (ADC * 3.0V)/4096
     */
    private int getADC() {
        byte[] rcv = new byte[2];
        try {
            i2c.read(SLA_MCP3221, rcv, 0, 2);
        } catch (IOException e) {
            System.out.println("getADC: " + e);
        }
        int temp = (int) (rcv[0] << 8 | (rcv[1] & 0xFF));
        return temp;
    }

    /**
     * sets the DAC output of TC1321 on the PICKit to level
     * @param level value 0 to 1023, Vout = (DAC * 3.0V)/1024
     */
    private void setDAC(int level) {
        byte[] snd = new byte[2];
        snd[0] = (byte) ((level >> 2) & 0xFF);
        snd[1] = (byte) (level & 0x03);
        try {
            i2c.write(SLA_TC1321, 0, 1, snd, 0, 2);     // command byte = 0
        } catch (IOException e) {
            System.out.println("setDAC: " + e);
        }
    }

    /**
     * write a byte to serial EEPROM 24LC02B on the PICkit
     * wants a delay after the write of about 50 to 100msec
     * @param adr 0 to 2047 byte address
     * @param data byte to write
     */
    private void writeByteEEPROM(int adr, byte data) {
        byte sla = (byte) (SLA_24LC02B | (((adr >> 8) & 7) << 1));
        byte[] snd = new byte[2];
        snd[0] = (byte) (adr & 0xFF); // word address
        snd[1] = data;
        try {
            i2c.write(sla, snd, 0, 2);
        } catch (IOException e) {
            System.out.println("writeByteEEPROM: " + e);
        }
    }

    /**
     * read a byte from serial EEPROM 24LC02B on the PICkit
     * 
     * @param adr 0 to 2047 byte address
     * @returns data byte read at address
     */
    private byte readByteEEPROM(int adr) {
        byte sla = (byte) (SLA_24LC02B | (((adr >> 8) & 7) << 1));
        byte[] rcv = new byte[1];
        try {
            i2c.read(sla, (byte) (adr & 0xFF), 1, rcv, 0, 1);     // command byte = address
        } catch (IOException e) {
            System.out.println("readByteEEPROM: " + e);
        }
        return rcv[0];
    }

    /**
     * set IO Expander for MCP23008 on the PCIKit
     * this connects to 8 LEDs which can be set by setting the direction to output and writing to the output latch
     * should be a getIOPort but leave it to the reader
     * @param adr 0 to 10 register address (see data sheet for register info)
     * @param data byte to write
     */
    private void setIOPort(int adr, int data) {
        byte[] snd = new byte[2];
        snd[0] = (byte) (adr & 0xFF); // address
        snd[1] = (byte) data;
        try {
            i2c.write(SLA_MCP23008, snd, 0, (byte) 2);
        } catch (IOException e) {
            System.out.println("setIOPort: " + e);
        }
    }

    private void run() throws IOException {
        System.out.println("Start EDemoTWI Test");
        // turn WRITE_ME off after first time running the program
        if (WRITE_ME) {
            String hello = "Hello Bob!";
            System.out.println("Writing \"" + hello + "\" to EEPROM");
            for (int i = 0; i < hello.length(); i++) {
                writeByteEEPROM(i, (byte) hello.charAt(i));
                Utils.sleep(100);
            }
            writeByteEEPROM(hello.length(), (byte) 0);
        }
        Utils.sleep(100);
        int j = 0;
        StringBuffer str = new StringBuffer();
        char ch;
        while ((ch = (char) readByteEEPROM(j++)) != 0) {
            str.append(ch);
        }
        System.out.println("Reading \"" + str.toString() + "\" from EEPROM");
        setIOPort(0, 0); // set as output // 3D 05 40 02 00 00 00, 05 BC 00, 42 01 2C 00
        j = 1;
        while (true) {
            System.out.println("Temperature: " + getTemperature() + "C");
            System.out.println("ADC: " + getADC());
            for (int i = 0; i < 1024; i++) {
                setDAC(i);
            }
            setIOPort(0x0A, (byte) j); // set as output 
            if (j == 128) {
                j = 1;
            } else {
                j = j << 1;
            }
            Utils.sleep(500);
        }

    }
 
    /**
     * startApp() is the MIDlet call that starts the application.
     */
    protected void startApp() throws MIDletStateChangeException {
        try {
            i2c.open();
            run();
        } catch (IOException ex) { //A problem in reading the sensors.
            ex.printStackTrace();
        } finally {
            try {
                i2c.close();
            } catch (IOException ex) { }
        }
    }

    /**
     * This will never be called by the Squawk VM.
     */
    protected void pauseApp() {
    }

    /**
     * Called if the MIDlet is terminated by the system.
     * I.e. if startApp throws any exception other than MIDletStateChangeException,
     * if the isolate running the MIDlet is killed with Isolate.exit(), or
     * if VM.stopVM() is called.
     * 
     * It is not called if MIDlet.notifyDestroyed() was called.
     */
    protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
    }
}
