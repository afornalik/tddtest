package com.afornalik.model;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private final String firstName;
    private final String lastName;
    private final LocalDate createDate;
    private boolean isBlocked = false;

    public User(String firstName, String lastName, LocalDate createDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createDate = createDate;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }


}
