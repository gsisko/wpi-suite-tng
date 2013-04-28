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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.tabs;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views.ListView;
import edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement.RequirementView;

/**
 * This provides a tab component with a close button to the left of the title.
 */
@SuppressWarnings("serial")
public class ClosableTabComponent extends JPanel implements ActionListener {
	
	// tabbed pane to be given a close button
	private final JTabbedPane tabbedPane;
	
	/**
	 * Create a close-able tab component belonging to the given tabbedPane.
	 * The title is extracted with {@link JTabbedPane#getTitleAt(int)}.
	 * @param tabbedPane  The JTabbedPane this tab component belongs to
	 */
	public ClosableTabComponent(JTabbedPane tabbedPane) {
		super(new FlowLayout(FlowLayout.LEFT, 0, 0));
		this.tabbedPane = tabbedPane;
		setOpaque(false);
		
		final JLabel label = new JLabel() {
			// display the title according to what's set on our JTabbedPane
			@Override
			public String getText() {
				final JTabbedPane tabbedPane = ClosableTabComponent.this.tabbedPane;
				final int index = tabbedPane.indexOfTabComponent(ClosableTabComponent.this);
				return index > -1 ? tabbedPane.getTitleAt(index) : "";
			}
		};
		label.setBorder(BorderFactory.createEmptyBorder(3, 0, 2, 7));
		add(label);
		
		final JButton closeButton = new JButton("\u2716");
		closeButton.setFont(closeButton.getFont().deriveFont((float) 8));
		closeButton.setMargin(new Insets(0, 0, 0, 0));
		closeButton.addActionListener(this);
		add(closeButton);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// close this tab when close button is clicked
		final int index = tabbedPane.indexOfTabComponent(this);
		
		Component comp = tabbedPane.getComponentAt(index);
		int prevIndex = tabbedPane.getSelectedIndex();
		
		if(comp instanceof RequirementView && ((RequirementView) comp).getRequirementPanel().getAttributePanel().isSaving()) {
			if (JOptionPane.showOptionDialog(this, "The requirement is still saving.  Are you sure you want to exit?", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {
				if (index> -1) {
					tabbedPane.remove(index);
				}
			}
		} else if(comp instanceof RequirementView && ((RequirementView) comp).getRequirementPanel().getAttributePanel().isFieldsChanged()) {
			tabbedPane.setSelectedIndex(index);
			if (JOptionPane.showOptionDialog(this, "You have unsaved changes to this requirement.  Are you sure you want to exit?", "Warning",
					JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null) == JOptionPane.OK_OPTION) {
				if (index> -1) {
					tabbedPane.remove(index);
					if (prevIndex < index)
						tabbedPane.setSelectedIndex(prevIndex);
					else
						tabbedPane.setSelectedIndex(prevIndex-1);
				}
			}
		} else if (index > -1) {
			tabbedPane.remove(index);
			if (((MainTabPanel)tabbedPane).getNonRequirementTabCount() == 0)
				((ListView)tabbedPane.getComponentAt(0)).getBtnEdit().setEnabled(true);
			else
				((ListView)tabbedPane.getComponentAt(0)).getBtnEdit().setEnabled(false);
		}
	}
	
}
