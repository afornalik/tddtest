package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.FieldValue;

public class FieldLastNameValue extends EditField {

    private final User user;
    private final FieldValue<String> fieldValue;

    public FieldLastNameValue(User user, FieldValue<String> fieldValue) {
        this.user = user;
        this.fieldValue = fieldValue;
    }

    @Override
    public FieldValue getCurrentFieldValue() {
        return fieldValue;
    }

    @Override
    public User changeAttribute(EditField editField) {
        if(editField instanceof FieldLastNameValue){
            return changeUserAttribute();
        }
            return getNextAttributeClass().changeAttribute(editField);
    }

    private User changeUserAttribute() {
        user.setLastName(fieldValue.getTypeValue());
        return user;
    }
}
