package com.afornalik.model;

public class UserSession {

    private final User loggedUser;

    public UserSession(User loggedUser) {
        this.loggedUser = loggedUser;
    }

    public User getLoggedUser() {
        return loggedUser;
    }
}
