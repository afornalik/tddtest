package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.UserTestGenericAttribute;

public class FieldLastNameChangeAttribute extends EditField {


    public FieldLastNameChangeAttribute(User user, UserTestGenericAttribute<String> userTestGenericAttribute) {
        super(user,userTestGenericAttribute);
    }

    private User changeUserAttribute() {
        super.getUser().setLastName(super.getT().getT().toString());
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
