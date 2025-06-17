package hse.studying.orders.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hse.studying.orders.domain.Order;
import hse.studying.orders.domain.OrderOutboxEvent;
import hse.studying.orders.domain.OrderStatus;
import hse.studying.orders.domain.PaymentRequestEvent;
import hse.studying.orders.repository.OrderRepository;
import hse.studying.orders.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepo;
    private final OutboxRepository outboxRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public Order createOrder(Long userId, Double amount, String description) {
        Order order = Order.builder()
                .userId(userId)
                .amount(amount)
                .description(description)
                .status(OrderStatus.PENDING_PAYMENT)
                .build();
        orderRepo.save(order);
        try {
            PaymentRequestEvent dto = PaymentRequestEvent.builder()
                    .orderId(order.getId().toString())
                    .userId(userId)
                    .amount(amount)
                    .description(description)
                    .build();
            OrderOutboxEvent evt = OrderOutboxEvent.builder()
                    .aggregateId(dto.getOrderId())
                    .type("PAYMENT_REQUESTED")
                    .payload(mapper.writeValueAsString(dto))
                    .sent(false)
                    .build();
            outboxRepo.save(evt);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    public List<Order> listOrders(Long userId) {return orderRepo.findByUserId(userId);}

    public Optional<Order> getOrder(Long userId, Long id) {
        return orderRepo.findById(id).filter(o -> o.getUserId().equals(userId));
    }
}
