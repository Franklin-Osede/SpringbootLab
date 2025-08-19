package com.example.app.modules.users.domain;

/**
 * Enum que representa los posibles estados de un usuario en el sistema.
 *
 * <p>Esta clase implementa el patrón Value Object de DDD para representar el estado del usuario de
 * manera inmutable y con comportamiento encapsulado.
 */
public enum UserStatus {

  /** Usuario pendiente de activación */
  PENDING("PENDING", "Usuario pendiente"),

  /** Usuario activo que puede acceder al sistema */
  ACTIVE("ACTIVE", "Usuario activo"),

  /** Usuario inactivo que no puede acceder al sistema */
  INACTIVE("INACTIVE", "Usuario inactivo"),

  /** Usuario suspendido temporalmente */
  SUSPENDED("SUSPENDED", "Usuario suspendido"),

  /** Usuario eliminado del sistema */
  DELETED("DELETED", "Usuario eliminado");

  private final String code;
  private final String description;

  UserStatus(final String code, final String description) {
    this.code = code;
    this.description = description;
  }

  /**
   * Obtiene el código del estado
   *
   * @return código del estado
   */
  public String getCode() {
    return code;
  }

  /**
   * Obtiene la descripción del estado
   *
   * @return descripción del estado
   */
  public String getDescription() {
    return description;
  }

  /**
   * Verifica si el usuario está activo
   *
   * @return true si está activo, false en caso contrario
   */
  public boolean isActive() {
    return this == ACTIVE;
  }

  /**
   * Verifica si el usuario puede acceder al sistema
   *
   * @return true si puede acceder, false en caso contrario
   */
  public boolean canAccess() {
    return this == ACTIVE;
  }

  @Override
  public String toString() {
    return code;
  }
}
