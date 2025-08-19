package com.example.app.after.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

/**
 * ✅ DTO para actualizar usuarios - DESPUÉS DEL REFACTORING
 * 
 * MEJORAS IMPLEMENTADAS:
 * - Validaciones opcionales con Bean Validation
 * - Inmutabilidad
 * - Documentación clara
 * - Separación de responsabilidades
 */
public record UpdateUserRequest(
    
    @Email(message = "Invalid email format")
    String email,
    
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    String name,
    
    String status
) {
    
    /**
     * ✅ MEJORA: Validación de dominio en el DTO
     */
    public UpdateUserRequest {
        if (email != null && email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty if provided");
        }
        
        if (name != null && name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty if provided");
        }
        
        if (status != null && !isValidStatus(status)) {
            throw new IllegalArgumentException("Invalid status value: " + status);
        }
    }
    
    /**
     * ✅ MEJORA: Método de validación de status
     */
    private static boolean isValidStatus(String status) {
        return status != null && (
            "ACTIVE".equals(status) ||
            "INACTIVE".equals(status) ||
            "SUSPENDED".equals(status) ||
            "DELETED".equals(status)
        );
    }
    
    /**
     * ✅ MEJORA: Método para verificar si hay cambios
     */
    public boolean hasChanges() {
        return email != null || name != null || status != null;
    }
}
