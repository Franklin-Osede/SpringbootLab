package com.example.app.shared.domain;

import java.util.Objects;

/**
 * Clase base para entidades siguiendo los principios de DDD.
 * 
 * Las entidades tienen identidad y pueden cambiar su estado manteniendo la misma identidad.
 * 
 * @param <ID> El tipo del identificador de la entidad
 */
public abstract class Entity<ID> {

    protected final ID id;

    protected Entity(ID id) {
        this.id = Objects.requireNonNull(id, "Entity ID cannot be null");
    }

    public ID getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Entity<?> that = (Entity<?>) obj;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id=" + id + ")";
    }
}
