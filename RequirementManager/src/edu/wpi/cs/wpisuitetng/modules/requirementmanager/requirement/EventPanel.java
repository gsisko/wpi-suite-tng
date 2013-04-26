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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/** This is a panel to display an individual event
 */
@SuppressWarnings("serial")
public class EventPanel extends JPanel{

	/** The actual body of the event (the changes that occurred) to be displayed in this panel, stored in a JTextArea */
	private JTextArea content;
	
	/** The generated title of this event (who did the event, when it happened, and what type of event it is). This is generated by the event that is displayed in this panel. */
	private String title; 

	/** This is the constructor for this panel.
	 * It takes in the Event to be displayed in this panel by two of it's components:
	 * -The title of the event, passed in as "eventTitle"
	 * -The body of the event, passed in as "message"
	 * @param eventTitle A string containing the title of the event to be displayed in this panel (who did the event, when it happened, and what type of event it is)
	 * @param message A string containing the actual body of the event to be displayed in this panel
	 */
	public EventPanel(String eventTitle, String message){
		this.setBackground(Color.white);//Set the background color of this panel to white
		this.setOpaque(true);//Set this panel to Opaque (means the background is painted)

		title = eventTitle;//Set the "title"

		content =  new JTextArea(message); //Create and set the "content"

		content.setLineWrap(true); //Make the content wrap long lines

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));//create and set the layout for this panel

		content.setFont(content.getFont().deriveFont(9)); //set the font of the content to size 9
		content.setEditable(false);
	
		//Create and set the titled border of this panel, using the title
		TitledBorder titleBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), eventTitle); //First create a titled and (lowerd) etched border, using the provided title
		titleBorder.setTitleJustification(TitledBorder.DEFAULT_JUSTIFICATION);//set the justification of the title to default (left justified)
		titleBorder.setTitlePosition(TitledBorder.BOTTOM);//set the location of the title to the bottom edge of the panel
		titleBorder.setTitleFont(content.getFont().deriveFont(Font.ITALIC));//set the font of the title to an italic version of the font of the content of this panel
		titleBorder.setTitleColor(Color.gray);//set the color of the title to grey

		//Add inner and outer padding to the "titleBorder" and set the border of this panel to the result
		setBorder(  BorderFactory.createCompoundBorder(	(BorderFactory.createEmptyBorder(5, 5, 5, 5)),
				BorderFactory.createCompoundBorder(titleBorder,
						(BorderFactory.createEmptyBorder(5, 5, 5, 5)) )  ));

		add(content);//actually add the  "content" JTextArea to this panel
		this.add(Box.createRigidArea(new Dimension(0,5)));//add 5 units of vertical spacing after the content
		
		//Define a maximum height of the proper height to contain it's components
		this.setMaximumSize(new Dimension(100000, this.getPreferredSize().height));
		
		//Add resize functionality to keep the maximum height current
		/** A reference to this event panel to use within the the resize component listener */
		final JPanel referenceToThis = this; 
		//Construct and add a new component listener to listen for a resize event
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) { //on resize...
				referenceToThis.setMaximumSize(new Dimension(100000, referenceToThis.getPreferredSize().height));//reset the maximum height appropriately 
				referenceToThis.invalidate(); //send an invalidate to signal to the parent container that this panel must be repainted
				((ListOfEventPanel) referenceToThis.getParent()).resizeFunction();//call the resize function on the parent (an instance of ListOfEventPanel) to resize the scroll pane holding the list appropriately
			}
		});
		//end resize functionality

	}


	/**
	 * @return the title of this panel (who did the event, when it happened, and what type of event it is)
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return the content of this panel- the actual body of the event containing the changes that occurred
	 */
	public JTextArea getContent() {
		return content;
	}

}
