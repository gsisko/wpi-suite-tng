package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>UserChangeTest</code> contains tests for the class <code>{@link UserChange}</code>.
 */
public class UserChangeTest {
	private Date test_date;
	private DateFormat dateFormat;
	/**
	 * Run the UserChange(Requirement,Requirement,String) constructor test.
	 */
	@Test
	public void testUserChange_1()
		throws Exception {
		Requirement oldReq = new Requirement();
		oldReq.setUserNames(new ArrayList());
		Requirement newReq = new Requirement();
		newReq.setUserNames(new ArrayList());
		String userName = "ejimenez";

		UserChange result = new UserChange(oldReq, newReq, userName);

		// add additional test code here
		assertNotNull(result);
		assertEquals("", result.getBodyString());
		assertEquals("User changes made by " +userName + " on " + dateFormat.format(test_date), result.getLabelString());
		assertEquals("ejimenez", result.getUser());
		assertEquals(null, result.getProject());
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_1()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "ejimenez");
		fixture.oldUsers = new ArrayList();
		fixture.newUsers = new ArrayList();
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getBodyString() method test.
	 *
	 */
	@Test
	public void testGetBodyString_2()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "");
		fixture.oldUsers = new ArrayList();
		fixture.newUsers = new ArrayList();
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_3()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "");
		fixture.oldUsers = new ArrayList();
		fixture.newUsers = new ArrayList();
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_4()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "");
		fixture.oldUsers = new ArrayList();
		fixture.newUsers = new ArrayList();
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_5()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "");
		ArrayList arrayList = new ArrayList();
		arrayList.add("");
		fixture.oldUsers = arrayList;
		ArrayList arrayList1 = new ArrayList();
		arrayList1.add("");
		fixture.newUsers = arrayList1;
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_6()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "");
		ArrayList arrayList = new ArrayList();
		arrayList.add("");
		fixture.oldUsers = arrayList;
		ArrayList arrayList1 = new ArrayList();
		arrayList1.add("");
		fixture.newUsers = arrayList1;
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_7()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "");
		ArrayList arrayList = new ArrayList();
		arrayList.add("");
		fixture.oldUsers = arrayList;
		ArrayList arrayList1 = new ArrayList();
		arrayList1.add("");
		fixture.newUsers = arrayList1;
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_8()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "");
		ArrayList arrayList = new ArrayList();
		arrayList.add("");
		fixture.oldUsers = arrayList;
		ArrayList arrayList1 = new ArrayList();
		arrayList1.add("");
		fixture.newUsers = arrayList1;
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getLabelString() method test.
	 */
	@Test
	public void testGetLabelString_1()
		throws Exception {
		Requirement requirement = new Requirement();
		requirement.setUserNames(new ArrayList());
		Requirement requirement1 = new Requirement();
		requirement1.setUserNames(new ArrayList());
		UserChange fixture = new UserChange(requirement, requirement1, "");
		fixture.oldUsers = new ArrayList();
		fixture.newUsers = new ArrayList();
		fixture.date = new Date();
		fixture.type = RequirementEvent.EventType.ACCEPTANCETEST;

		String result = fixture.getLabelString();

		// add additional test code here
		assertEquals("User changes made by  on " + dateFormat.format(test_date), result);
	}

	/**
	 * Perform pre-test initialization.
	 */
	@Before
	public void setUp() {
		test_date = new Date();
		dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
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