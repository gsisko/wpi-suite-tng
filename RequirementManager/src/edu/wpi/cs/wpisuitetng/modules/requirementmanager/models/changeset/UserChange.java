package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.google.gson.Gson;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Note;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

public class UserChange extends RequirementEvent {
	
	ArrayList<User> oldUsers;
	ArrayList<User> newUsers;
	
	public UserChange(Requirement oldReq, Requirement newReq) {
		this.type = EventType.USER;
		oldUsers = oldReq.getUsers();
		newUsers = newReq.getUsers();
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
			for (User u : newUsers) {
				if (!oldUsers.contains(u)) {
					if (!first) content += '\n';
					else first = false;
					
					content += u.getName() + "is now assigned to this requirement";
				}
			}
		}
		else {
			for (User u : oldUsers) {
				if (!newUsers.contains(u)) {
					if (!first) content += '\n';
					else first = false;
					
					content += u.getName() + "is no longer assigned to this requirement";
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
