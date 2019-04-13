package com.afornalik.controller;

import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.security.PasswordGenerator;
import com.afornalik.service.security.PasswordGeneratorApacheCommonLangImpl;
import com.afornalik.repository.UserRepository;
import com.afornalik.service.user.attribute.*;

public class UserController {

    private final UserRepository userRepository;
    private final MailService mailService;
    private UserSession userSession;
    private final PasswordGenerator passwordGenerator = new PasswordGeneratorApacheCommonLangImpl();
    private final UserOperationsFacade userOperationsFacade;

    public UserController(UserRepository userRepository, MailService mailService, UserSession userSession) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userSession = userSession;
        this.userOperationsFacade = new UserOperationsFacade(userRepository, null,mailService,userSession,passwordGenerator);
    }


    public User select() {
       return userOperationsFacade.selectLoggedUser();
    }

    public void edit(EditField editField) {
        userOperationsFacade.editUser(editField);
    }

    public void delete() {
        userOperationsFacade.deleteLoggedUser();
    }

    public void resetPassword() {
       userOperationsFacade.resetPassword();
    }

    public UserSession getUserSession() {
        return userSession;
    }
}
