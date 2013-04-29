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

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>UserListModelTest</code> contains tests for the class <code>{@link UserListModel}</code>.
 * @author Team 5 D13
 */
public class UserListModelTest {
	/**
	 * Run the UserListModel() constructor test.
	 */
	@Test
	public void testUserListModel_1()
		throws Exception {

		UserListModel result = new UserListModel();

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.getSize());
	}

	/**
	 * Run the void addUser(String) method test.
	 */
	@Test
	public void testAddUser_1()
		throws Exception {
		UserListModel fixture = new UserListModel();
		fixture.addUser("ejimenez");
		String newUser = "ejimenez";

		fixture.addUser(newUser);

		// add additional test code here
	}

	/**
	 * Run the void addUsers(String[]) method test.
	 */
	@Test
	public void testAddUsers_1()
		throws Exception {
		UserListModel fixture = new UserListModel();
		fixture.addUser("gpollice");
		String[] arrayOfUsers = new String[] {"ejimenez", "gpollice"};

		fixture.addUsers(arrayOfUsers);
	}

	/**
	 * Run the void addUsers(String[]) method test.
	 */
	@Test
	public void testAddUsers_2()
		throws Exception {
		UserListModel fixture = new UserListModel();
		fixture.addUser("mrcalder");
		String[] arrayOfUsers = new String[] {};

		fixture.addUsers(arrayOfUsers);
	}

	/**
	 * Run the void emptyModel() method test.
	 */
	@Test
	public void testEmptyModel_1()
		throws Exception {
		UserListModel fixture = new UserListModel();
		fixture.addUser("paular");

		fixture.emptyModel();
	}

	/**
	 * Run the void emptyModel() method test.
	 */
	@Test
	public void testEmptyModel_2()
		throws Exception {
		UserListModel fixture = new UserListModel();

		fixture.emptyModel();
	}

	/**
	 * Run the String getElementAt(int) method test.
	 */
	@Test
	public void testGetElementAt_1()
		throws Exception {
		UserListModel fixture = new UserListModel();
		fixture.addUser("ejimenez");
		int index = 0;

		String result = fixture.getElementAt(index);
		assertNotNull(result);
	}

	/**
	 * Run the int getSize() method test.
	 */
	@Test
	public void testGetSize_1()
		throws Exception {
		UserListModel fixture = new UserListModel();
		fixture.addUser("gpollice");

		int result = fixture.getSize();
		assertEquals(1, result);
	}

	/**
	 * Run the String getUserAt(int) method test.
	 */
	@Test
	public void testGetUserAt_1()
		throws Exception {
		UserListModel fixture = new UserListModel();
		fixture.addUser("ejimenez");
		int index = 0;

		String result = fixture.getUserAt(index);

		assertNotNull(result);
	}

	/**
	 * Run the void removeElementAt(int) method test.
	 */
	@Test
	public void testRemoveElementAt_1()
		throws Exception {
		UserListModel fixture = new UserListModel();
		fixture.addUser("paular");
		int index = 0;

		fixture.removeElementAt(index);

	}

	/**
	 * Perform pre-test initialization.
	 */
	@Before
	public void setUp(){
		
	}

	/**
	 * Perform post-test clean-up.
	 */
	@After
	public void tearDown() {
		
	}
}