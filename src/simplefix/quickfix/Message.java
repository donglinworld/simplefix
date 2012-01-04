package simplefix.quickfix;

import quickfix.FieldNotFound;
import quickfix.InvalidMessage;
import simplefix.MsgType;
import simplefix.Tag;

public class Message implements simplefix.Message {

    quickfix.Message _msg;
    MsgType _type;

    public Message(final MsgType type) {
        _type = type;
        _msg = new quickfix.Message();
        _msg.setString(35, type.getTypeString());
    }

    public Message(final String msg) {

        try {
            _msg = new quickfix.Message(msg);

            try {
                _type = MsgType.fromString(_msg.getHeader().getString(35));
            } catch (FieldNotFound e) {
                e.printStackTrace();
            }

        } catch (InvalidMessage e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Message(final quickfix.Message msg) {
        super();
        _msg = msg;
        try {
            _type = MsgType.fromString(msg.getHeader().getString(35));
        } catch (FieldNotFound e) {
            e.printStackTrace();
        }
    }

    public MsgType getMsgType() {
        return _type;
    }

    public Object getValue(final Tag tag) {
        String str;
        try {
            str = _msg.getString(tag.getTagNum());
            if (str != null) {
                return str;
            }
            str = _msg.getHeader().getString(tag.getTagNum());
            if (str != null) {
                return str;
            }
            str = _msg.getTrailer().getString(tag.getTagNum());
            if (str != null) {
                return str;
            }
        } catch (FieldNotFound e) {
            e.printStackTrace();
        }
        ;
        return null;
    }

    public void setValue(final Tag tag, final Object value) {
        _msg.setString(tag.getTagNum(), value.toString());
    }

}
