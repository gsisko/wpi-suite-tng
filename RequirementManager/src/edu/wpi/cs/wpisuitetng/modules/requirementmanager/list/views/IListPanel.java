/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Robert Dabrowski
 *		Danielle LaRose
 *		Edison Jimenez
 *		Christian Gonzalez
 *		Mike Calder
 *		John Bosworth
 *		Paula Rudy
 *		Gabe Isko
 *		Bangyan Zhang
 *		Cassie Hudson
 *		Robert Smieja
 *		Alex Solomon
 *		Brian Hetherman
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.event.MouseEvent;


/** Interface to go over list panels that work together
 * and have buttons/controllers that include the following.
 * Delete
 * New Model/Cancel
 */
public interface IListPanel {
	/** Takes whatever model(s) is(are) stored in the the current panel,
	 *  and returns the unique identifier(s) in an array. Generally
	 *  pulls the highlighted identifiers from a table view.
	 * 
	 * @return An array of unique identifiers in the form of strings
	 */
	public String[] getSelectedUniqueIdentifiers();


	/** Sets the New the button to clear/cancel	 */
	public void setNewBtnToCancel();


	/** Sets the "Cancel" button back to "New <Model>" 	 */
	public void setCancelBtnToNew();


	/** Begins refresh process, allows the panels to start triggering
	 *  their own controllers and chains of controllers
	 * 
	 * @return true on success, false on failure
	 */
	public boolean refreshAll();

	
	/** Toggles between "New Model" and "Cancel" mode */
	public void toggleNewCancelMode();

	
	/** Gets the unique identifier of the list entry that was double clicked
	 * 
	 * @param me The mouse event that was triggered by a double click
	 * @return The unique identifier, either name or ID number
	 */
	public String getSelectedUniqueIdentifier(MouseEvent me);

	
	/** Show the models in the list view
	 * 
	 * @param jsonString An array of models in the form of a JSON string
	 */
	public void showRecievedModels(String jsonString);

	/** Refresh all the requirements    */
	public void refreshRequirements();
	
	/** Sets the delete button to either activated or deactivated 
	 * 
	 * @param setActive True to activate and false to deactivate
	 */
	public void setDeleteEnabled(boolean setActive);
	
	/** Checks if the selected items can ALL be deleted or not
	 * @return false if any item selected cannot be deleted.
	 */
	public boolean areSelectedItemsDeletable();
}
