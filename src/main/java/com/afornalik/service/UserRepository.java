package com.afornalik.service;

import com.afornalik.model.User;

public interface UserRepository {

    void save(User user);

    boolean ifUserExist(User user);

    User select(User user);
}
