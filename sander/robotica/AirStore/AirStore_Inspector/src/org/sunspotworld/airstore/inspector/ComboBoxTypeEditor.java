/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sunspotworld.airstore.inspector;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 *
 * @author randy
 */
public class ComboBoxTypeEditor extends DefaultCellEditor {

    public ComboBoxTypeEditor(String[] items) { 
        super(new JComboBox(items));
        ((JComboBox)this.getComponent()).setMaximumRowCount(20);
    }
}
