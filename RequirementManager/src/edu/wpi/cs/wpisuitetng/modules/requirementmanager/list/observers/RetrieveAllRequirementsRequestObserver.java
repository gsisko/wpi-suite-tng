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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/** An observer for a request to retrieve all requirements   */
public class RetrieveAllRequirementsRequestObserver implements RequestObserver {

	/** The controller managing the request */
	protected RetrieveAllRequirementsController controller;

	/** Construct the observer
	 * @param controller the controller managing the request
	 */
	public RetrieveAllRequirementsRequestObserver(RetrieveAllRequirementsController controller) {
		this.controller = controller;
	}

	/** Parse the message that was received from the server and tells the controller
	 * @param iReq the request sent from the server
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	public void responseSuccess(IRequest iReq) {
		// cast observable to request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		if (response.getStatusCode() == 200) {
			// parse the response				
			Requirement[] requirements = Requirement.fromJSONArray(response.getBody());

			// notify the controller
			controller.receivedData(requirements);
		}
		else {
			controller.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		}
	}

	/** This method responses when there is a save response error
	 * @param iReq the request sent from the server
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	public void responseError(IRequest iReq) {
		// an error occurred
		controller.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
		controller.setRefreshes(controller.getRefreshes() -1);
	}

	/** This method responses when the save action failed 
	 * 
	 * @param iReq the request sent from the server
	 * @param exception unused
	 */
	public void fail(IRequest iReq, Exception exception) {
		// an error occurred
		controller.errorReceivingData("Unable to complete request: " + exception.getMessage());
		controller.setRefreshes(controller.getRefreshes() -1);
	}
}
