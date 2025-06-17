package hse.studying.payments.repository;

import hse.studying.payments.domain.PaymentOutboxEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<PaymentOutboxEvent, Long> {
    List<PaymentOutboxEvent> findBySentFalse();
}
