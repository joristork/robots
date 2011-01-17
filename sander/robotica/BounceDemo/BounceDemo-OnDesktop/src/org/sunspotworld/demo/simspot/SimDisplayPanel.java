/*
 * Copyright (c) 2006 Sun Microsystems, Inc.
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

package org.sunspotworld.demo.simspot;
 
import com.sun.spot.peripheral.ISpot;
import com.sun.spot.resources.Resources;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Simply a JPanel that contains one simulated Sun SPOT.
 * @author randy
 */
public class SimDisplayPanel extends JPanel {
    
    
    /** Creates a new instance of GraphPanel */
    public SimDisplayPanel() {
        init();
    }
    
    public void init(){
        setBackground(Color.lightGray);
        setLayout(null);
        SimSPOT spot = new SimSPOT();
        double x = (getPreferredSize().getWidth()  - spot.getPreferredSize().getWidth() )/2.0 ;
        double y = (getPreferredSize().getHeight() - spot.getPreferredSize().getHeight())/2.0 ;
        spot.setPosition(x, y, 0.0);
        add(spot);
    }
    
    public Dimension getPreferredSize(){
        return new Dimension(500,500);
    }
    
    public void openInWindow(){
        JFrame f     = new JFrame("An extra Sun SPOT (Simulated)");
        
        // f.setJMenuBar(gg.createMenuBar());
        f.setContentPane(this);
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        f.pack();
        f.setVisible(true);
        this.requestFocusInWindow();
    }
    
//
    public void initBaseStation(){
        // THIS IS A NOOP SINCE THE ORANGE RELEASE 
//        try{
//            LowPanPacketDispatcher.getInstance().initBaseStation();
//        } catch (Exception ex){
//            ex.printStackTrace();
//            JFrame frame = new JFrame("Error initializing basestation");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            JOptionPane.showMessageDialog(frame,
//                    "Basestation plugged in and turned on?",
//                    "Error initializing basestation.",
//                    JOptionPane.WARNING_MESSAGE);
//        }
    }
}
