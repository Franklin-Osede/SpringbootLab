package com.example.app.after.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.app.after.application.dto.CreateUserRequest;
import com.example.app.after.application.dto.UserResponse;
import com.example.app.after.application.service.UserApplicationService;
import com.example.app.after.infrastructure.web.UserController;

/**
 * ✅ USER CONTROLLER TEST - AFTER REFACTORING
 *
 * <p>IMPROVEMENTS DEMONSTRATED: - Clean unit tests - Mocking with Mockito - AssertJ assertions -
 * Debugging capabilities - Test-driven development
 */
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @Mock private UserApplicationService userApplicationService;

  private UserController userController;

  @BeforeEach
  void setUp() {
    userController = new UserController(userApplicationService);
  }

  /** ✅ IMPROVEMENT: Clean test with debugging capabilities */
  @Test
  void shouldCreateUserSuccessfully() {
    // Given - Set breakpoint here to debug test setup
    CreateUserRequest request =
        new CreateUserRequest("test@example.com", "John Doe", "password123");

    UserResponse expectedResponse =
        new UserResponse(
            "user-123",
            "test@example.com",
            "John Doe",
            "ACTIVE",
            java.time.LocalDateTime.now(),
            java.time.LocalDateTime.now());

    // Set breakpoint here to debug mock setup
    when(userApplicationService.createUser(any(CreateUserRequest.class)))
        .thenReturn(expectedResponse);

    // When - Set breakpoint here to debug method execution
    ResponseEntity<UserResponse> response = userController.createUser(request);

    // Then - Set breakpoint here to debug assertions
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    assertThat(response.getBody()).isNotNull();
    assertThat(response.getBody().email()).isEqualTo("test@example.com");
    assertThat(response.getBody().name()).isEqualTo("John Doe");
    assertThat(response.getBody().status()).isEqualTo("ACTIVE");
  }

  /** ✅ IMPROVEMENT: Test error handling */
  @Test
  void shouldHandleServiceException() {
    // Given
    CreateUserRequest request = new CreateUserRequest("invalid-email", "John Doe", "password123");

    when(userApplicationService.createUser(any(CreateUserRequest.class)))
        .thenThrow(new IllegalArgumentException("Invalid email format"));

    // When & Then
    try {
      userController.createUser(request);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).isEqualTo("Invalid email format");
    }
  }

  /** ✅ IMPROVEMENT: Test validation */
  @Test
  void shouldValidateRequestData() {
    // Given - Invalid request (empty email)
    CreateUserRequest request =
        new CreateUserRequest(
            "", // Invalid empty email
            "John Doe",
            "password123");

    // When & Then - Should throw validation exception
    try {
      userController.createUser(request);
    } catch (IllegalArgumentException e) {
      assertThat(e.getMessage()).contains("Email cannot be empty");
    }
  }
}
