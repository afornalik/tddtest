package com.afornalik;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private final String firstName;
    private final String lastName;
    private final LocalDate createDate;


    public User(String firstName, String lastName, LocalDate createDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createDate = createDate;
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
