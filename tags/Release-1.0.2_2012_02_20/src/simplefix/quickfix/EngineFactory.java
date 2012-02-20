package simplefix.quickfix;

import simplefix.MsgType;

public class EngineFactory implements simplefix.EngineFactory {

    public simplefix.Engine createEngine() {
        return new Engine();
    }

    public simplefix.Message createMessage(final MsgType type) {
        return new Message(type);
    }

    public simplefix.Message parseMessage(final String msg) {
        return new Message(msg);
    }

    public simplefix.Group createGroup(final int field, final int delim) {
        return new Group(new quickfix.Group(field, delim));
    }

}
