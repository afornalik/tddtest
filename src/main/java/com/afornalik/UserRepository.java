package com.afornalik;

public interface UserRepository {

    void save(User user);

    boolean ifUserExist(User user);
}
