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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.IObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AttachmentPart;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.SaveRequirementController;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * An observer for a request to retrieve all requirements
 */
public class SaveAttachmentPartsObserver implements RequestObserver,IObserver{

	/** The controller managing the request */
	protected SaveRequirementController controller;

	/**
	 * Construct the observer
	 * @param controller
	 */
	public SaveAttachmentPartsObserver(SaveRequirementController controller) {
		this.controller = controller;
	}

	@Override
	public void responseSuccess(IRequest iReq) {
		Request request = (Request) iReq;

		// get the response from the request
		ResponseModel response = request.getResponse();

		AttachmentPart attachmentPart = AttachmentPart.fromJson(response.getBody());
		controller.addAttachmentPartId(attachmentPart.getId());
	}

	@Override
	public void responseError(IRequest iReq) {
		// an error occurred
		//controller.errorReceivingData("Received " + iReq.getResponse().getStatusCode() + " error from server: " + iReq.getResponse().getStatusMessage());
	}

	@Override
	public void fail(IRequest iReq, Exception exception) {
		Request request = (Request) iReq;
		request.send();
		// an error occurred
		//controller.errorReceivingData("Unable to complete request: " + exception.getMessage());
	}
}
