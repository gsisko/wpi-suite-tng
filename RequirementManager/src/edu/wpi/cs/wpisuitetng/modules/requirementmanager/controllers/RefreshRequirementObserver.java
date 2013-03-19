package edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;

public class RefreshRequirementObserver implements RequestObserver {
    
    public RefreshRequirementController controller;

    public RefreshRequirementObserver(RefreshRequirementController controller) {
	this.controller = controller;
    }

    @Override
    public void responseSuccess(IRequest iReq) {
	Requirement[] requirements = Requirement.fromJSONArray(iReq.getResponse().getBody());
	controller.reviecedData(requirements);
    }

    @Override
    public void responseError(IRequest iReq) {
	// TODO Auto-generated method stub
	fail(iReq, null);
    }

    @Override
    public void fail(IRequest iReq, Exception exception) {
	// TODO Auto-generated method stub
	fail(iReq, null);
	/*
	 * Not sure how to edit this info to do what we want at this moment
	 * Requirement[] errorMessage = {new Requirement("Error retrieving messages.")};
	 * controller.receivedMessages(errorMessage);
	*/
    }

}
