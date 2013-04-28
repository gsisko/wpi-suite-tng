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

/**
 * Custom cell renderer for JTables. Displays changed as yellow, unchanged as white, and invalid as red
 */
@SuppressWarnings("serial")
public class ResultsTableCellRenderer extends DefaultTableCellRenderer {
	
	Boolean[][] needsSaving;
	Boolean[][] isValid;
	Boolean[][] isEditable;
	
	public ResultsTableCellRenderer(Boolean[][] needsSaving, Boolean[][] isValid, Boolean[][] isEditable) {
		this.needsSaving = needsSaving;
		this.isValid = isValid;
		this.isEditable = isEditable;
	}
	
	public Component getTableCellRendererComponent(JTable table,Object value, boolean isSelected, boolean hasFocus, int row, int column) {  

		Component cell= super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (needsSaving == null || isValid == null || isEditable == null) {
			if (isSelected) {
				Color defaultBlue = new Color(200,221,242);
				setBackground(defaultBlue);                
			}
			else
			{
				setBackground(Color.white);
			}
			return cell;
		}
		
		if (!isEditable[row][column]) {
			cell.setBackground(Color.lightGray);
		}
		else if (!isValid[row][column]) {
			Color defaultRed = new Color(255,50,50);
			cell.setBackground(defaultRed);
		}
		else if (needsSaving[row][column]) {
			Color defaultYellow = new Color(248,253,188);
			cell.setBackground(defaultYellow);                
		}        
		else {
			cell.setBackground(Color.white);
		}

		return cell;  

	}
}


