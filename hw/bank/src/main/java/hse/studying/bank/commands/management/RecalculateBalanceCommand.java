package hse.studying.bank.commands.management;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.facades.management.ManagementFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class RecalculateBalanceCommand implements Command<Double> {
    private final ManagementFacade managementFacade;
    @NotNull(message = "Bank account cannot be null")
    private BankAccount bankAccount;

    @Override
    public Double execute() {
        var newBalance = managementFacade.recalculateBalance(bankAccount);
        bankAccount.setBalance(newBalance);
        return newBalance;
    }
}
