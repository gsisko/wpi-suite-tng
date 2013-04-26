package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import org.junit.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest.AcceptanceTestResult;
import static org.junit.Assert.*;

/**
 * The class <code>AcceptanceTestTest</code> contains tests for the class <code>{@link AcceptanceTest}</code>.
 *
 * @generatedBy CodePro at 4/23/13 10:12 PM
 */
public class AcceptanceTestTest {
	/**
	 * Run the AcceptanceTest(String,String) constructor test.
	 */
	@Test
	public void testAcceptanceTest_1()
		throws Exception {
		String title = "Acceptance Test 1";
		String description = "Description 1";

		AcceptanceTest result = new AcceptanceTest(title, description);

		// add additional test code here
		assertNotNull(result);
		
		assertEquals("Description 1", result.getDescription());
		
		assertEquals("Acceptance Test 1", result.getAcceptanceTestTitle());
		assertEquals("Description 1", result.getBodyString());
		
		assertEquals("", result.getUser());
		assertEquals(null, result.getProject());
	}

	/**
	 * Run the AcceptanceTest(String,String,AcceptanceTestResult) constructor test.
	 */
	@Test
	public void testAcceptanceTest_2()
		throws Exception {
		String title = "Acceptance Test 2";
		String description = "Description 2";
		AcceptanceTestResult result = AcceptanceTestResult.FAILED;

		AcceptanceTest result2 = new AcceptanceTest(title, description, result);

		// add additional test code here
		assertNotNull(result2);
		
		assertEquals("Description 2", result2.getDescription());
		
		assertEquals("Acceptance Test 2", result2.getAcceptanceTestTitle());
		assertEquals("Description 2", result2.getBodyString());
		
		assertEquals("", result2.getUser());
		assertEquals(null, result2.getProject());
	}

	/**
	 * Run the AcceptanceTest fromJson(String) method test.
	 */
	@Test
	public void testFromJson_1()
		throws Exception {
		String json = "";

		AcceptanceTest result = AcceptanceTest.fromJson(json);

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the AcceptanceTest fromJson(String) method test.
	 */
	@Test
	public void testFromJson_2()
		throws Exception {
		String json = "";

		AcceptanceTest result = AcceptanceTest.fromJson(json);

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the AcceptanceTestResult getAcceptanceTestResult() method test.
	 */
	@Test
	public void testGetAcceptanceTestResult_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		AcceptanceTestResult result = fixture.getAcceptanceTestResult();

		// add additional test code here
		assertNotNull(result);
		assertEquals("FAILED", result.toString());
		assertEquals("FAILED", result.name());
		assertEquals(2, result.ordinal());
	}

	/**
	 * Run the String getAcceptanceTestTitle() method test.
	 */
	@Test
	public void testGetAcceptanceTestTitle_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.getAcceptanceTestTitle();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getDescription() method test.
	 */
	@Test
	public void testGetDescription_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.getDescription();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getLabelString() method test.
	 */
	@Test
	public void testGetLabelString_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.getLabelString();

		// add additional test code here
	}

	/**
	 * Run the void setAcceptanceTestResult(AcceptanceTestResult) method test.
	 */
	@Test
	public void testSetAcceptanceTestResult_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");
		AcceptanceTestResult result = AcceptanceTestResult.FAILED;

		fixture.setAcceptanceTestResult(result);

		// add additional test code here
	}

	/**
	 * Run the void setAcceptanceTestTitle(String) method test.
	 */
	@Test
	public void testSetAcceptanceTestTitle_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");
		String title = "";

		fixture.setAcceptanceTestTitle(title);

		// add additional test code here
	}

	/**
	 * Run the void setDescription(String) method test.
	 */
	@Test
	public void testSetDescription_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");
		String description = "";

		fixture.setDescription(description);

		// add additional test code here
	}

	/**
	 * Run the String toJSON() method test.
	 */
	@Test
	public void testToJSON_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.toJSON();

		// add additional test code here
	}

	/**
	 * Run the String toString() method test.
	 *
	 */
	@Test
	public void testToString_1()
		throws Exception {
		AcceptanceTest fixture = new AcceptanceTest("", "", AcceptanceTestResult.FAILED);
		fixture.setAcceptanceTestTitle("");
		fixture.setDescription("");

		String result = fixture.toString();

		// add additional test code here

	}

	/**
	 * Perform pre-test initialization.
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}
}