/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sunspotworld.airstore.inspector;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.records.CannotParseException;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordEntry;

/**
 *
 * @author randy
 */
public class RecordEditor implements ActionListener, KeyListener {

    private AirStoreInspector viewer;
    private Record record;
    private JButton okayButton, cancelButton, addRowButton;
    private JFrame frame;
    private JTable table;

    private String[] typeValues = new String[]{
        "",
        "boolean", "boolean[]",
        "byte", "byte[]",
        "double", "double[]",
        "int", "int[]",
        "long", "long[]",
        "null",
        "String","String[]"
    };

    private int nrows = 3;
    private Object[][] data = new Object[nrows][3];

    public void createAndShowGUI(Record r, AirStoreInspector v) {
        viewer = v;
        record = r;
        //Make the JFrame remove this from list of instances on disposing. 
        frame = new JFrame("Add a record");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] columnNames = {"Key", "type", "Value"};
        for (int i = 0; i < nrows; i++) {
             data[i][0] = "";
             data[i][1] = "";
             data[i][2] = "";
        }
        for (int i = 0; i < record.getEntries().size(); i++) {
            RecordEntry re = (RecordEntry) record.getEntries().elementAt(i);
            data[i][0] = re.getKey();
            data[i][1] = re.getTypeString();
            data[i][2] = re.valueToString();
        }
       // table = new JTable(data, columnNames);

        table = new JTable();
        table.setModel(new DefaultTableModel(data, columnNames));
        // Set the combobox editor on the middle column
        TableColumn col = table.getColumnModel().getColumn(1);
        col.setCellEditor(new ComboBoxTypeEditor(typeValues));

        // If the cell should appear like a combobox in its
        // non-editing state, also set the combobox renderer
        col.setCellRenderer(new ComboBoxRenderer(typeValues));

        Border raisedbevel = BorderFactory.createRaisedBevelBorder();
        Border loweredbevel = BorderFactory.createLoweredBevelBorder();
        Border compound = BorderFactory.createCompoundBorder(raisedbevel, loweredbevel);
        JPanel rvHolder = new JPanel();
        rvHolder.setLayout(new BorderLayout());
        rvHolder.add(table.getTableHeader(), BorderLayout.PAGE_START);
        rvHolder.add(table, BorderLayout.CENTER);
        rvHolder.setBorder(compound);
        rvHolder.setBackground(Color.LIGHT_GRAY);
        table.setBackground(new Color(230, 233, 233));

        table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        rvHolder.add(table);
        table.doLayout();
        panel.add(rvHolder);

        JScrollPane sp = new JScrollPane(panel);
        JPanel outerPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new GridLayout(1,2));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        okayButton = new JButton("okay");
        okayButton.addActionListener(this);
        okayButton.setPreferredSize(new Dimension(150, 35));
        buttonPanel.add(okayButton);
        cancelButton = new JButton("cancel");
        cancelButton.addActionListener(this);
        cancelButton.setPreferredSize(new Dimension(150, 35));
        buttonPanel.add(cancelButton);
        addRowButton = new JButton("+ row");
        addRowButton.addActionListener(this);
        addRowButton.setPreferredSize(new Dimension(150, 35));
        buttonPanel.add(addRowButton);
        buttonPanel.add( new JPanel());
        buttonPanel.add( new JPanel()); 
        buttonPanel.setPreferredSize(new Dimension(500, 35));
        outerPanel.add(buttonPanel, BorderLayout.PAGE_END);
        //sp.setPreferredSize(new Dimension((int) buttonPanel.getPreferredSize().getWidth(), 400));
        //outerPanel.setPreferredSize(new Dimension((int) buttonPanel.getPreferredSize().getWidth(), 500));
        outerPanel.add(sp, BorderLayout.CENTER);
        outerPanel.setOpaque(true); //content panes must be opaque
        //outerPanel.setSize(outerPanel.getWidth(), 500);
        frame.setContentPane(outerPanel);
        //Display the window.
        frame.pack();
        int screenHalfWidth = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2;
        int parentWindowXRight = viewer.getFrame().getX() + viewer.getFrame().getWidth();
        int parentWindowXLeft  = viewer.getFrame().getX();
        int parentWindowXCenter = (parentWindowXRight + parentWindowXLeft) / 2;
        int parentWindowYTop = viewer.getFrame().getY();
        //Open on the side with more space.
        if(parentWindowXCenter > screenHalfWidth){
            frame.setLocation(parentWindowXLeft - frame.getWidth(), parentWindowYTop);
        } else {
            frame.setLocation(parentWindowXRight, parentWindowYTop);
        }
        frame.setVisible(true);
        table.addKeyListener(this);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == okayButton){
            acceptEdits();
        }
        if(e.getSource() == cancelButton){
            frame.dispose();
        }
        if(e.getSource() == addRowButton){ 
            ((DefaultTableModel)table.getModel()).addRow(new String[]{"", "", ""});
        }
    }

    public void acceptEdits(){
        Record newRec = new Record();
            int i = 0;
            table.getCellEditor(i, 0).stopCellEditing(); //Moves the info from screen into data[][].
            table.getCellEditor(i, 2).stopCellEditing();
            String key   = (String) table.getModel().getValueAt(i, 0);
            String type  = (String) table.getModel().getValueAt(i, 1);
            String value = (String) table.getModel().getValueAt(i, 2);
            while(! key.equals("")){
                try {
                    addEntryToRecord(newRec, key, type, value);
                } catch (final CannotParseException ex) {
                    final String v = value;
                    final String t = type;
                    if(t == null || t.equals("")){
                        EventQueue.invokeLater(new Runnable(){ public void run(){
                          viewer.warnUser("Must set a type for the value " + v, frame);
                        }});
                    }
                    EventQueue.invokeLater(new Runnable(){ public void run(){
                        viewer.warnUser("Could not parse " + v + " as type " + t + "\n" + ex, frame);
                    }});
                    return;
                }
                table.getCellEditor(i, 0).stopCellEditing();
                table.getCellEditor(i, 2).stopCellEditing();
                key   = (String) table.getModel().getValueAt(i, 0);
                type  = (String) table.getModel().getValueAt(i, 1);
                value = (String) table.getModel().getValueAt(i, 2);
                i++;
            }
            AirStore.put(newRec);
            frame.dispose();
    }

    /**
     *
     * FIX this routine will map a String (such as "0.5 is a number") to the double value 0.5
     * @param r
     * @param key
     * @param s
     */
    public void addEntryToRecord(Record r, String key, String type, String valueString) throws CannotParseException {
        RecordEntry re = null;
        if(type == null || type.equals("") || key == null || key.equals("") || valueString == null ){
            throw new CannotParseException();
        }
        try {
            re = (RecordEntry) getClassForType(type).newInstance();
        } catch (InstantiationException ex) {
            Logger.getLogger(RecordEditor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(RecordEditor.class.getName()).log(Level.SEVERE, null, ex);
        }
        re.setKey(key);
        re.initializeValueFromString(valueString);
        r.addEntry(re); 
    }

    public Class getClassForType(String t){
        String nm = "org.sunspotworld.airstore.records.";
        if(t.equals("boolean"))   nm = nm + "RecordEntryBoolean";
        if(t.equals("boolean[]")) nm = nm + "RecordEntryBooleanArray";
        if(t.equals("byte"))      nm = nm + "RecordEntryByte";
        if(t.equals("byte[]"))    nm = nm + "RecordEntryByteArray";
        if(t.equals("double"))    nm = nm + "RecordEntryDouble";
        if(t.equals("double[]"))  nm = nm + "RecordEntryDoubleArray";
        if(t.equals("int"))       nm = nm + "RecordEntryInt";
        if(t.equals("int[]"))     nm = nm + "RecordEntryIntArray";
        if(t.equals("long"))      nm = nm + "RecordEntryLong";
        if(t.equals("long[]"))    nm = nm + "RecordEntryLongArray";
        if(t.equals("null"))      nm = nm + "RecordEntryNull";
        if(t.equals("String"))    nm = nm + "RecordEntryString";
        if(t.equals("String[]"))  nm = nm + "RecordEntryStringArray";
        try {
            return Class.forName(nm);
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(RecordEditor.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("[recordEditor] > getClassForType() > User failed to set type.");
            return null;
        }
    }

    public void keyTyped(KeyEvent e) {
        if(e.getKeyChar() == e.VK_ENTER) {
            acceptEdits();
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) { 
    }
}
