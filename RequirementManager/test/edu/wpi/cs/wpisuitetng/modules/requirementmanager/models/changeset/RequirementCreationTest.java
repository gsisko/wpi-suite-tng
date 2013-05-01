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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import static org.junit.Assert.*;

/** This class contains tests for the class {@link RequirementCreation}.
 */
public class RequirementCreationTest {

	private Date test_date;
	private DateFormat dateFormat;
	
	@Test
	public void testRequirementCreation_1()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setReleaseNumber("2");
		requirement.setType(RequirementType.Epic);
		requirement.setDescription("Desc 2");
		requirement.setName("Req 2");
		requirement.setPriority(RequirementPriority.High);
		String theCreator = "ejimenez";
		RequirementCreation result = new RequirementCreation(requirement, theCreator);

		assertNotNull(result);
		assertEquals("Requirement created by " +theCreator + " on " +dateFormat.format(test_date), result.getLabelString());
		assertEquals("Name initialized to \"Req 2\"\nDescription initialized to \"Desc 2\"\nType initialized to \"Epic\"\nPriority initialized to \"High\"\nReleaseNumber initialized to \"2\"", result.getBodyString());
		assertEquals("ejimenez", result.getUser());
		assertEquals(null, result.getProject());
	}

	/** Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_1()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setReleaseNumber("1");
		requirement.setType(RequirementType.Epic);
		requirement.setDescription("Description 1");
		requirement.setName("Name 1");
		requirement.setPriority(RequirementPriority.High);
		RequirementCreation fixture = new RequirementCreation(requirement, "");
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;
		fixture.date = new Date();
		String result = fixture.getBodyString();

		assertEquals("Name initialized to \"Name 1\"\nDescription initialized to \"Description 1\"\nType initialized to \"Epic\"\nPriority initialized to \"High\"\nReleaseNumber initialized to \"1\"", result);
	}

	/** Run the String getLabelString() method test.
	 */
	@Test
	public void testGetLabelString_1()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setReleaseNumber("");
		requirement.setType(RequirementType.Epic);
		requirement.setDescription("");
		requirement.setName("");
		requirement.setPriority(RequirementPriority.High);
		RequirementCreation fixture = new RequirementCreation(requirement, "");
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;
		fixture.date = new Date();
		String result = fixture.getLabelString();

		assertEquals("Requirement created by  on " + dateFormat.format(test_date),result);
	}

	/** Perform pre-test initialization.
	 */
	@Before
	public void setUp(){
		 test_date = new Date();
		 dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
	}

	/** Perform post-test clean-up.
	 */
	@After
	public void tearDown()
		throws Exception {
	}
}