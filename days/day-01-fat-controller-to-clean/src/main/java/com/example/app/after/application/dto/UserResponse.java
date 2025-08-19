package com.example.app.after.application.dto;

import java.time.LocalDateTime;

/**
 * ✅ USER RESPONSE DTO - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Immutable record
 * - Clean response structure
 * - No sensitive data exposure
 * - Type safety
 * - Follows DDD principles
 */
public record UserResponse(
    String id,
    String email,
    String name,
    String status,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {
    // ✅ IMPROVEMENT: Immutable record for API responses
}
