package hse.studying.bank.factories.operation;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class OperationFactory {

    public Operation createOperation(
            @NotNull BankAccount bankAccount,
            @NotNull Category category,
            @NotNull TransferType type,
            @Min(0) double amount,
            String description) {
        return new Operation(null, bankAccount, category, type, amount, new Date(), description);
    }
}
