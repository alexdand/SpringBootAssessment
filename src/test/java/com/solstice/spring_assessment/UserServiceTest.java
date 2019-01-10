package com.solstice.spring_assessment;

import com.solstice.spring_assessment.exception.UserNotFoundException;
import com.solstice.spring_assessment.model.User;
import com.solstice.spring_assessment.repository.UserRepository;
import com.solstice.spring_assessment.service.AccountService;
import com.solstice.spring_assessment.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;
    private User userMock;
    private List<User> users;

    @Before
    public void setup() {
        userService = new UserService(userRepository);
        userMock = new User(1L, 1234, "Louisa", "Lane");
        users = new ArrayList<User>(Arrays.asList(new User(), new User(), new User(), new User()));
    }

    @Test
    public void getUser_shouldFetchUser() {
        given(userRepository.findById(1L)).willReturn(Optional.of(userMock));
        User userById = userService.getUserById(1L);
        assertThat(userById.getId()).isEqualTo(1L);
        assertThat(userById.getDni()).isEqualTo(1234);
        assertThat(userById.getFirstName()).isEqualTo("Louisa");
    }

    @Test(expected = UserNotFoundException.class)
    public void getUser_userNotFound() {
        given(userRepository.findById(1L)).willReturn(Optional.ofNullable(null));
        userService.getUserById(1L);
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUser_userIsRemoved() {
        given(userService.getUserById(1L)).willReturn(userMock);
        userService.deleteUser(1L);
        userService.getUserById(1L);
    }

    @Test
    public void getUsers_getFourUsers() {
        given(userRepository.findAll()).willReturn(users);
        assertThat(userService.getAllUsers()).hasSize(4);
    }

}
