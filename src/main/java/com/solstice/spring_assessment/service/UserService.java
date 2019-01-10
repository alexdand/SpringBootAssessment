package com.solstice.spring_assessment.service;

import com.solstice.spring_assessment.exception.AccountTransactionException;
import com.solstice.spring_assessment.exception.UserNotFoundException;
import com.solstice.spring_assessment.helper.Quotations;
import com.solstice.spring_assessment.model.Account;
import com.solstice.spring_assessment.model.User;
import com.solstice.spring_assessment.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void transferMoneyToAccount(Long userId, Long originAccountId, Long destinyAccountId, BigDecimal amount)
            throws AccountTransactionException {
        User user = this.getUserById(userId);
        Account originAccount = getAccountFromUser(user, originAccountId);
        Account destinyAccount = getAccountFromUser(user, destinyAccountId);
        if (originAccount.equals(destinyAccount)) {
            throw new AccountTransactionException();
        }
        if (originAccount != null && destinyAccount != null) {
            if (originAccount.getAmount().subtract(amount).compareTo(BigDecimal.ZERO) >= 0) {
                originAccount.setAmount(originAccount.getAmount().subtract(amount));
                BigDecimal convertedAmount =
                        Quotations.convertAmount(amount, originAccount.getAccountType(), destinyAccount.getAccountType());
                destinyAccount.setAmount(destinyAccount.getAmount().add(convertedAmount));
                this.saveUser(user);
            } else {
                throw new AccountTransactionException();
            }
        }
    }

    private Account getAccountFromUser(User user, Long originAccountId) {
        return user.getAccounts().stream()
                .filter(acc -> acc.getId().equals(originAccountId)).findFirst().orElse(null);
    }

    public List<User> getUsersWithNegativeBalance() {
        List<User> userList = this.getAllUsers();
        List<User> filteredUsers = userList.stream().map(u -> {
            User aux = new User(u.getId(), u.getDni(), u.getFirstName(), u.getLastName());
            u.getAccounts().stream().forEach(account -> aux.getAccounts().add(account));
            return aux;
        }).collect(Collectors.toList())
        .stream().filter(u -> u.getAccounts().size() > 0).collect(Collectors.toList());
        return filteredUsers;
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return user;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long id, User acc) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException();
        }
        return userRepository.save(acc);
    }

    public User saveUser(User acc) {
        return userRepository.save(acc);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

}
