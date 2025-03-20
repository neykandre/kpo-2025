package hse.studying.bank.facades;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.analysis.AnalysisFacade;
import hse.studying.bank.facades.operation.OperationFacade;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnalysisFacadeTest {

    @Mock
    private OperationFacade operationFacade;

    @Mock
    private BankAccount account;

    @InjectMocks
    private AnalysisFacade analysisFacade;

    @Test
    void calculateDifference_NoOperations_ReturnsZero() {
        when(operationFacade.getOperations()).thenReturn(List.of());

        double result = analysisFacade.calculateBalanceDifference(account, new Date(), new Date());
        assertThat(result).isZero();
    }

    @Test
    void groupIncomeByCategory_EmptyResult_ReturnsEmptyMap() {
        when(operationFacade.getOperations()).thenReturn(List.of());

        Map<Category, List<Operation>> result =
                analysisFacade.getIncomeByCategory(account, new Date(), new Date());
        assertThat(result).isEqualTo(Map.of());
    }

    @Test
    void calculateDifference_ValidOperations_ReturnsCorrectValue() {
        List<Operation> operations = List.of(
                new Operation(
                        1L, account, new Category(1L, "Salary", TransferType.INCOME), TransferType.INCOME, 200.0,
                        new Date(), ""),
                new Operation(
                        2L,
                        account,
                        new Category(2L, "Food", TransferType.OUTCOME),
                        TransferType.OUTCOME,
                        50.0,
                        new Date(),
                        "")
        );

        when(operationFacade.getOperations()).thenReturn(operations);

        double result = analysisFacade.calculateBalanceDifference(account, new Date(0), new Date());
        assertEquals(150.0, result);
    }
}