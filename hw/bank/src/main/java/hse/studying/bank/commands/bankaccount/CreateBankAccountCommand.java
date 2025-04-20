package hse.studying.bank.commands.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.facades.bankaccount.BankAccountFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@RequiredArgsConstructor
@Setter
@Accessors(chain = true)
@Validated
public class CreateBankAccountCommand implements Command<BankAccount> {

    private final BankAccountFacade bankAccountFacade;
    @NotBlank(message = "Name cannot be empty")
    private String name;

    @Override
    @Transactional
    public BankAccount execute() {
        return bankAccountFacade.createBankAccount(name);
    }
}
