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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers;

/** This interface should be implemented by list panels that 
 *  will contain editable list fields. The methods included
 *  are all used by the ListSaveModelController    */
public interface IEditableListPanel {

	/** Gets the array of boolean flags of what models
	 *  need saving
	 * 
	 * @return a Boolean array of what models need saving
	 */
	Boolean[] getNeedsSaveFlags();
	
	/** Sets up any arrays of flags or other settings needed
	 *  before editing can start 
	 */
	public void setUpForEditing();

	
	/** Gets the JSOn version of the model at 
	 *  the given index
	 *  
	 * @param i The index of the model
	 * @return  The JSON version of the model
	 */
	String getModelAsJson(int i);

	/** Gets the unique identifier of the model at 
	 *  the given index
	 * 
	 * @param i The index of the model
	 * @return  The unique identifier of the model
	 */
	String getUniqueIdAtIndex(int i);

	
	/** Way to trigger a pop-up or enable/disable certain 
	 *  buttons when a  save is not successful.
	 */
	void failedToSave();

	/** Change settings of table to indicate that the 
	 *  save was completed and normal operations 
	 *  should resume.
	 */
	void savesComplete();

	/** Trigger a reset of all lists	 */
	void refreshAll();

}
