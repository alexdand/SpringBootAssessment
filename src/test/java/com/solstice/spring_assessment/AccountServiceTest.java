package com.solstice.spring_assessment;

import com.solstice.spring_assessment.exception.AccountNotFoundException;
import com.solstice.spring_assessment.model.Account;
import com.solstice.spring_assessment.model.AccountTypeEnum;
import com.solstice.spring_assessment.model.User;
import com.solstice.spring_assessment.repository.AccountRepository;
import com.solstice.spring_assessment.service.AccountService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserService userService;

    private AccountService accountService;

    private Boolean dataLoaded = false;
    private List<Account> accounts;
    private User userMock;

    @Before
    public void setUp() {
        accountService = new AccountService(accountRepository, userService);
        if (!dataLoaded) {
            accounts = new ArrayList<>(Arrays.asList(
                    new Account(1L,"1234567891234567891234", new BigDecimal("1500"), AccountTypeEnum.ARS),
                    new Account(2L,"1234567891234567891234", new BigDecimal("-270"), AccountTypeEnum.USD),
                    new Account(3L, "1234567891234567891234", new BigDecimal("850"), AccountTypeEnum.ARS),
                    new Account(4L, "1234567891234567891234", new BigDecimal("0"), AccountTypeEnum.ARS)
            ));
            userMock = new User(1L, 4442, "Sara", "Lance");
            accounts.stream().forEach(account -> userMock.getAccounts().add(account));
            dataLoaded = true;
        }
    }

    @Test
    public void getAccounts_shouldFetchThreeAccounts() {
        given(userService.getUserById(1L)).willReturn(userMock);
        List<Account> allAccounts = accountService.getAllAccounts(userMock.getId());
        assertThat(allAccounts).hasSize(4);
    }

    @Test
    public void getAccount_shouldFetchAccount() {
        given(userService.getUserById(1L)).willReturn(userMock);
        Account account = accountService.getAccountById(userMock.getId(), 1L);
        assertThat(account.getId()).isEqualTo(1L);
        assertThat(account.getCbu()).isEqualTo("1234567891234567891234");
        assertThat(account.getAccountType()).isEqualTo(AccountTypeEnum.ARS);
    }

    @Test(expected = AccountNotFoundException.class)
    public void getAccount_accountNotFound() {
        given(userService.getUserById(1L)).willReturn(userMock);
        accountService.getAccountById(userMock.getId(), 123L);
    }

    @Test
    public void createAccount_addsAccountToUser() {
        given(userService.getUserById(1L)).willReturn(userMock);
        accountService.create(1L, new Account(4L, "1234567891234567890000", new BigDecimal("500"), AccountTypeEnum.ARS));
        User userById = userService.getUserById(1L);
        assertThat(userById.getAccounts().stream().filter(acc -> acc.getId().equals(4L)).findFirst().get()).isNotNull();
    }

    @Test
    public void updateAccount_updatesAccount() {
        given(userService.getUserById(1L)).willReturn(userMock);
        Account update = new Account(1L, "1234567891234567891234", new BigDecimal("0"), AccountTypeEnum.ARS);
        accountService.updateAccount(1L, 1L, update);
    }

    @Test(expected = AccountNotFoundException.class)
    public void updateAccount_accountNotFound() {
        given(userService.getUserById(1L)).willReturn(userMock);
        accountService.updateAccount(1L, 123L, accounts.get(0));
    }

    @Test
    public void deleteAccount_accountDeleted() {
        given(userService.getUserById(1L)).willReturn(userMock);
        accountService.deleteAccount(userMock.getId(), 1L);
        User userById = userService.getUserById(1L);
        assertThat(userById.getAccounts().stream().filter(acc -> acc.getId().equals(1L)).findFirst()).isEqualTo(Optional.empty());
    }

}
