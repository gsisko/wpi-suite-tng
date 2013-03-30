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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;


/** Interface to go over builder and list panels that work together
 * and have buttons/controllers that include the following.
 * Delete
 * New Model/Cancel
 * Save
 * (upload functionality from a list view)
 * 
 */
public interface IListBuilder {
	
	/** Takes whatever model(s) is(are) stored in the the current panel,
	 *  and returns the unique identifier(s) in an array. Generally
	 *  pulls the highlighted identifiers from a table view.
	 * 
	 * @return An array of unique identifiers in the form of strings
	 */
	public String[] getUniqueIdentifiers();

	
	
	/** Sets clears and resets any fields in the current builder panel
	 *  and also resets the mode to "CREATE" if applicable. If called 
	 *  on a list view, sets the button to clear/cancel
	 */
	public void clearAndReset();


	/** Sets the "Cancel" button back to "New <Model>" 
	 */
	public void setCancelBtnToNew();


	/** Begins refresh process, allows the panels to start triggering
	 *  their own controllers and chains of controllers
	 * 
	 * @return true on success, false on failure
	 */
	public boolean refreshAll();



	/** Gets the model from the view/panel in the form of a JSON string
	 *  that is ready to be sent as a message over the network
	 * 
	 * *NOTE: can be used for passing messages between views!
	 * 
	 * @return JSON string of the model to be sent
	 */
	public String getModelMessage();

	
	/** Gets the model and passes it around 
	 *  When given to another view, must be cast appropriately (might not work)
	 */
//	public Model getModel();


	/** Toggles between "New Model" and "Cancel" mode */
	public void toggleNewCancalMode();



	/** Gets the unique identifier of the list entry that was double clicked
	 * 
	 * @return The unique identifier, either name or ID number
	 */
	public String getSelectedUniqueIdentifier();


	/** Takes a JSON string that holds an array of models and uploads them
	 *  to the builder panel. Also sets the 
	 *  
	 * @param jsonArray An array of models in JSON string form
	 */
	public void translateAndDisplayModel(String jsonArray);

 
	/** Show the models in the list view
	 *  Do nothing in builder
	 * 
	 * @param jsonString An array of models in the form of a JSON string
	 */
	public void showRecievedModels(String jsonString);

}
