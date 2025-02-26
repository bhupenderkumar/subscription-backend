package com.lab49.subscription;

import com.lab49.subscription.model.SubscriptionPlan;
import com.lab49.subscription.model.UserSubscription;
import com.lab49.subscription.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Subscription", description = "Subscription API")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @GetMapping("/health")
    @Operation(summary = "Health check endpoint")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Subscription service is healthy");
    }

    @GetMapping("/plans")
    @Operation(summary = "Get all subscription plans")
    public ResponseEntity<List<SubscriptionPlan>> getAllSubscriptionPlans() {
        return ResponseEntity.ok(subscriptionService.getAllSubscriptionPlans());
    }

    @PostMapping("/subscribe/{planId}")
    @Operation(summary = "Subscribe user to a plan")
    public ResponseEntity<UserSubscription> subscribeUserToPlan(@PathVariable Long planId, @RequestParam Long userId) {
        return ResponseEntity.ok(subscriptionService.subscribeUserToPlan(userId, planId));
    }

    @GetMapping("/subscription")
    @Operation(summary = "Get user subscription")
    public ResponseEntity<UserSubscription> getUserSubscription(@RequestParam Long userId) {
        return ResponseEntity.ok(subscriptionService.getUserSubscription(userId));
    }

    @PostMapping("/payment/callback")
    @Operation(summary = "Process payment callback")
    public ResponseEntity<String> processPaymentCallback(@RequestParam Long subscriptionId, @RequestParam boolean success) {
        subscriptionService.processPayment(subscriptionId, success);
        return ResponseEntity.ok("Payment processed");
    }

    @GetMapping("/subscriber/financial-data")
    @Operation(summary = "Get financial data for subscribers only")
    public ResponseEntity<String> getFinancialData() {
        // Dummy financial data
        String financialData = "{\"revenue\": 1000000, \"expenses\": 500000, \"profit\": 500000}";
        return ResponseEntity.ok(financialData);
    }

    @PostMapping("/users")
    @Operation(summary = "Create a new user")
    public ResponseEntity<String> createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        // Dummy user creation
        System.out.println("Creating user: " + username + ", " + password + ", " + email);
        return ResponseEntity.ok("User created successfully");
    }
}