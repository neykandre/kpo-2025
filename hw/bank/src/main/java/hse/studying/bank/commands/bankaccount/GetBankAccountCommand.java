package hse.studying.bank.commands.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.facades.bankaccount.BankAccountFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class GetBankAccountCommand implements Command<Optional<BankAccount>> {

    private final BankAccountFacade bankAccountFacade;
    @NotNull(message = "Bank account id cannot be null")
    private Long id;

    @Override
    @Transactional
    public Optional<BankAccount> execute() {
        return bankAccountFacade.getBankAccount(id);
    }
}
