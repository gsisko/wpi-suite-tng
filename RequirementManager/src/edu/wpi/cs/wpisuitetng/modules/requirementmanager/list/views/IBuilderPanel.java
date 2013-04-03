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


/** Interface to go over builder panels that work together
 * and have buttons/controllers that include the following.
 * Save
 * (upload functionality from a list view)
 * 
 */
public interface IBuilderPanel {
	// enum to say whether or not you are creating
	public enum Mode {
		CREATE,
		EDIT
	}
	
	/** Restore all fields to their initial values */
	public void resetFields();

	/** Sets the mode of the builder panel to the given mode. ALSO changes
	 *  the text in the button 
	 *  Mode.CREATE or Mode.EDIT
	 * 
	 * @param mode The mode that the builder panel should be in
	 */
	public void setModeAndBtn(Mode mode);

	/** Return the current "mode" of the panel. Can be either
	 *  Mode.EDIT or Mode.Create
	 * 
	 *  @return The current mode
	 */
	public Mode getCurrentMode();

	/** Enables or disables all fields in the builder panel.
	 * 
	 * @param setTo True activates the fields and false deactivates them
	 */
	public void setInputEnabled(boolean setTo);


	/** Gets the model from the panel in the form of a JSON string
	 *  that is ready to be sent as a message over the network
	 * 
	 * *NOTE: can be used for passing messages between views!
	 * 
	 * @return JSON string of the model to be sent, Returns null on failure
	 */
	public String convertCurrentModelToJSON();


	/** Takes a JSON string that holds an array of models and uploads them
	 *  to the builder panel.
	 *  
	 * @param jsonArray An array of models in JSON string form
	 */
	public void displayModelFromJSONArray(String jsonArray);


	/** Toggles between active and inactive modes mode */
	public void toggleNewCancelMode();


	/** Sets up the controllers and action listeners. This should be where all
	 *  controllers and action listeners are initialized because the controllers
	 *  require references that are not not fully initialized when the 
	 *  constructor for this class is called.
     */
	public void setupControllersAndListeners();
 



}