package hse.studying.bank.facades.management;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.bankaccount.BankAccountFacade;
import hse.studying.bank.facades.operation.OperationFacade;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManagementFacade {
    private final OperationFacade operationFacade;

    public double recalculateBalance(BankAccount bankAccount) {
        return StreamSupport.stream(operationFacade.getOperations().spliterator(), false)
                .filter(operation -> operation.getBankAccount().getId().equals(bankAccount.getId()))
                .mapToDouble(operation -> operation.getType() == TransferType.INCOME
                        ? operation.getAmount()
                        : -operation.getAmount())
                .sum();
    }
}
