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

import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel.Mode.CREATE;

import javax.swing.JOptionPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class SaveNoteController {
	    private final RequirementPanel view;

	    public SaveNoteController(RequirementView view) 
	    {
	    	this.view = view.getRequirementPanel();
	    }

	    public void save() {// get the fields from the UI
	    	Requirement currentRequirement = view.getCurrentRequirement();
	    	
	    	String NoteContent = view.getRequirementNote().getText();
			
	    	currentRequirement.getNotes().add(new Note(NoteContent));
	    	
	    	// make a POST http request and let the observer get the response
		    final Request request = Network.getInstance().makeRequest("requirementmanager/requirement", HttpMethod.POST); // POST == update
		    request.setBody(currentRequirement.toJSON()); // put the new message in the body of the request
		    request.addObserver(new SaveRequirementObserver(view.getParent())); // add an observer to process the response
		    request.send();
	    }

}
