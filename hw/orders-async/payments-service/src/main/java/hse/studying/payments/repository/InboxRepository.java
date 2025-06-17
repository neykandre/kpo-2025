package hse.studying.payments.repository;

import hse.studying.payments.domain.PaymentInboxEvent;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxRepository extends JpaRepository<PaymentInboxEvent, Long> {
    Optional<PaymentInboxEvent> findByEventId(String eventId);
}
