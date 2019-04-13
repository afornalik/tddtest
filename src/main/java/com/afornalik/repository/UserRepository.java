package com.afornalik.repository;

import com.afornalik.model.User;

public interface UserRepository {

    void save(User user);

    boolean ifUserExist(User user);

    void delete(User user);

    User selectByFirstName(String firstName);
}
