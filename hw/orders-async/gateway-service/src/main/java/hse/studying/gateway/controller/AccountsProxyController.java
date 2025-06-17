package hse.studying.gateway.controller;

import hse.studying.gateway.model.AccountDepositRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountsProxyController {

    private final WebClient paymentsClient;

    @PostMapping
    public Mono<ResponseEntity<String>> create(@RequestParam Long userId) {
        return paymentsClient.post()
                .uri(uri -> uri
                        .path("/accounts")
                        .queryParam("userId", userId)
                        .build()
                )
                .retrieve()
                .toEntity(String.class);
    }

    @PostMapping("/deposit")
    public Mono<ResponseEntity<String>> deposit(@RequestBody AccountDepositRequest req) {
        return paymentsClient.post()
                .uri(uri -> uri
                        .path("/accounts/deposit")
                        .queryParam("userId", req.getUserId())
                        .queryParam("amount", req.getAmount())
                        .build()
                )
                .retrieve()
                .toEntity(String.class);
    }

    @GetMapping("/balance")
    public Mono<ResponseEntity<String>> balance(@RequestParam Long userId) {
        return paymentsClient.get()
                .uri(uri -> uri
                        .path("/accounts/balance")
                        .queryParam("userId", userId)
                        .build()
                )
                .retrieve()
                .toEntity(String.class);
    }
}