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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/** An observer for a request to retrieve a requirement with the provided id. When the response
 * is gotten from the server, the body is passed to the controller for processing. 
 */
public class RetrieveRequirementRequestObserver implements RequestObserver {

	/** The retrieve requirement controller using this observer */
	protected RetrieveRequirementController controller;

	/** Construct a new observer
	 * @param controller the controller managing the request
	 */
	public RetrieveRequirementRequestObserver(RetrieveRequirementController controller) {
		this.controller = controller;
	}

	/** Parse the message that was received from the server and tells the controller
	 * @param iReq the request sent from the server
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// check the response code of the request
		if (response.getStatusCode() != 200) {
			controller.errorRetrievingRequirement("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
			return;
		}

		// parse the requirement received from the core
		Requirement[] requirements = Requirement.fromJSONArray(response.getBody());
		if (requirements.length > 0 && requirements[0] != null) {
			controller.showRequirement(requirements[0]);
		}
		else {
			controller.errorRetrievingRequirement("No requirements received.");
		}
	}

	/** This method responses when there is a save response error
	 * @param iReq the request sent from the server
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	public void responseError(IRequest iReq) {
		controller.errorRetrievingRequirement("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	/** This method responses when the save action failed 
	 * 
	 * @param iReq the request sent from the server
	 * @param exception unused
	 */
	public void fail(IRequest iReq, Exception exception) {
		controller.errorRetrievingRequirement("Unable to complete request: " + exception.getMessage());
	}
}
