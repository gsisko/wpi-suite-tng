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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration.IterationListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListPanel.Mode;

/**
 * This tabbed pane will appear as the main content of the Requirements tab.
 * It starts out showing the single Dashboard tab.
 */
@SuppressWarnings("serial")
public class ListTabView extends JTabbedPane {
	
	private ListPanel parent;
	private FilterListPanel filterList;
	private IterationListPanel iterationList;
	
	public ListTabView(ListPanel view) {
		
		this.parent = view;
		
		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3)); //TODO: Do we need?
		filterList = new FilterListPanel(parent);
		addTab("Filters", new ImageIcon(), filterList, "List of Filters");
		iterationList = new IterationListPanel(parent);
		addTab("Iterations", new ImageIcon(), iterationList, "List of Iterations");
		
		this.setPreferredSize(new Dimension(190, 500));
	}
	
	@Override
	public void setComponentAt(int index, Component component) {
		super.setComponentAt(index, component);
		fireStateChanged(); // hack to make sure toolbar knows if component changes
	}
	
	@Override
	public void setSelectedIndex(int index) {
		super.setSelectedIndex(index);
		if (getComponentAt(index) instanceof FilterListPanel) {
			parent.setMode(Mode.FILTER);
		}
		else {
			parent.setMode(Mode.ITERATION);
		}
	}

	/**
	 * @return the listPanel
	 */
	public FilterListPanel getFilterList() {
		return filterList;
	}

	/**
	 * @param listPanel the listPanel to set
	 */
	public void setFilterList(FilterListPanel filterList) {
		this.filterList = filterList;
	}
	
	/**
	 * @return the listPanel
	 */
	public IterationListPanel getIterationList() {
		return iterationList;
	}

	/**
	 * @param listPanel the listPanel to set
	 */
	public void setIterationList(IterationListPanel iterationList) {
		this.iterationList = iterationList;
	}
	
}
