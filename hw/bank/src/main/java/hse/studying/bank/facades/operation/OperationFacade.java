package hse.studying.bank.facades.operation;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.factories.operation.OperationFactory;
import hse.studying.bank.providers.FinanceRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class OperationFacade {

    private final OperationFactory operationFactory;
    private final FinanceRepository<Operation> operationRepository;

    public Operation createOperation(
            BankAccount bankAccount,
            Category category,
            TransferType type,
            double amount,
            String description) {
        var operation = operationFactory.createOperation(
                bankAccount, category, type, amount,
                description);

        if (type == TransferType.OUTCOME && amount > bankAccount.getBalance()) {
            throw new IllegalArgumentException("Not enough money");
        }

        if (type != category.getType()) {
            throw new IllegalArgumentException("Operation type and category type do not match");
        }

        return operationRepository.save(operation);
    }

    public Optional<Operation> getOperation(Long id) {
        return operationRepository.findById(id);
    }

    public Iterable<Operation> getOperations() {
        return operationRepository.findAll();
    }

    public Operation updateOperation(
            @NotNull Long id,
            @NotNull BankAccount newBankAccount,
            @NotNull Category newCategory,
            @NotNull TransferType newType,
            @Min(0) double newAmount,
            String newDescription
    ) {
        var operation = getOperation(id);
        if (operation.isEmpty()) {
            throw new IllegalArgumentException("Operation not found");
        }

        if (newType != newCategory.getType()) {
            throw new IllegalArgumentException("Operation type and category type do not match");
        }

        operation.get().setBankAccount(newBankAccount);
        operation.get().setCategory(newCategory);
        operation.get().setType(newType);
        operation.get().setAmount(newAmount);
        operation.get().setDescription(newDescription);
        return operationRepository.save(operation.get());
    }

    public Void deleteOperation(Long id) {
        operationRepository.deleteById(id);
        return null;
    }
}
