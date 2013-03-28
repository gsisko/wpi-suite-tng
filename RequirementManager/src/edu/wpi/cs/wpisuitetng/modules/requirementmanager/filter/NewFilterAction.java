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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterListPanel;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.FilterBuilderPanel.Mode;

public class NewFilterAction implements ActionListener {
	/** The button to watch */
	JButton filterListPanelButton;
	/** The panel with the button to watch button to watch */
	FilterListPanel inPanel;
	/** THE filter builder panel that will be set when this button is pressed*/
	FilterBuilderPanel  builder;


	/** When pressed, the information in the filter builder panel
	 *  is reset and the fields are grayed out.
	 * 
	 * @param theButton The button to watch
	 * @param builder   The filter builder panel to do work on
	 */
	public NewFilterAction(FilterListPanel thePanel, FilterBuilderPanel builder){
		this.inPanel = thePanel;
		this.filterListPanelButton = inPanel.getBtnCreate();
		this.builder = builder;		
		inPanel.setBtnCreateIsCancel(false);

	}

	/** This is called whenever the "Cancel"/"New Filter" button is 
	 *  called. If the button mode is set to cancel mode, it is set 
	 *  to display "New Filter" as the button and disables and
	 *  resets the fields in the FilterBuilderPanel. If the mode is
	 *  set to New Filter, then the opposite happens.
	 *  
	 *  @param e The event that triggers these responses 
	 */
	public void actionPerformed(ActionEvent e) {
		if (inPanel.isBtnCreateIsCancel()){
			builder.setInputEnabled(false);
			filterListPanelButton.setText("New Filter");  
		} else {
			builder.setInputEnabled(true);
			builder.getStatus().setEnabled(false);
			filterListPanelButton.setText("Cancel");  
		}
		// Set up system for next button press
		builder.setCurrentMode(Mode.CREATE); 
		builder.setCurrentFilter(null);
		inPanel.setBtnCreateIsCancel(!inPanel.isBtnCreateIsCancel());
	}
}
