package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;

/** Requirement: Holds data that makes up a Requirement
 * 
 * @author Team 5
 * @version $Revision: 1.0 $
 */
public class Requirement extends AbstractModel {
	/** Unique ID of the record- assigned by entity manager  */
	private int id;           
	/** The name of the Requirement (100 chars)    	  */
	private String name;         
	/** A description of the Requirement     */
	private String description;    
	/** the type of the requirement **/
	private RequirementType type;
	/** The status in the work flow- Default to NEW   */
	private RequirementStatus status;     
	/** The priority set to the Requirement  */
	private RequirementPriority priority;  
	/** Must be a release number of the current project ***/
	private int releaseNumber;    
	/** An estimate of what this Requirement will take  */
	private int estimate;         
	/** The actual effort it took for this Requirement  */
	private int actualEffort;    
	
	// blank constructor
	public Requirement(){
		new Requirement("","",RequirementType.NoType, RequirementPriority.NoPriority,0);
	}
	
	
	/**
	 * Constructor for Requirement.
	 * @param name String 
	 * @param description String
	 * @param releaseNumber int
	 * @param priority RequirementPriority
	 * @param estimate int
	 */
	public Requirement(String name, String description, RequirementType type, RequirementPriority priority, int releaseNumber) {
		this.setName(name);
		this.setDescription(description);
		this.setType(type);
		this.setPriority(priority); // Initialize priority
		this.setReleaseNumber(releaseNumber); // release number of current project
		
		// The rest are default values
		this.setEstimate(0);
		this.setActualEffort(0);			// Initial actual effort set to zero
		this.setStatus(RequirementStatus.New);		// Initial status should be set to NEW
		this.setId(-1); // (-1) will be a flag to the server/database that this value needs to be set
	}
	
	
	


	
	// The following functions come from the Model interface
	/**
	 * Method save.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#save()
	 */
	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Method delete.
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#delete()
	 */
	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Converts this Epic to a JSON string for sending accross the network
	
	
	 * @return a string in JSON representing this Epic * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON() * @see edu.wpi.cs.wpisuitetng.modules.Model#toJSON()
	 */
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Requirement.class);
		return json;
	}

	/**
	 * Method identify.
	 * @param o Object
	
	
	 * @return Boolean * @see edu.wpi.cs.wpisuitetng.modules.Model#identify(Object) */
	public Boolean identify(Object o) {
		Boolean returnValue = false;
		if(o instanceof Requirement && id == ((Requirement) o).getId()) {
			returnValue = true;
		}	
		if(o instanceof String && Integer.toString(id).equals(o)) {
			returnValue = true;
		}
		return returnValue;
	}
	
	// End of Model interface functions
	
	
	/**
	 * Converts the given list of Requirements to a JSON string
	 * @param rlist a list of Requirements
	
	 * @return a string in JSON representing the list of Requirements */
	public static String toJSON(Requirement[] rlist) {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(rlist, Requirement.class);
		return json;
	}
	
	/**
	 * Method toString. Current outputs a JSON string
	
	
	 * @return String * @see edu.wpi.cs.wpisuitetng.modules.Model#toString() */
	@Override
	public String toString() {
		return this.toJSON();
	}
	
	/**
	 * @param json Json string to parse containing Defect
	
	 * @return The Defect given by json */
	public static Requirement fromJSON(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement.class);
	}
	
	/**
	 * @param json Json string to parse containing Requirement array
	
	 * @return The Requirement array given by json */
	public static Requirement[] fromJSONArray(String json) {
		GsonBuilder builder = new GsonBuilder();
		addGsonDependencies(builder);
		return builder.create().fromJson(json, Requirement[].class);
	}
	
	/**
	 * Add dependencies necessary for Gson to interact with this class
	 * @param builder Builder to modify
	 */
	public static void addGsonDependencies(GsonBuilder builder) {
		RequirementEvent.addGsonDependencies(builder);
	}
	
	/**
	 * Method setProject.
	 * @param project Project
	
	 * @see edu.wpi.cs.wpisuitetng.modules.Model#setProject(Project) */
	@Override
	public void setProject(Project project) {
		super.setProject(project);
	}

	// The following are getters and setters
	
	/**
	
	 * @return the id */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}	
	
	/**
	
	 * @return the actualEffort */
	public int getActualEffort() {
		return actualEffort;
	}

	/**
	
	 * @param actualEffort int
	 */
	public void setActualEffort(int actualEffort) {
		this.actualEffort = actualEffort;
	}

	/**
	
	 * @return the estimate */
	public int getEstimate() {
		return estimate;
	}

	/**
	 * @param estimate the estimate to set
	 */
	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	/**
	
	 * @return the description */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	
	 * @return the name */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	
	 * @return the releaseNumber */
	public int getReleaseNumber() {
		return releaseNumber;
	}

	/**
	 * @param releaseNumber the releaseNumber to set
	 */
	public void setReleaseNumber(int releaseNumber) {
		this.releaseNumber = releaseNumber;
	}

	/**
	
	 * @return the status */
	public RequirementStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(RequirementStatus status) {
		this.status = status;
	}

	/**
	
	 * @return the priority */
	public RequirementPriority getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(RequirementPriority priority) {
		this.priority = priority;
	}
	
	/** Changes all fields in the current Requirement to equal the fields of the reqUpdate
	 * 
	 * @param reqUpdate Requirement holding the updates
	 */
	public void updateReq(Requirement reqUpdate) {
		setReleaseNumber(reqUpdate.getReleaseNumber());
		setStatus(reqUpdate.getStatus());
		setPriority(reqUpdate.getPriority());
		setName(reqUpdate.getName());
		setDescription(reqUpdate.getDescription());
		setEstimate(reqUpdate.getEstimate());
		setActualEffort(reqUpdate.getActualEffort());
		setType(reqUpdate.getType());
	}


	/**
	 * @return the type
	 */
	public RequirementType getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(RequirementType type) {
		this.type = type;
	}

}
