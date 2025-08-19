package com.example.app.after.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import com.example.app.after.domain.User;
import com.example.app.after.domain.valueobjects.UserId;

/**
 * ✅ USER REPOSITORY INTERFACE - AFTER REFACTORING
 *
 * <p>IMPROVEMENTS IMPLEMENTED: - Clean repository interface - Domain object usage - Optional return
 * types - Pagination support - Search capabilities - Follows DDD principles
 */
public interface UserRepository {

  /** ✅ IMPROVEMENT: Save user with domain object */
  User save(User user);

  /** ✅ IMPROVEMENT: Find by ID with Optional */
  Optional<User> findById(UserId id);

  /** ✅ IMPROVEMENT: Find by email with Optional */
  Optional<User> findByEmail(String email);

  /** ✅ IMPROVEMENT: Find all with pagination and filters */
  List<User> findAll(int page, int size, String status, String search);

  /** ✅ IMPROVEMENT: Check if email exists */
  boolean existsByEmail(String email);

  /** ✅ IMPROVEMENT: Count users with filters */
  long count(String status, String search);

  /** ✅ IMPROVEMENT: Delete user */
  void delete(UserId id);
}
