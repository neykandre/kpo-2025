package hse.studying.bank.commands.bankaccount;

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
public class DeleteBankAccountCommand implements Command<Void> {

    private final BankAccountFacade bankAccountFacade;
    @NotNull(message = "Bank account id cannot be null")
    private Long id;

    @Override
    @Transactional
    public Void execute() {
        bankAccountFacade.deleteBankAccount(id);
        return null;
    }
}
