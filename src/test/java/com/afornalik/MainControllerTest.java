package com.afornalik;

import com.afornalik.controller.MainController;
import com.afornalik.model.User;
import com.afornalik.model.UserSession;
import com.afornalik.service.user.UserRepository;
import com.afornalik.view.LoginUser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class MainControllerTest {

    private UserSession userSession;
    private  MainController mainController;

    @Mock
    private UserRepository userRepository;

    @Mock
    private LoginUser loginUser;

    @Before
    public void initParam() {


        mainController = new MainController(userRepository,loginUser);
    }

    @Test
    public void shouldCreateUserSession() {
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
}
