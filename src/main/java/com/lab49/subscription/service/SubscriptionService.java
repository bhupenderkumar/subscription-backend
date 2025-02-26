package com.lab49.subscription.service;

import com.lab49.subscription.model.SubscriptionPlan;
import com.lab49.subscription.model.User;
import com.lab49.subscription.model.UserSubscription;
import com.lab49.subscription.repository.SubscriptionPlanRepository;
import com.lab49.subscription.repository.UserRepository;
import com.lab49.subscription.repository.UserSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubscriptionPlanRepository subscriptionPlanRepository;

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    public List<SubscriptionPlan> getAllSubscriptionPlans() {
        return subscriptionPlanRepository.findAll();
    }

    public UserSubscription subscribeUserToPlan(Long userId, Long planId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        SubscriptionPlan plan = subscriptionPlanRepository.findById(planId).orElseThrow(() -> new IllegalArgumentException("Invalid plan ID"));

        UserSubscription userSubscription = new UserSubscription();
        userSubscription.setUser(user);
        userSubscription.setPlan(plan);
        userSubscription.setStartDate(LocalDate.now());
        userSubscription.setEndDate(LocalDate.now().plusMonths(1)); // Default to monthly subscription
        userSubscription.setPaymentStatus("PENDING");

        return userSubscriptionRepository.save(userSubscription);
    }



    public UserSubscription getUserSubscription(Long userId) {
        Optional<UserSubscription> subscription = userSubscriptionRepository.findByUser_UserId(userId);
        return subscription.orElse(null);
    }

    @Transactional
    public void processPayment(Long subscriptionId, boolean success) {
        UserSubscription userSubscription = userSubscriptionRepository.findById(subscriptionId).orElseThrow(() -> new IllegalArgumentException("Invalid subscription ID"));
        if (success) {
            userSubscription.setPaymentStatus("PAID");
            userSubscriptionRepository.save(userSubscription);
        } else {
            // Handle payment failure
            userSubscription.setPaymentStatus("FAILED");
            userSubscriptionRepository.save(userSubscription);
        }
    }
}