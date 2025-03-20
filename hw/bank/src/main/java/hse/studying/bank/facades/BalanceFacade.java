package hse.studying.bank.facades;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.bankaccount.BankAccountFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BalanceFacade {

    private final BankAccountFacade bankAccountFacade;

    @Transactional
    public void applyOperation(Operation operation) {
        BankAccount bankAccount = operation.getBankAccount();
        bankAccountFacade.updateBankAccount(
                bankAccount.getId(),
                bankAccount.getName(),
                calculateNewBalance(bankAccount, operation));
    }

    @Transactional
    public void revertOperation(Operation operation) {
        BankAccount bankAccount = operation.getBankAccount();
        bankAccountFacade.updateBankAccount(
                bankAccount.getId(),
                bankAccount.getName(),
                calculateRevertedBalance(bankAccount, operation));
    }

    private double calculateNewBalance(BankAccount bankAccount, Operation operation)
            throws IllegalArgumentException {
        if (operation.getType() == TransferType.INCOME) {
            return bankAccount.getBalance() + operation.getAmount();
        } else {
            if (bankAccount.getBalance() < operation.getAmount()) {
                throw new IllegalArgumentException("Not enough funds");
            }
            return bankAccount.getBalance() - operation.getAmount();
        }
    }

    private double calculateRevertedBalance(BankAccount bankAccount, Operation operation) {
        if (operation.getType() == TransferType.INCOME) {
            return bankAccount.getBalance() - operation.getAmount();
        } else {
            return bankAccount.getBalance() + operation.getAmount();
        }
    }
}
