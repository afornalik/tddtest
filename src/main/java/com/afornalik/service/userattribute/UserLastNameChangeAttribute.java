package com.afornalik.service.userattribute;

import com.afornalik.model.User;
import com.afornalik.service.UserAttribute;

public class UserLastNameChangeAttribute implements UserAttribute {

    private User user;
    private String newAttribute;

    public UserLastNameChangeAttribute(User user,String newAttribute) {
        this.user = user;
        this.newAttribute = newAttribute;
    }

    @Override
    public void changeUserAttribute() {
        user.setLastName(newAttribute);
    }

    @Override
    public User returnUser() {
        changeUserAttribute();
        return user;
    }
}
