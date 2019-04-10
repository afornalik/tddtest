package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.FieldValue;

public abstract class EditField{

    private EditField nextAttributeClass;
    private final User user;
    private final FieldValue fieldValue;

    public EditField(User user, FieldValue fieldValue) {
        this.user = user;
        this.fieldValue = fieldValue;
    }

    public User getUser() {
        return user;
    }

    public FieldValue getFieldValue() {
        return fieldValue;
    }

    public void setNextAttributeClass(EditField nextAttributeClass) {
        this.nextAttributeClass = nextAttributeClass;
    }

    public EditField getNextAttributeClass() {
        return nextAttributeClass;
    }

    public abstract User changeAttribute(EditField editField);
}
