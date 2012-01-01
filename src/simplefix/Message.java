package simplefix;

public interface Message {
    
    MsgType getMsgType();
    
    Object getValue(Tag tag);
    
    void setValue(Tag tag, Object value);
    
}
