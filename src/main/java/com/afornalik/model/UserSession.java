package com.afornalik.model;

public class UserSession {

    private final User logedUser;

    public UserSession(User logedUser) {
        this.logedUser = logedUser;
    }

    public User getLogedUser() {
        return logedUser;
    }
}
