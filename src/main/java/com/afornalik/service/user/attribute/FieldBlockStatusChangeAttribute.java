package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.UserStatus;
import com.afornalik.service.user.attribute.value.UserTestGenericAttribute;

public class FieldBlockStatusChangeAttribute extends EditField {



    public FieldBlockStatusChangeAttribute(User user, UserTestGenericAttribute<UserStatus> userTestGenericAttribute) {
        super(user,userTestGenericAttribute);
    }


    private User changeUserAttribute() {
        if (super.getT().getT().equals(UserStatus.BLOCKED)) {
            super.getUser().setBlocked(UserStatus.UNBLOCKED);
        } else if (super.getT().getT().equals(UserStatus.UNBLOCKED)) {
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
