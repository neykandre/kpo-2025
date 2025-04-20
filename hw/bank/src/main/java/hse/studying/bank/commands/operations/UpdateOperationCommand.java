package hse.studying.bank.commands.operations;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.BalanceFacade;
import hse.studying.bank.facades.operation.OperationFacade;
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
public class UpdateOperationCommand implements Command<Operation> {

    private final OperationFacade operationFacade;
    private final BalanceFacade balanceFacade;
    @NotNull(message = "Operation id cannot be null")
    private Long id;
    private BankAccount newBankAccount;
    private Category newCategory;
    private TransferType newType;
    private Double newAmount;
    private String newDescription;

    @Override
    @Transactional
    public Operation execute() {
        var op =
                operationFacade.getOperation(id)
                        .orElseThrow(() -> new IllegalArgumentException("Operation not found"));
        balanceFacade.revertOperation(op);

        if (newBankAccount == null) {
            newBankAccount = op.getBankAccount();
        }
        if (newCategory == null) {
            newCategory = op.getCategory();
        }
        if (newType == null) {
            newType = op.getType();
        }
        if (newAmount == null) {
            newAmount = op.getAmount();
        }
        if (newDescription == null) {
            newDescription = op.getDescription();
        }

        var newOp =
                operationFacade.updateOperation(
                        id, newBankAccount, newCategory, newType, newAmount,
                        newDescription);
        balanceFacade.applyOperation(newOp);
        return newOp;
    }
}
