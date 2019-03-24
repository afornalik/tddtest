package com.afornalik.service.userattribute;

import com.afornalik.model.User;
import com.afornalik.service.UserAttribute;

public class UserFirstNameChangeAttribute implements UserAttribute {

    private User user;
    private String newAttribute;

    public UserFirstNameChangeAttribute(User user,String newAttribute) {
        this.user = user;
        this.newAttribute = newAttribute;
    }

    @Override
    public User changeUserAttribute() {
        user.setFirstName(newAttribute);
        return user;
    }


}
