package com.example.app.after.domain;

/**
 * ✅ USER STATUS ENUM - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Domain enum with business meaning
 * - Immutable values
 * - Clear business states
 * - Follows DDD principles
 */
public enum UserStatus {
    
    /**
     * User is active and can use the system
     */
    ACTIVE,
    
    /**
     * User is inactive and cannot use the system
     */
    INACTIVE,
    
    /**
     * User is suspended due to policy violation
     */
    SUSPENDED,
    
    /**
     * User is deleted (soft delete)
     */
    DELETED,
    
    /**
     * User is pending activation
     */
    PENDING;

    /**
     * ✅ IMPROVEMENT: Check if status allows login
     */
    public boolean canLogin() {
        return this == ACTIVE;
    }

    /**
     * ✅ IMPROVEMENT: Check if status allows updates
     */
    public boolean canUpdate() {
        return this != DELETED;
    }

    /**
     * ✅ IMPROVEMENT: Check if status is active
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * ✅ IMPROVEMENT: Check if status is inactive
     */
    public boolean isInactive() {
        return this == INACTIVE;
    }

    /**
     * ✅ IMPROVEMENT: Check if status is suspended
     */
    public boolean isSuspended() {
        return this == SUSPENDED;
    }

    /**
     * ✅ IMPROVEMENT: Check if status is deleted
     */
    public boolean isDeleted() {
        return this == DELETED;
    }

    /**
     * ✅ IMPROVEMENT: Check if status is pending
     */
    public boolean isPending() {
        return this == PENDING;
    }
}
