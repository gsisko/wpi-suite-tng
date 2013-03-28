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

import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

/** Observer that waits for a single delete filter request to return
 *  from the server. It refreshes all requirement lists/ filter lists 
 *  upon success. Prints to console upon failure
 */
public class DeleteFilterObserver implements RequestObserver {
	/** Controller that started this observer   	 */
	private final DeleteFilterController controller;
	/** Controller for refreshing after deletion */
	private final RetrieveAllFiltersController refreshController;
	
	/** Default constructor. Pulls  the refresh controller from the delete
	 * controller from the view.
	 * 
	 * @param deleteFilterController
	 */
	public DeleteFilterObserver(DeleteFilterController deleteFilterController) {
		this.controller = deleteFilterController;
		this.refreshController = controller.getTheView().getFilterController();
	}

	/** Upon success, refresh all lists
	 * @param iReq The request response from the server 
	 */
	public void responseSuccess(IRequest iReq) {
		// TODO Auto-generated method stub
		refreshController.refreshData();

	}

	/**  Upon failure, prints to console
	 * @param iReq The request response from the server 
	 */
	public void responseError(IRequest iReq) {
		System.err.println("Could not delete the filter.");
	}

	/** Upon failure, prints to console
	 * @param iReq The request response from the server 
	 */
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Fail: Cannot delete the filter.");
	}
}
