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

import java.util.Date;
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

/** This is the entity manager for iterations in the RequirementManager module. The provided
 *  methods include functionality for creating, updating, getting specific iterations, getting
 *  all iterations and deleting iterations. Currently, iterations are project specific, so
 *  iterations pulled from the DB will only be for the current current project. 
 *  "Deleting" simply sets the project field of iterations to null so that the iteration cannot be
 *  pulled from the DB, but it will still exist to preserve unique IDs. 
 *  
 *   Also, the first time iterations are asked for in a new project, a single "Backlog" iteration
 *   will be created, stored and included with the other iterations requested.   
 */
public class IterationManager implements EntityManager<Iteration> {
	/** The database */
	private Data db;

	/** This logger is for advanced logging and debugging of the server interactions */
	private static final Logger logger = Logger.getLogger("Iteration manager logger");

	/** Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. 
	 * To make sure this happens, be sure to place add this entity 
	 * manager to the map in the ManagerLayer file.
	 * 
	 * NOTE: This Expects that the data passed is valid and does no error checking!
	 *
	 * @param data Database in the core
	 */	
	public IterationManager(Data data) {
		db = data;
	}

	/** Checks the database to make sure there is a "Backlog" of ID 0 for 
	 *  the current project. Makes one if necessary.
	 *  Backlog has start and end date of 1ms after the epoch.
	 *  
	 *  This is designed to be called by getall 
	 *  
	 *  @param s The current session which contains the current project  
	 *	@see edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.IterationManager#getAll(Session s)
	 */
	public void instantiateBacklog(Session s){		
		// Get the current project
		Project currentProject = s.getProject();
		
		try{
			Iteration[] iterations = getEntity( s, "0");
			if (iterations.length == 1) 
				return; // There is a backlog already
			else
				System.err.println("There is more than one backlog!");
				return;
		// Get entity throws an exception when it can't find things
		} catch (WPISuiteException wse){
			// Make the backlog
			Iteration model = new Iteration("", new Date(1), new Date(1));
			model.setID(0);
			model.setProject(currentProject);
			// Save the backlog in the database if possible, otherwise throw an exception
			// We want the iteration to be associated with the project the user logged in to
			if (!db.save(model,currentProject)) {
				logger.log(Level.WARNING, "A backlog was not created properly.");
			}
		}	
	}	

	/** Takes an encoded Iteration(as a string) and converts it back to a 
	 *  Iteration and saves it in the database
	 *  
	 * @param s The current user session
	 * @param content The iteration that comes in the form of a string to be recreated
	 * @return the Iteration that originally came as a string
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
	 * @throws WPISuiteException  "Unable to save Iteration."
	 */
	public void save(Session s, Iteration model) throws WPISuiteException {
		// Save the iteration in the database if possible, otherwise throw an exception
		// We want the iteration to be associated with the project the user logged in to
		if (!db.save(model, s.getProject())) {
			throw new WPISuiteException("Unable to save Iteration.");
		}

		logger.log(Level.FINE, "Iteration Saved :" + model);
	}

	/** Takes an Iteration and assigns a unique id if necessary
	 * 
	 * @param iter The iteration that possibly needs a unique id
	 * @throws WPISuiteException "Count failed" 
	 */
	public void assignUniqueID(Iteration iter) throws WPISuiteException{
		if (iter.getID() == -1){// -1 is a flag that says a unique id is needed            
			iter.setID(Count() + 1); // Makes first Iteration for a have id = 1
		}        
	}

	/** Returns the number of Iterations currently in the database. Disregards
	 *  the current user session
	 * @return The number of Iterations currently in the database
	 * @throws WPISuiteException "Retrieve all failed"@see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	public int Count() throws WPISuiteException {
		// Passing a dummy Iteration lets the db know what type of object to retrieve
		return db.retrieveAll(new Iteration()).size();
	}

	/** Takes a session and returns an array of all the Iterations contained
	 * 
	 * @param s The current user session
	 * @return An array of all iterations in the Database
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getAll(Session)
	 */
	public Iteration[] getAll(Session s)  {
		// Assure that the current project has a back log before retrieving all. 
		instantiateBacklog(s);

		// Ask the database to retrieve all objects of the type Iteration.
		// Passing a dummy Iteration lets the db know what type of object to retrieve
		// Passing the project makes it only get iterations from that project
		// Return the list of iterations as an array
		return db.retrieveAll(new Iteration(), s.getProject()).toArray(new Iteration[0]);
	}	

	/**  For the current user session, takes a specific id for a Iteration and returns it 
	 *   in an array.	
	 *  
	 *  @param s  The current user session
	 *  @param sid String representation of id of desired iteration
	 *  @return An array of Iterations 
	 * 	@throws NotFoundException  "The Iteration with the specified id was not found:" + intId
	 * 	@throws WPISuiteException  "There was a problem retrieving from the database."
	 * 	@see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String)
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
	 *  @return the changed iteration 
	 * 	@throws WPISuiteException  "There was a problem retrieving from the database."   or "Null session."
	 *	@throws NotFoundException  "The Iteration with the specified id was not found:" + intId
	 * 	@see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
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

	/** "Deletes" a Iteration from the database (makes it effectively hidden). Backlogs cannot be deleted.
	 *  
	 *  @param s The current user session
	 *  @param id The unique of the iteration to delete
	 *  @return TRUE if successful or FALSE if it fails
	 *	@throws WPISuiteException  "There was a problem retrieving from the database."   or "Null session."
	 *	@throws NotFoundException  "The Iteration with the specified id was not found:" + intId
	 *	@see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String)
	 */
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// Attempt to get the entity, NotFoundException or WPISuiteException may be thrown	    	
		Iteration oldIteration = getEntity(s, id)[0];
		if (id.equals("0")){ // ID of 0 = backlog
			return false;
		}

		// Set Project to null to a different user so that it is not pulled
		// out by "getAll" calls... "effectively deleted"
		oldIteration.setProject(null); 

		// Attempt to save. WPISuiteException may be thrown
		db.save( oldIteration);

		return true; // The deletion was successful
	}

	/** Deletes ALL Iterations from the database permanently
	 * 
	 *  @param s The current user session
	 * 	@see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteAll(Session) 
	 */
	public void deleteAll(Session s)  {
		db.deleteAll(new Iteration(), s.getProject());
	}

	//The following methods are not implemented but required by the "EntityManager" interface:
	
	/** Method advancedPut. (Not implemented but required by the "EntityManager" interface)
	 * @param s Session
	 * @param args String[]
	 * @return String
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	/**Method advancedPut. (Not implemented but required by the "EntityManager" interface)
	 * @param s Session
	 * @param args String[]
	 * @param content String
	 * @return String
	 * @throws WPISuiteException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#advancedPut(Session, String[], String)
	 */
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		throw new NotImplementedException();
	}

	/**Method advancedPost. (Not implemented but required by the "EntityManager" interface)
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
