package com.afornalik.controller;

import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.security.PasswordGenerator;
import com.afornalik.service.security.PasswordGeneratorApacheCommonLangImpl;
import com.afornalik.service.user.UserAttribute;
import com.afornalik.service.user.UserRepository;
import com.afornalik.service.user.EditUser;
import com.afornalik.service.user.attribute.UserPasswordChangeAttribute;
import com.afornalik.service.user.attribute.UserStatus;
import com.afornalik.view.LoginUserView;

public class UserController {

    private final UserRepository userRepository;
    private final EditUser editUser;
    private final MailService mailService;
    private UserSession userSession;
    private final LoginUserView loginUserView;
    private final PasswordGenerator passwordGenerator = new PasswordGeneratorApacheCommonLangImpl();

    public UserController(UserRepository userRepository, EditUser editUser, MailService mailService, UserSession userSession, LoginUserView loginUserView) {
        this.userRepository = userRepository;
        this.editUser = editUser;
        this.mailService = mailService;
        this.userSession = userSession;
        this.loginUserView = loginUserView;
    }

    public User select() {
        return userSession.getLoggedUser();
    }

    public void edit(UserAttribute userAttribute) {
        userRepository.save(editUser.edit(userAttribute));
    }

    public void delete() {
        userRepository.delete(userSession.getLoggedUser());
        userSession = new UserSession(null);
    }

    public void resetPassword() {
            String newPassword = passwordGenerator.generatePassword();
            editUser.edit(new UserPasswordChangeAttribute(userSession.getLoggedUser(), newPassword));
            userRepository.save(userSession.getLoggedUser());
            mailService.sendNewPassword(userSession.getLoggedUser());

    }

    public UserSession getUserSession() {
        return userSession;
    }
}
