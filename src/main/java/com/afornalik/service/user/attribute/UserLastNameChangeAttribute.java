package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.UserAttribute;

public class UserLastNameChangeAttribute implements UserAttribute {

    private User user;
    private String newAttribute;

    public UserLastNameChangeAttribute(User user,String newAttribute) {
        this.user = user;
        this.newAttribute = newAttribute;
    }

    @Override
    public User changeUserAttribute() {
        user.setLastName(newAttribute);
        return user;
    }


}