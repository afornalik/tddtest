package com.afornalik.service.user.attribute.value;

public enum UserStatus  {

    UNBLOCKED(false) ,
    BLOCKED(true) ;

    private final boolean status;

    UserStatus(boolean status) {
        this.status = status;

    }

    public boolean isStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "UserStatus{" +
                "status=" + status +
                '}';
    }
}
