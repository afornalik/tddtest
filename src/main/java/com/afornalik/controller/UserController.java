package com.afornalik.controller;

import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.security.PasswordGenerator;
import com.afornalik.service.security.PasswordGeneratorApacheCommonLangImpl;
import com.afornalik.service.user.UserRepository;
import com.afornalik.service.user.attribute.*;
import com.afornalik.service.user.attribute.value.FieldValue;
import com.afornalik.view.LoginUserView;

public class UserController {

    private final UserRepository userRepository;
    private final MailService mailService;
    private UserSession userSession;
    private final LoginUserView loginUserView;
    private final PasswordGenerator passwordGenerator = new PasswordGeneratorApacheCommonLangImpl();

    public UserController(UserRepository userRepository, MailService mailService, UserSession userSession, LoginUserView loginUserView) {
        this.userRepository = userRepository;
        this.mailService = mailService;
        this.userSession = userSession;
        this.loginUserView = loginUserView;
    }

    public User select() {
        return userSession.getLoggedUser();
    }

    public void edit(EditField editField) {

        EditField fieldBlockStatusChangeAttribute = new FieldBlockStatusChangeAttribute(userSession.getLoggedUser(),editField.getFieldValue());
        EditField fieldFirstNameChangeAttribute = new FieldFirstNameChangeAttribute(userSession.getLoggedUser(), editField.getFieldValue());
        EditField fieldLastNameChangeAttribute = new FieldLastNameChangeAttribute(userSession.getLoggedUser(), editField.getFieldValue());
        EditField fieldPasswordChangeAttribute = new FieldPasswordChangeAttribute(userSession.getLoggedUser(), editField.getFieldValue());

        fieldPasswordChangeAttribute.setNextAttributeClass(fieldFirstNameChangeAttribute);
        fieldFirstNameChangeAttribute.setNextAttributeClass(fieldLastNameChangeAttribute);
        fieldLastNameChangeAttribute.setNextAttributeClass(fieldBlockStatusChangeAttribute);

        userRepository.save(fieldPasswordChangeAttribute.changeAttribute(editField));
    }

    public void delete() {
        userRepository.delete(userSession.getLoggedUser());
        userSession = new UserSession(null);
    }

    public void resetPassword() {
            String newPassword = passwordGenerator.generatePassword();
            edit(new FieldPasswordChangeAttribute(userSession.getLoggedUser(), new FieldValue<String>(newPassword)));
            mailService.sendNewPassword(userSession.getLoggedUser());

    }

    public UserSession getUserSession() {
        return userSession;
    }
}
