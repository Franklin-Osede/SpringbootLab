package com.example.app.shared.domain;

import java.time.Instant;
import java.util.UUID;

/**
 * Clase base para eventos de dominio siguiendo los principios de DDD.
 *
 * <p>Los eventos de dominio representan algo que ha ocurrido en el dominio y que puede ser de
 * interés para otras partes del sistema.
 */
public abstract class DomainEvent {

  private final String eventId;
  private final Instant occurredOn;
  private final String eventType;

  protected DomainEvent() {
    this.eventId = UUID.randomUUID().toString();
    this.occurredOn = Instant.now();
    this.eventType = this.getClass().getSimpleName();
  }

  public String getEventId() {
    return eventId;
  }

  public Instant getOccurredOn() {
    return occurredOn;
  }

  public String getEventType() {
    return eventType;
  }

  @Override
  public String toString() {
    return "DomainEvent{"
        + "eventId='"
        + eventId
        + '\''
        + ", occurredOn="
        + occurredOn
        + ", eventType='"
        + eventType
        + '\''
        + '}';
  }
}
