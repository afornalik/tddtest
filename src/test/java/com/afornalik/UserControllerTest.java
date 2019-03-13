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
    private User user = new User(LAST_NAME, FIRST_NAME, CREATE_DATE);

    private UserController userController;
    private UserRepository userRepository;

    @Before
    public void init() {
        userRepository = Mockito.mock(UserRepository.class);
        userController = new UserController(user,userRepository);
    }

    @Test
    public void shouldSaveUser() throws UserAlreadyExistException {
        //give
        user = new User(LAST_NAME, FIRST_NAME, CREATE_DATE);


        //when
        userController.create(user);

        //then
        BDDMockito.then(userRepository).should().save(user);

    }

    @Test(expected = IncorrectUserDataException.class)
    public void shouldThrowIncorrectUserDataException() throws UserAlreadyExistException {
        //give
        user = new User(null, LAST_NAME, CREATE_DATE);

        //when
        userController.create(user);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void shouldThrowUserAlreadyExistException() throws UserAlreadyExistException {
        //give
        doubleCreateSameUser(user);

        //when
        userController.create(user);
    }

    private void doubleCreateSameUser(User user) {
        BDDMockito.given(userRepository.ifUserExist(user)).willReturn(true);
    }
}
