package com.afornalik;

import com.afornalik.model.User;
import com.afornalik.service.UserAttribute;
import com.afornalik.service.UserRepository;
import com.afornalik.userexception.IncorrectUserDataException;
import com.afornalik.userexception.UserAlreadyExistException;
import com.afornalik.userexception.UserUnexistException;

public class UserController {

    private User user;
    private UserValidator userValidator;
    private UserRepository userRepository;
    private EditUser editUser;
    private UserAttribute userAttribute;


    public UserController(User user, UserRepository userRepository, EditUser editUser) {
        this.user = user;
        this.userRepository = userRepository;
        this.userValidator = new UserValidator(userRepository);
        this.editUser = editUser;
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


    public void edit(UserAttribute userAttribute) throws UserUnexistException {
        if (userRepository.ifUserExist(user)) {
            userRepository.save(editUser.edit(userAttribute));
        } else {
            throw new UserUnexistException();
        }
    }
}
