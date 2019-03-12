package com.afornalik;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.time.LocalDate;

public class UserControllerTest {

    private final String FIRST_NAME = "Andrzej";
    private final String LAST_NAME = "Kowalski";
    private final LocalDate CREATE_DATE = LocalDate.now();
    private User user;

    private UserController userController;
    private UserRepository userRepository;

    @Before
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userController = new UserController(userRepository);
    }

    @Test
    public void shouldSaveUser() {
        //give
        user = new User(LAST_NAME,FIRST_NAME,CREATE_DATE);;

        //when
        userController.create(user);

        //then
        BDDMockito.then(userRepository).should().save(user);

    }

    @Test(expected = IncorrectUserDataException.class)
    public void shouldThrowIncorrectUserDataException() {
        //give
        user = new User(null,FIRST_NAME,CREATE_DATE);

        //when
        userController.create(user);
        
    }
}
