package com.example.app.after.domain.valueobjects;

import java.util.Objects;

/**
 * ✅ VALUE OBJECT BASE CLASS - AFTER REFACTORING
 * 
 * IMPROVEMENTS IMPLEMENTED:
 * - Immutable base class for value objects
 * - Proper equals and hashCode implementation
 * - Type safety
 * - Follows DDD principles
 */
public abstract class ValueObject<T> {

    private final T value;

    protected ValueObject(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ValueObject<?> that = (ValueObject<?>) obj;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value != null ? value.toString() : "null";
    }
}
