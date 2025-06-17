package hse.studying.payments.service;

import hse.studying.payments.domain.Account;
import hse.studying.payments.repository.AccountRepository;
import jakarta.transaction.Transactional;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository repo;

    public Account createAccount(Long userId) {
        return repo.findById(userId).orElseGet(() -> repo.save(Account.builder()
                .userId(userId).balance(0.0).build()));
    }

    @Transactional
    public Account deposit(Long userId, Double amount) {
        Account acc = repo.findById(userId).orElseGet(() -> createAccount(userId));
        acc.setBalance(acc.getBalance() + amount);
        return acc;
    }

    public Optional<Account> balance(Long userId) {return repo.findById(userId);}
}