package com.afornalik.service.userattribute;

import com.afornalik.model.User;
import com.afornalik.service.UserAttribute;

public class UserBlockStatusChangeAttribute  implements UserAttribute {

    private User user;
    private UserStatus userStatus;

    public UserBlockStatusChangeAttribute(User user,UserStatus userStatus) {
        this.user = user;
        this.userStatus = userStatus;
    }

    @Override
    public void changeUserAttribute() {
        if(userStatus.equals(UserStatus.BLOCKED)){
            user.setBlocked(true);
        }else if(userStatus.equals(UserStatus.UNBLOCKED)) {
            user.setBlocked(false);
        }
    }

    @Override
    public User returnUser() {
        changeUserAttribute();
        return user;
    }
}
