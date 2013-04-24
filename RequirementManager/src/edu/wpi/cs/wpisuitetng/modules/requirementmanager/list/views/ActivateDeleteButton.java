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

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/** An action listener to watch a JTable for highlighted entries. When none are
 *  highlighted, the Delete button in the associated IListPanel is deactivated.
 *  If one or more entries are highlighted, the Delete button is activated. */
public class ActivateDeleteButton extends MouseAdapter{
	/**  The list view that this controller is watching */
	private final IListPanel listView;
	
	
	/** Constructor that takes the list panel that holds the Delete
	 *  button that is to be activated
	 * 
	 * @param listView The IListPanel that holds the table and button to watch
	 */
	public ActivateDeleteButton(IListPanel listView) {
		this.listView = listView;
	}
	
	/** When a mouse released action is done on the table being watched, this 
	 *  will be activated and set the Delete button to activated/deactivated 
	 *  appropriately.
	 */
	public void mouseReleased(MouseEvent e){
		listView.setDeleteEnabled(checkForHighlights());
	}
	
	/** When a mouse move action is done on the table being watched, this 
	 *  will be activated and set the Delete button to activated/deactivated 
	 *  appropriately. This is to cover all the bases of mouse movement.
	 */
	public void mouseMoved(MouseEvent e){		
		listView.setDeleteEnabled(checkForHighlights());
	}
	
	/** When a mouse enter action is done on the table being watched, this 
	 *  will be activated and set the Delete button to activated/deactivated 
	 *  appropriately. This is to cover all the bases of mouse movement.
	 */
	public void mouseEntered(MouseEvent e){		
		listView.setDeleteEnabled(checkForHighlights());
	}
	
	/** When a mouse exit action is done on the table being watched, this 
	 *  will be activated and set the Delete button to activated/deactivated 
	 *  appropriately. This is to cover all the bases of mouse movement.
	 */
	public void mouseExit(MouseEvent e){		
		listView.setDeleteEnabled(checkForHighlights());
	}
	
	/** Checks for the existence of at least one highlighted entry in the list. Returns
	 * true when there is 1 or more, false otherwise.
	 * 
	 * @return True if an entry is highlighted, false otherwise
	 */
	private boolean checkForHighlights(){
		String[] uniqueIdentifiers = listView.getSelectedUniqueIdentifiers();
		// If the mouse click/drag resulted in at least one entry being highlighted, return true
		if (uniqueIdentifiers.length >0 ){
			// Now check that the highlighted items can be deleted
			return listView.areSelectedItemsDeletable();
		}		
		// Else
		return false;		
	}
}
