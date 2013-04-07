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

import static org.junit.Assert.*;


import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.Session;
import edu.wpi.cs.wpisuitetng.exceptions.*;
import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.Role;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.MockData;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.*;

public class IterationManagerTest {

	MockData db;
	User existingUser;
	Iteration existingIteration;
	Session defaultSession;
	String mockSsid;
	IterationManager manager;
	Iteration newIteration;
	Iteration goodUpdatedIteration;
	Session adminSession;
	Project testProject;
	Project otherProject;
	Iteration otherIteration;
	
	@Before
	public void setUp() throws Exception {
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("joe", "joe", "1234", 2);
		existingIteration = new Iteration("Iteration 1", new Date(5000), new Date(6000));
		
		otherIteration = new Iteration("An Iteration of a different project", new Date(700), new Date (800));
		
		goodUpdatedIteration = new Iteration("A changed iteration name", new Date(300), new Date(400));
		goodUpdatedIteration.setEndDate(new Date (500));
		
		defaultSession = new Session(existingUser, testProject, mockSsid);
		newIteration = new Iteration("A new iteration", new Date(600), new Date(800));
		
		// Setting ID's manually
		existingIteration.setID(1);
		otherIteration.setID(2);
		
		
		db = new MockData(new HashSet<Object>(), new HashSet<Project>());
		db.addProject(testProject);
		db.addProject(otherProject);

		db.save(existingIteration, testProject);
		db.save(existingUser);
		db.save(otherIteration, otherProject);
		db.save(admin);
		
		manager = new IterationManager(db);
		
		//Check to make sure no set up failed
		assertNotNull(admin);
		assertNotNull(testProject);
		assertNotNull(otherProject);
		assertNotNull(mockSsid);
		assertNotNull(adminSession);
		assertNotNull(db);
		assertNotNull(existingUser);
		assertNotNull(existingIteration);
		assertNotNull(otherIteration);
		assertNotNull(goodUpdatedIteration);
		assertNotNull(newIteration);
		assertNotNull(defaultSession);
		assertNotNull(manager);
	}

	@Test    // This is completely independent and tests setup
	public void testInstantiateBacklogs() throws NotFoundException, WPISuiteException{
		// Special setup is used here to perform a full test from the ground up
		MockData testMockDB = new MockData(new HashSet<Object>(), new HashSet<Project>());
		Project testProject = new Project("Tester", "1");	
		Session testSession = new Session(existingUser, testProject, mockSsid);
		testMockDB.save(existingUser);
		testMockDB.addProject(testProject);
		
		// This is the fun part- in here instantiateBacklogs gets called
		IterationManager manager = new IterationManager(testMockDB);
		assertNotNull(manager.getEntity(testSession, "0")); // must find the backlog
		assertNotNull(manager.getAll(  testSession ));
	}

	
	@Test
	public void testMakeEntity() throws WPISuiteException {
		Iteration created = manager.makeEntity(defaultSession, newIteration.toJSON());
		assertEquals("A new iteration", created.getName());
		assertSame(db.retrieve(Iteration.class, "name", "A new iteration").get(0), created);
	}
	
	// this test should print a warning to the console
	@Test(expected=BadRequestException.class)
	public void testMakeBadEntity() throws WPISuiteException {
		newIteration.setName(""); // invalid title
		// make sure it's being passed through the validator
		manager.makeEntity(defaultSession, newIteration.toJSON()+ "string that breaks JSON");
	}
	
	@Test(expected=ConflictException.class)
	public void testMakeDuplicateEntity() throws  ConflictException, WPISuiteException{
		//simply call make entity on an iteration that we know is in the database
		manager.makeEntity(defaultSession, existingIteration.toJSON());	
		System.out.println("Shouldn't be here...");
	}
	
	@Test
	public void testGetEntity() throws WPISuiteException {
		Iteration[] gotten = manager.getEntity(defaultSession, "1");
		assertSame(existingIteration, gotten[0]);
	}

	@Test(expected=NotFoundException.class)
	public void testGetBadName() throws WPISuiteException {
		manager.getEntity(defaultSession, "-1");
	}

	@Test(expected=NotFoundException.class)
	public void testGetMissingEntity() throws WPISuiteException {
		manager.getEntity(defaultSession, "2");
	}
	
	@Test
	public void testGetAll() throws WPISuiteException {
		Iteration[] gotten = manager.getAll(defaultSession);
		assertEquals(2, gotten.length);
		assertSame(existingIteration, gotten[0]);
	}
	
	@Test
	public void testSave() throws WPISuiteException {
		Iteration newIteration = new Iteration("New iteration", new Date(1000), new Date(3000));
		newIteration.setID(3);
		newIteration.setProject(testProject);
		manager.save(adminSession, newIteration);
		assertSame(newIteration, db.retrieve(Iteration.class, "name", "New iteration").get(0));
		assertSame(testProject, newIteration.getProject());
	}
	
	@Test
	public void testDelete() throws WPISuiteException {
		assertSame(existingIteration, db.retrieve(Iteration.class, "name", "Iteration 1").get(0));
		assertTrue(manager.deleteEntity(adminSession, "1"));
		assertEquals(0, db.retrieve(Iteration.class, "id", "1").size());
	}
	
	@Test(expected=NotFoundException.class)
	public void testDeleteMissing() throws WPISuiteException {
		manager.deleteEntity(adminSession, "4534");
	}
	
	@Test(expected=NotFoundException.class)
	public void testDeleteFromOtherProject() throws WPISuiteException {
		manager.deleteEntity(adminSession, ((Integer)otherIteration.getID()).toString());
	}
	
	
	@Test
	public void testDeleteAll() throws WPISuiteException {
		Iteration anotherIteration = new Iteration("Name", new Date(600), new Date(9000));
		manager.makeEntity(defaultSession, anotherIteration.toJSON());
		assertEquals(3, db.retrieveAll(new Iteration(), testProject).size());
		manager.deleteAll(adminSession);
		assertEquals(0, db.retrieveAll(new Iteration(), testProject).size());
		// otherIteration should still be around
		assertEquals(2, db.retrieveAll(new Iteration(), otherProject).size());
	}
	
	
	@Test
	public void testDeleteAllWhenEmpty() throws WPISuiteException {
		manager.deleteAll(adminSession);
		manager.deleteAll(adminSession);
		// no exceptions
	}
	
	@Test
	public void testCount() throws WPISuiteException {
		assertEquals(4, manager.Count());
	}
	
	@Test
	public void testUpdate() throws WPISuiteException {
		// Start with makeEntity because the entity must exist already
		Iteration toUpdate = manager.makeEntity(defaultSession, goodUpdatedIteration.toJSON());
		toUpdate.setEndDate(new Date(1000));
		Iteration updated = manager.update(defaultSession, toUpdate.toJSON());
		assertSame(toUpdate,updated);
		assertEquals(goodUpdatedIteration.getName(), updated.getName());
		assertNotSame(goodUpdatedIteration.getEndDate(), updated.getEndDate()); 
		assertSame(toUpdate.getEndDate(), updated.getEndDate()); 
	}
	
	// this test should print a warning to the console
	@Test(expected=BadRequestException.class)
	public void testBadUpdate() throws WPISuiteException {
		goodUpdatedIteration.setName("");
		manager.update(defaultSession, goodUpdatedIteration.toJSON()+ "This makes JSON strings cry");
	}
	
	@Test
	public void testNoUpdate() throws WPISuiteException {
		Iteration updated = manager.update(defaultSession, existingIteration.toJSON());
		assertSame(existingIteration, updated);
		
		//Check if elements are the same
		assertEquals(existingIteration.getName(), updated.getName());
		assertEquals(existingIteration.getStartDate(), updated.getStartDate());
		assertEquals(existingIteration.getEndDate(), updated.getEndDate());
		
	}
	
	
	@Test(expected=NotImplementedException.class)
	public void testAdvancedGet() throws WPISuiteException {
		manager.advancedGet(defaultSession, new String[0]);
	}
	
	@Test(expected=NotImplementedException.class)
	public void testAdvancedPost() throws WPISuiteException {
		manager.advancedPost(defaultSession, "", "");
	}
	
	@Test(expected=NotImplementedException.class)
	public void testAdvancedPut() throws WPISuiteException {
		manager.advancedPut(defaultSession, new String[0], "");
	}

}
