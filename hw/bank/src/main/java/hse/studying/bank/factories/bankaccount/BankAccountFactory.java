package hse.studying.bank.factories.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class BankAccountFactory {

    public BankAccount createBankAccount(@NotBlank String name) {
        return new BankAccount(null, name, 0);
    }
}
