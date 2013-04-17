/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *		Robert Dabrowski
 *		Danielle LaRose
 *		Edison Jimenez
 *		Christian Gonzalez
 *		Mike Calder
 *		John Bosworth
 *		Paula Rudy
 *		Gabe Isko
 *		Bangyan Zhang
 *		Cassie Hudson
 *		Robert Smieja
 *		Alex Solomon
 *		Brian Hetherman
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Attachment;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;

/**
 * This panel is added to the RequirementTabPanel and 
 * contains all the gui components involving notes:
 * -a panel to hold the list of notes
 * -a text area for a user to input a new note
 * -a save button to save the new note
 */
@SuppressWarnings({"serial"})
public class AttachmentTab extends JPanel {
	
	//The fillable component

	//Setting up button
	private JButton saveButton;

	//The variables to hold information about the current instance of the panel
	private File currentAttachment;//Stores the note currently open for editing or creation

	// the parent might change to a Requirement View depending on how the UI is implemented
	private RequirementTab parent; //Stores the RequirementPanel that contains the panel

	//A boolean indicating if input is enabled on the form 
	protected boolean inputEnabled;
	
	private ListOfAttachmentPanel attachmentList;
	
	private AttachmentListModel attachmentListModel;
	
	//A scroll pane to hold the "noteList"
	JScrollPane listScrollPane;

	/**
	 * The constructor for NotePanel;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param reqPanelParent	The parent of this tab
	 */
	public AttachmentTab(RequirementTab reqPanelParent) {

		parent = reqPanelParent; //Set the RequirementPanel that contains this instance of this panel

		// Create and set the layout manager
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		//Set an empty border for spacing
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); 

		// Construct the noteListmodel
		attachmentListModel = new AttachmentListModel();
		
		ArrayList<Attachment> attachments = parent.getCurrentRequirement().getAttachments();
		for (int i = 0; i < attachments.size(); i++) {
			attachmentListModel.addFile(attachments.get(i));
		}


		// Put the listbox in a scroll pane
		listScrollPane = new JScrollPane(attachmentList);

		// Construct components to be displayed
		// a list component here
		saveButton = new JButton("Add File");

		// Set the txtMessage component to wrap



		// Set dimensions of the panel elements
		listScrollPane.setPreferredSize(new Dimension(580, 300));
		saveButton.setPreferredSize(new Dimension(120, 40));

		if ((parent.getMode()) == Mode.CREATE)
		{
			saveButton.setEnabled(false);
		}

		// Add components
		this.add(listScrollPane); //add the noteList, in the listScrollPane, to the panel
		this.add(Box.createRigidArea(new Dimension(0,6))); //Put some vertical space between these components
		//this.add(scrollMessage); // add the txtMessage box (in the scroll pane)  to the panel
		//this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // adds the save button to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));

		saveButton.setAlignmentX(CENTER_ALIGNMENT); //Set the horizontal alignment of the save button to the center of this panel


	}


	public void setUp() {
		// Set controller for save button
		Boolean restoreEnableStateBool = saveButton.isEnabled(); //store the enable state of the save button, since adding an action defaults the enable to true
		saveButton.setAction(new SaveAttachmentAction(parent.getParent().getController()));
		saveButton.setEnabled(restoreEnableStateBool);//restore the previously stored enable state
	}

	/**
	 * Returns a boolean representing whether or not input is enabled for the NotePanel.
	 * @return inputEnabled A boolean representing whether or not input is enabled for the NotePanel.
	 */
	public boolean getInputEnabled() {
		return inputEnabled;
	}

	/**
	 * This returns the JButton saveButton
	 * @return the saveButton JButton
	 */
	public JButton getSaveButton() {
		return saveButton;
	}

	/**
	 * This adds a new note to the noteListModel,
	 * and then recreates and redisplays the noteList
	 * panel.
	 * @param newNote the note to be added
	 */
	public void addAttachmentToList(Attachment newAttachment){
		this.removeAll();
		attachmentListModel.addFile(newAttachment);
		attachmentList  = new ListOfAttachmentPanel(attachmentListModel);

		listScrollPane = new JScrollPane(attachmentList);

		listScrollPane.setPreferredSize(new Dimension(580, 300));	
		
		listScrollPane.setEnabled(true);
		listScrollPane.setBackground(Color.WHITE);

		// Add components
		this.add(listScrollPane); //add the noteList, in the listScrollPane, to the panel
		this.add(Box.createRigidArea(new Dimension(0,6))); //Put some vertical space between these components
		//this.add(scrollMessage); // add the txtMessage box (in the scroll pane)  to the panel
		//this.add(Box.createRigidArea(new Dimension(0,6)));
		this.add(saveButton); // adds the save button to the panel
		this.add(Box.createRigidArea(new Dimension(0,6)));

	}


	/**
	 * @return the attachmentList
	 */
	public ListOfAttachmentPanel getAttachmentList() {
		return attachmentList;
	}


	/**
	 * @param attachmentList the attachmentList to set
	 */
	public void setAttachmentList(ListOfAttachmentPanel attachmentList) {
		this.attachmentList = attachmentList;
	}


	/**
	 * @return the currentAttachment
	 */
	public File getCurrentAttachment() {
		return currentAttachment;
	}


	/**
	 * @param currentAttachment the currentAttachment to set
	 */
	public void setCurrentAttachment(File currentAttachment) {
		this.currentAttachment = currentAttachment;
	}


	/**
	 * @return the attachmentListModel
	 */
	public AttachmentListModel getAttachmentListModel() {
		return attachmentListModel;
	}


	/**
	 * @param attachmentListModel the attachmentListModel to set
	 */
	public void setAttachmentListModel(AttachmentListModel attachmentListModel) {
		this.attachmentListModel = attachmentListModel;
	}

}
