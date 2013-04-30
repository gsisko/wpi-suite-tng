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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllModelsController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/** An observer waiting for a request to retrieve all models. That message is read and 
 * passed on to the controller.  */
public class RetrieveAllModelsObserver implements RequestObserver,IObserver{

	/** The controller managing the request */
	protected RetrieveAllModelsController controller;

	/** Construct the observer
	 * @param controller The controller managing the request
	 */
	public RetrieveAllModelsObserver(RetrieveAllModelsController controller) {
		this.controller = controller;
	}

	/** Upon success, tell the controller to trigger a refresh 
	 * @param iReq The request response from the server 
	 */
	public void responseSuccess(IRequest iReq) {
		// cast observable to request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		if (response.getStatusCode() == 200) {
			// notify the controller and give it the good data
			controller.receivedData(response.getBody());
		}
		else {
			controller.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		}
	}

	/**  Upon error, prints to console
	 * @param iReq The request response from the server 
	 */
	public void responseError(IRequest iReq) {
		// an error occurred
		controller.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	/** Upon failure, prints to console
	 * @param iReq The request response from the server 
 	 * @param exception unused
	 */
	public void fail(IRequest iReq, Exception exception) {
		// an error occurred
		controller.errorReceivingData("Unable to complete request: " + exception.getMessage());
	}
}
