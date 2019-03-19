package com.afornalik;

import com.afornalik.model.User;
import com.afornalik.service.UserRepository;
import com.afornalik.userexception.IncorrectUserDataException;
import com.afornalik.userexception.UserAlreadyExistException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private final String FIRST_NAME = "Andrzej";
    private final String LAST_NAME = "Kowalski";
    private final LocalDate CREATE_DATE = LocalDate.now();
    private User user = new User(LAST_NAME, FIRST_NAME, CREATE_DATE);
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Before
    public void init() {
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
        createUser(user);

        //when
        userController.create(user);
    }

    @Test
    public void shouldReceiveExistedUser(){
        //given
        createUser(user);
        selectUser(user);

        //when
        User resultUser = userController.select(user);

        //then
        Assert.assertEquals(resultUser,user);
    }

    @Test
    public void shouldBlockAndSaveExistUser() {
        //given
        user.setBlocked(false);

        //when
        userController.blockUser(user);

        //then
        Assert.assertTrue(user.isBlocked());
        BDDMockito.then(userRepository).should().save(user);


    }



    private void createUser(User user) {
        BDDMockito.given(userRepository.ifUserExist(user)).willReturn(true);
    }

    private void selectUser(User user) {
        BDDMockito.given(userRepository.select(user)).willReturn(user);
    }
}
