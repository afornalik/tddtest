package com.afornalik.controller;

import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.repository.UserRepository;
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
        User user = checkIfGivenUserExist();
        if (userExist(user)) {
            if (checkIfUserPasswordIsCorrect(user)) {
                createNewUserSession(user);
            }
        } else {
            throw new UserUnexistException();
        }
    }

    private User checkIfGivenUserExist() {
        String tempLogin = loginUserView.getUserLogin();
        return userRepository.selectByFirstName(tempLogin);
    }

    private boolean userExist(User user) {
        return user != null;
    }

    private boolean checkIfUserPasswordIsCorrect(User user) {
        return user.getPassword().equals(loginUserView.getPassword());
    }

    private void createNewUserSession(User user) {
        this.userSession = new UserSession(user);
    }

    public void enterUserSettings() {
        if (isUserSessionCreated()) {
            createUserController();
        }
    }

    private boolean isUserSessionCreated() {
        return userSession != null;
    }

    private void createUserController() {
        userController = new UserController(userRepository, mailService, userSession);
    }


    public UserController getUserController() {
        return userController;
    }

    public UserSession getUserSession() {
        return userSession;
    }
}
