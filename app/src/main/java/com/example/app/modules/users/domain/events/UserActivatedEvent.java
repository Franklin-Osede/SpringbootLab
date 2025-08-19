package com.example.app.modules.users.domain.events;

import com.example.app.modules.users.domain.UserId;
import com.example.app.shared.domain.DomainEvent;

/**
 * Evento de dominio que se dispara cuando se activa un usuario.
 *
 * <p>Este evento implementa el patrón Domain Event de DDD para notificar a otros componentes del
 * sistema sobre la activación de un usuario.
 */
public class UserActivatedEvent extends DomainEvent {

  private final UserId userId;

  public UserActivatedEvent(final UserId userId) {
    super();
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }

  @Override
  public String toString() {
    return "UserActivatedEvent{"
        + "eventId="
        + getEventId()
        + ", occurredOn="
        + getOccurredOn()
        + ", eventType="
        + getEventType()
        + ", userId="
        + userId
        + '}';
  }
}
