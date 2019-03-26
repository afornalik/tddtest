package com.afornalik;

import com.afornalik.model.User;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.security.PasswordGenerator;
import com.afornalik.service.security.PasswordGeneratorApacheCommonLangImpl;
import com.afornalik.service.user.UserAttribute;
import com.afornalik.service.user.UserRepository;
import com.afornalik.service.user.attribute.UserPasswordChangeAttribute;
import com.afornalik.userexception.IncorrectUserDataException;
import com.afornalik.userexception.UserAlreadyExistException;
import com.afornalik.userexception.UserUnexistException;

public class UserController {

    private UserValidator userValidator;
    private UserRepository userRepository;
    private EditUser editUser;
    private MailService mailService;
    private UserAttribute userAttribute;
    private PasswordGenerator passwordGenerator = new PasswordGeneratorApacheCommonLangImpl();


    public UserController(UserRepository userRepository, EditUser editUser, MailService mailService) {
        this.userRepository = userRepository;
        this.userValidator = new UserValidator(userRepository);
        this.editUser = editUser;
        this.mailService = mailService;
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
            if (!user.isBlocked()) {
                user.setBlocked(true);
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
}
