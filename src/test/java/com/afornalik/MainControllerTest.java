package com.afornalik;

import com.afornalik.controller.MainController;
import com.afornalik.controller.UserController;
import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.service.user.EditUser;
import com.afornalik.service.user.UserRepository;
import com.afornalik.service.user.attribute.UserFirstNameChangeAttribute;
import com.afornalik.service.user.exception.UserUnexistException;
import com.afornalik.view.LoginUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    private UserSession userSession;
    private  MainController mainController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoginUser loginUser;

    @Mock
    private MailService mailService;

    @Before
    public void initParam() {
        mainController = new MainController(userRepository,loginUser,mailService);
    }

    @Test
    public void shouldCreateUserSession() throws UserUnexistException {
        //given
        User user = new User("Adam","Kowalski","123456", LocalDate.now());
        BDDMockito.when(userRepository.selectByFirstName("Adam")).thenReturn(user);
        BDDMockito.when(loginUser.getUserLogin()).thenReturn("Adam");
        BDDMockito.when(loginUser.getPassword()).thenReturn("123456");

        //when
        userSession = mainController.logIn();

        //then
        Assert.assertEquals(userSession.getLogedUser(),user);


    }
    @Test (expected = UserUnexistException.class)
    public void shouldReturnUserNotExistException() throws UserUnexistException {
        //given
        BDDMockito.when(userRepository.selectByFirstName(any())).thenReturn(null);
        BDDMockito.when(loginUser.getUserLogin()).thenReturn("Adam");

        //when
        userSession = mainController.logIn();
    }

    @Test
    public void shouldNotLoginUser() throws UserUnexistException {
        //given
        User user = new User("Adam","Kowalski","123456", LocalDate.now());
        BDDMockito.when(userRepository.selectByFirstName("Adam")).thenReturn(user);
        BDDMockito.when(loginUser.getUserLogin()).thenReturn("Adam");
        BDDMockito.when(loginUser.getPassword()).thenReturn("12343");

        //when
        userSession = mainController.logIn();

        //than
        Assert.assertNull(userSession);
    }

    @Test
    public void shouldEnterUserController() throws UserUnexistException {
        //given
        User user = new User("Adam","Kowalski","123456", LocalDate.now());
        UserSession userSession = new UserSession(user);
        BDDMockito.when(userRepository.selectByFirstName("Adam")).thenReturn(user);
        BDDMockito.when(loginUser.getUserLogin()).thenReturn("Adam");
        BDDMockito.when(loginUser.getPassword()).thenReturn("123456");

        //when
        mainController.logIn();
        mainController.enterUserSettings();
        UserController userController = mainController.getUserController();

        //than
        Assert.assertEquals(userSession.getLogedUser().getFirstName(),userController.getUserSession().getLogedUser().getFirstName());
    }
}
