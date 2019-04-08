package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.UserTestGenericAttribute;

public class FieldPasswordChangeAttribute extends EditField {

    public FieldPasswordChangeAttribute(User user, UserTestGenericAttribute<String> userTestGenericAttribute) {
        super(user,userTestGenericAttribute);
    }

    private User changeUserAttribute() {
        super.getUser().setPassword(super.getT().getT().toString());
        return super.getUser();
    }

    @Override
    public User changeAttribute(EditField editField) {
        if(editField instanceof FieldPasswordChangeAttribute){
            return changeUserAttribute();
        }else {
            return getNextAttributeClass().changeAttribute(editField);
        }
    }
}
