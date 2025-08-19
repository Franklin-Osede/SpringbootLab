package com.example.app.shared.domain;

import java.util.Objects;

/**
 * Clase base para Value Objects siguiendo los principios de DDD.
 *
 * <p>Los Value Objects son inmutables y se comparan por valor, no por identidad.
 *
 * @param <T> El tipo del valor contenido
 */
public abstract class ValueObject<T> {

  protected final T value;

  protected ValueObject(T value) {
    this.value = Objects.requireNonNull(value, "Value cannot be null");
  }

  public T getValue() {
    return value;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    ValueObject<?> that = (ValueObject<?>) obj;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "(" + value + ")";
  }
}
