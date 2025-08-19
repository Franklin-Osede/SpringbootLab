package com.example.app.before.service;

import com.example.app.before.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ❌ "FAT" SERVICE - BEFORE REFACTORING
 * 
 * IDENTIFIED PROBLEMS:
 * - More than 300 lines of code
 * - Business logic mixed with data access
 * - Duplicated validations
 * - Inconsistent error handling
 * - Direct coupling with database
 * - Methods with multiple responsibilities
 * - Does not follow SOLID principles
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * ❌ PROBLEM: Method with multiple responsibilities
     * - Data validation
     * - Business logic
     * - Data transformation
     * - Database access
     */
    public Map<String, Object> createUser(Map<String, Object> userData) {
        try {
            // ❌ PROBLEM: Duplicated validation (already done in controller)
            String email = userData.get("email").toString();
            String name = userData.get("name").toString();
            String password = userData.get("password").toString();

            // ❌ PROBLEM: Duplicated email validation
            if (!email.contains("@") || !email.contains(".")) {
                throw new RuntimeException("Invalid email format");
            }

            // ❌ PROBLEM: Duplicated password validation
            if (password.length() < 6) {
                throw new RuntimeException("Password must be at least 6 characters");
            }

            // ❌ PROBLEM: Duplicated name validation
            if (name.length() < 2) {
                throw new RuntimeException("Name must be at least 2 characters");
            }

            // ❌ PROBLEM: Unique email verification (business logic)
            List<Map<String, Object>> existingUsers = userRepository.findByEmail(email);
            if (!existingUsers.isEmpty()) {
                throw new RuntimeException("Email already exists");
            }

            // ❌ PROBLEM: Manual ID generation
            String userId = UUID.randomUUID().toString();

            // ❌ PROBLEM: Manual password hash (without salt)
            String hashedPassword = password.hashCode() + "";

            // ❌ PROBLEM: Manual data construction
            Map<String, Object> userToSave = new HashMap<>();
            userToSave.put("id", userId);
            userToSave.put("email", email);
            userToSave.put("name", name);
            userToSave.put("password", hashedPassword);
            userToSave.put("status", userData.getOrDefault("status", "ACTIVE"));
            userToSave.put("created_at", LocalDateTime.now());
            userToSave.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEM: Direct repository call
            Map<String, Object> savedUser = userRepository.save(userToSave);

            // ❌ PROBLEM: Manual response transformation
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedUser.get("id"));
            response.put("email", savedUser.get("email"));
            response.put("name", savedUser.get("name"));
            response.put("status", savedUser.get("status"));
            response.put("createdAt", savedUser.get("created_at"));
            response.put("updatedAt", savedUser.get("updated_at"));

            return response;

        } catch (Exception e) {
            // ❌ PROBLEM: Generic error handling
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with mixed business logic
     */
    public Map<String, Object> updateUser(Map<String, Object> updateData) {
        try {
            String userId = updateData.get("id").toString();

            // ❌ PROBLEM: Manual existence verification
            Map<String, Object> existingUser = userRepository.findById(userId);
            if (existingUser == null) {
                return null;
            }

            // ❌ PROBLEM: Email validation if provided
            if (updateData.containsKey("email")) {
                String email = updateData.get("email").toString();
                if (!email.contains("@") || !email.contains(".")) {
                    throw new RuntimeException("Invalid email format");
                }

                // ❌ PROBLEM: Unique email verification
                List<Map<String, Object>> usersWithEmail = userRepository.findByEmail(email);
                for (Map<String, Object> user : usersWithEmail) {
                    if (!user.get("id").equals(userId)) {
                        throw new RuntimeException("Email already exists");
                    }
                }
            }

            // ❌ PROBLEM: Name validation if provided
            if (updateData.containsKey("name")) {
                String name = updateData.get("name").toString();
                if (name.length() < 2) {
                    throw new RuntimeException("Name must be at least 2 characters");
                }
            }

            // ❌ PROBLEM: Status validation if provided
            if (updateData.containsKey("status")) {
                String status = updateData.get("status").toString();
                if (!Arrays.asList("ACTIVE", "INACTIVE", "SUSPENDED", "DELETED").contains(status)) {
                    throw new RuntimeException("Invalid status value");
                }
            }

            // ❌ PROBLEM: Manual data construction actualizados
            Map<String, Object> userToUpdate = new HashMap<>(existingUser);
            for (Map.Entry<String, Object> entry : updateData.entrySet()) {
                if (!entry.getKey().equals("id")) {
                    userToUpdate.put(entry.getKey(), entry.getValue());
                }
            }
            userToUpdate.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEM: Direct repository call
            Map<String, Object> updatedUser = userRepository.save(userToUpdate);

            // ❌ PROBLEM: Manual response transformation
            Map<String, Object> response = new HashMap<>();
            response.put("id", updatedUser.get("id"));
            response.put("email", updatedUser.get("email"));
            response.put("name", updatedUser.get("name"));
            response.put("status", updatedUser.get("status"));
            response.put("createdAt", updatedUser.get("created_at"));
            response.put("updatedAt", updatedUser.get("updated_at"));

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with manual transformation
     */
    public Map<String, Object> getUserById(String id) {
        try {
            // ❌ PROBLEM: Direct repository call
            Map<String, Object> user = userRepository.findById(id);
            
            if (user == null) {
                return null;
            }

            // ❌ PROBLEM: Manual response transformation
            Map<String, Object> response = new HashMap<>();
            response.put("id", user.get("id"));
            response.put("email", user.get("email"));
            response.put("name", user.get("name"));
            response.put("status", user.get("status"));
            response.put("createdAt", user.get("created_at"));
            response.put("updatedAt", user.get("updated_at"));

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with manual pagination logic
     */
    public List<Map<String, Object>> getAllUsers(Map<String, Object> filters) {
        try {
            int page = (Integer) filters.get("page");
            int size = (Integer) filters.get("size");
            String status = (String) filters.get("status");
            String search = (String) filters.get("search");

            // ❌ PROBLEM: Manual filter construction
            Map<String, Object> queryFilters = new HashMap<>();
            if (status != null && !status.isEmpty()) {
                queryFilters.put("status", status);
            }
            if (search != null && !search.isEmpty()) {
                queryFilters.put("search", search);
            }

            // ❌ PROBLEM: Direct repository call
            List<Map<String, Object>> users = userRepository.findAll(queryFilters, page, size);

            // ❌ PROBLEM: Manual response transformation
            List<Map<String, Object>> response = new ArrayList<>();
            for (Map<String, Object> user : users) {
                Map<String, Object> userResponse = new HashMap<>();
                userResponse.put("id", user.get("id"));
                userResponse.put("email", user.get("email"));
                userResponse.put("name", user.get("name"));
                userResponse.put("status", user.get("status"));
                userResponse.put("createdAt", user.get("created_at"));
                userResponse.put("updatedAt", user.get("updated_at"));
                response.add(userResponse);
            }

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error retrieving users: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with specific business logic
     */
    public boolean deleteUser(String id) {
        try {
            // ❌ PROBLEM: Manual existence verification
            Map<String, Object> existingUser = userRepository.findById(id);
            if (existingUser == null) {
                return false;
            }

            // ❌ PROBLEM: Business logic in service
            String currentStatus = existingUser.get("status").toString();
            if ("DELETED".equals(currentStatus)) {
                return false;
            }

            // ❌ PROBLEM: Manual soft delete
            Map<String, Object> userToDelete = new HashMap<>(existingUser);
            userToDelete.put("status", "DELETED");
            userToDelete.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEM: Direct repository call
            userRepository.save(userToDelete);

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with specific business logic
     */
    public Map<String, Object> activateUser(String id) {
        try {
            // ❌ PROBLEM: Manual existence verification
            Map<String, Object> existingUser = userRepository.findById(id);
            if (existingUser == null) {
                return null;
            }

            // ❌ PROBLEM: Business logic in service
            String currentStatus = existingUser.get("status").toString();
            if ("ACTIVE".equals(currentStatus)) {
                throw new RuntimeException("User is already active");
            }

            // ❌ PROBLEM: Manual data construction
            Map<String, Object> userToActivate = new HashMap<>(existingUser);
            userToActivate.put("status", "ACTIVE");
            userToActivate.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEM: Direct repository call
            Map<String, Object> activatedUser = userRepository.save(userToActivate);

            // ❌ PROBLEM: Manual response transformation
            Map<String, Object> response = new HashMap<>();
            response.put("id", activatedUser.get("id"));
            response.put("email", activatedUser.get("email"));
            response.put("name", activatedUser.get("name"));
            response.put("status", activatedUser.get("status"));
            response.put("createdAt", activatedUser.get("created_at"));
            response.put("updatedAt", activatedUser.get("updated_at"));

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error activating user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with specific business logic
     */
    public Map<String, Object> deactivateUser(String id) {
        try {
            // ❌ PROBLEM: Manual existence verification
            Map<String, Object> existingUser = userRepository.findById(id);
            if (existingUser == null) {
                return null;
            }

            // ❌ PROBLEM: Business logic in service
            String currentStatus = existingUser.get("status").toString();
            if ("INACTIVE".equals(currentStatus)) {
                throw new RuntimeException("User is already inactive");
            }

            // ❌ PROBLEM: Manual data construction
            Map<String, Object> userToDeactivate = new HashMap<>(existingUser);
            userToDeactivate.put("status", "INACTIVE");
            userToDeactivate.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEM: Direct repository call
            Map<String, Object> deactivatedUser = userRepository.save(userToDeactivate);

            // ❌ PROBLEM: Manual response transformation
            Map<String, Object> response = new HashMap<>();
            response.put("id", deactivatedUser.get("id"));
            response.put("email", deactivatedUser.get("email"));
            response.put("name", deactivatedUser.get("name"));
            response.put("status", deactivatedUser.get("status"));
            response.put("createdAt", deactivatedUser.get("created_at"));
            response.put("updatedAt", deactivatedUser.get("updated_at"));

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Error deactivating user: " + e.getMessage());
        }
    }
}
