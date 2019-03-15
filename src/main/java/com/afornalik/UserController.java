package com.afornalik;

public class UserController {

    private User user;
    private UserValidator userValidator;
    private UserRepository userRepository;


    public UserController(User user, UserRepository userRepository) {
        this.user = user;
        this.userRepository = userRepository;
        this.userValidator = new UserValidator(userRepository);

    }

    public void create(User user) throws IncorrectUserDataException, UserAlreadyExistException {
        if (userValidator.checkUserData(user)) {
            userRepository.save(user);
        }
    }


    public User select(User user) {
        if(userRepository.ifUserExist(user)){
           return  userRepository.select(user);
        }
        return null;
    }
}
