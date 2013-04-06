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

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration.IterationListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab.Mode;

/**
 * This tabbed pane will appear as the main content of the Requirements tab.
 * It starts out showing the single Dashboard tab.
 */
@SuppressWarnings("serial")
public class ListTabPanel extends JTabbedPane {

	private ListTab parent;
	private FilterListTab filterList;
	private IterationListTab iterationList;

	public ListTabPanel(ListTab view) {

		this.parent = view;

		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		filterList = new FilterListTab(parent);
		addTab("Filters", new ImageIcon(), filterList, "List of Filters");
		iterationList = new IterationListTab(parent);
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
		if (getComponentAt(index) instanceof FilterListTab) {
			parent.setMode(Mode.FILTER);
		}
		else {
			parent.setMode(Mode.ITERATION);
		}
	}

	/**
	 * @return the listPanel
	 */
	public FilterListTab getFilterList() {
		return filterList;
	}

	/**
	 * @param listPanel the listPanel to set
	 */
	public void setFilterList(FilterListTab filterList) {
		this.filterList = filterList;
	}

	/**
	 * @return the listPanel
	 */
	public IterationListTab getIterationList() {
		return iterationList;
	}

	/**
	 * @param listPanel the listPanel to set
	 */
	public void setIterationList(IterationListTab iterationList) {
		this.iterationList = iterationList;
	}

}
