package com.solstice.spring_assessment.controller;

import com.solstice.spring_assessment.exception.AccountNotFoundException;
import com.solstice.spring_assessment.exception.UserNotFoundException;
import com.solstice.spring_assessment.model.Account;
import com.solstice.spring_assessment.model.User;
import com.solstice.spring_assessment.service.AccountService;
import com.solstice.spring_assessment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountController {

    private AccountService accountService;
    private UserService userService;

    public AccountController(AccountService accountService, UserService userService) {
        this.accountService = accountService;
        this.userService = userService;
    }

    @GetMapping(path = "/users/{userId}/accounts")
    public ResponseEntity<List<Account>> getAccounts(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAllAccounts(userId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @GetMapping(path = "/users/{userId}/accounts/{accountId}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long userId, @PathVariable Long accountId) {
        Account account = accountService.getAccountById(userId, accountId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping(path = "/users/{userId}/accounts/{accountId}")
    public ResponseEntity<Account> updateAccount(@PathVariable Long userId, @PathVariable Long accountId, @RequestBody Account accountUpdated) {
        accountService.updateAccount(userId, accountId, accountUpdated);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping(path = "/users/{userId}/accounts")
    public ResponseEntity<Account> createAccount(@PathVariable Long userId, @RequestBody Account account) {
        accountService.create(userId, account);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/users/{userId}/accounts/{accountId}")
    public ResponseEntity deleteAccount(@PathVariable Long userId, @PathVariable Long accountId) {
        accountService.deleteAccount(userId, accountId);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void accountNotFoundHandler(AccountNotFoundException ex) {}

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void userNotFoundHandler(UserNotFoundException ex) {}

}
