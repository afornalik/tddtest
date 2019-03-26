package com.afornalik;

import com.afornalik.model.User;
import com.afornalik.service.user.UserRepository;
import com.afornalik.userexception.IncorrectUserDataException;
import com.afornalik.userexception.UserAlreadyExistException;

public class UserValidator {

    private User user;
    private UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean checkUserData(User user) throws IncorrectUserDataException, UserAlreadyExistException {
        this.user = user;
        if(checkFirstName() && checkLastName() && checkCreateData()) {
            if(!checkIfUserExist()) {
                return true;
            }else {
                throw new UserAlreadyExistException();
            }
        }else {
            throw new IncorrectUserDataException();
        }
    }



    private boolean checkCreateData() {
        if(user.getCreateDate().getYear() <= 2000){
            return false;
        } else {
            return true;
        }
    }

    private boolean checkLastName() {
        return user.getLastName() != null ;
    }

    private boolean checkFirstName() {
        return user.getFirstName() != null  ;
    }

    private boolean checkIfUserExist(){
        return userRepository.ifUserExist(user);
    }
}
