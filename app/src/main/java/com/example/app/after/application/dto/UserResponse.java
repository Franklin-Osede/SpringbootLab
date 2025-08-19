package com.example.app.after.application.dto;

import java.time.LocalDateTime;

/**
 * ✅ DTO de respuesta para usuarios - DESPUÉS DEL REFACTORING
 */
public record UserResponse(
    String id,
    String email,
    String name,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    
    public UserResponse {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        
        if (status == null || status.trim().isEmpty()) {
            throw new IllegalArgumentException("User status cannot be null or empty");
        }
    }
    
    public boolean isActive() {
        return "ACTIVE".equals(status);
    }
    
    public boolean isInactive() {
        return "INACTIVE".equals(status);
    }
    
    public boolean isSuspended() {
        return "SUSPENDED".equals(status);
    }
    
    public boolean isDeleted() {
        return "DELETED".equals(status);
    }
}
