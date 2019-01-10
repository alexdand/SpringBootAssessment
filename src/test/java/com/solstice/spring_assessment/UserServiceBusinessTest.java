package com.solstice.spring_assessment;

import com.solstice.spring_assessment.exception.AccountTransactionException;
import com.solstice.spring_assessment.model.Account;
import com.solstice.spring_assessment.model.AccountTypeEnum;
import com.solstice.spring_assessment.model.User;
import com.solstice.spring_assessment.repository.UserRepository;
import com.solstice.spring_assessment.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceBusinessTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    private List<User> users;
    private List<Account> accounts;
    private Boolean dataLoaded = false;

    @Before
    public void setUp() {
        userService = new UserService(userRepository);
        if (!dataLoaded) {
            accounts = new ArrayList<Account>(Arrays.asList(
                    new Account(1L, "", new BigDecimal("4000"), AccountTypeEnum.ARS),
                    new Account(2L, "", new BigDecimal("700"), AccountTypeEnum.USD),
                    new Account(3L, "", new BigDecimal("-800"), AccountTypeEnum.USD),
                    new Account(4L, "", new BigDecimal("950"), AccountTypeEnum.ARS)
            ));
            users = new ArrayList<User>(Arrays.asList(
                    new User(1L, 1122, "Barbara", "Gordon"),
                    new User(2L, 3344, "Gwen", "Stacy")
            ));
            users.get(0).getAccounts().add(accounts.get(0));
            users.get(0).getAccounts().add(accounts.get(1));
            users.get(1).getAccounts().add(accounts.get(2));
            users.get(1).getAccounts().add(accounts.get(3));
            dataLoaded = true;
        }
    }

    @Test
    public void getUsers_getUsersWithNegativeBalance() {
        given(userRepository.findAll()).willReturn(users);
        List<User> usersWithNegativeBalance = userService.getUsersWithNegativeBalance();
        usersWithNegativeBalance.stream().forEach(user -> {
            user.getAccounts().stream().forEach(account -> {
                assertThat(account.getAmount().compareTo(BigDecimal.ZERO) <= 0);
            });
        });
    }

    @Test
    public void doTransaction_userTransfersArsToUsdAccount() {
        given(userRepository.findById(1L)).willReturn(Optional.of(users.get(0)));
        userService.transferMoneyToAccount(1L, 1L, 2L, new BigDecimal("500"));
        User user = userService.getUserById(1L);
        Account account1 = user.getAccounts().stream().filter(acc -> acc.getId().equals(1L)).findFirst().get();
        Account account2 = user.getAccounts().stream().filter(acc -> acc.getId().equals(2L)).findFirst().get();
        System.out.println(account1.getAmount());
        assertThat(account1.getAmount()).isEqualTo(new BigDecimal("3500"));
        assertThat(account2.getAmount()).isEqualTo(new BigDecimal("713.500"));
    }

    @Test(expected = AccountTransactionException.class)
    public void doTransaction_userTransfersInsufficientBalance() {
        given(userRepository.findById(1L)).willReturn(Optional.of(users.get(0)));
        userService.transferMoneyToAccount(2L, 2L, 1L, new BigDecimal("1000"));
    }

}
