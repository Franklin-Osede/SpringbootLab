package com.example.app.shared.domain;

import java.util.Objects;

/**
 * Base class for Entities in Domain-Driven Design.
 * 
 * Entities have identity and their equality is based on their ID,
 * not their values.
 */
public abstract class Entity<ID> {
    
    private final ID id;
    
    protected Entity(ID id) {
        this.id = id;
    }
    
    public ID getId() {
        return id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Entity<?> entity = (Entity<?>) obj;
        return Objects.equals(id, entity.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + "]";
    }
}
