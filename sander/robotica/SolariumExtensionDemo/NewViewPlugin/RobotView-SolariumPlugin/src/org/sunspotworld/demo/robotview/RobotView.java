/*
 * Copyright 2009 Sun Microsystems, Inc.
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

package org.sunspotworld.demo.robotview;

import org.sunspotworld.demo.robotview.RobotWorld.Beacon;

import com.sun.spot.solarium.gui.DockingFrameUtilities;
import com.sun.spot.solarium.gui.JMenuSeparator;
import com.sun.spot.solarium.gui.SpotWorldPortal;
import com.sun.spot.solarium.gui.logging.Log;
import com.sun.spot.solarium.gui.views.planeview.PlaneView;
import com.sun.spot.solarium.gui.views.planeview.PlaneViewHolder;
import com.sun.spot.solarium.spotworld.SpotWorld;
import com.sun.spot.solarium.spotworld.common.ObjectMap;
import com.sun.spot.solarium.spotworld.emulator.EmulatorConfig;
import com.sun.spot.solarium.spotworld.participants.EmulatedSunSPOT;
import com.sun.spot.solarium.spotworld.virtualobjects.IVirtualObject;

import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 * A view for displaying SPOT controlled robots!
 * A first draft for FIRST.
 *
 * @author Ron Goldman
 */
public class RobotView extends PlaneView implements Runnable {

    private static final double MM_TO_SCREEN = RVObject.MM_TO_SCREEN;

    private TexturePaint paint;
    private BasicStroke stroke = new BasicStroke(3);
    private Color paleBlue = new Color(230, 240, 255);

    private RVObject selectedNode = null;
    private RVObject draggedNode = null;
    private int dragOffsetX;
    private int dragOffsetY;

    private Vector<RVObject> robots = new Vector<RVObject>();

    private RobotWorld emptyRoom;
    private RobotWorld maze;
    private RobotWorld obstacleCourse;
    private RobotWorld myWorld;

    private boolean isRunning = false;

    private JButton pauseButton;
    private int defaultWidth = 900;
    private int defaultHeight = 700;

    /**
     * Called by Solarium when user selects Robot View
     *
     * @param world
     * @throws IOException
     */
    public RobotView(SpotWorld world) throws IOException {
        super(world);
        setLayout(null);
        setBackground(paleBlue);
        setPreferredSize(new Dimension(defaultWidth + 100, defaultHeight + 100));

        // The origin of this coordinate system is a point in the view
        // offset from the upper left corner by the following amount, in
        // "world coordinates" (as opposed to the window's pixels).
        xOrgOffset = 0.0;
        yOrgOffset = 0.0;

        // setup the mappings of participants to my known classes
        objectMap = new ObjectMap();
        setToolTipText("Robot View");   // need to do this to enable tooltips

        emptyRoom = RobotWorld.createEmptyRoom((int)(defaultWidth / MM_TO_SCREEN), (int)(defaultHeight / MM_TO_SCREEN));
        maze = RobotWorld.createMaze((int)(2 * defaultWidth / MM_TO_SCREEN), (int)(2 * defaultHeight / MM_TO_SCREEN));
        obstacleCourse = RobotWorld.createObstacleCourse((int)(2 * defaultWidth / MM_TO_SCREEN), (int)(2 * defaultHeight / MM_TO_SCREEN));
        myWorld = emptyRoom;
    }

    public void removeNode(RVObject node) {
        robots.remove(node);
        repaint();
    }

    private RVObject findNode(long addr) {
        String address = IEEEAddress.toDottedHex(addr);
        for (RVObject node : robots) {
            if (address.equals(node.getAddress())) {
                return node;
            }
        }
        return null;
    }

    private void resetRobotPositions() {
        for (RVObject robot : robots) {
            robot.resetRobot();
            robot.setPosition(myWorld.getStartX(), myWorld.getStartY());
            robot.setHeading(myWorld.getStartHeading());
        }
    }

    public void run() {
        while (isRunning) {
            boolean moved = false;
            for (RVObject n : robots) {
                moved |= n.action(myWorld, robots);
            }
            if (moved) {
                repaint();
            }
            Utils.sleep(50);
        }
    }

    /**********************************************************
     *
     * GUI stuff
     *
     **********************************************************/

    private void paintRobots(Graphics2D g2) {
        int hoff = (int)yOrgOffset;
        int woff = (int)xOrgOffset;
        AffineTransform at = g2.getTransform();
        AffineTransform at2 = AffineTransform.getTranslateInstance(at.getTranslateX() + woff, at.getTranslateY() + hoff);
        at2.scale(getZoom(), getZoom());
        for (RVObject n : robots) {
            int x = (int) (n.getX() * MM_TO_SCREEN);
            int y = (int) (n.getY() * MM_TO_SCREEN);

            Image im = n.getImage();
            int w2 = im.getWidth(null) / 2;
            int h2 = im.getHeight(null) / 2;

            AffineTransform at3 = new AffineTransform(at2);
            at3.translate(x - w2, y - h2);
            g2.setTransform(at3);
            g2.drawImage(im, AffineTransform.getRotateInstance(Math.toRadians(n.getHeading()), w2, h2), this);

//            Line2D wallSensor = n.getSensorLine();
//            g2.setColor(Color.RED);
//            g2.draw(wallSensor);
            
            if (n == selectedNode) {
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(stroke);
                g2.setColor(Color.WHITE);
                g2.drawRect(-2, -2, 2 * w2 + 4, 2 * h2 + 15);
                g2.setStroke(oldStroke);
            }
            g2.setColor(Color.BLACK);
            g2.drawString(n.getAddress().substring(15), w2 - 16, 2 * h2 + 10);
        }
        g2.setTransform(at);
    }

    private void paintWalls(Graphics2D g2) {
        int hoff = (int)yOrgOffset;
        int woff = (int)xOrgOffset;
        AffineTransform at = g2.getTransform();
        AffineTransform at2 = AffineTransform.getTranslateInstance(at.getTranslateX() + woff, at.getTranslateY() + hoff);
        at2.scale(getZoom() * MM_TO_SCREEN, getZoom() * MM_TO_SCREEN);
        g2.setTransform(at2);
        for (Wall w : myWorld.getWalls()) {
            g2.setColor(w.getColor());
            g2.fill(w.getShape());
        }
        g2.setColor(Color.RED);
        Stroke oldStroke = g2.getStroke();
        g2.setStroke(stroke);
        for (Beacon n : myWorld.getBeacons()) {
            if (n.chr == 'X') {
                g2.drawLine(n.x - 80, n.y - 80, n.x + 80, n.y + 80);
                g2.drawLine(n.x + 80, n.y - 80, n.x - 80, n.y + 80);
            } else {
                g2.drawOval(n.x - 80, n.y - 80, 160, 160);
            }
        }
        g2.setStroke(oldStroke);
        g2.setTransform(at);
    }

    public void paintBackground(Graphics2D g2) {
        if (paint != null) {
            Paint oldPaint = g2.getPaint();
            g2.setPaint(paint);
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.setPaint(oldPaint);
        } else {
            g2.setColor(paleBlue);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);  // handle the background
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        paintBackground(g2);
        paintWalls(g2);
        paintRobots(g2);
    }

    private RVObject findNode(MouseEvent event) {
        int hoff = (int)yOrgOffset;
        int woff = (int)xOrgOffset;
        int x = (int)((event.getX() - woff) / (getZoom() * MM_TO_SCREEN));
        int y = (int)((event.getY() - hoff) / (getZoom() * MM_TO_SCREEN));
        for (RVObject n : robots) {
            if (n.ptInRobot(x, y)) {
                return n;
            }
        }
        return null;
    }

    public String getToolTipText(MouseEvent event) {
        String tooltip = null;

        RVObject n = findNode(event);
        if (n != null) {
            tooltip = n.toString();
        }
        return tooltip;
    }

    public void mouseDragged(MouseEvent event) {
        if (draggedNode != null) {
            int deltaX = event.getX() - dragOffsetX;
            int deltaY = event.getY() - dragOffsetY;
            draggedNode.moveBy(deltaX / getZoom() / MM_TO_SCREEN, deltaY / getZoom() / MM_TO_SCREEN);
            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
            repaint();
        }
    }

    public void mouseReleased(MouseEvent event) {
        if (event.isPopupTrigger()) {
            displayPopup(event);
        } else {
            if (draggedNode != null) {
                int deltaX = event.getX() - dragOffsetX;
                int deltaY = event.getY() - dragOffsetY;
                draggedNode.moveBy(deltaX / getZoom() / MM_TO_SCREEN, deltaY / getZoom() / MM_TO_SCREEN);
                dragOffsetX = event.getX();
                dragOffsetY = event.getY();
                selectedNode = draggedNode;
                draggedNode = null;
            } else {
                selectedNode = findNode(event);
            }
            repaint();
        }
    }

    public void mousePressed(MouseEvent event) {
        if (event.isPopupTrigger()) {
            displayPopup(event);
        } else {
            draggedNode = findNode(event);
            dragOffsetX = event.getX();
            dragOffsetY = event.getY();
        }
    }

    private void displayPopup(MouseEvent event) {
        RVObject n = findNode(event);
        if (n != null) {
            showNodePopupMenu(event, n);
            return;
        }
        // showViewPopupMenu(event);
    }

    // view popup is no longer used - now use Button bar at bottom
    private void showViewPopupMenu(MouseEvent event) {
        final RobotView view = this;
        JPopupMenu popup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Create new robot");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                EmulatedSunSPOT spot = EmulatorConfig.addVirtualSpot();
                String remoteAddress = spot.getAddress();
                RVObject node = new RVObject(spot, remoteAddress);
                node.setPosition(300, 350);
                robots.add(node);
                spot.addUIObject(node);
                node.setRobotView(view);
                repaint();
            }
        });
        popup.add(menuItem);
        popup.show(this, event.getX(), event.getY());
    }

    private void showNodePopupMenu(MouseEvent event, final RVObject n) {
        final RobotView self = this;
        final String src = n.getAddress();
        // System.out.println("display popup for " + (n.basestation ? "basestation " : "SPOT ") + src);
        JPopupMenu popup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Show robot info");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                String msg = n.toString();
                JTextArea txt = new JTextArea(msg);
                txt.setEditable(false);
                txt.setMargin(new Insets(8, 10, 14, 10));
                txt.setBackground(new Color(240, 240, 243));
                Dimension d = txt.getPreferredSize();
                Point p = self.getLocationOnScreen();
                int cx = p.x + (int) (n.getX() * MM_TO_SCREEN) + (n.getImage().getWidth(null) / 2) + (int) xOrgOffset;
                int cy = p.y + (int) (n.getY() * MM_TO_SCREEN) + (n.getImage().getHeight(null) / 2) + (self.getHeight() / 2);
                int x = cx - d.width / 2;
                int y = cy - d.height / 2;
                DockingFrameUtilities.displayNewPane(txt, "Robot " + src, x, y, d.width, d.height, false);
            }
        });
        popup.add(menuItem);
        menuItem = new JMenuItem("Rotate right");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                n.turnBy(45);
                self.repaint();
            }
        });
        popup.add(menuItem);
        menuItem = new JMenuItem("Rotate left");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                n.turnBy(-45);
                self.repaint();
            }
        });
        popup.add(menuItem);
        popup.add(new JMenuSeparator());
        menuItem = new JMenuItem("Delete robot...");
        menuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if (JOptionPane.showConfirmDialog(self,
                        "Please confirm killing any running MIDlets and \ndeleting this virtual robot " + n.getAddress(),
                        "Delete robot?", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
                    n.getEmulatedSunSPOT().delete();
                    removeNode(n);
                }
            }
        });
        popup.add(menuItem);
        popup.show(this, event.getX(), event.getY());
    }

    /*
     * Call from within the AWT thread, as this does paintImmediately(...).
     */
    public void setZoom(double z) {
        double change = z / zoom;
        Dimension d = getPreferredSize();
        setPreferredSize(new Dimension((int)(d.width * change), (int)(d.height * change)));
        revalidate();
        this.zoom = z;
        paintImmediately(getX(), getY(), getWidth(), getHeight());
    }

    /**
     * Return the View Holder, which is by default just a pane with the zoom controls.
     * This routine customizes the pane to include controls to select which Room to display
     * and buttons to Reset, Add robot & Pause.
     *
     * @return the View Holder pane
     */
    public Component getViewHolder() {
        if (holder == null) {
            holder = new PlaneViewHolder(this, true);
            final RobotView view = this;
            holder.addToButtonBox(Box.createHorizontalGlue());

            holder.addToButtonBox(new JLabel("Display: "));
            ButtonGroup vg = new ButtonGroup();
            JRadioButton rButton = new JRadioButton("Empty room ", true);
            vg.add(rButton);
            rButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    myWorld = emptyRoom;
                    resetRobotPositions();
                    setPreferredSize(new Dimension(defaultWidth + 100, defaultHeight + 100));
                    revalidate();
                    repaint();
                }
            });
            holder.addToButtonBox(rButton);
            rButton = new JRadioButton("Maze ", true);
            vg.add(rButton);
            rButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    myWorld = maze;
                    resetRobotPositions();
                    setPreferredSize(new Dimension(2 * defaultWidth + 100, 2 * defaultHeight + 100));
                    revalidate();
                    repaint();
                }
            });
            holder.addToButtonBox(rButton);
            rButton = new JRadioButton("Obstacle course", true);
            vg.add(rButton);
            rButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    myWorld = obstacleCourse;
                    resetRobotPositions();
                    setPreferredSize(new Dimension(2 * defaultWidth + 100, 2 * defaultHeight + 100));
                    revalidate();
                    repaint();
                }
            });
            holder.addToButtonBox(rButton);
            holder.addToButtonBox(Box.createHorizontalStrut(20));
            holder.addToButtonBox(Box.createHorizontalGlue());

            JButton resetButton = new JButton("Reset");
            resetButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    resetRobotPositions();
                    repaint();
                }
            });
            holder.addToButtonBox(resetButton);

            JButton addButton = new JButton("Add robot");
            addButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    EmulatedSunSPOT spot = EmulatorConfig.addVirtualSpot();
                    RVObject robot = new RVObject(spot, spot.getAddress());
                    robot.setPosition(myWorld.getStartX(), myWorld.getStartY());
                    robot.setHeading(myWorld.getStartHeading());
                    robots.add(robot);
                    spot.addUIObject(robot);
                    robot.setRobotView(view);
                    repaint();
                }
            });
            holder.addToButtonBox(addButton);

            pauseButton = new JButton("Pause");
            pauseButton.setPreferredSize(new Dimension(89, 29));
            pauseButton.setMinimumSize(new Dimension(89, 29));
            pauseButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (!isRunning) {
                        isRunning = true;
                        new Thread(view, "Animate Robot View").start();
                        pauseButton.setText("Pause");
                    } else {
                        isRunning = false;
                        pauseButton.setText("Resume");
                    }
                }
            });
            holder.addToButtonBox(pauseButton);
            holder.addToButtonBox(Box.createHorizontalStrut(20));

            isRunning = true;
            new Thread(view, "Animate Robot View").start();

        }
        return holder;
    }


    /***************************************************
     *
     * Solarium-related methods:
     *
     ***************************************************/

    protected void logInit() {
        SpotWorldPortal.log("Creating New Instance of RobotView");
    }

    /**
     * Adds a new virtual object to the view.
     * For now RobotView just ignores objects discovered by Solarium.
     *
     * @param obj - the object to be added
     */
    public void addVirtualObject(IVirtualObject obj) {
        // System.out.println("Adding vo: " + obj.getClass().getName());
    }

}
