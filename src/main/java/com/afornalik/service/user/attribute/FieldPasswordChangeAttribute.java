package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.FieldValue;

public class FieldPasswordChangeAttribute extends EditField {

    public FieldPasswordChangeAttribute(User user, FieldValue<String> fieldValue) {
        super(user, fieldValue);
    }

    private User changeUserAttribute() {
        super.getUser().setPassword(super.getFieldValue().getT().toString());
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
