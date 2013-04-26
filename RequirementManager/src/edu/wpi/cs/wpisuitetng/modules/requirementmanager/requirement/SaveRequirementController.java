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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode.CREATE;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.SaveAttachmentPartsObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AcceptanceTest;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AttachmentPart;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest.UpdateAcceptanceTestObserver;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SaveRequirementController
{
	private final RequirementTab view;
	private Attachment currentAttachment;

	public SaveRequirementController(RequirementView view) 
	{
		this.view = view.getRequirementPanel();
	}

	public void save() 
	{
		//If the requirement estimate is blank...
		if (view.getRequirementEstimate().getText().length() == 0) {
			view.getRequirementEstimate().setText("0");//...set it to 0
		} 
		//If the requirement actual effort is blank...
		if (view.getRequirementActualEffort().getText().length() == 0) {
			view.getRequirementActualEffort().setText("0");//...set it to 0
		}

		// Warn when the user tries to exit the tab
		view.getAttributePanel().setSaving(true);

		view.getParent().setSaveButtonEnable(false);
		if (view.getMode() == CREATE) { // if we are creating a new requirement

			// make a PUT http request and let the observer get the response
			final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.PUT); // PUT == create
			request.setBody(view.getRequirement().toJSON()); // put the new message in the body of the request
			request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
			request.send();
		}

		else { // we are updating an existing requirement

			// make a new requirement to story the updated data
			Requirement updatedRequirement = new Requirement();

			//grab the old requirement
			Requirement oldRequirement = view.getCurrentRequirement();

			updatedRequirement.setStatus(RequirementStatus.toStatus(view.getRequirementStatus().getSelectedItem().toString()));

			// give the new requirement the correct ID number
			updatedRequirement.setId(oldRequirement.getId());

			// fill in the rest of the fields with the data from the UI
			updatedRequirement.setName(view.getRequirementName().getText());
			updatedRequirement.setDescription(view.getRequirementDescription().getText());
			updatedRequirement.setType(RequirementType.toType(view.getRequirementType().getSelectedItem().toString()));
			updatedRequirement.setReleaseNumber(view.getRequirementReleaseNumber().getText());
			updatedRequirement.setPriority(RequirementPriority.toPriority(view.getRequirementPriority().getSelectedItem().toString()));
			updatedRequirement.setEstimate(Integer.parseInt(view.getRequirementEstimate().getText()));
			updatedRequirement.setActualEffort(Integer.parseInt(view.getRequirementActualEffort().getText()));
			updatedRequirement.setAcceptanceTests(oldRequirement.getAcceptanceTests());
			updatedRequirement.setNotes(oldRequirement.getNotes());
			updatedRequirement.setEvents(oldRequirement.getEvents());

			// Setting the Iteration			
			String selectedIteration = view.getIterationBox().getSelectedItem().toString();
			for (Iteration iter: view.getAllIterations()){
				// If it is the right Iteration, save it into the updated requirement
				if (selectedIteration.equals(iter.getName())){
					updatedRequirement.setIteration(iter.getID());
				}
			}

			//if user had tried to change the status to "Deleted", set the Iteration to "Backlog"
			if (updatedRequirement.getStatus() == RequirementStatus.Deleted || updatedRequirement.getStatus() == RequirementStatus.Open) {
				updatedRequirement.setIteration(0);
			}

			//If we changed the assigned iteration or estimate... no reason to spam the server otherwise
			//This should reduce the number of requests the server gets sent
			if (updatedRequirement.getIteration() != oldRequirement.getIteration() || updatedRequirement.getEstimate() != oldRequirement.getEstimate()){
				//!!! Assuming Iteration will be set above !!!

				/** Update oldIteration */
				Iteration oldIteration = null;

				for (Iteration i : view.getAllIterations()) {
					if (oldRequirement.getIteration() == i.getID()) {
						oldIteration = i;
					}
				}

				//Update totalEstimate
				oldIteration.setTotalEstimate(oldIteration.getTotalEstimate() - oldRequirement.getEstimate());

				//Remove id from the list
				ArrayList<Integer> requirementList = oldIteration.getRequirementsContained();
				if(requirementList.size() != 0){ //Only update if there are requirements saved...
					requirementList.remove((Integer)oldRequirement.getId());
				}
				oldIteration.setRequirementsContained(requirementList);

				//Save the oldIteration on the server. There is no observer because we don't care about the responses //TODO: Make an observer to receive error messages?
				Request saveOldIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration", HttpMethod.POST);
				saveOldIterationRequest.setBody(oldIteration.toJSON());
				saveOldIterationRequest.send();

				/** Update updatedIteration*/
				Iteration updatedIteration = null;

				for (Iteration i : view.getAllIterations()) {
					if (updatedRequirement.getIteration() == i.getID()) {
						updatedIteration = i;
					}
				}

				//Add id to the list
				ArrayList<Integer> updatedRequirementList = updatedIteration.getRequirementsContained();
				updatedRequirementList.add((Integer)updatedRequirement.getId());
				updatedIteration.setRequirementsContained(updatedRequirementList);

				//Update totalEstimate
				updatedIteration.setTotalEstimate(updatedIteration.getTotalEstimate() + updatedRequirement.getEstimate());

				//Save the updatedIteration on the server. There is no observer because we don't care about the responses //TODO: Make an observer to receive error messages?
				Request saveUpdatedIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration", HttpMethod.POST);
				saveUpdatedIterationRequest.setBody(updatedIteration.toJSON());
				saveUpdatedIterationRequest.addObserver(new SaveIterationObserver()); //TODO: Fix? Maybe? Does it matter? This is here to just avoid a nullPointerException...
				saveUpdatedIterationRequest.clearAsynchronous();
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
			/** Update updatedIteration*/
			Iteration currentIteration = null;

			for (Iteration i : view.getAllIterations()) {
				if (i.getID() == newReq.getIteration()) {
					currentIteration = i;
				}
			}

			boolean alreadyContained = false;
			for (int i : currentIteration.getRequirementsContained()) {
				if (newReq.getId() == i) {
					alreadyContained = true;
				}
			}

			if (!alreadyContained) {

				//Add id to the list
				ArrayList<Integer> updatedRequirementList = currentIteration.getRequirementsContained();
				updatedRequirementList.add(newReq.getId());
				currentIteration.setRequirementsContained(updatedRequirementList);

				//Save the updatedIteration on the server. There is no observer because we don't care about the responses //TODO: Make an observer to receive error messages?
				Request saveUpdatedIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration", HttpMethod.POST);
				saveUpdatedIterationRequest.setBody(currentIteration.toJSON());
				saveUpdatedIterationRequest.addObserver(new SaveIterationObserver()); //TODO: Fix? Maybe? Does it matter? This is here to just avoid a nullPointerException...
				saveUpdatedIterationRequest.send();

			}
			if (view.getCurrentRequirement().getStatus() == RequirementStatus.Deleted) {		// Disable the note panel and userChooserTab if the requirement has been deleted

				//Disable notes
				view.toggleEnabled(view.getTabPanel().getNotePanel().getNoteMessage(), false);
				view.getTabPanel().getNotePanel().getSaveButton().setEnabled(false);
				view.getTabPanel().getNotePanel().setEnabled(false);
				if (!view.getTabPanel().getNotePanel().getNoteMessage().getText().equals("")) {
					view.getTabPanel().getNotePanel().getNoteMessage().setText("");
				}

				//Disable UserChooseTab
				view.getTabPanel().getUserChooserPanel().setInputEnabled(false);

				//Disable Acceptance Tests
				view.toggleEnabled(view.getTabPanel().getAcceptanceTestPanel().getAcceptanceTestDescription(), false);
				view.getTabPanel().getAcceptanceTestPanel().getSaveButton().setEnabled(false);
				view.getTabPanel().getAcceptanceTestPanel().setEnabled(false);

				//Clear out the text if disabled
				if (!view.getTabPanel().getAcceptanceTestPanel().getAcceptanceTestDescription().getText().equals("")) {
					view.getTabPanel().getAcceptanceTestPanel().getAcceptanceTestDescription().setText("");
				}
				if (!view.getTabPanel().getAcceptanceTestPanel().getTxtName().getText().equals("")) {
					view.getTabPanel().getAcceptanceTestPanel().getTxtName().setText("");
				}
			} else {

				//Enable Notes
				view.toggleEnabled(view.getTabPanel().getNotePanel().getNoteMessage(), true);
				view.getTabPanel().getNotePanel().setSaveButtonWhenMessageIsValid();
				view.getTabPanel().getNotePanel().setEnabled(true);
				if (!view.getTabPanel().getNotePanel().getNoteMessage().getText().equals("")) {
					view.getTabPanel().getNotePanel().getNoteMessage().setBackground(new Color(248,253,188));
				}

				//Enable UserChooserTab
				view.getTabPanel().getUserChooserPanel().setInputEnabled(true);

				//Enable Acceptance Tests
				view.toggleEnabled(view.getTabPanel().getAcceptanceTestPanel().getAcceptanceTestDescription(), true);
				view.toggleEnabled(view.getTabPanel().getAcceptanceTestPanel().getTxtName(), true);
				view.getTabPanel().getAcceptanceTestPanel().setSaveButtonWhenMessageIsValid();
				view.getTabPanel().getAcceptanceTestPanel().setEnabled(true);

				//Set background to yellow if changed
				if (!view.getTabPanel().getAcceptanceTestPanel().getTxtName().getText().equals("")) {
					view.getTabPanel().getAcceptanceTestPanel().getTxtName().setBackground(new Color(248,253,188));
				}
				if (!view.getTabPanel().getAcceptanceTestPanel().getAcceptanceTestDescription().getText().equals("")) {
					view.getTabPanel().getAcceptanceTestPanel().getAcceptanceTestDescription().setBackground(new Color(248,253,188));
				}
			}
			// refreshes the list view, should be made much cleaner in the future
			((ListView)view.getParent().getTabController().getView().getComponentAt(0)).refreshData();
		} else {
			System.err.print("Undetected error saving requirement\n");
		}
		view.getAttributePanel().getSaveButton().setEnabled(false);
	}

	public RequirementTab getView() {
		return view;
	}
	/**
	 * Saves a new note to the Requirement
	 */
	public void saveNote() {

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

	/**
	 * Saves an acceptance test to the Requirement
	 */
	public void saveAcceptanceTest() {
		Requirement currentRequirement = view.getCurrentRequirement();

		AcceptanceTest newTest = view.getRequirementAcceptanceTest();
		view.getTabPanel().getAcceptanceTestPanel().getAcceptanceTestDescription().setText("");
		view.getTabPanel().getAcceptanceTestPanel().getTxtName().setText("");

		currentRequirement.getAcceptanceTests().add(newTest);

		// make a POST http request and let the observer get the response
		final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
		request.setBody(currentRequirement.toJSON()); // put the new message in the body of the request
		request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
		request.send();
	}
	
	/** Updates an old AcceptanceTest
	 * 
	 * @param oldTest The old version of the test
	 * @param newTest Contains the changes we want to save
	 */
	public void updateAcceptanceTest (AcceptanceTest oldTest, AcceptanceTest newTest) {
		Requirement currentRequirement = view.getCurrentRequirement();
		ArrayList<AcceptanceTest> myList = currentRequirement.getAcceptanceTests();
		for (int i = 0; i < myList.size(); i++) { 	// Look through the list of tests
			// For the matching old test
			if (myList.get(i).getAcceptanceTestTitle().equals(oldTest.getAcceptanceTestTitle()) && myList.get(i).getDescription().equals(oldTest.getDescription())) {
				myList.get(i).setAcceptanceTestResult(newTest.getAcceptanceTestResult());	// And update it
			}
		}
		
		// make a POST http request and let the observer get the response
		final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
		request.setBody(currentRequirement.toJSON()); // put the new message in the body of the request
		request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
		request.send();
	}

	public void saveAttachment() throws IOException {
		JFileChooser fc = new JFileChooser();

		int returnVal = fc.showDialog(null,"Add Attachment");

		//Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION && fc.getSelectedFile().exists() && fc.getSelectedFile().length() <= 4194304) {

			Requirement currentRequirement = view.getCurrentRequirement();
			InputStream source = null;
			ArrayList<ByteArrayOutputStream> destinations = new ArrayList<ByteArrayOutputStream>();

			try {
				source = new FileInputStream(fc.getSelectedFile());// = new InputStream().getChannel();

				byte[] buffer = new byte[8192];


				int read = 0;
				while ( (read = source.read(buffer)) != -1 ) {
					ByteArrayOutputStream newDestination = new ByteArrayOutputStream();
					newDestination.write(buffer, 0, read);
					destinations.add(newDestination);
				}
			}
			finally {
				if(source != null) {
					source.close();
				}
				for(ByteArrayOutputStream destination : destinations){
					if(destination != null) {
						destination.close();
					}
				}   
			}

			currentAttachment = new Attachment(fc.getSelectedFile().getName(), (int) fc.getSelectedFile().length());

			int n = 0;
			ArrayList<AttachmentPart> parts = new ArrayList<AttachmentPart>();
			for(ByteArrayOutputStream destination : destinations){
				AttachmentPart part = new AttachmentPart(destination.size(), destination.toByteArray(), n);
				parts.add(part);

				final Request request = Network.getInstance().makeRequest("requirementmanager/attachmentpart", HttpMethod.PUT); // PUT == create
				request.setBody(part.toJSON()); // put the new message in the body of the request
				request.addObserver(new SaveAttachmentPartsObserver(this)); // add an observer to process the response
				request.send();

				n++;
			}

			boolean finished = false;
			while(!finished) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (currentAttachment.getAttachmentPartIds().size() == destinations.size())
					finished = true;
			}

			currentRequirement.getAttachments().add(currentAttachment);

			// make a POST http request and let the observer get the response
			final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
			request.setBody(currentRequirement.toJSON()); // put the new message in the body of the request
			request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
			request.send();
		}
		else if(fc.getSelectedFile().exists() && fc.getSelectedFile().length() > 4194304){ //TODO Get rid of popup?
			JOptionPane.showMessageDialog(null, "File size must be 4 megabytes or less.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		fc.setSelectedFile(null);
	}

	public void saveUsers() {

		Requirement currentRequirement = view.getCurrentRequirement();
		ArrayList<String> assignedUsers = new ArrayList<String>();
		UserListModel assignedUserListModel = view.getTabPanel().getUserChooserPanel().getAssignedUserListModel();

		for (int i = 0; i < assignedUserListModel.getSize(); i++) {
			assignedUsers.add(assignedUserListModel.getUserAt(i));
		}
		currentRequirement.setUserNames(assignedUsers);

		final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
		request.setBody(currentRequirement.toJSON()); // put the new message in the body of the request
		request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
		request.send();
	}

	/**
	 * @return the currentAttachment
	 */
	public Attachment getCurrentAttachment() {
		return currentAttachment;
	}

	/**
	 * @param currentAttachment the currentAttachment to set
	 */
	public void setCurrentAttachment(Attachment currentAttachment) {
		this.currentAttachment = currentAttachment;
	}

	public synchronized void addAttachmentPartId(int id) {
		currentAttachment.getAttachmentPartIds().add(id);
	}
}
