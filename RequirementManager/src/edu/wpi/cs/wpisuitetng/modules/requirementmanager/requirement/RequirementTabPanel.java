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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest.AcceptanceTestTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.note.NoteTab;

/** This is a JTabbedPane that is held within the RequirementTab and contains all the
 * GUIs used for Requirement Notes (a NoteTab), Events (a HistoryTab),
 * Assigning Users (a UserChooserTab), Acceptance Tests (a AcceptanceTestTab),
 * and Attachments (a AttachementTab).
 */
@SuppressWarnings("serial")
public class RequirementTabPanel extends JTabbedPane {

	/** The "parent" of this panel, the RequirementTab that holds this panel */
	private RequirementTab parent;

	/** The tab panel that contains the GUI for everything involving the creation and display of Notes*/
	private NoteTab notePanel;

	/** The tab panel that contains the GUI for everything involving the creation, display, and editing of AcceptanceTests */
	private AcceptanceTestTab acceptanceTestPanel;

	/** The tab panel that contains the GUI for displaying the history of the current Requirement */
	private HistoryTab historyPanel;

	/** The tab panel that contains the GUI for everything involving the assignment/unassignment of Users to this Requirement*/
	private UserChooserTab userChooserPanel;

	/** The constructor for this RequirementTabPanel tabbed pane. Construct the tabs and add them to this pane.
	 * @param view The RequirementTab that contains this RequirementTabPanel (the "parent" of this panel)
	 */
	public RequirementTabPanel(RequirementTab view) {
		parent = view; //Set the parent

		//Set the layout of this panel
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);

		//Create and add the HistoryTab "historyPanel"
		historyPanel = new HistoryTab(parent);
		addTab("History", new ImageIcon(), historyPanel, "Event history for the current requirement");

		//Create and add the NoteTab "notePanel"
		notePanel = new NoteTab(parent);
		addTab("Notes", new ImageIcon(), notePanel, "Notes for the current requirement");

		//Create and add the UserChooserTab "userChooserPanel"
		userChooserPanel = new UserChooserTab(parent); 
		addTab("Users", new ImageIcon(), userChooserPanel, "Users assigned to the current requirement");

		//Create and add the AcceptanceTestTab "acceptanceTestPanel"
		acceptanceTestPanel = new AcceptanceTestTab(parent);
		addTab("Acceptance Tests", new ImageIcon(), acceptanceTestPanel, "Acceptance tests for the current requirement");

		this.setPreferredSize(new Dimension(450, 500)); // Set the preferred size of this panel
	}

	/**
	 * @return notePanel The "notePanel" NoteTab
	 */
	public NoteTab getNotePanel() {
		return notePanel;
	}

	/**
	 * @param notePanel The "notePanel" (an instance of NoteTab) to set
	 */
	public void setNotePanel(NoteTab notePanel) {
		this.notePanel = notePanel;
	}

	/**
	 * @return acceptanceTestPanel The "acceptanceTestPanel" AcceptanceTestTab
	 */
	public AcceptanceTestTab getAcceptanceTestPanel() {
		return acceptanceTestPanel;
	}

	/**
	 * @param acceptanceTestPanel The "acceptanceTestPanel" (an instance of AcceptanceTestTab) to set
	 */
	public void setAcceptanceTestPanel(AcceptanceTestTab acceptanceTestPanel) {
		this.acceptanceTestPanel = acceptanceTestPanel;
	}

	/**
	 * @return historyPanel The "historyPanel" HistoryTab
	 */
	public HistoryTab getHistoryPanel() {
		return historyPanel;
	}

	/**
	 * @param historyPanel The "historyPanel" (an instance of HistoryTab) to set
	 */
	public void setHistoryPanel(HistoryTab historyPanel) {
		this.historyPanel = historyPanel;
	}

	/**
	 * @return userChooserPanel The "userChooserPanel" UserChooserTab
	 */
	public UserChooserTab getUserChooserPanel() {
		return userChooserPanel;
	}

	/**
	 * @param userChooserPanel The "userChooserPanel" (an instance of UserChooserTab) to set
	 */
	public void setUserChooserPanel(UserChooserTab userChooserPanel) {
		this.userChooserPanel = userChooserPanel;
	}
}
