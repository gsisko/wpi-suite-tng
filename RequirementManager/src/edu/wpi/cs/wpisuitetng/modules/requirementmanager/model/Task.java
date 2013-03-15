package edu.wpi.cs.wpisuitetng.modules.requirementmanager.model;

import java.util.Date;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;

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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
