package simplefix;

import java.util.List;

public interface Group {

    Object getValue(Tag tag);

    void setValue(Tag tag, Object value);

    List<Group> getGroupValue(Tag tag);

    void setGroupValue(Tag tag, List<Group> value);
}
