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

/** Custom cell renderer for the Filter List Table. This makes the selected filter blue,
 * inactive filters grey, and active filters white
 */
@SuppressWarnings("serial")
public class ActiveFilterTableCellRenderer extends DefaultTableCellRenderer {
	
	/*
	 * (non-Javadoc)
	 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
	public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column) {  

		/*
		 * instantiates the cell
		 */
		Component cell= super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);  

		/*
		 * from the model, this gets the value of the 4th column (status)
		 * because the colors depend on status
		 */
		TableModel model = table.getModel();
		int modelRow = table.getRowSorter().convertRowIndexToModel(row);
		String status = (String)model.getValueAt(modelRow, 4);

		
		/*
		 * if the row for the status is selected, make it the default blue color
		 * if that status of the filter is active, indicated by "yes", make the row white
		 * otherwise, make the row grey
		 */
		if (isSelected) {
			Color defaultBlue = new Color(184,207,229);
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


