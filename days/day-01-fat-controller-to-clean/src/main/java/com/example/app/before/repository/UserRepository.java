package com.example.app.before.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ❌ SIMPLE REPOSITORY - BEFORE REFACTORING
 * 
 * IDENTIFIED PROBLEMS:
 * - In-memory storage (not persistent)
 * - No transactions
 * - No integrity validations
 * - Manual queries without optimization
 * - No concurrency management
 * - Direct coupling with business logic
 */
@Repository
public class UserRepository {

    // ❌ PROBLEM: In-memory storage (lost on restart)
    private final Map<String, Map<String, Object>> users = new ConcurrentHashMap<>();

    /**
     * ❌ PROBLEM: Method without integrity validations
     */
    public Map<String, Object> save(Map<String, Object> user) {
        try {
            String id = user.get("id").toString();
            
            // ❌ PROBLEM: No integrity validations
            users.put(id, new HashMap<>(user));
            
            return users.get(id);
            
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method without query optimization
     */
    public Map<String, Object> findById(String id) {
        try {
            // ❌ PROBLEM: Manual query without optimization
            return users.get(id);
            
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by id: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with inefficient manual query
     */
    public List<Map<String, Object>> findByEmail(String email) {
        try {
            // ❌ PROBLEM: Manual query without index
            return users.values().stream()
                    .filter(user -> email.equals(user.get("email")))
                    .collect(Collectors.toList());
            
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with inefficient manual pagination
     */
    public List<Map<String, Object>> findAll(Map<String, Object> filters, int page, int size) {
        try {
            // ❌ PROBLEM: Manual filtering without optimization
            List<Map<String, Object>> filteredUsers = users.values().stream()
                    .filter(user -> {
                        // ❌ PROBLEM: Manual filtering by status
                        if (filters.containsKey("status")) {
                            String filterStatus = filters.get("status").toString();
                            String userStatus = user.get("status").toString();
                            if (!filterStatus.equals(userStatus)) {
                                return false;
                            }
                        }
                        
                        // ❌ PROBLEM: Manual filtering by search
                        if (filters.containsKey("search")) {
                            String searchTerm = filters.get("search").toString().toLowerCase();
                            String userName = user.get("name").toString().toLowerCase();
                            String userEmail = user.get("email").toString().toLowerCase();
                            
                            if (!userName.contains(searchTerm) && !userEmail.contains(searchTerm)) {
                                return false;
                            }
                        }
                        
                        return true;
                    })
                    .collect(Collectors.toList());

            // ❌ PROBLEM: Manual pagination
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, filteredUsers.size());
            
            if (startIndex >= filteredUsers.size()) {
                return new ArrayList<>();
            }
            
            return filteredUsers.subList(startIndex, endIndex);
            
        } catch (Exception e) {
            throw new RuntimeException("Error finding all users: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method without transactions
     */
    public boolean delete(String id) {
        try {
            // ❌ PROBLEM: No transactions
            return users.remove(id) != null;
            
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with inefficient manual count
     */
    public long count() {
        try {
            // ❌ PROBLEM: Manual count without optimization
            return users.size();
            
        } catch (Exception e) {
            throw new RuntimeException("Error counting users: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with inefficient manual search
     */
    public List<Map<String, Object>> findByStatus(String status) {
        try {
            // ❌ PROBLEM: Manual search without index
            return users.values().stream()
                    .filter(user -> status.equals(user.get("status")))
                    .collect(Collectors.toList());
            
        } catch (Exception e) {
            throw new RuntimeException("Error finding users by status: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with inefficient manual search
     */
    public List<Map<String, Object>> findByNameContaining(String name) {
        try {
            // ❌ PROBLEM: Manual search without index
            return users.values().stream()
                    .filter(user -> {
                        String userName = user.get("name").toString().toLowerCase();
                        return userName.contains(name.toLowerCase());
                    })
                    .collect(Collectors.toList());
            
        } catch (Exception e) {
            throw new RuntimeException("Error finding users by name: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with manual update without validations
     */
    public Map<String, Object> update(String id, Map<String, Object> updates) {
        try {
            Map<String, Object> existingUser = users.get(id);
            if (existingUser == null) {
                return null;
            }

            // ❌ PROBLEM: Manual update without validations
            Map<String, Object> updatedUser = new HashMap<>(existingUser);
            updatedUser.putAll(updates);
            updatedUser.put("updated_at", LocalDateTime.now());
            
            users.put(id, updatedUser);
            
            return updatedUser;
            
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with manual cleanup without transactions
     */
    public void deleteAll() {
        try {
            // ❌ PROBLEM: No transactions
            users.clear();
            
        } catch (Exception e) {
            throw new RuntimeException("Error deleting all users: " + e.getMessage());
        }
    }
}
