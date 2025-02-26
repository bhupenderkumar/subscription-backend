package com.lab49.subscription.repository;

import com.lab49.subscription.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}