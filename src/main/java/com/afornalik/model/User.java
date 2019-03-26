package com.afornalik.model;

import java.time.LocalDate;

public class User {

    private String firstName;
    private String lastName;
    private final LocalDate createDate;
    private boolean isBlocked;
    private String password;

    public User(String firstName, String lastName, String password, LocalDate createDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.createDate = createDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", createDate=" + createDate +
                ", isBlocked=" + isBlocked +
                '}';
    }

}
