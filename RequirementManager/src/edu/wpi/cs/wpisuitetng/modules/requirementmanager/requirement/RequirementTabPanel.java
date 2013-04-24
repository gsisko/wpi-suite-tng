// $codepro.audit.disable unnecessaryOverride
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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.attachment.AttachmentTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.note.NoteTab;

/**
 * This tabbed pane will appear as the main content of the Requirements tab.
 * It starts out showing the single Dashboard tab.
 */
@SuppressWarnings("serial")
public class RequirementTabPanel extends JTabbedPane {

	private RequirementTab parent;
	private NoteTab notePanel;
	private AcceptanceTestTab acceptanceTestPanel;
	private AttachmentTab attachmentPanel;
	private HistoryTab historyPanel;

	private SubRequirementTab subRequirementPanel;

	private UserChooserTab userChooserPanel;


	public RequirementTabPanel(RequirementTab view) {

		this.parent = view;

		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		
		historyPanel = new HistoryTab(parent);
		addTab("History", new ImageIcon(), historyPanel, "Event history for the current requirement");
		
		notePanel = new NoteTab(parent);
		addTab("Notes", new ImageIcon(), notePanel, "Notes for the current requirement");
		
		acceptanceTestPanel = new AcceptanceTestTab(parent);
		addTab("Acceptance Tests", new ImageIcon(), acceptanceTestPanel, "Acceptance tests for the current requirement");
		
	
		subRequirementPanel= new SubRequirementTab(parent);
		addTab("Sub-Requirements", new ImageIcon(), subRequirementPanel, "Sub-Requirements for the current requirement");

		attachmentPanel = new AttachmentTab(parent);
		addTab("Attachments", new ImageIcon(), attachmentPanel, "Attachments for the current requirement");
		
		userChooserPanel = new UserChooserTab(parent); 
		addTab("Users", new ImageIcon(), userChooserPanel, "Users assigned to the current requirement");

		this.setPreferredSize(new Dimension(450, 500));
	}

	@Override
	public void setSelectedIndex(int index) {
		super.setSelectedIndex(index);
		if (getComponentAt(index) instanceof SubRequirementTab) {
			subRequirementPanel.getSubController().refreshData();
		}
	}
	
	/**
	 * @return the notePanel
	 */
	public NoteTab getNotePanel() {
		return notePanel;
	}

	/**
	 * @param notePanel the notePanel to set
	 */
	public void setNotePanel(NoteTab notePanel) {
		this.notePanel = notePanel;
	}

	/**
	 * @return the acceptanceTestPanel
	 */
	public AcceptanceTestTab getAcceptanceTestPanel() {
		return acceptanceTestPanel;
	}

	/**
	 * @param acceptanceTestPanel the acceptanceTestPanel to set
	 */
	public void setAcceptanceTestPanel(AcceptanceTestTab acceptanceTestPanel) {
		this.acceptanceTestPanel = acceptanceTestPanel;
	}
	
	/**
	 * @return the historyPanel
	 */
	public HistoryTab getHistoryPanel() {
		return historyPanel;
	}

	/**
	 * @param historyPanel the historyPanel to set
	 */
	public void setHistoryPanel(HistoryTab historyPanel) {
		this.historyPanel = historyPanel;
	}


	public SubRequirementTab getSubRequirementPanel() {
		return subRequirementPanel;
	}

	public void setSubRequirementPanel(SubRequirementTab subRequirementPanel) {
		this.subRequirementPanel = subRequirementPanel;
	}


	public AttachmentTab getAttachmentPanel() {
		return attachmentPanel;
	}

	public void setAttachmentPanel(AttachmentTab attachmentPanel) {
		this.attachmentPanel = attachmentPanel;
	}

	/**
	 * @return the userChooserPanel
	 */
	public UserChooserTab getUserChooserPanel() {
		return userChooserPanel;
	}

	/**
	 * @param userChooserPanel the userChooserPanel to set
	 */
	public void setUserChooserPanel(UserChooserTab userChooserPanel) {
		this.userChooserPanel = userChooserPanel;
	}
}
