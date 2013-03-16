package edu.wpi.cs.wpisuitetng.modules.requirementmanager.model;

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;


// Additional functionality will need to be added for this class before it is release worthy
public class Task extends AbstractModel {

	private Date date;
	private String message;
	
	public Task(Date date, String message) {
		this.date = date;
		this.message = message;
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
	 * @return The date when this task was made
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * 
	 * @param date The date when this task was made
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * 
	 * @return The message that is the content of this task in the form of a string
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * 
	 * @param message The message that is the content of this task in the form of a string
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
