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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.DeleteModelController;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/** Observer that waits for a single delete Model request to return
 *  from the server. It refreshes all list views applicable. */
public class DeleteModelObserver implements RequestObserver {
	/** Controller that started this observer   	 */
	private final DeleteModelController controller;
	
	/** Default constructor with a reference back to the controller
	 * @param deleteFilterController
	 */
	public DeleteModelObserver(DeleteModelController deleteFilterController) {
		this.controller = deleteFilterController;
	}

	/** Upon success, tell the controller to trigger a refresh 
	 * @param iReq The request response from the server 
	 */
	public void responseSuccess(IRequest iReq) {
		// If deleted successfully, we refresh the
		// list view so we can see the changes.
		controller.refreshListViews();
	}

	/**  Upon failure, prints to console
	 * @param iReq The request response from the server 
	 */
	public void responseError(IRequest iReq) {
		System.err.println("Could not delete the model.");
	}

	/** Upon failure, prints to console
	 * @param iReq The request response from the server 
	 */
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Fail: Cannot delete the model.");
	}
}
