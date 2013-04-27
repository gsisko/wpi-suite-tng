package edu.wpi.cs.wpisuitetng.modules.requirementmanager.list.views;

import java.awt.Toolkit;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;

public class DataFilter extends DocumentFilter{
	int currentValue = 0;

	public void insertString(DocumentFilter.FilterBypass fb, int offset,
	String text, AttributeSet attr) throws BadLocationException {

	if (text == null) {
	return;
	} else {
	replace(fb, offset, 0, text, attr);
	}

	}

	public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
	String text, AttributeSet attr) throws BadLocationException {
	Document doc = fb.getDocument();
	int currentLength = doc.getLength();
	String currentContent = doc.getText(0, currentLength);
	String before = currentContent.substring(0, offset);
	String after = currentContent.substring(
	length + offset, currentLength);
	String newValue = before +
	(text == null ? "" : text) + after;
	currentValue = checkInput(newValue, offset);
	fb.replace(offset, length, text, attr);
}
	
	private int checkInput(String numValue, int offset)
			throws BadLocationException {
			int theValue = 0;
			boolean isNum = false;

			if (numValue == null) {
			isNum = true;
			}

			if (numValue.length() > 0) {
			try {
			theValue = Integer.parseInt(numValue);
			if (theValue > 0) {
			isNum = true;
			}
			} catch (NumberFormatException e) {
			throw new BadLocationException(
			numValue, offset);
			}
			}
			if (!isNum) {
			Toolkit.getDefaultToolkit().beep();
			}

			return theValue;
			}
	}
			

	
