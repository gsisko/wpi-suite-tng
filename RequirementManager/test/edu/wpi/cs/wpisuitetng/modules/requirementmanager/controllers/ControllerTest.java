package edu.wpi.cs.wpisuitetng.modules.requirementmanager.controllers;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;

public class ControllerTest
{
	@Before
	public void setup()
	{
		
	}
	
	@Test
	public void testFoo()
	{
		assertEquals(4,4);
	}
	
	@Test(expected=ArrayIndexOutOfBoundsException.class)
	public void testException()
	{
		int[] arr = new int[4];
		int num = arr[5];
	}
	

	@After
	public void teardown()
	{
		
	}
}