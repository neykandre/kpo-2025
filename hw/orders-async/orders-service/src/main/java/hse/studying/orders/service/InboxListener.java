package hse.studying.orders.service;

import hse.studying.orders.domain.Order;
import hse.studying.orders.domain.OrderStatus;
import hse.studying.orders.domain.PaymentResultEvent;
import hse.studying.orders.repository.OrderRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InboxListener {

    private final OrderRepository orderRepo;

    @KafkaListener(topics = "order.payment.results", groupId = "orders", containerFactory = "orderReqContainerFactory")
    @Transactional
    public void listen(PaymentResultEvent evt) {
        Optional<Order> optional = orderRepo.findById(Long.valueOf(evt.getOrderId()));
        if (optional.isPresent()) {
            Order order = optional.get();
            order.setStatus("SUCCESS".equals(evt.getStatus())
                    ? OrderStatus.PAID : OrderStatus.FAILED);
            orderRepo.save(order);
        }
    }
}
