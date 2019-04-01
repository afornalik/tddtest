package com.afornalik.controller;

import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.security.PasswordGenerator;
import com.afornalik.service.security.PasswordGeneratorApacheCommonLangImpl;
import com.afornalik.service.user.UserAttribute;
import com.afornalik.service.user.UserRepository;
import com.afornalik.service.user.EditUser;
import com.afornalik.service.user.UserValidator;
import com.afornalik.service.user.attribute.UserPasswordChangeAttribute;
import com.afornalik.service.user.attribute.UserStatus;
import com.afornalik.service.user.exception.IncorrectUserDataException;
import com.afornalik.service.user.exception.UserAlreadyExistException;
import com.afornalik.service.user.exception.UserUnexistException;

public class UserController {

    private final UserValidator userValidator;
    private final UserRepository userRepository;
    private final EditUser editUser;
    private final MailService mailService;
    private final UserSession userSession;
    private final PasswordGenerator passwordGenerator = new PasswordGeneratorApacheCommonLangImpl();

    public UserController(UserRepository userRepository, EditUser editUser, MailService mailService, UserSession userSession) {
        this.userRepository = userRepository;
        this.userValidator = new UserValidator(userRepository);
        this.editUser = editUser;
        this.mailService = mailService;
        this.userSession = userSession;
    }

    public void create(User user) throws IncorrectUserDataException, UserAlreadyExistException {
        if (userValidator.checkUserData(user)) {
            userRepository.save(user);
        }
    }


    public User select(User user) {
        if (userRepository.ifUserExist(user)) {
            return userRepository.select(user);
        }
        return null;
    }

    public void blockUser(User user) {
        if (userRepository.ifUserExist(user))
            if (user.isBlocked() == UserStatus.UNBLOCKED) {
                user.setBlocked(UserStatus.BLOCKED);
                userRepository.save(user);
            }
    }


    public void edit(User user, UserAttribute userAttribute) throws UserUnexistException {
        if (userRepository.ifUserExist(user)) {
            userRepository.save(editUser.edit(userAttribute));
        } else {
            throw new UserUnexistException();
        }
    }

    public void delete(User user) {
        if (userRepository.ifUserExist(user)) {
            userRepository.delete(user);
        }
    }

    public void resetPassword(User user) {
        if (userRepository.ifUserExist(user)) {
            String newPassword = passwordGenerator.generatePassword();
            editUser.edit(new UserPasswordChangeAttribute(user, newPassword));
            userRepository.save(user);
            mailService.sendNewPassword(user);
        }
    }

    public UserSession getUserSession() {
        return userSession;
    }
}
