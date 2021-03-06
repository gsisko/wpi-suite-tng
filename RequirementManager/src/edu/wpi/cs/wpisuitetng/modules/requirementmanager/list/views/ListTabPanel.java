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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter.FilterListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.iteration.IterationListTab;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab.Mode;

/** A tabbed pane that contains the other content of the RequirementManager module
 * as tabs. It initializes a single ListTab, "List Requirements".
 */
@SuppressWarnings("serial")
public class ListTabPanel extends JTabbedPane {

	private ListTab parent;
	private FilterListTab filterList;
	private IterationListTab iterationList;
	private boolean firstTime;

	/** Constructor for ListTabPanel that takes in a ListTab that will be it's parent
	 * @param view ListTab
	 */
	public ListTabPanel(ListTab view) {

		parent = view;
		firstTime = true;

		setTabPlacement(TOP);
		setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
		setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		filterList = new FilterListTab(parent);
		iterationList = new IterationListTab(parent);
		addTab("Iterations", new ImageIcon(), iterationList, "List of Iterations");
		addTab("Filters", new ImageIcon(), filterList, "List of Filters");

		this.setPreferredSize(new Dimension(190, 500));
	}

	/** Method setComponentAt.
	 * @param index int index of the component to set
	 * @param component Component the component to set to
	 */
	@Override
	public void setComponentAt(int index, Component component) {
		super.setComponentAt(index, component);
		fireStateChanged(); //make sure toolbar knows if component changes
	}

	/** Sets the current tab index of the tab panel to the specified tab index
	 * @param index int the new index to set to
	 */
	@Override
	public void setSelectedIndex(int index) {
		super.setSelectedIndex(index);
		if (!firstTime) {
			if (getComponentAt(index) instanceof FilterListTab) {
				parent.setMode(Mode.FILTER);
				filterList.refreshAll();
				filterList.setCancelBtnToNew();
				filterList.setDeleteEnabled(false);
				parent.getFilterBuilderPanel().resetFields();
				parent.getFilterBuilderPanel().setInputEnabled(false);
			}
			else {
				parent.setMode(Mode.ITERATION);
				iterationList.refreshAll();
				iterationList.setCancelBtnToNew();
				iterationList.setDeleteEnabled(false);
				parent.getIterationBuilderPanel().resetFields();
				parent.getIterationBuilderPanel().setInputEnabled(false);
			}
		}
		else firstTime = false;
	}

	/**
	 * @return filterList The "filterList" FilterListTab
	 */
	public FilterListTab getFilterList() {
		return filterList;
	}

	/**
	 * @param filterList The "filterList" FilterListTab to set
	 */
	public void setFilterList(FilterListTab filterList) {
		this.filterList = filterList;
	}

	/**
	 * @return iterationList The "iterationList" IterationListTab
	 */
	public IterationListTab getIterationList() {
		return iterationList;
	}

	/**
	 * @param iterationList The "iterationList" IterationListTab to set
	 */
	public void setIterationList(IterationListTab iterationList) {
		this.iterationList = iterationList;
	}

}
