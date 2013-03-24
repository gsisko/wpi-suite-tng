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
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
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
     * @param req The requirement that possibly needs a unique id
     */
    public void assignUniqueID(Filter filter, Project p){
        if (filter.getUniqueID() == -1){// -1 is a flag that says a unique id is needed            
            filter.setUniqueID(this.getNextFilterId()); // Makes first Filter have id = 1
            this.setNextFilterId(this.getNextFilterId() + 1);
        }        
    }
	
	
	@Override
	public int Count() throws WPISuiteException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	@Override
	public void save(Session s, Filter model) throws WPISuiteException {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public Filter makeEntity(Session s, String content)
			throws BadRequestException, ConflictException, WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}





	@Override
	public Filter[] getEntity(Session s, String id) throws NotFoundException,
			WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Filter update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	
// Rob will do from here down	
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
	}



	// Leave getAll to rob
	@Override
	public Filter[] getAll(Session s) throws WPISuiteException {
		 List<Model> filterList =  this.db.retrieve(Filter.class, "user", s.getUser());
		 filterList.size();
		 return filterList.toArray(new Filter[filterList.size()]);
	}



	
	@Override
	public void deleteAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		
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

	
	/**
	 * @return the db
	 */
	public Data getDb() {
		return db;
	}

	/**
	 * @param db the db to set
	 */
	public void setDb(Data db) {
		this.db = db;
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
