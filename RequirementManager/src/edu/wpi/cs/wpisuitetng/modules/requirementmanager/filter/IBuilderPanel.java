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


/** Interface to go over builder panels that work together
 * and have buttons/controllers that include the following.
 * Save
 * (upload functionality from a list view)
 * 
 */
public interface IBuilderPanel {

	/** Sets clears and resets any fields in the current builder panel
	 *  and also resets the mode to "CREATE" if applicable. 
	 */
	public void clearAndReset();

	
	/** Enables or disables all fields in the builder panel. Not intended for
	 *  use by controllers trying to load in data to the  builder panel
	 * 
	 * @param setTo True activates the fields and false deactivates them
	 */
	public void setInputEnabled(boolean setTo);


	/** Gets the model from the panel in the form of a JSON string
	 *  that is ready to be sent as a message over the network
	 * 
	 * *NOTE: can be used for passing messages between views!
	 * 
	 * @return JSON string of the model to be sent
	 */
	public String getModelMessage();


	/** Takes a JSON string that holds an array of models and uploads them
	 *  to the builder panel. Also sets the the modes
	 *  
	 * @param jsonArray An array of models in JSON string form
	 */
	public void translateAndDisplayModel(String jsonArray);


	/** Toggles between active and inactive modes mode */
	public void toggleNewCancelMode();

 
	
	
	
}
