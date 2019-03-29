package com.afornalik.service.user;

import com.afornalik.model.User;
import com.afornalik.service.user.UserAttribute;

public class EditUser {

    public User edit( UserAttribute userAttribute) {
        return userAttribute.changeUserAttribute();
    }
}
