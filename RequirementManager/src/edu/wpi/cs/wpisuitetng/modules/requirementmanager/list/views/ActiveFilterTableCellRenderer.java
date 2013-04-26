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

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

/**
 * Custom cell renderer for JTables. Displays inactive as grey and active as white
 */
@SuppressWarnings("serial")
public class ActiveFilterTableCellRenderer extends DefaultTableCellRenderer {
	
	public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column) {  

		Component cell= super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  

		TableModel model = table.getModel();
		int modelRow = table.getRowSorter().convertRowIndexToModel(row);
		String status = (String)model.getValueAt(modelRow, 4);

		if (isSelected) {
			Color defaultBlue = new Color(200,221,242);
			setBackground(defaultBlue);                
		}        
		else if(status.equals("yes")) {
			setBackground(Color.white);
		}
		else {        
			Color defaultGrey = new Color(238,238,238);
			setBackground(defaultGrey);         
		}     

		return cell;  

	}
}


