package com.solstice.spring_assessment.controller;

import com.solstice.spring_assessment.exception.AccountTransactionException;
import com.solstice.spring_assessment.exception.UserNotFoundException;
import com.solstice.spring_assessment.model.User;
import com.solstice.spring_assessment.service.AccountService;
import com.solstice.spring_assessment.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class UserController {

    private UserService userService;
    private AccountService accountService;

    public UserController(UserService userService, AccountService accountService) {
        this.userService = userService;
        this.accountService = accountService;
    }

    @GetMapping("/users/accounts/balance/negative")
    public ResponseEntity<List<User>> getUsersNegativeBalance() {
        List<User> usersWithNegativeBalance = userService.getUsersWithNegativeBalance();
        return new ResponseEntity<>(usersWithNegativeBalance, HttpStatus.OK);
    }

    @PutMapping("/users/{id}/transfer/{amount}")
    public ResponseEntity transferMoneyToAccount(
            @PathVariable("id") Long userId,
            @RequestParam("originAccount") Long originAccount,
            @RequestParam("destinyAccount") Long destinyAccount,
            @PathVariable("amount") String amount) {
        userService.transferMoneyToAccount(userId, originAccount, destinyAccount, new BigDecimal(amount));
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> allUsers = userService.getAllUsers();
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<User> createUser(@RequestBody User user){
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User userCreated = userService.saveUser(user);
        return new ResponseEntity<>(userCreated, HttpStatus.OK);
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User updatedUser = userService.updateUser(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return new ResponseEntity(HttpStatus.ACCEPTED);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void userNotFoundHandler(UserNotFoundException ex) {}

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private void transactionErrorHandler(AccountTransactionException ex) {}

}
