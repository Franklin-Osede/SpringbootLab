package com.example.app.after.application.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.after.application.dto.CreateUserRequest;
import com.example.app.after.application.dto.UpdateUserRequest;
import com.example.app.after.application.dto.UserResponse;
import com.example.app.after.application.mapper.UserMapper;
import com.example.app.after.domain.User;
import com.example.app.after.domain.UserStatus;
import com.example.app.after.domain.valueobjects.UserId;
import com.example.app.after.infrastructure.persistence.UserRepository;

/**
 * ✅ APPLICATION SERVICE - AFTER REFACTORING
 *
 * <p>IMPROVEMENTS IMPLEMENTED: - Single responsibility: orchestrate use cases - Transactional
 * boundaries - Domain object usage - Clean separation of concerns - Constructor injection - Follows
 * SOLID principles
 */
@Service
@Transactional
public class UserApplicationService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;

  public UserApplicationService(UserRepository userRepository, UserMapper userMapper) {
    this.userRepository = userRepository;
    this.userMapper = userMapper;
  }

  /** ✅ IMPROVEMENT: Single responsibility method */
  public UserResponse createUser(CreateUserRequest request) {
    // ✅ IMPROVEMENT: Domain object creation
    User user = User.create(request.email(), request.name(), request.password());

    // ✅ IMPROVEMENT: Domain object persistence
    User savedUser = userRepository.save(user);

    // ✅ IMPROVEMENT: Clean mapping to DTO
    return userMapper.toResponse(savedUser);
  }

  /** ✅ IMPROVEMENT: Single responsibility method */
  public UserResponse updateUser(String id, UpdateUserRequest request) {
    // ✅ IMPROVEMENT: Domain object retrieval
    UserId userId = UserId.of(id);
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    // ✅ IMPROVEMENT: Domain object update
    if (request.email() != null) {
      user.updateEmail(request.email());
    }

    if (request.name() != null) {
      user.updateName(request.name());
    }

    if (request.status() != null) {
      user.updateStatus(UserStatus.valueOf(request.status()));
    }

    // ✅ IMPROVEMENT: Domain object persistence
    User updatedUser = userRepository.save(user);

    // ✅ IMPROVEMENT: Clean mapping to DTO
    return userMapper.toResponse(updatedUser);
  }

  /** ✅ IMPROVEMENT: Single responsibility method */
  @Transactional(readOnly = true)
  public UserResponse getUserById(String id) {
    // ✅ IMPROVEMENT: Domain object retrieval
    UserId userId = new UserId(id);
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    // ✅ IMPROVEMENT: Clean mapping to DTO
    return userMapper.toResponse(user);
  }

  /** ✅ IMPROVEMENT: Single responsibility method */
  @Transactional(readOnly = true)
  public List<UserResponse> getAllUsers(int page, int size, String status, String search) {
    // ✅ IMPROVEMENT: Domain object retrieval with pagination
    List<User> users = userRepository.findAll(page, size, status, search);

    // ✅ IMPROVEMENT: Clean mapping to DTOs
    return users.stream().map(userMapper::toResponse).collect(Collectors.toList());
  }

  /** ✅ IMPROVEMENT: Single responsibility method */
  public void deleteUser(String id) {
    // ✅ IMPROVEMENT: Domain object retrieval
    UserId userId = new UserId(id);
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    // ✅ IMPROVEMENT: Domain object deletion
    user.delete();
    userRepository.save(user);
  }

  /** ✅ IMPROVEMENT: Single responsibility method */
  public UserResponse activateUser(String id) {
    // ✅ IMPROVEMENT: Domain object retrieval
    UserId userId = new UserId(id);
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    // ✅ IMPROVEMENT: Domain object activation
    user.activate();

    // ✅ IMPROVEMENT: Domain object persistence
    User activatedUser = userRepository.save(user);

    // ✅ IMPROVEMENT: Clean mapping to DTO
    return userMapper.toResponse(activatedUser);
  }

  /** ✅ IMPROVEMENT: Single responsibility method */
  public UserResponse deactivateUser(String id) {
    // ✅ IMPROVEMENT: Domain object retrieval
    UserId userId = new UserId(id);
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

    // ✅ IMPROVEMENT: Domain object deactivation
    user.deactivate();

    // ✅ IMPROVEMENT: Domain object persistence
    User deactivatedUser = userRepository.save(user);

    // ✅ IMPROVEMENT: Clean mapping to DTO
    return userMapper.toResponse(deactivatedUser);
  }

  /** ✅ IMPROVEMENT: Custom exception for domain errors */
  public static class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
      super(message);
    }
  }
}
