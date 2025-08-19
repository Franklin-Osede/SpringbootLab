package com.example.app.modules.users.domain;

import com.example.app.shared.domain.Entity;
import com.example.app.modules.users.domain.events.UserRegisteredEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * User aggregate root representing a user in the system.
 * 
 * This entity encapsulates user business logic and ensures domain invariants.
 * Following DDD principles, it contains business behavior, not just data.
 */
public class User extends Entity<UserId> {

    private Email email;
    private String name;
    private String passwordHash;
    private UserStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private final List<DomainEvent> domainEvents = new ArrayList<>();

    // Private constructor for JPA
    private User() {
        super(null);
    }

    /**
     * Factory method to create a new user.
     * This ensures all business rules are applied during creation.
     */
    public static User create(Email email, String name, String passwordHash) {
        User user = new User();
        user.id = UserId.generate();
        user.email = email;
        user.name = name;
        user.passwordHash = passwordHash;
        user.status = UserStatus.PENDING;
        user.createdAt = LocalDateTime.now();
        user.updatedAt = LocalDateTime.now();
        
        // Add domain event
        user.addDomainEvent(new UserRegisteredEvent(user.id, user.email));
        
        return user;
    }

    /**
     * Activate the user account.
     * This method encapsulates the business rule for user activation.
     */
    public void activate() {
        if (this.status == UserStatus.ACTIVE) {
            throw new IllegalStateException("User is already active");
        }
        
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Deactivate the user account.
     */
    public void deactivate() {
        if (this.status == UserStatus.INACTIVE) {
            throw new IllegalStateException("User is already inactive");
        }
        
        this.status = UserStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Update user information.
     * Validates that the user is active before allowing updates.
     */
    public void updateInfo(String name) {
        if (this.status != UserStatus.ACTIVE) {
            throw new IllegalStateException("Cannot update inactive user");
        }
        
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        
        this.name = name.trim();
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Change user password.
     */
    public void changePassword(String newPasswordHash) {
        if (newPasswordHash == null || newPasswordHash.trim().isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be empty");
        }
        
        this.passwordHash = newPasswordHash;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Check if user is active.
     */
    public boolean isActive() {
        return this.status == UserStatus.ACTIVE;
    }

    /**
     * Check if user can perform actions.
     */
    public boolean canPerformActions() {
        return this.status == UserStatus.ACTIVE;
    }

    // Getters
    public Email getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public UserStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Get all domain events and clear them.
     * This is used by the infrastructure layer to publish events.
     */
    public List<DomainEvent> getDomainEvents() {
        List<DomainEvent> events = new ArrayList<>(domainEvents);
        domainEvents.clear();
        return events;
    }

    private void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email=" + email +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
