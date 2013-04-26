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

@SuppressWarnings("serial")
public class JNumberTextField extends JTextField
{
    private static final char DOT = '.';
    private static final char NEGATIVE = '-';
    private static final String BLANK = "";
    private static final int DEF_PRECISION = 2;

    public static final int NUMERIC = 2;
    public static final int DECIMAL = 3;

    public static final String FM_NUMERIC = "0123456789";
    public static final String FM_DECIMAL = FM_NUMERIC + DOT;

    private int maxLength = 0;
    private int format = NUMERIC;
    private String negativeChars = BLANK;
    private String allowedChars = null;
    private boolean allowNegative = false;
    private int precision = 0;

    protected PlainDocument numberFieldFilter;

    public JNumberTextField()
    {
        this( 9, NUMERIC );
    }

    public JNumberTextField( int maxLen )
    {
        this( maxLen, NUMERIC );
    }

    public JNumberTextField( int maxLen, int format )
    {
        setAllowNegative( true );
        setMaxLength( maxLen );
        setFormat( format );

        numberFieldFilter = new JNumberFieldFilter();
        super.setDocument( numberFieldFilter );
    }

    public void setMaxLength( int maxLen )
    {
        if (maxLen > 0)
            maxLength = maxLen;
        else
            maxLength = 0;
    }

    public int getMaxLength()
    {
        return maxLength;
    }

    public void setPrecision( int precision )
    {
        if ( format == NUMERIC )
            return;

        if ( precision >= 0 )
            this.precision = precision;
        else
            this.precision = DEF_PRECISION;
    }

    public int getPrecision()
    {
        return precision;
    }

    public Number getNumber()
    {
        Number number = null;

        if ( format == NUMERIC )
            number = new Integer(getText());
        else
            number = new Double(getText());

        return number;
    }

    public void setNumber( Number value )
    {
        setText(String.valueOf(value));
    }

    public int getInt()
    {
        return Integer.parseInt( getText() );
    }

    public void setInt( int value )
    {
        setText( String.valueOf( value ) );
    }

    public float getFloat()
    {
        return Float.parseFloat(getText());
    }

    public void setFloat(float value)
    {
        setText( String.valueOf( value ) );
    }

    public double getDouble()
    {
        return Double.parseDouble(getText());
    }

    public void setDouble(double value)
    {
        setText( String.valueOf(value) );
    }

    public int getFormat()
    {
        return format;
    }

    public void setFormat(int format)
    {
        switch ( format )
        {
        case NUMERIC:
        default:
            this.format = NUMERIC;
            precision = 0;
            allowedChars = FM_NUMERIC;
            break;

        case DECIMAL:
            this.format = DECIMAL;
            precision = DEF_PRECISION;
            allowedChars = FM_DECIMAL;
            break;
        }
    }

    public void setAllowNegative( boolean value )
    {
        allowNegative = value;

        if ( value )
            negativeChars = "" + NEGATIVE;
        else
            negativeChars = BLANK;
    }

    public boolean isAllowNegative()
    {
        return allowNegative;
    }

    public void setDocument( Document document )
    {
    }

	class JNumberFieldFilter extends PlainDocument
    {
        private JNumberFieldFilter()
        {
            super();
        }

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