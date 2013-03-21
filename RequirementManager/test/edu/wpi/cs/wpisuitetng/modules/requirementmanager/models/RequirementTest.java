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

import edu.wpi.cs.wpisuitetng.modules.core.models.Project;
import edu.wpi.cs.wpisuitetng.modules.core.models.User;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;

import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType.*;
import static edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority.*;

/**
 * JUnit 4 tests for Requirement.java in edu.wpi.cs.wpisuitetng.modules.requirementmanager.models
 * @author Robert Smieja
 *
 */
public class RequirementTest {

	User bob;
	Requirement d1;
	Requirement d1copy;
	Requirement d2;
	Project project;
	
	@Before
	public void setUp() {
		bob = new User("Bob", "bob", "", -1);
		d1 = new Requirement("Bob", "bob test unit", NoType, High, 1);
		d1copy = new Requirement("Bob", "bob test unit", NoType, High, 1);
		d2 = new Requirement("Bob2", "bob test unit", NoType, Medium, 2);
		project = new Project("test", "1");
	}
	
	@Test
	public void testIdentify() {
		assertTrue(d1.identify(d1));
		assertTrue(d1.identify(d1copy));
		//assertTrue(d1.identify("1"));
		//assertFalse(d1.identify(d2)); //should fail, due to id stuff
		//assertFalse(d1.identify("2"));
		assertFalse(d1.identify(new Object()));
		assertFalse(d1.identify(null));
	}
	
	@Test
	public void testfromJSON() {
		String json = d1.toJSON();
		Requirement newRequirement = Requirement.fromJSON(json);
		assertEquals(1, newRequirement.getReleaseNumber());
	}
	
	@Test
	public void testSetProject() {
		d1.setProject(project);
		assertSame(project, d1.getProject());
	}

}
