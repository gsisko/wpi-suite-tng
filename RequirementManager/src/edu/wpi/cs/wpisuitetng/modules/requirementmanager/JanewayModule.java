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
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabController;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs.MainTabPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar.ToolbarView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.toolbar.ToolbarController;


/**
 * A dummy module to demonstrate the Janeway client
 *
 */
public class JanewayModule implements IJanewayModule {
	
	/** The tabs used by this module */
	private ArrayList<JanewayTabModel> tabs;
	
	/** The controllers used by this module */
	public final MainTabController mainTabController;
	public final ToolbarController toolbarController;
	
	public final MainTabPanel mainTabPanel;
	public final ToolbarView toolbarView;
	
	/**
	 * Construct a new DummyModule for demonstration purposes
	 */
	public JanewayModule() {
		
		// Setup main tab view and controller
		mainTabPanel = new MainTabPanel();
		mainTabController = mainTabPanel.getMainTabController();
		
		// Setup tool bar view and controller
		toolbarView = new ToolbarView(mainTabController);
		toolbarController = new ToolbarController(toolbarView, mainTabController);
		
		// Add the refresh button
		toolbarController.setRelevantTabGroup(((ListView)mainTabPanel.getComponentAt(0)).getGroup());
		
		// Sets up an array that holds all the tabs
		tabs = new ArrayList<JanewayTabModel>();
		JanewayTabModel tab = new JanewayTabModel("Requirement Manager", new ImageIcon(), toolbarView, mainTabPanel);
		tabs.add(tab);
		
		// add keyboard shortcuts to requirements tab
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
	
	@SuppressWarnings("serial")
	private void registerKeyboardShortcuts(JanewayTabModel tab) {
		
		// retrieves the name of the operating system
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

	@Override
	public void invokeWhenSelected() {
		((ListView)mainTabPanel.getComponentAt(0)).refreshData();
	}
}
