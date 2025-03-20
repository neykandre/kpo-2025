package hse.studying.bank.commands.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.facades.bankaccount.BankAccountFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class UpdateBankAccountCommand implements Command<BankAccount> {

    private final BankAccountFacade bankAccountFacade;
    @NotNull(message = "Bank account id cannot be null")
    private Long id;
    private String newName;
    private Double newBalance;

    @Override
    @Transactional
    public BankAccount execute() {
        var account = bankAccountFacade.getBankAccount(id);
        if (account.isEmpty()) {
            throw new IllegalArgumentException("Bank account not found");
        }
        if (newName == null) {
            newName = account.get().getName();
        }
        if (newBalance == null) {
            newBalance = account.get().getBalance();
        }
        return bankAccountFacade.updateBankAccount(id, newName, newBalance);
    }
}
