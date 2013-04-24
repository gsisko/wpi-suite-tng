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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * Observer to respond when a lookup requirement response is received
 */
public class LookupRequestObserver implements RequestObserver {

	/** The lookup requirement controller */
	protected LookupRequirementController controller;

	/**
	 * Construct the observer
	 * @param controller the lookup requirement controller
	 */
	public LookupRequestObserver(LookupRequirementController controller) {
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		// cast observable to a Request
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		// check the response code of the request
		if (response.getStatusCode() != 200) {
			controller.requestFailed();
			return;
		}

		// parse the list of requirements received from the core
		Requirement[] requirements = Requirement.fromJSONArray(response.getBody());

		// make sure that there is actually a requirement in the body			
		if (requirements.length > 0 && requirements[0] != null) {
			controller.receivedResponse(requirements[0]);
		}
		else {
			controller.requestFailed();
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		controller.requestFailed();
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		//TODO deal with exception
		controller.requestFailed();
	}
}
