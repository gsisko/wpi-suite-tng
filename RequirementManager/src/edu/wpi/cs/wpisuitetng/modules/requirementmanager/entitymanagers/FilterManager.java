package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.database.Data;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.EntityManager;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

/** This is the entity manager for filters in the RequirementManager module
 * @author Team 5
 *
 */
public class FilterManager implements EntityManager<Filter> {
	/** The database */
	@SuppressWarnings("unused")
	private Data db;
	
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
		this.db = data;
	}
	
    /** Takes a filter and assigns a unique id if necessary
     * 
     * @param req The requirement that possibly needs a unique id
    
     * @throws WPISuiteException If there are no Requirements in the database */
    public void assignUniqueID(Requirement req, Project p) throws WPISuiteException{
        if (req.getId() == -1){// -1 is a flag that says a unique id is needed            
            req.setId(Count() + 1); // Makes first Requirement have id = 1
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
	public Filter[] getAll(Session s) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Filter update(Session s, String content) throws WPISuiteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public boolean deleteEntity(Session s, String id) throws WPISuiteException {
		// TODO Auto-generated method stub
		return false;
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
}
