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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import org.junit.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest.AcceptanceTestResult;
import static org.junit.Assert.*;

/** This class contains tests for the class {@link AcceptanceTest}.
 * @generatedBy CodePro at 4/23/13 10:12 PM
 */
public class AcceptanceTestTest {
	
	/** Run the AcceptanceTest(String,String) constructor test.
	 */
	@Test
	public void testAcceptanceTest_1()
		throws Exception {
		String title = "Acceptance Test 1";
		String description = "Description 1";

		AcceptanceTest result = new AcceptanceTest(title, description);

		assertNotNull(result);
		
		assertEquals("Description 1", result.getDescription());
		
		assertEquals("Acceptance Test 1", result.getAcceptanceTestTitle());
		assertEquals("Acceptance Test 1", result.getBodyString());
		
		assertEquals("", result.getUser());
		assertEquals(null, result.getProject());
	}

	/** Run the AcceptanceTest(String,String,AcceptanceTestResult) constructor test.
	 */
	@Test
	public void testAcceptanceTest_2()
		throws Exception {
		String title = "Acceptance Test 2";
		String description = "Description 2";
		AcceptanceTestResult result = AcceptanceTestResult.Failed;

		AcceptanceTest result2 = new AcceptanceTest(title, description, result);

		// add additional test code here
		assertNotNull(result2);
		
		assertEquals("Description 2", result2.getDescription());
		
		assertEquals("Acceptance Test 2", result2.getAcceptanceTestTitle());
		assertEquals("Acceptance Test 2", result2.getBodyString());
		
		assertEquals("", result2.getUser());
		assertEquals(null, result2.getProject());
	}

	/** Run the AcceptanceTest fromJson(String) method test.
	 */
	@Test
	public void testFromJson_1()
		throws Exception {
		String json = "";

		AcceptanceTest result = AcceptanceTest.fromJson(json);

		// add additional test code here
		assertEquals(null, result);
	}

	/** Run the AcceptanceTest fromJson(String) method test.
	 */
	@Test
	public void testFromJson_2()
		throws Exception {
		String json = "";

		AcceptanceTest result = AcceptanceTest.fromJson(json);

		assertEquals(null, result);
	}

	/** Run the AcceptanceTestResult getAcceptanceTestResult() method test.
	 */
	@Test
	public void testGetAcceptanceTestResult_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		AcceptanceTestResult result = fixture.getAcceptanceTestResult();

		assertNotNull(result);
		assertEquals("Failed", result.toString());
		assertEquals("Failed", result.name());
		assertEquals(2, result.ordinal());
	}

	/** Run the String getAcceptanceTestTitle() method test.
	 */
	@Test
	public void testGetAcceptanceTestTitle_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.getAcceptanceTestTitle();

		assertEquals("", result);
	}

	/** Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.getBodyString();

		assertEquals("", result);
	}

	/** Run the String getDescription() method test.
	 */
	@Test
	public void testGetDescription_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.getDescription();

		assertEquals("", result);
	}

	/** Run the String getLabelString() method test.
	 */
	@Test
	public void testGetLabelString_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.getLabelString();
	}

	/** Run the void setAcceptanceTestResult(AcceptanceTestResult) method test.
	 */
	@Test
	public void testSetAcceptanceTestResult_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");
		AcceptanceTestResult result = AcceptanceTestResult.Failed;

		fixture.setAcceptanceTestResult(result);
	}

	/** Run the void setAcceptanceTestTitle(String) method test.
	 */
	@Test
	public void testSetAcceptanceTestTitle_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");
		String title = "";

		fixture.setAcceptanceTestTitle(title);
	}

	/** Run the void setDescription(String) method test.
	 */
	@Test
	public void testSetDescription_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");
		String description = "";

		fixture.setDescription(description);
	}

	/** Run the String toJSON() method test.
	 */
	@Test
	public void testToJSON_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.toJSON();
	}

	/** Run the String toString() method test.
	 */
	@Test
	public void testToString_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.Failed);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.toString();
	}

	/** Perform pre-test initialization.
	 */
	@Before
	public void setUp()
		throws Exception {
		
	}

	/** Perform post-test clean-up.
	 */
	@After
	public void tearDown()
		throws Exception {
	}
}