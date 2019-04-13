package com.afornalik.controller;

import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.repository.UserRepository;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.security.PasswordGenerator;
import com.afornalik.service.security.PasswordGeneratorApacheCommonLangImpl;
import com.afornalik.service.user.attribute.*;
import com.afornalik.service.user.attribute.value.FieldValue;
import com.afornalik.view.LoginUserView;

class UserOperationsFacade {

    private final UserRepository userRepository;
    private LoginUserView loginUserView;
    private final MailService mailService;
    private UserSession userSession;
    private final PasswordGenerator passwordGenerator;

    UserOperationsFacade(UserRepository userRepository, LoginUserView loginUserView, MailService mailService,UserSession userSession, PasswordGenerator passwordGenerator) {
        this.userRepository = userRepository;
        this.loginUserView = loginUserView;
        this.mailService = mailService;
        this.userSession = userSession;
        this.passwordGenerator = passwordGenerator;
    }

    User selectLoggedUser() {
        return userSession.getLoggedUser();
    }

    void editUser(EditField editField){
        EditField fieldBlockStatusChangeAttribute = new FieldUserStatusValue(userSession.getLoggedUser(),editField.getCurrentFieldValue());
        EditField fieldFirstNameChangeAttribute = new FieldFirstNameValue(userSession.getLoggedUser(), editField.getCurrentFieldValue());
        EditField fieldLastNameChangeAttribute = new FieldLastNameValue(userSession.getLoggedUser(), editField.getCurrentFieldValue());
        EditField fieldPasswordChangeAttribute = new FieldPasswordValue(userSession.getLoggedUser(), editField.getCurrentFieldValue());

        fieldPasswordChangeAttribute.setNextAttributeClass(fieldFirstNameChangeAttribute);
        fieldFirstNameChangeAttribute.setNextAttributeClass(fieldLastNameChangeAttribute);
        fieldLastNameChangeAttribute.setNextAttributeClass(fieldBlockStatusChangeAttribute);

        userRepository.save(fieldPasswordChangeAttribute.changeAttribute(editField));
    }

    void deleteLoggedUser() {
        userRepository.delete(userSession.getLoggedUser());
        userSession = new UserSession(null);
    }

    void resetPassword() {
        String newPassword = passwordGenerator.generatePassword();
        editUser(new FieldPasswordValue(userSession.getLoggedUser(), new FieldValue<String>(newPassword)));
        mailService.sendNewPassword(userSession.getLoggedUser());

    }


}
