package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class UserChange extends RequirementEvent {
	
	ArrayList<String> oldUsers;
	ArrayList<String> newUsers;
	
	public UserChange(Requirement oldReq, Requirement newReq, String userName) {
		this.type = EventType.USER;
		oldUsers = oldReq.getUserNames();
		newUsers = newReq.getUserNames();
		this.userName = userName;
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
		boolean first = true;
		
		if (newUsers.size() > oldUsers.size()) {
			for (String u : newUsers) {
				if (!oldUsers.contains(u)) {
					if (!first) content += '\n';
					else first = false;
					
					content += u + " is now assigned to this requirement";
				}
			}
		}
		else {
			for (String u : oldUsers) {
				if (!newUsers.contains(u)) {
					if (!first) content += '\n';
					else first = false;
					
					content += u + " is no longer assigned to this requirement";
				}
			}
		}
		
		return content;
	}

	@Override
	public String getLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		return "User changes made by " + userName + " on " + dateFormat.format(this.getDate());
	}

}
