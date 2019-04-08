package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.attribute.value.UserTestGenericAttribute;

public abstract class EditField{

    private EditField nextAttributeClass;
    private final User user;
    private final UserTestGenericAttribute userTestGenericAttribute;

    public EditField(User user, UserTestGenericAttribute userTestGenericAttribute) {
        this.user = user;
        this.userTestGenericAttribute = userTestGenericAttribute;
    }

    public User getUser() {
        return user;
    }

    public UserTestGenericAttribute getUserTestGenericAttribute() {
        return userTestGenericAttribute;
    }

    public void setNextAttributeClass(EditField nextAttributeClass) {
        this.nextAttributeClass = nextAttributeClass;
    }

    public EditField getNextAttributeClass() {
        return nextAttributeClass;
    }

    public abstract User changeAttribute(EditField editField);
}
