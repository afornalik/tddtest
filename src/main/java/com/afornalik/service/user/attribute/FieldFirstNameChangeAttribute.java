package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.UserTestGenericAttribute;

public class FieldFirstNameChangeAttribute extends EditField {




    public FieldFirstNameChangeAttribute(User user, UserTestGenericAttribute<String> userTestGenericAttribute) {
        super(user,userTestGenericAttribute);

    }

    private User changeUserAttribute() {
        super.getUser().setFirstName(super.getUserTestGenericAttribute().getT().toString());
        return super.getUser();
    }


    @Override
    public User changeAttribute(EditField editField) {
        if(editField instanceof FieldFirstNameChangeAttribute) {
            return changeUserAttribute();
        }else {
            return getNextAttributeClass().changeAttribute(editField);
        }
    }
}
