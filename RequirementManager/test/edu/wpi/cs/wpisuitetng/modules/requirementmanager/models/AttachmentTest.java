package edu.wpi.cs.wpisuitetng.modules.requirementmanager.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>AttachmentTest</code> contains tests for the class <code>{@link Attachment}</code>.
 *
 * @generatedBy CodePro at 4/23/13 11:07 PM
 * @author Team 5 D13
 * @version $Revision: 1.0 $
 */
public class AttachmentTest {
	/**
	 * Run the Attachment(String,int) constructor test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	
	private Date test_date;
	private DateFormat dateFormat;
	
	@Test
	public void testAttachment_1()
		throws Exception {
		String fileName = "Attachment1.txt";
		int fileSize = 1;

		Attachment result = new Attachment(fileName, fileSize);

		// add additional test code here
		assertNotNull(result);
		assertEquals("Attachment added by  on " +dateFormat.format(test_date), result.toString());
		assertEquals("Attachment1.txt", result.getFileName());
		assertEquals(1, result.getFileSize());
		assertEquals("Attachment1.txt", result.getBodyString());
		assertEquals("Attachment added by  on " + dateFormat.format(test_date), result.getLabelString());
		//assertEquals("{\"fileName\":\"\",\"fileSize\":1,\"attachmentPartIds\":[],\"date\":\"Apr 23, 2013 11:07:48 PM\",\"userName\":\"\",\"type\":\"ATTACHMENT\",\"permissionMap\":{}}", result.toJSON());
		assertEquals("", result.getUser());
		assertEquals(null, result.getProject());
	}

	/**
	 * Run the ArrayList<Integer> getAttachmentPartIds() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testGetAttachmentPartIds_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());

		ArrayList<Integer> result = fixture.getAttachmentPartIds();

		// add additional test code here
		assertNotNull(result);
		assertEquals(0, result.size());
	}

	/**
	 * Run the String getBodyString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testGetBodyString_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());

		String result = fixture.getBodyString();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the String getFileName() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testGetFileName_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());

		String result = fixture.getFileName();

		// add additional test code here
		assertEquals("", result);
	}

	/**
	 * Run the int getFileSize() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testGetFileSize_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());

		int result = fixture.getFileSize();

		// add additional test code here
		assertEquals(1, result);
	}

	/**
	 * Run the String getLabelString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testGetLabelString_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());

		String result = fixture.getLabelString();

		// add additional test code here
		assertEquals("Attachment added by  on " + dateFormat.format(test_date), result);
	}

	/**
	 * Run the void setAttachmentPartIds(ArrayList<Integer>) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testSetAttachmentPartIds_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());
		ArrayList<Integer> attachmentPartIds = new ArrayList();

		fixture.setAttachmentPartIds(attachmentPartIds);

		// add additional test code here
	}

	/**
	 * Run the void setFileName(String) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testSetFileName_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());
		String fileName = "";

		fixture.setFileName(fileName);

		// add additional test code here
	}

	/**
	 * Run the void setFileSize(int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testSetFileSize_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());
		int fileSize = 1;

		fixture.setFileSize(fileSize);

		// add additional test code here
	}

	/**
	 * Run the String toJSON() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testToJSON_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());

		String result = fixture.toJSON();

		// add additional test code here
		//assertEquals("{\"fileName\":\"\",\"fileSize\":1,\"attachmentPartIds\":[],\"date\":\"Apr 23, 2013 11:07:50 PM\",\"userName\":\"\",\"type\":\"ATTACHMENT\",\"permissionMap\":{}}", result);
	}

	/**
	 * Run the String toString() method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Test
	public void testToString_1()
		throws Exception {
		Attachment fixture = new Attachment("", 1);
		fixture.setAttachmentPartIds(new ArrayList());

		String result = fixture.toString();

		// add additional test code here
		assertEquals("Attachment added by  on " + dateFormat.format(test_date), result);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@Before
	public void setUp()
	{
		test_date = new Date();
		dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm a");
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 4/23/13 11:07 PM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}
}