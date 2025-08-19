package com.example.app.after.domain.valueobjects;

import java.util.UUID;

/**
 * ✅ USER ID VALUE OBJECT - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Immutable value object
 * - Type safety
 * - Domain-specific validation
 * - Proper equals and hashCode
 * - Follows DDD principles
 */
public class UserId extends ValueObject<String> {

    public UserId(String value) {
        super(value);
        validate(value);
    }

    public static UserId generate() {
        return new UserId("user-" + UUID.randomUUID().toString().replace("-", ""));
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        if (!value.startsWith("user-")) {
            throw new IllegalArgumentException("User ID must start with 'user-'");
        }
        if (value.length() < 10) {
            throw new IllegalArgumentException("User ID must be at least 10 characters");
        }
    }

    @Override
    public String toString() {
        return getValue();
    }
}
