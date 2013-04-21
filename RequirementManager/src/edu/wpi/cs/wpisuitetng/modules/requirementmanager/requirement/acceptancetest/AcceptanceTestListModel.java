/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Robert Dabrowski
 *		Danielle LaRose
 *		Edison Jimenez
 *		Christian Gonzalez
 *		Mike Calder
 *		John Bosworth
 *		Paula Rudy
 *		Gabe Isko
 *		Bangyan Zhang
 *		Cassie Hudson
 *		Robert Smieja
 *		Alex Solomon
 *		Brian Hetherman
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AcceptanceTest;

@SuppressWarnings({"serial","rawtypes"})
public class AcceptanceTestListModel extends AbstractListModel {
	
	/** The list of messages on the board */
	private List<AcceptanceTest> acceptanceTests;
	
	/**
	 * Constructs a new board with no messages.
	 */
	public AcceptanceTestListModel() {
		acceptanceTests = new ArrayList<AcceptanceTest>();
	}

	/**
	 * Adds the given message to the board
	 * 
	 * @param newMessage the new message to add
	 */
	public void addMessage(AcceptanceTest newMessage) {
		// Add the message
		acceptanceTests.add(newMessage);
		
		// Notify the model that it has changed so the GUI will be udpated
		this.fireIntervalAdded(this, 0, 0);
	}
	
	/**
	 * Adds the given array of tests to the board
	 * 
	 * @param acceptanceTests the array of tests to add
	 */
	public void addMessages(AcceptanceTest[] acceptanceTests) {
		for (int i = 0; i < acceptanceTests.length; i++) {
			this.acceptanceTests.add(acceptanceTests[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	/**
	 * Removes all tests from this model
	 * 
	 * NOTE: One cannot simply construct a new instance of
	 * the model, because other classes in this module have
	 * references to it. Hence, we manually remove each test
	 * from the model.
	 */
	public void emptyModel() {
		int oldSize = getSize();
		Iterator<AcceptanceTest> iterator = acceptanceTests.iterator();
		while (iterator.hasNext()) {
			iterator.next();
			iterator.remove();
		}
		this.fireIntervalRemoved(this, 0, Math.max(oldSize - 1, 0));
	}
	
	/* 
	 * Returns the test at the given index. This method is called
	 * internally by the JList in BoardPanel. Note this method returns
	 * elements in reverse order, so newest messages are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public AcceptanceTest getElementAt(int index) {
		return acceptanceTests.get(acceptanceTests.size() - 1 - index);
	}

	/*
	 * Returns the number of tests in the model. Also used internally
	 * by the JList in BoardPanel.
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return acceptanceTests.size();
	}
}
