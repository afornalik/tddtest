package com.afornalik;

import com.afornalik.controller.MainController;
import com.afornalik.controller.UserController;
import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.mail.MailService;
import com.afornalik.repository.UserRepository;
import com.afornalik.service.user.exception.UserUnexistException;
import com.afornalik.view.LoginUserView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    private final User user = new User("Adam", "Kowalski", "123456","akowal@xxx.yyy", LocalDate.now());
    private MainController mainController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoginUserView loginUserView;

    @Mock
    private MailService mailService;

    @Before
    public void initParam() {
        mainController = new MainController(userRepository, loginUserView, mailService);
    }

    @Test
    public void shouldCreateUserSession() throws UserUnexistException {
        //given
        userExist();

        //when
        mainController.logIn();
        UserSession userSession = mainController.getUserSession();

        //then
        Assert.assertEquals(userSession.getLoggedUser(), user);
    }

    @Test(expected = UserUnexistException.class)
    public void shouldThrowUserNotExistException() throws UserUnexistException {
        //given
        userDoesNotExist();

        //when
        mainController.logIn();
    }

    @Test
    public void shouldNotLoginUser() throws UserUnexistException {
        //given
        userExistButWrongPasswordIsGiven();

        //when
        mainController.logIn();
        UserSession userSession = mainController.getUserSession();

        //than
        Assert.assertNull(userSession);
    }

    @Test
    public void shouldEnterUserController() throws UserUnexistException {
        //given
        UserSession userSession = new UserSession(user);
        userExist();

        //when
        mainController.logIn();
        mainController.enterUserSettings();
        UserController userController = mainController.getUserController();

        //than
        Assert.assertEquals(userSession.getLoggedUser(), userController.getUserSession().getLoggedUser());
    }

    private void userDoesNotExist() {
        BDDMockito.when(userRepository.selectByFirstName(any())).thenReturn(null);
        BDDMockito.when(loginUserView.getUserLogin()).thenReturn("Adam");
    }

    private void userExist() {
        BDDMockito.when(userRepository.selectByFirstName("Adam")).thenReturn(user);
        BDDMockito.when(loginUserView.getUserLogin()).thenReturn("Adam");
        BDDMockito.when(loginUserView.getPassword()).thenReturn("123456");
    }

    private void userExistButWrongPasswordIsGiven() {
        BDDMockito.when(userRepository.selectByFirstName("Adam")).thenReturn(user);
        BDDMockito.when(loginUserView.getUserLogin()).thenReturn("Adam");
        BDDMockito.when(loginUserView.getPassword()).thenReturn("12343");
    }
}
