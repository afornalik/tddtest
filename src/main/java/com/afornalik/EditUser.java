package com.afornalik;

import com.afornalik.model.User;

import java.time.LocalDate;

public class EditUser {


    private User user;

    public EditUser(User user) {
        this.user = user;
    }

    public User edit(User user) {
        return user;
    }
}
