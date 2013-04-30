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

package edu.wpi.cs.wpisuitetng.modules.requirementmanager.views;

import javax.swing.*;
import javax.swing.text.*;

/** A text field that takes only numbers. Nothing else may
 * be entered into suchfields
 * 
 */
@SuppressWarnings("serial")
public class JNumberTextField extends JTextField
{
	/** string representation of a dot */
	private static final char DOT = '.';
	/** string representation of a dash or negative sign*/
	private static final char NEGATIVE = '-';
	/** string representation of blank */
	private static final String BLANK = "";
	/** the precision of the numbers */
	private static final int DEF_PRECISION = 2;

	/** Bit code to use when selecting NUMERIC */
	public static final int NUMERIC = 2;
	/** Bit code to use when selecting DECIMAL */
	public static final int DECIMAL = 3;

	/** Accepted characters allowed in numeric mode, in string form */
	public static final String FM_NUMERIC = "0123456789";
	/** Accepted characters allowed in decimal mode, in string form */
	public static final String FM_DECIMAL = FM_NUMERIC + DOT;

	/** the max length allowed */
	private int maxLength = 0;
	/** the format of the text field */
	private int format = NUMERIC;
	/** what a negative character is */
	private String negativeChars = BLANK;
	/** the allow characters */
	private String allowedChars = null;
	/** whether or not negative numbers are allowed  */
	private boolean allowNegative = false;
	/** the precision of the text field */
	private int precision = 0;

	/** the filter over things typed into the text field */
	protected PlainDocument numberFieldFilter;

	/** standard constructor */
	public JNumberTextField()
	{
		this( 9, NUMERIC );
	}

	/** Constructor that is sized accordingly
	 * 
	 * @param maxLen the number of characters allowed in the text box
	 */
	public JNumberTextField( int maxLen )
	{
		this( maxLen, NUMERIC );
	}

	/** constructor that is sized and formatted accordingly
	 * 
	 * @param maxLen the number of characters allowed in the text box
	 * @param format the format of the text field
	 */
	public JNumberTextField( int maxLen, int format )
	{
		setAllowNegative( true );
		setMaxLength( maxLen );
		setFormat( format );

		numberFieldFilter = new JNumberFieldFilter();
		super.setDocument( numberFieldFilter );
	}

	/** Sets the max length. Does not allow negative lengths 
	 * 
	 * @param maxLen the length to be set to
	 */ 
	public void setMaxLength( int maxLen )
	{
		if (maxLen > 0)
			maxLength = maxLen;
		else
			maxLength = 0;
	}

	/** Gets the max length
	 * 
	 * @return the maximum length
	 */
	public int getMaxLength()
	{
		return maxLength;
	}

	/** Sets the precision accordingly. If a negative number was input,
	 *  the precision is set to the default
	 *  
	 * @param precision the precision to set to
	 */
	public void setPrecision( int precision )
	{
		if ( format == NUMERIC )
			return;

		if ( precision >= 0 )
			this.precision = precision;
		else
			this.precision = DEF_PRECISION;
	}

	/** Gets the precision
	 * 
	 * @return the current precision
	 */
	public int getPrecision()
	{
		return precision;
	}

	/** Gets the number currently in the text field
	 * 
	 * @return the number currently in the text field
	 */
	public Number getNumber()
	{
		Number number = null;

		if ( format == NUMERIC )
			number = new Integer(getText());
		else
			number = new Double(getText());

		return number;
	}

	/** Puts the given value into the text field
	 * 
	 * @param value the number to put into the text field
	 */
	public void setNumber( Number value )
	{
		setText(String.valueOf(value));
	}

	/** Gets the value in the text field as an int
	 * 
	 * @return
	 */
	public int getInt()
	{
		return Integer.parseInt( getText() );
	}

	/** sets the value in the text field to a given int
	 * 
	 * @param value the value to set
	 */ 
	public void setInt( int value )
	{
		setText( String.valueOf( value ) );
	}

	/**
	 * 
	 * @return
	 */
	public float getFloat()
	{
		return Float.parseFloat(getText());
	}

	/**
	 * 
	 * @param value
	 */
	public void setFloat(float value)
	{
		setText( String.valueOf( value ) );
	}

	/**
	 * 
	 * @return
	 */
	public double getDouble()
	{
		return Double.parseDouble(getText());
	}

	/**
	 * 
	 * @param value
	 */
	public void setDouble(double value)
	{
		setText( String.valueOf(value) );
	}

	/**
	 * 
	 * @return
	 */
	public int getFormat()
	{
		return format;
	}

	/** Sets the format to the given format. If that format is
	 *  not recognized, the default will be used.
	 * 
	 * @param format the format to set to
	 */
	public void setFormat(int format)
	{
		switch ( format )
		{
		case DECIMAL:
			this.format = DECIMAL;
			precision = DEF_PRECISION;
			allowedChars = FM_DECIMAL;
			break;
		case NUMERIC:
		default:
			this.format = NUMERIC;
			precision = 0;
			allowedChars = FM_NUMERIC;
			break;
		}
	}

	/**
	 * 
	 * @param value
	 */
	public void setAllowNegative( boolean value )
	{
		allowNegative = value;

		if ( value )
			negativeChars = "" + NEGATIVE;
		else
			negativeChars = BLANK;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isAllowNegative()
	{
		return allowNegative;
	}

	/**
	 * 
	 */
	public void setDocument( Document document )
	{
	}

	/** A filter that works with JNumberFields. Does not allow non-numeric
	 *  characters to be put in the text field	 */
	class JNumberFieldFilter extends PlainDocument
	{
		/** basic constructor */
		private JNumberFieldFilter()
		{

		}

		/** Inserts the string with the given offset and applicable attributes
		 * 
		 * @param offset to offset by
		 * @param str the string to set
		 * @param attr the attribute to apply
		 * 
		 */
		public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException
		{
			String text = getText(0,offset) + str + getText(offset,(getLength() - offset));

			if ( str == null || text == null )
				return;

			for ( int i=0; i<str.length(); i++ )
			{
				if ( ( allowedChars + negativeChars ).indexOf( str.charAt(i) ) == -1)
					return;
			}

			int precisionLength = 0, dotLength = 0, minusLength = 0;
			int textLength = text.length();

			try
			{
				if ( format == NUMERIC )
				{
					if ( ! ( ( text.equals( negativeChars ) ) && ( text.length() == 1) ) )
						new Long(text);
				}
				else if ( format == DECIMAL )
				{
					if ( ! ( ( text.equals( negativeChars ) ) && ( text.length() == 1) ) )
						new Double(text);

					int dotIndex = text.indexOf(DOT);
					if( dotIndex != -1 )
					{
						dotLength = 1;
						precisionLength = textLength - dotIndex - dotLength;

						if( precisionLength > precision )
							return;
					}
				}
			}
			catch(Exception ex)
			{
				return;
			}

			if ( text.startsWith( "" + NEGATIVE ) )
			{
				if ( !allowNegative )
					return;
				else
					minusLength = 1;
			}

			if ( maxLength < ( textLength - dotLength - precisionLength - minusLength ) )
				return;

			super.insertString( offset, str, attr );
		}
	}
}