package edu.wpi.cs.wpisuitetng.modules.requirementmanager;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.RequirementPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.views.ToolbarPanel;


/**
 * A dummy module to demonstrate the Janeway client
 *
 */
public class JanewayModule implements IJanewayModule {
	
	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	
	/**
	 * Construct a new DummyModule for demonstration purposes
	 */
	public JanewayModule() {
		
		// Setup toolbar panel
		JPanel toolbarPanel = new ToolbarPanel();
		
		// Setup the main panel
		RequirementPanel mainPanel = new RequirementPanel();
		
		tabs = new ArrayList<JanewayTabModel>();
		JanewayTabModel tab = new JanewayTabModel("Requirement Manager", new ImageIcon(), toolbarPanel, mainPanel);
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
