package com.afornalik.service.userattribute;

public enum UserStatus {

    UNBLOCKED(false){
        public boolean isBlocked() {
            return false;
        }
    },
    BLOCKED(true){
        public boolean isBlocked() {
            return true;
        }
    };

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
