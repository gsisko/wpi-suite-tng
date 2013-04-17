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
package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import edu.wpi.cs.wpisuitetng.modules.core.models.User;

/**
 * This panel is added to the RequirementTabPanel and 
 * contains all the GUI components involving assigning a user to a requirement:
 * -a list of available users (in a scrollpane) not assigned to this requirement
 * -a list of users (in a scrollpane) assigned to this requirement
 * -two buttons to enable transfer of selected users between the lists (in an inner panel)
 */
@SuppressWarnings({"serial","rawtypes","unchecked"})
public class UserChooserTab extends JPanel {

	private UserListModel unassignedUserListModel;//The actual list of Users used to create the JList unassignedList
	private UserListModel assignedUserListModel;//The actual list of Users used to create the JList assignedList
	private JList unassignedList;//The JList that displays the available unassigned users
	private JList assignedList;//The JList that displays the users assigned to this requirement

	//A boolean indicating if input is enabled on the form 
	protected boolean inputEnabled;

	private RequirementTab parent; //Stores the RequirementTab that contains the panel

	private JScrollPane unassignedScroll;//The scroll pane to hold the JList of unassigned users ("unassignedList")
	private JScrollPane assignedScroll;//The scroll pane to hold the JList of users assigned to this requirement ("assignedList")

	private JButton addSelectedUserButton;//The button to trigger the assignment of a selected user to this requirement from the "unassignedList"
	private JButton removeSelectedUserButton;//The button to trigger the unassignment of a selected user to this requirement from the "assignedList"

	
	/**
	 * The constructor for UserChooserTab;
	 * Construct the panel, the components, and add the
	 * components to the panel.
	 * @param reqTabParent	The RequirementTab parent of this tab
	 */
	public UserChooserTab(RequirementTab reqTabParent) {

		parent = reqTabParent; //Set the RequirementPanel that contains this instance of this panel

		// Set the layout manager for this tab to a new Box layout with a line orientation (left to right)
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

		//Set an empty border around this panel for spacing purposes (10 units wide in all directions)
		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		//Create the UserListModels
		unassignedUserListModel = new UserListModel();
		assignedUserListModel = new UserListModel();

		//Create the JLists using the appropriate UserListModel
		unassignedList = new JList(unassignedUserListModel);
		assignedList = new JList(assignedUserListModel);

		//Dummy list of users
		ArrayList<User> users = new ArrayList<User>();

		users.add(new User("userA1", "userA", "aaa", 1));
		users.add(new User("userB2", "userB", "bbb", 2));
		users.add(new User("userC4", "userC", "ccc", 4));
		users.add(new User("userD5", "userD", "ddd", 5));
		users.add(new User("userE6", "userE", "eee", 6));
		users.add(new User("userF7", "userF", "fff", 7));
		users.add(new User("userG8", "userG", "ggg", 8));
		users.add(new User("userH09", "userH", "hhh", 9));
		//end dummy list of users

		//Add the users to the unassignedUserListModel
		for (int i = 0; i < users.size(); i++) {
			unassignedUserListModel.addUser(users.get(i));
		}

		//Create the scrollpanes to hold their corresponding lists
		unassignedScroll = new JScrollPane(unassignedList);
		assignedScroll = new JScrollPane(assignedList);

		//Find the maximum preferred width and height of the two lists
		double maxPrefHeight= 0;//Used to store the maximum preferred height
		double maxPrefWidth = 0;//Used to store the maximum preferred width
		
		if ((unassignedUserListModel.getSize() == 0) && (assignedUserListModel.getSize() == 0)) //If both lists are empty
		{
			//Set the maximum preferred width and height to a default
			maxPrefHeight = 300;
			maxPrefWidth = 140;
		}
		else if (unassignedUserListModel.getSize() == 0) //If only the unassigned user list is empty
		{
			//Use the preferred width and height of the assigned list
			maxPrefHeight = assignedScroll.getPreferredSize().getHeight();
			maxPrefWidth = assignedScroll.getPreferredSize().getWidth();
		}
		else if (assignedUserListModel.getSize() == 0)//If only the assigned list is empty
		{
			//Use the preferred width and height of the unassigned list
			maxPrefHeight = unassignedScroll.getPreferredSize().getHeight();
			maxPrefWidth = unassignedScroll.getPreferredSize().getWidth();
		}
		else //If neither list is empty
		{
			if (unassignedScroll.getPreferredSize().getHeight()>= assignedScroll.getPreferredSize().getHeight())//If the unassigned list has a equal or greater preferred height to the assigned list
				maxPrefHeight = unassignedScroll.getPreferredSize().getHeight();//Use the preferred height of the unassigned list
			else//The assigned list has a greater preferred height
				maxPrefHeight = assignedScroll.getPreferredSize().getHeight();//So use the preferred height of the assigned list
			
			if (unassignedScroll.getPreferredSize().getWidth()>= assignedScroll.getPreferredSize().getWidth())//If the unassigned list has a equal or greater preferred width to the assigned list
				maxPrefWidth = unassignedScroll.getPreferredSize().getWidth();//Use the preferred width of the unassigned list
			else//The assigned list has a greater preferred width
				maxPrefWidth = assignedScroll.getPreferredSize().getWidth();//So use the preferred width of the assigned list
		}
		//Add 10 to each value to account for the border
		maxPrefHeight += 10;
		maxPrefWidth += 10;
		
		//Set the preferred size of both of the scroll panes holding the lists to the same value (the maximum of the preferred widths and heights of the lists)
		unassignedScroll.setPreferredSize(new Dimension((int)maxPrefWidth,(int)maxPrefHeight));
		assignedScroll.setPreferredSize(new Dimension((int)maxPrefWidth,(int)maxPrefHeight));
		
		//Create the buttons
		addSelectedUserButton = new JButton("Add User ->");
		removeSelectedUserButton= new JButton("<- Remove User");
		
		//Create and set the titled borders for the lists
		//create the titled border for the list of assigned users
		TitledBorder assignedUsersTitleBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Assigned Users");
		assignedUsersTitleBorder.setTitleFont(addSelectedUserButton.getFont().deriveFont(12));//set the title font to the same font used on the "addSelectedUsersButton" in a size 12
		assignedUsersTitleBorder.setTitleColor(Color.gray);//set the color of the title to grey
		//create the titled border for the list of unassigned users
		TitledBorder unassignedUsersTitleBorder = BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "Unassigned Users"); 
		unassignedUsersTitleBorder.setTitleFont(assignedUsersTitleBorder.getTitleFont());//Use the same font as the "assignedUsersTitleBorder"
		unassignedUsersTitleBorder.setTitleColor(Color.gray);//set the color of the title to grey
		
		//add an inner border padding of 5 units all around to each titled border, and set the appropriate border to the corresponding scroll pane
		assignedScroll.setBorder(BorderFactory.createCompoundBorder(assignedUsersTitleBorder, (BorderFactory.createEmptyBorder(5, 5, 5, 5)) )  );
		unassignedScroll.setBorder(BorderFactory.createCompoundBorder(unassignedUsersTitleBorder, (BorderFactory.createEmptyBorder(5, 5, 5, 5)) )  );
		
		//Set the background of both of the scroll panes to white
		assignedScroll.setBackground(Color.WHITE);
		unassignedScroll.setBackground(Color.WHITE);
		
		//Create and add an action listener to the "addSelectedUserButton"
		addSelectedUserButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  if(!(unassignedList.isSelectionEmpty()))//if a user is selected in the unassigned user list at the time the button is pushed...
			  {
				  int index = unassignedList.getSelectedIndex();//Grab the index of the selected user in the unassigned user list
				  System.out.println("You have pushed the addSelectedUserButton!\n You have selected: "+ unassignedUserListModel.getElementAt(index));//Print a message containing the selected user to the console

				  assignedUserListModel.addUser(unassignedUserListModel.getUserAt(index));//add the appropriate user from the unassignedUserListModel to the assignedUserListModel
				  unassignedUserListModel.removeElementAt(index);//remove the appropriate user from the unassignedUserListModel
				  
				  //Revalidate and repaint both lists to ensure the change shows on the GUI
				  unassignedList.revalidate();
				  unassignedList.repaint();
				  assignedList.revalidate();
				  assignedList.repaint();
			  }
			  else//No users were selected in the unassigned user list at the time the button was pushed
				  System.out.println("You have pushed the addSelectedUserButton!\n No users are selected in the unassigned user list.\n");//Print a message to the console
			 
			  
		  }
		});
		
		//Create and add an action listener to the "removeSelectedUserButton"
		removeSelectedUserButton.addActionListener(new ActionListener()
		{
			  public void actionPerformed(ActionEvent e)
			  {
				  if(!(assignedList.isSelectionEmpty()))//if a user is selected in the assigned user list at the time the button is pushed...
				  {
					  int index = assignedList.getSelectedIndex();//Grab the index of the selected user in the assigned user list
					  System.out.println("You have pushed the removeSelectedUserButton!\n You have selected: "+ assignedUserListModel.getElementAt(index));//Print a message containing the selected user to the console

					  unassignedUserListModel.addUser(assignedUserListModel.getUserAt(index));//add the appropriate user from the assignedUserListModel to the unassignedUserListModel
					  assignedUserListModel.removeElementAt(index);//remove the appropriate user from the assignedUserListModel
					//Revalidate and repaint both lists to ensure the change shows on the GUI
					  unassignedList.revalidate();
					  unassignedList.repaint();
					  assignedList.revalidate();
					  assignedList.repaint();
				  }
				  else//No users were selected in the assigned user list at the time the button was pushed
					  System.out.println("You have pushed the removeSelectedUserButton!\n No users are selected in the assigned user list.\n");//Print a message to the console
				 
			  }
		});
		
		//Create the buttonPanel using a grid layout with 2 rows, 1 column, 0 horizontal spacing, and 6 units of vertical spacing between the components
		//This is a small inner panel to hold the buttons
		JPanel buttonPanel = new JPanel(new GridLayout(2,1,0,6));
		//Set the border of the buttonPanel to an empty boarder for spacing purposes
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 3, 3, 3));
		
		//add the buttons to the buttonPanel
		buttonPanel.add(addSelectedUserButton);
		buttonPanel.add(removeSelectedUserButton);
		
		//set the maximum size of the buttonPanel. This is especially important because a grid layout will stretch it's components to fill whatever space it can
		buttonPanel.setMaximumSize(new Dimension(140, 66));
		
		//End button panel
		

		//Add the components to this panel
		this.add(Box.createHorizontalGlue());//Add a horizontal glue component to keep the lists in the center of the panel
		this.add(unassignedScroll);//Add the list of unassigned users, in the "unassignedScroll" scroll pane to this panel
		this.add(Box.createRigidArea(new Dimension(15,0)));//Add a rigid area for spacing
		this.add(buttonPanel);//Add the inner "buttonPanel" containing the buttons to this panel
		this.add(Box.createRigidArea(new Dimension(15,0)));//Add a rigid area for spacing
		this.add(assignedScroll);//Add the list of assigned users, in the "assignedScroll" scroll pane to this panel
		this.add(Box.createHorizontalGlue());//Add a horizontal glue component to keep the lists in the center of the panel
		

	}

	/**
	 * @return the unassignedList
	 */
	public JList getUnassignedList() {
		return unassignedList;
	}

	/**
	 * @param unassignedList the unassignedList to set
	 */
	public void setUnassignedList(JList unassignedList) {
		this.unassignedList = unassignedList;
	}

	/**
	 * @return the assignedList
	 */
	public JList getAssignedList() {
		return assignedList;
	}

	/**
	 * @param assignedList the assignedList to set
	 */
	public void setAssignedList(JList assignedList) {
		this.assignedList = assignedList;
	}


	/**
	 * @return the addSelectedUserButton
	 */
	public JButton getAddSelectedUserButton() {
		return addSelectedUserButton;
	}

	/**
	 * @param addSelectedUserButton the addSelectedUserButton to set
	 */
	public void setAddSelectedUserButton(JButton addSelectedUserButton) {
		this.addSelectedUserButton = addSelectedUserButton;
	}

	/**
	 * @return the removeSelectedUserButton
	 */
	public JButton getRemoveSelectedUserButton() {
		return removeSelectedUserButton;
	}

	/**
	 * @param removeSelectedUserButton the removeSelectedUserButton to set
	 */
	public void setRemoveSelectedUserButton(JButton removeSelectedUserButton) {
		this.removeSelectedUserButton = removeSelectedUserButton;
	}

	/**
	 * @return the unassignedUserListModel
	 */
	public UserListModel getUnassignedUserListModel() {
		return unassignedUserListModel;
	}

	/**
	 * @param unassignedUserListModel the unassignedUserListModel to set
	 */
	public void setUnassignedUserListModel(UserListModel unassignedUserListModel) {
		this.unassignedUserListModel = unassignedUserListModel;
	}

	/**
	 * @return the assignedUserListModel
	 */
	public UserListModel getAssignedUserListModel() {
		return assignedUserListModel;
	}

	/**
	 * @param assignedUserListModel the assignedUserListModel to set
	 */
	public void setAssignedUserListModel(UserListModel assignedUserListModel) {
		this.assignedUserListModel = assignedUserListModel;
	}

	/**
	 * @return the parent RequirementTab
	 */
	public RequirementTab getReqTabParent() {
		return parent;
	}

}