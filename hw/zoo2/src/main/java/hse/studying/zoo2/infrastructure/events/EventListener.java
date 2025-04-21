package hse.studying.zoo2.infrastructure.events;

import hse.studying.zoo2.domain.events.DomainEvent;
import hse.studying.zoo2.domain.utils.DomainEventListener;
import hse.studying.zoo2.domain.utils.DomainEvents;
import jakarta.annotation.PostConstruct;
import java.util.logging.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventListener implements DomainEventListener {
    private final Logger log = Logger.getLogger(EventListener.class.getName());

    @PostConstruct
    public void init() {
        DomainEvents.register(this);
    }

    @Override
    public void handle(DomainEvent event) {
        log.info("Event: " + event);
    }
}
