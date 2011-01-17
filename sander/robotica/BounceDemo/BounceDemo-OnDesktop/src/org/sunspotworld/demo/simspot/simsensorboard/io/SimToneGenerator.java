/*
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

package org.sunspotworld.demo.simspot.simsensorboard.io;

import com.sun.spot.resources.Resource;
import com.sun.spot.resources.transducers.IToneGenerator;
import com.sun.spot.resources.transducers.SensorEvent;
import com.sun.spot.util.Utils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author randy
 */
public class SimToneGenerator extends Resource implements IToneGenerator {

    private Clip clipLine;
    private byte[] squareWaveBytes = new byte[40000];  //Enough for 20 kHz tone for 1 second.

    public SimToneGenerator(){
        init();
    }

    public void init(){
        addTag("speaker");
        for (int i = 0; i < squareWaveBytes.length; i += 2) {
            squareWaveBytes[i] = 10;
        }
        for (int i = 1; i < squareWaveBytes.length; i += 2) {
            squareWaveBytes[i] = -10;
        }
        AudioFormat format = new AudioFormat(4000.0f , 8, 1, true, false);
        DataLine.Info info = new DataLine.Info(Clip.class, format);
        if (!AudioSystem.isLineSupported(info)) {
            System.out.println("No appropriate Java sound supported on this device.");
            return;
        }
        try {
            clipLine = (Clip) AudioSystem.getLine(info);
        } catch (LineUnavailableException ex) {
            System.out.println("Java sound line unavailable");
        }
        clipLine.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void startTone(final double freq, final int dur) {
        if(clipLine == null) return;
        final int realDur = dur < 4 ?  4: dur;
        new Thread(){@Override
            public void run(){
                startTone(freq, dur);
                Utils.sleep(realDur);
                stopTone();
        }}.start();
    }

    public void startTone(double freq) {
        if(clipLine == null) return;
        AudioFormat format = new AudioFormat((float) (2.0 * freq), 8, 1, true, false);
        try {
            clipLine.open(format, squareWaveBytes, 0, squareWaveBytes.length);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(SimToneGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        clipLine.start();
    }

    public void stopTone() {
        if(clipLine == null) return;
       // clipLine.flush();
        clipLine.stop();
        clipLine.close();
    }

     /**
     * Play the given frequncy for the given duration (in ms)
     * blocking until done.
     *
     * @param freq
     * @param dur in milliseconds
     */
    public void startToneAndWait(double freq, int dur){
        if(clipLine == null) return;
        AudioFormat format = new AudioFormat((float) (2.0 * freq) , 8, 1, true, false);
        try {
            clipLine.open(format, squareWaveBytes, 0, squareWaveBytes.length);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(SimToneGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
        clipLine.start();
        Utils.sleep(dur);
        clipLine.stop();
        clipLine.flush();
        clipLine.close();
    }

    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getMaxSamplingRate() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public SensorEvent createSensorEvent() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void saveEventState(SensorEvent evt) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


    public void setFrequency(double d) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getFrequency() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDuration(int i) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void beep() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void startTone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void endTone() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
