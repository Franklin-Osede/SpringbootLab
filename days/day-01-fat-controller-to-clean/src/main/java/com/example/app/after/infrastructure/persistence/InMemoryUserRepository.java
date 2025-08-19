package com.example.app.after.infrastructure.persistence;

import com.example.app.after.domain.User;
import com.example.app.after.domain.valueobjects.UserId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ✅ IN-MEMORY USER REPOSITORY IMPLEMENTATION - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Clean repository implementation
 * - Domain object usage
 * - Thread-safe storage
 * - Proper error handling
 * - Follows DDD principles
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
        return users.values().stream()
            .anyMatch(user -> user.getEmail().getValue().equals(email));
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
        return user.getName().toLowerCase().contains(searchLower) ||
               user.getEmail().getValue().toLowerCase().contains(searchLower);
    }
}
