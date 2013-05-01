/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Team 5 D13
 * 
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.AbstractListModel;

/**
 * The model for containing a list of users
 */
@SuppressWarnings({"serial","rawtypes"})
public class UserListModel extends AbstractListModel {
	
	private ArrayList<String> users;
	
	public UserListModel() {
		users = new ArrayList<String>();
	}
	
	/**
	 * Adds the given user to the list
	 * @param newUser String
	 */
	public void addUser(String newUser) {
		users.add(newUser);// Add the user
		this.fireIntervalAdded(this, 0, 0);// Notify the model that it has changed so the GUI will be updated
	}
	
	/**
	 * Adds an array of given users to the list
	 * @param arrayOfUsers String[]
	 */
	public void addUsers(String[] arrayOfUsers) {
		for (int i = 0; i < arrayOfUsers.length; i++) {
			users.add(arrayOfUsers[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<String> iterator = users.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}

	/**
	 * Gets a user at the given index
	 * @param index int
	 * @return String
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public String getElementAt(int index) {
		return users.get(users.size() - 1 - index).toString();
	}
	
	/**
	 * Gets a user at the given index
	 * @param index int
	 * @return String
	 */
	public String getUserAt(int index) {
		return users.get(users.size() - 1 - index);
	}
	
	/**
	 * Removes the user at the given index
	 * @param index int
	 */
	public void removeElementAt(int index) {
		users.remove(users.size() - 1 - index);
	}

	/**
	 * Returns the size of the list
	 * @return int
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return users.size();
	}
}
