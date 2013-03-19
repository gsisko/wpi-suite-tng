/**
 * 
 */
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit 4 tests for Requirement.java in edu.wpi.cs.wpisuitetng.modules.requirementmanager.models
 * @author Robert Smieja
 *
 */
public class RequirementTest {

	//Misc variables to be used in testing
	private static int numOfTestObjects = 2;
	
	//Create an array of Requirement objects that we will use and abuse
	private static Requirement[] tester;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//This runs before the testing methods are called
		tester = new Requirement[numOfTestObjects];
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		//This runs before the testing methods are called
		
		//Garbage collection we don't need to free...
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setProject(edu.wpi.cs.wpisuitetng.modules.core.models.Project)}.
	 */
	@Test
	public final void testSetProject() {
		
		for (int objectCount = 0; objectCount < numOfTestObjects; objectCount++){
			tester[objectCount].setProject(null);
		}
		
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#Requirement(java.lang.String, java.lang.String, int, int, edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus, edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority, int, int)}.
	 */
	@Test
	public final void testRequirement() {
		Requirement test;
		
		//Test normal input
		test = new Requirement("Test", "JUnit test requirement", 0, 0, null, null, 0, 0);
		test = null;
		
		//Test garbage and illegal input
		test = new Requirement(null, null, 0, 0, null, null, 0, 0);
		test = null;
		
		//Test at max, below and above
		test = new Requirement(null, null, 0, 0, null, null, 0, 0);
		test = null;
		
		test = new Requirement(null, null, 0, 0, null, null, 0, 0);
		test = null;
		
		test = new Requirement(null, null, 0, 0, null, null, 0, 0);
		test = null;
		
		//Test at min, below and above
		test = new Requirement(null, null, 0, 0, null, null, 0, 0);
		test = null;
		
		test = new Requirement(null, null, 0, 0, null, null, 0, 0);
		test = null;
		
		test = new Requirement(null, null, 0, 0, null, null, 0, 0);
		test = null;
		

		
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#save()}.
	 */
	@Test
	public final void testSave() {
//		Requirement tester = new Requirement("Test", "Test requirement for JUnit", 0, 0, null, null, 0, 0);
		Requirement tester = new Requirement(null, null, 0, 0, null, null, 0, 0);
		tester.save();
		
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#delete()}.
	 */
	@Test
	public final void testDelete() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#toJSON()}.
	 */
	@Test
	public final void testToJSON() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#identify(java.lang.Object)}.
	 */
	@Test
	public final void testIdentify() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#toJSON(edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement[])}.
	 */
	@Test
	public final void testToJSONRequirementArray() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#toString()}.
	 */
	@Test
	public final void testToString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#fromJSON(java.lang.String)}.
	 */
	@Test
	public final void testFromJSON() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#fromJSONArray(java.lang.String)}.
	 */
	@Test
	public final void testFromJSONArray() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#addGsonDependencies(com.google.gson.GsonBuilder)}.
	 */
	@Test
	public final void testAddGsonDependencies() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getId()}.
	 */
	@Test
	public final void testGetId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setId(int)}.
	 */
	@Test
	public final void testSetId() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getActualEffort()}.
	 */
	@Test
	public final void testGetActualEffort() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setActualEffort(int)}.
	 */
	@Test
	public final void testSetActualEffort() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getEstimate()}.
	 */
	@Test
	public final void testGetEstimate() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setEstimate(int)}.
	 */
	@Test
	public final void testSetEstimate() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getDescription()}.
	 */
	@Test
	public final void testGetDescription() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setDescription(java.lang.String)}.
	 */
	@Test
	public final void testSetDescription() {
		Requirement test = new Requirement(null, null, 0, 0, null, null, 0, 0);
		
		//Test normal input
		test.setDescription("Test");
		
		//Assert will make sure the results match, else it will fail the test
		assertEquals("Description", "Test", test.getDescription());
		
		
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getName()}.
	 */
	@Test
	public final void testGetName() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setName(java.lang.String)}.
	 */
	@Test
	public final void testSetName() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getReleaseNumber()}.
	 */
	@Test
	public final void testGetReleaseNumber() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setReleaseNumber(int)}.
	 */
	@Test
	public final void testSetReleaseNumber() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getStatus()}.
	 */
	@Test
	public final void testGetStatus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setStatus(edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementStatus)}.
	 */
	@Test
	public final void testSetStatus() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getPriority()}.
	 */
	@Test
	public final void testGetPriority() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setPriority(edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority)}.
	 */
	@Test
	public final void testSetPriority() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#getEvents()}.
	 */
	@Test
	public final void testGetEvents() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#setEvents(java.util.List)}.
	 */
	@Test
	public final void testSetEvents() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement#updateReq(edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement)}.
	 */
	@Test
	public final void testUpdateReq() {
		fail("Not yet implemented"); // TODO
	}

}
