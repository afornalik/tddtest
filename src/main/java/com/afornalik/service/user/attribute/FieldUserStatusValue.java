package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.UserStatus;
import com.afornalik.service.user.attribute.value.FieldValue;

public class FieldUserStatusValue extends EditField {

    private final User user;
    private final FieldValue<UserStatus> fieldValue;

    public FieldUserStatusValue(User user, FieldValue<UserStatus> fieldValue) {
        this.user = user;
        this.fieldValue = fieldValue;
    }

    @Override
    public FieldValue getCurrentFieldValue() {
        return fieldValue;
    }

    @Override
    public User changeAttribute(EditField editField) {
        if(editField instanceof FieldUserStatusValue){
            return changeUserAttribute();
        }
            return getNextAttributeClass().changeAttribute(editField);
    }

    private User changeUserAttribute() {
        if (fieldValue.getTypeValue().equals(UserStatus.BLOCKED)) {
            user.setBlocked(UserStatus.UNBLOCKED);
        } else if (fieldValue.getTypeValue().equals(UserStatus.UNBLOCKED)) {
            user.setBlocked(UserStatus.BLOCKED);
        }
        return user;
    }
}
