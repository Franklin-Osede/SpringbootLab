package com.example.app.after.application.mapper;

import com.example.app.after.application.dto.UserResponse;
import com.example.app.after.domain.User;
import org.springframework.stereotype.Component;

/**
 * ✅ USER MAPPER - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Single responsibility: mapping between domain and DTOs
 * - Clean separation of concerns
 * - Immutable mapping
 * - No business logic
 * - Follows SOLID principles
 */
@Component
public class UserMapper {

    /**
     * ✅ IMPROVEMENT: Clean mapping from domain to DTO
     */
    public UserResponse toResponse(User user) {
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
