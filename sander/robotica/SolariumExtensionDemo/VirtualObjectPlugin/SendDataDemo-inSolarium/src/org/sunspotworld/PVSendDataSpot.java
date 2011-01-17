/*
 * Copyright (c) 2008 Sun Microsystems, Inc.
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

package org.sunspotworld;
 
import com.sun.spot.solarium.gui.views.planeview.PVeSPOT;
import com.sun.spot.solarium.gui.GuiUtils;
import com.sun.spot.solarium.gui.JMenuSeparator;
import com.sun.spot.solarium.gui.views.planeview.PVComponentHolder;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Vector;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenuItem;

/**
 * Plane View GUI to a SunSPOT running the Send Data Demo.
 *
 * @author Ron Goldman
 */
public class PVSendDataSpot extends PVeSPOT {

    private JInternalFrame internalOutput = null;

    public void createIcons(){
        URL url      = getClass().getResource("/images/sendDataSPOTV2.png");
        URL urlMask  = getClass().getResource("/images/eSPOTV2-Mask2.gif");

        icon     = new ImageIcon(url, "a Send Data Demo eSPOT Icon");
        iconMask = new ImageIcon(urlMask, "an eSPOT Icon Mask");
    }

    public Vector<JMenuItem> getMenuItems() {
        Vector<JMenuItem> menuItems = new Vector<JMenuItem>();
        JMenuItem plot = new JMenuItem("Show Send Data Graph");
        menuItems.add(plot);
        plot.setToolTipText("Display the data received from this SPOT");
        plot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (internalOutput != null) {
                    internalOutput.setVisible(true);
                } else {
                    internalOutput = new JInternalFrame("Light Sensor Data", false, true, false);
                    internalOutput.setContentPane(getSpot().getSendDataPanel());
                    internalOutput.pack();
                    internalOutput.validate();
                    internalOutput.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
                    PVComponentHolder tch = new PVComponentHolder() {
                        public void paintShadow(Graphics2D g2) { }
                    };
                    tch.add(internalOutput);
                    /* place to left, centered on presumably the SPOT */
                    tch.setLocation(getLocation().x - (tch.getBasicWidth() - 20),
                            getLocation().y + (getBasicHeight() - tch.getBasicHeight()) / 2 + 20);
                    addLoosePiece(tch);
                    tch.setVisible(true);
                    tch.addToView(getView());
                }
            }
        });
        menuItems.add(new JMenuSeparator());

        if (getSpot() != null) {
            menuItems.addAll(GuiUtils.commandsToMenuItems(getSpot().getSpotMethods(), this));
        }
        return menuItems;
    }

    public SendDataSpot getSpot(){
        return (SendDataSpot) getVirtualObject();
    }


}
