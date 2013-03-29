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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.dashboard;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * This panel will show notifications, search widgets.
 */
@SuppressWarnings("serial")
public class DashboardView extends JPanel {
	
	public DashboardView() {
		JLabel testLabel = new JLabel("This is the dashboard panel.");
		this.add(testLabel);
	}
}
