package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.AbstractModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.old.Epic;

/**
 * @author Dabrowski
 *
 */
public class RequirementMessage extends AbstractModel{

	/**
	 * 
	 */
	public RequirementMessage() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Converts this Epic to a JSON string
	 * @return a string in JSON representing this Epic
	 */
	public String toJSON1() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Epic.class);
		return json;
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






	public static RequirementMessage fromJson(String content) {
		// TODO Auto-generated method stub
		return null;
	}

}
