package com.afornalik.service.user;

import com.afornalik.model.User;

public interface UserRepository {

    void save(User user);

    boolean ifUserExist(User user);

    User select(User user);

    void delete(User user);
}
