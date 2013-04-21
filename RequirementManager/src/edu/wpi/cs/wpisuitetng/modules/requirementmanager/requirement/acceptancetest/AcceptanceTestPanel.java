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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.acceptancetest;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import com.lowagie.text.Font;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.JTextFieldLimit;

/**
 * This is a panel to display an individual note
 *
 */
@SuppressWarnings({"serial","rawtypes", "unchecked", "unused"})
public class AcceptanceTestPanel extends JPanel{

	private  JLabel statusLabel; //The label for the status combo box ("statusBox")
	
	//The fillable components
	private JTextArea txtDescription;//The actual message component of the test to be displayed in this panel, stored in a JTextArea
	private  JTextField txtName;//The name text field
	private  JComboBox statusBox;//The status combo box

	public AcceptanceTestPanel(String testName, String testDescription){
		this.setBackground(Color.white);//Set the background color of this panel to white
		this.setOpaque(true);//Set this panel to Opaque (means the background is painted)
		
		//Create the label for the statusBox:
		statusLabel = new JLabel("Status:");
				
		//Create and set the components:
		
		//Name:
		txtName = new JTextField(testName);
		txtName.setFont(txtName.getFont().deriveFont(9)); //set the font of the name to size 9
		txtName.setFont(txtName.getFont().deriveFont(Font.BOLD)); //set the font of the name to bold
		
		//Set the character limit for the name
		txtName.setDocument(new JTextFieldLimit(100));
		//end Name
		
		//Description:
		txtDescription = new JTextArea(testDescription, 2, 2);
		txtDescription.setLineWrap(true); //Make the description wrap long lines
		txtDescription.setFont(txtDescription.getFont().deriveFont(9)); //set the font of the description to size 9
		//end Description
		
		//Status box:
		//Create the strings for the status box
		String[] statusStrings = { " ", "Passed", "Failed"};
		
		//Construct the status box
		statusBox = new JComboBox(statusStrings);
		
		//Set the initial selections for the status box
		statusBox.setSelectedIndex(0);
		//end Status box
		
		//Create an inner panel ("nameAndStatusPanel") to hold the name and status, add them to that panel
		JPanel nameAndStatusPanel = new JPanel();
		nameAndStatusPanel.setLayout(new BoxLayout(nameAndStatusPanel, BoxLayout.LINE_AXIS));//create and set the layout for the nameAndStatusPanel
		
		nameAndStatusPanel.add(txtName);//actually add the "txtName" JTextField to the nameAndStatusPanel
		nameAndStatusPanel.add(Box.createRigidArea(new Dimension(0,5)));//add 5 units of horizontal spacing after the txtName
		nameAndStatusPanel.add(statusBox);//actually add the "statusBox" JComboBox to the nameAndStatusPanel
		//end nameAndStatusPanel
		
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//create and set the layout for this panel

		//Create a lowered etched border, add inner and outer padding, and set the border of this panel to the result
		setBorder(  BorderFactory.createCompoundBorder(	(BorderFactory.createEmptyBorder(5, 5, 5, 5)),
				BorderFactory.createCompoundBorder( (BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)),
						(BorderFactory.createEmptyBorder(5, 5, 5, 5)) )  ));
	
		//Add the components to this panel
		add(nameAndStatusPanel);//actually add the nameAndStatusPanel to this panel
		this.add(Box.createRigidArea(new Dimension(0,5)));//add 5 units of vertical spacing after the nameAndStatusPanel
		add(txtDescription);//actually add the  "content" JTextArea to this panel
		this.add(Box.createRigidArea(new Dimension(0,5)));//add 5 units of vertical spacing after the content
		
		//Define a maximum height of the proper height to contain it's components
		this.setMaximumSize(new Dimension(100000, this.getPreferredSize().height));
		
		//Add resize functionality to keep the maximum height current
		final JPanel referenceToThis = this; // a reference to this panel to use within the following constructor
		//Construct and add a new component listener to listen for a resize event
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) { //on resize...
				referenceToThis.setMaximumSize(new Dimension(100000, referenceToThis.getPreferredSize().height));//reset the maximum height appropriately 
				referenceToThis.invalidate(); //send an invalidate to signal to the parent container that this panel must be repainted
			}
		});
		//end resize functionality

	}

	/**
	 * @return the txtDescription
	 */
	public JTextArea gettxtDescription() {
		return txtDescription;
	}

	/**
	 * @param txtDescription the txtDescription to set
	 */
	public void settxtDescription(JTextArea txtDescription) {
		this.txtDescription = txtDescription;
	}

	/**
	 * @return the txtName
	 */
	public JTextField getTxtName() {
		return txtName;
	}

	/**
	 * @param txtName the txtName to set
	 */
	public void setTxtName(JTextField txtName) {
		this.txtName = txtName;
	}
	

	/**
	 * @return the statusBox
	 */
	public JComboBox getStatusBox() {
		return statusBox;
	}

	/**
	 * @param statusBox the statusBox to set
	 */
	public void setStatusBox(JComboBox statusBox) {
		this.statusBox = statusBox;
	}



}
