package com.example.app.before.repository;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * ❌ REPOSITORIO SIMPLE - ANTES DEL REFACTORING
 * 
 * PROBLEMAS IDENTIFICADOS:
 * - Almacenamiento en memoria (no persistente)
 * - Sin transacciones
 * - Sin validaciones de integridad
 * - Consultas manuales sin optimización
 * - Sin manejo de concurrencia
 * - Acoplamiento directo con la lógica de negocio
 */
@Repository
public class UserRepository {

    // ❌ PROBLEMA: Almacenamiento en memoria (se pierde al reiniciar)
    private final Map<String, Map<String, Object>> users = new ConcurrentHashMap<>();

    /**
     * ❌ PROBLEMA: Método sin validaciones de integridad
     */
    public Map<String, Object> save(Map<String, Object> user) {
        try {
            String id = user.get("id").toString();
            
            // ❌ PROBLEMA: Sin validaciones de integridad
            users.put(id, new HashMap<>(user));
            
            return users.get(id);
            
        } catch (Exception e) {
            throw new RuntimeException("Error saving user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEMA: Método sin optimización de consulta
     */
    public Map<String, Object> findById(String id) {
        try {
            // ❌ PROBLEMA: Consulta manual sin optimización
            return users.get(id);
            
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by id: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEMA: Método con consulta manual ineficiente
     */
    public List<Map<String, Object>> findByEmail(String email) {
        try {
            // ❌ PROBLEMA: Consulta manual sin índice
            return users.values().stream()
                    .filter(user -> email.equals(user.get("email")))
                    .collect(Collectors.toList());
            
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEMA: Método con paginación manual ineficiente
     */
    public List<Map<String, Object>> findAll(Map<String, Object> filters, int page, int size) {
        try {
            // ❌ PROBLEMA: Filtrado manual sin optimización
            List<Map<String, Object>> filteredUsers = users.values().stream()
                    .filter(user -> {
                        // ❌ PROBLEMA: Filtrado manual por status
                        if (filters.containsKey("status")) {
                            String filterStatus = filters.get("status").toString();
                            String userStatus = user.get("status").toString();
                            if (!filterStatus.equals(userStatus)) {
                                return false;
                            }
                        }
                        
                        // ❌ PROBLEMA: Filtrado manual por búsqueda
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

            // ❌ PROBLEMA: Paginación manual
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
     * ❌ PROBLEMA: Método sin transacciones
     */
    public boolean delete(String id) {
        try {
            // ❌ PROBLEMA: Sin transacciones
            return users.remove(id) != null;
            
        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEMA: Método con conteo manual ineficiente
     */
    public long count() {
        try {
            // ❌ PROBLEMA: Conteo manual sin optimización
            return users.size();
            
        } catch (Exception e) {
            throw new RuntimeException("Error counting users: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEMA: Método con búsqueda manual ineficiente
     */
    public List<Map<String, Object>> findByStatus(String status) {
        try {
            // ❌ PROBLEMA: Búsqueda manual sin índice
            return users.values().stream()
                    .filter(user -> status.equals(user.get("status")))
                    .collect(Collectors.toList());
            
        } catch (Exception e) {
            throw new RuntimeException("Error finding users by status: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEMA: Método con búsqueda manual ineficiente
     */
    public List<Map<String, Object>> findByNameContaining(String name) {
        try {
            // ❌ PROBLEMA: Búsqueda manual sin índice
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
     * ❌ PROBLEMA: Método con actualización manual sin validaciones
     */
    public Map<String, Object> update(String id, Map<String, Object> updates) {
        try {
            Map<String, Object> existingUser = users.get(id);
            if (existingUser == null) {
                return null;
            }

            // ❌ PROBLEMA: Actualización manual sin validaciones
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
     * ❌ PROBLEMA: Método con limpieza manual sin transacciones
     */
    public void deleteAll() {
        try {
            // ❌ PROBLEMA: Sin transacciones
            users.clear();
            
        } catch (Exception e) {
            throw new RuntimeException("Error deleting all users: " + e.getMessage());
        }
    }
}
