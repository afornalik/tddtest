package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.UserStatus;
import com.afornalik.service.user.attribute.value.FieldValue;

public class FieldBlockStatusChangeAttribute extends EditField {



    public FieldBlockStatusChangeAttribute(User user, FieldValue<UserStatus> fieldValue) {
        super(user, fieldValue);
    }


    private User changeUserAttribute() {
        if (super.getFieldValue().getT().equals(UserStatus.BLOCKED)) {
            super.getUser().setBlocked(UserStatus.UNBLOCKED);
        } else if (super.getFieldValue().getT().equals(UserStatus.UNBLOCKED)) {
            super.getUser().setBlocked(UserStatus.BLOCKED);
        }
        return super.getUser();
    }

    @Override
    public User changeAttribute(EditField editField) {
        if(editField instanceof FieldBlockStatusChangeAttribute){
            return changeUserAttribute();
        }else {
            return getNextAttributeClass().changeAttribute(editField);
        }
    }


}
