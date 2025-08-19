package com.example.app.modules.users.domain.events;

import com.example.app.modules.users.domain.Email;
import com.example.app.modules.users.domain.UserId;
import com.example.app.shared.domain.DomainEvent;

/**
 * Evento de dominio que se dispara cuando se registra un nuevo usuario.
 *
 * <p>Este evento implementa el patrón Domain Event de DDD para notificar a otros componentes del
 * sistema sobre el registro de un usuario.
 */
public class UserRegisteredEvent extends DomainEvent {

  private final UserId userId;
  private final Email email;

  public UserRegisteredEvent(final UserId userId, final Email email) {
    super();
    this.userId = userId;
    this.email = email;
  }

  public UserId getUserId() {
    return userId;
  }

  public Email getEmail() {
    return email;
  }

  @Override
  public String toString() {
    return "UserRegisteredEvent{"
        + "eventId="
        + getEventId()
        + ", occurredOn="
        + getOccurredOn()
        + ", eventType="
        + getEventType()
        + ", userId="
        + userId
        + ", email="
        + email
        + '}';
  }
}
