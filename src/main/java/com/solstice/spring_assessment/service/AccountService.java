package com.solstice.spring_assessment.service;

import com.solstice.spring_assessment.exception.AccountNotFoundException;
import com.solstice.spring_assessment.exception.UserNotFoundException;
import com.solstice.spring_assessment.model.Account;
import com.solstice.spring_assessment.model.User;
import com.solstice.spring_assessment.repository.AccountRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private UserService userService;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    public Account getAccountById(Long userId, Long accountId) throws AccountNotFoundException {
        User user = userService.getUserById(userId);
        Account account = user.getAccounts().stream().filter(acc -> acc.getId().equals(accountId)).findFirst().orElse(null);
        if (account == null) {
            throw new AccountNotFoundException();
        }
        return account;
    }

    public List<Account> getAllAccounts(Long userId) {
        User user = userService.getUserById(userId);
        List<Account> accounts = user.getAccounts();
        return accounts;
    }

    public void updateAccount(Long userId, Long accountId, Account acc) {
        Account account = this.getAccountById(userId, accountId);
        User user = userService.getUserById(userId);
        if (user.getAccounts().contains(account)) {
            user.getAccounts().remove(account);
            user.getAccounts().add(acc);
            userService.updateUser(user.getId(), user);
        }
    }

    public void create(Long userId, Account acc) {
        User user = userService.getUserById(userId);
        user.getAccounts().add(acc);
        userService.saveUser(user);
    }

    public void deleteAccount(Long userId, Long accountId) {
        User user = userService.getUserById(userId);
        Account account = this.getAccountById(userId, accountId);
        if (user.getAccounts().contains(account)) {
            user.getAccounts().remove(account);
            userService.updateUser(user.getId(), user);
        }
        // accountRepository.deleteById(id);
    }

}
