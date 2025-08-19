package com.example.app.before.service;

import com.example.app.before.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

/**
 * ❌ SERVICIO "GORDO" - ANTES DEL REFACTORING
 * 
 * PROBLEMAS IDENTIFICADOS:
 * - Más de 300 líneas de código
 * - Lógica de negocio mezclada con acceso a datos
 * - Validaciones duplicadas
 * - Manejo de errores inconsistente
 * - Acoplamiento directo con la base de datos
 * - Métodos con múltiples responsabilidades
 * - No sigue principios SOLID
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * ❌ PROBLEMA: Método con múltiples responsabilidades
     * - Validación de datos
     * - Lógica de negocio
     * - Transformación de datos
     * - Acceso a base de datos
     */
    public Map<String, Object> createUser(Map<String, Object> userData) {
        try {
            // ❌ PROBLEMA: Validación duplicada (ya se hizo en el controlador)
            String email = userData.get("email").toString();
            String name = userData.get("name").toString();
            String password = userData.get("password").toString();

            // ❌ PROBLEMA: Validación de email duplicada
            if (!email.contains("@") || !email.contains(".")) {
                throw new RuntimeException("Invalid email format");
            }

            // ❌ PROBLEMA: Validación de password duplicada
            if (password.length() < 6) {
                throw new RuntimeException("Password must be at least 6 characters");
            }

            // ❌ PROBLEMA: Validación de nombre duplicada
            if (name.length() < 2) {
                throw new RuntimeException("Name must be at least 2 characters");
            }

            // ❌ PROBLEMA: Verificación de email único (lógica de negocio)
            List<Map<String, Object>> existingUsers = userRepository.findByEmail(email);
            if (!existingUsers.isEmpty()) {
                throw new RuntimeException("Email already exists");
            }

            // ❌ PROBLEMA: Generación manual de ID
            String userId = UUID.randomUUID().toString();

            // ❌ PROBLEMA: Hash manual de password (sin salt)
            String hashedPassword = password.hashCode() + "";

            // ❌ PROBLEMA: Construcción manual de datos
            Map<String, Object> userToSave = new HashMap<>();
            userToSave.put("id", userId);
            userToSave.put("email", email);
            userToSave.put("name", name);
            userToSave.put("password", hashedPassword);
            userToSave.put("status", userData.getOrDefault("status", "ACTIVE"));
            userToSave.put("created_at", LocalDateTime.now());
            userToSave.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEMA: Llamada directa al repositorio
            Map<String, Object> savedUser = userRepository.save(userToSave);

            // ❌ PROBLEMA: Transformación manual de respuesta
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedUser.get("id"));
            response.put("email", savedUser.get("email"));
            response.put("name", savedUser.get("name"));
            response.put("status", savedUser.get("status"));
            response.put("createdAt", savedUser.get("created_at"));
            response.put("updatedAt", savedUser.get("updated_at"));

            return response;

        } catch (Exception e) {
            // ❌ PROBLEMA: Manejo genérico de errores
            throw new RuntimeException("Error creating user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEMA: Método con lógica de negocio mezclada
     */
    public Map<String, Object> updateUser(Map<String, Object> updateData) {
        try {
            String userId = updateData.get("id").toString();

            // ❌ PROBLEMA: Verificación manual de existencia
            Map<String, Object> existingUser = userRepository.findById(userId);
            if (existingUser == null) {
                return null;
            }

            // ❌ PROBLEMA: Validación de email si se proporciona
            if (updateData.containsKey("email")) {
                String email = updateData.get("email").toString();
                if (!email.contains("@") || !email.contains(".")) {
                    throw new RuntimeException("Invalid email format");
                }

                // ❌ PROBLEMA: Verificación de email único
                List<Map<String, Object>> usersWithEmail = userRepository.findByEmail(email);
                for (Map<String, Object> user : usersWithEmail) {
                    if (!user.get("id").equals(userId)) {
                        throw new RuntimeException("Email already exists");
                    }
                }
            }

            // ❌ PROBLEMA: Validación de nombre si se proporciona
            if (updateData.containsKey("name")) {
                String name = updateData.get("name").toString();
                if (name.length() < 2) {
                    throw new RuntimeException("Name must be at least 2 characters");
                }
            }

            // ❌ PROBLEMA: Validación de status si se proporciona
            if (updateData.containsKey("status")) {
                String status = updateData.get("status").toString();
                if (!Arrays.asList("ACTIVE", "INACTIVE", "SUSPENDED", "DELETED").contains(status)) {
                    throw new RuntimeException("Invalid status value");
                }
            }

            // ❌ PROBLEMA: Construcción manual de datos actualizados
            Map<String, Object> userToUpdate = new HashMap<>(existingUser);
            for (Map.Entry<String, Object> entry : updateData.entrySet()) {
                if (!entry.getKey().equals("id")) {
                    userToUpdate.put(entry.getKey(), entry.getValue());
                }
            }
            userToUpdate.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEMA: Llamada directa al repositorio
            Map<String, Object> updatedUser = userRepository.save(userToUpdate);

            // ❌ PROBLEMA: Transformación manual de respuesta
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
     * ❌ PROBLEMA: Método con transformación manual
     */
    public Map<String, Object> getUserById(String id) {
        try {
            // ❌ PROBLEMA: Llamada directa al repositorio
            Map<String, Object> user = userRepository.findById(id);
            
            if (user == null) {
                return null;
            }

            // ❌ PROBLEMA: Transformación manual de respuesta
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
     * ❌ PROBLEMA: Método con lógica de paginación manual
     */
    public List<Map<String, Object>> getAllUsers(Map<String, Object> filters) {
        try {
            int page = (Integer) filters.get("page");
            int size = (Integer) filters.get("size");
            String status = (String) filters.get("status");
            String search = (String) filters.get("search");

            // ❌ PROBLEMA: Construcción manual de filtros
            Map<String, Object> queryFilters = new HashMap<>();
            if (status != null && !status.isEmpty()) {
                queryFilters.put("status", status);
            }
            if (search != null && !search.isEmpty()) {
                queryFilters.put("search", search);
            }

            // ❌ PROBLEMA: Llamada directa al repositorio
            List<Map<String, Object>> users = userRepository.findAll(queryFilters, page, size);

            // ❌ PROBLEMA: Transformación manual de respuesta
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
     * ❌ PROBLEMA: Método con lógica de negocio específica
     */
    public boolean deleteUser(String id) {
        try {
            // ❌ PROBLEMA: Verificación manual de existencia
            Map<String, Object> existingUser = userRepository.findById(id);
            if (existingUser == null) {
                return false;
            }

            // ❌ PROBLEMA: Lógica de negocio en el servicio
            String currentStatus = existingUser.get("status").toString();
            if ("DELETED".equals(currentStatus)) {
                return false;
            }

            // ❌ PROBLEMA: Soft delete manual
            Map<String, Object> userToDelete = new HashMap<>(existingUser);
            userToDelete.put("status", "DELETED");
            userToDelete.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEMA: Llamada directa al repositorio
            userRepository.save(userToDelete);

            return true;

        } catch (Exception e) {
            throw new RuntimeException("Error deleting user: " + e.getMessage());
        }
    }

    /**
     * ❌ PROBLEMA: Método con lógica de negocio específica
     */
    public Map<String, Object> activateUser(String id) {
        try {
            // ❌ PROBLEMA: Verificación manual de existencia
            Map<String, Object> existingUser = userRepository.findById(id);
            if (existingUser == null) {
                return null;
            }

            // ❌ PROBLEMA: Lógica de negocio en el servicio
            String currentStatus = existingUser.get("status").toString();
            if ("ACTIVE".equals(currentStatus)) {
                throw new RuntimeException("User is already active");
            }

            // ❌ PROBLEMA: Construcción manual de datos
            Map<String, Object> userToActivate = new HashMap<>(existingUser);
            userToActivate.put("status", "ACTIVE");
            userToActivate.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEMA: Llamada directa al repositorio
            Map<String, Object> activatedUser = userRepository.save(userToActivate);

            // ❌ PROBLEMA: Transformación manual de respuesta
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
     * ❌ PROBLEMA: Método con lógica de negocio específica
     */
    public Map<String, Object> deactivateUser(String id) {
        try {
            // ❌ PROBLEMA: Verificación manual de existencia
            Map<String, Object> existingUser = userRepository.findById(id);
            if (existingUser == null) {
                return null;
            }

            // ❌ PROBLEMA: Lógica de negocio en el servicio
            String currentStatus = existingUser.get("status").toString();
            if ("INACTIVE".equals(currentStatus)) {
                throw new RuntimeException("User is already inactive");
            }

            // ❌ PROBLEMA: Construcción manual de datos
            Map<String, Object> userToDeactivate = new HashMap<>(existingUser);
            userToDeactivate.put("status", "INACTIVE");
            userToDeactivate.put("updated_at", LocalDateTime.now());

            // ❌ PROBLEMA: Llamada directa al repositorio
            Map<String, Object> deactivatedUser = userRepository.save(userToDeactivate);

            // ❌ PROBLEMA: Transformación manual de respuesta
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
