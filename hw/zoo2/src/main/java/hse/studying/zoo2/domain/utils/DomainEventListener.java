package hse.studying.zoo2.domain.utils;

import hse.studying.zoo2.domain.events.DomainEvent;

public interface DomainEventListener {
    void handle(DomainEvent event);
}
