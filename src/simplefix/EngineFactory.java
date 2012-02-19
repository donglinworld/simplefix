package simplefix;

public interface EngineFactory {

    Engine createEngine();

    Message createMessage(MsgType type);

    Message parseMessage(String msg);

    Group createGroup(int field, int delim);
}
