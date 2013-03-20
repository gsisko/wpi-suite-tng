package edu.wpi.cs.wpisuitetng.modules.dropbox;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule;
import edu.wpi.cs.wpisuitetng.janeway.modules.JanewayTabModel;
import edu.wpi.cs.wpisuitetng.modules.dropbox.view.MainView;
import edu.wpi.cs.wpisuitetng.modules.dropbox.view.ToolbarView;

/**
 * A WPISuite module to allow users to share files among
 * each other. Users can add files to the dropbox, remove
 * them, and view files uploaded by other users.
 */
public class DropboxModule implements IJanewayModule {
	
	/** The tabs used by this module */
	private List<JanewayTabModel> tabs;
	
	/**
	 * Constructs the GUI for the Dropbox module
	 */
	public DropboxModule() {
		tabs = new ArrayList<JanewayTabModel>();
		ToolbarView toolbarView = new ToolbarView();
		MainView mainView = new MainView();
		JanewayTabModel tab1 = new JanewayTabModel(getName(), new ImageIcon(), toolbarView, mainView);
		tabs.add(tab1);
	}
	

	/*
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getName()
	 */
	@Override
	public String getName() {
		return "Dropbox";
	}

	/*
	 * @see edu.wpi.cs.wpisuitetng.janeway.modules.IJanewayModule#getTabs()
	 */
	@Override
	public List<JanewayTabModel> getTabs() {
		return tabs;
	}

}
