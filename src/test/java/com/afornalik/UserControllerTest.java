package com.afornalik;

import com.afornalik.controller.UserController;
import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.repository.UserRepository;
import com.afornalik.service.user.attribute.*;
import com.afornalik.service.user.attribute.value.FieldValue;
import com.afornalik.service.user.attribute.value.UserStatus;
import com.afornalik.service.user.exception.UserUnexistException;
import com.afornalik.view.LoginUserView;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    private final String FIRST_NAME = "Andrzej";
    private final String LAST_NAME = "Kowalski";
    private final String EMAIL = "akowal@xxx.yyy";
    private final String PASSWORD = "654321";
    private final LocalDate CREATE_DATE = LocalDate.now();
    private final User user = new User(FIRST_NAME, LAST_NAME, PASSWORD,EMAIL, CREATE_DATE);
    private UserController userController;


    @Mock
    private UserSession userSession = new UserSession(user) ;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MailService mailService;

    @Before
    public void initValue() {
        userController = new UserController( userRepository, mailService, userSession);
    }

    @Test
    public void shouldReceiveExistUser() {
        //given
        createUserSession();

        //when
        User resultUser = userController.select();

        //then
        assertEquals(resultUser, user);
    }

    @Test
    public void shouldEditUserFirstName() {
        //given
        createUserSession();
        String newFirstName = "Ala";


        //when
        userController.edit( new FieldFirstNameValue(user, new FieldValue<String>(newFirstName)));

        //then
        assertNotEquals(FIRST_NAME, user.getFirstName());
        assertEquals(newFirstName,user.getFirstName());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldEditUserLastName() {
        //given
        createUserSession();
        String newLastName = "Nowak";

        //when
        userController.edit(new FieldLastNameValue(user, new FieldValue<String>(newLastName)));

        //then
        assertNotEquals(LAST_NAME, user.getLastName());
        assertEquals(newLastName, user.getLastName());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldEditUserStatus() {
        //given
        createUserSession();

        //when
        userController.edit(new FieldUserStatusValue(user, new FieldValue<UserStatus>(UserStatus.BLOCKED)));

        //then
        assertNotEquals(false, user.isBlocked());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldRemoveUser() {
        //given
        createUserSession();

        //when
        userController.delete();

        //then
        then(userRepository).should().delete(user);
        assertNull(userController.select());
    }

    @Test
    public void shouldChangePassword() {
        //given
        createUserSession();
        String newPassword = "123456";

        //when
        userController.edit(new FieldPasswordValue(user, new FieldValue<String>(newPassword)));

        //then
        assertNotEquals(PASSWORD, user.getPassword());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldChangeEmail()  {
        //given
        createUserSession();
        String newEmail = "justDummyEmail@nnn.eee";

        //when
        userController.edit(new FieldEmailValue(user, new FieldValue<String>(newEmail)));

        //then
        assertNotEquals(EMAIL,user.getEmail());
        then(userRepository).should().save(user);
    }

    @Test
    public void shouldResetPassword() {
        //given
        createUserSession();

        //when
        userController.resetPassword();

        //then
        assertNotEquals(PASSWORD,user.getPassword());
        then(mailService).should().sendNewPassword(user);
        then(userRepository).should().save(user);
    }

    private void createUserSession() {
        given(userSession.getLoggedUser()).willReturn(user);
    }
}
