package hse.studying.bank.facades.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.factories.bankaccount.BankAccountFactory;
import hse.studying.bank.providers.FinanceRepository;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class BankAccountFacade {

    private final BankAccountFactory bankAccountFactory;
    private final FinanceRepository<BankAccount> bankAccountRepository;

    public BankAccount createBankAccount(String name) {
        return bankAccountRepository.save(bankAccountFactory.createBankAccount(name));
    }

    public Optional<BankAccount> getBankAccount(Long id) {
        return bankAccountRepository.findById(id);
    }

    public Iterable<BankAccount> getBankAccounts() {
        return bankAccountRepository.findAll();
    }

    public BankAccount updateBankAccount(
            @NotNull Long id,
            @NotBlank String newName,
            @Min(0) double newBalance
    ) {
        var bankAccount = getBankAccount(id);
        if (bankAccount.isEmpty()) {
            throw new IllegalArgumentException("Bank account not found");
        }
        bankAccount.get().setName(newName);
        bankAccount.get().setBalance(newBalance);
        return bankAccountRepository.save(bankAccount.get());
    }

    public Void deleteBankAccount(Long id) {
        bankAccountRepository.deleteById(id);
        return null;
    }
}
