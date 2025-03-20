package hse.studying.bank.facades.analysis;

import hse.studying.bank.domains.bankaccount.BankAccount;
import hse.studying.bank.domains.category.Category;
import hse.studying.bank.domains.operation.Operation;
import hse.studying.bank.enums.TransferType;
import hse.studying.bank.facades.bankaccount.BankAccountFacade;
import hse.studying.bank.facades.category.CategoryFacade;
import hse.studying.bank.facades.operation.OperationFacade;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnalysisFacade {

    private final BankAccountFacade bankAccountFacade;
    private final CategoryFacade categoryFacade;
    private final OperationFacade operationFacade;

    public double calculateBalanceDifference(
            BankAccount bankAccount, Date startDate,
            Date endDate) {
        return StreamSupport.stream(operationFacade.getOperations().spliterator(), false)
                .filter(operation -> operation.getBankAccount().equals(bankAccount))
                .filter(operation -> operation.getDate().after(startDate) && operation.getDate()
                        .before(endDate))
                .mapToDouble(operation -> operation.getType() == TransferType.INCOME
                        ? operation.getAmount()
                        : -operation.getAmount())
                .sum();
    }

    public Map<Category, List<Operation>> getIncomeByCategory(
            BankAccount bankAccount, Date startDate,
            Date endDate) {
        return StreamSupport.stream(operationFacade.getOperations().spliterator(), false)
                .filter(operation -> operation.getBankAccount().equals(bankAccount))
                .filter(operation -> operation.getDate().after(startDate) && operation.getDate()
                        .before(endDate))
                .filter(operation -> operation.getType() == TransferType.INCOME)
                .collect(Collectors.groupingBy(
                                Operation::getCategory
                        )
                );
    }

    public Map<Category, List<Operation>> getOutcomeByCategory(
            BankAccount bankAccount,
            Date startDate, Date endDate) {
        return StreamSupport.stream(operationFacade.getOperations().spliterator(), false)
                .filter(operation -> operation.getBankAccount().equals(bankAccount))
                .filter(operation -> operation.getDate().after(startDate) && operation.getDate()
                        .before(endDate))
                .filter(operation -> operation.getType() == TransferType.OUTCOME)
                .collect(Collectors.groupingBy(
                                Operation::getCategory
                        )
                );
    }
}
