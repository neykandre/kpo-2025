package hse.studying.orders.config;

import hse.studying.orders.domain.PaymentResultEvent;
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
    public ConcurrentKafkaListenerContainerFactory<String, PaymentResultEvent> orderReqContainerFactory() {
        JsonDeserializer<PaymentResultEvent> deserializer = new JsonDeserializer<>(PaymentResultEvent.class);
        Map<String, Object> p = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bs,
                ConsumerConfig.GROUP_ID_CONFIG, "orders",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        var f = new ConcurrentKafkaListenerContainerFactory<String, PaymentResultEvent>();
        f.setConsumerFactory(new DefaultKafkaConsumerFactory<>(p, new StringDeserializer(), deserializer));
        return f;
    }
}
