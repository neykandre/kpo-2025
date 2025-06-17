package hse.studying.payments.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hse.studying.payments.domain.Account;
import hse.studying.payments.domain.PaymentInboxEvent;
import hse.studying.payments.domain.PaymentOutboxEvent;
import hse.studying.payments.domain.PaymentRequestEvent;
import hse.studying.payments.domain.PaymentResultEvent;
import hse.studying.payments.repository.AccountRepository;
import hse.studying.payments.repository.InboxRepository;
import hse.studying.payments.repository.OutboxRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InboxListener {
    private final InboxRepository inboxRepo;
    private final AccountRepository accountRepo;
    private final OutboxRepository outboxRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    @KafkaListener(topics = "order.payment.requests", groupId = "payments", containerFactory = "paymentReqContainerFactory")
    @Transactional
    public void handle(PaymentRequestEvent evt) throws JsonProcessingException {
        if (inboxRepo.findByEventId(evt.getOrderId()).isPresent()) {
            return;
        }
        PaymentInboxEvent evtIn = PaymentInboxEvent.builder()
                .eventId(evt.getOrderId())
                .payload(mapper.writeValueAsString(evt))
                .build();
        inboxRepo.save(evtIn);
        try {
            boolean success = false;
            Optional<Account> accOpt = accountRepo.findById(evt.getUserId());
            if (accOpt.isPresent() && accOpt.get().getBalance() >= evt.getAmount()) {
                Account acc = accOpt.get();
                acc.setBalance(acc.getBalance() - evt.getAmount());
                success = true;
            }

            PaymentResultEvent res = PaymentResultEvent.builder()
                    .orderId(evt.getOrderId())
                    .status(success ? "SUCCESS" : "FAILED")
                    .build();

            PaymentOutboxEvent evtOut = PaymentOutboxEvent.builder()
                    .aggregateId(res.getOrderId())
                    .type(res.getStatus())
                    .payload(mapper.writeValueAsString(res))
                    .sent(false)
                    .build();
            outboxRepo.save(evtOut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
