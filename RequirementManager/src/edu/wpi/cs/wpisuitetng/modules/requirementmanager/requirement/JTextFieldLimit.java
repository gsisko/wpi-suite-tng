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
 * Code sourced from:
 * http://www.java2s.com/Code/Java/Swing-JFC/LimitJTextFieldinputtoamaximumlength.htm
 ******************************************************************************/

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.requirement;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

@SuppressWarnings("serial")
public class JTextFieldLimit extends PlainDocument {
	private int limit;
	public JTextFieldLimit(int limit) {
		
		this.limit = limit;
	}

	JTextFieldLimit(int limit, boolean upper) {
		
		this.limit = limit;
	}

	public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		if (str == null)
			return;

		if ((getLength() + str.length()) <= limit) {
			super.insertString(offset, str, attr);
		}
	}
}