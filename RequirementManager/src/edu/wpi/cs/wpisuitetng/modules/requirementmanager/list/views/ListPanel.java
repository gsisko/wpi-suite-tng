

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.JTable;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;

/**
 * Panel to hold the three portions of the requirement list interface. The
 * list of saved filters is displayed in {@link FilterListPanel}, the filter
 * builder is displayed in {@link FilterBuilderPanel}, and the results of
 * the list are displayed in {@link ResultsPanel}.
 */
@SuppressWarnings("serial")
public class ListPanel extends JPanel {

	/** Panel containing the filter building interface */
	protected FilterBuilderPanel builderPanel;

	/** Panel containing the list of filters saved by the user */
	protected FilterListPanel filtersPanel;

	/** Panel containing the results of the requirement list */
	protected ResultsPanel resultsPanel;

	/** The layout manager for this panel */
	protected SpringLayout layout;
	
	/** The main tab controller */
	protected MainTabController tabController;

	/**
	 * Constructs the list panel and sets up the layout for the sub-panels
	 * @param tabController The main tab controller
	 */
	public ListPanel(MainTabController tabController) {
		this.tabController = tabController;
		
		// Set the layout manager of this panel
		this.layout = new SpringLayout();
		this.setLayout(layout);

		// Construct the panels that compose the list view
		this.builderPanel = new FilterBuilderPanel(this);
		JScrollPane builderScrollPane = new JScrollPane(builderPanel);
		this.filtersPanel = new FilterListPanel(this);
		JScrollPane filtersScrollPane = new JScrollPane(filtersPanel);
		this.resultsPanel = new ResultsPanel(tabController);

		// Constrain the filtersPanel
		layout.putConstraint(SpringLayout.NORTH, filtersScrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, filtersScrollPane, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.SOUTH, filtersScrollPane, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.EAST, filtersScrollPane, 200, SpringLayout.WEST, filtersScrollPane);
		
		// Constrain the builderPanel
		layout.putConstraint(SpringLayout.NORTH, builderScrollPane, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, builderScrollPane, 0, SpringLayout.EAST, filtersScrollPane);
		layout.putConstraint(SpringLayout.EAST, builderScrollPane, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, builderScrollPane, 70, SpringLayout.NORTH, builderScrollPane);
		
		// Constrain the resultsPanel
		layout.putConstraint(SpringLayout.NORTH, resultsPanel, 0, SpringLayout.SOUTH, builderScrollPane);
		layout.putConstraint(SpringLayout.WEST, resultsPanel, 0, SpringLayout.EAST, filtersScrollPane);
		layout.putConstraint(SpringLayout.EAST, resultsPanel, 0, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.SOUTH, resultsPanel, 0, SpringLayout.SOUTH, this);

		// Add the panels
		this.add(filtersScrollPane);
		this.add(builderScrollPane);
		this.add(resultsPanel);
		

	}
	
	public ResultsPanel getResultsPanel(){
		return resultsPanel;
	}


	public MainTabController getTabController() {
		return tabController;
	}

	public FilterBuilderPanel getBuilderPanel(){
	    return builderPanel;
	}
	
	public FilterListPanel getFilterPanel(){
	    return filtersPanel;
	}
}
