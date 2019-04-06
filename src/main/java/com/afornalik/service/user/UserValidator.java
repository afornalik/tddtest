package com.afornalik.service.user;

import com.afornalik.model.UserSession;
import com.afornalik.service.user.exception.IncorrectUserDataException;
import com.afornalik.service.user.exception.UserAlreadyExistException;

public class UserValidator {

    private final UserSession userSession;
    private final UserRepository userRepository;

    public UserValidator(UserSession userSession,UserRepository userRepository) {
        this.userSession = userSession;
        this.userRepository = userRepository;
    }

    public boolean checkUserData() throws IncorrectUserDataException, UserAlreadyExistException {
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
        if(userSession.getLoggedUser().getCreateDate().getYear() <= 2000){
            return false;
        } else {
            return true;
        }
    }

    private boolean checkLastName() {
        return userSession.getLoggedUser().getLastName() != null ;
    }

    private boolean checkFirstName() {
        return userSession.getLoggedUser().getFirstName() != null  ;
    }

    private boolean checkIfUserExist(){
        return userRepository.ifUserExist(userSession.getLoggedUser());
    }
}
