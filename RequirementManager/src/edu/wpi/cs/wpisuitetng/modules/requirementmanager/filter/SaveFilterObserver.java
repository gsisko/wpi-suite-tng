package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to save a message. 
 *
 */
public class SaveFilterObserver implements RequestObserver {
	
	private final SaveFilterController rmcontroller;
	
	public SaveFilterObserver(SaveFilterController rmcontroller) {
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
		final Filter message = Filter.fromJSON(response.getBody());
		
		// Pass the messages back to the controller
		rmcontroller.saveSuccess(message);
		
		// auto-update
		//rmcontroller.getView().getRetrieveAllFiltersController().refreshData();
	}
	/* This method responses when there is a save response error
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Cannot save a filter.");
	}
	/*This method responses when the save action failed 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Fail: Cannot save a filter.");
	}

}
