package com.example.app.after.infrastructure.web;

import com.example.app.after.application.dto.CreateUserRequest;
import com.example.app.after.application.dto.UpdateUserRequest;
import com.example.app.after.application.dto.UserResponse;
import com.example.app.after.application.service.UserApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ✅ CONTROLADOR LIMPIO - DESPUÉS DEL REFACTORING
 * 
 * MEJORAS IMPLEMENTADAS:
 * - Menos de 50 líneas de código
 * - Solo responsabilidad de HTTP
 * - Validaciones automáticas con @Valid
 * - Manejo de errores centralizado
 * - Inyección por constructor
 * - Métodos con una sola responsabilidad
 * - Sigue principios SOLID
 */
@RestController
@RequestMapping("/api/v2/users")
public class UserController {

    private final UserApplicationService userApplicationService;

    public UserController(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    /**
     * ✅ MEJORA: Método con una sola responsabilidad
     */
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@Valid @RequestBody CreateUserRequest request) {
        UserResponse user = userApplicationService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    /**
     * ✅ MEJORA: Método con una sola responsabilidad
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @PathVariable String id,
            @Valid @RequestBody UpdateUserRequest request) {
        UserResponse user = userApplicationService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }

    /**
     * ✅ MEJORA: Método con una sola responsabilidad
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable String id) {
        UserResponse user = userApplicationService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * ✅ MEJORA: Método con una sola responsabilidad
     */
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        
        List<UserResponse> users = userApplicationService.getAllUsers(page, size, status, search);
        return ResponseEntity.ok(users);
    }

    /**
     * ✅ MEJORA: Método con una sola responsabilidad
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userApplicationService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * ✅ MEJORA: Método con una sola responsabilidad
     */
    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateUser(@PathVariable String id) {
        UserResponse user = userApplicationService.activateUser(id);
        return ResponseEntity.ok(user);
    }

    /**
     * ✅ MEJORA: Método con una sola responsabilidad
     */
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<UserResponse> deactivateUser(@PathVariable String id) {
        UserResponse user = userApplicationService.deactivateUser(id);
        return ResponseEntity.ok(user);
    }
}
