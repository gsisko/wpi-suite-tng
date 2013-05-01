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

import org.junit.*;
import static org.junit.Assert.*;

/**This class contains tests for the class {@link FieldChange}
 */
public class FieldChangeTest {
	
	/** Run the FieldChange(T,T) constructor test.
	 * @throws Exception
	 */
	@Test
	public void testFieldChange_1()
		throws Exception {
		FieldChange result = new FieldChange(null, null);
		
		assertNotNull(result);
		assertEquals(null, result.getNewValue());
		assertEquals(null, result.getOldValue());
	}

	/** Run the Object getNewValue() method test.
	 * @throws Exception
	 */
	@Test
	public void testGetNewValue_1()
		throws Exception {
		FieldChange fixture = new FieldChange(null, null);
		Object result = fixture.getNewValue();

		assertEquals(null, result);
	}

	/** Run the Object getOldValue() method test.
	 * @throws Exception
	 */
	@Test
	public void testGetOldValue_1()
		throws Exception {
		FieldChange fixture = new FieldChange(null, null);

		Object result = fixture.getOldValue();

		assertEquals(null, result);
	}

	/** Perform pre-test initialization.
	 * @throws Exception   if the initialization fails for some reason
	 */
	@Before
	public void setUp()
		throws Exception {
	}

	/** Perform post-test clean-up.
	 * @throws Exception if the clean-up fails for some reason
	 */
	@After
	public void tearDown()
		throws Exception {
	}
}