package com.example.app.after.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * ✅ UPDATE USER REQUEST DTO - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Immutable record
 * - Optional fields for updates
 * - Bean validation annotations
 * - Clear validation rules
 * - Type safety
 * - Follows DDD principles
 */
public record UpdateUserRequest(
    @Email(message = "Email must be valid")
    String email,
    
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,
    
    String status
) {
    // ✅ IMPROVEMENT: Immutable record with optional validation
}
