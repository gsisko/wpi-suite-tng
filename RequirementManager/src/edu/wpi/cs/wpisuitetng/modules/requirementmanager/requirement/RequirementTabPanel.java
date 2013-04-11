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

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

/**
 * This tabbed pane will appear as the main content of the Requirements tab.
 * It starts out showing the single Dashboard tab.
 */
@SuppressWarnings("serial")
public class RequirementTabPanel extends JTabbedPane {
	
	private RequirementTab parent;
	private NoteTab notePanel;
	
	public RequirementTabPanel(RequirementTab view) {
		
		this.parent = view;
		
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		//setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); //TODO: Do we need?
		
		notePanel = new NoteTab(parent);
		addTab("Notes", new ImageIcon(), notePanel, "Notes for the current requirement");
		
		this.setPreferredSize(new Dimension(450, 500));
	}
	
	@Override
	public void setSelectedIndex(int index) {
		super.setSelectedIndex(index);
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
	
}
