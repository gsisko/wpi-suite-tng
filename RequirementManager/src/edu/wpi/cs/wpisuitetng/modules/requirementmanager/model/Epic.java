package edu.wpi.cs.wpisuitetng.modules.requirementmanager.model;


public class Epic extends Requirement {
	
	private String name;
	private String description;
	private int uniqueID; // We need to discuss how to implement this.. possibly migrate to requirement... probably controlled on server end
	
	
	public Epic(RequirementPriority priority, String name, String description) {
		super(priority);
		this.setUniqueID(-1); // (-1) will be a flag to the server/database that this value needs to be set
		this.setName(name);
		this.setDescription(description);
		// TODO Auto-generated constructor stub
	}
	
	public Epic(RequirementPriority priority, int releaseNumber, String name, String description) {
		super(priority);this.setName(name);
		this.setDescription(description);
		this.setUniqueID(-1);    // (-1) will be a flag to the server/database that this value needs to be set
		// TODO Auto-generated constructor stub
	}

	@Override
	public void save() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean identify(Object o) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 *  
	 * @return The name of this epic
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name The name of this epic
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return The description of this epic
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description The description of this epic
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return The unique identifier for this Epic
	 */
	public int getUniqueID() {
		return uniqueID;
	}

	/**
	 * 
	 * @param uniqueID The unique identifier for this Epic
	 */
	public void setUniqueID(int uniqueID) {
		this.uniqueID = uniqueID;
	}

}
