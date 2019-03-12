package com.afornalik;

public class UserController {

    private UserValidator userValidator = new UserValidator();
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void create(User user) throws IncorrectUserDataException{
        if(userValidator.checkUserData(user)) {
            userRepository.save(user);
        }
    }
}
