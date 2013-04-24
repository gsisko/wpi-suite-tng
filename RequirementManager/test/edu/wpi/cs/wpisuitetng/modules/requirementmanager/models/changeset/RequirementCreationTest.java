package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import java.text.DateFormat;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementPriority;
import static org.junit.Assert.*;

/**
 * The class <code>RequirementCreationTest</code> contains tests for the class <code>{@link RequirementCreation}</code>.
 * @author Team 5 D13
 */
public class RequirementCreationTest {
	/**
	 * Run the RequirementCreation(Requirement,String) constructor test.
	 */
	
	private Date test_date;
	private DateFormat dateFormat;
	
	@Test
	public void testRequirementCreation_1() {
		Requirement requirement = new Requirement();
		requirement.setReleaseNumber("2");
		requirement.setType(RequirementType.Epic);
		requirement.setDescription("Desc 2");
		requirement.setName("Req 2");
		requirement.setPriority(RequirementPriority.High);
		String theCreator = "ejimenez";

		RequirementCreation result = new RequirementCreation(requirement, theCreator);

		// add additional test code here
		assertNotNull(result);
		assertEquals("Requirement created by " +theCreator + " on " +dateFormat.format(test_date), result.getLabelString());
		assertEquals("Name initialized to \"Req 2\"\nDescription initialized to \"Desc 2\"\nType initialized to \"Epic\"\nPriority initialized to \"High\"\nReleaseNumber initialized to \"2\"", result.getBodyString());
		assertEquals("ejimenez", result.getUser());
		assertEquals(null, result.getProject());
	}

	/**
	 * Run the String getBodyString() method test.
	 */
	@Test
	public void testGetBodyString_1() {
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

		// add additional test code here
		assertEquals("Name initialized to \"Name 1\"\nDescription initialized to \"Description 1\"\nType initialized to \"Epic\"\nPriority initialized to \"High\"\nReleaseNumber initialized to \"1\"", result);
	}

	/**
	 * Run the String getLabelString() method test.
	 */
	@Test
	public void testGetLabelString_1() {
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
		// add additional test code here
	}

	/**
	 * Perform pre-test initialization.
	 */
	@Before
	public void setUp(){
		 test_date = new Date();
		 dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
//	public void setUp() throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 */
	@After
	public void tearDown() {
		// Add additional tear down code here
	}
}