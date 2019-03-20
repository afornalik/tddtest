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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private final String FIRST_NAME = "Andrzej";
    private final String LAST_NAME = "Kowalski";
    private final LocalDate CREATE_DATE = LocalDate.now();
    private User user = new User(LAST_NAME, FIRST_NAME, CREATE_DATE);
    private UserController userController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EditUser editUser;


    @Before
    public void initVal() {
        userController = new UserController(user, userRepository, editUser);
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
        userExist(user);

        //when
        userController.create(user);
    }

    @Test
    public void shouldReceiveExistedUser() {
        //given
        userExist(user);
        selectUser(user);

        //when
        User resultUser = userController.select(user);

        //then
        Assert.assertEquals(resultUser, user);
    }

    @Test
    public void shouldBlockAndSaveExistUser() {
        //given
        userExist(user);

        //given - check before block
        Assert.assertFalse(user.isBlocked());

        //when
        userController.blockUser(user);

        //then
        Assert.assertTrue(user.isBlocked());
        BDDMockito.then(userRepository).should().save(user);

    }

    @Test
    public void shouldNotBlockWhenUserUnexist() {
        //given
        user = null;
        userDoesntExist(user);

        //when
        userController.blockUser(user);

        //then
        BDDMockito.then(userRepository).should(times(0)).save(user);
    }

    @Test
    public void shouldEditExistUser() {
        //given
        userExist(user);
        editUser(user);

        //when
        User result = userController.edit(user);

        //then
        Assert.assertNotNull(result);
        Assert.assertNotEquals(user, result);
    }

    private void editUser(User user) {
        BDDMockito.given(editUser.edit(user)).willReturn(new User("aa","dd",LocalDate.now()));
    }



    private void userDoesntExist(User user) {
        BDDMockito.given(userRepository.ifUserExist(user)).willReturn(false);
    }

    private void userExist(User user) {
        BDDMockito.given(userRepository.ifUserExist(user)).willReturn(true);
    }

    private void selectUser(User user) {
        BDDMockito.given(userRepository.select(user)).willReturn(user);
    }
}
