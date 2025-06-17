package hse.studying.orders.service;

import hse.studying.orders.domain.OrderOutboxEvent;
import hse.studying.orders.repository.OutboxRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OutboxPublisher {
    private static final Logger log = LoggerFactory.getLogger(OutboxPublisher.class);
    private final OutboxRepository repo;
    private final KafkaTemplate<String, String> kafka;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publish() {

        List<OrderOutboxEvent> evts = repo.findBySentFalse();
        evts.forEach(e -> kafka.send("order.payment.requests", e.getAggregateId(), e.getPayload())
                .whenComplete((r, ex) -> {
                    if (ex != null) {
                        log.error("Failed to send event", ex);
                    } else {
                        e.setSent(true);
                        repo.save(e);
                    }
                }));
    }
}