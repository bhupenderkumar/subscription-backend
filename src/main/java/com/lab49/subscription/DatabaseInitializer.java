package com.lab49.subscription;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DatabaseInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        logger.info("Initializing database...");
        jdbcTemplate.execute("DROP TABLE IF EXISTS user_subscription");
        jdbcTemplate.execute("DROP TABLE IF EXISTS subscription_plan");
        jdbcTemplate.execute("DROP TABLE IF EXISTS users");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS users (user_id BIGINT AUTO_INCREMENT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), email VARCHAR(255))");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS subscription_plan (plan_id BIGINT AUTO_INCREMENT PRIMARY KEY, plan_name VARCHAR(255), price DOUBLE, duration VARCHAR(255), features VARCHAR(1000))");
        jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS user_subscription (subscription_id BIGINT AUTO_INCREMENT PRIMARY KEY, user_id BIGINT NOT NULL, plan_id BIGINT NOT NULL, start_date DATE, end_date DATE, payment_status VARCHAR(255), FOREIGN KEY (user_id) REFERENCES users(user_id), FOREIGN KEY (plan_id) REFERENCES subscription_plan(plan_id))");

        // Insert sample data
        jdbcTemplate.execute("INSERT INTO users (username, password, email) VALUES ('testuser', 'password', 'test@example.com')");
        jdbcTemplate.execute("INSERT INTO subscription_plan (plan_name, price, duration, features) VALUES ('Basic', 9.99, 'MONTHLY', 'Access to standard features, Email support')");
        jdbcTemplate.execute("INSERT INTO user_subscription (user_id, plan_id, start_date, end_date, payment_status) VALUES (1, 1, '2024-01-01', '2024-01-31', 'PAID')");
        jdbcTemplate.execute("INSERT INTO subscription_plan (plan_name, price, duration, features) VALUES ('Premium', 19.99, 'YEARLY', 'Access to premium features, Priority email support, Monthly reports')");
        jdbcTemplate.execute("INSERT INTO subscription_plan (plan_name, price, duration, features) VALUES ('Enterprise', 29.99, 'BASIC', 'Access to all features, Dedicated support, Customization options, Training sessions')");
        jdbcTemplate.execute("INSERT INTO users (username, password, email) VALUES ('testuser2', 'password2', 'test2@example.com')");
        jdbcTemplate.execute("INSERT INTO user_subscription (user_id, plan_id, start_date, end_date, payment_status) VALUES (2, 2, '2024-01-01', '2024-01-31', 'PAID')");
        jdbcTemplate.execute("INSERT INTO subscription_plan (plan_name, price, duration, features) VALUES ('Lab49 AI Ready Developer', 49.99, 'MONTHLY', 'All Enterprise plan features, AI Mode: Access to AI-powered development tools, Fast Development Capabilities: Optimized environment for rapid development, Priority Support: Dedicated support for AI-related issues, Access to Lab49 AI community, Exclusive AI training modules, Early access to new AI features, Integration with Lab49''s AI platform, Personalized AI development roadmap, Access to AI research papers')");

        logger.info("Database initialized.");
    }
}