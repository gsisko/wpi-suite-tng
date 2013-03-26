

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.IToolbarGroupProvider;
import edu.wpi.cs.wpisuitetng.janeway.gui.container.toolbar.ToolbarGroupView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RefreshRequirementsAction;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveAllRequirementsController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.controllers.RetrieveRequirementController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.Tab;

/**
 * View that contains the entire requirement listing interface
 */
@SuppressWarnings("serial")
public class ListRequirementsView extends JPanel implements IToolbarGroupProvider {
	
	/** Panel containing the list interface */
	protected ListPanel mainPanel;
	
	/** The layout manager for this panel */
	protected SpringLayout layout;
	
	/** The panel containing buttons for the tool bar */
	protected ToolbarGroupView buttonGroup;
	
	/** The refresh button that reloads the results of the list/filter */
	protected JButton btnRefresh;
	
	/** Controller to handle list and filter requests from the user */
	protected RetrieveAllRequirementsController controller;
	
	/** The main tab controller */
	protected MainTabController tabController;
	
	/**
	 * Construct the view
	 * @param tabController The main tab controller
	 * @param tab The Tab containing this view
	 */
	public ListRequirementsView(MainTabController tabController, Tab tab) {
		this.tabController = tabController;
		
		if(tab != null) {
			tab.setTitle("List Requirements");
			tab.setIcon(new ImageIcon());
			tab.setToolTipText("List for requirements");
		}
		
		mainPanel = new ListPanel(tabController);
		
		// Construct the layout manager and add constraints
		layout = new SpringLayout();
		this.setLayout(layout);
		layout.putConstraint(SpringLayout.NORTH, mainPanel, 0, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.SOUTH, mainPanel, 0, SpringLayout.SOUTH, this);
		layout.putConstraint(SpringLayout.WEST, mainPanel, 0, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, mainPanel, 0, SpringLayout.EAST, this);
		
		// Add the mainPanel to this view
		this.add(mainPanel);
		
		// Instantiate the controller
		controller = new RetrieveAllRequirementsController(this);
		
		// Instantiate the button panel
		buttonGroup = new ToolbarGroupView("List/Filter");
		
		// Instantiate the refresh button
		btnRefresh = new JButton();
		btnRefresh.setAction(new RefreshRequirementsAction(controller));
		buttonGroup.getContent().add(btnRefresh);
		buttonGroup.setPreferredWidth(150);
		
		// Add a listener for row clicks in the actual table
		mainPanel.getResultsPanel().getResultsTable().addMouseListener(new RetrieveRequirementController(this.getListPanel().getResultsPanel()));
		
		// Load initial data
		controller.refreshData();
	}
	
	public ListPanel getListPanel() {
		return mainPanel;
	}

	@Override
	public ToolbarGroupView getGroup() {
		return buttonGroup;
	}
}
