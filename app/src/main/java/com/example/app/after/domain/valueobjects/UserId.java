package com.example.app.after.domain.valueobjects;

import java.util.UUID;

import com.example.app.shared.domain.ValueObject;

/**
 * ✅ USER ID VALUE OBJECT - AFTER REFACTORING
 *
 * <p>IMPROVEMENTS IMPLEMENTED: - Immutable value object - Domain validation - Type safety - Follows
 * DDD principles
 */
public class UserId extends ValueObject<String> {

  public UserId(String value) {
    super(value);
    validate(value);
  }

  /** ✅ IMPROVEMENT: Domain validation */
  private void validate(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw new IllegalArgumentException("User ID cannot be null or empty");
    }

    if (value.length() < 3) {
      throw new IllegalArgumentException("User ID must be at least 3 characters");
    }
  }

  /** ✅ IMPROVEMENT: Factory method for generating new IDs */
  public static UserId generate() {
    return new UserId(UUID.randomUUID().toString());
  }

  /** ✅ IMPROVEMENT: Factory method for creating from string */
  public static UserId of(String value) {
    return new UserId(value);
  }

  /** ✅ IMPROVEMENT: Get the string value */
  @Override
  public String getValue() {
    return super.getValue();
  }
}
