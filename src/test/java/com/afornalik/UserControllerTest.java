package com.afornalik;

import com.afornalik.model.User;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.user.UserRepository;
import com.afornalik.service.user.UserAttribute;
import com.afornalik.service.user.attribute.*;
import com.afornalik.userexception.IncorrectUserDataException;
import com.afornalik.userexception.UserAlreadyExistException;
import com.afornalik.userexception.UserUnexistException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private final String FIRST_NAME = "Andrzej";
    private final String LAST_NAME = "Kowalski";
    private final String PASSWORD = "654321";
    private final LocalDate CREATE_DATE = LocalDate.now();
    private User user;
    private UserController userController;
    private UserAttribute userAttribute;
    private EditUser editUser = new EditUser();

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailService mailService;

    @Before
    public void initValue() {
        userController = new UserController( userRepository, editUser, mailService);
    }

    @Test
    public void shouldSaveUser() throws UserAlreadyExistException {
        //give
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);

        //when
        userController.create(user);

        //then
        then(userRepository).should().save(user);
    }

    @Test(expected = IncorrectUserDataException.class)
    public void shouldThrowIncorrectUserDataException() throws UserAlreadyExistException {
        //give
        user = new User(null, LAST_NAME, PASSWORD, CREATE_DATE);

        //when
        userController.create(user);
    }

    @Test(expected = UserAlreadyExistException.class)
    public void shouldThrowUserAlreadyExistException() throws UserAlreadyExistException {
        //give
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);

        //when
        userController.create(user);
    }

    @Test
    public void shouldReceiveExistedUser() {
        //given
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);
        selectUser(user);

        //when
        User resultUser = userController.select(user);

        //then
        assertEquals(resultUser, user);
    }

    @Test
    public void shouldBlockExistUser() {
        //given
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);

        //given - check before block
        assertEquals(user.isBlocked(),UserStatus.UNBLOCKED);

        //when
        userController.blockUser(user);

        //then
        assertEquals(user.isBlocked(),UserStatus.BLOCKED);
        then(userRepository).should().save(user);

    }

    @Test
    public void shouldNotBlockWhenUserUnexist() {
        //given
        user = null;
        userDoesntExist(user);

        //when
        userController.blockUser(user);

        //then
        then(userRepository).should(times(0)).save(user);
    }


    @Test
    public void shouldEditUserFirstName() throws UserUnexistException {
        //given
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);
        String newFirstName = "Ala";
        userAttribute = new UserFirstNameChangeAttribute(user, newFirstName);

        //when
        userController.edit(user,userAttribute);

        //then
        assertNotEquals(FIRST_NAME, user.getFirstName());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldEditUserLastName() throws UserUnexistException {
        //given
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);
        String newLastName = "Nowak";
        userAttribute = new UserLastNameChangeAttribute(user, newLastName);

        //when
        userController.edit(user,userAttribute);

        //then
        assertNotEquals(LAST_NAME, user.getLastName());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldEditUserStatus() throws UserUnexistException {
        //given
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);
        userAttribute = new UserBlockStatusChangeAttribute(user, UserStatus.BLOCKED);

        //when
        userController.edit(user,userAttribute);

        //then
        assertNotEquals(false, user.isBlocked());
        then(userRepository).should().save(user);
    }

    @Test(expected = UserUnexistException.class)
    public void shouldThrowUserUnexistException() throws UserUnexistException {
        //given
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userDoesntExist(user);

        //when
        userController.edit(user,new UserFirstNameChangeAttribute(user, "newName"));

    }

    @Test
    public void shouldRemoveUser() {
        //given
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);

        //when
        userController.delete(user);

        //then
        then(userRepository).should().delete(user);
        assertNull(userController.select(user));
    }

    @Test
    public void shouldChangePassword() throws UserUnexistException {
        //give
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);
        String newPassword = "123456";
        userAttribute = new UserPasswordChangeAttribute(user, newPassword);

        //when
        userController.edit(user,userAttribute);

        //then
        assertNotEquals(PASSWORD, user.getPassword());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldResetPassword() {
        //given
        user = new User(FIRST_NAME, LAST_NAME, PASSWORD, CREATE_DATE);
        userExist(user);

        //when
        userController.resetPassword(user);

        //then
        assertNotEquals(PASSWORD,user.getPassword());
        then(mailService).should().sendNewPassword(user);
        then(userRepository).should().save(user);
    }


    private void userDoesntExist(User user) {
        given(userRepository.ifUserExist(user)).willReturn(false);
    }

    private void userExist(User user) {
        given(userRepository.ifUserExist(user)).willReturn(true);
    }

    private void selectUser(User user) {
        given(userRepository.select(user)).willReturn(user);
    }
}
