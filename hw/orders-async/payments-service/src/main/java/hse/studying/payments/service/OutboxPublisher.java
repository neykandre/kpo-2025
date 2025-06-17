package hse.studying.payments.service;

import hse.studying.payments.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {
    private final OutboxRepository repo;
    private final KafkaTemplate<String, String> kafka;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void flush() {
        repo.findBySentFalse().forEach(e -> kafka.send("order.payment.results", e.getAggregateId(), e.getPayload())
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        e.setSent(true);
                        repo.save(e);
                    } else {
                        log.error("Failed to send payment result", ex);
                    }
                }));
    }
}