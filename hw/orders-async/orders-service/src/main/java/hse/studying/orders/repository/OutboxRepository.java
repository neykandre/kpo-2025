package hse.studying.orders.repository;

import hse.studying.orders.domain.OrderOutboxEvent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OrderOutboxEvent, Long> {
    List<OrderOutboxEvent> findBySentFalse();
}
