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

import static org.junit.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.changeset.RequirementEvent.EventType;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest.AcceptanceTestResult;

/**
 * @author Team 5 D13
 *
 */
public class AcceptanceTestUpdateTest {

	private String testName;
	private String userName;
	private String content;
	private String oldResult;
	private String newResult;
	private Requirement test_req;
	
	private Date test_date;
	private DateFormat dateFormat;
	
	@Before
	public void setUp(){
		content = "";
		test_date = new Date();
		dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
		
		test_req = new Requirement();
		userName = "ejimenez";
		
		assertNotNull(test_req);
		assertEquals("ejimenez", userName);
		
		assertEquals("" + dateFormat.format(test_date), dateFormat.format(test_date));
	}
	

	@Test
	public void testGetLabelString() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
	}
	
	
	public void testAcceptanceTestUpdate(){
		EventType type = EventType.ACCEPTANCETESTUPDATE;
		testName = "test Name";
		oldResult = "old Result";
		newResult = "new Result";
		
		assertEquals("test Name", testName);
		assertEquals("old Result", oldResult);
		assertEquals("new Result", newResult);
	}
	
	@Test
	public void testGetBodyString(){
		content += "Result of acceptance test \"";
		content += testName;
		content += "\" changed from \"" + oldResult;
		content += "\" to \"" + newResult + "\"";
		
		assertNotNull(content);
		assertEquals("Result of acceptance test \"null\" changed from \"null\" to \"null\"", content);

	}
	
	
	

}
