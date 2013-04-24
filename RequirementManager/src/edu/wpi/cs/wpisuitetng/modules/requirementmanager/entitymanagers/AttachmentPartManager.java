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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import java.util.List;

import com.google.gson.JsonSyntaxException;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AttachmentPart;

/** This is the entity manager for filters in the RequirementManager module   */
public class AttachmentPartManager implements EntityManager<AttachmentPart> {

	private final Data db;
	
	/**
	 * Create a CommentManager that uses the given Data instance
	 * @param data The Data instance to interact with
	 */
	public AttachmentPartManager(Data data) {
		db = data;
	}
	
	/**
	 * Takes a filter and assigns a unique id if necessary
	 * 
	 * @param filter  The filter that possibly needs a unique id
	 * @throws WPISuiteException
	 */
	private void assignUniqueID(AttachmentPart attachmentPart) throws WPISuiteException {
		if (attachmentPart.getId() == -1) {// -1 is a flag that says a unique id is needed
			attachmentPart.setId(this.Count() + 1); // Makes first Filter have id = 1
		}
	}

	/**
	 * Returns the number of filters currently in the database. Counts only
	 * those filters specific to the current user
	 * 
	 * @return The number of Requirements currently in the database
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	public int Count() throws WPISuiteException {
		// Passing a dummy Filter lets the db know what type of object to
		// retrieve
		return this.db.retrieveAll(new AttachmentPart(0, null, 0)).size();
	}
	
	@Override
	public void save(Session s, AttachmentPart model) throws WPISuiteException {
		assignUniqueID(model); // Assigns a unique ID to the Req if necessary

		// Save the filter in the database if possible, otherwise throw an exception
		// We DON'T want the filter to be associated with any project
		if (!this.db.save(model)) {
			throw new WPISuiteException("Unable to save AttachmentPart.");
		}
	}
	
	@Override
	public synchronized AttachmentPart makeEntity(Session s, String content) throws WPISuiteException {
		// Parse the requirement from JSON
		final AttachmentPart newAttachmentPart;

		try {
			newAttachmentPart = AttachmentPart.fromJson(content);
		} catch (JsonSyntaxException e) { // the JSON conversion failed
			throw new BadRequestException(	"The AttachmentPart creation string had invalid formatting. Entity String: "+ content);
		}

		// Saves the filter in the database
		this.save(s, newAttachmentPart); // An exception may be thrown here if we can't save it

		// Return the newly created filter (this gets passed back to the client)
		return newAttachmentPart; // End method
	}

	@Override
	public AttachmentPart[] getEntity(Session s, String id) throws WPISuiteException {
		final int intId = Integer.parseInt(id);

		if (intId < 1) {
			throw new NotFoundException(
					"The Filter with the specified id was not found:" + intId);
		}
		AttachmentPart[] attachmentParts = null;

		// Try to retrieve the specific Filter
		try {
			attachmentParts = db.retrieve(AttachmentPart.class, "id", intId).toArray(new AttachmentPart[0]);
		} catch (WPISuiteException e) { // caught and re-thrown with a new message
			e.printStackTrace();
			throw new WPISuiteException("There was a problem retrieving from the database.");
		}

		// If a filter was pulled, but has no content
		if (attachmentParts.length < 1 || attachmentParts[0] == null) {
			throw new NotFoundException("The Filter with the specified id was not found:" + intId);
		}
		return attachmentParts;
	}

	@Override
	public AttachmentPart[] getAll(Session s) throws NotImplementedException {
		List<AttachmentPart> attachmentPartList = this.db.retrieveAll((new AttachmentPart(0, null, 0)));
		return attachmentPartList.toArray(new AttachmentPart[attachmentPartList.size()]);
	}

	@Override
	public AttachmentPart update(Session s, String content) throws NotImplementedException {
		throw new NotImplementedException();
	}

	

	@Override
	public boolean deleteEntity(Session s, String id) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public String advancedGet(Session s, String[] args) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public void deleteAll(Session s) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public String advancedPut(Session s, String[] args, String content) throws NotImplementedException {
		throw new NotImplementedException();
	}

	@Override
	public String advancedPost(Session s, String string, String content) throws NotImplementedException {
		throw new NotImplementedException();
	}

}
