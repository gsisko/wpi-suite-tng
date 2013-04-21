// $codepro.audit.disable emptyCatchClause
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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementTab.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.charts.ChartView;

/**
 * Controls the behavior of a given MainTabView.
 * Provides convenient public methods for controlling the MainTabView.
 * Keep in mind that this controller is visible as a public field in the module.
 */
public class MainTabController {

	private final MainTabPanel view;
	private boolean initialized = false;

	/**
	 * @param view Create a controller that controls this MainTabView
	 */
	public MainTabController(final MainTabPanel view) {
		this.view = view;
		this.view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				MainTabController.this.onMouseClick(event);
			}
		});
		this.view.addChangeListener(new ChangeListener()
		{
			@Override
			public void stateChanged(ChangeEvent arg0) {
				if (initialized)
				{
					Component tab = view.getComponentAt(view.getSelectedIndex());
					if (tab instanceof ListView) {
						((ListView)tab).getFilterController().refreshData();
						((ListView)tab).getIterationController().refreshData();
						
						((ListView)tab).getListTab().getTabPanel().getFilterList().setCancelBtnToNew();
						((ListView)tab).getListTab().getFilterBuilderPanel().resetFields();
						((ListView)tab).getListTab().getFilterBuilderPanel().setInputEnabled(false);
						
						((ListView)tab).getListTab().getTabPanel().getIterationList().setCancelBtnToNew();
						((ListView)tab).getListTab().getIterationBuilderPanel().resetFields();
						((ListView)tab).getListTab().getIterationBuilderPanel().setInputEnabled(false);
					} 
					
					if (tab instanceof ChartView) {
						((ChartView)tab).reloadData();
					}
					
					if (tab instanceof RequirementView){
						((RequirementView) tab).getRequirementPanel().getAttributePanel().fillIterationSelectionBox();
					}
				}
				else initialized = true;
			}

		});
	}

	/**
	 * Adds a tab.
	 * 
	 * @param title			The tab's title.
	 * @param icon			The icon for the tab.
	 * @param component		The component that will be displayed inside the tab.
	 * @param tip			The tooltip to display when the cursor hovers over the tab title.
	 * @return				The created Tab
	 */
	public Tab addTab(String title, Icon icon, Component component, String tip) {
		if(component instanceof ChartView && view.indexOfTab("Charts") != -1) {
			view.setSelectedIndex(view.indexOfTab("Charts"));
			return null;
		}
		
		view.addTab(title, icon, component, tip);
		int index = view.getTabCount() - 1;
		view.setSelectedIndex(index);
		return new Tab(view, view.getTabComponentAt(index));
	}

	/**
	 * @return Same as addTab(null, null, null, null)
	 */
	public Tab addTab() {
		return addTab(null, null, null, null);
	}

	/**
	 * Adds a tab that displays the given requirement in the given mode
	 * @param requirement The requirement to display
	 * @param mode The Mode to use
	 */
	private Tab addRequirementTab(Requirement requirement, Mode mode) {
		if(view.indexOfTab("Requirement #"+requirement.getId()) != -1){
			view.setSelectedIndex(view.indexOfTab("Requirement #"+requirement.getId()));
			return null;
		}

		Tab tab = addTab();
		RequirementView view = new RequirementView(requirement, mode, tab, this);
		tab.setComponent(view);
		view.requestFocus();
		return tab;
	}

	/**
	 * Adds a tab that displays the given requirement
	 * @param requirement the requirement to display
	 * @return The created Tab 
	 */
	public Tab addEditRequirementTab(Requirement requirement) {
		return addRequirementTab(requirement, Mode.EDIT);
	}

	/**
	 * Adds a tab that allows the user to create a new Requirement
	 * @return The created Tab
	 */
	public Tab addCreateRequirementTab() {
		return addRequirementTab(new Requirement(), Mode.CREATE);
	}

	/**
	 * Add a change listener to the view this is controlling.
	 * @param listener the ChangeListener that should receive ChangeEvents
	 */
	public void addChangeListener(ChangeListener listener) {
		view.addChangeListener(listener);
	}

	/**
	 * Changes the selected tab to the tab left of the current tab
	 */
	public void switchToLeftTab() {
		if (view.getSelectedIndex() > 0) {
			switchToTab(view.getSelectedIndex() - 1);
		}
	}

	/**
	 * Changes the selected tab to the tab right of the current tab
	 */
	public void switchToRightTab() {
		switchToTab(view.getSelectedIndex() + 1);
	}

	/**
	 * Closes the currently active tab
	 */
	public void closeCurrentTab() {
		try {
			view.removeTabAt(view.getSelectedIndex());
		}
		catch (IndexOutOfBoundsException ignored) {}
	}

	/**
	 * Changes the selected tab to the tab with the given index
	 * @param tabIndex the index of the tab to select
	 */
	public void switchToTab(int tabIndex) {
		try {
			view.setSelectedIndex(tabIndex);
		}
		catch (IndexOutOfBoundsException ignored) {}
	}

	/**
	 * Close tabs upon middle clicks.
	 * @param event MouseEvent that happened on this.view
	 */
	private void onMouseClick(MouseEvent event) {
		// only want middle mouse button
		if(event.getButton() == MouseEvent.BUTTON2) {
			final int clickedIndex = view.indexAtLocation(event.getX(), event.getY());
			if(clickedIndex > -1) {
				view.removeTabAt(clickedIndex);
			}
		}
	}
	
	public MainTabPanel getView() {
		return view;
	}
}
