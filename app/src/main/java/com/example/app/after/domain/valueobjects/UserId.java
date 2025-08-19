package com.example.app.after.domain.valueobjects;

import com.example.app.shared.domain.ValueObject;

import java.util.UUID;

/**
 * ✅ USER ID VALUE OBJECT - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Immutable value object
 * - Domain validation
 * - Type safety
 * - Follows DDD principles
 */
public class UserId extends ValueObject<String> {

    public UserId(String value) {
        super(value);
        validate(value);
    }

    /**
     * ✅ IMPROVEMENT: Domain validation
     */
    private void validate(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        
        if (value.length() < 3) {
            throw new IllegalArgumentException("User ID must be at least 3 characters");
        }
    }

    /**
     * ✅ IMPROVEMENT: Factory method for generating new IDs
     */
    public static UserId generate() {
        return new UserId(UUID.randomUUID().toString());
    }

    /**
     * ✅ IMPROVEMENT: Get the string value
     */
    public String getValue() {
        return value();
    }
}
