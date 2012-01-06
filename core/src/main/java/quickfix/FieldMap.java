/*******************************************************************************
 * Copyright (c) quickfixengine.org  All rights reserved. 
 * 
 * This file is part of the QuickFIX FIX Engine 
 * 
 * This file may be distributed under the terms of the quickfixengine.org 
 * license as defined by quickfixengine.org and appearing in the file 
 * LICENSE included in the packaging of this file. 
 * 
 * This file is provided AS IS with NO WARRANTY OF ANY KIND, INCLUDING 
 * THE WARRANTY OF DESIGN, MERCHANTABILITY AND FITNESS FOR A 
 * PARTICULAR PURPOSE. 
 * 
 * See http://www.quickfixengine.org/LICENSE for licensing information. 
 * 
 * Contact ask@quickfixengine.org if any conditions of this licensing 
 * are not clear to you.
 ******************************************************************************/

package quickfix;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import quickfix.field.BeginString;
import quickfix.field.BodyLength;
import quickfix.field.CheckSum;
import quickfix.field.SessionRejectReason;
import quickfix.field.converter.BooleanConverter;
import quickfix.field.converter.CharConverter;
import quickfix.field.converter.DecimalConverter;
import quickfix.field.converter.DoubleConverter;
import quickfix.field.converter.IntConverter;
import quickfix.field.converter.UtcDateOnlyConverter;
import quickfix.field.converter.UtcTimeOnlyConverter;
import quickfix.field.converter.UtcTimestampConverter;

/**
 * Field container used by messages, groups, and composites.
 */
public abstract class FieldMap implements Serializable {

    static final long serialVersionUID = -3193357271891865972L;

    private final int[] fieldOrder;

    private final TreeMap<Integer, Field<?>> fields;

    private final TreeMap<Integer, List<Group>> groups = new TreeMap<Integer, List<Group>>();

    protected FieldMap(final int[] fieldOrder) {
        this.fieldOrder = fieldOrder;
        fields = new TreeMap<Integer, Field<?>>(fieldOrder != null
                ? new FieldOrderComparator()
                : null);
    }

    protected FieldMap() {
        this(null);
    }

    public int[] getFieldOrder() {
        return fieldOrder;
    }

    public void clear() {
        fields.clear();
        groups.clear();
    }

    public boolean isEmpty() {
        return fields.size() == 0;
    }

    private class FieldOrderComparator implements Comparator<Integer>, Serializable {
        static final long serialVersionUID = 3416006398018829270L;

        public int compare(final Integer tag1, final Integer tag2) {
            final int index1 = indexOf(tag1.intValue(), getFieldOrder());
            final int index2 = indexOf(tag2.intValue(), getFieldOrder());

            if ((index1 != Integer.MAX_VALUE) && (index2 != Integer.MAX_VALUE)) {
                // We manage two ordered fields
                return index1 != index2 ? (index1 < index2 ? -1 : 1) : 0;
            } else if ((index1 == Integer.MAX_VALUE) || (index2 == Integer.MAX_VALUE)) {
                if (index1 != index2) {
                    return (index1 == Integer.MAX_VALUE ? 1 : -1);
                } else {
                    // index1 and index2 equals to Integer.MAX_VALUE so use the
                    // tags
                    return tag1.intValue() != tag2.intValue() ? (tag1.intValue() < tag2.intValue()
                            ? -1
                            : 1) : 0;
                }
            } else {
                return tag1.intValue() != tag2.intValue() ? (tag1.intValue() < tag2.intValue()
                        ? -1
                        : 1) : 0;
            }
        }

        private int indexOf(final int tag, final int[] order) {
            if (order != null) {
                for (int i = 0; i < order.length; i++) {
                    if (tag == order[i]) {
                        return i;
                    }
                }
            }
            return Integer.MAX_VALUE;
        }
    }

    public void setFields(final FieldMap fieldMap) {
        fields.clear();
        fields.putAll(fieldMap.fields);
    }

    protected void setComponent(final MessageComponent component) {
        component.copyTo(this);
    }

    protected void getComponent(final MessageComponent component) {
        component.clear();
        component.copyFrom(this);
    }

    public void setGroups(final FieldMap fieldMap) {
        groups.clear();
        groups.putAll(fieldMap.groups);
    }

    public void setGroups(final int key, final List<Group> groupList) {
        groups.put(key, groupList);
    }

    public void setString(final int field, final String value) {
        setField(new StringField(field, value));
    }

    public void setBytes(final int field, final byte[] value) {
        setField(field, new BytesField(field, value));
    }

    public void setBoolean(final int field, final boolean value) {
        final String s = BooleanConverter.convert(value);
        setField(new StringField(field, s));
    }

    public void setChar(final int field, final char value) {
        final String s = CharConverter.convert(value);
        setField(new StringField(field, s));
    }

    public void setInt(final int field, final int value) {
        final String s = IntConverter.convert(value);
        setField(new StringField(field, s));
    }

    public void setDouble(final int field, final double value) {
        setDouble(field, value, 0);
    }

    public void setDouble(final int field, final double value, final int padding) {
        final String s = DoubleConverter.convert(value, padding);
        setField(new StringField(field, s));
    }

    public void setDecimal(final int field, final BigDecimal value) {
        setField(new StringField(field, DecimalConverter.convert(value)));
    }

    public void setDecimal(final int field, final BigDecimal value, final int padding) {
        final String s = DecimalConverter.convert(value, padding);
        setField(new StringField(field, s));
    }

    public void setUtcTimeStamp(final int field, final Date value) {
        setUtcTimeStamp(field, value, false);
    }

    public void
            setUtcTimeStamp(final int field, final Date value, final boolean includeMilliseconds) {
        final String s = UtcTimestampConverter.convert(value, includeMilliseconds);
        setField(new StringField(field, s));
    }

    public void setUtcTimeOnly(final int field, final Date value) {
        setUtcTimeOnly(field, value, false);
    }

    public void setUtcTimeOnly(final int field, final Date value, final boolean includeMillseconds) {
        final String s = UtcTimeOnlyConverter.convert(value, includeMillseconds);
        setField(new StringField(field, s));
    }

    public void setUtcDateOnly(final int field, final Date value) {
        final String s = UtcDateOnlyConverter.convert(value);
        setField(new StringField(field, s));
    }

    public String getString(final int field) throws FieldNotFound {
        return getField(field).getObject();
    }

    StringField getField(final int field) throws FieldNotFound {
        final StringField f = (StringField) fields.get(field);
        if (f == null) {
            throw new FieldNotFound(field);
        }
        return f;
    }

    Field<?> getField(final int field, final Field<?> defaultValue) {
        final Field<?> f = fields.get(field);
        if (f == null) {
            return defaultValue;
        }
        return f;
    }

    public boolean getBoolean(final int field) throws FieldNotFound {
        final String value = getField(field).getValue();
        try {
            return BooleanConverter.convert(value);
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public char getChar(final int field) throws FieldNotFound {
        final String value = getField(field).getValue();
        try {
            return CharConverter.convert(value);
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public int getInt(final int field) throws FieldNotFound {
        final String value = getField(field).getValue();
        try {
            return IntConverter.convert(value);
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public double getDouble(final int field) throws FieldNotFound {
        final String value = getField(field).getValue();
        try {
            return DoubleConverter.convert(value);
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public BigDecimal getDecimal(final int field) throws FieldNotFound {
        final String value = getField(field).getValue();
        try {
            return DecimalConverter.convert(value);
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public Date getUtcTimeStamp(final int field) throws FieldNotFound {
        final String value = getField(field).getValue();
        try {
            return UtcTimestampConverter.convert(value);
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public Date getUtcTimeOnly(final int field) throws FieldNotFound {
        final String value = getField(field).getValue();
        try {
            return UtcTimeOnlyConverter.convert(value);
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public Date getUtcDateOnly(final int field) throws FieldNotFound {
        final String value = getField(field).getValue();
        try {
            return UtcDateOnlyConverter.convert(value);
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field);
        }
    }

    public void setField(final int key, final Field<?> field) {
        fields.put(key, field);
    }

    public void setField(final StringField field) {
        if (field.getValue() == null) {
            throw new NullPointerException("Null field values are not allowed.");
        }
        fields.put(field.getField(), field);
    }

    public void setField(final BooleanField field) {
        setBoolean(field.getField(), field.getValue());
    }

    public void setField(final CharField field) {
        setChar(field.getField(), field.getValue());
    }

    public void setField(final IntField field) {
        setInt(field.getField(), field.getValue());
    }

    public void setField(final DoubleField field) {
        setDouble(field.getField(), field.getValue());
    }

    public void setField(final DecimalField field) {
        setDecimal(field.getField(), field.getValue());
    }

    public void setField(final UtcTimeStampField field) {
        setUtcTimeStamp(field.getField(), field.getValue(), field.showMilliseconds());
    }

    public void setField(final UtcTimeOnlyField field) {
        setUtcTimeOnly(field.getField(), field.getValue(), field.showMilliseconds());
    }

    public void setField(final UtcDateOnlyField field) {
        setUtcDateOnly(field.getField(), field.getValue());
    }

    public void setField(final BytesField field) {
        setBytes(field.getField(), field.getObject());
    }

    public StringField getField(final StringField field) throws FieldNotFound {
        return (StringField) getFieldInternal(field);
    }

    private Field<String> getFieldInternal(final Field<String> field) throws FieldNotFound {
        field.setObject(getField(field.getField()).getObject());
        return field;
    }

    public BytesField getField(final BytesField field) throws FieldNotFound {
        final Field<?> returnField = fields.get(field.getField());
        if (returnField == null) {
            throw new FieldNotFound(field.getField());
        } else if (!(returnField instanceof BytesField)) {
            throw new FieldException(SessionRejectReason.INCORRECT_DATA_FORMAT_FOR_VALUE,
                    field.getField());
        } else {
            return (BytesField) returnField;
        }
    }

    public BooleanField getField(final BooleanField field) throws FieldNotFound {
        try {
            final String value = getField(field.getField()).getValue();
            field.setObject(Boolean.valueOf(BooleanConverter.convert(value)));
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field.getField());
        } catch (final FieldNotFound e) {
            throw e;
        }
        return field;
    }

    public CharField getField(final CharField field) throws FieldNotFound {
        try {
            final String value = getField(field.getField()).getValue();
            field.setObject(Character.valueOf(CharConverter.convert(value)));
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field.getField());
        } catch (final FieldNotFound e) {
            throw e;
        }
        return field;
    }

    public IntField getField(final IntField field) throws FieldNotFound {
        try {
            final String value = getField(field.getField()).getValue();
            field.setObject(IntConverter.convert(value));
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field.getField());
        } catch (final FieldNotFound e) {
            throw e;
        }
        return field;
    }

    public DoubleField getField(final DoubleField field) throws FieldNotFound {
        try {
            final String value = getField(field.getField()).getValue();
            field.setObject(new Double(DoubleConverter.convert(value)));
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field.getField());
        } catch (final FieldNotFound e) {
            throw e;
        }
        return field;
    }

    public DecimalField getField(final DecimalField field) throws FieldNotFound {
        try {
            final String value = getField(field.getField()).getValue();
            field.setObject(DecimalConverter.convert(value));
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field.getField());
        } catch (final FieldNotFound e) {
            throw e;
        }
        return field;
    }

    public UtcTimeStampField getField(final UtcTimeStampField field) throws FieldNotFound {
        try {
            final String value = getField(field.getField()).getValue();
            field.setObject(UtcTimestampConverter.convert(value));
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field.getField());
        } catch (final FieldNotFound e) {
            throw e;
        }
        return field;
    }

    public UtcTimeOnlyField getField(final UtcTimeOnlyField field) throws FieldNotFound {
        try {
            final String value = getField(field.getField()).getValue();
            field.setObject(UtcTimeOnlyConverter.convert(value));
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field.getField());
        } catch (final FieldNotFound e) {
            throw e;
        }
        return field;
    }

    public UtcDateOnlyField getField(final UtcDateOnlyField field) throws FieldNotFound {
        try {
            final String value = getField(field.getField()).getValue();
            field.setObject(UtcDateOnlyConverter.convert(value));
        } catch (final FieldConvertError e) {
            throw newIncorrectDataException(e, field.getField());
        } catch (final FieldNotFound e) {
            throw e;
        }
        return field;
    }

    private FieldException newIncorrectDataException(final FieldConvertError e, final int tag) {
        return new FieldException(SessionRejectReason.INCORRECT_DATA_FORMAT_FOR_VALUE,
                e.getMessage(), tag);
    }

    public boolean isSetField(final int field) {
        return fields.containsKey(field);
    }

    public boolean isSetField(final Field<?> field) {
        return isSetField(field.getField());
    }

    public void removeField(final int field) {
        fields.remove(field);
    }

    public Iterator<Field<?>> iterator() {
        return fields.values().iterator();
    }

    protected void initializeFrom(final FieldMap source) {
        fields.clear();
        fields.putAll(source.fields);
        final Iterator<Map.Entry<Integer, List<Group>>> groupItr = source.groups.entrySet()
                .iterator();
        while (groupItr.hasNext()) {
            final Map.Entry<Integer, List<Group>> entry = groupItr.next();
            final List<Group> clonedMembers = new ArrayList<Group>();
            final List<Group> groupMembers = entry.getValue();
            for (int i = 0; i < groupMembers.size(); i++) {
                final Group originalGroup = groupMembers.get(i);
                final Group clonedGroup = new Group(originalGroup.getFieldTag(),
                        originalGroup.delim(), originalGroup.getFieldOrder());
                clonedGroup.initializeFrom(originalGroup);
                clonedMembers.add(clonedGroup);
            }
            groups.put(entry.getKey(), clonedMembers);
        }
    }

    private boolean isGroupField(final int field) {
        return groups.containsKey(field);
    }

    protected void calculateString(final StringBuffer buffer, final int[] preFields,
            final int[] postFields) {
        if (preFields != null) {
            for (final int preField : preFields) {
                final Field<?> field = getField(preField, null);
                if (field != null) {
                    field.toString(buffer);
                    buffer.append('\001');
                }
            }
        }

        for (final Field<?> field2 : fields.values()) {
            final Field<?> field = field2;
            final int tag = field.getField();
            if (!isOrderedField(tag, preFields) && !isOrderedField(tag, postFields)
                    && !isGroupField(tag)) {
                field.toString(buffer);
                buffer.append('\001');
            } else if (isGroupField(tag) && isOrderedField(tag, fieldOrder)
                    && getGroupCount(tag) > 0) {
                final List<Group> groups = getGroups(tag);
                field.toString(buffer);
                buffer.append('\001');
                for (int i = 0; i < groups.size(); i++) {
                    final FieldMap groupFields = groups.get(i);
                    groupFields.calculateString(buffer, preFields, postFields);
                }
            }
        }

        for (final Entry<Integer, List<Group>> entry : groups.entrySet()) {
            final Integer groupCountTag = entry.getKey();
            if (!isOrderedField(groupCountTag.intValue(), fieldOrder)) {
                final List<Group> groups = entry.getValue();
                int groupCount = groups.size();
                if (groupCount > 0) {
                    final IntField countField = new IntField(groupCountTag.intValue(), groupCount);
                    countField.toString(buffer);
                    buffer.append('\001');
                    for (int i = 0; i < groupCount; i++) {
                        final FieldMap groupFields = groups.get(i);
                        groupFields.calculateString(buffer, preFields, postFields);
                    }
                }
            }
        }

        if (postFields != null) {
            for (final int postField : postFields) {
                final Field<?> field = getField(postField, null);
                field.toString(buffer);
                buffer.append('\001');
            }
        }
    }

    private boolean isOrderedField(final int field, final int[] afieldOrder) {
        if (afieldOrder != null) {
            for (final int element : afieldOrder) {
                if (field == element) {
                    return true;
                }
            }
        }
        return false;
    }

    int calculateLength() {
        int result = 0;
        int length = 0;
        for (final Field<?> field : fields.values()) {
            if (field.getField() == BeginString.FIELD || field.getField() == BodyLength.FIELD
                    || field.getField() == CheckSum.FIELD || isGroupField(field.getField())) {
                continue;
            }
            length = field.getLength();
            result += length;
        }

        final Iterator<Map.Entry<Integer, List<Group>>> iterator = groups.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<Integer, List<Group>> entry = iterator.next();
            final List<Group> groupList = entry.getValue();
            if (!groupList.isEmpty()) {
                final IntField groupField = new IntField((entry.getKey()).intValue());
                groupField.setValue(groupList.size());
                length = groupField.getLength();
                result += length;
                for (int i = 0; i < groupList.size(); i++) {
                    final Group group = groupList.get(i);
                    length = group.calculateLength();
                    result += length;
                }
            }
        }
        return result;

    }

    int calculateTotal() {

        int result = 0;
        for (final Field<?> field2 : fields.values()) {
            final Field<?> field = field2;
            if (field.getField() == CheckSum.FIELD || isGroupField(field.getField())) {
                continue;
            }
            result += field.getTotal();
        }

        final Iterator<Map.Entry<Integer, List<Group>>> iterator = groups.entrySet().iterator();
        while (iterator.hasNext()) {
            final Map.Entry<Integer, List<Group>> entry = iterator.next();
            final List<Group> groupList = entry.getValue();
            if (!groupList.isEmpty()) {
                final IntField groupField = new IntField((entry.getKey()).intValue());
                groupField.setValue(groupList.size());
                result += groupField.getTotal();
                for (int i = 0; i < groupList.size(); i++) {
                    final Group group = groupList.get(i);
                    result += group.calculateTotal();
                }
            }
        }

        return result;
    }

    /**
     * Returns the number of groups associated with the specified count tag.
     * @param tag the count tag number
     * @return the number of times the group repeats
     */
    public int getGroupCount(final int tag) {
        return getGroups(tag).size();
    }

    public Iterator<Integer> groupKeyIterator() {
        return groups.keySet().iterator();
    }

    Map<Integer, List<Group>> getGroups() {
        return groups;
    }

    public void addGroup(final Group group) {
        final int countTag = group.getFieldTag();
        final List<Group> currentGroups = getGroups(countTag);
        currentGroups.add(new Group(group));
        setGroupCount(countTag, currentGroups.size());
    }

    public void addGroupRef(final Group group) {
        int countTag = group.getFieldTag();
        List<Group> currentGroups = getGroups(countTag);
        currentGroups.add(group);
        setGroupCount(countTag, currentGroups.size());
    }

    protected void setGroupCount(final int countTag, final int groupSize) {
        try {
            StringField count;
            if (groupSize == 1) {
                count = new StringField(countTag, "1");
                setField(countTag, count);
            } else {
                count = getField(countTag);
            }
            count.setValue(Integer.toString(groupSize));
        } catch (final FieldNotFound e) {
            // Shouldn't happen
            throw new RuntimeError(e);
        }
    }

    public List<Group> getGroups(final int field) {
        List<Group> groupList = groups.get(field);
        if (groupList == null) {
            groupList = new ArrayList<Group>();
            groups.put(field, groupList);
        }
        return groupList;
    }

    public Group getGroup(final int num, final Group group) throws FieldNotFound {
        final List<Group> groupList = getGroups(group.getFieldTag());
        if (num > groupList.size()) {
            throw new FieldNotFound(group.getFieldTag() + ", index=" + num);
        }
        final Group grp = groupList.get(num - 1);
        group.setFields(grp);
        group.setGroups(grp);
        return group;
    }

    public Group getGroup(final int num, final int groupTag) throws FieldNotFound {
        List<Group> groupList = getGroups(groupTag);
        if (num > groupList.size()) {
            throw new FieldNotFound(groupTag + ", index=" + num);
        }
        final Group grp = groupList.get(num - 1);
        return grp;
    }

    public void replaceGroup(final int num, final Group group) {
        final int offset = num - 1;
        final List<Group> groupList = getGroups(group.getFieldTag());
        if (offset < 0 || offset >= groupList.size()) {
            return;
        }
        groupList.set(offset, new Group(group));
    }

    public void removeGroup(final int field) {
        getGroups(field).clear();
        removeField(field);
    }

    public void removeGroup(final int num, final int field) {
        final List<Group> groupList = getGroups(field);
        if (num <= groupList.size()) {
            groupList.remove(num - 1);
        }
        if (groupList.size() > 0) {
            setGroupCount(field, groupList.size());
        }
    }

    public void removeGroup(final int num, final Group group) {
        removeGroup(num, group.getFieldTag());
    }

    public void removeGroup(final Group group) {
        removeGroup(group.getFieldTag());
    }

    public boolean hasGroup(final int field) {
        return groups.containsKey(field);
    }

    public boolean hasGroup(final int num, final int field) {
        return hasGroup(field) && num <= getGroups(field).size();
    }

    public boolean hasGroup(final int num, final Group group) {
        return hasGroup(num, group.getFieldTag());
    }

    public boolean hasGroup(final Group group) {
        return hasGroup(group.getFieldTag());
    }

}
