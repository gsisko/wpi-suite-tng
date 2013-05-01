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

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.RowSorter.SortKey;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.ListSaveModelController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RefreshRequirementsAction;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllModelsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.models.Filter;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListTab.Mode;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Iteration;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.models.Requirement;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/** View that contains the entire requirement listing interface
 */
@SuppressWarnings("serial")
public class ListView extends JPanel implements IToolbarGroupProvider {

	/** Panel containing the list interface */
	protected ListTab mainPanel;

	/** The layout manager for this panel */
	protected SpringLayout layout;

	/** The layout manager for this panel */
	protected SpringLayout btnlayout;

	/** The panel containing buttons for the tool bar */
	protected ToolbarGroupView buttonGroup;

	/** The refresh button that reloads the results of the list/filter */
	protected JButton btnRefresh;

	/** The button that enables editing in the list view */
	protected JButton btnEnableEdit;

	/** The button that saves editing in the list view */
	protected JButton btnSave;

	/** The button that cancels editing in the list view */
	protected JButton btnCancel;

	/** The display pie chart button that loads the pie chart tab */
	protected JButton btnDisplayPieChart;

	/** The check box for Default Column Widths */
	protected JCheckBox checkBoxDefault;

	/** Boolean to represent state of the check box */
	protected boolean checkBoxStatus;

	/** Controller to handle list and filter requests from the user */
	protected RetrieveAllRequirementsController controller;

	/** The controller for retrieving filters */
	protected RetrieveAllModelsController filterController;
	/** The controller for retrieving iterations */
	protected RetrieveAllModelsController iterationController;

	/** The main tab controller */
	protected MainTabController tabController;

	/** Location of divider before entering edit mode */
	protected int oldDividerLocation;

	/** Size of divider before entering edit mode */
	protected int oldDividerSize;

	/** set true when waiting for a refresh */
	private boolean waitingForRefresh = false;
	
	/** store the previous mode */
	private Mode previousMode;
	
	/** Store the sort keys */
	List<? extends SortKey> sortKeys;

	/** Local copy of filters stored in the database */
	protected Filter[] allFilters;
	/** Local copy of iterations stored in the database */
	protected Iteration[] allIterations;
	/** Local copy of requirements stored in the database */
	protected Requirement[] allRequirements;
	/** Local copy of displayed requirements stored in the database */
	protected Requirement[] displayedRequirements;

	/** The controller that saves requirements that are edited in list view */
	private ListSaveModelController listSaveRequirementController = null;	

	/**Construct the view
	 * @param tabController The main tab controller
	 */
	public ListView(final MainTabController tabController) {
		this.tabController = tabController;

		mainPanel = new ListTab(tabController, this);

		allFilters = new Filter[0];
		allIterations = new Iteration[0];
		allRequirements = new Requirement[0];
		displayedRequirements = new Requirement[0];

		// Construct the layout manager and add constraints
		layout = new SpringLayout();
		this.setLayout(layout);

		// Construct the content panel
		JPanel content = new JPanel();
		btnlayout  = new SpringLayout();
		content.setLayout(btnlayout);
		content.setOpaque(false);

		// Initialize the controllers
		controller = new RetrieveAllRequirementsController(this);
		filterController = new RetrieveAllModelsController(
				mainPanel.getTabPanel().getFilterList(), mainPanel.getFilterBuilderPanel(), "filter");
		iterationController = new RetrieveAllModelsController(
				mainPanel.getTabPanel().getIterationList(), mainPanel.getIterationBuilderPanel(), "iteration");

		// Add a listener for row clicks in the actual table
		mainPanel.getResultsPanel().getResultsTable().addMouseListener(new RetrieveRequirementController(this.getListTab().getResultsPanel()));

		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("Options for Requirements", content);

		// Instantiate the refresh button
		btnRefresh = new JButton();
		btnRefresh.setAction(new RefreshRequirementsAction(controller));
		buttonGroup.setPreferredWidth((int)buttonGroup.getPreferredSize().getWidth() - 15);

		btnRefresh.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				refreshData();
			}
		});

		// Instantiate the defaultColumnWidths checkbox
		checkBoxDefault = new JCheckBox("Reset Table Layout", true);
		checkBoxDefault.setOpaque(false);
		checkBoxStatus = true;
		//Save the state of the checkbox every time it changes, workaround for reseting views when tabs are changed
		checkBoxDefault.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {

				checkBoxStatus = checkBoxDefault.isSelected();
			}
		});

		// Create and make button to Enable Editing in list view
		btnEnableEdit = new JButton("Enable Edit Mode");
		btnCancel = new JButton("Cancel");
		btnSave = new JButton("Save Changes");
		btnSave.setVisible(false);
		btnCancel.setVisible(false);

		btnEnableEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!waitingForRefresh){
					waitingForRefresh = true;
					int count = 0;
					// Wait until refreshes are done
					while (controller.getRefreshes() > 0 && count < controller.getRefreshes() + 2){
						try {
							Thread.sleep(500);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
						count++;
					}
					controller.setRefreshes(0);
					sortKeys = controller.getTableSort();
					mainPanel.getResultsPanel().getResultsTable().getRowSorter().setSortKeys(null);
					// Instantiate the controller if it hasn't been made yet. This is done here just in case
					// of instantiation path errors.
					if (listSaveRequirementController == null){
						listSaveRequirementController = new ListSaveModelController(mainPanel.getResultsPanel(), "requirement");
					}

					btnEditNotVisible();
					previousMode = mainPanel.getMode();
					mainPanel.setMode(Mode.EDIT);
					setListsAndBuildersVisible(false);
					btnRefresh.setEnabled(false);

					mainPanel.getResultsPanel().setUpForEditing();
					mainPanel.getResultsPanel().setInEditMode(true);
					mainPanel.getResultsPanel().getResultsTable().setRowSelectionAllowed(false);//select by cells when in edit mode
					mainPanel.getTabController().getView().getJanewayModule().getToolbarView().getCreateRequirementButton().setEnabled(false);//disables the create requirements button
					mainPanel.getTabController().getView().getJanewayModule().getToolbarView().getIDbox().setEnabled(false);//disables the lookup by ID box
					waitingForRefresh = false;
				}
			}
		});

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCancelSaveNotVisible();
				mainPanel.getResultsPanel().getModel().setEditable(false);
				mainPanel.setMode(previousMode);
				setListsAndBuildersVisible(true);
				btnRefresh.setEnabled(true);
				refreshData();
				mainPanel.getResultsPanel().setInEditMode(false);
				mainPanel.getResultsPanel().enableSorting();
				mainPanel.getResultsPanel().getResultsTable().setRowSelectionAllowed(true);//select by rows when not in edit mode
				mainPanel.getTabController().getView().getJanewayModule().getToolbarView().getCreateRequirementButton().setEnabled(false);//disables the create requirements button
				mainPanel.getResultsPanel().getResultsTable().setDefaultRenderer(String.class, new ResultsTableCellRenderer(null, null, null));
				mainPanel.getTabController().getView().getJanewayModule().getToolbarView().getCreateRequirementButton().setEnabled(true);//enables the create requirements button
				mainPanel.getTabController().getView().getJanewayModule().getToolbarView().getIDbox().setEnabled(true);//enables the lookup by ID box
				mainPanel.getEditModeBuilderPanel().setInvalidInputMessages(new String[3]);
				
				controller.setTableSort(sortKeys);
			}
		});
		btnCancel.setPreferredSize(new Dimension(80, 25));

		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				listSaveRequirementController.perform();				

				btnCancelSaveNotVisible();
				mainPanel.getResultsPanel().getModel().setEditable(false);
				mainPanel.setMode(previousMode);
				setListsAndBuildersVisible(true);
				btnRefresh.setEnabled(true);
				refreshData();
				mainPanel.getResultsPanel().setInEditMode(false);
				mainPanel.getResultsPanel().enableSorting();
				mainPanel.getResultsPanel().getResultsTable().setRowSelectionAllowed(true);//select by rows when not in edit mode
				mainPanel.getResultsPanel().getResultsTable().setDefaultRenderer(String.class, new ResultsTableCellRenderer(null, null, null));
				mainPanel.getTabController().getView().getJanewayModule().getToolbarView().getCreateRequirementButton().setEnabled(true);//enables the create requirements button
				mainPanel.getTabController().getView().getJanewayModule().getToolbarView().getIDbox().setEnabled(true);//enables the lookup by ID box
				mainPanel.getEditModeBuilderPanel().setInvalidInputMessages(new String[3]);
				
				controller.setTableSort(sortKeys);
			}
		});
		btnSave.setPreferredSize(new Dimension(130, 25));

		layout.putConstraint(SpringLayout.NORTH, mainPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, mainPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, mainPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, this);

		// Add the mainPanel to this view
		this.add(mainPanel);

		// Add buttons to the content panel
		content.add(btnRefresh);
		content.add(checkBoxDefault);
		content.add(btnEnableEdit);
		content.add(btnCancel);
		content.add(btnSave);

		// Configure the layout of the buttons on the content panel
		btnlayout.putConstraint(SpringLayout.NORTH, btnRefresh, 5, SpringLayout.NORTH, content); //Refresh button to top of panel
		btnlayout.putConstraint(SpringLayout.WEST, btnRefresh, 25, SpringLayout.WEST, content); //Refresh button to left of panel
		btnlayout.putConstraint(SpringLayout.WEST, checkBoxDefault, 10, SpringLayout.EAST, btnRefresh); //check box next to Create Requirement
		btnlayout.putConstraint(SpringLayout.NORTH, checkBoxDefault, 0, SpringLayout.NORTH, btnRefresh); //check to top of panel
		btnlayout.putConstraint(SpringLayout.SOUTH, checkBoxDefault, 0, SpringLayout.SOUTH, btnRefresh); //Align bot of refresh and check box
		btnlayout.putConstraint(SpringLayout.NORTH, btnEnableEdit, 11, SpringLayout.SOUTH, btnRefresh); //Align enable edit to bot of refresh
		btnlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnEnableEdit, 0, SpringLayout.HORIZONTAL_CENTER, content); //Align enable edit to center
		btnlayout.putConstraint(SpringLayout.NORTH, btnCancel, 11, SpringLayout.SOUTH, btnRefresh); //Align cancel to bot of refresh
		btnlayout.putConstraint(SpringLayout.WEST, btnCancel, 0, SpringLayout.WEST, btnRefresh); //Align cancel to left side of refresh
		btnlayout.putConstraint(SpringLayout.NORTH, btnSave, 11, SpringLayout.SOUTH, btnRefresh); //Align save to bot of refresh
		btnlayout.putConstraint(SpringLayout.HORIZONTAL_CENTER, btnSave, 0, SpringLayout.HORIZONTAL_CENTER, checkBoxDefault); //Align save to right side of check box
	}

	/** Gets the check box status
	 * @return checkBoxStatus the boolean status of the check box 
	 */
	public boolean getCheckBoxStatus() {
		return checkBoxStatus;
	}

	/** Sets the status of the checkbox
	 * @param checkBoxStatus the status to set the checkbox to (a boolean value
	 */
	protected void setCheckBoxStatus(boolean checkBoxStatus) {
		this.checkBoxStatus = checkBoxStatus;
	}

	/** Refresh the Data
	 */
	public void refreshData() {
		iterationController.refreshData();
		int count = 0;
		while (allIterations.length == 0) {
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (++count > 500) break;
		}
		filterController.refreshData();
	}
	
	/** Gets the controller for getting requirements
	 * @return controller the controller for retrieving requirements
	 */
	public RetrieveAllRequirementsController getController() {
		return controller;
	}

	/** Gets the controller for retrieving filters
	 * @return filterController the controller for retrieving filters
	 */
	public RetrieveAllModelsController getFilterController() {
		return filterController;
	}
	
	/** Gets the controller for retrieving iterations
	 * @return iterationController the controller for retrieving iterations
	 */
	public RetrieveAllModelsController getIterationController() {
		return iterationController;
	}
	
	/** Gets the main panel which is a list tab
	 * @return mainPanel the main panel
 	 */
	public ListTab getListTab() {
		return mainPanel;
	}

	/** Gets the tool bar
	 * @return buttonGroup the toolbar group view that has buttons
	 */
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}

	/** Gets all the local filters
	 * @return allFilters the allFilters array of filters
	 */
	public Filter[] getAllFilters() {
		return allFilters;
	}

	/** Sets the local filters
	 * @param allFilters the allFilters (an array of filters) to set
	 */
	public void setAllFilters(Filter[] allFilters) {
		this.allFilters = allFilters;
	}

	/** Gets the local iterations
	 * @return allIterations the allIterations array of iterations
	 */
	public Iteration[] getAllIterations() {
		return allIterations;
	}

	/**Sets the local iterations
	 * @param allIterations the allIterations array of iterations to set
	 */
	public void setAllIterations(Iteration[] allIterations) {
		this.allIterations = allIterations;
	}

	/** Gets all the requirements
	 * @return allRequirements the allRequirements array of requirements
	 */
	public Requirement[] getAllRequirements() {
		return allRequirements;
	}

	/** Sets all the requirements
	 * @param allRequirements the allRequirements array of requirements to set
	 */
	public void setAllRequirements(Requirement[] allRequirements) {
		this.allRequirements = allRequirements;
		if (allRequirements.length > 0)
			tabController.getView().getJanewayModule().getToolbarView().getDisplayChartsButton().setEnabled(true);
		else
			tabController.getView().getJanewayModule().getToolbarView().getDisplayChartsButton().setEnabled(false);
	}

	/** Gets the displayed requirements
	 * @return displayedRequirements the displayedRequirements array of requirements
	 */
	public Requirement[] getDisplayedRequirements() {
		return displayedRequirements;
	}

	/** Sets the displayed requirements
	 * @param displayedRequirements the displayedRequirements array of requirements to set
	 */
	public void setDisplayedRequirements(Requirement[] displayedRequirements) {
		this.displayedRequirements = displayedRequirements;
		if (displayedRequirements.length > 0 && tabController.getView().getNonRequirementTabCount() == 0)
			btnEnableEdit.setEnabled(true);
		else
			btnEnableEdit.setEnabled(false);
	}

	/** Get the Edit button
	 * @return btnEnableEdit The btnEnableEdit JButton
	 */
	public JButton getBtnEdit(){
		return btnEnableEdit;
	}

	/** Set the Save and Cancel buttons to invisible
	 * Set the EnableEdit button to visible
	 */
	public void btnCancelSaveNotVisible() {
		btnCancel.setVisible(false);
		btnSave.setVisible(false);
		btnEnableEdit.setVisible(true);
	}

	/** Set the Save and Cancel buttons to visible
	 * Set the EnableEdit button to invisible
	 */
	public void btnEditNotVisible() {
		btnEnableEdit.setVisible(false);
		btnSave.setVisible(true);
		btnCancel.setVisible(true);
	}

	/** Set everything else enabled or disabled when changing edit modes
	 */
	public void setListsAndBuildersVisible(boolean enable) {
		if (enable) {
			mainPanel.getSplitPane().setDividerLocation(oldDividerLocation);
			mainPanel.getSplitPane().setEnabled(true);
			mainPanel.getSplitPane().setDividerSize(oldDividerSize);
			mainPanel.getLeftPanel().setMinimumSize(new Dimension (260, 500));
		}
		else {
			oldDividerLocation = mainPanel.getSplitPane().getDividerLocation();
			oldDividerSize = mainPanel.getSplitPane().getDividerSize();
			mainPanel.getSplitPane().setEnabled(false);
			mainPanel.getSplitPane().setDividerLocation(0);
			mainPanel.getSplitPane().setDividerSize(0);
			mainPanel.getLeftPanel().setMinimumSize(new Dimension (0, 500));
		}

		if (mainPanel.getMode() == Mode.FILTER) {
			mainPanel.getFilterBuilderPanel().setVisible(enable);
		}
		else if (mainPanel.getMode() == Mode.ITERATION) {
			mainPanel.getIterationBuilderPanel().setVisible(enable);
		}
		else {
			mainPanel.getEditModeBuilderPanel().setVisible(!enable);
		}
	}

	/** Gets the save button
	 * @return btnSave the btnSave JButton
	 */
	public JButton getBtnSave() {
		return btnSave;
	}

	/**Sets the save button
	 * @param btnSave the btnSave JButton to set
	 */
	public void setBtnSave(JButton btnSave) {
		this.btnSave = btnSave;
	}

}
