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

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration.IterationListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListPanel.Mode;

/**
 * This tabbed pane will appear as the main content of the Requirements tab.
 * It starts out showing the single Dashboard tab.
 */
@SuppressWarnings("serial")
public class RequirementTabPanel extends JTabbedPane {
	
//	private ListPanel parent;
//	private NotePanel filterList;
	
	public RequirementTabPanel(){
//	public RequirementTabView(ListPanel view) {
		
//		this.parent = view;
		
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); //TODO: Do we need?
		
		addTab("Notes", new ImageIcon(), new JPanel(), "Notes for the current requirement");
		
		this.setPreferredSize(new Dimension(600, 500));
	}
	
	@Override
	public void setSelectedIndex(int index) {
		super.setSelectedIndex(index);
		if (getComponentAt(index) instanceof FilterListPanel) {
//			parent.setMode(Mode.FILTER);
		}
		else {
//			parent.setMode(Mode.ITERATION);
		}
	}

//	/**
//	 * @return the listPanel
//	 */
//	public FilterListPanel getFilterList() {
//		return filterList;
//	}
//
//	/**
//	 * @param listPanel the listPanel to set
//	 */
//	public void setFilterList(FilterListPanel filterList) {
//		this.filterList = filterList;
//	}
//	
//	/**
//	 * @return the listPanel
//	 */
//	public IterationListPanel getIterationList() {
//		return iterationList;
//	}

//	/**
//	 * @param listPanel the listPanel to set
//	 */
//	public void setIterationList(IterationListPanel iterationList) {
//		this.iterationList = iterationList;
//	}
	
}
