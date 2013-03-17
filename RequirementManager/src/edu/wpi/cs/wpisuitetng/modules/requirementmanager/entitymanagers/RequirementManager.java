package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import java.util.List;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers.RequirementMessage;

/**This is the entity manager for the RequirementMessage in the 
 * RequirementManager module
 * 
 * @author Calder
 * @author Dabrowski
 *
 */
public class RequirementManager implements EntityManager<RequirementMessage> {
	/** The database */
	Data db;

	/** Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. 
	 * To make sure this happens, be sure to place add this entity 
	 * manager to the map in the ManagerLayer file.
	 * 
	 * @param db a reference to the persistent database
	 */	
	public RequirementManager(Data data) {
		db = data;
	}

		
	/** Takes an encoded Requirement(as a string) and converts it back to a 
	 *  Requirement and saves it in the database
	 *  
	 *	@param s The current user session
	 *	@param content The message that comes in the form of a string to be recreated
	 *	@return the RequirementMessage that originally came as a string
	 */
	public RequirementMessage makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {

		// Parse the message from JSON
		final RequirementMessage newRequirementMessage = RequirementMessage.fromJson(content);

		// Saves the message in the database if possible
		this.save(s,newRequirementMessage); 

		// Return the newly created message (this gets passed back to the client)
		return newRequirementMessage;
	}
		
	/** Takes a session and returns an array of all the Requirements contained
	 * 
	 * @param s The current user session
	 * @return An array of all requirements in the Database	 * 
	 */
	public RequirementMessage[] getAll(Session s) throws WPISuiteException {
		// Ask the database to retrieve all objects of the type RequirementMessage.
		// Passing a dummy RequirementMessage lets the db know what type of object to retrieve
		// Passing the project makes it only get messages from that project
		List<Model> messages = db.retrieveAll(new RequirementMessage(), s.getProject());

		// Return the list of messages as an array
		return messages.toArray(new RequirementMessage[0]);
	}
	
	
	/** Saves the given RequirementMessage into the database if possible.
	 * 
	 *  @param s The current user session
	 *  @param model The RequirementMessage to be saved to the database
	 * 
	 */
	public void save(Session s, RequirementMessage model) throws WPISuiteException {
		// Save the message in the database if possible, otherwise throw an exception
		// We want the message to be associated with the project the user logged in to
		if (!db.save(model, s.getProject())) {
			throw new WPISuiteException();
		}
	}


	/** Returns the number of RequirementMessages currently in the database. Disregards
	 *  the current user session
	 * 
	 *  @return The number of RequirementMessages currently in the databse
	 */
	public int Count() throws WPISuiteException {
		// Passing a dummy RequirementMessage lets the db know what type of object to retrieve
		return db.retrieveAll(new RequirementMessage()).size();
	}


// Unimplemented Manager methods	
	
	
	/**  For the current user session, Takes a specific id for a Requirement and returns it 
	 *   in an array.	
	 *  
	 *  @param s  The current user session
	 *  @param id Points to a specific requirement
	 *  @return An array of Requirements 
 	 */
	public RequirementMessage[] getEntity(Session s, String id) throws NotFoundException, WPISuiteException {
		// TODO Auto-generated method stub
		
		// Throw an exception if an ID was specified but not found
		//throw new NotFoundException();
		return null;
	}
	
	
	
	// TODO    figure out how we might update a Requirement..
	// Hint: The "content" argument will hold a unique ID for the requirement to change
	/**  Updates a Requirement already in the database
	 *   
	 *  @param s The current user session
	 *  @param content The requirement to be update + the updates
	 * 	@return the changed requirement message
	 */
	public RequirementMessage update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/** Deletes a RequirementMessage from the database (not advised)
	 * 
	 *  @param s The current user session
	 *  @param id The unique of the message to delete
	 *  @return TRUE if successful or FALSE if it fails
	 */
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub

		// Not implemented- Throw exception
		throw new WPISuiteException();
	}
	

	/** Deletes ALL RequirementMessage from the database (not advised)
	 * 
	 *  @param s The current user session
	 */
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		
		// Not implemented- Throw exception
		throw new WPISuiteException();
	}
	

	
// Advanced Manager methods
	/** 
	 * 
	 *  @param s The current user session
	 *  @param args 
	 *  @return 
	 */
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public String advancedPut(Session s, String[] args, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String advancedPost(Session s, String string, String content)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}


}
