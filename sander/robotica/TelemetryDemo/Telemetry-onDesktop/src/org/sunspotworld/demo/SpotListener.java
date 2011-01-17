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
 */

package org.sunspotworld.demo;

import com.sun.spot.io.j2me.radiogram.*;
import com.sun.spot.peripheral.ota.OTACommandServer;
import com.sun.spot.peripheral.radio.BasestationManager;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Enumeration;
import java.util.Vector;
import javax.microedition.io.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

/**
 * Simple example class to listen for a remote service (on a SPOT) and
 * to connect to it.
 *
 * @author Ron Goldman<br>
 * Date: August 1, 2010
 */
public class SpotListener extends Thread implements PacketTypes {

    public static ImageIcon aboutIcon = null;

    private static JFrame fr;
    private static Vector windows = new Vector();

    private static boolean baseStationPresent = false;
    private static RadiogramConnection rcvConn = null;
    private static DatagramConnection txConn = null;
    private static Datagram txDg;

    private static JTextArea status;
    private static long[] addresses = new long[20];
    private static TelemetryFrame[] frames = new TelemetryFrame[20];

    /**
     * Create a new SpotListener to connect to remote SPOTs over the radio.
     */
    public SpotListener () {
    }

    /**
     * Connect to base station & other initialization.
     */
    private void init () {
        baseStationPresent = true;              // assume we have a basestation
        boolean dedicatedBasestation = true;
        String tmp = System.getProperty("spot.basestation.sharing");
        if (tmp != null && "true".equalsIgnoreCase(tmp)) {  // using shared basestation
            dedicatedBasestation = false;
        } else {
            tmp = System.getProperty("SERIAL_PORT");        // does serial port = dummyport ?
            dedicatedBasestation = tmp != null && !"dummyport".equalsIgnoreCase(tmp);
        }
        if (!dedicatedBasestation) {
            baseStationPresent = BasestationManager.isSharedBasestationPresent();
        }
        if (!baseStationPresent) {
            status.append("No base station connected.\n");
	}
	try {
            rcvConn = (RadiogramConnection) Connector.open("radiogram://:" + BROADCAST_PORT);
            txConn = (DatagramConnection) Connector.open("radiogram://broadcast:" + CONNECTED_PORT);
            ((RadiogramConnection) txConn).setMaxBroadcastHops(4);
            txDg = txConn.newDatagram(10);
            baseStationPresent = true;
        } catch (Exception ex) {
            baseStationPresent = false;
            status.append("Problem connecting to base station: " + ex + "\n");
        }
    }
    

    /**
     * Main runtime loop to connect to a remote SPOT.
     * Do not call directly. Call start() instead.
     */
    public void run () {
        if (baseStationPresent) {
            broadcastCmd(DISPLAY_SERVER_RESTART); // Broadcast that the host display server is starting
            status.append("Listening for SPOTs...\n");
            listenForSpots();
        } else {
            while (true) {
                Utils.sleep(10000);
            }
        }
    }

    /**
     * Broadcast a simple command request to the remote SPOTs.
     *
     * @param cmd the command to send
     */
    private static void broadcastCmd (byte cmd) {
        if (baseStationPresent) {
            try {
                txDg.reset();
                txDg.writeByte(cmd);
                txConn.send(txDg);
            } catch (IOException ex) {
                status.append("Error broadcasting server start/quit command: " + ex.toString() + "\n");
            }
        }
    }

    /**
     * Stop running. Also notify the remote SPOTs that we are no longer listening to them.
     */
    public static void doQuit () {
        broadcastCmd(DISPLAY_SERVER_QUITTING);
        System.exit(0);
    }

    
    /**
     * Wait for a remote SPOT to request a connection.
     */
    private void listenForSpots () {
        while (true) {
            try {
                Datagram dg = rcvConn.newDatagram(10);
                rcvConn.receive(dg);            // wait until we receive a request
                if (dg.readByte() == LOCATE_DISPLAY_SERVER_REQ) {       // type of packet
                    String addr = dg.getAddress();
                    IEEEAddress ieeeAddr = new IEEEAddress(addr);
                    long macAddress = ieeeAddr.asLong();
                    System.out.println("Received request from: " + ieeeAddr.asDottedHex());
                    Datagram rdg = rcvConn.newDatagram(10);
                    rdg.reset();
                    rdg.setAddress(dg);
                    rdg.writeByte(DISPLAY_SERVER_AVAIL_REPLY);        // packet type
                    rdg.writeLong(macAddress);                        // requestor's ID
                    rcvConn.send(rdg);                                // send it
                    findFrame(macAddress);
                }
            } catch (IOException ex) {
                System.out.println("Error waiting for remote Spot: " + ex.toString());
                ex.printStackTrace();
            }
        }
    }

    private void setup() {
        aboutIcon = new ImageIcon(getClass().getResource("/org/sunspotworld/demo/racecar.gif"));
        fr = new JFrame("Telemetry Demo Host App");
        status = new JTextArea();
        JScrollPane sp = new JScrollPane(status);
        fr.add(sp);
        fr.setSize(360, 200);
        fr.validate();
        fr.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        fr.setTitle("Sun SPOTs Telemetry Demo");
        fr.setName("spotTelemetry");
        fr.setVisible(true);
        for (int i = 0; i < addresses.length; i++) {
            addresses[i] = 0;
            frames[i] = null;
        }
        initMenus();
        init();
        addFrame(fr);
    }

    private void initMenus() {
        int mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        boolean onMac = System.getProperty("os.name").toLowerCase().startsWith("mac os x");
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        if (!onMac) {
            JMenuItem aboutMenuItem = new JMenuItem();
            aboutMenuItem.setText("About...");
            aboutMenuItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    openAboutPanel();
                }
            });
            fileMenu.add(aboutMenuItem);
            fileMenu.add(new JSeparator());
        }
        JMenuItem openMenuItem = new JMenuItem();
        openMenuItem.setText("Open...");
        openMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
                int returnVal = chooser.showOpenDialog(fr);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = chooser.getSelectedFile();
                    GraphView gView = new GraphView();
                    if (gView.readTelemetryFile(file)) {
                        TelemetryFrame tf = new TelemetryFrame(file, gView);
                    } else {
                        status.append("Error reading file: " + file.getName() + "\n");
                    }
                }
            }
        });
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, mask));
        fileMenu.add(openMenuItem);

        if (!onMac) {
            JMenuItem quitMenuItem = new JMenuItem();
            quitMenuItem.setText("Quit");
            quitMenuItem.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    doQuit();
                }
            });
            fileMenu.add(new JSeparator());
            fileMenu.add(quitMenuItem);
            quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, mask));
        }
        menuBar.add(fileMenu);
        final JMenu windowMenu = new JMenu("Windows");
        windowMenu.addMenuListener(new MenuListener() {
            public void menuSelected(MenuEvent evt) {
                windowMenu.removeAll();
                for (Enumeration e = getWindows().elements(); e.hasMoreElements();) {
                    final JFrame tf = (JFrame) e.nextElement();
                    JMenuItem it = windowMenu.add(tf.getTitle());
                    it.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent evt) {
                            tf.setVisible(true);
                        }
                    });
                }
            }
            public void menuDeselected(MenuEvent evt) {
            }
            public void menuCanceled(MenuEvent evt) {
            }
        });

        menuBar.add(windowMenu);
        fr.setJMenuBar(menuBar);

        if (onMac) {
            setupMacGUI();
        }
    }

    private void findFrame(long addr) {
        for (int i = 0; i < addresses.length; i++) {
            if (addresses[i] == addr) {
                break;                              // already exists
            } else if (addresses[i] == 0) {         // need to create new display
                String ieee = IEEEAddress.toDottedHex(addr);
                addresses[i] = addr;
                status.append("Creating new display for " + ieee + "\n");
                frames[i] = new TelemetryFrame(ieee);
                final int ii = i;
                java.awt.EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        frames[ii].setVisible(true);
                    }
                });
                break;
            }
        }
    }

    public static Vector getWindows() {
        return windows;
    }

    public static void addFrame(JFrame fr) {
        windows.add(fr);
    }

    public static void removeFrame(TelemetryFrame fr) {
        for (int i = 0; i < frames.length; i++) {
            if (frames[i] == fr) {
                for (int j = i; (j + 1) < frames.length; j++) {
                    frames[j] = frames[j + 1];
                    addresses[j] = addresses[j + 1];
                }
                frames[frames.length - 1] = null;
                addresses[frames.length - 1] = 0;
                break;
            }
        }
        windows.remove(fr);
    }

    public void openAboutPanel() {
        JOptionPane.showMessageDialog(fr,
                "Sun SPOTs Telemetry Demo (Version " + TelemetryFrame.version + ")\n\n"
                + "A demo showing how to collect data from a SPOT and \n"
                + "send it to a desktop application to be displayed.\n\n"
                + "Author: Ron Goldman, Sun Labs\n"
                + "Date: " + TelemetryFrame.versionDate,
                "About Telemetry Demo",
                JOptionPane.INFORMATION_MESSAGE,
                SpotListener.aboutIcon);
    }
    
    private void setupMacGUI() {
        try {
            Class applicationListener = Class.forName("com.apple.eawt.ApplicationListener");
            Object proxy = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[] { applicationListener }, new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] args) {
                    if (method.getName().equals("handleQuit")) {
                        doQuit();
                    } else if (method.getName().equals("handleAbout")) {
                        try {
                            openAboutPanel();
                            Class applicationEventClass = Class.forName("com.apple.eawt.ApplicationEvent");
                            Method setHandled = applicationEventClass.getMethod("setHandled", boolean.class);
                            setHandled.invoke(args[0], true);
                        } catch (Exception ex) {
                            System.out.println("Error showing About Box: " + ex);
                        }
                    }
                    return null;
                }
            });

            Class applicationClass = Class.forName("com.apple.eawt.Application");
            Object applicationInstance = applicationClass.newInstance();

            Method m = applicationClass.getMethod("addApplicationListener", applicationListener);
            m.invoke(applicationInstance, proxy);

            if (aboutIcon != null) {
                try {
                    m = applicationClass.getMethod("setDockIconImage", Image.class);
                    m.invoke(applicationInstance, aboutIcon.getImage());
                } catch (NoSuchMethodException ex){
                    // ignore error as method doesn't exist in older versions of Apple Java
                }
            }
        } catch (Exception ex) {
            System.out.println("Error defining Apple Quit & About handlers: " + ex);
            ex.printStackTrace();
        }
    }

    /**
     * Start up the host application.
     *
     * @param args any command line arguments
     */
    public static void main(String[] args) throws Exception {
        // Set system properties for Mac OS X before AWT & Swing get loaded - doesn't hurt if not on a MAC
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "TelemetryDemo");

        // register the application's name with the OTA Command server & start OTA running
        OTACommandServer.start("TelemetryDemo");

        SpotListener us = new SpotListener();
        us.setup();
        us.start();
    }

}
