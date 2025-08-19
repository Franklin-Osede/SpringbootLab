package com.example.app.after.application.mapper;

import com.example.app.after.application.dto.UserResponse;
import com.example.app.after.domain.User;
import org.springframework.stereotype.Component;

/**
 * ✅ USER MAPPER - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Clean mapping logic
 * - Single responsibility
 * - No business logic
 * - Type safety
 * - Follows DDD principles
 */
@Component
public class UserMapper {

    /**
     * ✅ IMPROVEMENT: Clean mapping from domain to DTO
     */
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        return new UserResponse(
            user.getId().getValue(),
            user.getEmail().getValue(),
            user.getName(),
            user.getStatus().name(),
            user.getCreatedAt(),
            user.getUpdatedAt()
        );
    }
}
