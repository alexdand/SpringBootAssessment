package com.solstice.spring_assessment.repository;

import com.solstice.spring_assessment.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
