package com.example.app.modules.users.domain.events;

import com.example.app.modules.users.domain.UserId;
import com.example.app.shared.domain.DomainEvent;

/**
 * Evento de dominio que se dispara cuando se desactiva un usuario.
 *
 * <p>Este evento implementa el patrón Domain Event de DDD para notificar a otros componentes del
 * sistema sobre la desactivación de un usuario.
 */
public class UserDeactivatedEvent extends DomainEvent {

  private final UserId userId;

  public UserDeactivatedEvent(final UserId userId) {
    super();
    this.userId = userId;
  }

  public UserId getUserId() {
    return userId;
  }

  @Override
  public String toString() {
    return "UserDeactivatedEvent{"
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
