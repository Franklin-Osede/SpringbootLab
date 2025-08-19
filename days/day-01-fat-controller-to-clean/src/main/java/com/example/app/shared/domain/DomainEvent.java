package com.example.app.shared.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Base class for Domain Events in Domain-Driven Design.
 * 
 * Domain Events represent something that happened in the domain
 * and can be used for integration between bounded contexts.
 */
public abstract class DomainEvent {
    
    private final String eventId;
    private final LocalDateTime occurredOn;
    private final String eventType;
    
    protected DomainEvent() {
        this.eventId = UUID.randomUUID().toString();
        this.occurredOn = LocalDateTime.now();
        this.eventType = this.getClass().getSimpleName();
    }
    
    public String getEventId() {
        return eventId;
    }
    
    public LocalDateTime getOccurredOn() {
        return occurredOn;
    }
    
    public String getEventType() {
        return eventType;
    }
    
    @Override
    public String toString() {
        return eventType + "[id=" + eventId + ", occurredOn=" + occurredOn + "]";
    }
}
