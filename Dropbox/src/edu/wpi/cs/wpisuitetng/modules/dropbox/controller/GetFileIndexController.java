/*******************************************************************************
 * Copyright (c) 2013 -- WPI Suite
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Chris Casola
 ******************************************************************************/
package edu.wpi.cs.wpisuitetng.modules.dropbox.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileIndex;
import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileListModel;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * 
 *
 */
public class GetFileIndexController implements ActionListener {
	
	private final FileListModel model;

	public GetFileIndexController(FileListModel model) {
		this.model = model;
	}

	/*
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Request request;
		request = Network.getInstance().makeRequest("dropbox/fileindex", HttpMethod.GET);
		request.addObserver(new GetFileIndexObserver(this));
		System.out.println("Sending request");
		request.send();
	}

	public void responseSuccess(FileIndex fileIndex) {
		model.setModel(fileIndex);
	}

}
