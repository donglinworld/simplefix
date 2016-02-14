/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved.
 * 
 * This file is part of the QuickFIX FIX Engine
 * 
 * This file may be distributed under the terms of the quickfixengine.org
 * license as defined by quickfixengine.org and appearing in the file
 * LICENSE included in the packaging of this file.
 * 
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A
 * PARTICULAR PURPOSE.
 * 
 * See http://www.quickfixengine.org/LICENSE for licensing information.
 * 
 * Contact ask@quickfixengine.org if any conditions of this licensing
 * are not clear to you.
 ******************************************************************************/

package quickfix;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import org.quickfixj.CharsetSupport;

/**
 * Base class for FIX message fields. This class should be
 * abstract but that would break compatibility with the QF JNI
 * classes.
 */
public /*abstract*/ class Field<T> implements Serializable{
    static final long serialVersionUID = 7098326013456432197L;
    private int tag;
    private T object;
    private boolean isCalculated = false;
    private String data;

    public Field(final int field, final T object) {
        this.tag = field;
        this.object = object;
    }

    /**
     * Gets the field's tag.
     * 
     * @return the tag
     */
    public int getTag() {
        return tag;
    }

    /**
     * Gets the field's tag. (QF/C++ compatibility)
     * 
     * @return the tag
     * @see quickfix.Field#getTag()
     */
    public int getField() {
        return getTag();
    }

    /**
     * Sets the field's value to the given object.
     * @param object
     */
    protected void setObject(final T object) {
        this.object = object;
        isCalculated = false;
    }

    /**
     * Get the field value
     * @return an object representing the field's value
     */
    public T getObject() {
        return object;
    }


    /**
     * Return's the formatted field (tag=value<SOH>)
     * @return the formatted field
     */
    @Override
    public String toString() {
        calculate();
        return data;
    }

    /*package*/ void toString(final StringBuffer buffer) {
        buffer.append(tag).append('=').append(objectAsString());
    }

    protected String objectAsString() {
        return object.toString();
    }

    @Override
    public boolean equals(final Object object) {
        if (super.equals(object) == true) {
            return true;
        }
        if (!(object instanceof Field)) {
            return false;
        }
        return tag == ((Field<?>) object).getField()
                && getObject().equals(((Field<?>) object).getObject());
    }

    @Override
    public int hashCode() {
        return object.hashCode();
    }

    /*package*/ int getLength() {
        calculate();
        try {
            return data.getBytes(CharsetSupport.getCharset()).length + 1;
        } catch (UnsupportedEncodingException e) {
            return data.length() + 1;
        }
    }

    /*package*/ int getTotal() {
        calculate();
        int sum = 0;

        byte[] byteData = null;
        try {
            byteData = data.getBytes(CharsetSupport.getCharset());
        } catch (UnsupportedEncodingException e) {
            byteData = null;
        }
        if (byteData != null) {
            for (int i = 0; i < byteData.length; i++) {
                sum += MessageUtils.getUnsignedByte(byteData[i]);
            }
        } else {
            for (int i = 0; i < data.length(); i++) {
                sum += data.charAt(i);
            }
        }
        return sum+1;
    }

    private void calculate() {
        if (isCalculated) {
            return;
        }

        StringBuffer buffer = new StringBuffer();
        toString(buffer);
        data = buffer.toString();

        isCalculated = true;
    }

    public void setTag(final int tag) {
        this.tag = tag;
        isCalculated = false;
        calculate();
    }
}