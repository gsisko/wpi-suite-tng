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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.JsonSyntaxException;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/**This is the entity manager for the Requirement in the RequirementManager module
 * 
 * @author Team 5
 *
 * @version $Revision: 1.0 $
 */
public class RequirementManager implements EntityManager<Requirement> {
	/** The database */
	private Data db;
	
	/** This is for advanced logging and debugging of the server interactions */
	private static final Logger logger = Logger.getLogger(RequirementManager.class.getName());
	
	/** Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. 
	 * To make sure this happens, be sure to place add this entity 
	 * manager to the map in the ManagerLayer file.
	 * 
	 * Expects that the data passed is valid and does no error checking!
	 *
	 * @param data Database in the core
	 */	
	public RequirementManager(Data data) {
		this.db = data;
	
	}

	
		
	/** Takes an encoded Requirement(as a string) and converts it back to a 
	 *  Requirement and saves it in the database
	 *  
	 *	@param s The current user session
	 *	@param content The requirement that comes in the form of a string to be recreated
	 *	
	@return the Requirement that originally came as a string
	 * @throws BadRequestException "The Requirement creation string had invalid formatting. Entity String: " + content
	 * @throws ConflictException "A Requirement with the given ID already exists. Entity String: " + content
	 * @throws WPISuiteException "Unable to save Requirement."
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(Session, String)
	 */
	public Requirement makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		
		// Parse the requirement from JSON
		final Requirement newRequirement;
		logger.log(Level.FINER, "Attempting new Requirement creation...");
		try {
			newRequirement = Requirement.fromJSON(content);
		} catch(JsonSyntaxException e){ // the JSON conversion failed
			logger.log(Level.WARNING, "Invalid Requirement entity creation string.");
			throw new BadRequestException("The Requirement creation string had invalid formatting. Entity String: " + content);			
		}
		
//		// Check to see if the requirement exists in the database already - check by ID only
//		if(getEntity(s,((Integer) newRequirement.getId()).toString())[0] != null){ //indicates it exists already
//			logger.log(Level.WARNING, "ID Conflict Exception during Requirement creation.");
//			throw new ConflictException("A Requirement with the given ID already exists. Entity String: " + content); 
//		}
		
		// Saves the requirement in the database
		this.save(s,newRequirement); // An exception may be thrown here if we can't save it
		
		// Return the newly created requirement (this gets passed back to the client)
		logger.log(Level.FINER, "Requirement creation success!");
		return newRequirement;
	}
		
	
	
	/** Saves the given Requirement into the database if possible.
	 * 
	 *  @param s The current user session
	 *  @param model The Requirement to be saved to the database
	 * 
	 * @throws WPISuiteException  "Unable to save Requirement."
	 */
	public void save(Session s, Requirement model) throws WPISuiteException {
		assignUniqueID(model); // Assigns a unique ID to the Req if necessary
		
		// Save the requirement in the database if possible, otherwise throw an exception
		// We want the requirement to be associated with the project the user logged in to
		if (!this.db.save(model, s.getProject())) {
			throw new WPISuiteException("Unable to save Requirement.");
		}
		logger.log(Level.FINE, "Requirement Saved :" + model);
	}

	
	
    /** Takes a Requirement and assigns a unique id if necessary
     * 
     * @param req The requirement that possibly needs a unique id
    
     * @throws WPISuiteException "Count failed"
     */
    public void assignUniqueID(Requirement req) throws WPISuiteException{
        if (req.getId() == -1){// -1 is a flag that says a unique id is needed            
            req.setId(Count() + 1); // Makes first Requirement have id = 1
        }        
    }

    
    
	/** Returns the number of Requirements currently in the database. Disregards
	 *  the current user session
	 * 
	 *  
	@return The number of Requirements currently in the database 
	 * @throws WPISuiteException "Retrieve all failed"
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	public int Count() throws WPISuiteException {
		// Passing a dummy Requirement lets the db know what type of object to retrieve
		return this.db.retrieveAll(new Requirement()).size();
	}

	
	
	/** Takes a session and returns an array of all the Requirements contained
	 * 
	 * @param s The current user session
	 * @return An array of all requirements in the Database	 
	 */
	public Requirement[] getAll(Session s)  {
		// Ask the database to retrieve all objects of the type Requirement.
		// Passing a dummy Requirement lets the db know what type of object to retrieve
		// Passing the project makes it only get requirements from that project
		// Return the list of requirements as an array
		return this.db.retrieveAll(new Requirement(), s.getProject()).toArray(new Requirement[0]);
	}	
	
	
	
	
	/**  For the current user session, Takes a specific id for a Requirement and returns it 
	 *   in an array.	
	 *  
	 *  @param s  The current user session
	 *  @param id Points to a specific requirement
	 *  
 	@return An array of Requirements  
 	 * @throws NotFoundException  "The Requirement with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database." 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String)
	 */
	public Requirement[] getEntity(Session s, String id) throws NotFoundException, WPISuiteException {
		
		final int intId = Integer.parseInt(id);
		if(intId < 1) {
			throw new NotFoundException("The Requirement with the specified id was not found:" + intId);
		}
		Requirement[] requirements = null;
		
		// Try to retrieve the specific Requirement
		try {
			requirements = db.retrieve(Requirement.class, "id", intId, s.getProject()).toArray(new Requirement[0]);
		} catch (WPISuiteException e) { // caught and re-thrown with a new message
			e.printStackTrace();
			throw new WPISuiteException("There was a problem retrieving from the database." );
		}
		
		// If a requirement was pulled, but has no content
		if(requirements.length < 1 || requirements[0] == null) {
			throw new NotFoundException("The Requirement with the specified id was not found:" + intId);
		}
		return requirements;
	}


	/**  Updates a Requirement already in the database
	 *   
	 *  @param s The current user session
	 *  @param content The requirement to be update + the updates
	 * 	
	@return the changed requirement 
	 * @throws NotFoundException  "The Requirement with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database."   or "Null session."	  
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	public Requirement update(Session s, String content) throws WPISuiteException {
		// If there is no session
		if(s == null){
			throw new WPISuiteException("Null session.");
		} 
		
		// Try to parse the message
		final Requirement reqUpdate;
		logger.log(Level.FINER, "Attempting to update a Requirement...");
		try {
			reqUpdate = Requirement.fromJSON(content);
		} catch(JsonSyntaxException e){ // the JSON conversion failed
			logger.log(Level.WARNING, "Invalid Requirement entity update string.");
			throw new BadRequestException("The Requirement update string had invalid formatting. Entity String: " + content);			
		}
		
		// Attempt to get the entity, NotFoundException or WPISuiteException may be thrown	    	
		Requirement oldReq = getEntity(s, Integer.toString(  reqUpdate.getId()  )  )[0];
		
		// Copy new field values into old Requirement. This is because the "same" model must
		// be saved back into the database
		oldReq.updateReq(reqUpdate);
		
		// If there are Notes, and the last one is new, set its User to the current User
		ArrayList<Note> notes = oldReq.getNotes();
		if (notes.size() > 0)
		{
			Note lastNote = notes.get(notes.size() - 1);
			User lastUser = lastNote.getUser();
			if (lastUser.getIdNum() == -1)
			{
				lastNote.setUser((User)db.retrieve(User.class, "username", s.getUsername()).get(0));
			}
		}
		// Attempt to save. WPISuiteException may be thrown
		this.save(s,oldReq);
				
		return oldReq;
		
	}
	
	
	
	
	
	
	/** Deletes a Requirement from the database (not advised)
	 *  
	 *  @param s The current user session
	 *  @param id The unique of the requirement to delete
	 *  
	@return TRUE if successful or FALSE if it fails
	 * @throws NotFoundException  "The Requirement with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database."   or "Null session."	  
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String)
	 */
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// Attempt to get the entity, NotFoundException or WPISuiteException may be thrown	    	
		Requirement oldReq = getEntity(s,   id    )[0];
		
	    if (this.db.delete(oldReq) == oldReq){
	    	return true; // the deletion was successful
	    }	    
		return false; // The deletion was unsuccessful
	}
	

	
	/** Deletes ALL Requirement from the database (not advised)
	 * 
	 *  @param s The current user session
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	public void deleteAll(Session s)  {
		this.db.deleteAll(new Requirement(), s.getProject());
	}
	

		
// Unimplemented Manager methods	
// Advanced Manager methods

	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		throw new NotImplementedException();
	}
	
	
	/**
	 * Method advancedPut.
	 * @param s Session
	 * @param args String[]
	 * @param content String
	  @return String
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	/**
	 * Method advancedPost.
	 * @param s Session
	 * @param string String
	 * @param content String
	 * @return String
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPost(Session, String, String)
	 */
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}


}
