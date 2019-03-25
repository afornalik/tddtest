package com.afornalik.service.userattribute;

public enum UserStatus {

    UNBLOCKED(false) ,
    BLOCKED(true) ;

    private final boolean status;

    UserStatus(boolean status) {
        this.status = status;

    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "status=" + status +
                '}';
    }
}
