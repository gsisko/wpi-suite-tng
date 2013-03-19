package edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to add a message.
 *
 */
public class SaveRequirementObserver implements RequestObserver {
	
	private final SaveRequirementController rmcontroller;
	
	public SaveRequirementObserver(SaveRequirementController rmcontroller) {
		this.rmcontroller = rmcontroller;
	}
	
	/*
	 * Parse the message that was received from the server then pass them to
	 * the controller.
	 * 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseSuccess(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseSuccess(IRequest iReq) {
		// Get the response to the given request
		final ResponseModel response = iReq.getResponse();
		
		// Parse the message out of the response body
		final Requirement message = Requirement.fromJson(response.getBody());
		
		// Pass the messages back to the controller
		rmcontroller.addRMMessageToModel(message);
	}

	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Cannot add a message");
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Cannot add a message.");
	}

}

