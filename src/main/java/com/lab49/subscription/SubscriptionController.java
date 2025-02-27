package com.lab49.subscription;

import com.lab49.subscription.model.SubscriptionPlan;
import com.lab49.subscription.model.User;
import com.lab49.subscription.model.UserRegistrationRequest;
import com.lab49.subscription.model.UserSubscription;
import com.lab49.subscription.service.SubscriptionService;
import com.lab49.subscription.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Subscription", description = "API for managing subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;
    
    @Autowired
    private UserService userService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
        @GetMapping("/health")
        @Operation(summary = "Health check endpoint", description = "Returns a message indicating that the subscription service is healthy.")
        public ResponseEntity<String> healthCheck() {
            return ResponseEntity.ok("Subscription service is healthy");
        }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
        @GetMapping("/plans")
        @Operation(summary = "Get all subscription plans", description = "Returns a list of all available subscription plans.")
        public ResponseEntity<List<SubscriptionPlan>> getAllSubscriptionPlans() {
            return ResponseEntity.ok(subscriptionService.getAllSubscriptionPlans());
        }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
        @PostMapping("/subscribe/{planId}")
        @Operation(summary = "Subscribe user to a plan", description = "Subscribes a user to a specific subscription plan.")
        public ResponseEntity<UserSubscription> subscribeUserToPlan(@PathVariable Long planId, @RequestParam Long userId) {
            return ResponseEntity.ok(subscriptionService.subscribeUserToPlan(userId, planId));
        }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
        @GetMapping("/subscription")
        @Operation(summary = "Get user subscription", description = "Retrieves the subscription details for a given user.")
        public ResponseEntity<UserSubscription> getUserSubscription(@RequestParam Long userId) {
            return ResponseEntity.ok(subscriptionService.getUserSubscription(userId));
        }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
        @PostMapping("/payment/callback")
        @Operation(summary = "Process payment callback", description = "Processes the payment callback after a subscription payment is made.")
        public ResponseEntity<String> processPaymentCallback(@RequestParam Long subscriptionId, @RequestParam boolean success) {
            subscriptionService.processPayment(subscriptionId, success);
            return ResponseEntity.ok("Payment processed");
        }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
        @GetMapping("/subscriber/financial-data")
        @Operation(summary = "Get financial data for subscribers only", description = "Returns financial data for subscribers only. This endpoint is restricted to users with a valid subscription.")
        public ResponseEntity<String> getFinancialData() {
            // Dummy financial data
            String financialData = "{\"revenue\": 1000000, \"expenses\": 500000, \"profit\": 500000}";
            return ResponseEntity.ok(financialData);
        }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/users")
    @Operation(summary = "Create a new user", description = "Creates a new user with the provided username, password, and email.")
    public ResponseEntity<User> createUser(@Valid @RequestBody UserRegistrationRequest request) {
        User createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Returns a list of all registered users.")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
