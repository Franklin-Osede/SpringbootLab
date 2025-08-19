package com.example.app.after.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.app.after.domain.valueobjects.Email;
import com.example.app.after.domain.valueobjects.UserId;
import com.example.app.shared.domain.DomainEvent;
import com.example.app.shared.domain.Entity;

/**
 * ✅ USER DOMAIN ENTITY - AFTER REFACTORING
 *
 * <p>IMPROVEMENTS IMPLEMENTED: - Rich domain model with business logic - Encapsulated state changes
 * - Domain events - Invariants protection - Factory methods - Follows DDD principles
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

  // Constructor for creating new users
  private User(UserId id) {
    super(id);
  }

  /** ✅ IMPROVEMENT: Factory method to create a new user */
  public static User create(String email, String name, String password) {
    User user = new User(UserId.generate());
    user.email = new Email(email);
    user.name = validateName(name);
    user.passwordHash = hashPassword(password);
    user.status = UserStatus.ACTIVE;
    user.createdAt = LocalDateTime.now();
    user.updatedAt = LocalDateTime.now();

    // ✅ IMPROVEMENT: Domain event
    user.addDomainEvent(new UserCreatedEvent(user.getId(), user.getEmail(), user.getName()));

    return user;
  }

  /** ✅ IMPROVEMENT: Domain validation */
  private static String validateName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new IllegalArgumentException("Name cannot be null or empty");
    }

    if (name.length() < 2) {
      throw new IllegalArgumentException("Name must be at least 2 characters");
    }

    if (name.length() > 100) {
      throw new IllegalArgumentException("Name cannot exceed 100 characters");
    }

    return name.trim();
  }

  /** ✅ IMPROVEMENT: Password hashing */
  private static String hashPassword(String password) {
    if (password == null || password.trim().isEmpty()) {
      throw new IllegalArgumentException("Password cannot be null or empty");
    }

    if (password.length() < 6) {
      throw new IllegalArgumentException("Password must be at least 6 characters");
    }

    // In a real application, use BCrypt or similar
    return String.valueOf(password.hashCode());
  }

  /** ✅ IMPROVEMENT: Update email with validation */
  public void updateEmail(String newEmail) {
    if (status == UserStatus.DELETED) {
      throw new IllegalStateException("Cannot update deleted user");
    }

    Email oldEmail = this.email;
    this.email = new Email(newEmail);
    this.updatedAt = LocalDateTime.now();

    // ✅ IMPROVEMENT: Domain event
    addDomainEvent(new UserEmailUpdatedEvent(this.getId(), oldEmail, this.email));
  }

  /** ✅ IMPROVEMENT: Update name with validation */
  public void updateName(String newName) {
    if (status == UserStatus.DELETED) {
      throw new IllegalStateException("Cannot update deleted user");
    }

    String oldName = this.name;
    this.name = validateName(newName);
    this.updatedAt = LocalDateTime.now();

    // ✅ IMPROVEMENT: Domain event
    addDomainEvent(new UserNameUpdatedEvent(this.getId(), oldName, this.name));
  }

  /** ✅ IMPROVEMENT: Update status with validation */
  public void updateStatus(UserStatus newStatus) {
    if (status == UserStatus.DELETED) {
      throw new IllegalStateException("Cannot update deleted user");
    }

    UserStatus oldStatus = this.status;
    this.status = newStatus;
    this.updatedAt = LocalDateTime.now();

    // ✅ IMPROVEMENT: Domain event
    addDomainEvent(new UserStatusUpdatedEvent(this.getId(), oldStatus, this.status));
  }

  /** ✅ IMPROVEMENT: Activate user */
  public void activate() {
    if (status == UserStatus.ACTIVE) {
      throw new IllegalStateException("User is already active");
    }

    if (status == UserStatus.DELETED) {
      throw new IllegalStateException("Cannot activate deleted user");
    }

    UserStatus oldStatus = this.status;
    this.status = UserStatus.ACTIVE;
    this.updatedAt = LocalDateTime.now();

    // ✅ IMPROVEMENT: Domain event
    addDomainEvent(new UserActivatedEvent(this.getId()));
  }

  /** ✅ IMPROVEMENT: Deactivate user */
  public void deactivate() {
    if (status == UserStatus.INACTIVE) {
      throw new IllegalStateException("User is already inactive");
    }

    if (status == UserStatus.DELETED) {
      throw new IllegalStateException("Cannot deactivate deleted user");
    }

    UserStatus oldStatus = this.status;
    this.status = UserStatus.INACTIVE;
    this.updatedAt = LocalDateTime.now();

    // ✅ IMPROVEMENT: Domain event
    addDomainEvent(new UserDeactivatedEvent(this.getId()));
  }

  /** ✅ IMPROVEMENT: Delete user (soft delete) */
  public void delete() {
    if (status == UserStatus.DELETED) {
      throw new IllegalStateException("User is already deleted");
    }

    UserStatus oldStatus = this.status;
    this.status = UserStatus.DELETED;
    this.updatedAt = LocalDateTime.now();

    // ✅ IMPROVEMENT: Domain event
    addDomainEvent(new UserDeletedEvent(this.getId()));
  }

  /** ✅ IMPROVEMENT: Add domain event */
  private void addDomainEvent(DomainEvent event) {
    domainEvents.add(event);
  }

  /** ✅ IMPROVEMENT: Get domain events and clear them */
  public List<DomainEvent> getDomainEvents() {
    List<DomainEvent> events = new ArrayList<>(domainEvents);
    domainEvents.clear();
    return events;
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

  // Domain Events
  public static class UserCreatedEvent extends DomainEvent {
    private final UserId userId;
    private final Email email;
    private final String name;

    public UserCreatedEvent(UserId userId, Email email, String name) {
      super();
      this.userId = userId;
      this.email = email;
      this.name = name;
    }

    public UserId getUserId() {
      return userId;
    }

    public Email getEmail() {
      return email;
    }

    public String getName() {
      return name;
    }
  }

  public static class UserEmailUpdatedEvent extends DomainEvent {
    private final UserId userId;
    private final Email oldEmail;
    private final Email newEmail;

    public UserEmailUpdatedEvent(UserId userId, Email oldEmail, Email newEmail) {
      super();
      this.userId = userId;
      this.oldEmail = oldEmail;
      this.newEmail = newEmail;
    }

    public UserId getUserId() {
      return userId;
    }

    public Email getOldEmail() {
      return oldEmail;
    }

    public Email getNewEmail() {
      return newEmail;
    }
  }

  public static class UserNameUpdatedEvent extends DomainEvent {
    private final UserId userId;
    private final String oldName;
    private final String newName;

    public UserNameUpdatedEvent(UserId userId, String oldName, String newName) {
      super();
      this.userId = userId;
      this.oldName = oldName;
      this.newName = newName;
    }

    public UserId getUserId() {
      return userId;
    }

    public String getOldName() {
      return oldName;
    }

    public String getNewName() {
      return newName;
    }
  }

  public static class UserStatusUpdatedEvent extends DomainEvent {
    private final UserId userId;
    private final UserStatus oldStatus;
    private final UserStatus newStatus;

    public UserStatusUpdatedEvent(UserId userId, UserStatus oldStatus, UserStatus newStatus) {
      super();
      this.userId = userId;
      this.oldStatus = oldStatus;
      this.newStatus = newStatus;
    }

    public UserId getUserId() {
      return userId;
    }

    public UserStatus getOldStatus() {
      return oldStatus;
    }

    public UserStatus getNewStatus() {
      return newStatus;
    }
  }

  public static class UserActivatedEvent extends DomainEvent {
    private final UserId userId;

    public UserActivatedEvent(UserId userId) {
      super();
      this.userId = userId;
    }

    public UserId getUserId() {
      return userId;
    }
  }

  public static class UserDeactivatedEvent extends DomainEvent {
    private final UserId userId;

    public UserDeactivatedEvent(UserId userId) {
      super();
      this.userId = userId;
    }

    public UserId getUserId() {
      return userId;
    }
  }

  public static class UserDeletedEvent extends DomainEvent {
    private final UserId userId;

    public UserDeletedEvent(UserId userId) {
      super();
      this.userId = userId;
    }

    public UserId getUserId() {
      return userId;
    }
  }
}
