/*
 * AirStoreInspector.java
 *
 * Created on Mar 4, 2009 4:21:44 PM;
 */

package org.sunspotworld.airstore.inspector;

import com.sun.spot.peripheral.ota.OTACommandServer;
import com.sun.spot.peripheral.radio.RadioFactory;
import com.sun.spot.util.IEEEAddress;
import com.sun.spot.util.Utils;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.IAirStoreListener;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordTemplate;
import org.sunspotworld.airstore.service.AirStoreService;

/**
 * A tool for viewing AirStore: see all the records, remove or add new ones.
 * For future deelopment work: one way to nail down the type in the value field
 * would be to have a third column for value type. User could select from a combo box.
 * For a way to put a combo box in a column, see http://www.exampledepot.com/egs/javax.swing.table/ComboBox.html
 * This could be useful for setting the type of each RecordEntry.
 */
public class AirStoreInspector implements ActionListener, IAirStoreListener, WindowListener {

    private String title = "AirStore Inspector";
    private JPanel mainPanel;
    private JFrame frame;
    private JButton addButton, refreshButton;

    static boolean isGUIReady = false;

    public static AirStoreInspector openNew(){

        final AirStoreInspector asv = new AirStoreInspector();

        //This call, as it is the first ref to AirStoer, should hang unitl it is up to date.
//        AirStore.addListener(asv, new RecordTemplate()); //Interested in everything that will be added...

        //asv.startAirStoreTestThreads();

        isGUIReady = false; //After the GUI is ready this is set true

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                asv.createAndShowGUI();
                isGUIReady = true;
            }
        });

        //Busy wait until the GUI is showing, then update display.
        (new Thread(){ public void run(){
            while(! isGUIReady){ Utils.sleep(200);}
            AirStore.addListener(asv, new RecordTemplate()); //Interested in everything that will be added...
            asv.update();
            //asv.startAirStoreTestThreads();
        }}).start();

        return asv;
    }
     
     public void startAirStoreTestThreads(){
         (new Thread(){ public void run(){
            print("TEST THREAD 0 STARTING  IN 2 Seconds");
            Utils.sleep(2000);
            byte[] bytes = new byte[]{ 43 , 51, 79};
            long value1 = RadioFactory.getRadioPolicyManager().getIEEEAddress();
            String address = IEEEAddress.toDottedHex(value1);
            AirStore.put(address, bytes);
            print("TEST THREAD sleeping 2 Seconds");
            Utils.sleep(2000);
            bytes = new byte[]{ 0 , 1, 2};
            AirStore.put(address, bytes);
            print("TEST THREAD sleeping 2 Seconds");
            Utils.sleep(2000);
            AirStore.takeAll(address);
            print("TEST THREAD 0 EXITING");
        }}).start();
        (new Thread(){ public void run(){
            print("TEST THREAD 1 STARTING");
            Utils.sleep(4000);
            AirStore.put("fleetingRecord", "should last ~ 3.0 seconds, to be taken by another thread");
            Utils.sleep(1400);
            String[] sa = new String[]{"one", "two", "three"};
            AirStore.put("permRecSA", sa);
            int[]ia = new int[]{1, 2, 3};
            AirStore.put("permRecIA", ia);
            boolean[]ba = new boolean[]{true, false, true};
            AirStore.put("permRecBA", ba);
            print("TEST THREAD 1 EXITING");
        }}).start();
        (new Thread(){ public void run(){
            print("TEST THREAD 2 STARTING");
            Utils.sleep(7000);
            AirStore.takeAll("fleetingRecord");
            Utils.sleep(999);
            print("Taking tempRecord");
            Vector v = AirStore.takeAll("tempRecord");
            print("[test thread 2] takeAll(\"tempRecord\") gives "+v);
            print("TEST THREAD 2 EXITING");
        }}).start();
        (new Thread(){ public void run(){
            print("TEST THREAD 3 STARTING");
            Utils.sleep(4000);
            AirStore.put("permRecord1", "arrived 4 seconds in");
            Utils.sleep(500);
            AirStore.put("permRecord2", "4.5 seconds in");
            Utils.sleep(500);
            print(" Putting tempRecord");
            AirStore.put("tempRecord", "1.5 in lasting 0.5");
            print("TEST THREAD 3 EXITING");
        }}).start();
    }

     /**
      * This is simply the list of records currently being viewed. Not necessarily
      * in sync with AirStore, though that is of course the goal.
      *
      * @return a vecotr of records currently in this inspector
      */
    public synchronized Vector<Record> getViewedRecords(){
        Vector rvs = getRecordViews();
        Vector<Record> reply = new Vector<Record>();
        for (int i = 0; i < rvs.size(); i++) {
            RecordView rv = (RecordView) rvs.elementAt(i);
            reply.addElement(rv.getRecord());
        }
        return reply;
    }

    public synchronized void update() {
        Vector airStoreRecs = AirStore.getAllMatches(new RecordTemplate()); //Everything in there
        Vector<Record> viewedRecs = getViewedRecords();
        for (int i = 0; i < airStoreRecs.size(); i++) {
            Record airStoreRec = (Record) airStoreRecs.elementAt(i);
            boolean matchForASRec = false;
            for (int j = 0; j < viewedRecs.size(); j++) {
                Record viewedRec = viewedRecs.elementAt(j);
                matchForASRec = matchForASRec | airStoreRec.equals(viewedRec);
            }
            if (!matchForASRec) {
               // print("AirStore had record not in Inspector: " + airStoreRec + ", adding it.");
                 addRecord(airStoreRec, 0);
            }
        }
        viewedRecs = getViewedRecords();
        for (int i = 0; i < viewedRecs.size(); i++) {
            Record viewedRec = viewedRecs.elementAt(i);
            boolean matchForViewRec = false;
            for (int j = 0; j < airStoreRecs.size(); j++) {
                Record airStoreRec = (Record) airStoreRecs.elementAt(j);
                matchForViewRec = matchForViewRec | airStoreRec.equals(viewedRec);
            }
            if (!matchForViewRec) {
            //    print("Inspector had record not in AirStore: " + viewedRec + ", removing it.");
                removeRecord(viewedRec);
            }
        }
    }

    void warnUser(String string, Component c) {
        JOptionPane.showMessageDialog(c, string, "Yikes!", JOptionPane.ERROR_MESSAGE);
    }

    private void createAndShowGUI() {
        //Make the JFrame remove this from list of instances on disposing.
        OTACommandServer.start("AirStoreInspector"); //It can show up in Solarium this way.

        frame = new JFrame(title);
        mainPanel = new JPanel(new GridLayout(0,1));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JScrollPane sp = new JScrollPane(mainPanel);
        JPanel outerPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        addButton = new JButton("+");
        addButton.addActionListener(this);
        buttonPanel.add(addButton, BorderLayout.EAST);
        refreshButton = new JButton("Print AirStore");
        refreshButton.addActionListener(this);
        buttonPanel.add(refreshButton, BorderLayout.WEST);
        //JPanel labelPanel = new JPanel(new GridLayout(1,0));
        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("AirStore");
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        labelPanel.add(label);
        label = new JLabel("Records");
        label.setFont(new Font("Helvetica", Font.BOLD, 16));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setVerticalAlignment(SwingConstants.BOTTOM);
        labelPanel.add(label);
        buttonPanel.add(labelPanel, BorderLayout.CENTER);
        buttonPanel.setPreferredSize(new Dimension(400, 65));
        outerPanel.add(buttonPanel, BorderLayout.PAGE_START);
        outerPanel.add(sp,BorderLayout.CENTER);
        outerPanel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(outerPanel);

        //Display the window.
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.addWindowListener(this);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int xScr = (screenSize.width - frame.getWidth()) / 2;
        int yScr = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(xScr, yScr);
        frame.setVisible(true);
    }

    /**
     * Add this record to our VIEW, (not to AirStore itself).
     * @param r the Record to add.
     * @param positionHint Make this negative to put record at end, make it zero for beginning.
     */
    public synchronized void addRecord(Record r, int positionHint) {
        RecordView rv = RecordView.newRecordView(r);
        rv.setAirStoreInspector(this);
        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        //Border b =  BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        JPanel rvHolder = new JPanel(); //new GridLayout(0,1));
        rvHolder.setLayout(new BorderLayout());
        rvHolder.add(rv.getTableHeader(), BorderLayout.PAGE_START);
        rvHolder.add(rv, BorderLayout.CENTER);
        rvHolder.setBorder(compound);
        rvHolder.setBackground(Color.LIGHT_GRAY);
        rv.setBackground(new Color(230, 233, 233));

        rv.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        rvHolder.add(rv);
        rv.doLayout();
        if(positionHint < 0) mainPanel.add(rvHolder);
        else mainPanel.add(rvHolder, 0);
        frame.pack();
    }

    /**
     * Remove this record from our VIEW (not from AirStore itself.)
     * @param r
     */
    public synchronized void removeRecord(final Record r) {
        final RecordView rv = getRecordViewFor(r);
        /**
         * The invokeLater() is to help give all records a chance to
         * get a UI created for them, so we can then safely delete them
         * from the GUI. (Otherwise, problems when first starting as puts()
         * can be happening (they can generate removes) before any GUI yet exisits.)
         * Even with this maneuver, it sometimes doesn't work, hence the null test.
         */
        EventQueue.invokeLater(new Runnable() { public void run() {  
                if (rv != null) {
                    Container p = rv.getParent();
                    Container gP = p.getParent();
                    gP.remove(p);
                    getFrame().pack(); //Redoes layout and display.
        }}});
    }

    public Insets getInsets(){
        return new Insets(5,5,5,5);
    }

    public static void main(String[] argv) {
           AirStoreInspector asi = AirStoreInspector.openNew();
    }

    /**
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            (new RecordEditor()).createAndShowGUI(new Record(), this);
        }
        if (e.getSource() == refreshButton) {
            update();
            Vector actualRecs = AirStore.getAllMatches(new RecordTemplate()); //Everything in there
            System.out.println("________________ AirStore ________________");
            System.out.println("|");
            for (int i = 0; i < actualRecs.size(); i++) {
                System.out.println("| " + actualRecs.elementAt(i));
            }
            System.out.println("|");
            System.out.println("|__________________________________________");
        }
    }

    /********* UTILITIES *********/
    /**
     * Find the first (and what therefore should be the only) record view whose
     * record is equal() to r.
     * @param r
     * @return
     */
    public RecordView getRecordViewFor(Record r) {
        Vector rvs = getRecordViews();
        for (int i = 0; i < rvs.size(); i++) {
            RecordView rv = (RecordView) rvs.elementAt(i);
            if (rv.getRecord().equals(r)) {
                return rv;
            }
        }
        return null;
    }

    public Vector getRecordViews(){
        Vector v = new Vector();
        Vector children = getAllChildren(frame);
        for (int i = 0; i < children.size(); i++) {
             if(children.elementAt(i) instanceof RecordView){
                 v.add(children.elementAt(i));
             }
        }
        return v;
    }

    /**
     * Utility to transitively get ALL the children of the given Container
     * @param c The container from whomn to get ALL the children.
     * @return
     */
    public Vector getAllChildren(Container c){
        Vector v = new Vector();
        if(c == null) return v;
        Component[] comps = c.getComponents();
        for (int i = 0; i < comps.length; i++) {
            Component component = comps[i];
            v.add(component);
            if(component instanceof Container){
               v.addAll(getAllChildren((Container)component));
            }
        }
        return v;
    }

    public void windowOpened(WindowEvent e) {
    }

    public void windowClosing(WindowEvent e) {
    }

    public void windowClosed(WindowEvent e) {
          AirStore.removeListener(this, new RecordTemplate());
          AirStoreService.getInstance().shutdown();
          System.exit(0);
    }

    public void windowIconified(WindowEvent e) {
    }

    public void windowDeiconified(WindowEvent e) {
    }

    public void windowActivated(WindowEvent e) {
    }

    public void windowDeactivated(WindowEvent e) {
    }

    public void notifyPut(Record r) {
        addRecord(r, 0);
    }

    public void notifyTake(Record r) {
        removeRecord(r);
    }

    public void notifyReplace(Record out, Record in) {
        RecordView rv = getRecordViewFor(out);
        if (rv != null) {
            rv.setRecord(in);
            rv.repaint();
        } else {
            notifyPut(in);
        }
    }

    public String toString(){
        return "an AirStoreInspector";
    }

    public void print(String message){
        StackTraceElement stackFrame = Thread.currentThread().getStackTrace()[3];  //invoking method's frame
        System.out.println("[AirStoreInspector > " + stackFrame.getMethodName() + "] " + message);
    }
}
