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
        _app = app;
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
