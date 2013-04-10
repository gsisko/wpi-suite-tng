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

import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode.CREATE;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SaveRequirementController
{
	private final RequirementTab view;

	public SaveRequirementController(RequirementView view) 
	{
		this.view = view.getRequirementPanel();
	}

	public void save() 
	{
		// check if any inputs are invalid, print an error message if one is
		String error = "";
		if (view.getRequirementName().getText().length() == 0) {
			error += "Name must be non-blank.\n";
		}
		if (view.getRequirementName().getText().length() > 100) {
			error += "Name cannot be greater than 100 characters.\n";
		}
		if (view.getRequirementDescription().getText().length() == 0) {
			error += "Description must be non-blank.\n";
		}
		if (view.getRequirementEstimate().getText().length() == 0) {
			error += "Estimate must be non-blank.\n";
		}
		if (view.getRequirementActualEffort().getText().length() == 0) {
			error += "ActualEffort must be non-blank.\n";
		}
		//TODO this should change to eliminate popups, maybe put error checking in the panel itself?
		if (!error.equals("")) {
			JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		if (view.getMode() == CREATE) { // if we are creating a new requirement

			// make a PUT http request and let the observer get the response
			final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.PUT); // PUT == create
			request.setBody(view.getRequirement().toJSON()); // put the new message in the body of the request
			request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
			request.send();
		}

		else { // we are updating an existing requirement

			Requirement oldRequirement = view.getCurrentRequirement();//grab the old requirement

			//Check to see if the status update is invalid because the user had tried to change the status from "InProgress" to "Deleted")
			RequirementStatus oldStatus = oldRequirement.getStatus(); //grab the old status
			RequirementStatus newStatus = RequirementStatus.toStatus(view.getRequirementStatus().getSelectedItem().toString()); //get the new status
			if (   ( oldStatus == RequirementStatus.InProgress) && (newStatus == RequirementStatus.Deleted) ) {//if user had tried to change the status from "InProgress" to "Deleted"...
				JOptionPane.showMessageDialog(null, "Cannot change status from InProgress to Deleted.", "Error", JOptionPane.ERROR_MESSAGE); //popup an error message
				return;//cancel the update
			}

			// make a new requirement to story the updated data
			Requirement updatedRequirement = new Requirement(); 

			// give the new requirement the correct ID number
			updatedRequirement.setId(oldRequirement.getId());

			// fill in the rest of the fields with the data from the UI
			updatedRequirement.setName(view.getRequirementName().getText());
			updatedRequirement.setDescription(view.getRequirementDescription().getText());
			updatedRequirement.setType(RequirementType.toType(view.getRequirementType().getSelectedItem().toString()));
			updatedRequirement.setReleaseNumber(Integer.parseInt((view.getRequirementReleaseNumber().getText().equals("")) ? "-1" : view.getRequirementReleaseNumber().getText()));
			updatedRequirement.setStatus(RequirementStatus.toStatus(view.getRequirementStatus().getSelectedItem().toString()));
			updatedRequirement.setPriority(RequirementPriority.toPriority(view.getRequirementPriority().getSelectedItem().toString()));
			updatedRequirement.setEstimate(Integer.parseInt(view.getRequirementEstimate().getText()));
			updatedRequirement.setActualEffort(Integer.parseInt(view.getRequirementActualEffort().getText()));
			updatedRequirement.setNotes(oldRequirement.getNotes());

			//If we changed the assigned iteration... no reason to spam the server otherwise
			if (updatedRequirement.getAssignedIteration() != oldRequirement.getAssignedIteration()){
				//!!! Assuming Iteration will be set above !!!

				/** Update oldIteration */
				Iteration oldIteration;

				//Convert oldIteration from JSON
				final Request getOldIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration/" + oldRequirement.getAssignedIteration() , HttpMethod.GET);
				getOldIterationRequest.addObserver(null); //TODO: Fix? Maybe? Does it matter?
				getOldIterationRequest.send();
				oldIteration = Iteration.fromJSON(getOldIterationRequest.getBody());

				//Remove id from the list
				ArrayList<Integer> requirementList = oldIteration.getRequirementsContained();
				requirementList.remove(oldRequirement.getId());
				oldIteration.setRequirementsContained(requirementList);

				//Update totalEstimate
				oldIteration.setTotalEstimate(oldIteration.getTotalEstimate() - oldRequirement.getEstimate());

				//Save the oldIteration on the server
				final Request saveOldIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration/" + oldRequirement.getAssignedIteration() , HttpMethod.POST);
				saveOldIterationRequest.setBody(oldIteration.toJSON());
				saveOldIterationRequest.addObserver(null); //TODO: Fix? Maybe? Does it matter?
				saveOldIterationRequest.send();

				/** Update updatedIteration*/
				Iteration updatedIteration;

				//Convert updatedIteration from JSON
				final Request getUpdatedIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration/" + updatedRequirement.getAssignedIteration() , HttpMethod.GET);
				getUpdatedIterationRequest.addObserver(null); //TODO: Fix? Maybe? Does it matter?
				getUpdatedIterationRequest.send();
				updatedIteration = Iteration.fromJSON(getUpdatedIterationRequest.getBody());

				//Add id to the list
				ArrayList<Integer> updatedRequirementList = updatedIteration.getRequirementsContained();
				updatedRequirementList.add(updatedRequirement.getId());
				updatedIteration.setRequirementsContained(updatedRequirementList);

				//Update totalEstimate
				updatedIteration.setTotalEstimate(updatedIteration.getTotalEstimate() + updatedRequirement.getEstimate());

				//Save the updatedIteration on the server
				final Request saveUpdatedIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration/" + updatedRequirement.getAssignedIteration() , HttpMethod.POST);
				saveUpdatedIterationRequest.setBody(updatedIteration.toJSON());
				saveUpdatedIterationRequest.addObserver(null); //TODO: Fix? Maybe? Does it matter?
				saveUpdatedIterationRequest.send();
			}

			// make a POST http request and let the observer get the response
			final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
			request.setBody(updatedRequirement.toJSON()); // put the new message in the body of the request
			request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
			request.send();

		}

	}


	/**
	 * Simple success message for saving a new requirement.  If we want the boxes to clear automatically,
	 * this is probably where we would want to implement it.
	 * @param newReq Requirement that was saved.
	 */
	public void saveSuccess(Requirement newReq) {
		// if success, set all of the UI fields appropriately for post-save actions
		if (newReq != null) {
			System.out.print("Requirement " + newReq.getId() + " saved successfully\n");
		}
		else {
			System.err.print("Undected error saving requirement\n");
		}
	}

	public RequirementTab getView() {
		return view;
	}
	/**
	 * Saves a new note to the Requirement
	 */
	public void saveNote() {

		// check if any inputs are invalid, print an error message if one is
		if (view.getTabPanel().getNotePanel().getNoteMessage().getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Note must be non-blank.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		Requirement currentRequirement = view.getCurrentRequirement();

		String NoteContent = view.getRequirementNote().getText();
		view.getRequirementNote().setText("");
		currentRequirement.getNotes().add(new Note(NoteContent));

		// make a POST http request and let the observer get the response
		final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
		request.setBody(currentRequirement.toJSON()); // put the new message in the body of the request
		request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
		request.send();
	}

}

