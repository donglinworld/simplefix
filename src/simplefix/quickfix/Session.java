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

import quickfix.SessionID;
import simplefix.FixVersion;
import simplefix.Message;

public class Session implements simplefix.Session {

    SessionID _session;

    public Session(final SessionID session) {
        super();
        this._session = session;
    }

    public FixVersion getFixVersion() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getSenderCompID() {
        // TODO Auto-generated method stub
        return null;
    }

    public String getTargetCompID() {
        // TODO Auto-generated method stub
        return null;
    }

    public void sendAppMessage(final Message message) {
        // TODO Auto-generated method stub

    }

}
