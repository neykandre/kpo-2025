package hse.studying.zoo2.domain.utils;

import hse.studying.zoo2.domain.events.DomainEvent;
import java.util.ArrayList;
import java.util.List;

public final class DomainEvents {
    private static final List<DomainEventListener> listeners = new ArrayList<>();

    public static void register(DomainEventListener listener) {
        listeners.add(listener);
    }

    public static void raise(DomainEvent event) {
        listeners.forEach(listener -> listener.handle(event));
    }
}
