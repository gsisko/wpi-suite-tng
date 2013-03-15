package edu.wpi.cs.wpisuitetng.modules.requirementmanager.model;

public class Epic extends Requirement {
	
	private String name;
	private String description;

	public Epic(RequirementPriority priority, String name, String description) {
		super(priority);
		this.setName(name);
		this.setDescription(description);
		// TODO Auto-generated constructor stub
	}
	
	public Epic(RequirementPriority priority, int releaseNumber, String name, String description) {
		super(priority);this.setName(name);
		this.setDescription(description);
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
