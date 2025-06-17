package hse.studying.payments.controller;

import hse.studying.payments.domain.Account;
import hse.studying.payments.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService svc;

    @PostMapping
    public Account create(@RequestParam Long userId) {return svc.createAccount(userId);}

    @PostMapping("/deposit")
    public Account deposit(@RequestParam Long userId, @RequestParam Double amount) {return svc.deposit(userId, amount);}

    @GetMapping("/balance")
    public ResponseEntity<Account> bal(@RequestParam Long userId) {
        return svc.balance(userId).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}