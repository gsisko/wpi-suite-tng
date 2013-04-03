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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveModelController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An observer for a request to retrieve a requirement with the provided id
 */
public class RetrieveModelObserver implements RequestObserver {
	/** Controller that started this observer   	 */
	private final RetrieveModelController controller;

	/**
	 * Construct a new observer
	 * @param retrieveModelController the controller managing the request
	 */
	public RetrieveModelObserver(RetrieveModelController retrieveModelController) {
		this.controller = retrieveModelController;
	}

	/** Respond to a successful message from the network
	 * 
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

	/** Respond to an  unsuccessful message from the network
	 * 
	 */
	public void responseError(IRequest iReq) {
		controller.errorRetrievingModel("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	/** Respond to a failure message from the network
	 * 
	 */
	public void fail(IRequest iReq, Exception exception) {
		controller.errorRetrievingModel("Unable to complete request: " + exception.getMessage());
	}
}
