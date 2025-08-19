package com.example.app.after.infrastructure.persistence;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.example.app.after.domain.User;
import com.example.app.after.domain.valueobjects.UserId;

/**
 * ✅ IN-MEMORY USER REPOSITORY - AFTER REFACTORING
 *
 * <p>IMPROVEMENTS IMPLEMENTED: - Clean interface implementation - In-memory storage for Day 1 -
 * Thread-safe operations - Domain object usage - No database coupling
 */
@Repository
public class InMemoryUserRepository implements UserRepository {

  private final Map<String, User> users = new ConcurrentHashMap<>();

  @Override
  public User save(User user) {
    users.put(user.getId().getValue(), user);
    return user;
  }

  @Override
  public Optional<User> findById(UserId id) {
    return Optional.ofNullable(users.get(id.getValue()));
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return users.values().stream()
        .filter(user -> user.getEmail().getValue().equals(email))
        .findFirst();
  }

  @Override
  public List<User> findAll(int page, int size, String status, String search) {
    return users.values().stream()
        .filter(user -> filterByStatus(user, status))
        .filter(user -> filterBySearch(user, search))
        .skip((long) page * size)
        .limit(size)
        .collect(Collectors.toList());
  }

  @Override
  public boolean existsByEmail(String email) {
    return users.values().stream().anyMatch(user -> user.getEmail().getValue().equals(email));
  }

  @Override
  public long count(String status, String search) {
    return users.values().stream()
        .filter(user -> filterByStatus(user, status))
        .filter(user -> filterBySearch(user, search))
        .count();
  }

  @Override
  public void delete(UserId id) {
    users.remove(id.getValue());
  }

  private boolean filterByStatus(User user, String status) {
    if (status == null || status.trim().isEmpty()) {
      return true;
    }
    return user.getStatus().name().equals(status);
  }

  private boolean filterBySearch(User user, String search) {
    if (search == null || search.trim().isEmpty()) {
      return true;
    }
    String searchLower = search.toLowerCase();
    return user.getName().toLowerCase().contains(searchLower)
        || user.getEmail().getValue().toLowerCase().contains(searchLower);
  }
}
