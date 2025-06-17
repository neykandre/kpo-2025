package hse.studying.payments.config;

import hse.studying.payments.domain.PaymentRequestEvent;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}") private String bs;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, PaymentRequestEvent> paymentReqContainerFactory() {
        JsonDeserializer<PaymentRequestEvent> deserializer = new JsonDeserializer<>(PaymentRequestEvent.class);
        Map<String, Object> p = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bs,
                ConsumerConfig.GROUP_ID_CONFIG, "payments",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        var f = new ConcurrentKafkaListenerContainerFactory<String, PaymentRequestEvent>();
        f.setConsumerFactory(new DefaultKafkaConsumerFactory<>(p, new StringDeserializer(), deserializer));
        return f;
    }
}
