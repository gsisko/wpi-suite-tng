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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFileChooser;

import org.apache.commons.codec.binary.Base64;

import edu.wpi.cs.wpisuitetng.janeway.gui.container.JanewayFrame;
import edu.wpi.cs.wpisuitetng.modules.dropbox.model.FileBlob;
import edu.wpi.cs.wpisuitetng.network.Network;
import edu.wpi.cs.wpisuitetng.network.Request;
import edu.wpi.cs.wpisuitetng.network.models.HttpMethod;

/**
 * 
 *
 */
public class AddFileController implements ActionListener {

	private final JFileChooser fileChooser;

	public AddFileController() {
		fileChooser = new JFileChooser();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		final int retVal = fileChooser.showDialog(JanewayFrame.getInstance(), "Upload");

		if (retVal == JFileChooser.APPROVE_OPTION) {
			final File file = fileChooser.getSelectedFile();
			byte[] fileContents = null;
			
			try {
				fileContents = readFile(file);
			}
			catch (IOException e) {
				System.out.println("Error occurred reading file!");
			}
			
			if (fileContents != null) {
				Request request = null;
				request = Network.getInstance().makeRequest("dropbox/fileblob", HttpMethod.PUT);
				request.setBody(new FileBlob(fileContents, file.getName()).toJSON());
				request.send();
			}
		}
	}

	public byte[] readFile(File file) throws IOException {
		final InputStream inStream = new FileInputStream(file);
		
		int offset = 0;
		int numRead = 0;
		int length = (int)file.length();
		byte[] retVal = new byte[length];
		
		while ((numRead = inStream.read(retVal, offset, length)) > 0) {
			System.out.println("NumRead: " + numRead);
			offset += numRead;
			length -= numRead;
		}
		inStream.close();
		return retVal;
	}
}
