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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.RequestObserver;
import edu.wpi.cs.wpisuitetng.network.models.IRequest;
import edu.wpi.cs.wpisuitetng.network.models.ResponseModel;

/**
 * This observer is called when a response is received from a request
 * to the server to save a message. 
 *
 */
public class SaveRequirementObserver implements RequestObserver {
	
	private final RequirementView view;
	
	public SaveRequirementObserver(RequirementView view) {
		this.view = view;
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
		final Requirement requirement = Requirement.fromJSON(response.getBody());

		// make sure the requirement isn't null
		if (requirement != null) {
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					((RequirementPanel) view.getRequirementPanel()).updateModel(requirement);
					view.setEditModeDescriptors(requirement);
					NoteListModel noteListModel = view.getRequirementPanel().getTabPanel().getNotePanel().getNoteListModel();
					if (noteListModel.getSize() < requirement.getNotes().size()) {
						noteListModel.addMessage(requirement.getNotes().get(requirement.getNotes().size() - 1));
						view.getRequirementPanel().getRequirementNote().setText("");
					}
				}
			});
		}
		else {
			JOptionPane.showMessageDialog(view,	"Unable to parse requirement received from server.", 
					"Save Requirement Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	/* This method responses when there is a save response error
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#responseError(edu.wpi.cs.wpisuitetng.network.models.IRequest)
	 */
	@Override
	public void responseError(IRequest iReq) {
		System.err.println("Cannot save a requirement.");
	}
	/*This method responses when the save action failed 
	 * @see edu.wpi.cs.wpisuitetng.network.RequestObserver#fail(edu.wpi.cs.wpisuitetng.network.models.IRequest, java.lang.Exception)
	 */
	@Override
	public void fail(IRequest iReq, Exception exception) {
		System.err.println("Fail: Cannot save a requirement.");
	}

}
