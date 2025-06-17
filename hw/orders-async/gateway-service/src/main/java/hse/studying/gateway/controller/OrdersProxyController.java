package hse.studying.gateway.controller;

import hse.studying.gateway.model.OrderRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersProxyController {

    private final WebClient ordersClient;

    @PostMapping
    public Mono<ResponseEntity<String>> create(
            @RequestBody OrderRequest orderRequest
    ) {
        return ordersClient.post()
                .uri(uri -> uri
                        .path("/orders")
                        .queryParam("userId", orderRequest.getUserId())
                        .build()
                )
                .bodyValue(orderRequest)
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping
    public Mono<ResponseEntity<String>> list(@RequestParam Long userId) {
        return ordersClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders").queryParam("userId", userId).build())
                .retrieve().toEntity(String.class);
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<String>> get(@RequestParam Long userId, @PathVariable Long id) {
        return ordersClient.get()
                .uri(uriBuilder -> uriBuilder.path("/orders/{id}")
                        .queryParam("userId", userId).build(id))
                .retrieve().toEntity(String.class);
    }
}