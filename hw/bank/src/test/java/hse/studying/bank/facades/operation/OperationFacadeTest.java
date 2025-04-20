package hse.studying.bank.facades.operation;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.factories.operation.OperationFactory;
import hse.studying.bank.providers.FinanceRepository;
import jakarta.validation.ConstraintViolationException;
import java.util.Date;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OperationFacadeTest {

    private final BankAccount testAccount = new BankAccount(1L, "Acc", 100);
    private final Category testCategory = new Category(1L, "Food", TransferType.OUTCOME);
    @Mock private OperationFactory factory;
    @Mock private FinanceRepository<Operation> repository;
    @InjectMocks private OperationFacade facade;

    // CREATE
    @Test
    void createOperation_ValidOutcome_Success() {
        Operation op = new Operation(
                1L, testAccount, testCategory,
                TransferType.OUTCOME, 50, new Date(), "Lunch");
        when(factory.createOperation(
                testAccount, testCategory,
                TransferType.OUTCOME, 50, "Lunch")).thenReturn(op);
        when(repository.save(op)).thenReturn(op);

        Operation result = facade.createOperation(
                testAccount, testCategory,
                TransferType.OUTCOME, 50, "Lunch");
        verify(repository).save(op);
        assertThat(result).isEqualTo(op);
    }

    @Test
    void createOperation_NegativeAmount_ValidationFailed() {
        when(factory.createOperation(
                testAccount, testCategory,
                TransferType.OUTCOME, -10, "Error")).thenThrow(new ConstraintViolationException(
                "createOperation.amount: must be greater than or equal to 0", null)
        );
        assertThatThrownBy(() -> facade.createOperation(
                testAccount, testCategory, TransferType.OUTCOME, -10, "Error"))
                .isInstanceOf(ConstraintViolationException.class);
    }

    // UPDATE
    @Test
    void updateOperation_ChangeAmount_Success() {
        Operation existing = new Operation(
                1L, testAccount, testCategory,
                TransferType.OUTCOME, 50, new Date(), "");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);

        Operation updated = facade.updateOperation(
                1L, testAccount, testCategory,
                TransferType.OUTCOME, 30, "Updated");
        assertThat(updated.getAmount()).isEqualTo(30);
    }

    @Test
    void updateOperation_InvalidCategoryType_ThrowsException() {
        Category invalidCategory = new Category(2L, "Salary", TransferType.INCOME);
        Operation existing = new Operation(
                1L, testAccount, invalidCategory,
                TransferType.INCOME, 50, new Date(), "");
        when(repository.findById(1L)).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> facade.updateOperation(
                1L, testAccount, invalidCategory,
                TransferType.OUTCOME, 30, "Error"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void deleteOperation_Success() {
        Operation op = new Operation(1L, testAccount, testCategory, TransferType.OUTCOME, 50, new Date(), "");

        facade.deleteOperation(1L);
        verify(repository).deleteById(op.getId());
    }

    @Test
    void createOperation_NotEnoughMoney_ThrowsException() {
        assertThatThrownBy(() -> facade.createOperation(
                testAccount, testCategory, TransferType.OUTCOME, 150, "Error"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}