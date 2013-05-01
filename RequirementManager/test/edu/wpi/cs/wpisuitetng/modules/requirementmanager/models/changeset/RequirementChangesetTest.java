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
import java.util.HashMap;
import java.util.Map;
import org.junit.*;
import static org.junit.Assert.*;

/** This class contains tests for the class {@link RequirementChangeset}
 */
public class RequirementChangesetTest {
	private Date test_date;
	private DateFormat dateFormat;
	
	/** Run the RequirementChangeset(String) constructor test.
	 */
	@Test
	public void testRequirementChangeset_1()
		throws Exception {
		String user = "ejimenez";
		RequirementChangeset result = new RequirementChangeset(user);

		assertNotNull(result);
		assertEquals("", result.getBodyString());
		assertEquals("Changes made by " +user + " on " + dateFormat.format(test_date), result.getLabelString());
		assertEquals("ejimenez", result.getUser());
		assertEquals(null, result.getProject());
	}

	/** Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_1()
		throws Exception {
		RequirementChangeset fixture = new RequirementChangeset("change 2");
		fixture.setChanges(new HashMap());
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;
		String result = fixture.getBodyString();

		assertEquals("", result);
	}

	/** Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_2()
		throws Exception {
		RequirementChangeset fixture = new RequirementChangeset("change 1");
		fixture.setChanges(new HashMap());
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;
		String result = fixture.getBodyString();

		assertEquals("", result);
	}

	/** Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_3()
		throws Exception {
		RequirementChangeset fixture = new RequirementChangeset("change 3");
		fixture.setChanges(new HashMap());
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;
		String result = fixture.getBodyString();

		assertEquals("", result);
	}

	/** Run the Map<String, FieldChange<Object>> getChanges() method test.
	 */
	@Test
	public void testGetChanges_1()
		throws Exception {
		RequirementChangeset fixture = new RequirementChangeset("");
		fixture.setChanges(new HashMap());
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;
	}

	/** Run the String getLabelString() method test.
	 */
	@Test
	public void testGetLabelString_1()
		throws Exception {
		RequirementChangeset fixture = new RequirementChangeset("change 4");
		fixture.setChanges(new HashMap());
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;
		String result = fixture.getLabelString();

		assertEquals("Changes made by change 4 on " + dateFormat.format(test_date), result);
	}

	/** Run the void setChanges(Map<String,FieldChange<?>>) method test.
	 */
	@Test
	public void testSetChanges_1()
		throws Exception {
		RequirementChangeset fixture = new RequirementChangeset("");
		fixture.setChanges(new HashMap());
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;
		Map<String, FieldChange<Object>> changes = new HashMap();
	}

	/** Perform pre-test initialization.
	 */
	@Before
	public void setUp() {
		test_date = new Date();
		dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
	}

	/** Perform post-test clean-up.
	 */
	@After
	public void tearDown()
	{
	}
}