package simplefix;

public interface EngineFactory {

    Engine getEngine();

    Message createMessage(MsgType type);

    Message parseMessage(String msg);

}
