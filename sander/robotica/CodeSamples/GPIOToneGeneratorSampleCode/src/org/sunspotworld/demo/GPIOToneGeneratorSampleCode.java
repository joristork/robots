/*
 * Copyright (c) 2006-2010 Sun Microsystems, Inc.
 * Copyright (c) 2010 Oracle
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

package org.sunspotworld.demo;

/*
 * GPIOToneGeneratorSampleCode.java
 *
 * Illustrates use of the GPIO pin coupled with special driver software
 * to implement a tone generator. This program will play a major scale,
 * repeated indefinitely.
 * <p>
 * When this program is executed on a rev8 SPOT it will use the small speaker 
 * that is mounted on the sensor board. For older SPOTs you can use almost any
 * speaker and connect one wire to pin D0 and the other to ground pin on the
 * SPOT demo sensor board.
 * <p>
 * This sample illustrates how the programmer "binds" special drivers such as
 * the tone generator or the servo controller software to particular pins.
 * To use these services, bind the proper service to the desired pin,
 * and capture the returned object. That object has the methods you need
 * to interact with the pin as appropriate for that service.
 * In this case we have a tone generator. See initAndRun() below.
 * <p>
 *
 * author: Randy Smith, Steve Uhler
 * date: August 2, 2006<br>
 * modified: August 19, 2010
 */

import com.sun.spot.resources.Resources;
import com.sun.spot.resources.transducers.IIOPin;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.sensorboard.peripheral.ToneGenerator;
import com.sun.spot.util.Utils;
import java.io.IOException;

import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

public class GPIOToneGeneratorSampleCode extends MIDlet {
    
    /*
     * Convert note numbers into frequencies
     * to feed the eDemo board tone generator.
     * <p>
     * If we choose A=440 as the base frequency of an equal
     * tempered scale, and let A=110 (two octaves down = A2) be
     * note "0" (the lowest note), then:<br>
     *  f = 110 * 2 ^ (n/12)<br>
     * Where "f" is the note frequency in hz, and "n" is the note.
     * <p>
     * As the eDemo cpu load increases with increasing frequency,
     * the maximum allowable frequency is 3876hz.
     * <p>
     * "tone_map" contains the frequencies for the notes in the lowest
     * octave.
     */ 
    static double tone_map[] = {
        110.0, 116.541, 123.471, 130.813, 138.591, 146.832, 155.563, 164.814,
        174.614, 184.997, 195.998, 207.652
    };
    
    /*
     * These note values, when added to any starting note,
     * make up a major scale
     */ 
    static int major[] = { 0, 2, 4, 5, 7, 9, 11, 12 };

    IToneGenerator speaker;
    
    /**
     * Main method. 
     **/
    private void initAndRun() {

        speaker = (IToneGenerator) Resources.lookup(IToneGenerator.class, "speaker");
        if (speaker == null) {
            // Instantiate a tone generator by binding the driver. We use pin D0.
            IIOPin pinD0 = (IIOPin) Resources.lookup(IIOPin.class, "D0");
            speaker = new ToneGenerator(pinD0);
        }

        // Play a major scale
        int start_note = 21;	// arbitrary
        while(true){
            for (int i=0;i<major.length;i++) {
                playNote(speaker, major[i] + start_note, 500, 80);
            }
        }
    }
    
    /**
     * Calculate the frequency of a note.
     *
     * @param note number of the note to play (0 = A110)
     * @return the frequency of the note
     **/
    public static double note2freq(int note) {
        // The tone_map[] only covers the lowest octave, so account for
        // multiple octaves by multiplying by the appropriate power of 2.
        int octave = note/12;
        return tone_map[note%12] * (1<<octave);
    }
    
    /**
     * Play a note:
     * @param tone	The tone generator to use
     * @param note	The note number (0=A110)
     * @param dur	The total note duration in ms
     * @param len	The % of the total duration the note is playing
     */
    void playNote(IToneGenerator tone, int note, int dur, int len) {
        int on = dur * len/100; 
        tone.startTone(note2freq(note));      // Play the tone
        Utils.sleep(on);
        tone.stopTone();                        // now rest
        Utils.sleep(dur - on);
    }
    
    /**
     * startApp() is the MIDlet call that starts the application.
     */
    protected void startApp() throws MIDletStateChangeException {
        // Listen for downloads/commands over USB connection
        new com.sun.spot.service.BootloaderListenerService().getInstance().start();

        initAndRun();
    }
    
    /**
     * This will never be called by the Squawk VM.
     */
    protected void pauseApp() {
    }
    
    /**
     * Called if the MIDlet is terminated by the system.
     */
    protected void destroyApp(boolean unconditional) throws MIDletStateChangeException {
    }
}
