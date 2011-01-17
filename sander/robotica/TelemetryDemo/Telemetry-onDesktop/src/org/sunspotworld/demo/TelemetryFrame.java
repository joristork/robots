/*
 * Copyright (c) 2007 Sun Microsystems, Inc.
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

import com.sun.spot.peripheral.ota.OTACommandServer;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.*;
import javax.swing.*;
import java.util.*;
import java.text.*;

/**
 * GUI creating code to make a window to display accelerometer data gathered
 * from a remote SPOT. Provides the user interface to interact with the SPOT
 * and to control the telemetry data collected.
 *
 * @author Ron Goldman<br>
 * date: May 2, 2006<br>
 * revised: August 1, 2007<br>
 * revised: August 1, 2010
 */
public class TelemetryFrame extends JFrame implements Printable {

    public static String version = "2.0";
    public static String versionDate = "August 1, 2010";
    private static final Font footerFont = new Font("Serif", Font.PLAIN, 9);
    private static final DateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy  HH:mm z");
    
    private AccelerometerListener listener;
    private GraphView graphView;
    private JPanel axisView;
    private boolean sendData = false;
    private File file = null;
    private boolean fixedData = false;
    private boolean clearedData = true;

    private PrinterJob printJob = PrinterJob.getPrinterJob();
    private PageFormat pageFormat = printJob.defaultPage();

    private int[] scales = { 2, 6 };
    private int currentScale = 0;

    /**
     * Check that new window has a unique name.
     *
     * @param str proposed new window name
     * @return true if current name is unique, false if it is the same as another window
     */
    private boolean checkTitle(String str) {
        boolean results = true;
        for (Enumeration e = SpotListener.getWindows().elements() ; e.hasMoreElements() ;) {
            JFrame fr = (JFrame)e.nextElement();
            if (str.equals(fr.getTitle())) {
                results = false;
                break;
            }
        }
        return results;
    }

    /**
     * Creates a new TelemetryFrame window.
     *
     * @param ieee address of SPOT
     */
    public TelemetryFrame(String ieee) {
        init(ieee, null, null);
    }
    
    /**
     * Creates a new TelemetryFrame window with an associated file.
     *
     * @param file the file to read/write accelerometer data from/to
     */
    public TelemetryFrame(File file, GraphView graphView) {
        init(null, file, graphView);
    }

    /**
     * Initialize the new TelemetryFrame
     */
    private void init(String ieee, File file, GraphView graphView) {
        initComponents();
        setupAcceleratorKeys();
        if (System.getProperty("os.name").toLowerCase().startsWith("mac os x")) {
            aboutMenuItem.setVisible(false);
            jSeparator5.setVisible(false);
            quitMenuItem.setVisible(false);
            jSeparator2.setVisible(false);
        }

        if (graphView == null) {
            setGraphView(new GraphView());
        } else {
            setGraphView(graphView);
        }
        if (ieee != null) {
            this.setTitle("SPOT " + ieee);
            listener = new AccelerometerListener(ieee, this, graphView);
            listener.start();
            setConnectionStatus(true, "Connected");
        }
        this.file = file;
        if (file != null) {
            this.setTitle(file.getName());
            fixedData = true;
            clearedData = false;
            twoGRadioButton.setEnabled(false);
            sixGRadioButton.setEnabled(false);
            eightGRadioButton.setEnabled(false);
            twoGRadioButton.setText(graphView.getScale() + "G             ");
            sixGRadioButton.setVisible(false);
            eightGRadioButton.setVisible(false);
            sendDataButton.setVisible(false);
            clearButton.setVisible(false);
            calibrateButton.setVisible(false);
            pingButton.setVisible(false);
            blinkButton.setVisible(false);
            reconnButton.setVisible(false);
        } else {
            sixGRadioButton.setText("4G");
        }
        pageFormat.setOrientation(PageFormat.LANDSCAPE);

        setVisible(true);
        String str = getTitle();
        if (!checkTitle(str)) {
            int i = 1;
            while (true) {
                if (checkTitle(str + "-" + i)) {
                    setTitle(str + "-" + i);
                    break;
                } else {
                    i++;
                }
            }
        }
        SpotListener.addFrame(this);
    }
    
    /**
     * Make sure the correct command key is used.
     */
    private void setupAcceleratorKeys() {
        int mask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, mask));
        closeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, mask));
        saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, mask));
        printMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, mask));
        quitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, mask));
    }

    /**
     * Set the GraphView to display accelerometer values for this window.
     */
    private void setGraphView(GraphView gv) {
        graphView = gv;
        graphViewScrollPane.setViewportView(gv);
        gv.setViewport(graphViewScrollPane.getViewport());
        gv.setMaxGLabel(maxGLabel);
        Integer fieldWidth = (Integer)filterWidthField.getValue();
        graphView.setFilterWidth(fieldWidth.intValue() - 1);
        final GraphView gview = gv;
        axisView = new JPanel(){
            public Dimension getPreferredSize() {
                return new Dimension(GraphView.AXIS_WIDTH, gview.getPreferredSize().height);
            }
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                gview.paintYaxis(g);
            }
        };
        axisView.setBackground(Color.WHITE);
        y_axisPanel.add(axisView);
        graphView.setAxisPanel(axisView);
        if (fixedData) {
            twoGRadioButton.setSelected(true);
            twoGRadioButton.setText(graphView.getScale() + "G");
            twoGRadioButton.setEnabled(false);
            sixGRadioButton.setVisible(false);
            eightGRadioButton.setVisible(false);
        }
    }

    private void setScale() {
        twoGRadioButton.setSelected(currentScale == 0);
        sixGRadioButton.setSelected(currentScale == 1);
        eightGRadioButton.setSelected(currentScale == 2);
    }

    public void setScale(int newScale) {
        graphView.setScale(newScale);
    }

    /**
     * Display the current connection status to a remote SPOT. 
     * Called by the AccelerometerListener whenever the radio connection status changes.
     *
     * @param conn true if now connected to a remote SPOT
     * @param msg the String message to display, includes the 
     */
    public void setConnectionStatus(boolean conn, String msg) {
        connStatusLabel.setText(msg);
        blinkButton.setEnabled(conn);
        pingButton.setEnabled(conn);
        reconnButton.setEnabled(conn);
        if (!fixedData) {
            if (conn) {
                scales = listener.getScales();
                currentScale = listener.getCurrentScale();
                sixGRadioButton.setText(scales[1] + "G");
                eightGRadioButton.setVisible(scales.length > 2);
                setScale();
            }
            twoGRadioButton.setEnabled(conn);
            sixGRadioButton.setEnabled(conn);
            eightGRadioButton.setEnabled(conn);
            sendDataButton.setEnabled(conn);
            clearButton.setEnabled(conn);
            calibrateButton.setEnabled(conn);
        }
    }
    
    /**
     * Select a (new) file to save the accelerometer data in.
     */
    private void doSaveAs() {
        JFileChooser chooser;
        if (file != null) {
            chooser = new JFileChooser(file.getParent());
        } else {
            chooser = new JFileChooser(System.getProperty("user.dir"));
        }
        int returnVal = chooser.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            if (file.exists()) {
                int n = JOptionPane.showConfirmDialog(this, "The file: " + file.getName() + 
                                                      " already exists. Do you wish to replace it?",
                                                      "File Already Exists",
                                                      JOptionPane.YES_NO_OPTION);
                if (n != JOptionPane.YES_OPTION) {
                    return;                             // cancel the Save As command
                }
            }
            setTitle(file.getName());
            doSave();
        }
    }
    
    /**
     * Save the current accelerometer data to the file associated with this window.
     */
    private void doSave() {
        if (graphView.writeData(file)) {
            saveMenuItem.setEnabled(false);
        }
    }
    
    /**
     * Routine to print out each page of the current graph with a footer.
     *
     * @param g the graphics context to use to print
     * @param pageFormat how big is each page
     * @param pageIndex the page to print
     */
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) {
        double xscale = 0.5;
        double yscale = 0.75;
        int mx = 40;
        int my = 30;
        double x0 = pageFormat.getImageableX() + mx;
        double y0 = pageFormat.getImageableY() + my;
        double axisW = GraphView.AXIS_WIDTH * xscale;
        double w = pageFormat.getImageableWidth() - axisW - 2 * mx;
        double h = pageFormat.getImageableHeight() - 2 * my;
        int pagesNeeded = (int) (xscale * graphView.getMaxWidth() / w);
        if (pageIndex > pagesNeeded) {
            return(NO_SUCH_PAGE);
        } else {
            Graphics2D g2d = (Graphics2D)g;
            // first print our footer
            int y = (int) (y0 + h + 18);
            g2d.setPaint(Color.black);
            g2d.setFont(footerFont);
            g2d.drawString(dateFormat.format(new Date()).toString(), (int) (x0 + 5), y);
            if (file != null) {
                String name = file.getName();
                g2d.drawString(name, (int) (x0 + w/2 - 2 * name.length() / 2), y);
            }
            g2d.drawString((pageIndex + 1) + "/" + (pagesNeeded + 1), (int) (x0 + w - 20), y);
            
            // now print the Y-axis
            axisView.setDoubleBuffered(false);
            g2d.translate(x0, y0);
            g2d.scale(xscale, yscale);
            axisView.paint(g2d);
            axisView.setDoubleBuffered(true);

            // now have graph view print the next page
            // note: while the values to translate & setClip work they seem wrong. Why 2 * axisW ???
            graphView.setDoubleBuffered(false);
            g2d.translate(2 * axisW + 1 - (w * pageIndex) / xscale, 0);
            g2d.setClip((int)((w * pageIndex) / xscale + 2), 0, (int)(w / xscale), (int)(h / yscale));
            graphView.paint(g2d);
            graphView.setDoubleBuffered(true);
                    
            return(PAGE_EXISTS);
        }
    }

    /**
     * Routine to bring the user selected window to the front.
     *
     * @param evt the menu command with the name of the selected window
     */
    private void windowSelected(ActionEvent evt) {
        String str = evt.getActionCommand();
        for (Enumeration e = SpotListener.getWindows().elements() ; e.hasMoreElements() ;) {
            JFrame fr = (JFrame)e.nextElement();
            if (str.equals(fr.getTitle())) {
                fr.setVisible(true);
                break;
            }
        }
    }


    /**
     * Cleanly exit.
     */
    private void doQuit() {
        SpotListener.doQuit();
    }
    
    // GUI code generated using NetBeans GUI editor:

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        fullscaleGroup = new javax.swing.ButtonGroup();
        xZoomGroup = new javax.swing.ButtonGroup();
        yZoomGroup = new javax.swing.ButtonGroup();
        smoothGroup = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        y_axisPanel = new javax.swing.JPanel();
        graphViewScrollPane = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        axisPanel = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        xZoomButton1 = new javax.swing.JRadioButton();
        xZoomButton2 = new javax.swing.JRadioButton();
        xZoomButton3 = new javax.swing.JRadioButton();
        xZoomButton4 = new javax.swing.JRadioButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        yZoomButton1 = new javax.swing.JRadioButton();
        yZoomButton2 = new javax.swing.JRadioButton();
        yZoomButton3 = new javax.swing.JRadioButton();
        yZoomButton4 = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        xCheckBox = new javax.swing.JCheckBox();
        yCheckBox = new javax.swing.JCheckBox();
        zCheckBox = new javax.swing.JCheckBox();
        gCheckBox = new javax.swing.JCheckBox();
        jPanel4 = new javax.swing.JPanel();
        gPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        twoGRadioButton = new javax.swing.JRadioButton();
        sixGRadioButton = new javax.swing.JRadioButton();
        eightGRadioButton = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        maxGLabel = new javax.swing.JLabel();
        connStatusLabel = new javax.swing.JLabel();
        smoothPanel = new javax.swing.JPanel();
        gravityCheckBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        noSmoothingButton = new javax.swing.JRadioButton();
        boxcarSmoothingButton = new javax.swing.JRadioButton();
        triangleSmoothingButton = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        filterWidthField = new javax.swing.JFormattedTextField();
        buttonPanel = new javax.swing.JPanel();
        clearButton = new javax.swing.JButton();
        calibrateButton = new javax.swing.JButton();
        sendDataButton = new javax.swing.JButton();
        pingButton = new javax.swing.JButton();
        blinkButton = new javax.swing.JButton();
        reconnButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        aboutMenuItem = new javax.swing.JMenuItem();
        jSeparator5 = new javax.swing.JSeparator();
        openMenuItem = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JSeparator();
        closeMenuItem = new javax.swing.JMenuItem();
        jSeparator4 = new javax.swing.JSeparator();
        saveMenuItem = new javax.swing.JMenuItem();
        saveAsMenuItem = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        pagesetupMenuItem = new javax.swing.JMenuItem();
        printMenuItem = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JSeparator();
        quitMenuItem = new javax.swing.JMenuItem();
        windowMenu = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sun SPOTs Telemetry Demo");
        setName("spotTelemetry"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });
        getContentPane().setLayout(new java.awt.BorderLayout(0, 5));

        jPanel1.setPreferredSize(new java.awt.Dimension(870, 525));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        y_axisPanel.setAlignmentX(1.0F);
        y_axisPanel.setAlignmentY(0.0F);
        y_axisPanel.setMaximumSize(new java.awt.Dimension(65, 3725));
        y_axisPanel.setMinimumSize(new java.awt.Dimension(65, 125));
        y_axisPanel.setPreferredSize(new java.awt.Dimension(65, 525));
        y_axisPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        jPanel1.add(y_axisPanel, gridBagConstraints);

        graphViewScrollPane.setBorder(null);
        graphViewScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        graphViewScrollPane.setAutoscrolls(true);
        graphViewScrollPane.setMaximumSize(new java.awt.Dimension(32767, 7725));
        graphViewScrollPane.setMinimumSize(new java.awt.Dimension(350, 125));
        graphViewScrollPane.setPreferredSize(new java.awt.Dimension(800, 525));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(graphViewScrollPane, gridBagConstraints);

        jPanel2.setMaximumSize(new java.awt.Dimension(5, 7725));
        jPanel2.setMinimumSize(new java.awt.Dimension(5, 125));
        jPanel2.setPreferredSize(new java.awt.Dimension(5, 525));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        jPanel1.add(jPanel2, gridBagConstraints);

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        jPanel3.setAlignmentX(0.0F);
        jPanel3.setAlignmentY(0.0F);
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 147));
        jPanel3.setMinimumSize(new java.awt.Dimension(605, 103));
        jPanel3.setPreferredSize(new java.awt.Dimension(950, 103));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        axisPanel.setAlignmentX(0.0F);
        axisPanel.setAlignmentY(0.0F);
        axisPanel.setMaximumSize(new java.awt.Dimension(350, 90));
        axisPanel.setMinimumSize(new java.awt.Dimension(275, 84));
        axisPanel.setPreferredSize(new java.awt.Dimension(295, 86));
        axisPanel.setLayout(new java.awt.GridLayout(3, 1));

        jPanel5.setAlignmentX(0.0F);
        jPanel5.setMinimumSize(new java.awt.Dimension(350, 28));
        jPanel5.setPreferredSize(new java.awt.Dimension(350, 28));
        jPanel5.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel5.setText(" Zoom x-axis:");
        jPanel5.add(jLabel5);

        xZoomGroup.add(xZoomButton1);
        xZoomButton1.setText("0.5x");
        xZoomButton1.setIconTextGap(2);
        xZoomButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        xZoomButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xZoomButton1ActionPerformed(evt);
            }
        });
        jPanel5.add(xZoomButton1);

        xZoomGroup.add(xZoomButton2);
        xZoomButton2.setSelected(true);
        xZoomButton2.setText("1x");
        xZoomButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        xZoomButton2.setMaximumSize(new java.awt.Dimension(48, 18));
        xZoomButton2.setMinimumSize(new java.awt.Dimension(45, 18));
        xZoomButton2.setPreferredSize(new java.awt.Dimension(48, 18));
        xZoomButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xZoomButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(xZoomButton2);

        xZoomGroup.add(xZoomButton3);
        xZoomButton3.setText("2x");
        xZoomButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        xZoomButton3.setMaximumSize(new java.awt.Dimension(48, 18));
        xZoomButton3.setMinimumSize(new java.awt.Dimension(45, 18));
        xZoomButton3.setPreferredSize(new java.awt.Dimension(48, 18));
        xZoomButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xZoomButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(xZoomButton3);

        xZoomGroup.add(xZoomButton4);
        xZoomButton4.setText("4x");
        xZoomButton4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        xZoomButton4.setMaximumSize(new java.awt.Dimension(48, 18));
        xZoomButton4.setMinimumSize(new java.awt.Dimension(45, 18));
        xZoomButton4.setPreferredSize(new java.awt.Dimension(48, 18));
        xZoomButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xZoomButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(xZoomButton4);

        axisPanel.add(jPanel5);

        jPanel6.setAlignmentX(0.0F);
        jPanel6.setMinimumSize(new java.awt.Dimension(350, 28));
        jPanel6.setPreferredSize(new java.awt.Dimension(350, 28));
        jPanel6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("y-axis:");
        jLabel2.setMaximumSize(new java.awt.Dimension(90, 16));
        jLabel2.setMinimumSize(new java.awt.Dimension(90, 16));
        jLabel2.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel6.add(jLabel2);

        yZoomGroup.add(yZoomButton1);
        yZoomButton1.setSelected(true);
        yZoomButton1.setText("1x  ");
        yZoomButton1.setMargin(new java.awt.Insets(0, 0, 0, 0));
        yZoomButton1.setMaximumSize(new java.awt.Dimension(56, 18));
        yZoomButton1.setMinimumSize(new java.awt.Dimension(55, 18));
        yZoomButton1.setPreferredSize(new java.awt.Dimension(56, 18));
        yZoomButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yZoomButton1ActionPerformed(evt);
            }
        });
        jPanel6.add(yZoomButton1);

        yZoomGroup.add(yZoomButton2);
        yZoomButton2.setText("2x");
        yZoomButton2.setMargin(new java.awt.Insets(0, 0, 0, 0));
        yZoomButton2.setMaximumSize(new java.awt.Dimension(48, 18));
        yZoomButton2.setMinimumSize(new java.awt.Dimension(45, 18));
        yZoomButton2.setPreferredSize(new java.awt.Dimension(48, 18));
        yZoomButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yZoomButton2ActionPerformed(evt);
            }
        });
        jPanel6.add(yZoomButton2);

        yZoomGroup.add(yZoomButton3);
        yZoomButton3.setText("4x");
        yZoomButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        yZoomButton3.setMaximumSize(new java.awt.Dimension(48, 18));
        yZoomButton3.setMinimumSize(new java.awt.Dimension(45, 18));
        yZoomButton3.setPreferredSize(new java.awt.Dimension(48, 18));
        yZoomButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yZoomButton3ActionPerformed(evt);
            }
        });
        jPanel6.add(yZoomButton3);

        yZoomGroup.add(yZoomButton4);
        yZoomButton4.setText("8x");
        yZoomButton4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        yZoomButton4.setMaximumSize(new java.awt.Dimension(48, 18));
        yZoomButton4.setMinimumSize(new java.awt.Dimension(45, 18));
        yZoomButton4.setPreferredSize(new java.awt.Dimension(48, 18));
        yZoomButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yZoomButton4ActionPerformed(evt);
            }
        });
        jPanel6.add(yZoomButton4);

        axisPanel.add(jPanel6);

        jPanel7.setMinimumSize(new java.awt.Dimension(350, 28));
        jPanel7.setPreferredSize(new java.awt.Dimension(350, 28));
        jPanel7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Show accel:");
        jLabel1.setMaximumSize(new java.awt.Dimension(90, 16));
        jLabel1.setMinimumSize(new java.awt.Dimension(90, 16));
        jLabel1.setPreferredSize(new java.awt.Dimension(90, 16));
        jPanel7.add(jLabel1);
        jLabel1.getAccessibleContext().setAccessibleName("Show accel: ");

        xCheckBox.setForeground(new java.awt.Color(0, 150, 0));
        xCheckBox.setSelected(true);
        xCheckBox.setText("aX ");
        xCheckBox.setMargin(new java.awt.Insets(0, 1, 0, 0));
        xCheckBox.setMaximumSize(new java.awt.Dimension(48, 18));
        xCheckBox.setMinimumSize(new java.awt.Dimension(48, 18));
        xCheckBox.setPreferredSize(new java.awt.Dimension(56, 18));
        xCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xCheckBoxActionPerformed(evt);
            }
        });
        jPanel7.add(xCheckBox);

        yCheckBox.setForeground(java.awt.Color.blue);
        yCheckBox.setSelected(true);
        yCheckBox.setText("aY");
        yCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        yCheckBox.setMaximumSize(new java.awt.Dimension(48, 18));
        yCheckBox.setMinimumSize(new java.awt.Dimension(45, 18));
        yCheckBox.setPreferredSize(new java.awt.Dimension(48, 18));
        yCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yCheckBoxActionPerformed(evt);
            }
        });
        jPanel7.add(yCheckBox);

        zCheckBox.setForeground(java.awt.Color.red);
        zCheckBox.setSelected(true);
        zCheckBox.setText("aZ");
        zCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        zCheckBox.setMaximumSize(new java.awt.Dimension(48, 18));
        zCheckBox.setMinimumSize(new java.awt.Dimension(45, 18));
        zCheckBox.setPreferredSize(new java.awt.Dimension(48, 18));
        zCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zCheckBoxActionPerformed(evt);
            }
        });
        jPanel7.add(zCheckBox);

        gCheckBox.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        gCheckBox.setForeground(new java.awt.Color(255, 140, 0));
        gCheckBox.setSelected(true);
        gCheckBox.setText("|a|");
        gCheckBox.setIconTextGap(2);
        gCheckBox.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gCheckBox.setMaximumSize(new java.awt.Dimension(48, 18));
        gCheckBox.setMinimumSize(new java.awt.Dimension(45, 18));
        gCheckBox.setPreferredSize(new java.awt.Dimension(48, 18));
        gCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gCheckBoxActionPerformed(evt);
            }
        });
        jPanel7.add(gCheckBox);

        axisPanel.add(jPanel7);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        jPanel3.add(axisPanel, gridBagConstraints);

        jPanel4.setMinimumSize(new java.awt.Dimension(591, 56));
        jPanel4.setPreferredSize(new java.awt.Dimension(455, 56));
        jPanel4.setLayout(new java.awt.GridLayout(2, 1));

        gPanel.setAlignmentX(0.0F);
        gPanel.setAlignmentY(0.75F);
        gPanel.setMaximumSize(new java.awt.Dimension(32767, 28));
        gPanel.setMinimumSize(new java.awt.Dimension(345, 28));
        gPanel.setPreferredSize(new java.awt.Dimension(611, 28));
        gPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 5));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("  Scale:");
        gPanel.add(jLabel8);

        fullscaleGroup.add(twoGRadioButton);
        twoGRadioButton.setSelected(true);
        twoGRadioButton.setText("2G");
        twoGRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        twoGRadioButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        twoGRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoGRadioButtonActionPerformed(evt);
            }
        });
        gPanel.add(twoGRadioButton);

        fullscaleGroup.add(sixGRadioButton);
        sixGRadioButton.setText("6G ");
        sixGRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        sixGRadioButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        sixGRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sixGRadioButtonActionPerformed(evt);
            }
        });
        gPanel.add(sixGRadioButton);

        fullscaleGroup.add(eightGRadioButton);
        eightGRadioButton.setText("8G ");
        eightGRadioButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        eightGRadioButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        eightGRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eightGRadioButtonActionPerformed(evt);
            }
        });
        gPanel.add(eightGRadioButton);

        jLabel9.setText("  Max acceleration |a| = ");
        gPanel.add(jLabel9);

        maxGLabel.setText("0.0");
        maxGLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        maxGLabel.setMaximumSize(new java.awt.Dimension(80, 16));
        maxGLabel.setMinimumSize(new java.awt.Dimension(40, 16));
        maxGLabel.setPreferredSize(new java.awt.Dimension(40, 16));
        gPanel.add(maxGLabel);

        connStatusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        connStatusLabel.setText("Not connected");
        connStatusLabel.setFocusTraversalPolicyProvider(true);
        connStatusLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        connStatusLabel.setMaximumSize(new java.awt.Dimension(320, 16));
        connStatusLabel.setMinimumSize(new java.awt.Dimension(100, 16));
        connStatusLabel.setPreferredSize(new java.awt.Dimension(115, 16));
        gPanel.add(connStatusLabel);

        jPanel4.add(gPanel);

        smoothPanel.setAlignmentX(0.0F);
        smoothPanel.setAlignmentY(0.0F);
        smoothPanel.setMaximumSize(new java.awt.Dimension(32767, 26));
        smoothPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 2, 5));

        gravityCheckBox.setSelected(true);
        gravityCheckBox.setText("Include Gravity    ");
        gravityCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        gravityCheckBox.setMargin(new java.awt.Insets(0, 2, 2, 0));
        gravityCheckBox.setMaximumSize(new java.awt.Dimension(155, 18));
        gravityCheckBox.setMinimumSize(new java.awt.Dimension(155, 18));
        gravityCheckBox.setPreferredSize(new java.awt.Dimension(155, 18));
        gravityCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gravityCheckBoxActionPerformed(evt);
            }
        });
        smoothPanel.add(gravityCheckBox);

        jLabel3.setText("Smooth data: ");
        smoothPanel.add(jLabel3);

        smoothGroup.add(noSmoothingButton);
        noSmoothingButton.setSelected(true);
        noSmoothingButton.setText("No ");
        noSmoothingButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        noSmoothingButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        noSmoothingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noSmoothingButtonActionPerformed(evt);
            }
        });
        smoothPanel.add(noSmoothingButton);

        smoothGroup.add(boxcarSmoothingButton);
        boxcarSmoothingButton.setText("Boxcar ");
        boxcarSmoothingButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        boxcarSmoothingButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        boxcarSmoothingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxcarSmoothingButtonActionPerformed(evt);
            }
        });
        smoothPanel.add(boxcarSmoothingButton);

        smoothGroup.add(triangleSmoothingButton);
        triangleSmoothingButton.setText("Triangle");
        triangleSmoothingButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        triangleSmoothingButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        triangleSmoothingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triangleSmoothingButtonActionPerformed(evt);
            }
        });
        smoothPanel.add(triangleSmoothingButton);

        jLabel4.setText("    Filter Width:");
        smoothPanel.add(jLabel4);

        filterWidthField.setColumns(2);
        filterWidthField.setText("5");
        filterWidthField.setAlignmentY(1.0F);
        filterWidthField.setMaximumSize(new java.awt.Dimension(32, 22));
        filterWidthField.setMinimumSize(new java.awt.Dimension(32, 22));
        filterWidthField.setValue(new Integer(5));
        filterWidthField.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                filterWidthFieldPropertyChange(evt);
            }
        });
        smoothPanel.add(filterWidthField);

        jPanel4.add(smoothPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jPanel4, gridBagConstraints);

        buttonPanel.setAlignmentX(1.0F);
        buttonPanel.setAlignmentY(0.0F);
        buttonPanel.setMaximumSize(new java.awt.Dimension(566, 39));
        buttonPanel.setMinimumSize(new java.awt.Dimension(550, 30));
        buttonPanel.setPreferredSize(new java.awt.Dimension(550, 35));
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 0, 5));

        clearButton.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        clearButton.setText("Clear Graph");
        clearButton.setMaximumSize(new java.awt.Dimension(101, 29));
        clearButton.setMinimumSize(new java.awt.Dimension(101, 29));
        clearButton.setPreferredSize(new java.awt.Dimension(101, 29));
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(clearButton);

        calibrateButton.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        calibrateButton.setText("Zero Out");
        calibrateButton.setMinimumSize(new java.awt.Dimension(85, 29));
        calibrateButton.setPreferredSize(new java.awt.Dimension(85, 29));
        calibrateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calibrateButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(calibrateButton);

        sendDataButton.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        sendDataButton.setText("Collect Data");
        sendDataButton.setMinimumSize(new java.awt.Dimension(101, 29));
        sendDataButton.setPreferredSize(new java.awt.Dimension(105, 29));
        sendDataButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendDataButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(sendDataButton);

        pingButton.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        pingButton.setText("Ping Spot");
        pingButton.setPreferredSize(new java.awt.Dimension(87, 29));
        pingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pingButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(pingButton);

        blinkButton.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        blinkButton.setText("Blink LEDs");
        blinkButton.setMinimumSize(new java.awt.Dimension(90, 29));
        blinkButton.setPreferredSize(new java.awt.Dimension(94, 29));
        blinkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blinkButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(blinkButton);

        reconnButton.setFont(new java.awt.Font("Lucida Grande", 0, 12));
        reconnButton.setText("Reconnect");
        reconnButton.setMinimumSize(new java.awt.Dimension(90, 29));
        reconnButton.setPreferredSize(new java.awt.Dimension(93, 29));
        reconnButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reconnButtonActionPerformed(evt);
            }
        });
        buttonPanel.add(reconnButton);

        jLabel6.setText("   ");
        buttonPanel.add(jLabel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(buttonPanel, gridBagConstraints);

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        fileMenu.setText("File");

        aboutMenuItem.setText("About...");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(aboutMenuItem);
        fileMenu.add(jSeparator5);

        openMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        openMenuItem.setText("Open...");
        openMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(openMenuItem);
        fileMenu.add(jSeparator3);

        closeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        closeMenuItem.setText("Close");
        closeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(closeMenuItem);
        fileMenu.add(jSeparator4);

        saveMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        saveMenuItem.setText("Save");
        saveMenuItem.setEnabled(false);
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMenuItem);

        saveAsMenuItem.setText("Save As...");
        saveAsMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveAsMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveAsMenuItem);
        fileMenu.add(jSeparator1);

        pagesetupMenuItem.setText("Page Setup...");
        pagesetupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pagesetupMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(pagesetupMenuItem);

        printMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        printMenuItem.setText("Print...");
        printMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(printMenuItem);
        fileMenu.add(jSeparator2);

        quitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        quitMenuItem.setText("Quit");
        quitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                quitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(quitMenuItem);

        jMenuBar1.add(fileMenu);

        windowMenu.setText("Windows");
        windowMenu.addMenuListener(new javax.swing.event.MenuListener() {
            public void menuSelected(javax.swing.event.MenuEvent evt) {
                windowMenuMenuSelected(evt);
            }
            public void menuDeselected(javax.swing.event.MenuEvent evt) {
            }
            public void menuCanceled(javax.swing.event.MenuEvent evt) {
            }
        });
        jMenuBar1.add(windowMenu);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void gravityCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gravityCheckBoxActionPerformed
        graphView.setIncludeGravity(gravityCheckBox.isSelected());
    }//GEN-LAST:event_gravityCheckBoxActionPerformed

    private void blinkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blinkButtonActionPerformed
        listener.doBlink();
    }//GEN-LAST:event_blinkButtonActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        JOptionPane.showMessageDialog(this,
                "Sun SPOTs Telemetry Demo (Version " + version + ")\n\nA demo showing how to collect data from a SPOT and \nsend it to a desktop application to be displayed.\n\nAuthor: Ron Goldman, Sun Labs\nDate: " + versionDate,
                "About Telemetry Demo",
                JOptionPane.INFORMATION_MESSAGE,
                SpotListener.aboutIcon);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void pagesetupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pagesetupMenuItemActionPerformed
        // Ask user for page format (e.g., portrait/landscape)
        pageFormat = printJob.pageDialog(pageFormat);
    }//GEN-LAST:event_pagesetupMenuItemActionPerformed

    private void reconnButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reconnButtonActionPerformed
        listener.reconnect();
    }//GEN-LAST:event_reconnButtonActionPerformed

    private void windowMenuMenuSelected(javax.swing.event.MenuEvent evt) {//GEN-FIRST:event_windowMenuMenuSelected
        windowMenu.removeAll();
        for (Enumeration e = SpotListener.getWindows().elements() ; e.hasMoreElements() ;) {
            JMenuItem it = windowMenu.add(((JFrame)e.nextElement()).getTitle());
            it.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    windowSelected(evt);
                }
            });
        }
    }//GEN-LAST:event_windowMenuMenuSelected

    private void xZoomButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xZoomButton4ActionPerformed
        graphView.setZoomX(8);
    }//GEN-LAST:event_xZoomButton4ActionPerformed

    private void xZoomButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xZoomButton3ActionPerformed
        graphView.setZoomX(4);
    }//GEN-LAST:event_xZoomButton3ActionPerformed

    private void xZoomButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xZoomButton2ActionPerformed
        graphView.setZoomX(2);
    }//GEN-LAST:event_xZoomButton2ActionPerformed

    private void xZoomButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xZoomButton1ActionPerformed
        graphView.setZoomX(1);
    }//GEN-LAST:event_xZoomButton1ActionPerformed

    private void filterWidthFieldPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_filterWidthFieldPropertyChange
        Integer fieldWidth = (Integer)filterWidthField.getValue();
        int w = fieldWidth.intValue();
        if (w <= 0) {
            w = 2;
        }
        if ((w % 2) == 0) {
            w++;
            filterWidthField.setValue(new Integer(w));
        }
        if (graphView != null) {
            graphView.setFilterWidth(w - 1);
        }
    }//GEN-LAST:event_filterWidthFieldPropertyChange

    private void triangleSmoothingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triangleSmoothingButtonActionPerformed
        graphView.setSmooth(true);
        graphView.setFiltertype(false);
    }//GEN-LAST:event_triangleSmoothingButtonActionPerformed

    private void boxcarSmoothingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxcarSmoothingButtonActionPerformed
        graphView.setSmooth(true);
        graphView.setFiltertype(true);
    }//GEN-LAST:event_boxcarSmoothingButtonActionPerformed

    private void noSmoothingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noSmoothingButtonActionPerformed
        graphView.setSmooth(false);
    }//GEN-LAST:event_noSmoothingButtonActionPerformed

    private void yZoomButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yZoomButton4ActionPerformed
        graphView.setZoomY(8);
    }//GEN-LAST:event_yZoomButton4ActionPerformed

    private void yZoomButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yZoomButton3ActionPerformed
        graphView.setZoomY(4);
    }//GEN-LAST:event_yZoomButton3ActionPerformed

    private void yZoomButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yZoomButton2ActionPerformed
        graphView.setZoomY(2);
    }//GEN-LAST:event_yZoomButton2ActionPerformed

    private void yZoomButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yZoomButton1ActionPerformed
        graphView.setZoomY(1);
    }//GEN-LAST:event_yZoomButton1ActionPerformed

    private void quitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_quitMenuItemActionPerformed
        doQuit();
    }//GEN-LAST:event_quitMenuItemActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        SpotListener.removeFrame(this);
        if (listener != null) {
            listener.doQuit();
        }
    }//GEN-LAST:event_formWindowClosed

    private void printMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printMenuItemActionPerformed
        printJob.setPrintable(this, pageFormat);
        if (printJob.printDialog()) {
            try {
                setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                printJob.print();
            } catch(PrinterException pe) {
                System.out.println("Error printing: " + pe);
            } finally {
                setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_printMenuItemActionPerformed

    private void saveAsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveAsMenuItemActionPerformed
        doSaveAs();
    }//GEN-LAST:event_saveAsMenuItemActionPerformed

    private void saveMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMenuItemActionPerformed
        if (file == null) {
            doSaveAs();
        } else {
            doSave();
        }
    }//GEN-LAST:event_saveMenuItemActionPerformed

    private void closeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeMenuItemActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_closeMenuItemActionPerformed

    private void openMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openMenuItemActionPerformed
        JFileChooser chooser;
        if (file != null) {
            chooser = new JFileChooser(file.getParent());
        } else {
            chooser = new JFileChooser(System.getProperty("user.dir"));
        }
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            GraphView gView = new GraphView();
            if (gView.readTelemetryFile(file)) {
                new TelemetryFrame(file, gView);
            }
        }
    }//GEN-LAST:event_openMenuItemActionPerformed

    private void xCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_xCheckBoxActionPerformed
        graphView.setShowX(xCheckBox.isSelected());
    }//GEN-LAST:event_xCheckBoxActionPerformed

    private void yCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yCheckBoxActionPerformed
        graphView.setShowY(yCheckBox.isSelected());
    }//GEN-LAST:event_yCheckBoxActionPerformed

    private void zCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zCheckBoxActionPerformed
        graphView.setShowZ(zCheckBox.isSelected());
    }//GEN-LAST:event_zCheckBoxActionPerformed

    private void gCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gCheckBoxActionPerformed
        graphView.setShowG(gCheckBox.isSelected());
    }//GEN-LAST:event_gCheckBoxActionPerformed

    private void pingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pingButtonActionPerformed
        listener.doPing();
    }//GEN-LAST:event_pingButtonActionPerformed

    private void sixGRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sixGRadioButtonActionPerformed
        listener.doSetScale(scales[1]);
    }//GEN-LAST:event_sixGRadioButtonActionPerformed

    private void twoGRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoGRadioButtonActionPerformed
        listener.doSetScale(2);
    }//GEN-LAST:event_twoGRadioButtonActionPerformed

    private void calibrateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calibrateButtonActionPerformed
        listener.doCalibrate();
    }//GEN-LAST:event_calibrateButtonActionPerformed

    private void sendDataButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendDataButtonActionPerformed
        sendData = !sendData;
        listener.doSendData(sendData, graphView);
        sendDataButton.setText(sendData ? "Stop Data" : "Collect Data");
        saveMenuItem.setEnabled(true);
        clearedData = false;
    }//GEN-LAST:event_sendDataButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        if (saveMenuItem.isEnabled()) {
            int n = JOptionPane.showConfirmDialog(this, "The current data has not been saved to a file. " + 
                                                  "Do you wish to delete it?",
                                                  "Data Not Saved",
                                                  JOptionPane.YES_NO_OPTION,
                                                  JOptionPane.WARNING_MESSAGE,
                                                  SpotListener.aboutIcon);
            if (n != JOptionPane.YES_OPTION) {
                return;                             // cancel the Clear command
            }
        }

        if (sendData) {                             // if currently sending data, then stop
            listener.doSendData(false, graphView);            
        }
        graphView.clearGraph();
        listener.clear();
        clearedData = true;
        saveMenuItem.setEnabled(sendData);
    }//GEN-LAST:event_clearButtonActionPerformed

    private void eightGRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eightGRadioButtonActionPerformed
        listener.doSetScale(8);
    }//GEN-LAST:event_eightGRadioButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JPanel axisPanel;
    private javax.swing.JButton blinkButton;
    private javax.swing.JRadioButton boxcarSmoothingButton;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton calibrateButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JMenuItem closeMenuItem;
    private javax.swing.JLabel connStatusLabel;
    private javax.swing.JRadioButton eightGRadioButton;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JFormattedTextField filterWidthField;
    private javax.swing.ButtonGroup fullscaleGroup;
    private javax.swing.JCheckBox gCheckBox;
    private javax.swing.JPanel gPanel;
    private javax.swing.JScrollPane graphViewScrollPane;
    private javax.swing.JCheckBox gravityCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel maxGLabel;
    private javax.swing.JRadioButton noSmoothingButton;
    private javax.swing.JMenuItem openMenuItem;
    private javax.swing.JMenuItem pagesetupMenuItem;
    private javax.swing.JButton pingButton;
    private javax.swing.JMenuItem printMenuItem;
    private javax.swing.JMenuItem quitMenuItem;
    private javax.swing.JButton reconnButton;
    private javax.swing.JMenuItem saveAsMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JButton sendDataButton;
    private javax.swing.JRadioButton sixGRadioButton;
    private javax.swing.ButtonGroup smoothGroup;
    private javax.swing.JPanel smoothPanel;
    private javax.swing.JRadioButton triangleSmoothingButton;
    private javax.swing.JRadioButton twoGRadioButton;
    private javax.swing.JMenu windowMenu;
    private javax.swing.JCheckBox xCheckBox;
    private javax.swing.JRadioButton xZoomButton1;
    private javax.swing.JRadioButton xZoomButton2;
    private javax.swing.JRadioButton xZoomButton3;
    private javax.swing.JRadioButton xZoomButton4;
    private javax.swing.ButtonGroup xZoomGroup;
    private javax.swing.JCheckBox yCheckBox;
    private javax.swing.JRadioButton yZoomButton1;
    private javax.swing.JRadioButton yZoomButton2;
    private javax.swing.JRadioButton yZoomButton3;
    private javax.swing.JRadioButton yZoomButton4;
    private javax.swing.ButtonGroup yZoomGroup;
    private javax.swing.JPanel y_axisPanel;
    private javax.swing.JCheckBox zCheckBox;
    // End of variables declaration//GEN-END:variables
    
}
