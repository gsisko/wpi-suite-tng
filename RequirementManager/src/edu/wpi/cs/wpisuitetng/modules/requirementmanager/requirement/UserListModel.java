package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

@SuppressWarnings({"serial","rawtypes"})
public class UserListModel extends AbstractListModel {
	
	
	private List<User> users;
	

	public UserListModel() {
		users = new ArrayList<User>();
	}


	public void addUser(User newUser) {
		// Add the user
		users.add(newUser);
		
		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}
	

	public void addUsers(User[] arrayOfUsers) {
		for (int i = 0; i < arrayOfUsers.length; i++) {
			this.users.add(arrayOfUsers[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	

	public void emptyModel() {
		int oldSize = getSize();
		Iterator<User> iterator = users.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}
	

	@Override
	public String getElementAt(int index) {
		return users.get(users.size() - 1 - index).toString();
	}
	

	public User getUserAt(int index) {
		return users.get(users.size() - 1 - index);
	}
	

	public void removeElementAt(int index) {
		users.remove(users.size() - 1 - index);
	}


	@Override
	public int getSize() {
		return users.size();
	}
}
