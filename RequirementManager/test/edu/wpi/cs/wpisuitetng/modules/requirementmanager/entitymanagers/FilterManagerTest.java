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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.BadRequestException;
import edu.wpi.cs.wpisuitetng.exceptions.ConflictException;
import edu.wpi.cs.wpisuitetng.exceptions.NotFoundException;
import edu.wpi.cs.wpisuitetng.exceptions.NotImplementedException;
import edu.wpi.cs.wpisuitetng.exceptions.WPISuiteException;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockData;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.FilterType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.OperatorType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus;


/**
 * The class <code>FilterManagerTest</code> contains tests for the class {@link <code>FilterManager</code>}
 * 
 * @author Team 5
 */
public class FilterManagerTest {

	MockData db;
	User existingUser;
	Filter existingFilter;
	Session defaultSession;
	String mockSsid;
	FilterManager manager;
	Filter newFilter;
	User bob;
	Filter goodUpdatedFilter;
	Session adminSession;
	Project testProject;
	Project otherProject;
	Filter otherFilter;
	Session otherSession;

	@Before
	public void setUp() throws Exception {
		// Setting up background stuff
		// Users
		bob = new User("bob", "bob", "1234", 28);
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		// Project
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		// Starts up admin session to activate core stuff
		adminSession = new Session(admin, testProject, mockSsid);
		otherSession = new Session(bob, testProject, mockSsid);
		// Another user
		existingUser = new User("rob", "rob", "1234", 2);
		// Session for the user
		defaultSession = new Session(existingUser, testProject, mockSsid);

		// New instances of the model for the manager that we are testing
		existingFilter = new Filter(FilterType.ActualEffort,
				OperatorType.EqualTo, 2, true); // has user
		otherFilter = new Filter(FilterType.Description, OperatorType.Contains,
				"Cat", true); // has user
		newFilter = new Filter(FilterType.Status, OperatorType.NotEqualTo,
				RequirementStatus.New, false); // no user
		goodUpdatedFilter = new Filter(FilterType.Name, OperatorType.EqualTo,
				"Random Name", true); // has user - not special until history
										// logs

		// Set ID's before saving into db directly (since manager would normally
		// handle that
		existingFilter.setUniqueID(1);
		otherFilter.setUniqueID(2);

		// User assignment - specific to filters
		existingFilter.setUser(existingUser);
		otherFilter.setUser(existingUser);
		goodUpdatedFilter.setUser(existingUser);

		// Set up and store stuff in the database
		db = new MockData(new HashSet<Object>());
		db.save(existingFilter, testProject);
		db.save(existingUser);
		db.save(otherFilter, otherProject);
		db.save(admin);
		manager = new FilterManager(db);
	}

	@Test
	public void testMakeEntity() throws WPISuiteException {
		Filter created = manager.makeEntity(defaultSession, newFilter.toJSON());
		assertEquals(3, created.getUniqueID()); // IDs are unique across all
												// Filters currently, and not
												// checked by .equals()
		assertTrue(created.equals(newFilter)); // Tests to see if the filter put
												// in is the one that comes out
		assertTrue(db.retrieve(Filter.class, "UniqueID", 3).get(0)
				.equals(created));
	}

	@Test(expected = ConflictException.class)
	public void testBadMakeEntity() throws WPISuiteException, ConflictException {
		manager.makeEntity(defaultSession, existingFilter.toJSON());
	}

	@Test
	public void testGetEntity() throws WPISuiteException, NotFoundException {
		Filter[] gotten = manager.getEntity(defaultSession, "1");
		assertSame(existingFilter, gotten[0]);
	}

	@Test(expected = NotFoundException.class)
	public void testGetBadId() throws NotFoundException, WPISuiteException {
		// A bad ID should yield a NotFoundException because it is unfindable
		@SuppressWarnings("unused")
		Filter badIDEntity = manager.getEntity(defaultSession, "-1")[0];
	}

	@Test(expected = NotFoundException.class)
	public void testGetMissingEntity() throws NotFoundException,
			WPISuiteException {
		// A bad ID should yield a NotFoundException because it is unfindable
		@SuppressWarnings("unused")
		Filter missingEntity = manager.getEntity(defaultSession, "7")[0]; // this
																			// ID
																			// shouldn't
																			// yield
																			// a
																			// filter

	}

	@Test
	public void testGetAll() throws WPISuiteException {
		Filter[] gotten = manager.getAll(defaultSession);
		assertEquals(2, gotten.length);
		assertTrue(existingFilter.equals(gotten[0])
				|| existingFilter.equals(gotten[1])); // The order is not
														// guranteed
		assertTrue(otherFilter.equals(gotten[1])
				|| otherFilter.equals(gotten[0])); // The order is not guranteed
	}

	@Test
	public void testSave() throws WPISuiteException {
		Filter newFilter2 = new Filter(FilterType.Priority,
				OperatorType.EqualTo, RequirementPriority.High, true);
		manager.save(defaultSession, newFilter2);
		Filter missingEntity = manager.getEntity(defaultSession, "3")[0];
		assertTrue(missingEntity.equals(newFilter2));
	}

	/**
	 * Delete is special in this implementation of Filter models. Here the
	 * filter is "deleted" by having its "user" field changed to null, so that
	 * no user can actually access it. Thus, it is deleted to all users, even if
	 * it persists in the database.
	 * 
	 * @throws WPISuiteException
	 */
	@Test
	public void testDelete() throws WPISuiteException {
		assertSame(existingFilter, db.retrieve(Filter.class, "UniqueID", 1)
				.get(0));
		assertSame(existingUser,
				((Filter) db.retrieve(Filter.class, "UniqueID", 1).get(0))
						.getUser());
		int numFilters = manager.getAll(defaultSession).length; // getAll pulls
																// by username-
																// critical to
																// deletion
																// method

		assertTrue(manager.deleteEntity(defaultSession, "1"));
		assertNotSame(existingUser,
				((Filter) db.retrieve(Filter.class, "UniqueID", 1).get(0))
						.getUser());

		assertTrue(numFilters != manager.getAll(defaultSession).length); // show
																			// proper
																			// deletion
	}

	@Test(expected = NotFoundException.class)
	public void testDeleteMissing() throws WPISuiteException {
		manager.deleteEntity(adminSession, "4534");
	}

	@Test
	public void testDeleteAll() throws WPISuiteException {
		Filter anotherFilter = new Filter(FilterType.ActualEffort,
				OperatorType.EqualTo, 2, true);
		manager.makeEntity(defaultSession, anotherFilter.toJSON());
		assertEquals(3, manager.getAll(defaultSession).length);
		manager.deleteAll(defaultSession);
		assertEquals(0, manager.getAll(defaultSession).length);
	}

	@Test
	public void testDeleteAllWhenEmpty() throws WPISuiteException {
		manager.deleteAll(adminSession);
		manager.deleteAll(adminSession);
		// no exceptions
	}

	@Test
	public void testCount() throws WPISuiteException {
		assertEquals(2, manager.Count());
	}

	@Test
	public void testCountAfterDelete() throws WPISuiteException {
		// Count should stay the same since technically, no Filters are removed
		assertEquals(2, manager.Count());
		manager.deleteAll(adminSession);
		assertEquals(2, manager.Count());
	}

	@Test
	public void testUpdate() throws WPISuiteException {
		Filter toUpdate = manager.getEntity(defaultSession, "1")[0]; // Get
																		// something
																		// to
																		// update

		assertNotSame(toUpdate.getType(), "Random Value"); // Make sure it isn't
															// the value we want
															// to update to
		toUpdate.setValue("Random Value"); // Change the value

		Filter updated = manager.update(defaultSession, toUpdate.toJSON()); // Perform
																			// the
																			// update

		assertTrue(toUpdate.equals(updated)); // update returned the right thing
		assertEquals(manager.getEntity(defaultSession, "1")[0], toUpdate); // The
																			// right
																			// thing
																			// was
																			// changed
																			// in
																			// the
																			// DB
	}

	@Test(expected = BadRequestException.class)
	public void testBadUpdate() throws WPISuiteException {
		manager.update(defaultSession, (new Filter()).toJSON() + "asdf"); // 'asdf'
																			// corrupts
																			// the
																			// string
	}

	@Test
	public void testNoUpdate() throws WPISuiteException {
		Filter updated = manager
				.update(defaultSession, existingFilter.toJSON());
		assertSame(existingFilter, updated);
	}

	@Test(expected = NotImplementedException.class)
	public void testAdvancedGet() throws WPISuiteException {
		manager.advancedGet(defaultSession, new String[0]);
	}

	@Test(expected = NotImplementedException.class)
	public void testAdvancedPost() throws WPISuiteException {
		manager.advancedPost(defaultSession, "", "");

	}

	@Test(expected = NotImplementedException.class)
	public void testAdvancedPut() throws WPISuiteException {
		manager.advancedPut(defaultSession, new String[0], "");
	}

	@Test
	public void testFiltersAreUserDependent() throws WPISuiteException {
		assertEquals(2, manager.getAll(defaultSession).length);
		assertEquals(0, manager.getAll(otherSession).length);
	}
}
