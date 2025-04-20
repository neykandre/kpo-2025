package hse.studying.bank.factories.operation;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import jakarta.validation.ConstraintViolationException;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class OperationFactoryTest {

    private final BankAccount testAccount = new BankAccount(1L, "Test", 100);
    private final Category testCategory = new Category(1L, "Food", TransferType.OUTCOME);
    @Autowired
    private OperationFactory factory;

    @Test
    void createOperation_ValidData_ReturnsCorrectObject() {
        Operation operation = factory.createOperation(
                testAccount,
                testCategory,
                TransferType.OUTCOME,
                50.0,
                "Lunch"
        );

        assertThat(operation)
                .isNotNull()
                .satisfies(op -> {
                    assertThat(op.getBankAccount()).isEqualTo(testAccount);
                    assertThat(op.getCategory()).isEqualTo(testCategory);
                    assertThat(op.getType()).isEqualTo(TransferType.OUTCOME);
                    assertThat(op.getAmount()).isEqualTo(50.0);
                    assertThat(op.getDescription()).isEqualTo("Lunch");
                    assertThat(op.getDate()).isCloseTo(new Date(), 1000);
                });
    }

    @Test
    void createOperation_NegativeAmount_ThrowsException() {
        assertThatThrownBy(() -> factory.createOperation(
                testAccount,
                testCategory,
                TransferType.OUTCOME,
                -10.0,
                "Invalid"
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("createOperation.amount: must be greater than or equal to 0");
    }

    @Test
    void createOperation_NullParameters_ThrowsExceptions() {
        assertThatThrownBy(() -> factory.createOperation(
                null,
                testCategory,
                TransferType.OUTCOME,
                10.0,
                ""
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("createOperation.bankAccount: must not be null");

        assertThatThrownBy(() -> factory.createOperation(
                testAccount,
                null,
                TransferType.OUTCOME,
                10.0,
                ""
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("createOperation.category: must not be null");

        assertThatThrownBy(() -> factory.createOperation(
                testAccount,
                testCategory,
                null,
                10.0,
                ""
        )).isInstanceOf(ConstraintViolationException.class)
                .hasMessageContaining("createOperation.type: must not be null");
    }
}