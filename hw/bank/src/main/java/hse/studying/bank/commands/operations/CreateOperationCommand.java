package hse.studying.bank.commands.operations;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.BalanceFacade;
import hse.studying.bank.facades.operation.OperationFacade;
import hse.studying.bank.interfaces.Command;
import jakarta.validation.constraints.Min;
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
public class CreateOperationCommand implements Command<Operation> {

    private final OperationFacade operationFacade;
    private final BalanceFacade balanceFacade;
    @NotNull(message = "Bank account cannot be null")
    private BankAccount bankAccount;
    @NotNull(message = "Category cannot be null")
    private Category category;
    @NotNull(message = "Type cannot be null")
    private TransferType type;
    @Min(value = 0, message = "Amount cannot be negative")
    private Double amount;
    private String description;

    @Override
    @Transactional
    public Operation execute() {
        var op = operationFacade.createOperation(bankAccount, category, type, amount, description);
        balanceFacade.applyOperation(op);
        return op;
    }
}
