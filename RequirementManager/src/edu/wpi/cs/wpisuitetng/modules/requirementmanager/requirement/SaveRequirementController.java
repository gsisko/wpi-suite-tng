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

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.observers.SaveAttachmentPartsObserver;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AttachmentPart;
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
	private Attachment currentAttachment;
	private boolean partSaveSuccess;

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
			updatedRequirement.setNotes(oldRequirement.getNotes());
			updatedRequirement.setEvents(oldRequirement.getEvents());

			Iteration newIter = null;
			// Setting the Iteration			
			String selectedIteration = view.getIterationBox().getSelectedItem().toString();
			for (Iteration iter: view.getAllIterations()){
				// If it is the right Iteration, save it into the updated requirement
				if (selectedIteration.equals(iter.getName())){
					updatedRequirement.setIteration(iter.getID());
					newIter = iter;
				}
			}



			int oldIterID = oldRequirement.getIteration(); //grab the old status
			int newIterID = updatedRequirement.getIteration();
			if (newIterID != oldIterID) {
				if (newIter.getEndDate().before(new Date()) && newIterID != 0) {
					JOptionPane.showMessageDialog(null, "Cannot assign a requirement to an iteration that has already ended.", "Error", JOptionPane.ERROR_MESSAGE); //popup an error message
					return;//cancel the update
				}

			}

			//if user had tried to change the status to "Deleted", set the Iteration to "Backlog"
			if (updatedRequirement.getStatus() == RequirementStatus.Deleted) {
				updatedRequirement.setIteration(0);
			}

//			//If we changed the assigned iteration or estimate... no reason to spam the server otherwise
//			//This should reduce the number of requests the server gets sent
//			if (updatedRequirement.getIteration() != oldRequirement.getIteration() || updatedRequirement.getEstimate() != oldRequirement.getEstimate()){
//				//!!! Assuming Iteration will be set above !!!
//
//				/** Update oldIteration */
//				Iteration oldIteration = null;
//
//				for (Iteration i : view.getAllIterations()) {
//					if (oldRequirement.getIteration() == i.getID()) {
//						oldIteration = i;
//					}
//				}
//
//				System.out.println(oldIteration.getTotalEstimate() - oldRequirement.getEstimate());
//				//Update totalEstimate
//				oldIteration.setTotalEstimate(oldIteration.getTotalEstimate() - oldRequirement.getEstimate());
//
//				//Remove id from the list
//				ArrayList<Integer> requirementList = oldIteration.getRequirementsContained();
//				if(requirementList.size() != 0){ //Only update if there are requirements saved...
//					requirementList.remove((Integer)oldRequirement.getId());
//				}
//				oldIteration.setRequirementsContained(requirementList);
//
//
//
//				//Save the oldIteration on the server. There is no observer because we don't care about the responses //TODO: Make an observer to receive error messages?
//				Request saveOldIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration", HttpMethod.POST);
//				saveOldIterationRequest.setBody(oldIteration.toJSON());
//				saveOldIterationRequest.send();
//
//				/** Update updatedIteration*/
//				Iteration updatedIteration = null;
//
//				for (Iteration i : view.getAllIterations()) {
//					if (updatedRequirement.getIteration() == i.getID()) {
//						updatedIteration = i;
//					}
//				}
//
//				//Add id to the list
//				ArrayList<Integer> updatedRequirementList = updatedIteration.getRequirementsContained();
//				updatedRequirementList.add((Integer)updatedRequirement.getId());
//				updatedIteration.setRequirementsContained(updatedRequirementList);
//
//				//Update totalEstimate
//				updatedIteration.setTotalEstimate(updatedIteration.getTotalEstimate() + updatedRequirement.getEstimate());
//
//				//Save the updatedIteration on the server. There is no observer because we don't care about the responses //TODO: Make an observer to receive error messages?
//				Request saveUpdatedIterationRequest = Network.getInstance().makeRequest("requirementmanager/iteration", HttpMethod.POST);
//				saveUpdatedIterationRequest.setBody(updatedIteration.toJSON());
//				saveUpdatedIterationRequest.addObserver(new SaveIterationObserver()); //TODO: Fix? Maybe? Does it matter? This is here to just avoid a nullPointerException...
//				saveUpdatedIterationRequest.clearAsynchronous();
//				saveUpdatedIterationRequest.send();
//			}

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
	
	public void saveAttachment() throws IOException {
		JFileChooser fc = new JFileChooser();

        int returnVal = fc.showDialog(null,"Add Attachment");

        //Process the results.
        if (returnVal == JFileChooser.APPROVE_OPTION && fc.getSelectedFile().exists()) {
        	
        	Requirement currentRequirement = view.getCurrentRequirement();
        	InputStream source = null;
        	ArrayList<ByteArrayOutputStream> destinations = new ArrayList<ByteArrayOutputStream>();
			
			try {
				source = new FileInputStream(fc.getSelectedFile());// = new InputStream().getChannel();
				
				byte[] buffer = new byte[32768];
				
		        
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
			
			ArrayList<AttachmentPart> parts = new ArrayList<AttachmentPart>();
			for(ByteArrayOutputStream destination : destinations){
				partSaveSuccess = false;
	        	AttachmentPart part = new AttachmentPart(destination.size(), destination.toByteArray());
	        	parts.add(part);
	        	
	        	final Request request = Network.getInstance().makeRequest("requirementmanager/attachmentpart", HttpMethod.PUT); // PUT == create
	    	    request.setBody(part.toJSON()); // put the new message in the body of the request
	    	    request.addObserver(new SaveAttachmentPartsObserver(this)); // add an observer to process the response
	    	    request.send();
	    	    
	    	    while(!partSaveSuccess);
	        }

        	currentRequirement.getAttachments().add(currentAttachment);
        	
        	// make a POST http request and let the observer get the response
    	    final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
    	    request.setBody(currentRequirement.toJSON()); // put the new message in the body of the request
    	    request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
    	    request.send();
        }
        fc.setSelectedFile(null);
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

	public void setPartSaveSuccess(boolean b, int id) {
		this.partSaveSuccess = b;
		this.currentAttachment.getAttachmentPartIds().add(id);
	}
}
