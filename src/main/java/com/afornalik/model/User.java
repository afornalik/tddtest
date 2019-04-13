package com.afornalik.model;

import com.afornalik.service.user.attribute.value.UserStatus;

import java.time.LocalDate;

public class User {

    private String firstName;
    private String lastName;
    private String email;
    private final LocalDate createDate;
    private UserStatus isBlocked = UserStatus.UNBLOCKED;
    private String password;

    public User(String firstName, String lastName, String password,String email, LocalDate createDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.createDate = createDate;
    }


    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserStatus isBlocked() {
        return isBlocked;
    }

    public void setBlocked(UserStatus blocked) {
        isBlocked = blocked;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
                ", email='" + email + '\'' +
                ", createDate=" + createDate +
                ", isBlocked=" + isBlocked +
                ", password='" + password + '\'' +
                '}';
    }
}
