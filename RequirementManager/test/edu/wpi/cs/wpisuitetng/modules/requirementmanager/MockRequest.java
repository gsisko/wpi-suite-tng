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

import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.configuration.NetworkConfiguration;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

public class MockRequest extends Request {
	
	protected boolean sent = false;

	public MockRequest(NetworkConfiguration networkConfiguration, String path, HttpMethod requestMethod) {
		super(networkConfiguration, path, requestMethod);
	}

	@Override
	public void send() throws IllegalStateException {
		// don't actually send
		sent = true;
	}
	
	public boolean isSent() {
		return sent;
	}
}
