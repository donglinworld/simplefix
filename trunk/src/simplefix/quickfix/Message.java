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

package simplefix.quickfix;

import simplefix.MsgType;
import simplefix.Tag;

public class Message implements simplefix.Message {

    quickfix.Message _msg;

    public Message(final quickfix.Message msg) {
        super();
        this._msg = msg;
    }

    public MsgType getMsgType() {
        // TODO Auto-generated method stub
        return null;
    }

    public Object getValue(final Tag tag) {
        // TODO Auto-generated method stub
        return null;
    }

    public void setValue(final Tag tag, final Object value) {
        // TODO Auto-generated method stub

    }

}
