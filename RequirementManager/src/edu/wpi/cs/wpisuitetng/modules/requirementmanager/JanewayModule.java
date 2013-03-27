package edu.wpi.cs.wpisuitetng.modules.requirementmanager;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import edu.wpi.cs.wpisuitetng.janeway.gui.widgets.KeyboardShortcut;
import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar.ToolbarController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar.ToolbarView;


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
		
		tabs = new ArrayList<JanewayTabModel>();
		JanewayTabModel tab = new JanewayTabModel("Requirement Manager", new ImageIcon(), toolbarView, mainTabView);
		tabs.add(tab);
		
		// add keyboard shortcuts to defects tab
	    registerKeyboardShortcuts(tab);
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
	
	/**
	 * Registers basic keyboard shortcuts.
	 * @param tab JanewayTabModel to modify
	 */
	@SuppressWarnings("serial")
	private void registerKeyboardShortcuts(JanewayTabModel tab) {
		String osName = System.getProperty("os.name").toLowerCase();
		
		// control + tab: switch to right tab
		tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control TAB"), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainTabController.switchToRightTab();
			}
		}));
		
		// control + shift + tab: switch to left tab
		tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control shift TAB"), new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainTabController.switchToLeftTab();
			}
		}));
		
		// command + w for mac or control + w for windows: close the current tab
		if (osName.contains("mac")) {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("meta W"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainTabController.closeCurrentTab();
				}
			}));
		}
		else {
			tab.addKeyboardShortcut(new KeyboardShortcut(KeyStroke.getKeyStroke("control W"), new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					mainTabController.closeCurrentTab();
				}
			}));
		}
	}
}
