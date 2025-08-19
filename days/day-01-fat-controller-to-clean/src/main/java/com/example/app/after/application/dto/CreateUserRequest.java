package com.example.app.after.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * ✅ CREATE USER REQUEST DTO - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Immutable record
 * - Bean validation annotations
 * - Clear validation rules
 * - Type safety
 * - Follows DDD principles
 */
public record CreateUserRequest(
    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    String email,
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,
    
    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    String password
) {
    // ✅ IMPROVEMENT: Immutable record with validation
}
