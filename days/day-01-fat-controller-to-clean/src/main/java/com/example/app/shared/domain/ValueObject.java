package com.example.app.shared.domain;

import java.util.Objects;

/**
 * Base class for Value Objects in Domain-Driven Design.
 * 
 * Value Objects are immutable and their equality is based on their values,
 * not their identity.
 */
public abstract class ValueObject<T> {
    
    private final T value;
    
    protected ValueObject(T value) {
        this.value = value;
    }
    
    protected T value() {
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
        return getClass().getSimpleName() + "[" + value + "]";
    }
}
