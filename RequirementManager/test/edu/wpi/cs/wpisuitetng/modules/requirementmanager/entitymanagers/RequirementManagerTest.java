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

public class RequirementManagerTest {

	MockData db;
	User existingUser;
	Requirement existingRequirement;
	Session defaultSession;
	String mockSsid;
	RequirementManager manager;
	Requirement newRequirement;
	Requirement goodUpdatedRequirement;
	Session adminSession;
	Project testProject;
	Project otherProject;
	Requirement otherRequirement;
	
	@Before
	public void setUp() throws Exception {
		User admin = new User("admin", "admin", "1234", 27);
		admin.setRole(Role.ADMIN);
		testProject = new Project("test", "1");
		otherProject = new Project("other", "2");
		mockSsid = "abc123";
		adminSession = new Session(admin, testProject, mockSsid);
		
		existingUser = new User("joe", "joe", "1234", 2);
		existingRequirement = new Requirement("An existing requirement", "An existing requirement description", RequirementType.NoType, RequirementPriority.NoPriority, 0,0);
		existingRequirement.setActualEffort(0);
		existingRequirement.setEstimate(100);
		existingRequirement.setType(RequirementType.Scenario);
		existingRequirement.setPriority(RequirementPriority.Medium);
		existingRequirement.setStatus(RequirementStatus.New);
		existingRequirement.setId(1);
		existingRequirement.setReleaseNumber(1);
		
		otherRequirement = new Requirement("A requirement in a different project", "", RequirementType.NoType, RequirementPriority.NoPriority, 0,0);
		
		goodUpdatedRequirement = new Requirement("A changed title", "A changed description", RequirementType.UserStory, RequirementPriority.High, 0,0);
		goodUpdatedRequirement.setStatus(RequirementStatus.Complete);
		
		defaultSession = new Session(existingUser, testProject, mockSsid);
		newRequirement = new Requirement("A new requirement", "A description", RequirementType.NoType, RequirementPriority.NoPriority, 0,0);
		
		db = new MockData(new HashSet<Object>());
		db.save(existingRequirement, testProject);
		db.save(existingUser);
		db.save(otherRequirement, otherProject);
		db.save(admin);
		
		manager = new RequirementManager(db);
		
		//Check to make sure no set up failed
		assertNotNull(admin);
		assertNotNull(testProject);
		assertNotNull(otherProject);
		assertNotNull(mockSsid);
		assertNotNull(adminSession);
		assertNotNull(db);
		assertNotNull(existingUser);
		assertNotNull(existingRequirement);
		assertNotNull(otherRequirement);
		assertNotNull(goodUpdatedRequirement);
		assertNotNull(newRequirement);
		assertNotNull(defaultSession);
		assertNotNull(manager);
	}

	@Test
	public void testMakeEntity() throws WPISuiteException {
		Requirement created = manager.makeEntity(defaultSession, newRequirement.toJSON());
		assertEquals(3, created.getId()); // IDs are unique across projects
		assertEquals("A new requirement", created.getName());
		assertSame(db.retrieve(Requirement.class, "id", 3).get(0), created);
	}
	
	// this test should print a warning to the console
	@Test(expected=BadRequestException.class)
	public void testMakeBadEntity() throws WPISuiteException {
		newRequirement.setName(""); // invalid title
		// make sure it's being passed through the validator
		manager.makeEntity(defaultSession, newRequirement.toJSON()+ "string that breaks JSON");
	}
	
	@Test
	public void testGetEntity() throws WPISuiteException {
		Requirement[] gotten = manager.getEntity(defaultSession, "1");
		assertSame(existingRequirement, gotten[0]);
	}

	@Test(expected=NotFoundException.class)
	public void testGetBadId() throws WPISuiteException {
		manager.getEntity(defaultSession, "-1");
	}

	@Test(expected=NotFoundException.class)
	public void testGetMissingEntity() throws WPISuiteException {
		manager.getEntity(defaultSession, "2");
	}
	
	@Test
	public void testGetAll() throws WPISuiteException {
		Requirement[] gotten = manager.getAll(defaultSession);
		assertEquals(1, gotten.length);
		assertSame(existingRequirement, gotten[0]);
	}
	
	@Test
	public void testSave() throws WPISuiteException {
		Requirement newRequirement = new Requirement("Name", "Description", RequirementType.NoType, RequirementPriority.NoPriority, 0,0);
		newRequirement.setProject(testProject);
		manager.save(defaultSession, newRequirement);
		assertSame(newRequirement, db.retrieve(Requirement.class, "id", 3).get(0));
		assertSame(testProject, newRequirement.getProject());
	}
	
	@Test
	public void testDelete() throws WPISuiteException {
		assertSame(existingRequirement, db.retrieve(Requirement.class, "id", 1).get(0));
		assertTrue(manager.deleteEntity(adminSession, "1"));
		assertEquals(0, db.retrieve(Requirement.class, "id", 1).size());
	}
	
	@Test(expected=NotFoundException.class)
	public void testDeleteMissing() throws WPISuiteException {
		manager.deleteEntity(adminSession, "4534");
	}
	
	@Test(expected=NotFoundException.class)
	public void testDeleteFromOtherProject() throws WPISuiteException {
		manager.deleteEntity(adminSession, Integer.toString(otherRequirement.getId()));
	}
	
	
	@Test
	public void testDeleteAll() throws WPISuiteException {
		Requirement anotherRequirement = new Requirement("Name", "Description", RequirementType.NoType, RequirementPriority.NoPriority, 0,0);
		manager.makeEntity(defaultSession, anotherRequirement.toJSON());
		assertEquals(2, db.retrieveAll(new Requirement(), testProject).size());
		manager.deleteAll(adminSession);
		assertEquals(0, db.retrieveAll(new Requirement(), testProject).size());
		// otherRequirement should still be around
		assertEquals(1, db.retrieveAll(new Requirement(), otherProject).size());
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
	public void testUpdate() throws WPISuiteException {
		// Start with makeEntity because the entity must exist already
		Requirement toUpdate = manager.makeEntity(defaultSession, goodUpdatedRequirement.toJSON());
		toUpdate.setStatus(RequirementStatus.Deleted);
		Requirement updated = manager.update(defaultSession, toUpdate.toJSON());
		assertSame(toUpdate,updated);
		assertEquals(goodUpdatedRequirement.getName(), updated.getName());
		assertNotSame(goodUpdatedRequirement.getStatus(), updated.getStatus()); 
		assertSame(toUpdate.getStatus(), updated.getStatus()); 
	}
	
	// this test should print a warning to the console
	@Test(expected=BadRequestException.class)
	public void testBadUpdate() throws WPISuiteException {
		goodUpdatedRequirement.setName("");
		manager.update(defaultSession, goodUpdatedRequirement.toJSON()+ "This makes JSON strings cry");
	}
	
	@Test
	public void testNoUpdate() throws WPISuiteException {
		Requirement updated = manager.update(defaultSession, existingRequirement.toJSON());
		assertSame(existingRequirement, updated);
		
		//Check if elements are the same
		assertEquals(existingRequirement.getPriority(), updated.getPriority());
		assertEquals(existingRequirement.getStatus(), updated.getStatus());
		assertEquals(existingRequirement.getType(), updated.getType());
		assertEquals(existingRequirement.getReleaseNumber(), updated.getReleaseNumber());
		assertEquals(existingRequirement.getId(), updated.getId());
		assertEquals(existingRequirement.getEstimate(), updated.getEstimate());
		assertEquals(existingRequirement.getDescription(), updated.getDescription());
		assertEquals(existingRequirement.getName(), updated.getName());
		assertEquals(existingRequirement.getActualEffort(), updated.getActualEffort());
		
	}
	
	@Test(expected=WPISuiteException.class)
	public void testUpdateNullSession() throws WPISuiteException{
		manager.update(null, existingRequirement.toJSON());
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
