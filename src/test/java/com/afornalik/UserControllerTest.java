package com.afornalik;

import com.afornalik.model.User;
import com.afornalik.service.UserRepository;
import com.afornalik.service.UserAttribute;
import com.afornalik.service.userattribute.UserBlockStatusChangeAttribute;
import com.afornalik.service.userattribute.UserFirstNameChangeAttribute;
import com.afornalik.service.userattribute.UserLastNameChangeAttribute;
import com.afornalik.service.userattribute.UserStatus;
import com.afornalik.userexception.IncorrectUserDataException;
import com.afornalik.userexception.UserAlreadyExistException;
import com.afornalik.userexception.UserUnexistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private final String FIRST_NAME = "Andrzej";
    private final String LAST_NAME = "Kowalski";
    private final LocalDate CREATE_DATE = LocalDate.now();
    private User user = new User(FIRST_NAME, LAST_NAME, CREATE_DATE);
    private UserController userController;
    private UserAttribute userAttribute;
    private EditUser editUser = new EditUser();

    @Mock
    private UserRepository userRepository;


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
        then(userRepository).should().save(user);
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
        userExist();

        //when
        userController.create(user);
    }

    @Test
    public void shouldReceiveExistedUser() {
        //given
        userExist();
        selectUser(user);

        //when
        User resultUser = userController.select(user);

        //then
        assertEquals(resultUser, user);
    }

    @Test
    public void shouldBlockExistUser() {
        //given
        userExist();

        //given - check before block
        assertFalse(user.isBlocked());

        //when
        userController.blockUser(user);

        //then
        assertTrue(user.isBlocked());
        then(userRepository).should().save(user);

    }

    @Test
    public void shouldNotBlockWhenUserUnexist() {
        //given
        user = null;
        userDoesntExist();

        //when
        userController.blockUser(user);

        //then
        then(userRepository).should(times(0)).save(user);
    }


    @Test
    public void shouldEditUserFirstName() throws UserUnexistException {
        //given
        userExist();
        String newFirstName = "Ala";
        userAttribute = new UserFirstNameChangeAttribute(user, newFirstName);

        //when
        userController.edit(userAttribute);

        //then
        assertNotEquals(FIRST_NAME, user.getFirstName());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldEditUserLastName() throws UserUnexistException {
        //given
        userExist();
        String newLastName = "Nowak";
        userAttribute = new UserLastNameChangeAttribute(user, newLastName);

        //when
        userController.edit(userAttribute);

        //then
        assertNotEquals(LAST_NAME, user.getLastName());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldEditUserStatus() throws UserUnexistException {
        //given
        userExist();
        boolean isBlocked = user.isBlocked();
        UserStatus userStatus = UserStatus.BLOCKED;
        userAttribute = new UserBlockStatusChangeAttribute(user, userStatus);

        //when
        userController.edit(userAttribute);

        //then
        assertNotEquals(isBlocked, user.isBlocked());
        then(userRepository).should().save(user);
    }

    @Test(expected = UserUnexistException.class)
    public void shouldThrowUserUnexistException() throws UserUnexistException {
        //given
        userDoesntExist();

        //when
        userController.edit(new UserFirstNameChangeAttribute(user, "newName"));

    }


    private void userDoesntExist() {
        given(userRepository.ifUserExist(any())).willReturn(false);
    }

    private void userExist() {
        given(userRepository.ifUserExist(any())).willReturn(true);
    }

    private void selectUser(User user) {
        given(userRepository.select(user)).willReturn(user);
    }
}
