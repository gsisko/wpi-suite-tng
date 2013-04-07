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
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;

/**This is the entity manager for the Iteration in the IterationManager module
 * 
 * @author Team 5
 *
 * @version $Revision: 1.0 $
 */
public class IterationManager implements EntityManager<Iteration> {
	/** The database */
	private Data db;
	
	/** This is for advanced logging and debugging of the server interactions */
	private static final Logger logger = Logger.getLogger("Iteration manager logger");
	
	/** Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. 
	 * To make sure this happens, be sure to place add this entity 
	 * manager to the map in the ManagerLayer file.
	 * 
	 * Expects that the data passed is valid and does no error checking!
	 *
	 * @param data Database in the core
	 */	
	public IterationManager(Data data) {
		this.db = data;
		instantiateBacklogs();
	}


	/** Checks the database to make sure there is a "Backlog" of ID 0 for 
	 *  each project. Makes one if necessary for each project.        */
	private void instantiateBacklogs() {
		Project[] projectsThatNeedBacklogs = new Project[1];
		projectsThatNeedBacklogs = db.retrieveAll(new Project("","")).toArray(projectsThatNeedBacklogs);

		// Go through the array of projects, and make sure each has a backlog
		// If it doesn't have a backlog, make one!
		for (int i = 0; i < projectsThatNeedBacklogs.length; i++){
			boolean backlogExistsFlag = false;
			// Get all Iterations for the current project
			Iteration[] iterations = new Iteration[1];
			iterations = this.db.retrieveAll(Iteration.class, projectsThatNeedBacklogs[i]).toArray(new Iteration[0]);
			// Figure out if we actually have to check that there isn't a backlog 
			if (iterations.length > 0){ 
				// Check each iteration to see if it is the backlog
				for (int j= 0; i< iterations.length; i++){
					// Check the current iteration's ID to see if it is the backlog
					if ((iterations[j]).getID()== 0){
						backlogExistsFlag = true;
					}
				}
			}
			// If a backlog wasn't found, make one for the current project
			if (!backlogExistsFlag){
				Iteration model = new Iteration("Backlog", null, null);
				model.setID(0);
				model.setProject(projectsThatNeedBacklogs[i]);
				// Save the backlog in the database if possible, otherwise throw an exception
				// We want the iteration to be associated with the project the user logged in to
				if (!this.db.save(model, projectsThatNeedBacklogs[i])) {
					logger.log(Level.WARNING, "A backlog was not created properly.");
				}
			}
		}
		logger.log(Level.FINE, "Backlog creation successful");
	}

	
		
	/** Takes an encoded Iteration(as a string) and converts it back to a 
	 *  Iteration and saves it in the database
	 *  
	 *	@param s The current user session
	 *	@param content The iteration that comes in the form of a string to be recreated
	 *	
	@return the Iteration that originally came as a string
	 * @throws BadRequestException "The Iteration creation string had invalid formatting. Entity String: " + content
	 * @throws ConflictException "A Iteration with the given ID already exists. Entity String: " + content
	 * @throws WPISuiteException "Unable to save Iteration."
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(Session, String)
	 */
	public Iteration makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		
		// Parse the iteration from JSON
		final Iteration newIteration;
		logger.log(Level.FINER, "Attempting new Iteration creation...");
		try {
			newIteration = Iteration.fromJSON(content);
		} catch(JsonSyntaxException e){ // the JSON conversion failed
			logger.log(Level.WARNING, "Invalid Iteration entity creation string.");
			throw new BadRequestException("The Iteration creation string had invalid formatting. Entity String: " + content);			
		}
		
		// If the new iteration has an ID already, then it shouldn't be here
		if (newIteration.getID() != -1){
			throw new ConflictException("Cannot make a new entity that has an ID already.");
		}
				
		// Assign a unique ID
		assignUniqueID(newIteration);		
		
		// Saves the iteration in the database
		this.save(s,newIteration); // An exception may be thrown here if we can't save it
		
		// Return the newly created iteration (this gets passed back to the client)
		logger.log(Level.FINER, "Iteration creation success!");
		return newIteration;
	}
		
	
	
	/** Saves the given Iteration into the database if possible.
	 * 
	 *  @param s The current user session
	 *  @param model The Iteration to be saved to the database
	 * 
	 * @throws WPISuiteException  "Unable to save Iteration."
	 */
	public void save(Session s, Iteration model) throws WPISuiteException {
		// Save the iteration in the database if possible, otherwise throw an exception
		// We want the iteration to be associated with the project the user logged in to
		if (!this.db.save(model, s.getProject())) {
			throw new WPISuiteException("Unable to save Iteration.");
		}

		logger.log(Level.FINE, "Iteration Saved :" + model);
	}
    
    
    /** Takes an Iteration and assigns a unique id if necessary
     * 
     * @param req The requirement that possibly needs a unique id
    
     * @throws WPISuiteException "Count failed"
     */
    public void assignUniqueID(Iteration iter) throws WPISuiteException{
        if (iter.getID() == -1){// -1 is a flag that says a unique id is needed            
        	iter.setID(Count() + 1); // Makes first Iteration for a have id = 1
        }        
    }
	
	
	/** Returns the number of Iterations currently in the database. Disregards
	 *  the current user session
	 * 
	 *  
	@return The number of Iterations currently in the database 
	 * @throws WPISuiteException "Retrieve all failed"
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	public int Count() throws WPISuiteException {
		// Passing a dummy Iteration lets the db know what type of object to retrieve
		return this.db.retrieveAll(new Iteration()).size();
	}

	
	
	/** Takes a session and returns an array of all the Iterations contained
	 * 
	 * @param s The current user session
	 * @return An array of all iterations in the Database	 
	 */
	public Iteration[] getAll(Session s)  {
		// Ask the database to retrieve all objects of the type Iteration.
		// Passing a dummy Iteration lets the db know what type of object to retrieve
		// Passing the project makes it only get iterations from that project
		// Return the list of iterations as an array
		return this.db.retrieveAll(new Iteration(), s.getProject()).toArray(new Iteration[0]);
	}	
	
	
	
	
	/**  For the current user session, Takes a specific id for a Iteration and returns it 
	 *   in an array.	
	 *  
	 *  @param s  The current user session
	 *  @param id Points to a specific iteration
	 *  
 	@return An array of Iterations  
 	 * @throws NotFoundException  "The Iteration with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database." 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String)
	 */
	public Iteration[] getEntity(Session s, String sid) throws NotFoundException, WPISuiteException {
		
		Iteration[] iterations = null;
		int id = Integer.parseInt(sid);
		
		
		// Try to retrieve the specific Iteration
		try {
			iterations = db.retrieve(Iteration.class, "id", id, s.getProject()).toArray(new Iteration[0]);
		} catch (WPISuiteException e) { // caught and re-thrown with a new message
			e.printStackTrace();
			throw new WPISuiteException("There was a problem retrieving from the database." );
		}
		
		// If a iteration was pulled, but has no content
		if(iterations.length < 1 || iterations[0] == null) {
			throw new NotFoundException("The Iteration with the specified name was not found:" + id);
		}
		return iterations;
	}


	/**  Updates a Iteration already in the database
	 *   
	 *  @param s The current user session
	 *  @param content The iteration to be update + the updates
	 * 	
	@return the changed iteration 
	 * @throws NotFoundException  "The Iteration with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database."   or "Null session."	  
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	public Iteration update(Session s, String content) throws WPISuiteException {
		// If there is no session
		if(s == null){
			throw new WPISuiteException("Null session.");
		} 
		
		// Try to parse the message
		final Iteration iterationUpdate;
		logger.log(Level.FINER, "Attempting to update a Iteration...");
		try {
			iterationUpdate = Iteration.fromJSON(content);
		} catch(JsonSyntaxException e){ // the JSON conversion failed
			logger.log(Level.WARNING, "Invalid Iteration entity update string.");
			throw new BadRequestException("The Iteration update string had invalid formatting. Entity String: " + content);			
		}
		
		// Attempt to get the entity, NotFoundException or WPISuiteException may be thrown	    	
		Iteration oldIteration = getEntity(s, ((Integer)iterationUpdate.getID()).toString() )[0];
		
		// Copy new field values into old Iteration. This is because the "same" model must
		// be saved back into the database
		oldIteration.updateIteration(iterationUpdate);
		
		// Attempt to save. WPISuiteException may be thrown
		this.save(s,oldIteration);
				
		return oldIteration;
		
	}
	
	
	
	/** Deletes a Iteration from the database (not advised)
	 *  
	 *  @param s The current user session
	 *  @param id The unique of the iteration to delete
	 *  
	@return TRUE if successful or FALSE if it fails
	 * @throws NotFoundException  "The Iteration with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database."   or "Null session."	  
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String)
	 */
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// Attempt to get the entity, NotFoundException or WPISuiteException may be thrown	    	
		Iteration oldIteration = getEntity(s, id)[0];
		if (id.equals("0")){ // ID of 0 = backlog
			return false;
		}
		
		
	    if (this.db.delete(oldIteration) == oldIteration){
	    	return true; // the deletion was successful
	    }	    
		return false; // The deletion was unsuccessful
	}
	

	
	/** Deletes ALL Iteration from the database (not advised)
	 * 
	 *  @param s The current user session
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session)
	 */
	public void deleteAll(Session s)  {
		this.db.deleteAll(new Iteration(), s.getProject());
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
