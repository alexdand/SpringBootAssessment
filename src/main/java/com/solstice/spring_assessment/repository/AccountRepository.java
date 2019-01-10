package com.solstice.spring_assessment.repository;

import com.solstice.spring_assessment.model.Account;
import com.solstice.spring_assessment.service.AccountService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {}
