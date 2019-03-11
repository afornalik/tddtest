package com.afornalik;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;

import java.time.LocalDate;

public class UserControllerTest {

    private final String firstName = "Andrzej";
    private final String lastName = "Kowalski";
    private final LocalDate createDate = LocalDate.now();
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
        user = new User(firstName,lastName,createDate);;

        //when
        userController.create(user);

        //then
        BDDMockito.then(userRepository).should().save(user);

    }
}
