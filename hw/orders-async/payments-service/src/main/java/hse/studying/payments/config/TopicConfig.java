package hse.studying.payments.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

    @Bean
    public NewTopic paymentRequests() {
        return TopicBuilder.name("order.payment.requests")
                .partitions(1).replicas(1).build();
    }

    @Bean
    public NewTopic paymentResults() {
        return TopicBuilder.name("order.payment.results")
                .partitions(1).replicas(1).build();
    }
}
