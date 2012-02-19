package simplefix.quickfix;

import quickfix.SessionID;
import quickfix.SessionNotFound;
import simplefix.FixVersion;

public class Session implements simplefix.Session {

    SessionID _session;

    public Session(final SessionID session) {
        super();
        _session = session;
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

    public void sendAppMessage(final simplefix.Message msg) {

        quickfix.Message message;
        if (msg instanceof Message) {
            message = ((Message) msg)._msg;
        } else {
            return;
        }
        try {
            quickfix.Session session = quickfix.Session.lookupSession(_session);
            if (session == null) {
                throw new SessionNotFound(_session.toString());
            }
            session.send(message);
        } catch (SessionNotFound e) {
            e.printStackTrace();
        }

    }
}
