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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.AcceptanceTest;

/**
 * A model that contains a list of AcceptanceTest models
 */
@SuppressWarnings({"serial","rawtypes"})
public class AcceptanceTestListModel extends AbstractListModel {
	
	/** The list of tests in the model */
	private List<AcceptanceTest> acceptanceTests;
	
	/** Constructs a new model with no tests.
	 */
	public AcceptanceTestListModel() {
		acceptanceTests = new ArrayList<AcceptanceTest>();
	}

	/** Adds the given test to the list of tests in the model
	 * @param newTest the new AcceptanceTest to add
	 */
	public void addAcceptanceTest(AcceptanceTest newTest) {
		acceptanceTests.add(newTest);// Add the test
		this.fireIntervalAdded(this, 0, 0);// Notify the model that it has changed so the GUI will be updated
	}
	
	/** Adds the given array of tests to the list of tests in the model
	 * @param acceptanceTests the array of tests to add
	 */
	public void addAcceptanceTests(AcceptanceTest[] acceptanceTests) {
		for (int i = 0; i < acceptanceTests.length; i++) {
			this.acceptanceTests.add(acceptanceTests[i]);
		}
		this.fireIntervalAdded(this, 0, Math.max(getSize() - 1, 0));
	}
	
	/** Removes all tests from this model
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
	
	/** Returns the test at the given index. Note this method returns
	 * elements in reverse order, so newest messages are returned first.
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public AcceptanceTest getElementAt(int index) {
		return acceptanceTests.get(acceptanceTests.size() - 1 - index);
	}

	/** Sets the test at the given index. Note this method accesses
	 * elements in reverse order, so newest messages are returned first.
	 * @param index int
	 * @param newAcceptanceTest AcceptanceTest
	 */
	public void setElementAt(int index, AcceptanceTest newAcceptanceTest) {
		acceptanceTests.set(acceptanceTests.size() - 1 - index, newAcceptanceTest);
	}
	
	/** Returns the number of tests in the model.
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return acceptanceTests.size();
	}
}
