package hse.studying.bank.commands.bankaccount;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.facades.bankaccount.BankAccountFacade;
import hse.studying.bank.interfaces.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class GetAllBankAccountsCommand implements Command<Iterable<BankAccount>> {

    private final BankAccountFacade bankAccountFacade;

    @Override
    @Transactional
    public Iterable<BankAccount> execute() {
        return bankAccountFacade.getBankAccounts();
    }
}
