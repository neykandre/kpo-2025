package hse.studying.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.orders-base-url}")
    private String ordersUrl;

    @Value("${app.payments-base-url}")
    private String paymentsUrl;

    @Bean("ordersClient")
    public WebClient ordersClient() {
        return WebClient.builder().baseUrl(ordersUrl).build();
    }

    @Bean("paymentsClient")
    public WebClient paymentsClient() {
        return WebClient.builder().baseUrl(paymentsUrl).build();
    }
}