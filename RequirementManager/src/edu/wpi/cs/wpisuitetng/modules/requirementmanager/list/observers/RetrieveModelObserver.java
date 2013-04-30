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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveModelController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/** An observer for a request to retrieve a requirement with the provided id. When the response
 * is gotten from the server, the body is passed to the controller for processing. */
public class RetrieveModelObserver implements RequestObserver, IObserver{
	
	/** Controller that started this observer */
	private final RetrieveModelController controller;

	/**Construct a new observer
	 * @param retrieveModelController the controller managing the request
	 */
	public RetrieveModelObserver(RetrieveModelController retrieveModelController) {
		controller = retrieveModelController;
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
			controller.errorRetrievingModel("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
			return;
		}
		
		controller.showModel(response.getBody());
	}

	/** This method responses when there is a save response error
	 * @param iReq the request sent from the server
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	public void responseError(IRequest iReq) {
		controller.errorRetrievingModel("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	/** This method responses when the save action failed 
	 * 
	 * @param iReq the request sent from the server
	 * @param exception unused
	 */
	public void fail(IRequest iReq, Exception exception) {
		controller.errorRetrievingModel("Unable to complete request: " + exception.getMessage());
	}
}
