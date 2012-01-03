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

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;

public class Application implements quickfix.Application {

    final simplefix.Application _app;

    public Application(final simplefix.Application app) {
        super();
        this._app = app;
    }

    public void onCreate(final SessionID sessionId) {
        // TODO Auto-generated method stub

    }

    public void onLogon(final SessionID sessionId) {
        _app.onLogon(new Session(sessionId));

    }

    public void onLogout(final SessionID sessionId) {
        _app.onLogout(new Session(sessionId));

    }

    public void toAdmin(final quickfix.Message message, final SessionID sessionId) {
        // TODO Auto-generated method stub

    }

    public void fromAdmin(final quickfix.Message message, final SessionID sessionId)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
        // TODO Auto-generated method stub

    }

    public void toApp(final quickfix.Message message, final SessionID sessionId) throws DoNotSend {
        // TODO Auto-generated method stub

    }

    public void fromApp(final quickfix.Message message, final SessionID sessionId)
            throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
        _app.onAppMessage(new Message(message), new Session(sessionId));

    }

}
