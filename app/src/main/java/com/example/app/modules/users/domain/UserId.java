package com.example.app.modules.users.domain;

import com.example.app.shared.domain.ValueObject;
import java.util.UUID;

/**
 * UserId value object representing a unique user identifier.
 * 
 * This class encapsulates the user ID generation and validation logic.
 * It ensures that user IDs are always valid UUIDs.
 */
public class UserId extends ValueObject<UUID> {

    public UserId(UUID value) {
        super(value);
        validateUserId(value);
    }

    private void validateUserId(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
    }

    /**
     * Generate a new random UserId.
     */
    public static UserId generate() {
        return new UserId(UUID.randomUUID());
    }

    /**
     * Create UserId from string representation.
     */
    public static UserId fromString(String userIdString) {
        try {
            UUID uuid = UUID.fromString(userIdString);
            return new UserId(uuid);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid User ID format: " + userIdString);
        }
    }

    /**
     * Get the string representation of the UserId.
     */
    public String toString() {
        return value.toString();
    }

    /**
     * Get the UUID value.
     */
    public UUID getValue() {
        return value;
    }
}
