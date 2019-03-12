package com.afornalik;

public class UserValidator {

    private User user;

    public boolean checkUserData(User user) throws IncorrectUserDataException{
        this.user = user;
        if(checkFirstName() && checkLastName() && checkCreateData()) {
            return true;
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

}
