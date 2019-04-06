package com.afornalik.controller;

import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.user.EditUser;
import com.afornalik.service.user.UserRepository;
import com.afornalik.service.user.exception.UserUnexistException;
import com.afornalik.view.LoginUserView;

public class MainController {

    private final UserRepository userRepository;
    private final LoginUserView loginUserView;
    private final MailService mailService;
    private UserController userController;
    private UserSession userSession;

    public MainController(UserRepository userRepository, LoginUserView loginUserView, MailService mailService) {
        this.userRepository = userRepository;
        this.loginUserView = loginUserView;
        this.mailService = mailService;
    }

    public void logIn() throws UserUnexistException {
        String tempLogin = loginUserView.getUserLogin();
        User user = userRepository.selectByFirstName(tempLogin);
        if (user != null) {
            if (user.getPassword().equals(loginUserView.getPassword())) {
                this.userSession = new UserSession(user);
            }
        } else {
            throw new UserUnexistException();
        }
    }

    public void enterUserSettings() {
        if (userSession != null) {
            createUserController();
        }
    }

    private void createUserController() {
        userController = new UserController(userRepository, new EditUser(), mailService, userSession,loginUserView);
    }


    public UserController getUserController() {
        return userController;
    }

    public UserSession getUserSession() {
        return userSession;
    }
}
