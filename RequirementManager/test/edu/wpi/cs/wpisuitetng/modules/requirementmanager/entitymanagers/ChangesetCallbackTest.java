package edu.wpi.cs.wpisuitetng.modules.requirementmanager.entitymanagers;

import org.junit.*;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.Model;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.RequirementChangeset;
import static org.junit.Assert.*;

/**
 * The class <code>ChangesetCallbackTest</code> contains tests for the class <code>{@link ChangesetCallback}</code>.
 *
 * @generatedBy CodePro at 3/20/13 2:22 PM
 * @author Robert Smieja
 * @version $Revision: 1.0 $
 */
public class ChangesetCallbackTest {
	/**
	 * Run the ChangesetCallback(RequirementChangeset) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testChangesetCallback_1()
		throws Exception {
		RequirementChangeset changeset = new RequirementChangeset();

		ChangesetCallback result = new ChangesetCallback(changeset);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Object call(Model,Model,String,Object,Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testCall_1()
		throws Exception {
		ChangesetCallback fixture = new ChangesetCallback(new RequirementChangeset());
		Model source = new Requirement();
		Model destination = new Requirement();
		String fieldName = "";
		Object sourceValue = new Object();
		Object destinationValue = new Object();

		Object result = fixture.call(source, destination, fieldName, sourceValue, destinationValue);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Object call(Model,Model,String,Object,Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testCall_2()
		throws Exception {
		ChangesetCallback fixture = new ChangesetCallback(new RequirementChangeset());
		Model source = new Requirement();
		Model destination = new Requirement();
		String fieldName = "";
		Object sourceValue = new Object();
		Object destinationValue = new Object();

		Object result = fixture.call(source, destination, fieldName, sourceValue, destinationValue);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Run the Object call(Model,Model,String,Object,Object) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Test
	public void testCall_3()
		throws Exception {
		ChangesetCallback fixture = new ChangesetCallback(new RequirementChangeset());
		Model source = new Requirement();
		Model destination = new Requirement();
		String fieldName = "";
		Object sourceValue = new Object();
		Object destinationValue = new Object();

		Object result = fixture.call(source, destination, fieldName, sourceValue, destinationValue);

		// add additional test code here
		assertNotNull(result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 3/20/13 2:22 PM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(ChangesetCallbackTest.class);
	}
}