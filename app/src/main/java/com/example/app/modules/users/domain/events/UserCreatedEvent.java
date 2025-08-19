package com.example.app.modules.users.domain.events;

import com.example.app.shared.domain.DomainEvent;
import com.example.app.modules.users.domain.UserId;
import com.example.app.modules.users.domain.Email;



/**
 * Evento de dominio que se dispara cuando se crea un nuevo usuario.
 * 
 * Este evento implementa el patrón Domain Event de DDD para notificar
 * a otros componentes del sistema sobre la creación de un usuario.
 */
public class UserCreatedEvent extends DomainEvent {
    
    private final UserId userId;
    private final Email email;
    private final String name;
    
    public UserCreatedEvent(final UserId userId, final Email email, final String name) {
        super();
        this.userId = userId;
        this.email = email;
        this.name = name;
    }
    
    public UserId getUserId() {
        return userId;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
    @Override
    public String toString() {
        return "UserCreatedEvent{" +
                "eventId=" + getEventId() +
                ", occurredOn=" + getOccurredOn() +
                ", eventType=" + getEventType() +
                ", userId=" + userId +
                ", email=" + email +
                ", name='" + name + '\'' +
                '}';
    }
}
