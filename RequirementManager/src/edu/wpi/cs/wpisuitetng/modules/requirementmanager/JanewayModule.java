package edu.wpi.cs.wpisuitetng.modules.requirementmanager;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar.ToolbarController;


/**
 * A dummy module to demonstrate the Janeway client
 *
 */
public class JanewayModule implements IJanewayModule {
	
	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	
	public final MainTabController mainTabController;
	public final ToolbarController toolbarController;
	
	/**
	 * Construct a new DummyModule for demonstration purposes
	 */
	public JanewayModule() {
		
		MainTabView mainTabView = new MainTabView();
		mainTabController = new MainTabController(mainTabView);
		
		// Setup toolbar view and controller
		ToolbarView toolbarView = new ToolbarView(mainTabController);
		toolbarController = new ToolbarController(toolbarView, mainTabController);
		
		// Setup the main panel
		RequirementPanel mainPanel = new RequirementPanel(null,null,null); //TODO: change as soon as possible
		
		tabs = new ArrayList<JanewayTabModel>();
		JanewayTabModel tab = new JanewayTabModel("Requirement Manager", new ImageIcon(), toolbarView, mainPanel);
		tabs.add(tab);
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return "Requirement Manager";
	}

	/**
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}
}
