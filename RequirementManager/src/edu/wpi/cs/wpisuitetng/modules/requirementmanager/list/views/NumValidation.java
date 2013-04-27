/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team 5 D13
 * 
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.Component;

import javax.swing.AbstractCellEditor;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
/**
 * Set a filter to filter out unwanted data types.
 *
 */
@SuppressWarnings("serial")
public class NumValidation extends AbstractCellEditor implements TableCellEditor {

JComponent component = new JTextField();

public Component getTableCellEditorComponent(JTable table, Object value,
boolean isSelected, int row, int col) {

DocumentFilter dfilter = new DataFilter();
	
((AbstractDocument) ((JTextField) component).getDocument()).setDocumentFilter(dfilter);

((JTextField) component).setText((String) value);

return component;
}

/**
 * Get the cellvalue at the cell
 */
public Object getCellEditorValue() {
return ((JTextField) component).getText();
}
}