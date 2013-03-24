package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.RetrieveFilterController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An observer for a request to retrieve a requirement with the provided id
 */
public class RetrieveFilterObserver implements RequestObserver {

	/** The retrieve requirement controller using this observer */
	protected RetrieveFilterController controller;

	/**
	 * Construct a new observer
	 * @param controller the controller managing the request
	 */
	public RetrieveFilterObserver(RetrieveFilterController controller) {
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
			controller.errorRetrievingFilter("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
			return;
		}

		// parse the requirement received from the core
		Filter[] requirements = Filter.fromJSONArray(response.getBody());
		if (requirements.length > 0 && requirements[0] != null) {
			controller.showFilter(requirements[0]);
		}
		else {
			controller.errorRetrievingFilter("No requirements received.");
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		controller.errorRetrievingFilter("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with exception
		controller.errorRetrievingFilter("Unable to complete request: " + exception.getMessage());
	}
}
