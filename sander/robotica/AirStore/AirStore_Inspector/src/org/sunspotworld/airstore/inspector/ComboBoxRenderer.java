/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.inspector;

import java.awt.Component;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author randy
 */
public class ComboBoxRenderer extends JComboBox implements TableCellRenderer {

    public ComboBoxRenderer(String[] items) {
        super(items);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
//        if (isSelected) {
//            setForeground(table.getSelectionForeground());
//            super.setBackground(table.getSelectionBackground());
//        } else {
//            setForeground(table.getForeground());
//            setBackground(table.getBackground());
//        }

        // Select the current value
//        String typeString = (String) table.getModel().getValueAt(row, column);
//        System.out.println("[ComboBoxRenderer > getTableCellRendererComponent ] " + typeString);

        setSelectedItem(value);
        return this;
    }
}
