package com.example.app.before.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.app.before.service.UserService;

/**
 * ❌ CONTROLADOR "GORDO" - ANTES DEL REFACTORING
 *
 * <p>PROBLEMAS IDENTIFICADOS: - Más de 200 líneas de código - Lógica de negocio en el controlador -
 * Validaciones manuales - Manejo de errores inconsistente - Acoplamiento directo con la base de
 * datos - Métodos con múltiples responsabilidades - No sigue principios SOLID
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

  @Autowired private UserService userService;

  /**
   * ❌ PROBLEMA: Método con múltiples responsabilidades - Validación de datos - Lógica de negocio -
   * Transformación de datos - Manejo de errores
   */
  @PostMapping
  public ResponseEntity<?> createUser(@RequestBody Map<String, Object> request) {
    try {
      // ❌ PROBLEMA: Validación manual en el controlador
      if (request.get("email") == null || request.get("email").toString().isEmpty()) {
        return ResponseEntity.badRequest().body("Email is required");
      }

      if (request.get("name") == null || request.get("name").toString().isEmpty()) {
        return ResponseEntity.badRequest().body("Name is required");
      }

      if (request.get("password") == null || request.get("password").toString().isEmpty()) {
        return ResponseEntity.badRequest().body("Password is required");
      }

      // ❌ PROBLEMA: Validación de email manual
      String email = request.get("email").toString();
      if (!email.contains("@")) {
        return ResponseEntity.badRequest().body("Invalid email format");
      }

      // ❌ PROBLEMA: Validación de password manual
      String password = request.get("password").toString();
      if (password.length() < 6) {
        return ResponseEntity.badRequest().body("Password must be at least 6 characters");
      }

      // ❌ PROBLEMA: Lógica de negocio en el controlador
      String name = request.get("name").toString();
      if (name.length() < 2) {
        return ResponseEntity.badRequest().body("Name must be at least 2 characters");
      }

      // ❌ PROBLEMA: Transformación manual de datos
      Map<String, Object> userData =
          Map.of(
              "email", email,
              "name", name,
              "password", password,
              "status", "ACTIVE");

      // ❌ PROBLEMA: Llamada directa al servicio sin DTOs
      Map<String, Object> createdUser = userService.createUser(userData);

      return ResponseEntity.ok(createdUser);

    } catch (Exception e) {
      // ❌ PROBLEMA: Manejo genérico de errores
      return ResponseEntity.internalServerError().body("Error creating user: " + e.getMessage());
    }
  }

  /** ❌ PROBLEMA: Método con lógica de negocio mezclada */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateUser(
      @PathVariable String id, @RequestBody Map<String, Object> request) {
    try {
      // ❌ PROBLEMA: Validación manual
      if (id == null || id.isEmpty()) {
        return ResponseEntity.badRequest().body("User ID is required");
      }

      // ❌ PROBLEMA: Lógica de negocio en el controlador
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

      // ❌ PROBLEMA: Transformación manual
      Map<String, Object> updateData =
          Map.of(
              "id", id,
              "email", request.getOrDefault("email", ""),
              "name", request.getOrDefault("name", ""),
              "status", request.getOrDefault("status", "ACTIVE"));

      Map<String, Object> updatedUser = userService.updateUser(updateData);

      if (updatedUser == null) {
        return ResponseEntity.notFound().build();
      }

      return ResponseEntity.ok(updatedUser);

    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error updating user: " + e.getMessage());
    }
  }

  /** ❌ PROBLEMA: Método con múltiples responsabilidades */
  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable String id) {
    try {
      // ❌ PROBLEMA: Validación manual
      if (id == null || id.isEmpty()) {
        return ResponseEntity.badRequest().body("User ID is required");
      }

      // ❌ PROBLEMA: Lógica de negocio en el controlador
      if (id.equals("admin")) {
        return ResponseEntity.status(403).body("Access denied for admin user");
      }

      Map<String, Object> user = userService.getUserById(id);

      if (user == null) {
        return ResponseEntity.notFound().build();
      }

      // ❌ PROBLEMA: Transformación manual de respuesta
      Map<String, Object> response =
          Map.of(
              "id", user.get("id"),
              "email", user.get("email"),
              "name", user.get("name"),
              "status", user.get("status"),
              "createdAt", user.get("created_at"),
              "updatedAt", user.get("updated_at"));

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error retrieving user: " + e.getMessage());
    }
  }

  /** ❌ PROBLEMA: Método con lógica de paginación manual */
  @GetMapping
  public ResponseEntity<?> getAllUsers(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size,
      @RequestParam(required = false) String status,
      @RequestParam(required = false) String search) {

    try {
      // ❌ PROBLEMA: Validación manual de parámetros
      if (page < 0) {
        return ResponseEntity.badRequest().body("Page must be non-negative");
      }

      if (size < 1 || size > 100) {
        return ResponseEntity.badRequest().body("Size must be between 1 and 100");
      }

      // ❌ PROBLEMA: Lógica de negocio en el controlador
      if (status != null && !status.matches("ACTIVE|INACTIVE|SUSPENDED")) {
        return ResponseEntity.badRequest().body("Invalid status value");
      }

      // ❌ PROBLEMA: Construcción manual de filtros
      Map<String, Object> filters =
          Map.of(
              "page",
              page,
              "size",
              size,
              "status",
              status != null ? status : "ACTIVE",
              "search",
              search != null ? search : "");

      List<Map<String, Object>> users = userService.getAllUsers(filters);

      // ❌ PROBLEMA: Transformación manual de respuesta
      Map<String, Object> response =
          Map.of(
              "users", users,
              "page", page,
              "size", size,
              "total", users.size(),
              "hasNext", users.size() == size);

      return ResponseEntity.ok(response);

    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error retrieving users: " + e.getMessage());
    }
  }

  /** ❌ PROBLEMA: Método con lógica de negocio compleja */
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable String id) {
    try {
      // ❌ PROBLEMA: Validación manual
      if (id == null || id.isEmpty()) {
        return ResponseEntity.badRequest().body("User ID is required");
      }

      // ❌ PROBLEMA: Lógica de negocio en el controlador
      if (id.equals("admin")) {
        return ResponseEntity.status(403).body("Cannot delete admin user");
      }

      // ❌ PROBLEMA: Verificación manual de existencia
      Map<String, Object> existingUser = userService.getUserById(id);
      if (existingUser == null) {
        return ResponseEntity.notFound().build();
      }

      // ❌ PROBLEMA: Lógica de negocio en el controlador
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

  /** ❌ PROBLEMA: Método con lógica de negocio específica */
  @PatchMapping("/{id}/activate")
  public ResponseEntity<?> activateUser(@PathVariable String id) {
    try {
      // ❌ PROBLEMA: Validación manual
      if (id == null || id.isEmpty()) {
        return ResponseEntity.badRequest().body("User ID is required");
      }

      // ❌ PROBLEMA: Verificación manual
      Map<String, Object> existingUser = userService.getUserById(id);
      if (existingUser == null) {
        return ResponseEntity.notFound().build();
      }

      // ❌ PROBLEMA: Lógica de negocio en el controlador
      String currentStatus = existingUser.get("status").toString();
      if ("ACTIVE".equals(currentStatus)) {
        return ResponseEntity.badRequest().body("User is already active");
      }

      Map<String, Object> updateData = Map.of("id", id, "status", "ACTIVE");

      Map<String, Object> activatedUser = userService.updateUser(updateData);

      return ResponseEntity.ok(activatedUser);

    } catch (Exception e) {
      return ResponseEntity.internalServerError().body("Error activating user: " + e.getMessage());
    }
  }

  /** ❌ PROBLEMA: Método con lógica de negocio específica */
  @PatchMapping("/{id}/deactivate")
  public ResponseEntity<?> deactivateUser(@PathVariable String id) {
    try {
      // ❌ PROBLEMA: Validación manual
      if (id == null || id.isEmpty()) {
        return ResponseEntity.badRequest().body("User ID is required");
      }

      // ❌ PROBLEMA: Verificación manual
      Map<String, Object> existingUser = userService.getUserById(id);
      if (existingUser == null) {
        return ResponseEntity.notFound().build();
      }

      // ❌ PROBLEMA: Lógica de negocio en el controlador
      String currentStatus = existingUser.get("status").toString();
      if ("INACTIVE".equals(currentStatus)) {
        return ResponseEntity.badRequest().body("User is already inactive");
      }

      Map<String, Object> updateData = Map.of("id", id, "status", "INACTIVE");

      Map<String, Object> deactivatedUser = userService.updateUser(updateData);

      return ResponseEntity.ok(deactivatedUser);

    } catch (Exception e) {
      return ResponseEntity.internalServerError()
          .body("Error deactivating user: " + e.getMessage());
    }
  }
}
