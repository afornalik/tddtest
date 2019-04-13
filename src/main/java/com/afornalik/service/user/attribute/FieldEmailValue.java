package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.FieldValue;

public class FieldEmailValue extends EditField  {

    private final User user;
    private final FieldValue<String> fieldValue;

    public FieldEmailValue(User user, FieldValue<String> fieldValue) {
        this.user = user;
        this.fieldValue = fieldValue;
    }

    @Override
    public User changeAttribute(EditField editField) {
        if (editField instanceof FieldEmailValue) {
            return changeUserAttribute();
        }
        return getNextAttributeClass().changeAttribute(editField);
    }

    @Override
    public FieldValue getCurrentFieldValue() {
        return fieldValue;
    }

    private User changeUserAttribute() {
        user.setEmail(fieldValue.getTypeValue());
        return user;
    }
}
