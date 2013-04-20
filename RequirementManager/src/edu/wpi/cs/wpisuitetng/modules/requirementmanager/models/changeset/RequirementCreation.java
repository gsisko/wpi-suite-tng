package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;

public class RequirementCreation extends RequirementEvent {

	private String reqName;
	private String reqDescription;
	private String reqType;
	private String reqPriority;
	private String reqReleaseNumber;
	

	public RequirementCreation(Requirement requirement) {
		this.type = EventType.CREATION;
		this.reqName = requirement.getName();
		this.reqDescription = requirement.getDescription();
		this.reqType = RequirementType.toBlankString(requirement.getType());
		this.reqPriority = RequirementPriority.toBlankString(requirement.getPriority());
		this.reqReleaseNumber = requirement.getReleaseNumber();
	}
	
	@Override
	public String toJSON() {
		String json;
		Gson gson = new Gson();
		json = gson.toJson(this, Note.class);
		return json;
	}

	@Override
	public String getBodyString() {
		String content = "";
		
		content += "Name initialized to \"" + reqName + "\"\n";
		content += "Description initialized to \"" + reqDescription + "\"\n";
		content += "Type initialized to \"" + reqType + "\"\n";
		content += "Priority initialized to \"" + reqPriority + "\"\n";
		content += "ReleaseNumber initialized to \"" + reqReleaseNumber + "\"";
		
		return content;
	}

	@Override
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "Requirement created by " + userName + " on " + dateFormat.format(this.getDate());
	}

}
