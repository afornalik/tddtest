package com.afornalik.controller;

import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.user.UserRepository;
import com.afornalik.view.LoginUser;

public class MainController {

    public final UserRepository userRepository;
    public final LoginUser loginUser;

    public MainController(UserRepository userRepository,LoginUser loginUser) {
        this.userRepository = userRepository;
        this.loginUser = loginUser;
    }

    public UserSession logIn() {
        String tempLogin = loginUser.getUserLogin();
        User user = userRepository.selectByFirstName(tempLogin);
        if(user.getPassword().equals(loginUser.getPassword())) {
            return new UserSession(user);
        }
            return null;
    }
}
