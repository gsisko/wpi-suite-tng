package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import java.util.List;
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
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;

/** This is the entity manager for filters in the RequirementManager module
 * @author Team 5
 *
 */
public class FilterManager implements EntityManager<Filter> {
	/** The database */
	private Data db;
	/** Current implementation of unique ID's for filters  */
	private int nextFilterId;
	
	/** This is for advanced logging and debugging of the server interactions */
	private static final Logger logger = Logger.getLogger(FilterManager.class.getName());
	
	/** Constructs the entity manager. This constructor is called by
	 * {@link edu.wpi.cs.wpisuitetng.ManagerLayer#ManagerLayer()}. 
	 * To make sure this happens, be sure to place add this entity 
	 * manager to the map in the ManagerLayer file.
	 * 
	 * Expects that the data passed is valid and does no error checking!
	 *
	 * @param data Database in the core
	 */		
	public FilterManager(Data data){
		this.setDb(data);
		this.setNextFilterId(1);
	}
	
 

	/** Takes a filter and assigns a unique id if necessary
     * 
     * @param req The filter that possibly needs a unique id
     */
    public void assignUniqueID(Filter filter){
        if (filter.getUniqueID() == -1){// -1 is a flag that says a unique id is needed            
            filter.setUniqueID(this.getNextFilterId()); // Makes first Filter have id = 1
            this.setNextFilterId(this.getNextFilterId() + 1);
        }        
    }
	
	
	/** Returns the number of filters currently in the database. Counts only those 
	 * filters specific to the current user
	 * 
	 *  
	@return The number of Requirements currently in the database 
	 * @throws NotImplementedException
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#Count()
	 */
	public int Count() throws WPISuiteException {
		throw new NotImplementedException();
	}
	
	/** Saves the given Filter into the database if possible.
	 * 
	 *  @param s The current user session
	 *  @param model The Filter to be saved to the database
	 * 
	 * @throws WPISuiteException  "Unable to save Filter."
	 */
	public void save(Session s, Filter model) throws WPISuiteException {
		assignUniqueID(model); // Assigns a unique ID to the Req if necessary
		
		// Save the filter in the database if possible, otherwise throw an exception
		// We DON'T want the filter to be associated with any project
		if (!this.db.save(model)) {
			throw new WPISuiteException("Unable to save Filter.");
		}
		logger.log(Level.FINE, "Filtert Saved :" + model);
	}
	
	
	/** Takes an encoded Filter(as a string) and converts it back to a 
	 *  Requirement and saves it in the database
	 *  
	 *	@param s The current user session
	 *	@param content The filter that comes in the form of a string to be recreated
	 *	
	@return the Requirement that originally came as a string
	 * @throws BadRequestException "The Filter creation string had invalid formatting. Entity String: " + content
	 * @throws ConflictException "A filter with the given ID already exists. Entity String: " + content
	 * @throws WPISuiteException "Unable to save Requirement."
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#makeEntity(Session, String)
	 */
	public Filter makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// Parse the requirement from JSON
		final Filter newFilter;
		logger.log(Level.FINER, "Attempting new Filter creation...");
		try {
			newFilter = Filter.fromJSON(content);
		} catch(JsonSyntaxException e){ // the JSON conversion failed
			logger.log(Level.WARNING, "Invalid Filter entity creation string.");
			throw new BadRequestException("The Filter creation string had invalid formatting. Entity String: " + content);			
		}
		
		// Check to see if the requirement exists in the database already - check by ID only
		if(getEntity(s,((Integer) newFilter.getUniqueID()).toString())[0] != null){ //indicates it exists already
			logger.log(Level.WARNING, "ID Conflict Exception during Filter creation.");
			throw new ConflictException("A Filter with the given ID already exists. Entity String: " + content); 
		}
		
		// Saves the requirement in the database
		this.save(s,newFilter); // An exception may be thrown here if we can't save it
		
		// Return the newly created requirement (this gets passed back to the client)
		logger.log(Level.FINER, "Filter creation success!");
		return newFilter;
	}




	/**  For the current user session, Takes a specific id for a Filter and returns it 
	 *   in an array. 
	 *  
	 *  @param s  The current user session
	 *  @param id Points to a specific Filter
	 *  
 	@return An array of Requirements  
 	 * @throws NotFoundException  "The Filter with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database." 
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#getEntity(Session, String)
	 */
	public Filter[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		final int intId = Integer.parseInt(id);
	
		if(intId < 1) {
			throw new NotFoundException("The Filter with the specified id was not found:" + intId);
		}
		Filter[] filters = null;
		
		// Try to retrieve the specific Filter
		try {
			filters = db.retrieve(Filter.class, "id", intId, s.getProject()).toArray(new Filter[0]);
		} catch (WPISuiteException e) { // caught and re-thrown with a new message
			e.printStackTrace();
			throw new WPISuiteException("There was a problem retrieving from the database." );
		}
		
		// If a filter was pulled, but has no content
		if(filters.length < 1 || filters[0] == null) {
			throw new NotFoundException("The Filter with the specified id was not found:" + intId);
		}
		return filters;
	}


	/**  Updates a Filter already in the database
	 *   
	 *  @param s The current user session
	 *  @param content The filter to be update + the updates
	 * 	
	@return the changed Filter
	 * @throws NotFoundException  "The Filter with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database."   or "Null session."	  
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#update(Session, String)
	 */
	public Filter update(Session s, String content) throws WPISuiteException {
		// If there is no session
		if(s == null){
			throw new WPISuiteException("Null session.");
		} 
		
		// Try to parse the message
		final Filter filterUpdate;
		logger.log(Level.FINER, "Attempting to update a Filter...");
		try {
			filterUpdate = Filter.fromJSON(content);
		} catch(JsonSyntaxException e){ // the JSON conversion failed
			logger.log(Level.WARNING, "Invalid Requirement entity update string.");
			throw new BadRequestException("The Requirement update string had invalid formatting. Entity String: " + content);			
		}
		
		// Attempt to get the entity, NotFoundException or WPISuiteException may be thrown	    	
		Filter oldFilter = getEntity(s, Integer.toString(  filterUpdate.getUniqueID()  )  )[0];
		
		// Copy new field values into old Requirement. This is because the "same" model must
		// be saved back into the database
		oldFilter.updateFilter(filterUpdate);
		
		// Attempt to save. WPISuiteException may be thrown
		this.save(s,oldFilter);
				
		return oldFilter;
		
	}

	
	
	
	/** Deletes a Filter from the database. Allowed here because Filters are trivial
	 *  
	 *  @param s The current user session
	 *  @param id The unique of the filter to delete
	 *  
	@return TRUE if successful or FALSE if it fails
	 * @throws NotFoundException  "The Filter with the specified id was not found:" + intId
	 * @throws WPISuiteException  "There was a problem retrieving from the database."   or "Null session."	  
	 * @see edu.wpi.cs.wpisuitetng.modules.EntityManager#deleteEntity(Session, String)
	 */
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// Attempt to get the entity, NotFoundException or WPISuiteException may be thrown	    	
		Filter oldFilter = getEntity(s,   id    )[0];
		
	    if (this.db.delete(oldFilter) == oldFilter){
	    	return true; // the deletion was successful
	    }	    
		return false; // The deletion was unsuccessful
	}



	/** Get's all Filters made by the current user
	 * 
	 * @param s The current session. The current user is extracted from this.
	 * @return All of the Filters made by the current user.
	 * @throws WPISuiteException -- thrown if there are problems retrieving
	 */
	public Filter[] getAll(Session s) throws WPISuiteException {
		 List<Model> filterList =  this.db.retrieve(Filter.class, "user", s.getUser());
		 filterList.size();
		 return filterList.toArray(new Filter[filterList.size()]);
	}



	/** Delete all Filters made by the current user 
	 * 
	 * @param s The current session. The current user is extracted from this.
	 * @throws WPISuiteException -- thrown when there are problems deleting
	 */
	public void deleteAll(Session s) throws WPISuiteException {
		Filter[] filtersToDelete= this.getAll(s);
		for (Filter ftd: filtersToDelete){
			this.deleteEntity(s,Integer.toString(  ftd.getUniqueID()  ) );
		}
	}
	
	
	
	
// Not going to be implemented until hell freezes over	
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

	
	@Override
	public String advancedGet(Session s, String[] args)
			throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	private void setDb(Data data) {
		// TODO Auto-generated method stub
	}

	/**
	 * @return the nextFilterId
	 */
	public int getNextFilterId() {
		return nextFilterId;
	}

	/**
	 * @param nextFilterId the nextFilterId to set
	 */
	public void setNextFilterId(int nextFilterId) {
		this.nextFilterId = nextFilterId;
	}
}
