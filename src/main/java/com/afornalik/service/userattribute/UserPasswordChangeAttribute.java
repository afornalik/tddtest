package com.afornalik.service.userattribute;

import com.afornalik.model.User;
import com.afornalik.service.UserAttribute;

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
