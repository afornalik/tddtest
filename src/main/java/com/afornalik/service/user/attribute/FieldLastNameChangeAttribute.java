package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.FieldValue;

public class FieldLastNameChangeAttribute extends EditField {


    public FieldLastNameChangeAttribute(User user, FieldValue<String> fieldValue) {
        super(user, fieldValue);
    }

    private User changeUserAttribute() {
        super.getUser().setLastName(super.getFieldValue().getT().toString());
        return super.getUser();
    }


    @Override
    public User changeAttribute(EditField editField) {
        if(editField instanceof FieldLastNameChangeAttribute){
            return changeUserAttribute();
        }else {
            return getNextAttributeClass().changeAttribute(editField);
        }
    }
}
