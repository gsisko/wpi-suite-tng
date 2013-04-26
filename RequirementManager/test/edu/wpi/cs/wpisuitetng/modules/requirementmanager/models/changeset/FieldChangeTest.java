package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>FieldChangeTest</code> contains tests for the class <code>{@link FieldChange}</code>.
 * @author Team 5 D13
 */
public class FieldChangeTest {
	/**
	 * Run the FieldChange(T,T) constructor test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testFieldChange_1()
		throws Exception {

		FieldChange result = new FieldChange(null, null);

		// add additional test code here
		assertNotNull(result);
		assertEquals(null, result.getNewValue());
		assertEquals(null, result.getOldValue());
	}

	/**
	 * Run the Object getNewValue() method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetNewValue_1()
		throws Exception {
		FieldChange fixture = new FieldChange(null, null);

		Object result = fixture.getNewValue();

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Run the Object getOldValue() method test.
	 *
	 * @throws Exception
	 */
	@Test
	public void testGetOldValue_1()
		throws Exception {
		FieldChange fixture = new FieldChange(null, null);

		Object result = fixture.getOldValue();

		// add additional test code here
		assertEquals(null, result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
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
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}
}