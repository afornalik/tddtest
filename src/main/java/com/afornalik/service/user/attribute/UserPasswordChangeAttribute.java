package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.UserAttribute;

public class UserPasswordChangeAttribute implements UserAttribute {

    private User user;
    private String newAttribute;

    public UserPasswordChangeAttribute(User user, String newAttribute) {
        this.user = user;
        this.newAttribute = newAttribute;
    }

    @Override
    public User changeUserAttribute() {
        user.setPassword(newAttribute);
        return user;
    }
}
