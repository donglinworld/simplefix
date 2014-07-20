package simplefix.quickfix;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.FieldNotFound;
import simplefix.Tag;

public class Group implements simplefix.Group {

    private final static Logger log = LoggerFactory.getLogger(Group.class);

    final quickfix.Group _group;

    public Group(final quickfix.Group group) {
        super();
        _group = group;
    }

    public Object getValue(final Tag tag) {
        try {
            return _group.getString(tag.getTagNum());
        } catch (FieldNotFound e) {
            log.error("Exception:", e);
        }
        return null;
    }

    public void setValue(final Tag tag, final Object value) {
        _group.setString(tag.getTagNum(), value.toString());
    }

    public List<simplefix.Group> getGroupValue(final Tag tag) {
        List<quickfix.Group> quickGroups = _group.getGroups(tag.getTagNum());
        List<simplefix.Group> simpleGroups = new LinkedList<simplefix.Group>();
        for (quickfix.Group quickGroup : quickGroups) {
            simpleGroups.add(new Group(quickGroup));
        }
        return simpleGroups;
    }

    public void setGroupValue(final Tag tag, final List<simplefix.Group> value) {
        List<quickfix.Group> quickGroups = new LinkedList<quickfix.Group>();
        for (simplefix.Group simpleGroup : value) {
            if (simpleGroup instanceof Group) {
                quickGroups.add(((Group) simpleGroup)._group);
            }
        }
        _group.setGroups(tag.getTagNum(), quickGroups);
    }

    public quickfix.Group getQuickFixGroup() {
        return _group;
    }
}
