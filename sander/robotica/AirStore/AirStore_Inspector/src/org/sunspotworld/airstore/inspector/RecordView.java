/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.inspector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import org.sunspotworld.airstore.AirStore;
import org.sunspotworld.airstore.records.Record;
import org.sunspotworld.airstore.records.RecordEntry;


/**
 *
 * @author randy
 */
public class RecordView extends JTable implements MouseListener, ActionListener {

    private Record record;
    private JPopupMenu popup;
    private AirStoreInspector inspector;

    public RecordView(TableModel tm){
        super(tm);
    }

    public static RecordView newRecordView(final Record r){
        //Set up all the pointers and back pointers in relationship between
        //the Record, RecordView, and RecordViewTableModel
        RecordViewTableModel dataModel = new RecordViewTableModel();
        RecordView self = new RecordView(dataModel); 
        self.setRecord(r);
        dataModel.setRecordView(self);

        ((AbstractTableModel)self.getModel()).fireTableStructureChanged();

        self.setPreferredScrollableViewportSize(new Dimension(200, 100));
        self.addMouseListener(self);
        self.createPopupMenu();
        return self;
    }

    public static RecordView newTestRecordView(final Record r){
        RecordView self = new RecordView();
        self.setRecord(r);
        DefaultTableModel model = (DefaultTableModel)self.getModel();

        // Add some columns
        model.addColumn("A", new Object[]{"item1"});
        model.addColumn("B", new Object[]{"item2"});

        // These are the combobox values
        String[] values = new String[]{"item1", "item2", "item3"};

        // Set the combobox editor on the middle column
        TableColumn col = self.getColumnModel().getColumn(1);
        col.setCellEditor(new ComboBoxTypeEditor(values));

        // If the cell should appear like a combobox in its
        // non-editing state, also set the combobox renderer
        col.setCellRenderer(new ComboBoxRenderer(values));
        return self;
    }

    private RecordView() {
         super();
    }

    public void paintComponent(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(0,0, getWidth(), getHeight());
        super.paintComponent(g);
    }

    /**
     * @return the record
     */
    public Record getRecord() {
        return record;
    }

    /**
     *
     * @return the instance of AirStoreInspector in which this recordView is nestled
     */
    public AirStoreInspector getAirStoreInspector(){
        return inspector;
    }

    public void setAirStoreInspector(AirStoreInspector v){
        inspector = v;
    }

    /**
     * @param record the record to set
     */
    public void setRecord(Record record) {
        this.record = record;
    }

    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() ==2){
            doEditRecord();
        }
    }

    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }

    public void mouseEntered(MouseEvent e) {
       
    }

    public void mouseExited(MouseEvent e) {
       
    }

    /**
     * @return the popup
     */
    public JPopupMenu getPopup() {
        return popup;
    }

    /**
     * @param popup the popup to set
     */
    public void setPopup(JPopupMenu popup) {
        this.popup = popup;
    }

    public void createPopupMenu() {
        popup = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Edit and add a copy...");
        menuItem.addActionListener(this);
        popup.add(menuItem);
        menuItem = new JMenuItem("Delete this record");
        menuItem.addActionListener(this);
        popup.add(menuItem);
    }

    public void actionPerformed(ActionEvent e) {
        if(((JMenuItem) e.getSource()).getText().equals("Edit and add a copy...")){
            doEditRecord();
        }
        if(((JMenuItem) e.getSource()).getText().equals("Delete this record")){
            doDeleteRecord();
        }
    }

    public void doEditRecord(){
        (new RecordEditor()).createAndShowGUI(record, inspector);
    }

    public void doDeleteRecord(){
        AirStore.takeAllMatches(record.asRecordTemplate());
    }

    public String toString(){
        return "a RecordView of" + record;
    }
}
class RecordViewTableModel extends DefaultTableModel {
            private RecordView recordView;
            public void setRecordView(RecordView rv){ recordView = rv;}
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public String getColumnName(int col) {
                String result = "";
                switch (col) {
                    case 0:    result = "Key";   break;
                    case 1:    result = "type";  break;
                    case 2:    result = "Value"; break;
                }
                return result;
            }
            public int getColumnCount() { return 3; }
            public synchronized int getRowCount() {
                if(recordView == null) return 0;
                if(recordView.getRecord() == null) return 0;
                return recordView.getRecord().getEntries().size();
            }
            public synchronized Object getValueAt(int row, int col) {
                Object result = null;
                RecordEntry recordEntry = (RecordEntry)recordView.getRecord().getEntries().elementAt(row);
                switch (col) {
                    case 0:    result = recordEntry.getKey();  break;
                    case 1:    result = recordEntry.getTypeString(); break;
                    case 2:    result = recordEntry.valueToString(); break; // was getValue();
                }
                return result;
            }
}
