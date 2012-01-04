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
