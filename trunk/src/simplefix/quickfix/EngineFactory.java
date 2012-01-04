package simplefix.quickfix;

import simplefix.MsgType;

public class EngineFactory implements simplefix.EngineFactory {

    private static simplefix.Engine _engine;

    public simplefix.Engine getEngine() {
        if (_engine == null) {
            _engine = new Engine();
        }
        return _engine;

    }

    public simplefix.Message createMessage(final MsgType type) {
        return new Message(type);
    }

    public simplefix.Message parseMessage(final String msg) {
        return new Message(msg);
    }

}
