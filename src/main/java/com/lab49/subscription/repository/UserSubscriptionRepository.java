package com.lab49.subscription.repository;

import com.lab49.subscription.model.UserSubscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserSubscriptionRepository extends JpaRepository<UserSubscription, Long> {
    @Query("SELECT us FROM UserSubscription us WHERE us.user.userId = :userId")
    Optional<UserSubscription> findByUser_UserId(@Param("userId") Long userId);
}