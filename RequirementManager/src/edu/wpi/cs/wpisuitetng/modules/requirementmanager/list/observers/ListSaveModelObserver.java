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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.ListSaveModelController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/** This observer handles responses from the server after they are requested
 *  by the ListSaveModelController. The observer keeps track of successful 
 *  responses and triggers a mass refresh when all server requests have been
 *  completed successfully.           
 */
public class ListSaveModelObserver implements RequestObserver,IObserver {
	
	/** The controller that initiated the request. Also the controller 
	 * that needs to be notified upon successful request retrievals.   
	 */
	ListSaveModelController controller;
	
	/** Reference to a Boolean array that holds flags that represent the 
	 *  statuses of requirements 
	 */
	Boolean[][] modelsThatNeedSaving;
	
	/** The index of the model in the Boolean array */
	int modelIndex;
	
	/** Constructor for ListSaveModelObserver. Takes the controller that created it,
	 *  a Boolean array for referencing flags, and a specific index of a Boolean in
	 *  the array to set specifically.
	 * 
	 * @param controller           The controller that initiated the request.
	 * @param modelsThatNeedSaving Reference to a Boolean array that holds flags that represent the statuses of requirements
	 * @param modelIndex           The index of the model in question in the Boolean array
	 */
	public ListSaveModelObserver(ListSaveModelController controller, Boolean[][] modelsThatNeedSaving, int modelIndex){
		this.controller = controller;
		this.modelsThatNeedSaving = modelsThatNeedSaving;
		this.modelIndex = modelIndex;
	}
	
	/** Upon success, update the Boolean array of flags to show the completion. 
	 *  If all the flags in the array are false, this will indicate that the 
	 *  saving is done. 
	 * 
	 * @param iReq The response from the server
	 */
	public void responseSuccess(IRequest iReq) {
		// Update the array of flags
		for (int i = 0; i < 9 ; i++){
			modelsThatNeedSaving[modelIndex][i] = new Boolean(false);
		}
		// Go through the array of flags and see if we can refresh yet
		for (Boolean[] array: modelsThatNeedSaving){
			for (Boolean flags: array){
			// If any of the flags are still true, exit the method as we cannot refresh yet
				if (flags.booleanValue()) return;	
			}
		}
		
		// If we exit the for loop, we can trigger a refresh
		controller.success("");
	}

	/** Upon error, prints to console
	 * @param iReq The request response from the server 
	 */
	public void responseError(IRequest iReq) {
		controller.error(iReq.getBody());
	}

	/** Upon failure, prints to console
	 * @param iReq The request response from the server 
	 * @param exception unused
	 */
	public void fail(IRequest iReq, Exception exception) {
		controller.fail();
	}

}
