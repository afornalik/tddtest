package com.afornalik.service.user.attribute;

import com.afornalik.model.User;
import com.afornalik.service.user.UserAttribute;

public class UserBlockStatusChangeAttribute implements UserAttribute {

    private User user;
    private UserStatus userStatus;

    public UserBlockStatusChangeAttribute(User user, UserStatus userStatus) {
        this.user = user;
        this.userStatus = userStatus;
    }

    @Override
    public User changeUserAttribute() {
        if (userStatus.equals(UserStatus.BLOCKED)) {
            user.setBlocked(UserStatus.UNBLOCKED);
        } else if (userStatus.equals(UserStatus.UNBLOCKED)) {
            user.setBlocked(UserStatus.BLOCKED);
        }
        return user;
    }

}