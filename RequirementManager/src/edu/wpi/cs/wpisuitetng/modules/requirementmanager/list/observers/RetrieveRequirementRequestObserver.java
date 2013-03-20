package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An observer for a request to retrieve a requirement with the provided id
 */
public class RetrieveRequirementRequestObserver implements RequestObserver {

	/** The retrieve requirement controller using this observer */
	protected RetrieveRequirementController controller;
	protected int row;

	/**
	 * Construct a new observer
	 * @param controller the controller managing the request
	 */
	public RetrieveRequirementRequestObserver(RetrieveRequirementController controller, int row) {
		this.controller = controller;
		this.row = row;
	}

	@Override
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
		if (requirements.length > 0 && requirements[row] != null) {
			controller.showRequirement(requirements[row]);
		}
		else {
			controller.errorRetrievingRequirement("No requirements received.");
		}
	}

	@Override
	public void responseError(IRequest iReq) {
		controller.errorRetrievingRequirement("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		// TODO deal with exception
		controller.errorRetrievingRequirement("Unable to complete request: " + exception.getMessage());
	}
}
