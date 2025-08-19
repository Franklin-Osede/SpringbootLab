package com.example.app.before.controller;

import com.example.app.before.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ❌ "FAT" CONTROLLER - BEFORE REFACTORING
 * 
 * IDENTIFIED PROBLEMS:
 * - More than 200 lines of code
 * - Business logic in controller
 * - Manual validations
 * - Inconsistent error handling
 * - Direct coupling with database
 * - Methods with multiple responsibilities
 * - Does not follow SOLID principles
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * ❌ PROBLEM: Method with multiple responsibilities
     * - Data validation
     * - Business logic
     * - Data transformation
     * - Error handling
     */
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody Map<String, Object> request) {
        try {
            // ❌ PROBLEM: Manual validation in controller
            if (request.get("email") == null || request.get("email").toString().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            
            if (request.get("name") == null || request.get("name").toString().isEmpty()) {
                return ResponseEntity.badRequest().body("Name is required");
            }
            
            if (request.get("password") == null || request.get("password").toString().isEmpty()) {
                return ResponseEntity.badRequest().body("Password is required");
            }

            // ❌ PROBLEM: Manual email validation
            String email = request.get("email").toString();
            if (!email.contains("@")) {
                return ResponseEntity.badRequest().body("Invalid email format");
            }

            // ❌ PROBLEM: Manual password validation
            String password = request.get("password").toString();
            if (password.length() < 6) {
                return ResponseEntity.badRequest().body("Password must be at least 6 characters");
            }

            // ❌ PROBLEM: Business logic in controller
            String name = request.get("name").toString();
            if (name.length() < 2) {
                return ResponseEntity.badRequest().body("Name must be at least 2 characters");
            }

            // ❌ PROBLEM: Manual data transformation
            Map<String, Object> userData = Map.of(
                "email", email,
                "name", name,
                "password", password,
                "status", "ACTIVE"
            );

            // ❌ PROBLEM: Direct service call without DTOs
            Map<String, Object> createdUser = userService.createUser(userData);
            
            return ResponseEntity.ok(createdUser);
            
        } catch (Exception e) {
            // ❌ PROBLEM: Generic error handling
            return ResponseEntity.internalServerError().body("Error creating user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with mixed business logic
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody Map<String, Object> request) {
        try {
            // ❌ PROBLEM: Manual validation
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID is required");
            }

            // ❌ PROBLEM: Business logic in controller
            if (request.containsKey("email")) {
                String email = request.get("email").toString();
                if (!email.contains("@")) {
                    return ResponseEntity.badRequest().body("Invalid email format");
                }
            }

            if (request.containsKey("name")) {
                String name = request.get("name").toString();
                if (name.length() < 2) {
                    return ResponseEntity.badRequest().body("Name must be at least 2 characters");
                }
            }

            // ❌ PROBLEM: Manual transformation
            Map<String, Object> updateData = Map.of(
                "id", id,
                "email", request.getOrDefault("email", ""),
                "name", request.getOrDefault("name", ""),
                "status", request.getOrDefault("status", "ACTIVE")
            );

            Map<String, Object> updatedUser = userService.updateUser(updateData);
            
            if (updatedUser == null) {
                return ResponseEntity.notFound().build();
            }
            
            return ResponseEntity.ok(updatedUser);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error updating user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with multiple responsibilities
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id) {
        try {
            // ❌ PROBLEM: Manual validation
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID is required");
            }

            // ❌ PROBLEM: Business logic in controller
            if (id.equals("admin")) {
                return ResponseEntity.status(403).body("Access denied for admin user");
            }

            Map<String, Object> user = userService.getUserById(id);
            
            if (user == null) {
                return ResponseEntity.notFound().build();
            }
            
            // ❌ PROBLEM: Manual transformation de respuesta
            Map<String, Object> response = Map.of(
                "id", user.get("id"),
                "email", user.get("email"),
                "name", user.get("name"),
                "status", user.get("status"),
                "createdAt", user.get("created_at"),
                "updatedAt", user.get("updated_at")
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with manual pagination logic
     */
    @GetMapping
    public ResponseEntity<?> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        
        try {
            // ❌ PROBLEM: Manual parameter validation
            if (page < 0) {
                return ResponseEntity.badRequest().body("Page must be non-negative");
            }
            
            if (size < 1 || size > 100) {
                return ResponseEntity.badRequest().body("Size must be between 1 and 100");
            }

            // ❌ PROBLEM: Business logic in controller
            if (status != null && !status.matches("ACTIVE|INACTIVE|SUSPENDED")) {
                return ResponseEntity.badRequest().body("Invalid status value");
            }

            // ❌ PROBLEM: Manual filter construction
            Map<String, Object> filters = Map.of(
                "page", page,
                "size", size,
                "status", status != null ? status : "ACTIVE",
                "search", search != null ? search : ""
            );

            List<Map<String, Object>> users = userService.getAllUsers(filters);
            
            // ❌ PROBLEM: Manual transformation de respuesta
            Map<String, Object> response = Map.of(
                "users", users,
                "page", page,
                "size", size,
                "total", users.size(),
                "hasNext", users.size() == size
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error retrieving users: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with complex business logic
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        try {
            // ❌ PROBLEM: Manual validation
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID is required");
            }

            // ❌ PROBLEM: Business logic in controller
            if (id.equals("admin")) {
                return ResponseEntity.status(403).body("Cannot delete admin user");
            }

            // ❌ PROBLEM: Manual existence verification
            Map<String, Object> existingUser = userService.getUserById(id);
            if (existingUser == null) {
                return ResponseEntity.notFound().build();
            }

            // ❌ PROBLEM: Business logic in controller
            String currentStatus = existingUser.get("status").toString();
            if ("DELETED".equals(currentStatus)) {
                return ResponseEntity.badRequest().body("User is already deleted");
            }

            boolean deleted = userService.deleteUser(id);
            
            if (!deleted) {
                return ResponseEntity.internalServerError().body("Failed to delete user");
            }
            
            return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deleting user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with specific business logic
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable String id) {
        try {
            // ❌ PROBLEM: Manual validation
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID is required");
            }

            // ❌ PROBLEM: Manual verification
            Map<String, Object> existingUser = userService.getUserById(id);
            if (existingUser == null) {
                return ResponseEntity.notFound().build();
            }

            // ❌ PROBLEM: Business logic in controller
            String currentStatus = existingUser.get("status").toString();
            if ("ACTIVE".equals(currentStatus)) {
                return ResponseEntity.badRequest().body("User is already active");
            }

            Map<String, Object> updateData = Map.of(
                "id", id,
                "status", "ACTIVE"
            );

            Map<String, Object> activatedUser = userService.updateUser(updateData);
            
            return ResponseEntity.ok(activatedUser);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error activating user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEM: Method with specific business logic
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable String id) {
        try {
            // ❌ PROBLEM: Manual validation
            if (id == null || id.isEmpty()) {
                return ResponseEntity.badRequest().body("User ID is required");
            }

            // ❌ PROBLEM: Manual verification
            Map<String, Object> existingUser = userService.getUserById(id);
            if (existingUser == null) {
                return ResponseEntity.notFound().build();
            }

            // ❌ PROBLEM: Business logic in controller
            String currentStatus = existingUser.get("status").toString();
            if ("INACTIVE".equals(currentStatus)) {
                return ResponseEntity.badRequest().body("User is already inactive");
            }

            Map<String, Object> updateData = Map.of(
                "id", id,
                "status", "INACTIVE"
            );

            Map<String, Object> deactivatedUser = userService.updateUser(updateData);
            
            return ResponseEntity.ok(deactivatedUser);
            
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error deactivating user: " + e.getMessage());
        }
    }
}
